package com.finance_tracker.finance_tracker.features.category_settings.di

import com.finance_tracker.finance_tracker.features.category_settings.CategorySettingsViewModel
import com.finance_tracker.finance_tracker.features.category_settings.analytcis.CategorySettingsAnalytics
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

internal val categorySettingsModule = module {
    factoryOf(::CategorySettingsViewModel)
    factoryOf(::CategorySettingsAnalytics)
}