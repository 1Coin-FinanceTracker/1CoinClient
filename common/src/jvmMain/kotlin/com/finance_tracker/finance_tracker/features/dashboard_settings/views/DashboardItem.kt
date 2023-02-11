package com.finance_tracker.finance_tracker.features.dashboard_settings.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.noRippleClickable
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.CoinRadioButton
import com.finance_tracker.finance_tracker.core.ui.rememberVectorPainter
import com.finance_tracker.finance_tracker.domain.models.DashboardWidgetData
import dev.icerock.moko.resources.compose.stringResource


@Composable
internal fun DashboardItem(
    data: DashboardWidgetData,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .border(
                width = 1.dp,
                color = CoinTheme.color.dividers,
                shape = RoundedCornerShape(12.dp)
            )
            .background(CoinTheme.color.background)
            .noRippleClickable(enabled = data.type.isEditable) {
                onClick.invoke()
            }
            .padding(
                vertical = 24.dp,
                horizontal = 16.dp
            )
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = rememberVectorPainter("ic_three_stripes"),
            contentDescription = null,
            modifier = Modifier
                .padding(end = 12.dp)
                .size(18.dp),
            tint = CoinTheme.color.content
        )
        Text(
            text = stringResource(data.type.nameRes),
            style = CoinTheme.typography.h5,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = CoinTheme.color.content
        )
        Spacer(modifier = Modifier.weight(1f))

        CoinRadioButton(
            modifier = Modifier
                .padding(start = 12.dp),
            active = data.isEnabled,
            editable = data.type.isEditable
        )
    }
}