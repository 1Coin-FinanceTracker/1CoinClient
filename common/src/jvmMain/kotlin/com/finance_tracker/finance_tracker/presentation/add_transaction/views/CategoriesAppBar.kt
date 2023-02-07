package com.finance_tracker.finance_tracker.presentation.add_transaction.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.TabRowDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.AppBarIcon
import com.finance_tracker.finance_tracker.core.ui.CoinTopAppBar
import com.finance_tracker.finance_tracker.core.ui.rememberVectorPainter
import com.finance_tracker.finance_tracker.core.ui.tab_rows.TransactionTypeTab
import com.finance_tracker.finance_tracker.core.ui.tab_rows.TransactionTypesTabRow
import ru.alexgladkov.odyssey.compose.local.LocalRootController

@Composable
internal fun CategoriesAppBar(
    doneButtonEnabled: Boolean,
    modifier: Modifier = Modifier,
    selectedTransactionType: TransactionTypeTab = TransactionTypeTab.Expense,
    onTransactionTypeSelect: (TransactionTypeTab) -> Unit = {},
    onDoneClick: () -> Unit = {}
) {
    val rootController = LocalRootController.current
    Column(modifier = modifier) {
        CoinTopAppBar(
            navigationIcon = {
                AppBarIcon(
                    painter = rememberVectorPainter("ic_arrow_back"),
                    onClick = { rootController.popBackStack() }
                )
            },
            title = {
                Row(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(end = 42.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Bottom
                ) {
                    TransactionTypesTabRow(
                        modifier = Modifier.wrapContentWidth(),
                        selectedType = selectedTransactionType,
                        hasBottomDivider = false,
                        isHorizontallyCentered = true,
                        onSelect = onTransactionTypeSelect
                    )
                }
            },
            actions = {
                AppBarIcon(
                    painter = rememberVectorPainter("ic_done"),
                    onClick = onDoneClick,
                    enabled = doneButtonEnabled
                )
            }
        )

        TabRowDefaults.Divider(
            color = CoinTheme.color.dividers
        )
    }
}