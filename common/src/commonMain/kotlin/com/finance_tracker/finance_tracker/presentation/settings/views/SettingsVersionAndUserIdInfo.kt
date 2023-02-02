package com.finance_tracker.finance_tracker.presentation.settings.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.rememberVectorPainter
import dev.icerock.moko.resources.compose.stringResource

private const val MaxUserIdCharCount = 10

@Composable
internal fun SettingsVersionAndUserIdInfo(
    versionName: String,
    userId: String,
    onCopyUserId: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(
                vertical = 8.dp,
                horizontal = 16.dp
            )
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.padding(vertical = 8.dp),
            text = "${stringResource(MR.strings.version)} $versionName",
            style = CoinTheme.typography.subtitle4,
            color = CoinTheme.color.secondary
        )

        Text(
            modifier = Modifier
                .padding(
                    start = 6.dp,
                    end = 2.dp
                ),
            style = CoinTheme.typography.subtitle4,
            text = "•",
            color = CoinTheme.color.secondary
        )

        Row(
            modifier = Modifier
                .width(IntrinsicSize.Max)
                .clip(RoundedCornerShape(16.dp))
                .clickable { onCopyUserId.invoke() }
                .padding(
                    vertical = 8.dp,
                    horizontal = 4.dp
                )
        ) {
            Text(
                modifier = Modifier
                    .padding(end = 4.dp)
                    .weight(1f),
                text = "${stringResource(MR.strings.user_id)} ${userId.take(MaxUserIdCharCount)}...",
                style = CoinTheme.typography.subtitle4,
                color = CoinTheme.color.secondary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Icon(
                painter = rememberVectorPainter("ic_duplicate"),
                contentDescription = null,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(12.dp),
                tint = CoinTheme.color.secondary
            )
        }
    }
}