package com.finance_tracker.finance_tracker.core.common

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

fun String?.hexToColor(defaultColor: Color = Color.Transparent): Color {
    if (this == null) return defaultColor

    return hexToColorOrNull() ?: defaultColor
}

fun String?.hexToColorOrNull(): Color? {
    if (this == null) return null
    return runSafeCatching { hexStringToColor() }.getOrNull()
}

@Suppress("MagicNumber")
private fun String.hexStringToColor(): Color {
    val colorString = removePrefix("#")
    val argb = colorString.toUInt(16)

    val b: Int = (argb and 0xFFu).toInt()
    val g: Int = (argb shr 8 and 0xFFu).toInt()
    val r: Int = (argb shr 16 and 0xFFu).toInt()
    val a: Int = (argb shr 24 and 0xFFu).toInt()

    return when (colorString.length) {
        6 -> Color(red = r, green = g, blue = b)
        8 -> Color(red = r, green = g, blue = b, alpha = a)
        else -> throw IllegalArgumentException("Unexpected hex color value in String: '$this'")
    }
}

@Suppress("MagicNumber")
fun Color.toHexString(): String {
    return "#${toArgb().toUInt().toString(16).takeLast(6)}"
}