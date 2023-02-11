package com.finance_tracker.finance_tracker.features.welcome

import com.finance_tracker.finance_tracker.core.common.view_models.BaseViewModel
import com.finance_tracker.finance_tracker.domain.interactors.AuthInteractor
import com.finance_tracker.finance_tracker.features.welcome.analytics.WelcomeAnalytics
import io.github.aakira.napier.Napier
import kotlinx.coroutines.launch

class WelcomeViewModel(
    private val welcomeAnalytics: WelcomeAnalytics,
    private val authInteractor: AuthInteractor
): BaseViewModel<WelcomeAction>() {

    init {
        welcomeAnalytics.trackScreenOpen()
    }

    fun onContinueWithGoogleClick() {
        welcomeAnalytics.trackContinueWithGoogleClick()
    }

    fun onGoogleSignInSuccess(token: String) {
        viewModelScope.launch {
            authInteractor.sendGoogleAuthToken(token)
        }
    }

    fun onGoogleSignInError(exception: Exception) {
        Napier.e(message = "GoogleSignInError", throwable = exception)
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