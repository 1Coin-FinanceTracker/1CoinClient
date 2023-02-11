package com.finance_tracker.finance_tracker.features.email_auth.enter_otp

import com.finance_tracker.finance_tracker.core.common.view_models.BaseLocalsStorage
import com.finance_tracker.finance_tracker.core.navigtion.main.MainNavigationTree
import ru.alexgladkov.odyssey.compose.extensions.push
import ru.alexgladkov.odyssey.core.LaunchFlag

fun handleAction(
    action: EnterOtpAction,
    baseLocalsStorage: BaseLocalsStorage
) {
    when (action) {
        EnterOtpAction.Close -> {
            baseLocalsStorage.rootController.popBackStack()
        }
        EnterOtpAction.OpenMainScreen -> {
            baseLocalsStorage.rootController.push(
                screen = MainNavigationTree.Main.name,
                launchFlag = LaunchFlag.SingleNewTask
            )
        }
    }
}