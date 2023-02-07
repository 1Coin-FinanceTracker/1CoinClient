package com.finance_tracker.finance_tracker.core.ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.finance_tracker.finance_tracker.core.theme.CoinTheme

@Composable
@Suppress("ReusedModifierInstance")
fun PrimaryButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    enabled: Boolean = true,
) {
    BaseButton(
        modifier = modifier,
        enabled = enabled,
        onClick = onClick
    ) {
        Text(
            text = text,
            style = CoinTheme.typography.body1_medium,
            textAlign = TextAlign.Center,
            color = CoinTheme.color.white
        )
    }
}
