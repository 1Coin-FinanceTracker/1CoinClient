package com.finance_tracker.finance_tracker.presentation.settings.views.items

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.rememberVectorPainter
import com.finance_tracker.finance_tracker.domain.models.Currency
import com.finance_tracker.finance_tracker.presentation.settings.views.ListItem
import dev.icerock.moko.resources.compose.stringResource

@Composable
internal fun SettingsMainCurrencyItem(
    primaryCurrency: Currency,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    ListItem(
        modifier = modifier,
        iconLeftPainter = rememberVectorPainter("ic_currency"),
        text = stringResource(MR.strings.settings_main_currency),
        onClick = onClick,
        iconRight = {
            Text(
                modifier = Modifier.padding(end = 16.dp),
                text = "${primaryCurrency.code}(${primaryCurrency.symbol})",
                color = CoinTheme.color.primary,
                style = CoinTheme.typography.body1_medium
            )
        }
    )
}