package com.finance_tracker.finance_tracker.data.repositories

import app.cash.paging.Pager
import app.cash.paging.PagingConfig
import app.cash.paging.PagingData
import com.finance_tracker.finance_tracker.core.common.date.currentLocalDateTime
import com.finance_tracker.finance_tracker.core.common.date.models.YearMonth
import com.finance_tracker.finance_tracker.core.common.zeroPrefixed
import com.finance_tracker.finance_tracker.data.data_sources.TransactionsPagingSource
import com.finance_tracker.finance_tracker.data.data_sources.TransactionsPagingSourceFactory
import com.finance_tracker.finance_tracker.data.database.mappers.fullTransactionMapper
import com.finance_tracker.finance_tracker.domain.models.Transaction
import com.finance_tracker.finance_tracker.domain.models.TransactionType
import com.financetracker.financetracker.data.TransactionsEntityQueries
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.number

private const val PageSize = 15

class TransactionsRepository(
    private val transactionsEntityQueries: TransactionsEntityQueries,
    private val transactionsPagingSourceFactory: TransactionsPagingSourceFactory,
) {

    private val paginatedTransactions: Flow<PagingData<Transaction>> =
        Pager(PagingConfig(pageSize = PageSize)) {
            TransactionsPagingSource(transactionsEntityQueries)
        }.flow
            .flowOn(Dispatchers.Default)

    suspend fun getTransactions(transactionType: TransactionType, yearMonth: YearMonth): List<Transaction> {
        return withContext(Dispatchers.Default) {
            transactionsEntityQueries.getFullTransactions(
                transactionType = transactionType,
                monthNumber = yearMonth.month.number.zeroPrefixed(2),
                year = yearMonth.year.toString(),
                mapper = fullTransactionMapper
            ).executeAsList()
        }
    }

    suspend fun deleteTransaction(transaction: Transaction) {
        withContext(Dispatchers.Default) {
            val transactionId = transaction.id ?: return@withContext
            transactionsEntityQueries.deleteTransactionById(transactionId)
        }
    }

    suspend fun deleteCategoryForTransactionsByCategoryId(categoryId: Long) {
        withContext(Dispatchers.Default) {
            transactionsEntityQueries.deleteCategoryForTransactionsByCategoryId(categoryId)
        }
    }

    suspend fun addOrUpdateTransaction(transaction: Transaction) {
        withContext(Dispatchers.Default) {
            transactionsEntityQueries.insertTransaction(
                id = transaction.id,
                type = transaction.type,
                amount = transaction.amount.amountValue,
                amountCurrency = transaction.amount.currency.code,
                categoryId = transaction._category?.id,
                accountId = transaction.account.id,
                insertionDate = transaction.insertionDateTime ?: Clock.System.currentLocalDateTime(),
                date = transaction.dateTime,
            )
        }
    }

    fun getPaginatedTransactions(): Flow<PagingData<Transaction>> {
        return paginatedTransactions
    }

    fun getPaginatedTransactionsByAccountId(id: Long): Flow<PagingData<Transaction>> {
        return Pager(PagingConfig(pageSize = PageSize)) {
            transactionsPagingSourceFactory.create(id)
        }.flow
            .flowOn(Dispatchers.Default)
    }

    fun getLastTransactions(limit: Long) : Flow<List<Transaction>> {
        return transactionsEntityQueries.getLastTransactions(
            limit = limit,
            mapper = fullTransactionMapper
        )
            .asFlow()
            .mapToList()
            .flowOn(Dispatchers.Default)
    }
}