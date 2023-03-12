package com.finance_tracker.finance_tracker.core.common

import android.app.Activity
import androidx.core.view.WindowCompat

actual fun Context.updateSystemBarsConfig(
    systemBarsConfig: SystemBarsConfig,
    isDarkTheme: Boolean
) {
    val isStatusBarLight = systemBarsConfig.isStatusBarLight
    val isNavigationBarLight = systemBarsConfig.isNavigationBarLight
    val window = (this as Activity).window
    WindowCompat.getInsetsController(window, window.decorView).apply {
        isAppearanceLightStatusBars = isStatusBarLight ?: !isDarkTheme
        isAppearanceLightNavigationBars = isNavigationBarLight ?: !isDarkTheme
    }
}
