package com.finance_tracker.finance_tracker.features.settings.views

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.theme.CoinTheme

@Composable
internal fun ListGroupHeader(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier
            .padding(
                vertical = 12.dp,
                horizontal = 16.dp,
            ),
        text = text,
        style = CoinTheme.typography.subtitle2_medium,
        color = CoinTheme.color.content.copy(alpha = 0.3f)
    )
}