package com.finance_tracker.finance_tracker.core.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.theme.CoinTheme

@Composable
fun CoinSwitch(
    modifier: Modifier = Modifier,
    scale: Float = 1f,
    width: Dp = 38.dp,
    height: Dp = 20.dp,
    checkedTrackColor: Color = CoinTheme.color.primary,
    uncheckedTrackColor: Color = CoinTheme.color.dividers,
    thumbColor: Color = CoinTheme.color.background,
    gapBetweenThumbAndTrackEdge: Dp = 2.dp
) {

    var switchOn by remember { mutableStateOf(true) }

    val thumbRadius = height / 2 - gapBetweenThumbAndTrackEdge

    val animatePosition = animateFloatAsState(
        targetValue = if (switchOn) {
            with(LocalDensity.current) {
                (width - thumbRadius - gapBetweenThumbAndTrackEdge).toPx()
            }
        } else {
            with(LocalDensity.current) {
                (thumbRadius + gapBetweenThumbAndTrackEdge).toPx()
            }
        }
    )

    Canvas(
        modifier = modifier
            .size(width = width, height = height)
            .scale(scale = scale)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        switchOn = !switchOn
                    }
                )
            }
    ) {

        val cornerRadiusPx = (height / 2).toPx()

        // Track
        drawRoundRect(
            color = if (switchOn) {
                checkedTrackColor
            } else {
                uncheckedTrackColor
            },
            cornerRadius = CornerRadius(
                x = cornerRadiusPx,
                y = cornerRadiusPx
            ),
        )

        // Thumb
        drawCircle(
            color = thumbColor,
            radius = thumbRadius.toPx(),
            center = Offset(
                x = animatePosition.value,
                y = size.height / 2
            )
        )
    }
}