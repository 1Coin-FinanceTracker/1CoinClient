package com.finance_tracker.finance_tracker.core.analytics

import com.finance_tracker.finance_tracker.core.common.Context

interface AnalyticsTracker {

    fun init(context: Context, userId: String)

    fun track(event: AnalyticsEvent)

    fun setUserProperty(property: String, value: Any)

    companion object {
        const val ANONYM_USER_ID = "anonym"
    }
}
