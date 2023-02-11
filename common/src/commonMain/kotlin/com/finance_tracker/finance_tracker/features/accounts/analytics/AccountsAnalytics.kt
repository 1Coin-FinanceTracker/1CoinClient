package com.finance_tracker.finance_tracker.features.accounts.analytics

import com.finance_tracker.finance_tracker.core.analytics.BaseAnalytics
import com.finance_tracker.finance_tracker.domain.models.Account

class AccountsAnalytics: BaseAnalytics() {

    override val screenName = "AccountsScreen"

    fun trackAccountClick(account: Account) {
        trackClick(
            eventName = "Account",
            properties = mapOf(
                "name" to account.name,
                "type" to account.type.analyticsName,
            )
        )
    }

    fun trackAddAccountClick() {
        trackClick(eventName = "AddAccount")
    }
}