package com.finance_tracker.finance_tracker.presentation.analytics.txs_by_category_chart_block

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.date.models.YearMonth
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.rememberVectorPainter
import com.finance_tracker.finance_tracker.domain.models.TxsByCategoryChart
import com.finance_tracker.finance_tracker.presentation.analytics.PieChartLabelSize
import com.finance_tracker.finance_tracker.presentation.analytics.PieChartSize
import com.finance_tracker.finance_tracker.presentation.analytics.views.CoinBezierLabelConnector
import io.github.koalaplot.core.ChartLayout
import io.github.koalaplot.core.pie.DefaultSlice
import io.github.koalaplot.core.pie.PieChart
import io.github.koalaplot.core.util.ExperimentalKoalaPlotApi

@Suppress("MagicNumber")
@OptIn(ExperimentalKoalaPlotApi::class)
@Composable
fun MainTxsByCategoryChart(
    monthTransactionsByCategory: TxsByCategoryChart,
    selectedYearMonth: YearMonth,
    modifier: Modifier = Modifier
) {
    val totalAmount = monthTransactionsByCategory.total
    val txsByCategoryChartPieces = monthTransactionsByCategory.mainPieces

    ChartLayout(
        modifier = modifier
            .padding(
                start = 24.dp,
                end = 24.dp,
                bottom = 24.dp,
            )
    ) {
        PieChart(
            modifier = Modifier.fillMaxWidth(),
            values = txsByCategoryChartPieces.map { it.amount.amountValue.toFloat() },
            minPieDiameter = PieChartSize,
            maxPieDiameter = PieChartSize,
            slice = { i: Int ->
                DefaultSlice(
                    color = txsByCategoryChartPieces[i].color,
                    hoverExpandFactor = 1.05f,
                    hoverElement = { Text(txsByCategoryChartPieces[i].amount.toString()) }
                )
            },
            label = { index ->
                val chartPiece = txsByCategoryChartPieces[index]
                Icon(
                    modifier = Modifier
                        .size(PieChartLabelSize)
                        .clip(CircleShape)
                        .background(chartPiece.color)
                        .padding(3.dp),
                    painter = rememberVectorPainter(
                        chartPiece.category.iconId
                    ),
                    contentDescription = null,
                    tint = CoinTheme.color.primaryVariant
                )
            },
            labelConnector = { index ->
                CoinBezierLabelConnector(
                    connectorColor = txsByCategoryChartPieces[index].color
                )
            },
            holeSize = 0.8f,
            holeContent = {
                HoleTotalLabel(
                    data = HoleTotalLabelData.Content(
                        yearMonth = selectedYearMonth,
                        amount = totalAmount
                    )
                )
            },
            labelSpacing = 1.1f
        )
    }
}