package com.finance_tracker.finance_tracker.core.common

import android.app.Activity
import android.content.res.Configuration
import androidx.core.view.WindowCompat

actual fun Context.updateSystemBarsConfig(systemBarsConfig: SystemBarsConfig) {
    val isStatusBarLight = systemBarsConfig.isStatusBarLight
    val isNavigationBarLight = systemBarsConfig.isNavigationBarLight
    val window = (this as Activity).window
    val isDarkMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK ==
            Configuration.UI_MODE_NIGHT_YES
    WindowCompat.getInsetsController(window, window.decorView).apply {
        isAppearanceLightStatusBars = isStatusBarLight ?: !isDarkMode
        isAppearanceLightNavigationBars = isNavigationBarLight ?: !isDarkMode
    }
}
