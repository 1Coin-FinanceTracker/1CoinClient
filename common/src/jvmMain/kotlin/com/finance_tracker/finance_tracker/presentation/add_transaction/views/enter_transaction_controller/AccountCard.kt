package com.finance_tracker.finance_tracker.presentation.add_transaction.views.enter_transaction_controller

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.toUIColor
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.theme.staticTextSize
import com.finance_tracker.finance_tracker.domain.models.Account

@Composable
internal fun AccountCard(
    account: Account,
    modifier: Modifier = Modifier,
    maxLines: Int = 2,
    textStyle: TextStyle = CoinTheme.typography.body1
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = account.colorModel.color.toUIColor(),
                    shape = CircleShape
                )
                .size(10.dp)
        )
        Text(
            modifier = Modifier.padding(start = 4.dp),
            text = account.name,
            style = textStyle.staticTextSize(),
            maxLines = maxLines,
            overflow = TextOverflow.Ellipsis
        )
    }
}