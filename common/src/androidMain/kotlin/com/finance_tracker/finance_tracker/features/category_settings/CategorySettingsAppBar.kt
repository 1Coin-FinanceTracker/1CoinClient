package com.finance_tracker.finance_tracker.features.category_settings

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.AppBarIcon
import com.finance_tracker.finance_tracker.core.ui.CoinTxTypeTopAppBar
import com.finance_tracker.finance_tracker.core.ui.rememberVectorPainter
import com.finance_tracker.finance_tracker.core.ui.tab_rows.TransactionTypeTab
import dev.icerock.moko.resources.compose.stringResource

@Composable
internal fun CategorySettingsAppBar(
    selectedTransactionTypeTab: TransactionTypeTab,
    onTransactionTypeSelect: (TransactionTypeTab) -> Unit,
    onAddCategoryClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    CoinTxTypeTopAppBar(
        modifier = modifier,
        navigationIcon = {
            AppBarIcon(
                painter = rememberVectorPainter("ic_arrow_back"),
                onClick = onBackClick,
            )
        },
        title = {
            Text(
                text = stringResource(MR.strings.category_settings),
                style = CoinTheme.typography.h4,
                color = CoinTheme.color.content
            )
        },
        actions = {
            AppBarIcon(
                rememberVectorPainter("ic_plus"),
                onClick = onAddCategoryClick,
                tint = CoinTheme.color.primary,
            )
        },
        selectedTransactionTypeTab = selectedTransactionTypeTab,
        onTransactionTypeSelect = onTransactionTypeSelect
    )
}