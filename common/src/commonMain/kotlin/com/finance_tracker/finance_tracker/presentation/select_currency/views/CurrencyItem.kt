package com.finance_tracker.finance_tracker.presentation.select_currency.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.CoinRadioButton
import com.finance_tracker.finance_tracker.domain.models.Currency

@Composable
internal fun CurrencyItem(
    currency: Currency,
    currencyCode: String,
    currencyName: String,
    modifier: Modifier = Modifier,
    onCurrencyClick: (Currency) -> Unit = {},
    isCurrencySelected: Boolean = false
) {
    Row(
        modifier = modifier
            .clickable { onCurrencyClick.invoke(currency) }
            .padding(
                vertical = 16.dp,
                horizontal = 16.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$currencyCode - $currencyName",
            style = CoinTheme.typography.body1
        )
        Spacer(modifier = Modifier.weight(1f))
        CoinRadioButton(
            active = isCurrencySelected
        )
    }
}