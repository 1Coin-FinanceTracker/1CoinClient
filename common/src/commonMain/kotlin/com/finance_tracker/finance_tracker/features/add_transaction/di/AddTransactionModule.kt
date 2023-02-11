package com.finance_tracker.finance_tracker.features.add_transaction.di

import com.finance_tracker.finance_tracker.features.add_transaction.AddTransactionViewModel
import com.finance_tracker.finance_tracker.features.add_transaction.analytics.AddTransactionAnalytics
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

internal val addTransactionModule = module {
    factoryOf(::AddTransactionViewModel)
    factoryOf(::AddTransactionAnalytics)
}