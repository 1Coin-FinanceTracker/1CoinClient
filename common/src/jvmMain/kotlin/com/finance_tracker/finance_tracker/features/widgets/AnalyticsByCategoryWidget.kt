package com.finance_tracker.finance_tracker.features.widgets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.ui.CoinWidget
import com.finance_tracker.finance_tracker.core.ui.tab_rows.TransactionTypeTab
import com.finance_tracker.finance_tracker.domain.models.Currency
import com.finance_tracker.finance_tracker.features.analytics.delegates.MonthTxsByCategoryDelegate
import com.finance_tracker.finance_tracker.features.analytics.txs_by_category_chart_block.TxsByCategoryChartBlock
import dev.icerock.moko.resources.compose.stringResource

@Composable
internal fun AnalyticsByCategoryWidget(
    primaryCurrency: Currency,
    monthTxsByCategoryDelegate: MonthTxsByCategoryDelegate,
    selectedTransactionTypeTab: TransactionTypeTab,
    modifier: Modifier = Modifier,
) {
    CoinWidget(
        modifier = modifier,
        title = stringResource(
            MR.strings.analytics_by_category,
            stringResource(selectedTransactionTypeTab.textId)
        ),
        withBorder = true
    ) {
        val isLoading by monthTxsByCategoryDelegate
            .isLoadingMonthTxsByCategory.collectAsState()

        val selectedMonth by monthTxsByCategoryDelegate
            .selectedMonth.collectAsState()

        val monthTransactionsByCategory by monthTxsByCategoryDelegate
            .monthTransactionsByCategory.collectAsState()

        TxsByCategoryChartBlock(
            primaryCurrency = primaryCurrency,
            isLoading = isLoading,
            selectedYearMonth = selectedMonth,
            monthTransactionsByCategory = monthTransactionsByCategory,
            onYearMonthSelect = monthTxsByCategoryDelegate::onMonthSelect
        )
    }
}