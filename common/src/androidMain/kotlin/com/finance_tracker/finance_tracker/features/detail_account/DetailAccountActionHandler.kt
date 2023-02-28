package com.finance_tracker.finance_tracker.features.detail_account

import com.finance_tracker.finance_tracker.core.common.view_models.BaseLocalsStorage
import com.finance_tracker.finance_tracker.core.navigtion.main.MainNavigationTree
import com.finance_tracker.finance_tracker.features.add_transaction.AddTransactionScreenParams
import ru.alexgladkov.odyssey.compose.extensions.push

fun handleAction(
    action: DetailAccountAction,
    baseLocalsStorage: BaseLocalsStorage
) {
    val rootController = baseLocalsStorage.rootController
    when (action) {
        DetailAccountAction.CloseScreen -> {
            val navController = rootController.findRootController()
            navController.popBackStack()
        }
        is DetailAccountAction.OpenEditAccountScreen -> {
            val navController = rootController.findRootController()
            navController.push(
                screen = MainNavigationTree.AddAccount.name,
                params = action.account
            )
        }
        is DetailAccountAction.OpenEditTransactionScreen -> {
            val navController = rootController.findRootController()
            navController.push(
                screen = MainNavigationTree.AddTransaction.name,
                params = AddTransactionScreenParams(
                    transaction = action.transaction
                )
            )
        }
        is DetailAccountAction.OpenAddTransactionScreen -> {
            val navController = rootController.findRootController()
            navController.push(
                screen = MainNavigationTree.AddTransaction.name,
                params = AddTransactionScreenParams(
                    preselectedAccount = action.account
                )
            )
        }
    }
}