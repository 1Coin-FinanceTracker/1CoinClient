package com.finance_tracker.finance_tracker.presentation.welcome

import com.finance_tracker.finance_tracker.core.common.view_models.BaseLocalsStorage
import com.finance_tracker.finance_tracker.core.navigation.main.MainNavigationTree
import ru.alexgladkov.odyssey.compose.extensions.push
import ru.alexgladkov.odyssey.core.LaunchFlag

sealed interface WelcomeAction {

    object OpenGoogleAuthScreen: WelcomeAction
    object OpenEmailAuthScreen: WelcomeAction
    object OpenMainScreen: WelcomeAction
}

fun handleAction(
    action: WelcomeAction,
    baseLocalsStorage: BaseLocalsStorage
) {
    when (action) {
        WelcomeAction.OpenEmailAuthScreen -> {
            baseLocalsStorage.rootController.push(
                screen = MainNavigationTree.EnterEmail.name
            )
        }
        WelcomeAction.OpenGoogleAuthScreen -> {
            // TODO
        }
        WelcomeAction.OpenMainScreen -> {
            baseLocalsStorage.rootController.push(
                screen = MainNavigationTree.Main.name,
                launchFlag = LaunchFlag.SingleNewTask
            )
        }
    }
}