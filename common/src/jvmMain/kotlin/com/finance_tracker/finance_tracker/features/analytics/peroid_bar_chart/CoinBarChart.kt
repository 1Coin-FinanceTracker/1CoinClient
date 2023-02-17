package com.finance_tracker.finance_tracker.features.analytics.peroid_bar_chart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.features.analytics.views.NoTransactionsStub

@Composable
internal fun CoinBarChart(
    barChartEntries: List<CoinBarChartEntry<Float, Float>>,
    labelsArrangement: LabelsArrangement,
    labels: List<String>,
    chartHeight: Dp,
    modifier: Modifier = Modifier,
    onBarChartSelect: (CoinBarChartEntry<*, *>?) -> Unit = {}
) {
    Column(modifier = modifier) {
        if (barChartEntries.any { it.yMax > 0 }) {
            CoinXYChart(
                modifier = Modifier
                    .height(chartHeight),
                barChartEntries = barChartEntries,
                onBarChartSelect = onBarChartSelect
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(chartHeight)
            ) {
                NoTransactionsStub(
                    modifier = Modifier.weight(1f)
                )
                Divider(
                    color = CoinTheme.color.dividers
                )
            }
        }
        LabelsRow(
            modifier = Modifier.padding(top = 12.dp),
            labelsArrangement = labelsArrangement,
            labels = labels
        )
    }
}

enum class LabelsArrangement {
    SpaceAround,
    SpaceBetween
}

@Composable
private fun LabelsRow(
    labelsArrangement: LabelsArrangement,
    labels: List<String>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = when (labelsArrangement) {
            LabelsArrangement.SpaceAround -> Arrangement.SpaceAround
            LabelsArrangement.SpaceBetween -> Arrangement.SpaceBetween
        }
    ) {
        labels.forEach {
            Label(
                modifier = Modifier
                    .weight(1f, fill = labelsArrangement == LabelsArrangement.SpaceAround),
                text = it
            )
        }
    }
}

@Composable
private fun Label(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = text,
        style = CoinTheme.typography.subtitle4,
        color = CoinTheme.color.secondary,
        textAlign = TextAlign.Center
    )
}