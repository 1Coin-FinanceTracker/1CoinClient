package com.finance_tracker.finance_tracker.features.dashboard_settings

import com.finance_tracker.finance_tracker.core.common.view_models.BaseViewModel
import com.finance_tracker.finance_tracker.domain.interactors.DashboardSettingsInteractor
import com.finance_tracker.finance_tracker.domain.models.DashboardWidgetData
import com.finance_tracker.finance_tracker.features.dashboard_settings.analytics.DashboardSettingsAnalytics
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DashboardSettingsViewModel(
    private val dashboardSettingsAnalytics: DashboardSettingsAnalytics,
    private val dashboardSettingsInteractor: DashboardSettingsInteractor
): BaseViewModel<DashboardSettingsAction>() {

    private val _dashboardWidgets = MutableStateFlow<ImmutableList<DashboardWidgetData>>(persistentListOf())
    val dashboardWidgets = _dashboardWidgets.asStateFlow()

    init {
        dashboardSettingsAnalytics.trackScreenOpen()
        loadDashboardWidgets()
    }

    private fun loadDashboardWidgets() {
        viewModelScope.launch {
            _dashboardWidgets.value = dashboardSettingsInteractor.getDashboardWidgets()
                .toImmutableList()
        }
    }

    fun onBackClick() {
        dashboardSettingsAnalytics.trackBackClick()
        viewAction = DashboardSettingsAction.Close
    }

    fun onDashboardItemClick(dashboardWidgetData: DashboardWidgetData) {
        dashboardSettingsAnalytics.trackDashboardItemClick(dashboardWidgetData)
        dashboardSettingsInteractor.changeDashboardWidgetEnabledState(dashboardWidgetData)

        _dashboardWidgets.update {
            val oldList = it.toMutableList()
            val itemPosition = oldList.indexOfFirst { item ->
                item.id == dashboardWidgetData.id
            }
            oldList[itemPosition] = dashboardWidgetData.copy(
                isEnabled = !dashboardWidgetData.isEnabled
            )
            oldList.toImmutableList()
        }
    }

    fun onDashboardItemPositionChange(fromPosition: Int, toPosition: Int) {
        val fromItem = dashboardWidgets.value[fromPosition]
        val toItem = dashboardWidgets.value[toPosition]

        dashboardSettingsAnalytics.trackSwapItems(
            from = fromPosition,
            to = toPosition,
            dashboardWidgetData = fromItem
        )

        _dashboardWidgets.update {
            val newList = dashboardWidgets.value.toMutableList()
            newList[fromPosition] = toItem
            newList[toPosition] = fromItem
            newList.toImmutableList()
        }
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