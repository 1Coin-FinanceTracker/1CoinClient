package com.finance_tracker.finance_tracker.core.theme

import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Suppress("TopLevelComposableFunctions")
object CoinTextFieldDefaults {

    @Composable
    fun outlinedTextFieldColors(
        placeholderColor: Color = CoinTheme.color.content.copy(alpha = 0.2f),
        focusedLabelColor: Color = CoinTheme.color.primary,
        unfocusedLabelColor: Color = CoinTheme.color.content.copy(alpha = 0.5f),
        unfocusedBorderColor: Color = CoinTheme.color.dividers,
        textColor: Color = CoinTheme.color.content,
        disabledTextColor: Color = CoinTheme.color.content.copy(alpha = 0.5f)
    ): TextFieldColors {

        return TextFieldDefaults.outlinedTextFieldColors(
            cursorColor = CoinTheme.color.primary,
            focusedBorderColor = CoinTheme.color.primary,
            unfocusedBorderColor = unfocusedBorderColor,
            focusedLabelColor = focusedLabelColor,
            unfocusedLabelColor = unfocusedLabelColor,
            placeholderColor = placeholderColor,
            disabledBorderColor = unfocusedBorderColor,
            disabledPlaceholderColor = placeholderColor,
            disabledLabelColor = unfocusedLabelColor,
            textColor = textColor,
            disabledTextColor = disabledTextColor
        )
    }

    @Composable
    fun outlinedSelectTextFieldColors(
        borderColor: Color = CoinTheme.color.dividers,
        labelColor: Color = CoinTheme.color.content.copy(alpha = 0.5f),
        textColor: Color = CoinTheme.color.content
    ): TextFieldColors {
        return outlinedTextFieldColors(
            placeholderColor = borderColor,
            focusedLabelColor = labelColor,
            unfocusedLabelColor = labelColor,
            unfocusedBorderColor = borderColor,
            textColor = textColor,
            disabledTextColor = textColor
        )
    }
}