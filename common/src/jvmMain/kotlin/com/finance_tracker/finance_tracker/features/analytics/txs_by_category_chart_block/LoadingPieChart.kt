package com.finance_tracker.finance_tracker.features.analytics.txs_by_category_chart_block

import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.date.models.YearMonth
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.features.analytics.PieChartLabelSize
import com.finance_tracker.finance_tracker.features.analytics.PieChartSize
import com.finance_tracker.finance_tracker.features.analytics.views.CoinBezierLabelConnector
import io.github.koalaplot.core.ChartLayout
import io.github.koalaplot.core.pie.DefaultSlice
import io.github.koalaplot.core.pie.PieChart
import io.github.koalaplot.core.util.ExperimentalKoalaPlotApi


@Suppress("MagicNumber")
@OptIn(ExperimentalKoalaPlotApi::class)
@Composable
internal fun LoadingPieChart(
    selectedYearMonth: YearMonth,
    modifier: Modifier = Modifier
) {
    val mockValues = listOf(30f, 20f, 10f, 10f, 10f, 10f, 10f)
    val loadingColor = CoinTheme.color.secondaryBackground

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
            values = mockValues,
            minPieDiameter = PieChartSize,
            maxPieDiameter = PieChartSize,
            slice = {
                DefaultSlice(color = loadingColor)
            },
            label = {
                Box(
                    modifier = Modifier
                        .size(PieChartLabelSize)
                        .clip(CircleShape)
                        .background(loadingColor)
                )
            },
            labelConnector = {
                CoinBezierLabelConnector(connectorColor = loadingColor)
            },
            holeSize = 0.8f,
            holeContent = {
                HoleTotalLabel(
                    data = HoleTotalLabelData.Loading(
                        yearMonth = selectedYearMonth,
                    )
                )
            },
            labelSpacing = 1.1f,
            animationSpec = tween(durationMillis = 0)
        )
    }
}