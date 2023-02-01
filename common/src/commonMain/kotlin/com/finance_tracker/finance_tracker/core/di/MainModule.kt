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
import com.finance_tracker.finance_tracker.presentation.accounts.di.accountsModule
import com.finance_tracker.finance_tracker.presentation.add_account.di.addAccountModule
import com.finance_tracker.finance_tracker.presentation.add_category.di.addCategoryModule
import com.finance_tracker.finance_tracker.presentation.add_transaction.di.addTransactionModule
import com.finance_tracker.finance_tracker.presentation.analytics.di.analyticsModule
import com.finance_tracker.finance_tracker.presentation.category_settings.di.categorySettingsModule
import com.finance_tracker.finance_tracker.presentation.dashboard_settings.di.dashboardSettingsModule
import com.finance_tracker.finance_tracker.presentation.detail_account.di.detailAccountModule
import com.finance_tracker.finance_tracker.presentation.email_auth.enter_email.di.enterEmailModule
import com.finance_tracker.finance_tracker.presentation.email_auth.enter_otp.di.enterOtpModule
import com.finance_tracker.finance_tracker.presentation.home.di.homeModule
import com.finance_tracker.finance_tracker.presentation.select_currency.di.currencyModule
import com.finance_tracker.finance_tracker.presentation.settings.di.settingsModule
import com.finance_tracker.finance_tracker.presentation.tabs_navigation.di.tabsNavigationModule
import com.finance_tracker.finance_tracker.presentation.transactions.di.transactionsModule
import com.finance_tracker.finance_tracker.presentation.welcome.di.welcomeModule

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

fun featureModules() = listOf(
    accountsModule,
    addAccountModule,
    addCategoryModule,
    addTransactionModule,
    analyticsModule,
    categorySettingsModule,
    detailAccountModule,
    dashboardSettingsModule,
    homeModule,
    tabsNavigationModule,
    transactionsModule,
    settingsModule,
    welcomeModule,
    enterEmailModule,
    enterOtpModule,
    currencyModule,
)