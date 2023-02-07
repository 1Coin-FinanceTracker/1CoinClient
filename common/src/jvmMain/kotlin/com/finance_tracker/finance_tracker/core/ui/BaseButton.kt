package com.finance_tracker.finance_tracker.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.clicks.scaleClickAnimation
import com.finance_tracker.finance_tracker.core.common.`if`
import com.finance_tracker.finance_tracker.core.theme.CoinAlpha
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import ru.alexgladkov.odyssey.compose.helpers.noRippleClickable

@Composable
@Suppress("ReusedModifierInstance")
internal fun BaseButton(
    modifier: Modifier = Modifier,
    contentColor: Color = CoinTheme.color.primaryVariant,
    backgroundColor: Color = CoinTheme.color.primary,
    borderColor: Color = backgroundColor,
    onClick: () -> Unit = {},
    enabled: Boolean = true,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalContentColor provides contentColor
    ) {
        Row(
            modifier = modifier
                .`if`(!enabled) {
                    alpha(CoinAlpha.Medium)
                }
                .scaleClickAnimation(enabled = enabled)
                .clip(RoundedCornerShape(12.dp))
                .border(
                    width = 0.5.dp,
                    color = borderColor,
                    shape = RoundedCornerShape(12.dp)
                )
                .background(backgroundColor)
                .`if`(enabled) {
                    noRippleClickable { onClick.invoke() }
                }
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            content.invoke()
        }
    }
}