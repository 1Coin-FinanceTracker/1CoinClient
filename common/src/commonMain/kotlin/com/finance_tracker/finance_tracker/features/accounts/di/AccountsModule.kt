package com.finance_tracker.finance_tracker.features.accounts.di

import com.finance_tracker.finance_tracker.features.accounts.AccountsViewModel
import com.finance_tracker.finance_tracker.features.accounts.analytics.AccountsAnalytics
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

internal val accountsModule = module {
    factoryOf(::AccountsViewModel)
    factoryOf(::AccountsAnalytics)
}