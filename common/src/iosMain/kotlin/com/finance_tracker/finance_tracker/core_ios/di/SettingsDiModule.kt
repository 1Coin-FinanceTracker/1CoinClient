package com.finance_tracker.finance_tracker.core_ios.di

import com.finance_tracker.finance_tracker.data.settings.AccountSettings
import com.finance_tracker.finance_tracker.data.settings.AnalyticsSettings
import com.finance_tracker.finance_tracker.data.settings.UserSettings
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class SettingsDiModule : KoinComponent {
    fun accountSettings() = get<AccountSettings>()
    fun analyticsSettings() = get<AnalyticsSettings>()
    fun userSettings() = get<UserSettings>()
}