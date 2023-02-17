package com.finance_tracker.finance_tracker.features.email_auth.enter_email.di

import com.finance_tracker.finance_tracker.features.email_auth.enter_email.EnterEmailViewModel
import com.finance_tracker.finance_tracker.features.email_auth.enter_email.analytics.EnterEmailAnalytics
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

internal val enterEmailModule = module {
    factoryOf(::EnterEmailViewModel)
    factoryOf(::EnterEmailAnalytics)
}