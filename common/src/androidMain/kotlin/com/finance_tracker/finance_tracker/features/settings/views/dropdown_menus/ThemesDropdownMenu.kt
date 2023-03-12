package com.finance_tracker.finance_tracker.features.settings.views.dropdown_menus

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.CoinDropdownMenu
import com.finance_tracker.finance_tracker.core.ui.DropdownMenuItem
import com.finance_tracker.finance_tracker.domain.models.ThemeMode
import dev.icerock.moko.resources.compose.stringResource

@Composable
internal fun ThemesDropdownMenu(
    items: List<ThemeMode>,
    expandedState: MutableState<Boolean>,
    modifier: Modifier = Modifier,
    onThemeSelect: (ThemeMode) -> Unit = {},
    xOffset: Dp = 0.dp,
    yOffset: Dp = 0.dp,
) {
    CoinDropdownMenu(
        modifier = modifier,
        expanded = expandedState.value,
        onDismissRequest = { expandedState.value = false },
        xOffset = xOffset,
        yOffset = yOffset,
    ) {
        items.forEach { item ->
            DropdownMenuItem(
                onClick = {
                    onThemeSelect.invoke(item)
                    expandedState.value = false
                }
            ) {
                Text(
                    text = stringResource(item.text),
                    style = CoinTheme.typography.body1,
                    color = CoinTheme.color.content
                )
            }
        }
    }
}