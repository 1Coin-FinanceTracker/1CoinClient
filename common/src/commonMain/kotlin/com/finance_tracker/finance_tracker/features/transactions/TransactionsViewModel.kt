package com.finance_tracker.finance_tracker.features.transactions

import app.cash.paging.cachedIn
import com.finance_tracker.finance_tracker.core.common.view_models.BaseViewModel
import com.finance_tracker.finance_tracker.domain.interactors.TransactionsInteractor
import com.finance_tracker.finance_tracker.domain.models.Transaction
import com.finance_tracker.finance_tracker.domain.models.TransactionListModel
import com.finance_tracker.finance_tracker.features.transactions.analytics.TransactionsAnalytics
import kotlinx.coroutines.launch

class TransactionsViewModel(
    private val transactionsInteractor: TransactionsInteractor,
    private val transactionsAnalytics: TransactionsAnalytics
): BaseViewModel<TransactionsAction>() {

    init {
        transactionsAnalytics.trackScreenOpen()
    }

    val paginatedTransactions = transactionsInteractor.getPaginatedTransactions()
        .cachedIn(viewModelScope)

    fun onConfirmDeleteTransactionsClick(
        transactions: List<TransactionListModel.Data>,
        dialogKey: String
    ) {
        transactionsAnalytics.trackConfirmDeleteTransactionsClick()
        viewModelScope.launch {
            transactionsInteractor.deleteTransactions(
                transactions = transactions.map { it.transaction }
            )
            viewAction = TransactionsAction.RefreshTransactions
        }
        viewAction = TransactionsAction.CloseDeleteTransactionDialog(dialogKey)
        viewAction = TransactionsAction.UnselectAllItems
    }

    fun onCancelDeletingTransactionsClick(dialogKey: String) {
        transactionsAnalytics.trackCancelDeletingTransactionsClick()
        viewAction = TransactionsAction.CloseDeleteTransactionDialog(dialogKey)
        viewAction = TransactionsAction.UnselectAllItems
    }

    fun onTransactionClick(transaction: Transaction) {
        transactionsAnalytics.trackTransactionClick(transaction)
        viewAction = TransactionsAction.OpenTransactionDetailScreen(transaction)
    }

    fun onCloseAppBarMenuClick() {
        transactionsAnalytics.trackCloseAppBarMenuClick()
        viewAction = TransactionsAction.UnselectAllItems
    }

    fun onDeleteTransactionsClick(selectedItemsCount: Int) {
        transactionsAnalytics.trackDeleteTransactionsClick()
        viewAction = TransactionsAction.ShowDeleteDialog(selectedItemsCount)
    }

    fun onAddTransactionClick() {
        transactionsAnalytics.onAddTransactionClick()
        viewAction = TransactionsAction.OpenAddTransactionScreen
    }
}