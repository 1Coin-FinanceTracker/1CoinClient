package com.finance_tracker.finance_tracker.features.analytics.txs_by_category_chart_block

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.date.models.YearMonth
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.domain.models.Amount
import com.finance_tracker.finance_tracker.domain.models.Currency
import com.finance_tracker.finance_tracker.features.analytics.PieChartSize
import io.github.koalaplot.core.ChartLayout
import io.github.koalaplot.core.pie.DefaultSlice
import io.github.koalaplot.core.pie.PieChart
import io.github.koalaplot.core.util.ExperimentalKoalaPlotApi

@OptIn(ExperimentalKoalaPlotApi::class)
@Composable
internal fun EmptyPieChart(
    primaryCurrency: Currency,
    selectedYearMonth: YearMonth,
    modifier: Modifier = Modifier
) {
    ChartLayout(
        modifier = modifier.padding(
            start = 24.dp,
            end = 24.dp,
            bottom = 24.dp,
        )
    ) {
        PieChart(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            values = listOf(1f),
            minPieDiameter = PieChartSize,
            maxPieDiameter = PieChartSize,
            slice = {
                DefaultSlice(color = CoinTheme.color.secondaryBackground)
            },
            labelConnector = {  },
            holeSize = 0.8f,
            holeContent = {
                HoleTotalLabel(
                    data = HoleTotalLabelData.Content(
                        yearMonth = selectedYearMonth,
                        amount = Amount(
                            currency = primaryCurrency,
                            amountValue = 0.0
                        )
                    )
                )
            },
            animationSpec = tween(durationMillis = 0)
        )
    }
}