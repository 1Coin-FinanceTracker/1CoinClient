package com.finance_tracker.finance_tracker.features.select_currency

sealed interface SelectCurrencyAction {
    object Close: SelectCurrencyAction
}