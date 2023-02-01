package com.finance_tracker.finance_tracker.presentation.add_account.dropdown_menus

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.CoinDropdownMenu
import com.finance_tracker.finance_tracker.core.ui.DropdownMenuItem
import com.finance_tracker.finance_tracker.domain.models.Currency

@Composable
internal fun AmountCurrenciesDropdownMenu(
    items: List<Currency>,
    expandedState: MutableState<Boolean>,
    modifier: Modifier = Modifier,
    onCurrencySelect: (Currency) -> Unit = {},
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
                    onCurrencySelect.invoke(item)
                    expandedState.value = false
                }
            ) {
                Text(
                    text = "${item.code} (${item.symbol})",
                    style = CoinTheme.typography.body1,
                    color = CoinTheme.color.content
                )
            }
        }
    }
}