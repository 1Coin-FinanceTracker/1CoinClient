package com.finance_tracker.finance_tracker.presentation.dashboard_settings

import com.finance_tracker.finance_tracker.core.common.view_models.BaseViewModel
import com.finance_tracker.finance_tracker.domain.interactors.DashboardSettingsInteractor
import com.finance_tracker.finance_tracker.domain.models.DashboardWidgetData
import com.finance_tracker.finance_tracker.presentation.dashboard_settings.analytics.DashboardSettingsAnalytics
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DashboardSettingsViewModel(
    private val dashboardSettingsAnalytics: DashboardSettingsAnalytics,
    private val dashboardSettingsInteractor: DashboardSettingsInteractor
): BaseViewModel<com.finance_tracker.finance_tracker.presentation.dashboard_settings.DashboardSettingsAction>() {

    val dashboardWidgets = dashboardSettingsInteractor.getDashboardWidgets()
        .stateIn(viewModelScope, started = SharingStarted.Lazily, initialValue = emptyList())

    init {
        dashboardSettingsAnalytics.trackScreenOpen()
    }

    fun onBackClick() {
        dashboardSettingsAnalytics.trackBackClick()
        viewAction = com.finance_tracker.finance_tracker.presentation.dashboard_settings.DashboardSettingsAction.Close
    }

    fun onDashboardItemClick(dashboardWidgetData: DashboardWidgetData) {
        dashboardSettingsAnalytics.trackDashboardItemClick(dashboardWidgetData)
        dashboardSettingsInteractor.changeDashboardWidgetEnabledState(dashboardWidgetData)
    }

    fun onDashboardItemPositionChange(fromPosition: Int, toPosition: Int) {
        val fromItem = dashboardWidgets.value[fromPosition]
        val toItem = dashboardWidgets.value[toPosition]

        dashboardSettingsAnalytics.trackSwapItems(
            from = fromPosition,
            to = toPosition,
            dashboardWidgetData = fromItem
        )

        saveDashboardWidgetsOrder(
            itemId1 = fromItem.id, newPosition1 = toPosition,
            itemId2 = toItem.id, newPosition2 = fromPosition
        )
    }

    private fun saveDashboardWidgetsOrder(
        itemId1: Int, newPosition1: Int,
        itemId2: Int, newPosition2: Int
    ) {
        viewModelScope.launch {
            dashboardSettingsInteractor.updateItemsPosition(
                itemId1, newPosition1,
                itemId2, newPosition2
            )
        }
    }
}