package com.finance_tracker.finance_tracker.presentation.select_currency

import com.finance_tracker.finance_tracker.core.common.view_models.BaseViewModel
import com.finance_tracker.finance_tracker.domain.interactors.CurrenciesInteractor
import com.finance_tracker.finance_tracker.domain.models.Currency
import com.finance_tracker.finance_tracker.presentation.select_currency.analytics.SelectCurrencyAnalytics
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SelectCurrencyViewModel(
    private val currenciesInteractor: CurrenciesInteractor,
    private val selectCurrencyAnalytics: SelectCurrencyAnalytics,
): BaseViewModel<SelectCurrencyAction>() {

    private val _currencies = MutableStateFlow(Currency.list)
    val currencies = _currencies.asStateFlow()

    val primaryCurrencyFlow = currenciesInteractor.getPrimaryCurrencyFlow()
        .stateIn(viewModelScope, started = SharingStarted.Lazily, initialValue = Currency.default)

    fun onBackClick() {
        selectCurrencyAnalytics.trackBackClick()
        viewAction = SelectCurrencyAction.Close
    }

    fun onCurrencySelect(currency: Currency) {
        selectCurrencyAnalytics.trackCurrencySelect(currency)
        viewModelScope.launch {
            currenciesInteractor.savePrimaryCurrency(currency)
        }
    }

    fun onSearchClick() {
        selectCurrencyAnalytics.trackSearchClick()
    }
}