package com.finance_tracker.finance_tracker.features.analytics.delegates

import com.finance_tracker.finance_tracker.core.common.date.currentYearMonth
import com.finance_tracker.finance_tracker.core.common.date.models.YearMonth
import com.finance_tracker.finance_tracker.domain.interactors.CurrenciesInteractor
import com.finance_tracker.finance_tracker.domain.interactors.TransactionsInteractor
import com.finance_tracker.finance_tracker.domain.models.TransactionType
import com.finance_tracker.finance_tracker.domain.models.TxsByCategoryChart
import com.finance_tracker.finance_tracker.features.analytics.analytics.AnalyticsScreenAnalytics
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.datetime.Clock
import kotlin.coroutines.CoroutineContext

class MonthTxsByCategoryDelegate(
    private val transactionsInteractor: TransactionsInteractor,
    currenciesInteractor: CurrenciesInteractor,
    private val analyticsScreenAnalytics: AnalyticsScreenAnalytics
): CoroutineScope {

    override val coroutineContext: CoroutineContext = SupervisorJob() + Dispatchers.Main.immediate

    private val currencyRatesFlow = currenciesInteractor.getCurrencyRatesFlow()
        .stateIn(this, started = SharingStarted.Lazily, initialValue = mapOf())

    private val primaryCurrency = currenciesInteractor.getPrimaryCurrencyFlow()
        .shareIn(this, started = SharingStarted.Lazily, replay = 1)

    private val _monthTransactionsByCategory = MutableStateFlow<TxsByCategoryChart?>(null)
    val monthTransactionsByCategory = _monthTransactionsByCategory.asStateFlow()

    private val _selectedYearMonth = MutableStateFlow(Clock.System.currentYearMonth())
    val selectedMonth = _selectedYearMonth.asStateFlow()

    private val _isLoadingMonthTxsByCategory = MutableStateFlow(false)
    val isLoadingMonthTxsByCategory = _isLoadingMonthTxsByCategory.asStateFlow()

    private var loadMonthTxsByCategoryJob: Job? = null

    private val selectedTransactionTypeFlow = MutableStateFlow(TransactionType.Expense)

    init {
        subscribeAnalyticsEvents()
    }

    fun updateMonthTxsByCategory() {
        loadMonthTxsByCategory(
            transactionType = selectedTransactionTypeFlow.value,
            yearMonth = selectedMonth.value
        )
    }

    private fun loadMonthTxsByCategory(transactionType: TransactionType, yearMonth: YearMonth) {
        loadMonthTxsByCategoryJob?.cancel()
        loadMonthTxsByCategoryJob = primaryCurrency.combine(currencyRatesFlow) { currency, currencyRates ->
            _isLoadingMonthTxsByCategory.value = true
            _monthTransactionsByCategory.value = transactionsInteractor.getTransactions(
                transactionType = transactionType,
                yearMonth = yearMonth,
                primaryCurrency = currency,
                currencyRates = currencyRates
            )
            _isLoadingMonthTxsByCategory.value = false
        }.launchIn(this)
    }

    private fun subscribeAnalyticsEvents() {
        combine(selectedTransactionTypeFlow, selectedMonth) { selectedTransactionType, selectedMonth ->
            analyticsScreenAnalytics.trackTxsByCategory(selectedTransactionType, selectedMonth)
        }.launchIn(this)
    }

    fun onMonthSelect(yearMonth: YearMonth) {
        _selectedYearMonth.value = yearMonth
        loadMonthTxsByCategory(
            transactionType = selectedTransactionTypeFlow.value,
            yearMonth = yearMonth
        )
    }

    fun onTransactionTypeSelect(transactionType: TransactionType) {
        loadMonthTxsByCategory(
            transactionType = transactionType,
            yearMonth = selectedMonth.value
        )
    }

    fun setSelectedTransactionType(transactionType: TransactionType) {
        selectedTransactionTypeFlow.value = transactionType
    }
}