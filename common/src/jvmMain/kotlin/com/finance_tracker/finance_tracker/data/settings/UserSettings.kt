package com.finance_tracker.finance_tracker.data.settings

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.Settings
import com.russhwolf.settings.coroutines.SuspendSettings
import com.russhwolf.settings.coroutines.toSuspendSettings
import kotlinx.coroutines.Dispatchers

@OptIn(ExperimentalSettingsApi::class)
class UserSettings(factory: Settings.Factory) {

    private val settings: Settings = factory.create("user")
    private val suspendSettings: SuspendSettings = settings.toSuspendSettings(Dispatchers.Default)

    suspend fun saveUserId(userId: String) {
        suspendSettings.putString(KEY_USER_ID, userId)
    }

    suspend fun getUserId(): String? {
        return suspendSettings.getStringOrNull(KEY_USER_ID)
    }

    companion object {
        private const val KEY_USER_ID = "user_id"
    }
}