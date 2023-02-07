package com.finance_tracker.finance_tracker.presentation.accounts

import com.finance_tracker.finance_tracker.core.common.view_models.BaseLocalsStorage
import com.finance_tracker.finance_tracker.core.navigation.main.MainNavigationTree
import com.finance_tracker.finance_tracker.domain.models.Account
import ru.alexgladkov.odyssey.compose.extensions.push

sealed interface AccountsAction {
    data class OpenEditAccountScreen(
        val account: Account
    ): AccountsAction
    object OpenAddAccountScreen:
        AccountsAction
}

fun handleAction(
    action: AccountsAction,
    baseLocalsStorage: BaseLocalsStorage
) {
    val rootController = baseLocalsStorage.rootController
    when (action) {
        is AccountsAction.OpenEditAccountScreen -> {
            val navController = rootController.findRootController()
            navController.push(
                screen = MainNavigationTree.DetailAccount.name,
                params = action.account
            )
        }
        AccountsAction.OpenAddAccountScreen -> {
            val navController = rootController.findRootController()
            navController.push(MainNavigationTree.AddAccount.name)
        }
    }
}