package com.finance_tracker.finance_tracker.core.navigation.main

import com.finance_tracker.finance_tracker.core.navigation.tabs.tabsNavigationGraph
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.domain.models.Account
import com.finance_tracker.finance_tracker.presentation.add_account.AddAccountScreen
import com.finance_tracker.finance_tracker.presentation.add_category.AddCategoryScreen
import com.finance_tracker.finance_tracker.presentation.add_category.AddCategoryScreenParams
import com.finance_tracker.finance_tracker.presentation.add_transaction.AddTransactionScreen
import com.finance_tracker.finance_tracker.presentation.add_transaction.AddTransactionScreenParams
import com.finance_tracker.finance_tracker.presentation.category_settings.CategorySettingsScreen
import com.finance_tracker.finance_tracker.presentation.dashboard_settings.DashboardSettingsScreen
import com.finance_tracker.finance_tracker.presentation.detail_account.DetailAccountScreen
import com.finance_tracker.finance_tracker.presentation.email_auth.enter_email.EnterEmailScreen
import com.finance_tracker.finance_tracker.presentation.email_auth.enter_otp.EnterOtpScreen
import com.finance_tracker.finance_tracker.presentation.select_currency.SelectCurrencyScreen
import com.finance_tracker.finance_tracker.presentation.settings.SettingsScreen
import com.finance_tracker.finance_tracker.presentation.welcome.WelcomeScreen
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