package com.finance_tracker.finance_tracker.core.navigation.main

import com.finance_tracker.finance_tracker.core.navigation.tabs.tabsNavigationGraph
import com.finance_tracker.finance_tracker.core.navigtion.main.MainNavigationTree
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
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
        CoinTheme {
            AddTransactionScreen(
                params = it as? AddTransactionScreenParams ?: AddTransactionScreenParams()
            )
        }
    }

    screen(MainNavigationTree.CategorySettings.name) {
        CoinTheme {
            CategorySettingsScreen()
        }
    }

    screen(MainNavigationTree.AddAccount.name) { account ->
        CoinTheme {
            AddAccountScreen(
                account = account as? Account ?: Account.EMPTY
            )
        }
    }

    screen(MainNavigationTree.AddCategory.name) { params ->
        CoinTheme {
            AddCategoryScreen(
                addCategoryScreenParams = params as AddCategoryScreenParams
            )
        }
    }

    screen(MainNavigationTree.DetailAccount.name) { params ->
        CoinTheme {
            DetailAccountScreen(account = params as Account)
        }
    }

    screen(MainNavigationTree.DashboardSettings.name) {
        CoinTheme {
            DashboardSettingsScreen()
        }
    }

    screen(MainNavigationTree.Settings.name) {
        CoinTheme {
            SettingsScreen()
        }
    }

    screen(MainNavigationTree.Welcome.name) {
        CoinTheme {
            WelcomeScreen()
        }
    }

    screen(MainNavigationTree.EnterEmail.name) {
        CoinTheme {
            EnterEmailScreen()
        }
    }

    screen(MainNavigationTree.EnterOtp.name) { email ->
        CoinTheme {
            EnterOtpScreen(email as String)
        }
    }

    screen(MainNavigationTree.SelectCurrency.name) {
        CoinTheme {
            SelectCurrencyScreen()
        }
    }

    tabsNavigationGraph()
}