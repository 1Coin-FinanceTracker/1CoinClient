package com.finance_tracker.finance_tracker.features.accounts

import com.finance_tracker.finance_tracker.core.common.view_models.BaseLocalsStorage
import com.finance_tracker.finance_tracker.core.navigtion.main.MainNavigationTree
import ru.alexgladkov.odyssey.compose.extensions.push

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