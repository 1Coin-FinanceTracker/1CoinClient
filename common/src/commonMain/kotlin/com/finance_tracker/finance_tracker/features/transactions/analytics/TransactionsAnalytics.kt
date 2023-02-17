package com.finance_tracker.finance_tracker.features.transactions.analytics

import com.finance_tracker.finance_tracker.core.analytics.BaseAnalytics
import com.finance_tracker.finance_tracker.domain.models.Transaction

class TransactionsAnalytics: BaseAnalytics() {

    override val screenName = "TransactionsScreen"

    fun trackTransactionClick(transaction: Transaction) {
        trackClick(
            eventName = "Transaction",
            properties = mapOf(
                "transaction_type" to transaction.type.analyticsName
            )
        )
    }

    fun trackCancelDeletingTransactionsClick() {
        trackClick(eventName = "CancelDeletingTransactions")
    }

    fun trackConfirmDeleteTransactionsClick() {
        trackClick(eventName = "ConfirmDeleteTransactions")
    }

    fun trackCloseAppBarMenuClick() {
        trackClick(eventName = "CloseAppBarMenu")
    }

    fun trackDeleteTransactionsClick() {
        trackClick(eventName = "DeleteTransactions")
    }

    fun onAddTransactionClick() {
        trackClick(eventName = "AddTransaction")
    }
}