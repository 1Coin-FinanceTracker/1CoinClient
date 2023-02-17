package com.finance_tracker.finance_tracker.data.database.di

import com.finance_tracker.finance_tracker.data.database.DriverFactory
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual class DriverFactoryModule {
    actual fun create(): Module = module {
        singleOf(::DriverFactory)
    }
}