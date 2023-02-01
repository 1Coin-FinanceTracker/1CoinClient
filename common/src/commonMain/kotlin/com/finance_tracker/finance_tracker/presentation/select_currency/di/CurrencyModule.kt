package com.finance_tracker.finance_tracker.presentation.select_currency.di

import com.finance_tracker.finance_tracker.presentation.select_currency.SelectCurrencyViewModel
import com.finance_tracker.finance_tracker.presentation.select_currency.analytics.SelectCurrencyAnalytics
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

internal val currencyModule = module {
    factoryOf(::SelectCurrencyViewModel)
    factoryOf(::SelectCurrencyAnalytics)
}