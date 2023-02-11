package com.finance_tracker.finance_tracker.data.repositories

import com.finance_tracker.finance_tracker.core.common.Context
import com.finance_tracker.finance_tracker.core.common.getUniqueDeviceId
import com.finance_tracker.finance_tracker.data.settings.AnalyticsSettings
import com.finance_tracker.finance_tracker.data.settings.UserSettings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class UserRepository(
    private val userSettings: UserSettings,
    private val analyticsSettings: AnalyticsSettings,
    private val context: Context
) {

    suspend fun getOrCreateUserId(): String {
        return withContext(Dispatchers.Default) {
            val currentUserId = userSettings.getUserId()
            if (currentUserId != null) {
                return@withContext currentUserId
            }

            val newUserId = getUniqueDeviceId(context)
            userSettings.saveUserId(newUserId)
            newUserId
        }
    }

    suspend fun saveIsAnalyticsEnabled(enabled: Boolean) {
        withContext(Dispatchers.Default) {
            analyticsSettings.saveIsAnalyticsEnabled(enabled)
        }
    }

    fun isAnalyticsEnabledFlow(): Flow<Boolean> {
        return analyticsSettings.isAnalyticsEnabledFlow()
            .flowOn(Dispatchers.Default)
    }
}