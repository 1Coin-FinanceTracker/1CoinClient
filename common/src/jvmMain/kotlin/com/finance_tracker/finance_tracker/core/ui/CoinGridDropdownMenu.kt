package com.finance_tracker.finance_tracker.core.ui

import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
expect fun CoinGridDropdownMenu(
    columnSize: Dp,
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    xOffset: Dp = 0.dp,
    yOffset: Dp = 8.dp,
    content: LazyGridScope.() -> Unit = {}
)