package com.finance_tracker.finance_tracker.core.common

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.finance_tracker.finance_tracker.MR

actual val RubikFontFamily: FontFamily = FontFamily(
    Font(
        resId = MR.fonts.Rubik.regular.fontResourceId,
        weight = FontWeight.Normal
    ),
    Font(
        resId = MR.fonts.Rubik.medium.fontResourceId,
        weight = FontWeight.Medium
    )
)