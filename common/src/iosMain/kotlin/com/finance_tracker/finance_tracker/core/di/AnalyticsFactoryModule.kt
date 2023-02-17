package com.finance_tracker.finance_tracker.core.di

import com.finance_tracker.finance_tracker.core.analytics.AnalyticsTracker
import com.finance_tracker.finance_tracker.core.analytics.IosAnalyticsTracker
import com.finance_tracker.finance_tracker.data.settings.AnalyticsSettings
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual class AnalyticsFactoryModule {

    actual fun create(): Module = module {
        singleOf<AnalyticsTracker, AnalyticsSettings>(::IosAnalyticsTracker)
    }
}