package com.finance_tracker.finance_tracker.features.add_transaction

import com.finance_tracker.finance_tracker.core.common.view_models.BaseLocalsStorage
import com.finance_tracker.finance_tracker.core.navigtion.main.MainNavigationTree
import com.finance_tracker.finance_tracker.core.ui.tab_rows.toTransactionType
import com.finance_tracker.finance_tracker.features.add_category.AddCategoryScreenParams
import ru.alexgladkov.odyssey.compose.extensions.push

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