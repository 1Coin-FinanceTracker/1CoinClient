package com.finance_tracker.finance_tracker.features.add_transaction.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.finance_tracker.finance_tracker.core.common.clicks.scaleClickAnimation
import com.finance_tracker.finance_tracker.core.common.formatters.isCurrencyPositionAtStart
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.domain.models.Currency
import ru.alexgladkov.odyssey.compose.helpers.noRippleClickable

@Composable
internal fun AmountTextField(
    currency: Currency?,
    amount: String,
    active: Boolean,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    amountFontSize: TextUnit = 42.sp,
) {
    Column(
        modifier = modifier
            .background(CoinTheme.color.background)
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .scaleClickAnimation()
            .noRippleClickable { onClick.invoke() },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val currencySymbol = currency?.symbol
        Text(
            text = when {
                currencySymbol == null -> amount
                isCurrencyPositionAtStart() -> "$currencySymbol $amount"
                else -> "$amount $currencySymbol"
            },
            color = if (active) {
                CoinTheme.color.content
            } else {
                CoinTheme.color.secondary
            },
            style = CoinTheme.typography.h1,
            fontSize = amountFontSize
        )
        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = label,
            color = CoinTheme.color.secondary,
            style = CoinTheme.typography.subtitle2
        )
    }
}