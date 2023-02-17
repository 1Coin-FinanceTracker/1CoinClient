package com.finance_tracker.finance_tracker.features.email_auth.enter_email

import com.finance_tracker.finance_tracker.core.common.view_models.BaseLocalsStorage
import com.finance_tracker.finance_tracker.core.navigtion.main.MainNavigationTree
import ru.alexgladkov.odyssey.compose.extensions.push

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