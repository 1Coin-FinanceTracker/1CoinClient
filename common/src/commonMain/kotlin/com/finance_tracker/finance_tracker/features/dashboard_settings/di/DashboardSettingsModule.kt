package com.finance_tracker.finance_tracker.features.dashboard_settings.di

import com.finance_tracker.finance_tracker.features.dashboard_settings.DashboardSettingsViewModel
import com.finance_tracker.finance_tracker.features.dashboard_settings.analytics.DashboardSettingsAnalytics
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

internal val dashboardSettingsModule = module {
    factoryOf(::DashboardSettingsViewModel)
    factoryOf(::DashboardSettingsAnalytics)
}