package com.finance_tracker.finance_tracker.features.email_auth.enter_otp.analytics

import com.finance_tracker.finance_tracker.core.analytics.BaseAnalytics

class EnterOtpAnalytics: BaseAnalytics() {

    override val screenName = "EnterOtpScreen"

    fun trackCreateAccountClick() {
        trackClick(eventName = "CreateAccount")
    }

    fun trackRequestCodeAgainClick() {
        trackClick(eventName = "RequestCodeAgain")
    }

}