package com.finance_tracker.finance_tracker.data.database.di

import org.koin.core.module.Module

expect class DriverFactoryModule() {
    fun create(): Module
}

val DriverFactoryModule.module: Module
    get() = create()