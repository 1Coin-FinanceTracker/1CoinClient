package com.finance_tracker.finance_tracker.features.dashboard_settings

import com.finance_tracker.finance_tracker.core.common.view_models.BaseLocalsStorage

fun handleAction(
    action: DashboardSettingsAction,
    baseLocalsStorage: BaseLocalsStorage
) {
    when (action) {
        DashboardSettingsAction.Close -> {
            baseLocalsStorage.rootController.popBackStack()
        }
    }
}