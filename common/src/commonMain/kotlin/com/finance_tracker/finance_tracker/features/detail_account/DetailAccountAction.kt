package com.finance_tracker.finance_tracker.features.detail_account

import com.finance_tracker.finance_tracker.domain.models.Account
import com.finance_tracker.finance_tracker.domain.models.Transaction

sealed interface DetailAccountAction {
    object CloseScreen: DetailAccountAction
    data class OpenEditAccountScreen(
        val account: Account
    ): DetailAccountAction
    data class OpenEditTransactionScreen(
        val transaction: Transaction
    ): DetailAccountAction
    data class OpenAddTransactionScreen(
        val account: Account
    ): DetailAccountAction
}