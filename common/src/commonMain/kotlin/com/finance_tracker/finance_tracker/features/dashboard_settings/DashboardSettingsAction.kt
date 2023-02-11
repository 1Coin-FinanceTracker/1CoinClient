package com.finance_tracker.finance_tracker.features.dashboard_settings

sealed interface DashboardSettingsAction {
    object Close: DashboardSettingsAction
}