package com.finance_tracker.finance_tracker.core.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit

@Composable
internal fun Dp.toPx(): Float {
    return LocalDensity.current.run { this@toPx.toPx() }
}

@Composable
internal fun Int.toDp(): Dp {
    return LocalDensity.current.run { this@toDp.toDp() }
}

@Composable
internal fun dpToSp(dp: Dp) = with(LocalDensity.current) { dp.toSp() }

@Composable
internal fun Dp.asSp() = with(LocalDensity.current) { toSp() }

@Composable
internal fun spToDp(sp: TextUnit) = with(LocalDensity.current) { sp.toDp() }

@Composable
internal fun TextUnit.asDp() = with(LocalDensity.current) { toDp() }