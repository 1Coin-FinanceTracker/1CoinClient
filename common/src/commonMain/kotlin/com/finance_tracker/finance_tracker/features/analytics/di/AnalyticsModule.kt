package com.finance_tracker.finance_tracker.features.analytics.di

import com.finance_tracker.finance_tracker.features.analytics.AnalyticsViewModel
import com.finance_tracker.finance_tracker.features.analytics.analytics.AnalyticsScreenAnalytics
import com.finance_tracker.finance_tracker.features.analytics.delegates.MonthTxsByCategoryDelegate
import com.finance_tracker.finance_tracker.features.analytics.delegates.TrendsAnalyticsDelegate
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

internal val analyticsModule = module {
    factoryOf(::AnalyticsViewModel)
    factoryOf(::TrendsAnalyticsDelegate)
    factoryOf(::MonthTxsByCategoryDelegate)
    factoryOf(::AnalyticsScreenAnalytics)
}