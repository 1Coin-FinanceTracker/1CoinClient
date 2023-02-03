package com.finance_tracker.finance_tracker.core.common

import android.annotation.SuppressLint
import android.provider.Settings

@SuppressLint("HardwareIds")
actual fun getUniqueDeviceId(context: Context): String {
    return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
}