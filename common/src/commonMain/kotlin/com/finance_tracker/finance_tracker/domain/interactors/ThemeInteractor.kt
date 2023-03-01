package com.finance_tracker.finance_tracker.domain.interactors

import com.finance_tracker.finance_tracker.data.repositories.ThemeRepository
import com.finance_tracker.finance_tracker.domain.models.ThemeMode

class ThemeInteractor(
    private val themeRepository: ThemeRepository
) {
    suspend fun setThemeMode(themeMode: ThemeMode) {
        themeRepository.setThemeMode(themeMode)
    }
}