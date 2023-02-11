package com.finance_tracker.finance_tracker.features.welcome.analytics

import com.finance_tracker.finance_tracker.core.analytics.BaseAnalytics

class WelcomeAnalytics: BaseAnalytics() {

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