package com.finance_tracker.finance_tracker.domain.interactors

import com.finance_tracker.finance_tracker.data.repositories.ThemeRepository
import com.finance_tracker.finance_tracker.domain.models.ThemeMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ThemeInteractor(
    private val themeRepository: ThemeRepository
) {

    fun getThemeModeFlow(): Flow<ThemeMode> {
        return themeRepository.getThemeModeFlow()
            .map { it ?: ThemeMode.System }
    }

    suspend fun setThemeMode(themeMode: ThemeMode) {
        themeRepository.setThemeMode(themeMode)
    }
}