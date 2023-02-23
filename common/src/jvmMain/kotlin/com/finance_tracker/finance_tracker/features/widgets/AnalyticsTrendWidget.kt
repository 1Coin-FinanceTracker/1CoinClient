package com.finance_tracker.finance_tracker.features.widgets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.common.LocalContext
import com.finance_tracker.finance_tracker.core.common.formatters.AmountFormatMode
import com.finance_tracker.finance_tracker.core.common.formatters.format
import com.finance_tracker.finance_tracker.core.ui.CoinWidget
import com.finance_tracker.finance_tracker.core.ui.tab_rows.TransactionTypeTab
import com.finance_tracker.finance_tracker.features.analytics.delegates.TrendsAnalyticsDelegate
import com.finance_tracker.finance_tracker.features.analytics.models.TrendBarDetails
import com.finance_tracker.finance_tracker.features.analytics.peroid_bar_chart.CoinBarChartEntry
import com.finance_tracker.finance_tracker.features.analytics.peroid_bar_chart.PeriodBarChart
import dev.icerock.moko.resources.compose.stringResource

@Composable
internal fun AnalyticsTrendWidget(
    trendsAnalyticsDelegate: TrendsAnalyticsDelegate,
    transactionTypeTab: TransactionTypeTab,
    modifier: Modifier = Modifier
) {
    CoinWidget(
        modifier = modifier,
        title = when (transactionTypeTab) {
            TransactionTypeTab.Expense -> {
                stringResource(MR.strings.expense_analytics_trend)
            }
            TransactionTypeTab.Income -> {
                stringResource(MR.strings.income_analytics_trend)
            }
            TransactionTypeTab.Transfer -> {
                ""
            }
        },
        withBorder = true
    ) {
        val selectedPeriodChip by trendsAnalyticsDelegate.selectedPeriodChipFlow.collectAsState()
        val trend by trendsAnalyticsDelegate.trendFlow.collectAsState()
        val total by trendsAnalyticsDelegate.totalFlow.collectAsState()

        PeriodBarChart(
            barChartEntries = trend.mapToBarChartEntities(),
            defaultTitle = stringResource(MR.strings.total),
            defaultValue = total.format(mode = AmountFormatMode.NoSigns),
            selectedPeriodChip = selectedPeriodChip,
            onChipSelect = { trendsAnalyticsDelegate.setPeriodChip(it) }
        )
    }
}

@Composable
private fun List<TrendBarDetails>.mapToBarChartEntities(): List<CoinBarChartEntry<Float, Float>> {
    val context = LocalContext.current
    return mapIndexed { index, trendBarDetails ->
        val (amount, format) = trendBarDetails.provideAmountWithFormat()
        CoinBarChartEntry(
            xValue = (index + 1).toFloat(),
            yMin = 0f,
            yMax = trendBarDetails.value.toFloat(),
            overviewTitle = trendBarDetails.title(context),
            overviewValue = amount.format(format)
        )
    }
}