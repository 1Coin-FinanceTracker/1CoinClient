package com.finance_tracker.finance_tracker.data.settings

import com.finance_tracker.finance_tracker.core.common.AppBuildConfig
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.Settings
import com.russhwolf.settings.coroutines.FlowSettings
import com.russhwolf.settings.coroutines.toFlowSettings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalSettingsApi::class)
class AnalyticsSettings(factory: Settings.Factory) {

    private val settings: ObservableSettings = factory.create("analytics") as ObservableSettings
    private val flowSettings: FlowSettings = settings.toFlowSettings(Dispatchers.Default)

    fun saveIsAnalyticsEnabled(enabled: Boolean) {
        settings.putBoolean(KEY_IS_ANALYTICS_ENABLED, enabled)
    }

    fun isAnalyticsEnabledFlow(): Flow<Boolean> {
        return flowSettings.getBooleanFlow(KEY_IS_ANALYTICS_ENABLED, AppBuildConfig.isReleaseBuild)
    }

    companion object {
        private const val KEY_IS_ANALYTICS_ENABLED = "is_enabled"
    }
}