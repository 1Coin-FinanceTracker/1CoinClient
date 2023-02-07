package com.finance_tracker.finance_tracker.presentation.analytics.peroid_bar_chart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import io.github.koalaplot.core.bar.VerticalBarChart
import io.github.koalaplot.core.xychart.LineStyle
import io.github.koalaplot.core.xychart.LinearAxisModel
import io.github.koalaplot.core.xychart.TickPosition
import io.github.koalaplot.core.xychart.XYChart
import io.github.koalaplot.core.xychart.rememberAxisStyle
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.isActive

private const val BarShartShift = 0.5f

@Composable
internal fun CoinXYChart(
    barChartEntries: List<CoinBarChartEntry<Float, Float>>,
    modifier: Modifier = Modifier,
    onBarChartSelect: (CoinBarChartEntry<*, *>?) -> Unit = {}
) {
    var bars by remember(barChartEntries) {
        mutableStateOf(
            barChartEntries.indices.associateWith { Offset.Zero }
        )
    }
    var position by remember { mutableStateOf(Offset(0f, 0f)) }
    var barWidth by remember { mutableStateOf(0) }
    var chartHeight by remember { mutableStateOf(0) }
    var chartOffsetY by remember { mutableStateOf(0f) }

    @Suppress("UnnecessaryParentheses")
    val activeBarIndex by remember(bars, position, barWidth) {
        derivedStateOf {
            if (bars.size != barChartEntries.size || position == Offset.Zero) {
                return@derivedStateOf null
            }
            bars
                .filter {
                    position.x in it.value.x..(it.value.x + barWidth) &&
                            position.y in chartOffsetY..(chartOffsetY + chartHeight)
                }
                .keys.firstOrNull()
        }
    }
    LaunchedEffect(activeBarIndex) {
        val barChartEntryIndex = activeBarIndex ?: -1
        val entry = barChartEntries.getOrNull(barChartEntryIndex)
        onBarChartSelect.invoke(entry)
    }
    XYChart(
        modifier = modifier
            .pointerInput(Unit) {
                val currentContext = currentCoroutineContext()
                awaitPointerEventScope {
                    val releaseEventTypes = setOf(PointerEventType.Release, PointerEventType.Exit)
                    while (currentContext.isActive) {
                        val event = awaitPointerEvent()
                        position = if (event.type in releaseEventTypes) {
                            Offset.Zero
                        } else {
                            event.changes.last().position
                        }
                    }
                }
            }
            .onSizeChanged { chartHeight = it.height }
            .onGloballyPositioned { chartOffsetY = it.positionInParent().y },
        xAxisModel = LinearAxisModel(
            BarShartShift..barChartEntries.count().toFloat() + BarShartShift,
            allowZooming = false,
            allowPanning = false,
            minorTickCount = 0,
            minimumMajorTickIncrement = 1f,
            minimumMajorTickSpacing = 4.dp
        ),
        yAxisModel = LinearAxisModel(
            range = 0f..barChartEntries.maxOf { it.yMax }.coerceAtLeast(1f),
            allowZooming = false,
            allowPanning = false,
            minorTickCount = 0,
            minimumMajorTickIncrement = Float.MAX_VALUE
        ),
        xAxisStyle = rememberAxisStyle(
            tickPosition = TickPosition.None,
            color = Color.Transparent,
            lineWidth = 0.dp,
            minorTickSize = 0.dp,
            majorTickSize = 0.dp
        ),
        yAxisStyle = rememberAxisStyle(
            tickPosition = TickPosition.None,
            color = Color.Transparent,
            lineWidth = 0.dp,
            minorTickSize = 0.dp,
            majorTickSize = 0.dp
        ),
        xAxisLabels = { /*empty*/ },
        xAxisTitle = { /*empty*/ },
        yAxisLabels = { /*empty*/ },
        verticalMajorGridLineStyle = null,
        horizontalMajorGridLineStyle = LineStyle(
            brush = SolidColor(CoinTheme.color.dividers),
            strokeWidth = 1.dp
        )
    ) {
        VerticalBarChart(
            series = listOf(barChartEntries),
            maxBarGroupWidth = 1f,
            bar = { _, index, _ ->
                VerticalBar(
                    active = index == activeBarIndex,
                    onGloballyPosition = { offset ->
                        val newBars = bars.toMutableMap()
                        newBars[index] = offset
                        bars = newBars
                    },
                    onSizeChange = { width ->
                        if (barWidth != width) {
                            barWidth = width
                        }
                    }
                )
            }
        )
    }
}

@Composable
private fun VerticalBar(
    active: Boolean,
    onGloballyPosition: (Offset) -> Unit,
    onSizeChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .onGloballyPositioned { coordinates ->
                val offset = coordinates.parentCoordinates?.parentCoordinates?.parentCoordinates?.positionInParent()
                if (offset != null) {
                    onGloballyPosition.invoke(offset)
                }
            }
            .onSizeChanged { onSizeChange.invoke(it.width) }
            .padding(horizontal = 2.dp)
            .background(
                brush = SolidColor(
                    if (active) {
                        CoinTheme.color.primary
                    } else {
                        CoinTheme.color.dividers
                    }
                ),
                shape = RoundedCornerShape(topStart = 2.dp, topEnd = 2.dp)
            )
    )
}