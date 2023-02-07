package com.finance_tracker.finance_tracker.presentation.analytics.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.rememberVectorPainter
import dev.icerock.moko.resources.compose.stringResource

@Composable
internal fun NoTransactionsStub(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = rememberVectorPainter("ic_error"),
            contentDescription = null,
            tint = CoinTheme.color.secondary
        )

        Text(
            modifier = Modifier
                .padding(start = 8.dp),
            text = stringResource(MR.strings.analytics_make_first_transactions),
            style = CoinTheme.typography.subtitle2,
            color = CoinTheme.color.secondary
        )
    }
}