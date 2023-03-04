package com.finance_tracker.finance_tracker.features.add_transaction.views.enter_transaction_controller

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.common.rememberAsyncImagePainter
import com.finance_tracker.finance_tracker.core.theme.CoinTheme

@Composable
internal fun AddCell(
    columnCount: Int,
    index: Int,
    dividerWidth: Dp,
    dividerColor: Color,
    cellHeight: Dp,
    onCellWidthChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    BoxWithDividers(
        modifier = modifier
            .clickable { onClick.invoke() },
        columnCount = columnCount,
        index = index,
        dividerWidth = dividerWidth,
        dividerColor = dividerColor
    ) {
        Box(
            modifier = Modifier
                .onSizeChanged { onCellWidthChange.invoke(it.width) }
                .height(cellHeight)
                .fillMaxSize()
        ) {
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.Center),
                painter = rememberAsyncImagePainter(MR.files.ic_plus),
                tint = CoinTheme.color.secondary,
                contentDescription = null
            )
        }
    }
}