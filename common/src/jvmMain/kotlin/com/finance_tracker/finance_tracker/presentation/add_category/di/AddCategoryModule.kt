package com.finance_tracker.finance_tracker.presentation.add_category.di

import com.finance_tracker.finance_tracker.presentation.add_category.AddCategoryViewModel
import com.finance_tracker.finance_tracker.presentation.add_category.analytics.AddCategoryAnalytics
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

internal val addCategoryModule = module {
    factoryOf(::AddCategoryViewModel)
    factoryOf(::AddCategoryAnalytics)
}