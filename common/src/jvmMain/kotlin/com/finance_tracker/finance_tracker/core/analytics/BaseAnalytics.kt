package com.finance_tracker.finance_tracker.core.analytics

import com.finance_tracker.finance_tracker.core.common.getKoin

abstract class BaseAnalytics {

    private val analyticsTracker: AnalyticsTracker by lazy { getKoin().get() }
    protected abstract val screenName: String

    fun trackClick(
        eventName: String,
        properties: Map<String, Any?> = mapOf()
    ) {
        analyticsTracker.track(
            AnalyticsEvent.Click(
                source = screenName,
                clickName = eventName,
                properties = properties
            )
        )
    }

    fun trackSelect(
        eventName: String,
        properties: Map<String, Any?> = mapOf()
    ) {
        analyticsTracker.track(
            AnalyticsEvent.Select(
                source = screenName,
                selectName = eventName,
                properties = properties
            )
        )
    }

    fun trackEvent(
        eventName: String,
        properties: Map<String, Any> = mapOf()
    ) {
        analyticsTracker.track(
            AnalyticsEvent.Event(
                source = screenName,
                eventName = eventName,
                properties = properties
            )
        )
    }

    fun trackScreenOpen(
        properties: Map<String, Any> = mapOf()
    ) {
        analyticsTracker.track(
            AnalyticsEvent.Open(
                screenName = screenName,
                properties = properties
            )
        )
    }

    fun trackBackClick() {
        trackClick(eventName = "Back")
    }
}