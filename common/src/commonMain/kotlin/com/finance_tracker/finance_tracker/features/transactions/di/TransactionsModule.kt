package com.finance_tracker.finance_tracker.features.transactions.di

import com.finance_tracker.finance_tracker.features.transactions.TransactionsViewModel
import com.finance_tracker.finance_tracker.features.transactions.analytics.TransactionsAnalytics
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

internal val transactionsModule = module {
    factoryOf(::TransactionsViewModel)
    factoryOf(::TransactionsAnalytics)
}