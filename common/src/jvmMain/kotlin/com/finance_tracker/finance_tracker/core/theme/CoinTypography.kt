package com.finance_tracker.finance_tracker.core.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.finance_tracker.finance_tracker.core.common.RubikFontFamily

private val defaultFontFamily: FontFamily = RubikFontFamily

@Immutable
@Suppress("ConstructorParameterNaming")
data class CoinTypography(
    val h1: TextStyle = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 42.sp,
        fontFamily = defaultFontFamily
    ),
    val h2: TextStyle = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 32.sp,
        fontFamily = defaultFontFamily
    ),
    val h3: TextStyle = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        fontFamily = defaultFontFamily
    ),
    val h4: TextStyle = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        fontFamily = defaultFontFamily
    ),
    val h5: TextStyle = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        fontFamily = defaultFontFamily
    ),
    val body1: TextStyle = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        fontFamily = defaultFontFamily
    ),
    val body1_medium: TextStyle = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        fontFamily = defaultFontFamily
    ),
    val subtitle1: TextStyle = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 13.sp,
        fontFamily = defaultFontFamily
    ),
    val subtitle2: TextStyle = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        fontFamily = defaultFontFamily
    ),
    val subtitle2_medium: TextStyle = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        fontFamily = defaultFontFamily
    ),
    val subtitle3: TextStyle = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        fontFamily = defaultFontFamily
    ),
    val subtitle4: TextStyle = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp,
        fontFamily = defaultFontFamily
    )
)