package com.finance_tracker.finance_tracker.data.settings.di

import com.finance_tracker.finance_tracker.data.settings.AccountSettings
import com.finance_tracker.finance_tracker.data.settings.AnalyticsSettings
import com.finance_tracker.finance_tracker.data.settings.ThemeSettings
import com.finance_tracker.finance_tracker.data.settings.UserSettings
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val settingsCacheModule = module {
    singleOf(::UserSettings)
    singleOf(::AccountSettings)
    singleOf(::AnalyticsSettings)
    singleOf(::ThemeSettings)
}