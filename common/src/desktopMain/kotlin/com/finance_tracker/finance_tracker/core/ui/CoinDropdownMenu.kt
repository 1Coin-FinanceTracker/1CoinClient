package com.finance_tracker.finance_tracker.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import androidx.compose.material.DropdownMenuItem as DesktopDropdownMenuItem

@Suppress("ReusedModifierInstance", "MissingModifierDefaultValue")
@Composable
actual fun CoinDropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier,
    xOffset: Dp,
    yOffset: Dp,
    content: @Composable ColumnScope.() -> Unit
) {
    MaterialTheme(shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(12.dp))) {
        DropdownMenu(
            modifier = modifier
                .background(CoinTheme.color.backgroundSurface),
            expanded = expanded,
            offset = DpOffset(x = xOffset, y = yOffset),
            onDismissRequest = onDismissRequest,
            content = content
        )
    }
}

@Composable
@Suppress("MissingModifierDefaultValue")
actual fun DropdownMenuItem(
    modifier: Modifier,
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit
) = DesktopDropdownMenuItem(
    modifier = modifier,
    onClick = onClick,
    content = content
)