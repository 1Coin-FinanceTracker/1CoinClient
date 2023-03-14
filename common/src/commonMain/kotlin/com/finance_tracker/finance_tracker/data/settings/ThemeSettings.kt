package com.finance_tracker.finance_tracker.data.settings

import com.finance_tracker.finance_tracker.core.common.stateIn
import com.finance_tracker.finance_tracker.domain.models.ThemeMode
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.Settings
import com.russhwolf.settings.coroutines.FlowSettings
import com.russhwolf.settings.coroutines.toFlowSettings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalSettingsApi::class)
class ThemeSettings(factory: Settings.Factory) {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    private val settings: ObservableSettings = factory.create("theme") as ObservableSettings
    private val flowSettings: FlowSettings = settings.toFlowSettings(Dispatchers.Default)
    val themeMode = getThemeMode().stateIn(coroutineScope, initialValue = null)

    fun saveThemeMode(themeMode: ThemeMode) {
        settings.putString(KEY_THEME_MODE, themeMode.modeId)
    }

    fun getThemeMode(): Flow<ThemeMode?> {
        return flowSettings.getStringOrNullFlow(KEY_THEME_MODE)
            .map(ThemeMode::from)
    }

    companion object {
        private const val KEY_THEME_MODE = "theme_mode"
    }
}