package com.finance_tracker.finance_tracker.features.category_settings

import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.common.DialogConfigurations
import com.finance_tracker.finance_tracker.core.common.view_models.BaseLocalsStorage
import com.finance_tracker.finance_tracker.core.navigtion.main.MainNavigationTree
import com.finance_tracker.finance_tracker.core.ui.DeleteDialog
import com.finance_tracker.finance_tracker.core.ui.tab_rows.toTransactionType
import com.finance_tracker.finance_tracker.domain.models.Category
import com.finance_tracker.finance_tracker.features.add_category.AddCategoryScreenParams
import dev.icerock.moko.resources.compose.stringResource
import ru.alexgladkov.odyssey.compose.extensions.present
import ru.alexgladkov.odyssey.compose.extensions.push

fun handleAction(
    action: CategorySettingsAction,
    baseLocalsStorage: BaseLocalsStorage,
    onCancelClick: (category: Category, dialogKey: String) -> Unit,
    onConfirmDeleteClick: (category: Category, dialogKey: String) -> Unit,
) {
    val rootController = baseLocalsStorage.rootController
    when (action) {
        is CategorySettingsAction.OpenAddCategoryScreen -> {
            val navController = rootController.findRootController()
            navController.push(
                MainNavigationTree.AddCategory.name,
                params = AddCategoryScreenParams(
                    transactionType = action.selectedTransactionTypeTab.toTransactionType()
                )
            )
        }
        CategorySettingsAction.CloseScreen -> {
            val navController = rootController.findRootController()
            navController.popBackStack()
        }
        is CategorySettingsAction.OpenDeleteDialog -> {
            val modalNavController = rootController.findModalController()
            modalNavController.present(DialogConfigurations.alert) { key ->
                DeleteDialog(
                    titleEntity = stringResource(MR.strings.deleting_category),
                    onCancelClick = { onCancelClick.invoke(action.category, key) },
                    onDeleteClick = { onConfirmDeleteClick.invoke(action.category, key) }
                )
            }
        }
        is CategorySettingsAction.DismissDialog -> {
            val modalNavController = rootController.findModalController()
            modalNavController.popBackStack(action.dialogKey, animate = false)
        }
        is CategorySettingsAction.OpenEditCategoryScreen -> {
            val navController = rootController.findRootController()
            navController.push(
                screen = MainNavigationTree.AddCategory.name,
                params = AddCategoryScreenParams(
                    transactionType = action.transactionType,
                    category = action.category
                )
            )
        }
    }
}