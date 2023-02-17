package com.finance_tracker.finance_tracker.core.analytics

sealed class AnalyticsEvent(
    val name: String,
    open val properties: Map<String, Any?> = mapOf()
) {

    open class Open(
        screenName: String,
        override val properties: Map<String, Any?> = mapOf()
    ) : AnalyticsEvent(
        name = "${screenName}_Open"
    )

    open class Click(
        source: String,
        val clickName: String,
        override val properties: Map<String, Any?> = mapOf()
    ) : AnalyticsEvent(
        name = "${source}_${clickName}_Click"
    )

    open class Select(
        source: String,
        val selectName: String,
        override val properties: Map<String, Any?> = mapOf()
    ) : AnalyticsEvent(
        name = "${source}_${selectName}_Select"
    )

    open class Event(
        source: String,
        eventName: String,
        override val properties: Map<String, Any?> = mapOf()
    ) : AnalyticsEvent(
        name = "${source}_${eventName}"
    )
}