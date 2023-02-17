package com.finance_tracker.finance_tracker.core.common

import androidx.compose.ui.text.TextRange as UiTextRange
import androidx.compose.ui.text.input.TextFieldValue as UiTextFieldValue

fun UiTextFieldValue.toTextFieldValue(): TextFieldValue {
    return TextFieldValue(
        text = text,
        selection = TextRange(
            start = selection.start,
            end = selection.end,
        ),
        composition = composition?.let {
            TextRange(
                start = it.start,
                end = it.end,
            )
        }
    )
}

fun TextFieldValue.toUiTextFieldValue(): UiTextFieldValue {
    return UiTextFieldValue(
        text = text,
        selection = UiTextRange(
            start = selection.start,
            end = selection.end,
        ),
        composition = composition?.let {
            UiTextRange(
                start = it.start,
                end = it.end,
            )
        }
    )
}