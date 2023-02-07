package com.finance_tracker.finance_tracker.core.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.theme.CoinTheme

private const val CheckAnimDurationMillis = 150

@Suppress("MagicNumber")
@Composable
internal fun CoinRadioButton(
    active: Boolean,
    modifier: Modifier = Modifier,
    editable: Boolean = true
) {
    Box(
        modifier = modifier
            .alpha(alpha = if (editable) 1f else 0.4f)
            .size(24.dp),
    ) {
        Icon(
            painter = rememberVectorPainter("ic_radio_inactive"),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            tint = CoinTheme.color.secondary
        )
        AnimatedVisibility(
            visible = active,
            enter = fadeIn(
                animationSpec = tween(CheckAnimDurationMillis)
            ),
            exit = fadeOut(
                animationSpec = tween(CheckAnimDurationMillis)
            )
        ) {
            Icon(
                painter = rememberVectorPainter("ic_radio_active"),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                tint = CoinTheme.color.primary
            )
        }
    }
}