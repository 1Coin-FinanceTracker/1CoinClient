package com.finance_tracker.finance_tracker.features.settings.views

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.theme.CoinTheme

@Composable
internal fun ListItemDivider(
    modifier: Modifier = Modifier
) {
    Divider(
        modifier = modifier
            .padding(
                vertical = 12.dp,
                horizontal = 16.dp
            ),
        color = CoinTheme.color.dividers
    )
}