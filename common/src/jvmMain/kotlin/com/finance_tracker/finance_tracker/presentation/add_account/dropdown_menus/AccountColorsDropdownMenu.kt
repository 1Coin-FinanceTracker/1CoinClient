package com.finance_tracker.finance_tracker.presentation.add_account.dropdown_menus

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.toDp
import com.finance_tracker.finance_tracker.core.common.toUIColor
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.CoinDropdownMenu
import com.finance_tracker.finance_tracker.domain.models.AccountColorModel
import dev.icerock.moko.resources.compose.stringResource

@Composable
internal fun AccountColorsDropdownMenu(
    items: List<AccountColorModel>,
    offsetXState: MutableState<Int>,
    expandedState: MutableState<Boolean>,
    modifier: Modifier = Modifier,
    onSelect: (AccountColorModel) -> Unit = {}
) {
    CoinDropdownMenu(
        modifier = modifier,
        expanded = expandedState.value,
        xOffset = offsetXState.value.toDp(),
        yOffset = 8.dp,
        onDismissRequest = { expandedState.value = false }
    ) {
        items.forEach { item ->
            AccountColorItem(
                data = item,
                onClick = {
                    onSelect.invoke(item)
                    expandedState.value = false
                }
            )
        }
    }
}

@Composable
private fun AccountColorItem(
    data: AccountColorModel,
    onClick: () -> Unit
) {
    DropdownMenuItem(onClick = onClick) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .border(
                        width = 2.dp,
                        color = data.color.toUIColor(),
                        shape = CircleShape
                    )
            )
            Text(
                modifier = Modifier
                    .padding(start = 16.dp),
                text = stringResource(data.colorName),
                style = CoinTheme.typography.body1,
                color = CoinTheme.color.content
            )
        }
    }
}