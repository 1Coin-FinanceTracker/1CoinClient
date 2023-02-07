package com.finance_tracker.finance_tracker.presentation.settings.di

import com.finance_tracker.finance_tracker.presentation.settings.SettingsScreenViewModel
import com.finance_tracker.finance_tracker.presentation.settings.analytics.SettingsAnalytics
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

internal val settingsModule = module {
    factoryOf(::SettingsScreenViewModel)
    factoryOf(::SettingsAnalytics)
}