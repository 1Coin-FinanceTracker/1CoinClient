package com.finance_tracker.finance_tracker.data.repositories

import com.finance_tracker.finance_tracker.data.settings.ThemeSettings
import com.finance_tracker.finance_tracker.domain.models.ThemeMode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class ThemeRepository(
    private val themeSettings: ThemeSettings
) {

    fun getThemeModeFlow(): Flow<ThemeMode?> {
        return themeSettings.getThemeMode()
    }

    suspend fun setThemeMode(themeMode: ThemeMode) {
        withContext(Dispatchers.Default) {
            themeSettings.saveThemeMode(themeMode)
        }
    }
}