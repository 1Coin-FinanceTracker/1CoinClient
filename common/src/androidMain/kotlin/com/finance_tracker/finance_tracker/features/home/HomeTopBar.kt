package com.finance_tracker.finance_tracker.features.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.common.formatters.AmountFormatMode
import com.finance_tracker.finance_tracker.core.common.formatters.format
import com.finance_tracker.finance_tracker.core.common.rememberAsyncImagePainter
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.CoinTopAppBar
import com.finance_tracker.finance_tracker.domain.models.Amount
import dev.icerock.moko.resources.compose.stringResource

@Composable
internal fun HomeTopBar(
    totalBalance: Amount,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    CoinTopAppBar(
        modifier = modifier,
        appBarHeight = 64.dp,
        title = {
            Column {
                Text(
                    text = totalBalance.format(mode = AmountFormatMode.NegativeSign),
                    style = CoinTheme.typography.h4,
                    color = CoinTheme.color.content
                )
                Text(
                    text = stringResource(MR.strings.home_topbar_text),
                    style = CoinTheme.typography.subtitle2,
                    color = CoinTheme.color.secondary
                )
            }
        },
        actions = {
            Icon(
                painter = rememberAsyncImagePainter(MR.files.ic_settings),
                contentDescription = null,
                Modifier
                    .padding(end = 2.dp)
                    .size(36.dp)
                    .clip(CircleShape)
                    .clickable { onSettingsClick.invoke() }
                    .padding(6.dp),
                tint = CoinTheme.color.content
            )
        }
    )
}