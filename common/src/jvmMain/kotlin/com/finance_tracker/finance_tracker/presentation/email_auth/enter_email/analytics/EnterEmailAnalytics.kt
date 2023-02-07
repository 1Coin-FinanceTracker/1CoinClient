package com.finance_tracker.finance_tracker.presentation.email_auth.enter_email.analytics

class EnterEmailAnalytics: com.finance_tracker.finance_tracker.core.analytics.BaseAnalytics() {

    override val screenName = "EnterEmailScreen"

    fun trackContinueClick() {
        trackClick(eventName = "Continue")
    }
}