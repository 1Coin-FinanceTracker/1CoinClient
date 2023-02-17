package com.finance_tracker.finance_tracker.core.di

import com.finance_tracker.finance_tracker.features.accounts.di.accountsModule
import com.finance_tracker.finance_tracker.features.add_account.di.addAccountModule
import com.finance_tracker.finance_tracker.features.add_category.di.addCategoryModule
import com.finance_tracker.finance_tracker.features.add_transaction.di.addTransactionModule
import com.finance_tracker.finance_tracker.features.analytics.di.analyticsModule
import com.finance_tracker.finance_tracker.features.category_settings.di.categorySettingsModule
import com.finance_tracker.finance_tracker.features.dashboard_settings.di.dashboardSettingsModule
import com.finance_tracker.finance_tracker.features.detail_account.di.detailAccountModule
import com.finance_tracker.finance_tracker.features.email_auth.enter_email.di.enterEmailModule
import com.finance_tracker.finance_tracker.features.email_auth.enter_otp.di.enterOtpModule
import com.finance_tracker.finance_tracker.features.home.di.homeModule
import com.finance_tracker.finance_tracker.features.select_currency.di.currencyModule
import com.finance_tracker.finance_tracker.features.settings.di.settingsModule
import com.finance_tracker.finance_tracker.features.tabs_navigation.di.tabsNavigationModule
import com.finance_tracker.finance_tracker.features.transactions.di.transactionsModule
import com.finance_tracker.finance_tracker.features.welcome.di.welcomeModule

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