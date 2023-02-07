package com.finance_tracker.finance_tracker.presentation.home.analytics

import com.finance_tracker.finance_tracker.domain.models.Account
import com.finance_tracker.finance_tracker.domain.models.Transaction

class HomeAnalytics: com.finance_tracker.finance_tracker.core.analytics.BaseAnalytics() {

    override val screenName = "HomeScreen"

    fun trackMyAccountsClick() {
        trackClick(eventName = "MyAccounts")
    }

    fun trackAddAccountClick() {
        trackClick(eventName = "AddAccount")
    }

    fun trackAccountClick(account: Account) {
        trackClick(
            eventName = "Account",
            properties = mapOf(
                "name" to account.name,
                "type" to account.type.analyticsName,
            )
        )
    }

    fun trackLastTransactionsClick() {
        trackClick(eventName = "LastTransactions")
    }

    fun trackTransactionClick(transaction: Transaction) {
        trackClick(
            eventName = "Transaction",
            properties = mapOf(
                "account_name" to transaction.account.name,
                "type" to transaction.type.analyticsName
            )
        )
    }

    fun trackSettingsClick() {
        trackClick(eventName = "Settings")
    }
}