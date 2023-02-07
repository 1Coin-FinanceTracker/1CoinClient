package com.finance_tracker.finance_tracker.core.theme

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.LocalContentColor
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.LocalContext
import com.finance_tracker.finance_tracker.core.common.LocalFixedInsets
import com.finance_tracker.finance_tracker.core.common.LocalSystemBarsConfig
import com.finance_tracker.finance_tracker.core.common.asSp
import com.finance_tracker.finance_tracker.core.common.getContext
import com.finance_tracker.finance_tracker.core.common.getFixedInsets
import com.finance_tracker.finance_tracker.core.common.updateSystemBarsConfig

@Immutable
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

val LocalCoinColors = staticCompositionLocalOf {
    CoinColors(
        primary = Color.Unspecified,
        primaryVariant = Color.Unspecified,
        secondary = Color.Unspecified,
        background = Color.Unspecified,
        backgroundSurface = Color.Unspecified,
        secondaryBackground = Color.Unspecified,
        dividers = Color.Unspecified,
        content = Color.Unspecified,
        accentGreen = Color.Unspecified,
        accentRed = Color.Unspecified,
        white = Color.White
    )
}
val LocalCoinTypography = staticCompositionLocalOf {
    CoinTypography()
}
val LocalCoinElevation = staticCompositionLocalOf {
    CoinElevation(
        default = 4.dp,
        pressed = 8.dp
    )
}

// Ripple

@Immutable
private object CoinRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = RippleTheme.defaultRippleColor(
        contentColor = LocalContentColor.current,
        lightTheme = true
    )

    @Composable
    override fun rippleAlpha() = DefaultRippleAlpha
}

private val DefaultRippleAlpha = RippleAlpha(
    pressedAlpha = 0.08f,
    focusedAlpha = 0.08f,
    draggedAlpha = 0.12f,
    hoveredAlpha = 0.06f
)

private val DarkColorPalette = CoinColors(
    primary = Color(0xFF1AA5FF),
    primaryVariant = Color(0xFF141414),
    secondary = Color(0xFFF2F2F2).copy(alpha = 0.4f),
    background = Color(0xFF1F1F1F),
    backgroundSurface = Color(0xFF262626),
    secondaryBackground = Color(0xFF262626),
    dividers = Color(0xFF3D3D3D),
    content = Color(0xFFF2F2F2),
    accentGreen = Color(0xFF00BC2D),
    accentRed = Color(0xFFF23030),
    white = Color(0xFFF2F2F2)
)

private val LightColorPalette = CoinColors(
    primary = Color(0xFF009BFF),
    primaryVariant = Color(0xFFFFFFFF),
    secondary = Color(0xFF000000).copy(alpha = 0.4f),
    background = Color(0xFFFFFFFF),
    backgroundSurface = Color(0xFFFFFFFF),
    secondaryBackground = Color(0xFFF7F7F7),
    dividers = Color(0xFFE6E6E6),
    content = Color(0xFF000000),
    accentGreen = Color(0xFF00BC2D),
    accentRed = Color(0xFFF20000),
    white = Color(0xFFFFFFFF)
)

object CoinAlpha {
    const val Medium = 0.2f
    const val Soft = 0.8f
}

@Composable
internal fun TextStyle.staticTextSize(isStaticContentSize: Boolean = true): TextStyle {
    return if (isStaticContentSize) copy(fontSize = fontSize.value.dp.asSp()) else this
}

@Composable
fun CoinTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val coinColors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    val context = getContext()
    context.updateSystemBarsConfig(LocalSystemBarsConfig.current)

    MaterialTheme(
        typography = MaterialTheme.typography.copy(
            subtitle1 = CoinTheme.typography.body1,
            caption = CoinTheme.typography.subtitle2
        ),
        colors = MaterialTheme.colors.copy(
            background = coinColors.background,
            onBackground = coinColors.content,
            surface = CoinTheme.color.backgroundSurface
        )
    ) {
        CompositionLocalProvider(
            LocalContext provides context,
            LocalCoinColors provides coinColors,
            LocalCoinTypography provides CoinTheme.typography,
            LocalCoinElevation provides CoinTheme.elevation,
            LocalContentColor provides CoinTheme.color.content,
            LocalIndication provides rememberRipple(),
            LocalRippleTheme provides CoinRippleTheme,
            LocalTextStyle provides CoinTheme.typography.body1,
            LocalFixedInsets provides getFixedInsets(),
            content = content
        )
    }
}

object CoinPaddings {
    val bottomNavigationBar = 96.dp
}

object CoinTheme {
    val color: CoinColors
        @Composable
        get() = LocalCoinColors.current
    val typography: CoinTypography
        @Composable
        get() = LocalCoinTypography.current
    val elevation: CoinElevation
        @Composable
        get() = LocalCoinElevation.current
}