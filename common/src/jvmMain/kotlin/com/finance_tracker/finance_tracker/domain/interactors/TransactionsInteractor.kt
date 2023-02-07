package com.finance_tracker.finance_tracker.domain.interactors

import app.cash.paging.PagingData
import app.cash.paging.insertSeparators
import app.cash.paging.map
import com.finance_tracker.finance_tracker.AppDatabase
import com.finance_tracker.finance_tracker.core.common.date.models.YearMonth
import com.finance_tracker.finance_tracker.core.common.suspendTransaction
import com.finance_tracker.finance_tracker.core.theme.ChartConfig
import com.finance_tracker.finance_tracker.data.repositories.AccountsRepository
import com.finance_tracker.finance_tracker.data.repositories.TransactionsRepository
import com.finance_tracker.finance_tracker.domain.models.Amount
import com.finance_tracker.finance_tracker.domain.models.Category
import com.finance_tracker.finance_tracker.domain.models.Currency
import com.finance_tracker.finance_tracker.domain.models.CurrencyRates
import com.finance_tracker.finance_tracker.domain.models.Transaction
import com.finance_tracker.finance_tracker.domain.models.TransactionListModel
import com.finance_tracker.finance_tracker.domain.models.TransactionType
import com.finance_tracker.finance_tracker.domain.models.TxsByCategoryChart
import io.github.koalaplot.core.util.toString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

private const val LastTransactionsLimit = 3L

class TransactionsInteractor(
    private val transactionsRepository: TransactionsRepository,
    private val accountsRepository: AccountsRepository,
    private val appDatabase: AppDatabase
) {

    fun getLastTransactions(): Flow<List<TransactionListModel.Data>> {
        return transactionsRepository.getLastTransactions(limit = LastTransactionsLimit)
            .map { transactions ->
                transactions.map(TransactionListModel::Data)
            }
    }

    private suspend fun updateAccountBalanceForTransaction(
        oldTransaction: Transaction?,
        newTransaction: Transaction?
    ) {
        val transaction = oldTransaction ?: newTransaction ?: return

        val oldTransactionAmount = oldTransaction.signedAmountValue()
        val newTransactionAmount = newTransaction.signedAmountValue()

        val accountId = transaction.account.id
        val accountBalanceDiff = newTransactionAmount - oldTransactionAmount
        accountsRepository.increaseAccountBalance(accountId, accountBalanceDiff)
    }

    private fun Transaction?.signedAmountValue(): Double {
        if (this == null) return 0.0

        return amount.amountValue.unaryMinusIf { type == TransactionType.Expense }
    }

    private fun Double.unaryMinusIf(condition: () -> Boolean): Double {
        return if (condition()) {
            unaryMinus()
        } else {
            this
        }
    }

    suspend fun deleteTransactions(transactions: List<Transaction>) {
        appDatabase.suspendTransaction {
            deleteTransactionsInternal(transactions)
        }
    }

    private suspend fun deleteTransactionsInternal(transactions: List<Transaction>) {
        transactions.forEach {
            deleteTransactionInternal(it)
        }
    }

    suspend fun deleteTransaction(
        transaction: Transaction
    ) {
        appDatabase.suspendTransaction {
            deleteTransactionInternal(transaction)
        }
    }

    private suspend fun deleteTransactionInternal(transaction: Transaction) {
        transactionsRepository.deleteTransaction(transaction)
        updateAccountBalanceForTransaction(
            oldTransaction = transaction,
            newTransaction = null
        )
    }

    suspend fun addTransaction(transaction: Transaction) {
        appDatabase.suspendTransaction {
            transactionsRepository.addOrUpdateTransaction(transaction)
            updateAccountBalanceForTransaction(
                oldTransaction = null,
                newTransaction = transaction
            )
        }
    }

    suspend fun updateTransaction(
        oldTransaction: Transaction,
        newTransaction: Transaction
    ) {
        appDatabase.suspendTransaction {
            transactionsRepository.addOrUpdateTransaction(newTransaction)
            updateAccountBalanceForTransaction(
                oldTransaction = oldTransaction,
                newTransaction = newTransaction
            )
        }
    }

    @Suppress("MagicNumber")
    suspend fun getTransactions(
        transactionType: TransactionType,
        yearMonth: YearMonth,
        primaryCurrency: Currency,
        currencyRates: CurrencyRates
    ): TxsByCategoryChart {
        return withContext(Dispatchers.Default) {
            val transactions = transactionsRepository.getTransactions(transactionType, yearMonth)
            val totalAmount = transactions.sumOf {
                it.amount.convertToCurrency(currencyRates, primaryCurrency)
            }

            val sortRules: (TxsByCategoryChart.Piece) -> Double = {
                it.amount.convertToCurrency(currencyRates, primaryCurrency)
            }
            val rawCategoryPieces = transactions
                .groupBy { it._category }
                .map { (category, transactions) ->
                    TxsByCategoryChart.Piece(
                        category = category,
                        amount = Amount(
                            currency = primaryCurrency,
                            amountValue = transactions.sumOf {
                                it.amount.convertToCurrency(currencyRates, primaryCurrency)
                            }
                        ),
                        percentValue = 0f,
                        transactionsCount = transactions.size
                    )
                }.sortedByDescending { sortRules.invoke(it) }

            val categoryPercentValues = calculateCategoryPercentValues(
                rawCategoryPieces, totalAmount
            )
            val allPieces = rawCategoryPieces.mapIndexed { index, piece ->
                piece.copy(
                    percentValue = categoryPercentValues[index].toFloat()
                )
            }

            val otherPieces = (allPieces.drop(OtherCategoryCountThreshold) +
                    allPieces.filter { it.percentValue < OtherMinPercentThreshold }).toSet()
                .sortedByDescending { sortRules.invoke(it) }
                .takeIf { it.size > 1 }
                .orEmpty()

            allPieces.forEachIndexed { index, piece ->
                piece.color = if (piece in otherPieces) {
                    ChartConfig.colorOther
                } else {
                    ChartConfig.colors[index % ChartConfig.colors.size]
                }
            }

            val mainPieces = if (otherPieces.isNotEmpty()) {
                allPieces - otherPieces.toSet() + TxsByCategoryChart.Piece(
                    category = Category(
                        id = -2,
                        name = "Other",
                        iconId = "ic_more_horiz"
                    ),
                    amount = Amount(
                        currency = primaryCurrency,
                        amountValue = otherPieces.sumOf {
                            it.amount.convertToCurrency(currencyRates, primaryCurrency)
                        }
                    ),
                    percentValue = otherPieces.sumOf {
                        it.percentValue.toDouble()
                    }.toFloat(),
                    transactionsCount = otherPieces.sumOf { it.transactionsCount }
                ).apply {
                    color = ChartConfig.colorOther
                }
            } else {
                allPieces
            }

            return@withContext TxsByCategoryChart(
                mainPieces = mainPieces,
                allPieces = allPieces,
                total = Amount(
                    currency = primaryCurrency,
                    amountValue = totalAmount
                )
            )
        }
    }

    private fun calculateCategoryPercentValues(
        allCategoryPieces: List<TxsByCategoryChart.Piece>,
        totalAmount: Double
    ): List<Double> {
        var accumulatedTotalPercent = 0.0
        val reversedCategoryPercentValues = mutableListOf<Double>()
        for (index in allCategoryPieces.size - 1 downTo  0) {
            val piece = allCategoryPieces[index]
            if (index == 0) {
                reversedCategoryPercentValues.add(100.0 - accumulatedTotalPercent)
            } else {
                val percentValue = (piece.amount.amountValue / totalAmount * 100).toString(
                    TxsByCategoryChart.Piece.PERCENT_PRECISION
                ).toDouble()
                accumulatedTotalPercent += percentValue
                reversedCategoryPercentValues.add(percentValue)
            }
        }
        return reversedCategoryPercentValues.reversed()
    }

    fun getPaginatedTransactions(): Flow<PagingData<TransactionListModel>> {
        return transactionsRepository.getPaginatedTransactions()
            .map { it.map(TransactionListModel::Data) }
            .map(::insertSeparators)
    }

    fun getPaginatedTransactionsByAccountId(id: Long): Flow<PagingData<TransactionListModel>> {
        return transactionsRepository.getPaginatedTransactionsByAccountId(id)
            .map { it.map(TransactionListModel::Data) }
            .map(::insertSeparators)
            .flowOn(Dispatchers.Default)
    }

    @Suppress("LABEL_NAME_CLASH")
    private fun insertSeparators(
        pagingData: PagingData<TransactionListModel.Data>
    ): PagingData<TransactionListModel> {
        return pagingData.insertSeparators { data: TransactionListModel.Data?,
                                             data2: TransactionListModel.Data? ->
            val previousTransaction = data?.transaction
            val currentTransaction = data2?.transaction ?: return@insertSeparators null
            if (previousTransaction == null ||
                previousTransaction.dateTime.date != currentTransaction.dateTime.date) {
                TransactionListModel.DateAndDayTotal(
                    dateTime = currentTransaction.dateTime,
                )
            } else {
                null
            }
        }
    }

    companion object {
        private const val OtherMinPercentThreshold = 1
        private val OtherCategoryCountThreshold = ChartConfig.colors.size
    }
}