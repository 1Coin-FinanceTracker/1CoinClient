package com.finance_tracker.finance_tracker.core.di

import com.finance_tracker.finance_tracker.data.data_sources.di.dataSourcesModule
import com.finance_tracker.finance_tracker.data.database.di.DriverFactoryModule
import com.finance_tracker.finance_tracker.data.database.di.coreDatabaseModule
import com.finance_tracker.finance_tracker.data.database.di.module
import com.finance_tracker.finance_tracker.data.network.di.networkModule
import com.finance_tracker.finance_tracker.data.repositories.di.repositoriesModule
import com.finance_tracker.finance_tracker.data.settings.di.SettingsFactoryModule
import com.finance_tracker.finance_tracker.data.settings.di.module
import com.finance_tracker.finance_tracker.data.settings.di.settingsCacheModule
import com.finance_tracker.finance_tracker.domain.di.domainModule

fun commonModules() = listOf(
    coreModule,
    DriverFactoryModule().module,
    AnalyticsFactoryModule().module,
    SettingsFactoryModule().module,
    coreDatabaseModule,
    settingsCacheModule,
    networkModule,
    repositoriesModule,
    domainModule,
    dataSourcesModule
)