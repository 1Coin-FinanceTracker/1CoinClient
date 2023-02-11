package com.finance_tracker.finance_tracker.features.welcome

sealed interface WelcomeAction {

    object OpenGoogleAuthScreen: WelcomeAction
    object OpenEmailAuthScreen: WelcomeAction
    object OpenMainScreen: WelcomeAction
}