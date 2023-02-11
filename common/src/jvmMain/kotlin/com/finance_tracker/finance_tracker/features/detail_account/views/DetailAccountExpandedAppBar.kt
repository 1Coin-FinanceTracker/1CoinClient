package com.finance_tracker.finance_tracker.features.detail_account.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.finance_tracker.finance_tracker.core.common.asDp
import com.finance_tracker.finance_tracker.core.common.formatters.AmountFormatMode
import com.finance_tracker.finance_tracker.core.common.formatters.format
import com.finance_tracker.finance_tracker.core.common.statusBarsPadding
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.rememberVectorPainter
import com.finance_tracker.finance_tracker.domain.models.Amount

@Composable
internal fun DetailAccountExpandedAppBar(
    color: Color,
    amount: Amount,
    iconId: String,
    contentAlpha: Float,
    modifier: Modifier = Modifier,
    onIconClick: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color)
            .alpha(contentAlpha)
            .statusBarsPadding()
            .padding(start = 16.dp)
    ) {
        Icon(
            modifier = Modifier
                .padding(top = 64.dp)
                .size(48.dp)
                .clip(CircleShape)
                .clickable {
                    onIconClick.invoke()
                }
                .background(CoinTheme.color.content.copy(alpha = 0.2f))
                .padding(12.dp),
            painter = rememberVectorPainter(iconId),
            contentDescription = null,
            tint = CoinTheme.color.white
        )
        Text(
            modifier = Modifier.padding(
                top = 16.dp,
                bottom = 30.sp.asDp()
            ),
            text = amount.format(mode = AmountFormatMode.NegativeSign),
            style = CoinTheme.typography.h2,
            color = CoinTheme.color.white
        )
    }
}