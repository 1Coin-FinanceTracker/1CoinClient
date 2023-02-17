package com.finance_tracker.finance_tracker.features.tabs_navigation.analytics

import com.finance_tracker.finance_tracker.core.analytics.BaseAnalytics

class TabsNavigationAnalytics: BaseAnalytics() {

    override val screenName = "TabsNavigationScreen"

    fun trackTabClick(tabName: String) {
        trackClick(eventName = tabName)
    }

    fun trackAddTransactionClick() {
        trackClick(eventName = "AddTransaction")
    }
}