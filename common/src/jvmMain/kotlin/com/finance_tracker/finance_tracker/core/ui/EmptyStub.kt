package com.finance_tracker.finance_tracker.core.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.theme.CoinTheme

@Suppress("MagicNumber")
@Composable
internal fun EmptyStub(
    image: Painter,
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    stubHeightAlignment: Float = 0.75f,
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(stubHeightAlignment),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = Modifier
                    .size(
                        width= 275.dp,
                        height= 155.dp,
                    ),
                painter = image,
                contentDescription = null
            )
            Row(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .clip(CircleShape)
                    .clickable {
                        onClick.invoke()
                    }
                    .padding(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .padding(end = 2.dp)
                        .size(24.dp),
                    painter = rememberVectorPainter("ic_plus"),
                    contentDescription = null,
                    tint = CoinTheme.color.primary,
                )
                Text(
                    text = text,
                    style = CoinTheme.typography.subtitle1,
                    color = CoinTheme.color.primary
                )
            }
        }
    }
}