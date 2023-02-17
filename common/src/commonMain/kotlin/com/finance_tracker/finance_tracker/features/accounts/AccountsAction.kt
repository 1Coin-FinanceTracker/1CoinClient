package com.finance_tracker.finance_tracker.features.accounts

import com.finance_tracker.finance_tracker.domain.models.Account

sealed interface AccountsAction {
    data class OpenEditAccountScreen(
        val account: Account
    ): AccountsAction
    object OpenAddAccountScreen:
        AccountsAction
}