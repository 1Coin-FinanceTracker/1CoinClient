package com.finance_tracker.finance_tracker.data.repositories

import com.finance_tracker.finance_tracker.data.settings.ThemeSettings
import com.finance_tracker.finance_tracker.domain.models.ThemeMode
import kotlinx.coroutines.flow.StateFlow

class ThemeRepository(
    private val themeSettings: ThemeSettings
) {

    fun getThemeModeFlow(): StateFlow<ThemeMode?> {
        return themeSettings.themeMode
    }

    suspend fun setThemeMode(themeMode: ThemeMode) {
        themeSettings.saveThemeMode(themeMode)
    }
}