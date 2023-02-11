package com.finance_tracker.finance_tracker.presentation.analytics.txs_by_category_chart_block

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.common.LocalContext
import com.finance_tracker.finance_tracker.core.common.`if`
import com.finance_tracker.finance_tracker.core.common.toUIColor
import com.finance_tracker.finance_tracker.core.common.transparent
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.domain.models.TxsByCategoryChart
import com.finance_tracker.finance_tracker.presentation.common.formatters.Category
import com.finance_tracker.finance_tracker.presentation.common.formatters.format
import dev.icerock.moko.graphics.Color
import dev.icerock.moko.resources.compose.stringResource
import io.github.koalaplot.core.pie.DefaultSlice
import io.github.koalaplot.core.pie.PieChart
import io.github.koalaplot.core.util.ExperimentalKoalaPlotApi

private val CircleChartRange = 10f..90f

@Composable
internal fun CategoriesList(
    pieces: List<TxsByCategoryChart.Piece>,
    totalAmount: Double,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        pieces.forEach { piece ->
            CategoryItem(piece, totalAmount)
        }
    }
}

@Composable
internal fun CategoryItem(
    piece: TxsByCategoryChart.Piece,
    total: Double,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    Row(
        modifier = modifier
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CategoryPieChart(piece, total)

        Column(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            val category = piece.category ?: Category.empty(context)
            Text(
                text = category.name,
                style = CoinTheme.typography.body1
            )
            val transactionsCount = piece.transactionsCount
            Text(
                text = stringResource(MR.plurals.analytics_transactions, transactionsCount, transactionsCount),
                style = CoinTheme.typography.subtitle2,
                color = CoinTheme.color.secondary
            )
        }

        Text(
            text = piece.amount.format(),
            style = CoinTheme.typography.body1
        )
    }
}

@OptIn(ExperimentalKoalaPlotApi::class)
@Composable
private fun CategoryPieChart(
    piece: TxsByCategoryChart.Piece,
    total: Double,
    modifier: Modifier = Modifier
) {
    val percentage = remember { (piece.amount.amountValue / total * 100).toFloat() }
    val chartSize = 32.dp
    Box(
        modifier = modifier
            .size(chartSize),
        contentAlignment = Alignment.Center
    ) {
        val values = listOf(percentage, 100f - percentage)
        PieChart(
            modifier = Modifier.matchParentSize(),
            minPieDiameter = chartSize,
            maxPieDiameter = chartSize,
            values = values,
            slice = { index: Int ->
                DefaultSlice(
                    color = if (index == 0) {
                        piece.color
                    } else {
                        Color.transparent
                    }.toUIColor()
                )
            },
            holeSize = 0.77f,
            labelConnector = { Spacer(modifier = Modifier.size(0.dp)) },
            animationSpec = tween(durationMillis = 0)
        )

        PieChart(
            modifier = Modifier.matchParentSize(),
            minPieDiameter = chartSize,
            maxPieDiameter = chartSize,
            values = values,
            slice = { index: Int ->
                DefaultSlice(
                    color = if (index == 0) {
                        Color.transparent.toUIColor()
                    } else {
                        CoinTheme.color.dividers
                    }
                )
            },
            holeSize = 0.84f,
            labelConnector = { Spacer(modifier = Modifier.size(0.dp)) },
            animationSpec = tween(durationMillis = 0)
        )

        Text(
            modifier = Modifier
                .`if`(piece.percentage.toFloat() !in CircleChartRange) {
                    // Necessary because chart has oval form
                    padding(top = 2.dp)
                },
            text = piece.percentage,
            style = CoinTheme.typography.subtitle3,
            textAlign = TextAlign.Center
        )
    }
}
