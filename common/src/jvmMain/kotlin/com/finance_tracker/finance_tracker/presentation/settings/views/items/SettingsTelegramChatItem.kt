package com.finance_tracker.finance_tracker.presentation.settings.views.items

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.rememberVectorPainter
import com.finance_tracker.finance_tracker.presentation.settings.views.ListItem
import dev.icerock.moko.resources.compose.stringResource

@Composable
internal fun SettingsTelegramChatItem(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ListItem(
        modifier = modifier,
        iconLeftPainter = rememberVectorPainter("telegram"),
        hasIconLeftTint = false,
        text = stringResource(MR.strings.settings_telegram_community),
        iconRight = {
            Icon(
                modifier = Modifier
                    .padding(end = 16.dp)
                    .size(20.dp),
                painter = rememberVectorPainter("ic_link"),
                contentDescription = null,
                tint = CoinTheme.color.content
            )
        },
        onClick = onClick
    )
}