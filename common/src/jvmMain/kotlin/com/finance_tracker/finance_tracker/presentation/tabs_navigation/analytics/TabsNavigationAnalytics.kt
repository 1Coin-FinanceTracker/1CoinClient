package com.finance_tracker.finance_tracker.presentation.tabs_navigation.analytics

import com.finance_tracker.finance_tracker.presentation.tabs_navigation.tabs.AccountsTab
import com.finance_tracker.finance_tracker.presentation.tabs_navigation.tabs.AnalyticsTab
import com.finance_tracker.finance_tracker.presentation.tabs_navigation.tabs.HomeTab
import com.finance_tracker.finance_tracker.presentation.tabs_navigation.tabs.TransactionsTab
import ru.alexgladkov.odyssey.compose.controllers.TabNavigationModel

class TabsNavigationAnalytics: com.finance_tracker.finance_tracker.core.analytics.BaseAnalytics() {

    override val screenName = "TabsNavigationScreen"

    fun trackTabClick(item: TabNavigationModel) {
        val eventName = when (item.tabInfo.tabItem) {
            is HomeTab -> "TabHome"
            is TransactionsTab -> "TabTransactions"
            is AccountsTab -> "TabAccounts"
            is AnalyticsTab -> "TabAnalytics"
            else -> null
        } ?: return

        trackClick(eventName = eventName)
    }

    fun trackAddTransactionClick() {
        trackClick(eventName = "AddTransaction")
    }
}