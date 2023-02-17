package com.finance_tracker.finance_tracker.features.email_auth.enter_email

sealed interface EnterEmailAction {

    object Close: EnterEmailAction

    data class OpenEnterOtpScreen(
        val email: String
    ): EnterEmailAction
}