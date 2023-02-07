package com.finance_tracker.finance_tracker.presentation.welcome.analytics

class WelcomeAnalytics: com.finance_tracker.finance_tracker.core.analytics.BaseAnalytics() {

    override val screenName = "WelcomeScreen"

    fun trackContinueWithGoogleClick() {
        trackClick(eventName = "ContinueWithGoogle")
    }

    fun trackContinueWithEmailClick() {
        trackClick(eventName = "ContinueWithEmail")
    }

    fun trackSkipClick() {
        trackClick(eventName = "Skip")
    }
}