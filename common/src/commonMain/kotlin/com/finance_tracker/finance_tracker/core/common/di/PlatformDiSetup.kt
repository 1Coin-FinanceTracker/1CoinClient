package com.finance_tracker.finance_tracker.core.common.di

import com.finance_tracker.finance_tracker.core.common.Context
import org.koin.core.KoinApplication

expect fun KoinApplication.platformKoinSetup(context: Context)