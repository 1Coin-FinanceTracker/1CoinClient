package com.finance_tracker.finance_tracker.features.add_account.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.rememberVectorPainter
import com.finance_tracker.finance_tracker.domain.models.Currency
import com.finance_tracker.finance_tracker.features.add_account.dropdown_menus.AmountCurrenciesDropdownMenu

@Composable
internal fun CurrencySelector(
    items: List<Currency>,
    selectedCurrency: Currency,
    modifier: Modifier = Modifier,
    onCurrencySelect: (Currency) -> Unit = {},
    onCurrencyClick: (Currency) -> Unit = {},
    xOffset: Dp = 0.dp,
    yOffset: Dp = 0.dp,
) {
    val currencyMenuExpanded = remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable {
                focusManager.clearFocus()
                currencyMenuExpanded.value = true
                onCurrencyClick.invoke(selectedCurrency)
            }
            .padding(
                start = 14.dp,
                end = 2.dp,
                top = 4.dp,
                bottom = 4.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "${selectedCurrency.code}(${selectedCurrency.symbol})",
            color = CoinTheme.color.primary,
            style = CoinTheme.typography.body1_medium
        )
        Icon(
            modifier = Modifier
                .size(24.dp),
            painter = rememberVectorPainter("ic_expand_more_small"),
            contentDescription = null,
            tint = CoinTheme.color.primary
        )
    }

    AmountCurrenciesDropdownMenu(
        items = items,
        expandedState = currencyMenuExpanded,
        onCurrencySelect = onCurrencySelect,
        xOffset = xOffset,
        yOffset = yOffset,
    )
}