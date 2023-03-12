package com.finance_tracker.finance_tracker.features.settings.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.rememberVectorPainter

@Composable
internal fun ListItem(
    iconLeftPainter: Painter,
    text: String,
    modifier: Modifier = Modifier,
    hasIconLeftTint: Boolean = true,
    onInfoClick: (() -> Unit)? = null,
    iconRight: (@Composable () -> Unit)? = null,
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(enabled = onClick != null) {
                onClick?.invoke()
            }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = iconLeftPainter,
            contentDescription = null,
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    end = 8.dp
                )
                .size(24.dp),
            tint = if (hasIconLeftTint) {
                CoinTheme.color.content
            } else {
                Color.Unspecified
            }
        )
        Row(
            modifier = Modifier
                .weight(1f)
                .padding(end = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                modifier = Modifier
                    .width(IntrinsicSize.Max),
                style = CoinTheme.typography.body1_medium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = CoinTheme.color.content
            )
            if (onInfoClick != null) {
                Icon(
                    painter = rememberVectorPainter(id = "ic_more_info"),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = 1.dp)
                        .size(24.dp)
                        .clip(CircleShape)
                        .clickable { onInfoClick.invoke() }
                        .padding(4.dp),
                    tint = CoinTheme.color.content
                )
            }
        }

        iconRight?.invoke()
    }
}