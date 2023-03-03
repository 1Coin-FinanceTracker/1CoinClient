package com.finance_tracker.finance_tracker.features.analytics.txs_by_category_chart_block

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.LocalContext
import com.finance_tracker.finance_tracker.core.common.date.models.YearMonth
import com.finance_tracker.finance_tracker.core.common.toUIColor
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.rememberVectorPainter
import com.finance_tracker.finance_tracker.domain.models.Category
import com.finance_tracker.finance_tracker.domain.models.TxsByCategoryChart
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
internal fun MainTxsByCategoryChart(
    monthTransactionsByCategory: TxsByCategoryChart,
    selectedYearMonth: YearMonth,
    modifier: Modifier = Modifier
) {
    val totalAmount = monthTransactionsByCategory.total
    val txsByCategoryChartPieces = monthTransactionsByCategory.mainPieces
    val context = LocalContext.current

    ChartLayout(
        modifier = modifier
            .padding(
                start = 24.dp,
                end = 24.dp,
                bottom = 24.dp,
            )
    ) {
        val values by remember(txsByCategoryChartPieces) {
            derivedStateOf {
                txsByCategoryChartPieces.map { it.amount.amountValue.toFloat() }
            }
        }
        PieChart(
            modifier = Modifier.fillMaxWidth(),
            values = values,
            minPieDiameter = PieChartSize,
            maxPieDiameter = PieChartSize,
            slice = { index: Int ->
                val piece = txsByCategoryChartPieces.getOrNull(index)
                if (piece != null) {
                    DefaultSlice(
                        color = piece.color.toUIColor(),
                        hoverExpandFactor = 1.05f,
                        hoverElement = { Text(piece.amount.toString()) }
                    )
                }
            },
            label = { index ->
                val piece = txsByCategoryChartPieces.getOrNull(index)
                if (piece != null) {
                    val category = piece.category ?: Category.empty(context)
                    Icon(
                        modifier = Modifier
                            .size(PieChartLabelSize)
                            .clip(CircleShape)
                            .background(piece.color.toUIColor())
                            .padding(3.dp),
                        painter = rememberVectorPainter(category.iconId),
                        contentDescription = null,
                        tint = CoinTheme.color.white
                    )
                }
            },
            labelConnector = { index ->
                val piece = txsByCategoryChartPieces.getOrNull(index)
                if (piece != null) {
                    CoinBezierLabelConnector(
                        connectorColor = piece.color.toUIColor()
                    )
                }
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