package com.finance_tracker.finance_tracker.features.dashboard_settings.analytics

import com.finance_tracker.finance_tracker.core.analytics.BaseAnalytics
import com.finance_tracker.finance_tracker.domain.models.DashboardWidgetData
import com.finance_tracker.finance_tracker.domain.models.getAnalyticsName

class DashboardSettingsAnalytics: BaseAnalytics() {

    override val screenName = "DashboardSettingsScreen"

    fun trackDashboardItemClick(dashboardWidgetData: DashboardWidgetData) {
        trackClick(
            eventName = "DashboardItem",
            properties = mapOf(
                "name" to dashboardWidgetData.getAnalyticsName(),
                "is_enabled" to dashboardWidgetData.isEnabled
            )
        )
    }

    fun trackSwapItems(from: Int, to: Int, dashboardWidgetData: DashboardWidgetData) {
        trackEvent(
            eventName = "WidgetItemSwap",
            properties = mapOf(
                "from" to from,
                "to" to to,
                "name" to dashboardWidgetData.getAnalyticsName(),
            )
        )
    }
}