package com.finance_tracker.finance_tracker.features.tabs_navigation.di

import com.finance_tracker.finance_tracker.features.tabs_navigation.analytics.TabsNavigationAnalytics
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

internal val tabsNavigationModule = module {
    factoryOf(::TabsNavigationAnalytics)
}