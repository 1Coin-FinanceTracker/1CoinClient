package com.finance_tracker.finance_tracker.presentation.transactions

import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.common.DialogConfigurations
import com.finance_tracker.finance_tracker.core.common.pagination.LazyPagingItems
import com.finance_tracker.finance_tracker.core.common.view_models.BaseLocalsStorage
import com.finance_tracker.finance_tracker.core.navigation.main.MainNavigationTree
import com.finance_tracker.finance_tracker.core.ui.DeleteDialog
import com.finance_tracker.finance_tracker.domain.models.Transaction
import com.finance_tracker.finance_tracker.domain.models.TransactionListModel
import com.finance_tracker.finance_tracker.presentation.add_transaction.AddTransactionScreenParams
import dev.icerock.moko.resources.compose.stringResource
import ru.alexgladkov.odyssey.compose.extensions.present
import ru.alexgladkov.odyssey.compose.extensions.push

sealed interface TransactionsAction {
    object RefreshTransactions: TransactionsAction
    data class OpenTransactionDetailScreen(val transaction: Transaction): TransactionsAction
    data class CloseDeleteTransactionDialog(val dialogKey: String): TransactionsAction
    object UnselectAllItems: TransactionsAction
    data class ShowDeleteDialog(val selectedItemsCount: Int): TransactionsAction
    object OpenAddTransactionScreen: TransactionsAction
}

fun handleAction(
    action: TransactionsAction,
    baseLocalsStorage: BaseLocalsStorage,
    lazyTransactionList: LazyPagingItems<TransactionListModel>,
    onUnselectItems: () -> Unit,
    onCancelClick: (dialogKey: String) -> Unit,
    onConfirmDeleteClick: (dialogKey: String) -> Unit
) {
    val navController = baseLocalsStorage.rootController.findRootController()
    val modalController = navController.findModalController()
    when (action) {
        TransactionsAction.RefreshTransactions -> {
            lazyTransactionList.refresh()
        }
        is TransactionsAction.OpenTransactionDetailScreen -> {
            navController.push(
                screen = MainNavigationTree.AddTransaction.name,
                params = AddTransactionScreenParams(
                    transaction = action.transaction
                )
            )
        }
        is TransactionsAction.CloseDeleteTransactionDialog -> {
            modalController.popBackStack(action.dialogKey, animate = false)
        }
        TransactionsAction.UnselectAllItems -> {
            onUnselectItems.invoke()
        }
        is TransactionsAction.ShowDeleteDialog -> {
            modalController.present(DialogConfigurations.alert) { key ->
                DeleteDialog(
                    titleEntity = if (action.selectedItemsCount > 1) {
                        stringResource(MR.strings.deleting_transactions)
                    } else {
                        stringResource(MR.strings.deleting_transaction)
                    },
                    onCancelClick = { onCancelClick.invoke(key) },
                    onDeleteClick = { onConfirmDeleteClick.invoke(key) }
                )
            }
        }
        TransactionsAction.OpenAddTransactionScreen -> {
            navController.push(
                screen = MainNavigationTree.AddTransaction.name
            )
        }
    }
}