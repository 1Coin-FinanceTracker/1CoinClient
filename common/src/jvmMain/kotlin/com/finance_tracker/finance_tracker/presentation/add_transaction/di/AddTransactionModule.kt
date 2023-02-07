package com.finance_tracker.finance_tracker.presentation.add_transaction.di

import com.finance_tracker.finance_tracker.presentation.add_transaction.AddTransactionViewModel
import com.finance_tracker.finance_tracker.presentation.add_transaction.analytics.AddTransactionAnalytics
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

internal val addTransactionModule = module {
    factoryOf(::AddTransactionViewModel)
    factoryOf(::AddTransactionAnalytics)
}