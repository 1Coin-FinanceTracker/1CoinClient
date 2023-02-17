package com.finance_tracker.finance_tracker.features.transactions

import com.finance_tracker.finance_tracker.domain.models.Transaction

sealed interface TransactionsAction {
    object RefreshTransactions: TransactionsAction
    data class OpenTransactionDetailScreen(val transaction: Transaction): TransactionsAction
    data class CloseDeleteTransactionDialog(val dialogKey: String): TransactionsAction
    object UnselectAllItems: TransactionsAction
    data class ShowDeleteDialog(val selectedItemsCount: Int): TransactionsAction
    object OpenAddTransactionScreen: TransactionsAction
}