package com.finance_tracker.finance_tracker.presentation.accounts.di

import com.finance_tracker.finance_tracker.presentation.accounts.AccountsViewModel
import com.finance_tracker.finance_tracker.presentation.accounts.analytics.AccountsAnalytics
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

internal val accountsModule = module {
    factoryOf(::AccountsViewModel)
    factoryOf(::AccountsAnalytics)
}