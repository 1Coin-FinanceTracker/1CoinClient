package com.finance_tracker.finance_tracker.core.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.`if`
import com.finance_tracker.finance_tracker.core.theme.CoinAlpha
import com.finance_tracker.finance_tracker.core.theme.CoinTheme

@Composable
internal fun AppBarIcon(
    painter: Painter,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    contentDescription: String? = null,
    tint: Color = CoinTheme.color.content,
    enabled: Boolean = true,
    width: Dp = 40.dp,
    height: Dp = 40.dp
) {
    Icon(
        modifier = modifier
            .`if`(!enabled) {
                alpha(CoinAlpha.Medium)
            }
            .clip(CircleShape)
            .size(
                width = width,
                height = height
            )
            .`if`(enabled) {
                clickable { onClick.invoke() }
            }
            .padding(8.dp),
        painter = painter,
        contentDescription = contentDescription,
        tint = tint
    )
}