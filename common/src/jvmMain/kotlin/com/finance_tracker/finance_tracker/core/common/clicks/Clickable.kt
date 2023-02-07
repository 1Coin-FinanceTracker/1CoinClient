package com.finance_tracker.finance_tracker.core.common.clicks

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput

fun Modifier.scaleClickAnimation(
    enabled: Boolean = true
): Modifier = composed {

    var state by remember { mutableStateOf(State.Idle) }

    @Suppress("MagicNumber")
    val scale by animateFloatAsState(
        targetValue = if (enabled && state == State.Pressed) {
            0.97f
        } else {
            1f
        },
        animationSpec = tween(durationMillis = 200)
    )

    this
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
            this.alpha = alpha
        }
        .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = {}
        )
        .pointerInput(state) {
            awaitPointerEventScope {
                state = if (state == State.Pressed) {
                    waitForUpOrCancellation()
                    State.Idle
                } else {
                    awaitFirstDown(false)
                    State.Pressed
                }
            }
        }
}