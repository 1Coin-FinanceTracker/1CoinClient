package com.finance_tracker.finance_tracker.features.email_auth.enter_otp

sealed interface EnterOtpAction {

    object Close: EnterOtpAction

    object OpenMainScreen: EnterOtpAction
}