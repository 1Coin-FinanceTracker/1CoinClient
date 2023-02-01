package com.finance_tracker.finance_tracker.presentation.settings.views.items

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.ui.rememberVectorPainter
import com.finance_tracker.finance_tracker.presentation.settings.views.ListItem
import dev.icerock.moko.resources.compose.stringResource

@Composable
internal fun SettingsMainCurrencyItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    ListItem(
        modifier = modifier,
        iconLeftPainter = rememberVectorPainter("ic_currency"),
        text = stringResource(MR.strings.settings_main_currency),
        onClick = onClick
    )
}