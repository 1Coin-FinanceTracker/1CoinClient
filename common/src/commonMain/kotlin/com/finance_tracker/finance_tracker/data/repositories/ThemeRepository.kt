package com.finance_tracker.finance_tracker.data.repositories

import com.finance_tracker.finance_tracker.data.settings.ThemeSettings
import com.finance_tracker.finance_tracker.domain.models.ThemeMode

class ThemeRepository(
    private val themeSettings: ThemeSettings
) {
    suspend fun setThemeMode(themeMode: ThemeMode) {
        themeSettings.saveThemeMode(themeMode)
    }
}