package com.finance_tracker.finance_tracker.core.navigation.tabs

import androidx.compose.runtime.Composable
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.features.accounts.AccountsScreen
import com.finance_tracker.finance_tracker.features.analytics.AnalyticsScreen
import com.finance_tracker.finance_tracker.features.home.HomeScreen
import com.finance_tracker.finance_tracker.features.tabs_navigation.tabs.AccountsTab
import com.finance_tracker.finance_tracker.features.tabs_navigation.tabs.AnalyticsTab
import com.finance_tracker.finance_tracker.features.tabs_navigation.tabs.HomeTab
import com.finance_tracker.finance_tracker.features.tabs_navigation.tabs.TransactionsTab
import com.finance_tracker.finance_tracker.features.transactions.TransactionsScreen
import ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation.TabItem

enum class TabsNavigationTree(
    val tab: TabItem,
    val screen: @Composable () -> Unit
) {
    Home(
        tab = HomeTab(),
        screen = {
            CoinTheme {
                HomeScreen()
            }
        }
    ),
    Transactions(
        tab = TransactionsTab(),
        screen = {
            CoinTheme {
                TransactionsScreen()
            }
        }
    ),
    Accounts(
        tab = AccountsTab(),
        screen = {
            CoinTheme {
                AccountsScreen()
            }
        }
    ),
    Analytics(
        tab = AnalyticsTab(),
        screen = {
            CoinTheme {
                AnalyticsScreen()
            }
        }
    )
}