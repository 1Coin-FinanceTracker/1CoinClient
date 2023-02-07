package com.finance_tracker.finance_tracker.presentation.welcome

import com.finance_tracker.finance_tracker.core.common.view_models.BaseViewModel
import com.finance_tracker.finance_tracker.presentation.welcome.analytics.WelcomeAnalytics

class WelcomeViewModel(
    private val welcomeAnalytics: WelcomeAnalytics
): BaseViewModel<WelcomeAction>() {

    init {
        welcomeAnalytics.trackScreenOpen()
    }

    fun onContinueWithGoogleClick() {
        welcomeAnalytics.trackContinueWithGoogleClick()
        viewAction = WelcomeAction.OpenGoogleAuthScreen
    }

    fun onContinueWithEmailClick() {
        welcomeAnalytics.trackContinueWithEmailClick()
        viewAction = WelcomeAction.OpenEmailAuthScreen
    }

    fun onSkipClick() {
        welcomeAnalytics.trackSkipClick()
        viewAction = WelcomeAction.OpenMainScreen
    }
}