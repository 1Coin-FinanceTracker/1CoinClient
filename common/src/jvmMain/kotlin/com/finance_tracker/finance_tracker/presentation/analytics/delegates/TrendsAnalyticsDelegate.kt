package com.finance_tracker.finance_tracker.presentation.analytics.delegates

import androidx.compose.runtime.Composable
import com.finance_tracker.finance_tracker.core.common.date.currentLocalDate
import com.finance_tracker.finance_tracker.core.common.strings.getDateMonthNameStringResBy
import com.finance_tracker.finance_tracker.core.common.strings.getMonthNameStringResBy
import com.finance_tracker.finance_tracker.core.common.strings.getWeekDayStringRes
import com.finance_tracker.finance_tracker.core.common.toDateTime
import com.finance_tracker.finance_tracker.data.repositories.CurrenciesRepository
import com.finance_tracker.finance_tracker.domain.models.Amount
import com.finance_tracker.finance_tracker.domain.models.Currency
import com.finance_tracker.finance_tracker.domain.models.CurrencyRates
import com.finance_tracker.finance_tracker.domain.models.TransactionType
import com.finance_tracker.finance_tracker.presentation.analytics.analytics.AnalyticsScreenAnalytics
import com.finance_tracker.finance_tracker.presentation.analytics.models.TrendBarDetails
import com.finance_tracker.finance_tracker.presentation.analytics.peroid_bar_chart.PeriodChip
import com.finance_tracker.finance_tracker.presentation.common.formatters.AmountFormatMode
import com.finance_tracker.finance_tracker.presentation.common.formatters.format
import com.financetracker.financetracker.data.GetTransactionsForPeriod
import com.financetracker.financetracker.data.TransactionsEntityQueries
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlin.coroutines.CoroutineContext

class TrendsAnalyticsDelegate(
    private val transactionsEntityQueries: TransactionsEntityQueries,
    currenciesRepository: CurrenciesRepository,
    private val analyticsScreenAnalytics: AnalyticsScreenAnalytics
): CoroutineScope {

    override val coroutineContext: CoroutineContext = SupervisorJob() + Dispatchers.Main.immediate

    private val databaseCurrencyRatesFlow = currenciesRepository.getCurrencyRatesFlow()
    private val primaryCurrencyFlow = currenciesRepository.getPrimaryCurrencyFlow()

    private val _selectedPeriodChipFlow = MutableStateFlow(PeriodChip.Week)
    val selectedPeriodChipFlow = _selectedPeriodChipFlow.asStateFlow()

    private val selectedTransactionTypeFlow = MutableStateFlow(TransactionType.Expense)

    private val weekTrendTransform: (
        List<GetTransactionsForPeriod>, CurrencyRates, Currency
    ) -> List<TrendBarDetails> = { transactions, currencyRates, primaryCurrency ->
        associateTransactionsByBars(
            transactions = transactions,
            barsRange = 1..DaysInWeek,
            transformDateToBarOrder = { date ->
                return@associateTransactionsByBars date.dayOfWeek.ordinal + 1
            },
            currencyRates = currencyRates,
            primaryCurrency = primaryCurrency
        )
            .map { (dayOfWeek, txsAmounts) ->
                mapTxsAmountsToTrendBarDetails(
                    txsAmounts = txsAmounts,
                    primaryCurrency = primaryCurrency,
                    title = { stringResource(getWeekDayStringRes(dayOfWeek)) }
                )
            }
    }

    private val monthTrendTransform: (
        List<GetTransactionsForPeriod>, CurrencyRates, Currency
    ) -> List<TrendBarDetails> = { transactions, currencyRates, primaryCurrency ->
        val startOfMonthDate = getStartOfNextMonthDate()
        val maxDayOfMonth = LocalDate.fromEpochDays(startOfMonthDate.toEpochDays())
            .minus(DateTimeUnit.DAY)
            .dayOfMonth

        associateTransactionsByBars(
            transactions = transactions,
            barsRange = 1..maxDayOfMonth,
            transformDateToBarOrder = { date -> date.dayOfMonth },
            currencyRates = currencyRates,
            primaryCurrency = primaryCurrency
        )
            .map { (dayOfMonth, txsAmounts) ->
                mapTxsAmountsToTrendBarDetails(
                    txsAmounts = txsAmounts,
                    primaryCurrency = primaryCurrency,
                    title = {
                        val monthNumber = Clock.System.currentLocalDate().monthNumber
                        stringResource(getDateMonthNameStringResBy(monthNumber), dayOfMonth)
                    }
                )
            }
    }

    private val yearTrendTransform: (
        List<GetTransactionsForPeriod>, CurrencyRates, Currency
    ) -> List<TrendBarDetails> = { transactions, currencyRates, primaryCurrency ->
        associateTransactionsByBars(
            transactions = transactions,
            barsRange = 1..MonthsInYear,
            transformDateToBarOrder = { date -> date.monthNumber },
            currencyRates = currencyRates,
            primaryCurrency = primaryCurrency
        )
            .map { (monthNumberOfYear, txsAmounts) ->
                mapTxsAmountsToTrendBarDetails(
                    txsAmounts = txsAmounts,
                    primaryCurrency = primaryCurrency,
                    title = { stringResource(getMonthNameStringResBy(monthNumberOfYear)) }
                )
            }
    }

    private val expenseWeekTrendFlow = subscribeTransactionsData(
        transactionType = TransactionType.Expense,
        fromDate = getStartOfWeekDate(),
        toDate = getStartOfNextWeekDate(),
        transform = weekTrendTransform
    )

    private val incomeWeekTrendFlow = subscribeTransactionsData(
        transactionType = TransactionType.Income,
        fromDate = getStartOfWeekDate(),
        toDate = getStartOfNextWeekDate(),
        transform = weekTrendTransform
    )

    private val expenseMonthTrendFlow = subscribeTransactionsData(
        transactionType = TransactionType.Expense,
        fromDate = getStartOfMonthDate(),
        toDate = getStartOfNextMonthDate(),
        transform = monthTrendTransform
    )

    private val incomeMonthTrendFlow = subscribeTransactionsData(
        transactionType = TransactionType.Income,
        fromDate = getStartOfMonthDate(),
        toDate = getStartOfNextMonthDate(),
        transform = monthTrendTransform
    )

    private val expenseYearTrendFlow = subscribeTransactionsData(
        transactionType = TransactionType.Expense,
        fromDate = getStartOfYearDate(),
        toDate = getStartOfNextYearDate(),
        transform = yearTrendTransform
    )

    private val incomeYearTrendFlow = subscribeTransactionsData(
        transactionType = TransactionType.Income,
        fromDate = getStartOfYearDate(),
        toDate = getStartOfNextYearDate(),
        transform = yearTrendTransform
    )

    private val expenseFlow = combine(
        selectedPeriodChipFlow, expenseWeekTrendFlow, expenseMonthTrendFlow, expenseYearTrendFlow
    ) { selectedPeriodChip, expenseWeekTrend, expenseMonthTrend, expenseYearTrend ->
        when (selectedPeriodChip) {
            PeriodChip.Week -> expenseWeekTrend
            PeriodChip.Month -> expenseMonthTrend
            PeriodChip.Year -> expenseYearTrend
        }
    }
        .flowOn(Dispatchers.Default)
        .stateIn(this@TrendsAnalyticsDelegate, started = SharingStarted.Lazily, initialValue = listOf())

    private val incomeFlow = combine(
        selectedPeriodChipFlow, incomeWeekTrendFlow, incomeMonthTrendFlow, incomeYearTrendFlow
    ) { selectedPeriodChip, incomeWeekTrend, incomeMonthTrend, incomeYearTrend ->
        when (selectedPeriodChip) {
            PeriodChip.Week -> incomeWeekTrend
            PeriodChip.Month -> incomeMonthTrend
            PeriodChip.Year -> incomeYearTrend
        }
    }
        .flowOn(Dispatchers.Default)
        .stateIn(this@TrendsAnalyticsDelegate, started = SharingStarted.Lazily, initialValue = listOf())

    val trendFlow = combine(selectedTransactionTypeFlow, expenseFlow, incomeFlow) {
            selectedTransactionType, expense, income ->

        when (selectedTransactionType) {
            TransactionType.Expense -> expense
            TransactionType.Income -> income
        }
    }
        .flowOn(Dispatchers.Default)
        .stateIn(this@TrendsAnalyticsDelegate, started = SharingStarted.Lazily, initialValue = listOf())

    val totalFlow = combine(trendFlow, primaryCurrencyFlow) {
        trend, primaryCurrency ->
        Amount(
            amountValue = trend.sumOf { it.value },
            currency = primaryCurrency
        )
    }
        .flowOn(Dispatchers.Default)
        .stateIn(this@TrendsAnalyticsDelegate, started = SharingStarted.Lazily, initialValue = Amount.default)

    init {
        subscribeAnalyticsEvents()
    }

    private fun subscribeAnalyticsEvents() {
        combine(selectedTransactionTypeFlow, selectedPeriodChipFlow) { selectedTransactionType, selectedPeriodChip ->
            analyticsScreenAnalytics.trackTrendEvent(selectedTransactionType, selectedPeriodChip)
        }.launchIn(this)
    }

    private fun associateTransactionsByBars(
        transactions: List<GetTransactionsForPeriod>,
        barsRange: IntRange,
        transformDateToBarOrder: (LocalDate) -> Int,
        currencyRates: CurrencyRates,
        primaryCurrency: Currency
    ): List<Pair<Int, List<Double>>> {
        val map = mutableMapOf<Int, MutableList<Double>>()
        barsRange.onEach { map[it] = mutableListOf() }

        transactions.forEach { transaction ->
            val date = transaction.date.date

            val amount = Amount(
                currency = Currency.getByCode(transaction.amountCurrency),
                amountValue = transaction.amount
            ).convertToCurrency(
                currencyRates = currencyRates,
                toCurrency = primaryCurrency
            )

            val dayOfWeek = transformDateToBarOrder.invoke(date)
            map[dayOfWeek]?.add(amount)
        }

        return map.toList().sortedBy { it.first }
    }

    private fun mapTxsAmountsToTrendBarDetails(
        txsAmounts: List<Double>,
        primaryCurrency: Currency,
        title: @Composable () -> String
    ): TrendBarDetails {
        val value = txsAmounts.sum()
        return TrendBarDetails(
            title = title,
            formattedValue = {
                Amount(
                    amountValue = value,
                    currency = primaryCurrency
                ).format(mode = AmountFormatMode.NoSigns)
            },
            value = value
        )
    }

    private fun subscribeTransactionsData(
        transactionType: TransactionType,
        fromDate: LocalDate,
        toDate: LocalDate,
        transform: (List<GetTransactionsForPeriod>, CurrencyRates, Currency) -> List<TrendBarDetails>
    ): Flow<List<TrendBarDetails>> {
        return combine(transactionsEntityQueries.getTransactionsForPeriod(
            transactionType = transactionType,
            fromDate = fromDate.toDateTime(),
            toDate = toDate.toDateTime()
        ).asFlow().mapToList(), databaseCurrencyRatesFlow, primaryCurrencyFlow, transform = transform)
            .flowOn(Dispatchers.Default)
            .stateIn(this@TrendsAnalyticsDelegate, started = SharingStarted.Lazily, initialValue = listOf())
    }

    private fun getStartOfWeekDate(): LocalDate {
        val nowDate = Clock.System.currentLocalDate()
        return LocalDate(
            year = nowDate.year,
            monthNumber = nowDate.monthNumber,
            dayOfMonth = nowDate.dayOfMonth
        ).minus(nowDate.dayOfWeek.ordinal, DateTimeUnit.DAY)
    }

    private fun getStartOfNextWeekDate(): LocalDate {
        return getStartOfWeekDate().plus(DaysInWeek, DateTimeUnit.DAY)
    }

    private fun getStartOfMonthDate(): LocalDate {
        val nowDate = Clock.System.currentLocalDate()
        return LocalDate(
            year = nowDate.year,
            monthNumber = nowDate.monthNumber,
            dayOfMonth = 1
        )
    }

    private fun getStartOfNextMonthDate(): LocalDate {
        val nowDate = Clock.System.currentLocalDate()
        return LocalDate(
            year = nowDate.year,
            monthNumber = nowDate.monthNumber + 1,
            dayOfMonth = 1
        )
    }

    private fun getStartOfYearDate(): LocalDate {
        val nowDate = Clock.System.currentLocalDate()
        return LocalDate(
            year = nowDate.year,
            monthNumber = 1,
            dayOfMonth = 1
        )
    }

    private fun getStartOfNextYearDate(): LocalDate {
        val nowDate = Clock.System.currentLocalDate()
        return LocalDate(
            year = nowDate.year + 1,
            monthNumber = 1,
            dayOfMonth = 1
        )
    }

    fun setPeriodChip(periodChip: PeriodChip) {
        _selectedPeriodChipFlow.value = periodChip
    }

    fun setSelectedTransactionType(transactionType: TransactionType) {
        selectedTransactionTypeFlow.value = transactionType
    }

    companion object {
        private const val DaysInWeek = 7
        private const val MonthsInYear = 12
    }
}