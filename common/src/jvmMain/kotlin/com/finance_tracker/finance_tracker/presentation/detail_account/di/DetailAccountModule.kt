package com.finance_tracker.finance_tracker.presentation.detail_account.di

import com.finance_tracker.finance_tracker.presentation.detail_account.DetailAccountViewModel
import com.finance_tracker.finance_tracker.presentation.detail_account.analytics.DetailAccountAnalytics
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

internal val detailAccountModule = module {
    factoryOf(::DetailAccountViewModel)
    factoryOf(::DetailAccountAnalytics)
}