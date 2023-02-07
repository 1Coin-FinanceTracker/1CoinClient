package com.finance_tracker.finance_tracker.domain.interactors

import com.finance_tracker.finance_tracker.data.repositories.UserRepository
import kotlinx.coroutines.flow.Flow

class UserInteractor(
    private val userRepository: UserRepository
) {

    suspend fun getOrCreateUserId(): String {
        return userRepository.getOrCreateUserId()
    }

    suspend fun saveIsAnalyticsEnabled(enabled: Boolean) {
        userRepository.saveIsAnalyticsEnabled(enabled)
    }

    fun isAnalyticsEnabledFlow(): Flow<Boolean> {
        return userRepository.isAnalyticsEnabledFlow()
    }
}