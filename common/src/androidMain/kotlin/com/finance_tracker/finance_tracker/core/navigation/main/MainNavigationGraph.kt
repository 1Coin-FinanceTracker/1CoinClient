package com.finance_tracker.finance_tracker.core.navigation.main

import com.finance_tracker.finance_tracker.core.navigation.tabs.tabsNavigationGraph
import com.finance_tracker.finance_tracker.core.navigtion.main.MainNavigationTree
import com.finance_tracker.finance_tracker.domain.models.Account
import com.finance_tracker.finance_tracker.features.add_account.AddAccountScreen
import com.finance_tracker.finance_tracker.features.add_category.AddCategoryScreen
import com.finance_tracker.finance_tracker.features.add_category.AddCategoryScreenParams
import com.finance_tracker.finance_tracker.features.add_transaction.AddTransactionScreen
import com.finance_tracker.finance_tracker.features.add_transaction.AddTransactionScreenParams
import com.finance_tracker.finance_tracker.features.category_settings.CategorySettingsScreen
import com.finance_tracker.finance_tracker.features.dashboard_settings.DashboardSettingsScreen
import com.finance_tracker.finance_tracker.features.detail_account.DetailAccountScreen
import com.finance_tracker.finance_tracker.features.email_auth.enter_email.EnterEmailScreen
import com.finance_tracker.finance_tracker.features.email_auth.enter_otp.EnterOtpScreen
import com.finance_tracker.finance_tracker.features.select_currency.SelectCurrencyScreen
import com.finance_tracker.finance_tracker.features.settings.SettingsScreen
import com.finance_tracker.finance_tracker.features.welcome.WelcomeScreen
import ru.alexgladkov.odyssey.compose.extensions.screen
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder

fun RootComposeBuilder.navigationGraph() {

    screen(MainNavigationTree.AddTransaction.name) {
        AddTransactionScreen(
            params = it as? AddTransactionScreenParams ?: AddTransactionScreenParams()
        )
    }

    screen(MainNavigationTree.CategorySettings.name) {
        CategorySettingsScreen()
    }

    screen(MainNavigationTree.AddAccount.name) { account ->
        AddAccountScreen(
            account = account as? Account ?: Account.EMPTY
        )
    }

    screen(MainNavigationTree.AddCategory.name) { params ->
        AddCategoryScreen(
            addCategoryScreenParams = params as AddCategoryScreenParams
        )
    }

    screen(MainNavigationTree.DetailAccount.name) { params ->
        DetailAccountScreen(account = params as Account)
    }

    screen(MainNavigationTree.DashboardSettings.name) {
        DashboardSettingsScreen()
    }

    screen(MainNavigationTree.Settings.name) {
        SettingsScreen()
    }

    screen(MainNavigationTree.Welcome.name) {
        WelcomeScreen()
    }

    screen(MainNavigationTree.EnterEmail.name) {
        EnterEmailScreen()
    }

    screen(MainNavigationTree.EnterOtp.name) { email ->
        EnterOtpScreen(email as String)
    }

    screen(MainNavigationTree.SelectCurrency.name) {
        SelectCurrencyScreen()
    }

    tabsNavigationGraph()
}