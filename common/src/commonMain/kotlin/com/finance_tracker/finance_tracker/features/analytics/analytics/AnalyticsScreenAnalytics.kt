package com.finance_tracker.finance_tracker.features.analytics.analytics

import com.finance_tracker.finance_tracker.core.analytics.BaseAnalytics
import com.finance_tracker.finance_tracker.core.common.date.models.YearMonth
import com.finance_tracker.finance_tracker.domain.models.TransactionType
import com.finance_tracker.finance_tracker.features.analytics.PeriodChip

class AnalyticsScreenAnalytics: BaseAnalytics() {

    override val screenName = "AnalyticsScreen"

    fun trackTrendEvent(
        transactionType: TransactionType,
        periodChip: PeriodChip
    ) {
        trackEvent(
            eventName = "TrendView",
            properties = mapOf(
                "type" to transactionType.analyticsName,
                "period" to periodChip.analyticsName
            )
        )
    }

    fun trackTxsByCategory(
        transactionType: TransactionType,
        yearMonth: YearMonth
    ) {
        trackEvent(
            eventName = "TxsByCategoryView",
            properties = mapOf(
                "type" to transactionType.analyticsName,
                "year" to yearMonth.year,
                "month" to yearMonth.month.name.lowercase(),
            )
        )
    }
}