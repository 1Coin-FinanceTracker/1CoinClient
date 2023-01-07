package com.finance_tracker.finance_tracker.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.stringResource
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.AccountCardWidth
import com.finance_tracker.finance_tracker.core.ui.rememberVectorPainter

@Suppress("MagicNumber")
@Composable
fun AddAccountCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    fullSize: Boolean = false
) {
    Row (
        modifier = modifier
            .fillMaxHeight()
            .width(width = if (fullSize) {
                AccountCardWidth
            } else {
                38.dp
            })
            .clip(RoundedCornerShape(12.dp))
            .border(
                width = 1.dp,
                color = CoinTheme.color.dividers,
                shape = RoundedCornerShape(12.dp)
            )
            .background(CoinTheme.color.secondaryBackground)
            .clickable { onClick.invoke() },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = rememberVectorPainter("ic_plus"),
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = CoinTheme.color.content.copy(alpha = 0.5f)
        )
        if (fullSize) {
            Text(
                modifier = Modifier.padding(horizontal = 4.dp),
                text = stringResource("home_add_account"),
                color = CoinTheme.color.content.copy(alpha = 0.5f),
                style = CoinTheme.typography.subtitle1
            )
        }
    }
}