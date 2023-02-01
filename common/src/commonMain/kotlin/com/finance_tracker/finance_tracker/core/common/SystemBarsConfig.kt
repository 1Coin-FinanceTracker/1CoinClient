package com.finance_tracker.finance_tracker.core.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

class SystemBarsConfig(
    initIsStatusBarLight: Boolean? = null,
    initIsNavigationBarLight: Boolean? = null
) {
    var isStatusBarLight: Boolean? by mutableStateOf(initIsStatusBarLight)
    var isNavigationBarLight: Boolean? by mutableStateOf(initIsNavigationBarLight)

    fun copy() = SystemBarsConfig(
        initIsStatusBarLight = isStatusBarLight,
        initIsNavigationBarLight = isNavigationBarLight
    )
}

@Composable
internal fun UpdateSystemBarsConfigEffect(action: SystemBarsConfig.() -> Unit) {
    val currentSystemBarsConfig = LocalSystemBarsConfig.current
    val oldSystemBarsConfig = remember { currentSystemBarsConfig.copy() }
    DisposableEffect(Unit) {
        action.invoke(currentSystemBarsConfig)
        onDispose {
            currentSystemBarsConfig.isStatusBarLight = oldSystemBarsConfig.isStatusBarLight
        }
    }
}

val LocalSystemBarsConfig = compositionLocalOf { SystemBarsConfig() }