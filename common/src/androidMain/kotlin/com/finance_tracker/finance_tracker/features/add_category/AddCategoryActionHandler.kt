package com.finance_tracker.finance_tracker.features.add_category

import com.finance_tracker.finance_tracker.core.common.view_models.BaseLocalsStorage

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