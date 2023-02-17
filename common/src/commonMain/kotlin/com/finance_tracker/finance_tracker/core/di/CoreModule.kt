package com.finance_tracker.finance_tracker.core.di

import com.finance_tracker.finance_tracker.core.common.AppInitializer
import com.finance_tracker.finance_tracker.core.common.logger.LoggerInitializer
import com.finance_tracker.finance_tracker.core.feature_flags.FeaturesManager
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val coreModule = module {
    factoryOf(::provideJsonFactory)
    singleOf(::AppInitializer)
    singleOf(::LoggerInitializer)
    singleOf(::FeaturesManager)
}

private fun provideJsonFactory(): Json {
    return Json {
        prettyPrint = false
        isLenient = true
        ignoreUnknownKeys = true
        coerceInputValues = true
        encodeDefaults = true
    }
}