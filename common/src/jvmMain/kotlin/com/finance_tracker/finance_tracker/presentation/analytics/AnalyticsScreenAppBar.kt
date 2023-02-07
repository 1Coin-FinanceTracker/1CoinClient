package com.finance_tracker.finance_tracker.presentation.analytics

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.CoinTxTypeTopAppBar
import com.finance_tracker.finance_tracker.core.ui.tab_rows.TransactionTypeTab
import dev.icerock.moko.resources.compose.stringResource

@Composable
internal fun AnalyticsScreenAppBar(
    selectedTransactionTypeTab: TransactionTypeTab,
    onTransactionTypeSelect: (TransactionTypeTab) -> Unit,
    modifier: Modifier = Modifier,
) {
    CoinTxTypeTopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = stringResource(MR.strings.tab_analytics),
                style = CoinTheme.typography.h4,
                color = CoinTheme.color.content
            )
        },
        selectedTransactionTypeTab = selectedTransactionTypeTab,
        onTransactionTypeSelect = onTransactionTypeSelect
    )
}