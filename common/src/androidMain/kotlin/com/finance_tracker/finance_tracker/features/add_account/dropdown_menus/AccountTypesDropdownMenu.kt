package com.finance_tracker.finance_tracker.features.add_account.dropdown_menus

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.CoinDropdownMenu
import com.finance_tracker.finance_tracker.core.ui.DropdownMenuItem
import com.finance_tracker.finance_tracker.domain.models.Account
import dev.icerock.moko.resources.compose.stringResource

@Composable
internal fun AccountTypesDropdownMenu(
    items: List<Account.Type>,
    expandedState: MutableState<Boolean>,
    modifier: Modifier = Modifier,
    onSelect: (Account.Type) -> Unit = {}
) {
    CoinDropdownMenu(
        modifier = modifier,
        expanded = expandedState.value,
        xOffset = 0.dp,
        yOffset = 8.dp,
        onDismissRequest = { expandedState.value = false }
    ) {
        items.forEach { item ->
            DropdownMenuItem(
                onClick = {
                    onSelect.invoke(item)
                    expandedState.value = false
                }
            ) {
                Text(
                    text = stringResource(item.textId),
                    style = CoinTheme.typography.body1,
                    color = CoinTheme.color.content
                )
            }
        }
    }
}