package com.finance_tracker.finance_tracker.features.email_auth.enter_otp.di

import com.finance_tracker.finance_tracker.features.email_auth.enter_otp.EnterOtpViewModel
import com.finance_tracker.finance_tracker.features.email_auth.enter_otp.analytics.EnterOtpAnalytics
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

internal val enterOtpModule = module {
    factoryOf(::EnterOtpViewModel)
    factoryOf(::EnterOtpAnalytics)
}