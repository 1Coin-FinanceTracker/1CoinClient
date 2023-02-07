package com.finance_tracker.finance_tracker.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.clicks.scaleClickAnimation
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import ru.alexgladkov.odyssey.compose.helpers.noRippleClickable

@Composable
internal fun IconActionButton(
    painter: Painter,
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified,
    onClick: () -> Unit = {}
) {
    Icon(
        modifier = modifier
            .size(40.dp)
            .scaleClickAnimation()
            .clip(RoundedCornerShape(12.dp))
            .background(CoinTheme.color.secondaryBackground)
            .noRippleClickable { onClick.invoke() }
            .padding(8.dp),
        painter = painter,
        tint = tint,
        contentDescription = null
    )
}