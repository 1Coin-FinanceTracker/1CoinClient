package com.finance_tracker.finance_tracker.features.home

import com.finance_tracker.finance_tracker.domain.models.Account
import com.finance_tracker.finance_tracker.domain.models.Transaction

sealed interface HomeAction {
    data class ScrollToItemAccounts(val index: Int): HomeAction
    object ShowSettingsDialog: HomeAction
    object OpenAccountsScreen: HomeAction
    object OpenTransactionsScreen: HomeAction
    data class OpenAccountDetailScreen(
        val account: Account
    ): HomeAction
    object OpenAddAccountScreen: HomeAction
    data class OpenEditTransactionScreen(
        val transaction: Transaction
    ): HomeAction
}