package com.finance_tracker.finance_tracker.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import kotlin.math.max
import kotlin.math.min

@Composable
internal fun ItemWrapper(
    isFirstItem: Boolean,
    isLastItem: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = {}
) {
    Box(
        modifier = modifier
            .background(CoinTheme.color.background)
            .border(
                strokeWidth = 1.dp,
                color = CoinTheme.color.dividers,
                radius = 24.dp,
                hasStartBorder = true,
                hasEndBorder = true,
                hasTopBorder = isFirstItem,
                hasBottomBorder = isLastItem
            )
    ) {
        content.invoke()
    }
}

@Stable
fun Modifier.border(
    strokeWidth: Dp = 1.dp,
    color: Color = Color.Black,
    radius: Dp = 0.dp,
    hasStartBorder: Boolean = true,
    hasTopBorder: Boolean = true,
    hasEndBorder: Boolean = true,
    hasBottomBorder: Boolean = true
): Modifier {
    return drawBehind {

        val radiusPx = radius.toPx()

        val strokeWidthPx = strokeWidth.toPx()

        val maxWidth: Float
        val maxHeight: Float
        if (size.width > size.height) {
            maxWidth = max(size.width, size.height)
            maxHeight = min(size.width, size.height)
        } else {
            maxWidth = min(size.width, size.height)
            maxHeight = max(size.width, size.height)
        }

        if (hasStartBorder && hasTopBorder) {
            drawTopStartCorner(
                strokeWidthPx = strokeWidthPx,
                color = color,
                maxWidth = maxWidth,
                maxHeight = maxHeight,
                radiusPx = radiusPx
            )
        }
        if (hasEndBorder && hasTopBorder) {
            drawTopEndCorner(
                strokeWidthPx = strokeWidthPx,
                color = color,
                maxWidth = maxWidth,
                maxHeight = maxHeight,
                radiusPx = radiusPx
            )
        }
        if (hasEndBorder && hasBottomBorder) {
            drawBottomEndCorner(
                strokeWidthPx = strokeWidthPx,
                color = color,
                maxWidth = maxWidth,
                maxHeight = maxHeight,
                radiusPx = radiusPx
            )
        }
        if (hasStartBorder && hasBottomBorder) {
            drawBottomStartCorner(
                strokeWidthPx = strokeWidthPx,
                color = color,
                maxWidth = maxWidth,
                maxHeight = maxHeight,
                radiusPx = radiusPx
            )
        }

        if (hasStartBorder) {
            drawStartBorder(
                strokeWidth = strokeWidth,
                color = color,
                radius = radius,
                hasTopStartCorner = hasTopBorder,
                hasBottomStartCorner = hasBottomBorder
            )
        }
        if (hasTopBorder) {
            drawTopBorder(
                strokeWidth = strokeWidth,
                color = color,
                radius = radius,
                hasTopStartCorner = hasStartBorder,
                hasTopEndCorner = hasEndBorder
            )
        }
        if (hasEndBorder) {
            drawEndBorder(
                strokeWidth = strokeWidth,
                color = color,
                radius = radius,
                hasTopEndCorner = hasTopBorder,
                hasBottomEndCorner = hasBottomBorder
            )
        }
        if (hasBottomBorder) {
            drawBottomBorder(
                strokeWidth = strokeWidth,
                color = color,
                radius = radius,
                hasBottomStartCorner = hasStartBorder,
                hasBottomEndCorner = hasEndBorder
            )
        }
    }
}

private fun DrawScope.drawTopStartCorner(
    color: Color,
    maxWidth: Float,
    maxHeight: Float,
    strokeWidthPx: Float,
    radiusPx: Float
) {
    drawCorner(
        startAngle = -180f,
        topLeft = Offset(x = 0f, y = 0f),
        color = color,
        maxWidth = maxWidth,
        maxHeight = maxHeight,
        strokeWidthPx = strokeWidthPx,
        radiusPx = radiusPx
    )
}

private fun DrawScope.drawTopEndCorner(
    color: Color,
    maxWidth: Float,
    maxHeight: Float,
    strokeWidthPx: Float,
    radiusPx: Float
) {
    drawCorner(
        startAngle = -90f,
        topLeft = Offset(
            x = size.width - radiusPx.coerceIn(0f, size.width),
            y = 0f
        ),
        color = color,
        maxWidth = maxWidth,
        maxHeight = maxHeight,
        strokeWidthPx = strokeWidthPx,
        radiusPx = radiusPx
    )
}

private fun DrawScope.drawBottomEndCorner(
    color: Color,
    maxWidth: Float,
    maxHeight: Float,
    strokeWidthPx: Float,
    radiusPx: Float
) {
    drawCorner(
        startAngle = 0f,
        topLeft = Offset(
            x = size.width - radiusPx.coerceIn(0f, size.width),
            y = size.height - radiusPx.coerceIn(0f, size.height)
        ),
        color = color,
        maxWidth = maxWidth,
        maxHeight = maxHeight,
        strokeWidthPx = strokeWidthPx,
        radiusPx = radiusPx
    )
}

private fun DrawScope.drawBottomStartCorner(
    color: Color,
    maxWidth: Float,
    maxHeight: Float,
    strokeWidthPx: Float,
    radiusPx: Float
) {
    drawCorner(
        startAngle = 90f,
        topLeft = Offset(
            x = 0f,
            y = size.height - radiusPx.coerceIn(0f, size.height)
        ),
        color = color,
        maxWidth = maxWidth,
        maxHeight = maxHeight,
        strokeWidthPx = strokeWidthPx,
        radiusPx = radiusPx
    )
}

private fun DrawScope.drawCorner(
    startAngle: Float,
    topLeft: Offset,
    color: Color,
    maxWidth: Float,
    maxHeight: Float,
    strokeWidthPx: Float,
    radiusPx: Float
) {
    if (strokeWidthPx == 0f) return
    drawArc(
        color = color,
        startAngle = startAngle,
        sweepAngle = 90f,
        useCenter = false,
        topLeft = topLeft,
        size = Size(
            width = radiusPx.coerceIn(minimumValue = 0f, maximumValue = maxWidth),
            height = radiusPx.coerceIn(minimumValue = 0f, maximumValue = maxHeight)
        ),
        style = Stroke(
            width = strokeWidthPx
        )
    )
}

private fun DrawScope.drawStartBorder(
    strokeWidth: Dp,
    color: Color,
    radius: Dp,
    hasTopStartCorner: Boolean,
    hasBottomStartCorner: Boolean,
) {
    val strokeWidthPx = strokeWidth.toPx()
    if (strokeWidthPx == 0f) return
    val topPadding = if (hasTopStartCorner) {
        radius.value
    } else {
        0f
    }
    val bottomPadding = if (hasBottomStartCorner) {
        radius.value
    } else {
        0f
    }
    drawLine(
        color = color,
        start = Offset(
            x = 0f,
            y = topPadding
        ),
        end = Offset(
            x = 0f,
            y = size.height - bottomPadding
        ),
        strokeWidth = strokeWidth.toPx()
    )
}

private fun DrawScope.drawTopBorder(
    strokeWidth: Dp,
    color: Color,
    radius: Dp,
    hasTopStartCorner: Boolean,
    hasTopEndCorner: Boolean
) {
    val strokeWidthPx = strokeWidth.toPx()
    if (strokeWidthPx == 0f) return
    val startPadding = if (hasTopStartCorner) {
        radius.value
    } else {
        0f
    }
    val endPadding = if (hasTopEndCorner) {
        radius.value
    } else {
        0f
    }
    drawLine(
        color = color,
        start = Offset(startPadding, 0f),
        end = Offset(size.width - endPadding, 0f),
        strokeWidth = strokeWidth.toPx()
    )
}

private fun DrawScope.drawEndBorder(
    strokeWidth: Dp,
    color: Color,
    radius: Dp,
    hasTopEndCorner: Boolean,
    hasBottomEndCorner: Boolean,
) {
    val strokeWidthPx = strokeWidth.toPx()
    if (strokeWidthPx == 0f) return
    val topPadding = if (hasTopEndCorner) {
        radius.value
    } else {
        0f
    }
    val bottomPadding = if (hasBottomEndCorner) {
        radius.value
    } else {
        0f
    }
    drawLine(
        color = color,
        start = Offset(size.width, topPadding),
        end = Offset(size.width, size.height - bottomPadding),
        strokeWidth = strokeWidth.toPx()
    )
}

private fun DrawScope.drawBottomBorder(
    strokeWidth: Dp,
    color: Color,
    radius: Dp,
    hasBottomStartCorner: Boolean,
    hasBottomEndCorner: Boolean
) {
    val strokeWidthPx = strokeWidth.toPx()
    if (strokeWidthPx == 0f) return
    val startPadding = if (hasBottomStartCorner) {
        radius.value
    } else {
        0f
    }
    val endPadding = if (hasBottomEndCorner) {
        radius.value
    } else {
        0f
    }
    drawLine(
        color = color,
        start = Offset(startPadding, size.height),
        end = Offset(size.width - endPadding, size.height),
        strokeWidth = strokeWidth.toPx()
    )
}