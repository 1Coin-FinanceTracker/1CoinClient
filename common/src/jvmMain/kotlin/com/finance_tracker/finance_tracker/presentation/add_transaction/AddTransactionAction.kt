package com.finance_tracker.finance_tracker.presentation.add_transaction

import com.finance_tracker.finance_tracker.core.common.view_models.BaseLocalsStorage
import com.finance_tracker.finance_tracker.core.navigation.main.MainNavigationTree
import com.finance_tracker.finance_tracker.core.ui.tab_rows.TransactionTypeTab
import com.finance_tracker.finance_tracker.core.ui.tab_rows.toTransactionType
import com.finance_tracker.finance_tracker.presentation.add_category.AddCategoryScreenParams
import ru.alexgladkov.odyssey.compose.extensions.push

sealed interface AddTransactionAction {

    object Close: AddTransactionAction

    data class DismissDialog(
        val dialogKey: String
    ): AddTransactionAction

    object OpenAddAccountScreen: AddTransactionAction

    data class OpenAddCategoryScreen(
        val type: TransactionTypeTab
    ): AddTransactionAction
}

fun handleAction(
    action: AddTransactionAction,
    baseLocalsStorage: BaseLocalsStorage
) {
    val navController = baseLocalsStorage.rootController.findRootController()
    when (action) {
        AddTransactionAction.Close -> {
            navController.popBackStack()
        }
        is AddTransactionAction.DismissDialog -> {
            navController.findModalController().popBackStack(
                key = action.dialogKey,
                animate = false
            )
        }
        AddTransactionAction.OpenAddAccountScreen -> {
            navController.push(MainNavigationTree.AddAccount.name)
        }
        is AddTransactionAction.OpenAddCategoryScreen -> {
            navController.push(
                screen = MainNavigationTree.AddCategory.name,
                params = AddCategoryScreenParams(
                    transactionType = action.type.toTransactionType()
                )
            )
        }
    }
}