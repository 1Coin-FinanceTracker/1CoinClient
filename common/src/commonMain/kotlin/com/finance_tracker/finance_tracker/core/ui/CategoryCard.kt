package com.finance_tracker.finance_tracker.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.domain.models.Category

@Composable
fun CategoryCard(
    data: Category,
    modifier: Modifier = Modifier,
    onCrossDeleteClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Icon(
            painter = rememberVectorPainter(loadXmlPicture("ic_three_stripes")),
            contentDescription = null,
            modifier = Modifier
                .padding(end = 12.dp)
                .size(18.dp)
                .align(Alignment.CenterVertically),
            tint = CoinTheme.color.content
        )
        Icon(
            painter = rememberVectorPainter(loadXmlPicture(name = data.iconId)),
            contentDescription = null,
            Modifier
                .size(42.dp)
                .clip(CircleShape)
                .background(
                    color = CoinTheme.color.secondaryBackground,
                    shape = CircleShape
                )
                .padding(8.dp)
                .align(Alignment.CenterVertically),
            tint = CoinTheme.color.content
        )
        Text(
            text = data.name,
            style = CoinTheme.typography.body2,
            modifier = Modifier
                .padding(start = 12.dp)
                .align(Alignment.CenterVertically)
        )
        Spacer(Modifier.weight(1f))
        Icon(
            painter = rememberVectorPainter(loadXmlPicture("ic_cross")),
            contentDescription = null,
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .size(32.dp)
                .align(Alignment.CenterVertically)
                .clickable { onCrossDeleteClick() },
            tint = Color.Red,
        )
    }
}