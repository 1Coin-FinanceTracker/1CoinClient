package com.finance_tracker.finance_tracker.core.theme

import dev.icerock.moko.graphics.Color

data class CoinColors(
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

object ColorPalette {

    val Undefined = CoinColors(
        primary = Color(0),
        primaryVariant = Color(0),
        secondary = Color(0),
        background = Color(0),
        backgroundSurface = Color(0),
        secondaryBackground = Color(0),
        dividers = Color(0),
        content = Color(0),
        accentGreen = Color(0),
        accentRed = Color(0),
        white = Color(0xFFFFFFFF)
    )

    fun provideColorPalette(isDarkMode: Boolean): CoinColors {
        return if (isDarkMode) {
            DarkColorPalette
        } else {
            LightColorPalette
        }
    }
}

private val DarkColorPalette = CoinColors(
    primary = Color(0x1AA5FFFF),
    primaryVariant = Color(0x141414FF),
    secondary = Color(0xF2F2F2FF).copy(alpha = (0.4 * 255).toInt()),
    background = Color(0x1F1F1FFF),
    backgroundSurface = Color(0x262626FF),
    secondaryBackground = Color(0x262626FF),
    dividers = Color(0x3D3D3DFF),
    content = Color(0xF2F2F2FF),
    accentGreen = Color(0x00BC2DFF),
    accentRed = Color(0xF23030FF),
    white = Color(0xF2F2F2FF)
)

private val LightColorPalette = CoinColors(
    primary = Color(0x009BFFFF),
    primaryVariant = Color(0xFFFFFFFF),
    secondary = Color(0x000000FF).copy(alpha = (0.4 * 255).toInt()),
    background = Color(0xFFFFFFFF),
    backgroundSurface = Color(0xFFFFFFFF),
    secondaryBackground = Color(0xF7F7F7FF),
    dividers = Color(0xE6E6E6FF),
    content = Color(0x000000FF),
    accentGreen = Color(0x00BC2DFF),
    accentRed = Color(0xF20000FF),
    white = Color(0xFFFFFFFF)
)