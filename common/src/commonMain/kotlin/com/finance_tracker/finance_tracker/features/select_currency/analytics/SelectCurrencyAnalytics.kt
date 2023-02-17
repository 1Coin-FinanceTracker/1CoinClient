package com.finance_tracker.finance_tracker.features.select_currency.analytics

import com.finance_tracker.finance_tracker.core.analytics.BaseAnalytics
import com.finance_tracker.finance_tracker.domain.models.Currency

class SelectCurrencyAnalytics: BaseAnalytics() {

    override val screenName = "SelectCurrencyScreen"

    fun trackCurrencySelect(currency: Currency) {
        trackSelect(
            eventName = "CurrencySelection",
            properties = mapOf(
                "currency" to currency
            )
        )
    }

    fun trackSearchClick() {
        trackClick(eventName = "SearchCurrency")
    }
}