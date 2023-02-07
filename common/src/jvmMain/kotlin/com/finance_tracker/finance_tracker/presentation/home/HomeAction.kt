package com.finance_tracker.finance_tracker.presentation.home

import androidx.compose.foundation.lazy.LazyListState
import com.finance_tracker.finance_tracker.core.common.view_models.BaseLocalsStorage
import com.finance_tracker.finance_tracker.core.navigation.main.MainNavigationTree
import com.finance_tracker.finance_tracker.core.navigation.tabs.TabsNavigationTree
import com.finance_tracker.finance_tracker.domain.models.Account
import com.finance_tracker.finance_tracker.domain.models.Transaction
import com.finance_tracker.finance_tracker.presentation.add_transaction.AddTransactionScreenParams
import kotlinx.coroutines.launch
import ru.alexgladkov.odyssey.compose.controllers.MultiStackRootController
import ru.alexgladkov.odyssey.compose.extensions.push

sealed interface HomeAction {
    data class ScrollToItemAccounts(val index: Int): HomeAction
    object ShowSettingsDialog: HomeAction
    object OpenAccountsScreen: HomeAction
    object OpenTransactionsScreen: HomeAction
    data class OpenAccountDetailScreen(
        val account: Account
    ): HomeAction
    object OpenAddAccountScreen: HomeAction
    data class OpenEditTransactionScreen(
        val transaction: Transaction
    ): HomeAction
}

fun handleAction(
    action: HomeAction,
    baseLocalsStorage: BaseLocalsStorage,
    accountsLazyListState: LazyListState
) {
    val rootController = baseLocalsStorage.rootController
    when (action) {
        is HomeAction.ScrollToItemAccounts -> {
            baseLocalsStorage.coroutineScope.launch {
                accountsLazyListState.animateScrollToItem(action.index)
            }
        }
        is HomeAction.OpenAccountDetailScreen -> {
            val navController = rootController.findRootController()
            navController.push(
                screen = MainNavigationTree.DetailAccount.name,
                params = action.account
            )
        }
        HomeAction.OpenAccountsScreen -> {
            val navController = rootController.parentRootController as MultiStackRootController
            navController.switchTab(TabsNavigationTree.Accounts.ordinal)
        }
        HomeAction.OpenAddAccountScreen -> {
            val navController = rootController.findRootController()
            navController.push(
                screen = MainNavigationTree.AddAccount.name,
            )
        }
        is HomeAction.OpenEditTransactionScreen -> {
            val navController = rootController.findRootController()
            navController.push(
                screen = MainNavigationTree.AddTransaction.name,
                params = AddTransactionScreenParams(
                    transaction = action.transaction
                )
            )
        }
        HomeAction.OpenTransactionsScreen -> {
            val navController = rootController.parentRootController as MultiStackRootController
            navController.switchTab(TabsNavigationTree.Transactions.ordinal)
        }
        HomeAction.ShowSettingsDialog -> {
            val navController = rootController.findRootController()
            navController.push(MainNavigationTree.Settings.name)
        }
    }
}