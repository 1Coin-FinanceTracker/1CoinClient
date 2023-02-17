package com.finance_tracker.finance_tracker.features.add_transaction.views.enter_transaction_controller

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.domain.models.Account
import kotlin.math.roundToInt

@Composable
internal fun AccountSelector(
    accounts: List<Account>,
    onAccountSelect: (Account) -> Unit,
    onAccountAdd: () -> Unit,
    modifier: Modifier = Modifier
) {
    var gridWidth by remember { mutableStateOf(1) }
    var cellWidth by remember { mutableStateOf(1) }
    val columnCount by remember {
        derivedStateOf {
            (gridWidth.toFloat() / cellWidth)
                .roundToInt()
                .coerceAtLeast(1)
        }
    }

    val dividerWidth = 0.5.dp
    val dividerColor = CoinTheme.color.dividers

    val cellHeight = 68.dp
    LazyVerticalGrid(
        modifier = modifier
            .fillMaxHeight()
            .onSizeChanged {
                gridWidth = it.width
            },
        columns = GridCells.Adaptive(156.dp)
    ) {
        itemsIndexed(accounts) { index, account ->
            BoxWithDividers(
                modifier = Modifier.clickable {
                    onAccountSelect.invoke(account)
                },
                columnCount = columnCount,
                index = index,
                dividerWidth = dividerWidth,
                dividerColor = dividerColor
            ) {
                AccountCard(
                    modifier = Modifier
                        .height(cellHeight)
                        .fillMaxSize(),
                    account = account
                )
            }
        }
        item {
            AddCell(
                columnCount = columnCount,
                index = accounts.size,
                dividerWidth = dividerWidth,
                dividerColor = dividerColor,
                cellHeight = cellHeight,
                onCellWidthChange = { cellWidth = it },
                onClick = onAccountAdd
            )
        }
    }
}