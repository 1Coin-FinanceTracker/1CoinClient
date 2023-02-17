package com.finance_tracker.finance_tracker.core.common.di

import com.finance_tracker.finance_tracker.core.common.Context
import com.finance_tracker.finance_tracker.core.common.EmptyContext
import org.koin.core.KoinApplication
import org.koin.dsl.binds
import org.koin.dsl.module

actual fun KoinApplication.platformKoinSetup(context: Context) {
    koin.loadModules(listOf(module {
        single { EmptyContext } binds arrayOf(Context::class)
    }))
}