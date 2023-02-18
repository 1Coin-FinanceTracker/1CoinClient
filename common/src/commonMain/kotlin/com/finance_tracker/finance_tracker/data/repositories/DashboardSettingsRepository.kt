package com.finance_tracker.finance_tracker.data.repositories

import com.finance_tracker.finance_tracker.domain.models.DashboardWidgetData
import com.financetracker.financetracker.data.DashboardWidgetEntity
import com.financetracker.financetracker.data.DashboardWidgetEntityQueries
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class DashboardSettingsRepository(
    private val dashboardWidgetEntityQueries: DashboardWidgetEntityQueries
) {

    suspend fun updateDashboardItems() {
        withContext(Dispatchers.Default) {
            dashboardWidgetEntityQueries.transaction {
                val savedItemsIds = dashboardWidgetEntityQueries.getDashboardWidgets()
                    .executeAsList()
                    .map { it.id.toInt() }
                val actualItems = DashboardWidgetData.default
                val actualItemsIds = actualItems.map { it.id }
                val deleteCandidateIds = savedItemsIds
                    .filter { it !in actualItemsIds }
                deleteCandidateIds.forEach { id ->
                    dashboardWidgetEntityQueries.deleteDashboardWidgetById(id.toLong())
                }
                actualItems.forEach { actualItem ->
                    dashboardWidgetEntityQueries.insertNewDashboardWidget(
                        id = actualItem.id.toLong(),
                        isEnabled = actualItem.isEnabled,
                        position = actualItem.position
                    )
                }
            }
        }
    }

    fun saveDashboardWidgetState(
        dashboardWidgetData: DashboardWidgetData,
        isEnabled: Boolean
    ) {
        dashboardWidgetEntityQueries.updateDashboardWidgetEnabled(
            id = dashboardWidgetData.id.toLong(),
            isEnabled = isEnabled
        )
    }

    fun getDashboardWidgetsFlow(): Flow<List<DashboardWidgetData>> {
        return dashboardWidgetEntityQueries.getDashboardWidgets()
            .asFlow()
            .mapToList(Dispatchers.Default)
            .map { items ->
                getDashboardWidgets(
                    dashboardItemsEntityMap = items.associateBy { it.id.toInt() }
                )
            }
            .map { items -> items.sortedBy { it.position } }
    }

    suspend fun getDashboardWidgets(): List<DashboardWidgetData> {
        return getDashboardWidgetsFlow().first()
    }

    private fun getDashboardWidgets(
        dashboardItemsEntityMap: Map<Int, DashboardWidgetEntity>
    ): List<DashboardWidgetData> {
        return DashboardWidgetData.default.map {
            val item = dashboardItemsEntityMap[it.id] ?: return@map it
            return@map it.copy(
                isEnabled = item.isEnabled,
                position = item.position
            )
        }
    }

    suspend fun updateItemsPosition(
        itemId1: Int, newPosition1: Int,
        itemId2: Int, newPosition2: Int
    ) {
        withContext(Dispatchers.Default) {
            dashboardWidgetEntityQueries.transaction {
                dashboardWidgetEntityQueries.updateDashboardWidgetPosition(
                    position = newPosition1,
                    id = itemId1.toLong()
                )
                dashboardWidgetEntityQueries.updateDashboardWidgetPosition(
                    position = newPosition2,
                    id = itemId2.toLong()
                )
            }
        }
    }
}