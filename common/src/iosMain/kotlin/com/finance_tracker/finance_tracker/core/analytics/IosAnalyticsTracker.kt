package com.finance_tracker.finance_tracker.core.analytics

import com.finance_tracker.finance_tracker.core.common.Context
import com.finance_tracker.finance_tracker.data.settings.AnalyticsSettings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

@Suppress("UnusedPrivateMember")
class IosAnalyticsTracker(
    analyticsSettings: AnalyticsSettings
) : AnalyticsTracker, CoroutineScope {

    override val coroutineContext: CoroutineContext = SupervisorJob() + Dispatchers.Default

    @Suppress("NotImplementedDeclaration")
    override fun init(context: Context, userId: String) {
        TODO()
    }

    @Suppress("NotImplementedDeclaration")
    override fun setUserProperty(property: String, value: Any) {
        TODO()
    }

    @Suppress("NotImplementedDeclaration")
    override fun track(event: AnalyticsEvent) {
        TODO()
    }
}