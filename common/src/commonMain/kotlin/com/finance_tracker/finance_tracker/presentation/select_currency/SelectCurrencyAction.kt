package com.finance_tracker.finance_tracker.presentation.select_currency

import com.finance_tracker.finance_tracker.core.common.view_models.BaseLocalsStorage

sealed interface SelectCurrencyAction {
    object Close: SelectCurrencyAction
}

fun handleAction(
    action: SelectCurrencyAction,
    baseLocalsStorage: BaseLocalsStorage,
) {
    val rootController = baseLocalsStorage.rootController

    when (action) {
        SelectCurrencyAction.Close -> { rootController.popBackStack() }
    }
}