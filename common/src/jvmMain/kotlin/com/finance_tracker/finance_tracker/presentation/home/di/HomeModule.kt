package com.finance_tracker.finance_tracker.presentation.home.di

import com.finance_tracker.finance_tracker.presentation.home.HomeViewModel
import com.finance_tracker.finance_tracker.presentation.home.analytics.HomeAnalytics
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

internal val homeModule = module {
    factoryOf(::HomeViewModel)
    factoryOf(::HomeAnalytics)
}