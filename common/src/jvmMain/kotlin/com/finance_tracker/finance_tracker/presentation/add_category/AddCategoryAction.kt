package com.finance_tracker.finance_tracker.presentation.add_category

import com.finance_tracker.finance_tracker.core.common.view_models.BaseLocalsStorage

sealed interface AddCategoryAction {
    object CloseScreen: AddCategoryAction
}

fun handleAction(
    action: AddCategoryAction,
    baseLocalsStorage: BaseLocalsStorage
) {
    val rootController = baseLocalsStorage.rootController
    when (action) {
        AddCategoryAction.CloseScreen -> {
            rootController.popBackStack()
        }
    }
}