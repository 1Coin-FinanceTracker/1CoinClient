package com.finance_tracker.finance_tracker.presentation.dashboard_settings

import com.finance_tracker.finance_tracker.core.common.view_models.BaseLocalsStorage

sealed interface DashboardSettingsAction {
    object Close:
        com.finance_tracker.finance_tracker.presentation.dashboard_settings.DashboardSettingsAction
}

fun handleAction(
    action: com.finance_tracker.finance_tracker.presentation.dashboard_settings.DashboardSettingsAction,
    baseLocalsStorage: BaseLocalsStorage
) {
    when (action) {
        com.finance_tracker.finance_tracker.presentation.dashboard_settings.DashboardSettingsAction.Close -> {
            baseLocalsStorage.rootController.popBackStack()
        }
    }
}