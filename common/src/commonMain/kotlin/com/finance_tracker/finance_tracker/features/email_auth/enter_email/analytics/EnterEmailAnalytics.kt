package com.finance_tracker.finance_tracker.features.email_auth.enter_email.analytics

import com.finance_tracker.finance_tracker.core.analytics.BaseAnalytics

class EnterEmailAnalytics: BaseAnalytics() {

    override val screenName = "EnterEmailScreen"

    fun trackContinueClick() {
        trackClick(eventName = "Continue")
    }
}