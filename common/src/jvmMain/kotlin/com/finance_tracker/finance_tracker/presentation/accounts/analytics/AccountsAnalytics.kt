package com.finance_tracker.finance_tracker.presentation.accounts.analytics

import com.finance_tracker.finance_tracker.domain.models.Account

class AccountsAnalytics: com.finance_tracker.finance_tracker.core.analytics.BaseAnalytics() {

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