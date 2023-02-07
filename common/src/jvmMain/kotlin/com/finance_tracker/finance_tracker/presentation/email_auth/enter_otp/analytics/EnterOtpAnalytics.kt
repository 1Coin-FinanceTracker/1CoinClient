package com.finance_tracker.finance_tracker.presentation.email_auth.enter_otp.analytics

class EnterOtpAnalytics: com.finance_tracker.finance_tracker.core.analytics.BaseAnalytics() {

    override val screenName = "EnterOtpScreen"

    fun trackCreateAccountClick() {
        trackClick(eventName = "CreateAccount")
    }

    fun trackRequestCodeAgainClick() {
        trackClick(eventName = "RequestCodeAgain")
    }

}