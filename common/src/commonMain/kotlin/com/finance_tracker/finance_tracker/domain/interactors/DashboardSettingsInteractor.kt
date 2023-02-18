package com.finance_tracker.finance_tracker.domain.interactors

import com.finance_tracker.finance_tracker.data.repositories.DashboardSettingsRepository
import com.finance_tracker.finance_tracker.domain.models.DashboardWidgetData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DashboardSettingsInteractor(
    private val dashboardSettingsRepository: DashboardSettingsRepository
) {

    suspend fun updateDashboardItems() {
        dashboardSettingsRepository.updateDashboardItems()
    }

    suspend fun getDashboardWidgets(): List<DashboardWidgetData> {
        return dashboardSettingsRepository.getDashboardWidgets()
    }

    fun changeDashboardWidgetEnabledState(
        dashboardWidgetData: DashboardWidgetData
    ) {
        dashboardSettingsRepository.saveDashboardWidgetState(
            dashboardWidgetData = dashboardWidgetData,
            isEnabled = !dashboardWidgetData.isEnabled
        )
    }

    fun getActiveDashboardWidgets(): Flow<List<DashboardWidgetData>> {
        return dashboardSettingsRepository.getDashboardWidgetsFlow()
            .map { items -> items.filter { it.isEnabled } }
    }

    suspend fun updateItemsPosition(
        itemId1: Int, newPosition1: Int,
        itemId2: Int, newPosition2: Int
    ) {
        return dashboardSettingsRepository.updateItemsPosition(
            itemId1, newPosition1,
            itemId2, newPosition2
        )
    }
}