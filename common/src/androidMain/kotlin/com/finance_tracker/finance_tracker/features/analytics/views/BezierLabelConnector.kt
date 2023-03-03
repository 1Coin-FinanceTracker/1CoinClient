package com.finance_tracker.finance_tracker.features.analytics.views

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.toPx
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import io.github.koalaplot.core.pie.BezierLabelConnector
import io.github.koalaplot.core.pie.LabelConnectorScope

@Composable
internal fun LabelConnectorScope.CoinBezierLabelConnector(
    modifier: Modifier = Modifier,
    connectorColor: Color = CoinTheme.color.primary,
    connectorStroke: Stroke = Stroke(width = 1.dp.toPx())
) {
    BezierLabelConnector(
        modifier = modifier,
        connectorColor = connectorColor,
        connectorStroke = connectorStroke
    )
}