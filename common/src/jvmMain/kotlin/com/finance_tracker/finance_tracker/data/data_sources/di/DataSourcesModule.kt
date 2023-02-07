package com.finance_tracker.finance_tracker.data.data_sources.di

import com.finance_tracker.finance_tracker.data.data_sources.TransactionsPagingSource
import com.finance_tracker.finance_tracker.data.data_sources.TransactionsPagingSourceFactory
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

internal val dataSourcesModule = module {
    factoryOf(::TransactionsPagingSource)
    factoryOf(::TransactionsPagingSourceFactory)
}