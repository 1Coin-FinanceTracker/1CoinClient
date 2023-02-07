package com.finance_tracker.finance_tracker.presentation.email_auth.enter_email

import com.finance_tracker.finance_tracker.core.common.view_models.BaseLocalsStorage
import com.finance_tracker.finance_tracker.core.navigation.main.MainNavigationTree
import ru.alexgladkov.odyssey.compose.extensions.push

sealed interface EnterEmailAction {

    object Close: EnterEmailAction

    data class OpenEnterOtpScreen(
        val email: String
    ): EnterEmailAction
}

fun handleAction(
    action: EnterEmailAction,
    baseLocalsStorage: BaseLocalsStorage,
) {
    when (action) {
        EnterEmailAction.Close -> {
            baseLocalsStorage.rootController.popBackStack()
        }
        is EnterEmailAction.OpenEnterOtpScreen -> {
            baseLocalsStorage.rootController.push(
                screen = MainNavigationTree.EnterOtp.name,
                params = action.email
            )
        }
    }
}