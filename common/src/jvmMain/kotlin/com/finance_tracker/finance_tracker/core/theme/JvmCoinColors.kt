package com.finance_tracker.finance_tracker.core.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.finance_tracker.finance_tracker.core.common.toUIColor

@Immutable
data class JvmCoinColors(
    val primary: Color,
    val primaryVariant: Color,
    val secondary: Color,
    val background: Color,
    val backgroundSurface: Color,
    val secondaryBackground: Color,
    val dividers: Color,
    val content: Color,
    val accentGreen: Color,
    val accentRed: Color,
    val white: Color
)

fun CoinColors.toJvmColorPalette(): JvmCoinColors {
    return JvmCoinColors(
        primary = primary.toUIColor(),
        primaryVariant = primaryVariant.toUIColor(),
        secondary = secondary.toUIColor(),
        background = background.toUIColor(),
        backgroundSurface = backgroundSurface.toUIColor(),
        secondaryBackground = secondaryBackground.toUIColor(),
        dividers = dividers.toUIColor(),
        content = content.toUIColor(),
        accentGreen = accentGreen.toUIColor(),
        accentRed = accentRed.toUIColor(),
        white = white.toUIColor()
    )
}