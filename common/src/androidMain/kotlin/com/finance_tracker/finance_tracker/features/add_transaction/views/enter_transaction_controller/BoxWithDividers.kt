package com.finance_tracker.finance_tracker.features.add_transaction.views.enter_transaction_controller

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.finance_tracker.finance_tracker.core.theme.CoinTheme

@Composable
internal fun BoxWithDividers(
    columnCount: Int,
    index: Int,
    dividerWidth: Dp,
    modifier: Modifier = Modifier,
    dividerColor: Color = CoinTheme.color.dividers,
    content: @Composable () -> Unit = {}
) {
    Column(
        modifier = modifier.width(IntrinsicSize.Min)
    ) {
        Row(
            modifier = Modifier.height(IntrinsicSize.Min)
        ) {
            Box(modifier = Modifier.weight(1f)) {
                content.invoke()
            }

            if ((index + 1) % columnCount != 0) {
                Divider(
                    modifier = Modifier
                        .background(dividerColor)
                        .width(dividerWidth)
                        .fillMaxHeight()
                )
            }
        }
        Divider(
            modifier = Modifier
                .background(dividerColor)
                .height(dividerWidth)
                .fillMaxWidth()
        )
    }
}