package com.finance_tracker.finance_tracker.features.transactions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.finance_tracker.finance_tracker.core.common.LocalFixedInsets
import com.finance_tracker.finance_tracker.core.common.StoredViewModel
import com.finance_tracker.finance_tracker.core.common.pagination.AutoRefreshList
import com.finance_tracker.finance_tracker.core.common.pagination.collectAsLazyPagingItems
import com.finance_tracker.finance_tracker.core.common.view_models.watchViewActions
import com.finance_tracker.finance_tracker.core.theme.CoinPaddings
import com.finance_tracker.finance_tracker.core.ui.transactions.CommonTransactionsList
import com.finance_tracker.finance_tracker.domain.models.TransactionListModel
import com.finance_tracker.finance_tracker.features.transactions.views.TransactionsAppBar

@Composable
internal fun TransactionsScreen() {
    StoredViewModel<TransactionsViewModel> { viewModel ->

        val lazyTransactionList =
            viewModel.paginatedTransactions.collectAsLazyPagingItems()

        var selectedItems by remember {
            mutableStateOf(emptyList<TransactionListModel.Data>())
        }

        fun unselectAllItems() {
            selectedItems.forEach {
                it.isSelected.value = false
            }
            selectedItems = emptyList()
        }

        viewModel.watchViewActions { action, baseLocalsStorage ->
            handleAction(
                action = action,
                baseLocalsStorage = baseLocalsStorage,
                lazyTransactionList = lazyTransactionList,
                onUnselectItems = ::unselectAllItems,
                onCancelClick = viewModel::onCancelDeletingTransactionsClick,
                onConfirmDeleteClick =  { key ->
                    viewModel.onConfirmDeleteTransactionsClick(
                        transactions = selectedItems,
                        dialogKey = key
                    )
                }
            )
        }

        AutoRefreshList(lazyTransactionList)

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            val selectedItemsCount by remember {
                derivedStateOf { selectedItems.count() }
            }

            TransactionsAppBar(
                selectedItemsCount = selectedItemsCount,
                onCloseClick = viewModel::onCloseAppBarMenuClick,
                onDeleteClick = { viewModel.onDeleteTransactionsClick(selectedItemsCount) }
            )

            val navigationBarsHeight = LocalFixedInsets.current.navigationBarsHeight
            CommonTransactionsList(
                transactions = lazyTransactionList,
                contentPadding = PaddingValues(
                    bottom = CoinPaddings.bottomNavigationBar + navigationBarsHeight
                ),
                onClick = { transactionData ->
                    if (selectedItemsCount > 0) {
                        if (transactionData.isSelected.value) {
                            transactionData.isSelected.value = false
                            selectedItems = selectedItems - transactionData
                        } else {
                            transactionData.isSelected.value = true
                            selectedItems = selectedItems + transactionData
                        }
                    } else {
                        viewModel.onTransactionClick(transactionData.transaction)
                    }
                },
                onLongClick = {
                    it.isSelected.value = !it.isSelected.value
                    selectedItems = selectedItems + it
                },
                onAddTransactionClick = viewModel::onAddTransactionClick
            )
        }
    }
}