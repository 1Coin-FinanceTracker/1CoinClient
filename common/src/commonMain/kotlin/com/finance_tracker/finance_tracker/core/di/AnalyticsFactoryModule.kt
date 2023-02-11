package com.finance_tracker.finance_tracker.core.di

import org.koin.core.module.Module

expect class AnalyticsFactoryModule() {
    fun create(): Module
}

val AnalyticsFactoryModule.module: Module
    get() = create()