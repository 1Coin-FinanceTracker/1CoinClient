package com.finance_tracker.finance_tracker.presentation.home

import com.finance_tracker.finance_tracker.core.common.view_models.BaseViewModel
import com.finance_tracker.finance_tracker.data.repositories.AccountsRepository
import com.finance_tracker.finance_tracker.domain.interactors.CurrenciesInteractor
import com.finance_tracker.finance_tracker.domain.interactors.DashboardSettingsInteractor
import com.finance_tracker.finance_tracker.domain.interactors.TransactionsInteractor
import com.finance_tracker.finance_tracker.domain.models.Account
import com.finance_tracker.finance_tracker.domain.models.Amount
import com.finance_tracker.finance_tracker.domain.models.Currency
import com.finance_tracker.finance_tracker.domain.models.CurrencyRates
import com.finance_tracker.finance_tracker.domain.models.Transaction
import com.finance_tracker.finance_tracker.presentation.analytics.delegates.MonthTxsByCategoryDelegate
import com.finance_tracker.finance_tracker.presentation.analytics.delegates.TrendsAnalyticsDelegate
import com.finance_tracker.finance_tracker.presentation.home.analytics.HomeAnalytics
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

class HomeViewModel(
    val trendsAnalyticsDelegate: TrendsAnalyticsDelegate,
    val monthTxsByCategoryDelegate: MonthTxsByCategoryDelegate,
    private val accountsRepository: AccountsRepository,
    private val currenciesInteractor: CurrenciesInteractor,
    transactionsInteractor: TransactionsInteractor,
    private val homeAnalytics: HomeAnalytics,
    dashboardSettingsInteractor: DashboardSettingsInteractor
): BaseViewModel<HomeAction>() {

    private val currencyRatesFlow = currenciesInteractor.getCurrencyRatesFlow()
        .stateIn(viewModelScope, started = SharingStarted.Lazily, initialValue = mapOf())

    private val _accounts = MutableStateFlow<List<Account>>(emptyList())
    val accounts = _accounts.asStateFlow()

    val widgets = dashboardSettingsInteractor.getActiveDashboardWidgets()
        .stateIn(viewModelScope, started = SharingStarted.Lazily, initialValue = emptyList())

    val lastTransactions = transactionsInteractor.getLastTransactions()
        .stateIn(viewModelScope, started = SharingStarted.Lazily, initialValue = emptyList())

    private val _totalBalance = MutableStateFlow(Amount.default)
    val totalBalance = _totalBalance.asStateFlow()

    private var loadTotalAmountJob: Job? = null

    private val totalCurrencyFlow = currenciesInteractor.getPrimaryCurrencyFlow()
        .stateIn(viewModelScope, started = SharingStarted.Lazily, initialValue = Currency.default)

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Napier.e("HomeViewModel", throwable)
    }

    init {
        updateCurrencyRates()
        homeAnalytics.trackScreenOpen()
    }

    fun onScreenComposed() {
        loadAccounts()
        loadTotalAmount()
        updateMonthTxsByCategory()
    }

    private fun updateMonthTxsByCategory() {
        monthTxsByCategoryDelegate.updateMonthTxsByCategory()
    }

    private fun updateCurrencyRates() {
        viewModelScope.launch {
            currenciesInteractor.updateCurrencyRates()
        }
    }

    private fun loadAccounts() {
        val oldAccountsCount = _accounts.value.size
        viewModelScope.launch {
            _accounts.value  = accountsRepository.getAllAccountsFromDatabase()
            val newAccountsCount = _accounts.value.size
            if (oldAccountsCount in 1 until newAccountsCount) {
                viewAction = HomeAction.ScrollToItemAccounts(newAccountsCount - 1)
            }
        }
    }

    private fun loadTotalAmount() {
        loadTotalAmountJob?.cancel()
        loadTotalAmountJob = currencyRatesFlow.combine(totalCurrencyFlow) {
                currencyRates, baseCurrency ->
            val balanceInBaseCurrency = getTotalBalanceAmountInCurrency(
                currencyRates = currencyRates,
                currency = baseCurrency
            )
            _totalBalance.value = Amount(
                currency = baseCurrency,
                amountValue = balanceInBaseCurrency
            )
        }
            .launchIn(viewModelScope + exceptionHandler)
    }

    private suspend fun getTotalBalanceAmountInCurrency(
        currencyRates: CurrencyRates,
        currency: Currency
    ): Double {
        return accountsRepository.getAllAccountsFromDatabase()
            .sumOf { account ->
                account.balance.convertToCurrency(
                    currencyRates = currencyRates,
                    toCurrency = currency
                )
            }
    }

    fun onMyAccountsClick() {
        homeAnalytics.trackMyAccountsClick()
        viewAction = HomeAction.OpenAccountsScreen
    }

    fun onAccountClick(account: Account) {
        homeAnalytics.trackAccountClick(account)
        viewAction = HomeAction.OpenAccountDetailScreen(account)
    }

    fun onAddAccountClick() {
        homeAnalytics.trackAddAccountClick()
        viewAction = HomeAction.OpenAddAccountScreen
    }

    fun onLastTransactionsClick() {
        homeAnalytics.trackLastTransactionsClick()
        viewAction = HomeAction.OpenTransactionsScreen
    }

    fun onTransactionClick(transaction: Transaction) {
        homeAnalytics.trackTransactionClick(transaction)
        viewAction = HomeAction.OpenEditTransactionScreen(transaction)
    }

    fun onSettingsClick() {
        homeAnalytics.trackSettingsClick()
        viewAction = HomeAction.ShowSettingsDialog
    }

    override fun onCleared() {
        super.onCleared()
        trendsAnalyticsDelegate.cancel()
        monthTxsByCategoryDelegate.cancel()
    }
}