package com.finance_tracker.finance_tracker.core.common

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class FixedInsets(
    val statusBarHeight: Dp,
    val navigationBarsHeight: Dp,
)

val LocalFixedInsets = compositionLocalOf { FixedInsets(0.dp, 0.dp) }