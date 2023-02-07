package com.finance_tracker.finance_tracker.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.StoredViewModel
import com.finance_tracker.finance_tracker.core.common.navigationBarsPadding
import com.finance_tracker.finance_tracker.core.common.view_models.watchViewActions
import com.finance_tracker.finance_tracker.core.theme.CoinPaddings
import com.finance_tracker.finance_tracker.core.ui.tab_rows.TransactionTypeTab
import com.finance_tracker.finance_tracker.domain.models.DashboardWidgetData
import com.finance_tracker.finance_tracker.presentation.widgets.AnalyticsByCategoryWidget
import com.finance_tracker.finance_tracker.presentation.widgets.AnalyticsTrendWidget
import com.finance_tracker.finance_tracker.presentation.widgets.LastTransactionsWidget
import com.finance_tracker.finance_tracker.presentation.widgets.MyAccountsWidget

@Composable
internal fun HomeScreen() {
    StoredViewModel<HomeViewModel> { viewModel ->
        val accounts by viewModel.accounts.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.onScreenComposed()
        }

        val accountsLazyListState = rememberLazyListState()
        viewModel.watchViewActions { action, baseLocalsStorage ->
            handleAction(action, baseLocalsStorage, accountsLazyListState)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val totalBalance by viewModel.totalBalance.collectAsState()
            HomeTopBar(
                totalBalance = totalBalance,
                onSettingsClick = viewModel::onSettingsClick
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(top = 12.dp)
            ) {

                val widgets by viewModel.widgets.collectAsState()
                widgets.forEach { widgetItem ->
                    when (widgetItem.type) {
                        DashboardWidgetData.DashboardWidgetType.MyAccounts -> {
                            MyAccountsWidget(
                                accounts = accounts,
                                accountsLazyListState = accountsLazyListState,
                                onAccountClick = viewModel::onAccountClick,
                                onAddAccountClick = viewModel::onAddAccountClick,
                                onClick = viewModel::onMyAccountsClick
                            )
                        }
                        DashboardWidgetData.DashboardWidgetType.ExpenseTrend -> {
                            AnalyticsTrendWidget(
                                trendsAnalyticsDelegate = viewModel.trendsAnalyticsDelegate,
                                transactionTypeTab = TransactionTypeTab.Expense
                            )
                        }
                        DashboardWidgetData.DashboardWidgetType.IncomeTrend -> {
                            AnalyticsTrendWidget(
                                trendsAnalyticsDelegate = viewModel.trendsAnalyticsDelegate,
                                transactionTypeTab = TransactionTypeTab.Income
                            )
                        }
                        DashboardWidgetData.DashboardWidgetType.ExpenseByCategory -> {
                            AnalyticsByCategoryWidget(
                                primaryCurrency = totalBalance.currency,
                                monthTxsByCategoryDelegate = viewModel.monthTxsByCategoryDelegate,
                                selectedTransactionTypeTab = TransactionTypeTab.Expense
                            )
                        }
                        DashboardWidgetData.DashboardWidgetType.IncomeByCategory -> {
                            AnalyticsByCategoryWidget(
                                primaryCurrency = totalBalance.currency,
                                monthTxsByCategoryDelegate = viewModel.monthTxsByCategoryDelegate,
                                selectedTransactionTypeTab = TransactionTypeTab.Income
                            )
                        }
                        DashboardWidgetData.DashboardWidgetType.LastTransactions -> {
                            val lastTransactions by viewModel.lastTransactions.collectAsState()
                            LastTransactionsWidget(
                                lastTransactions = lastTransactions,
                                onClick = viewModel::onLastTransactionsClick,
                                onTransactionClick = viewModel::onTransactionClick
                            )
                        }
                    }
                }

                Spacer(
                    modifier = Modifier
                        .padding(bottom = CoinPaddings.bottomNavigationBar)
                        .navigationBarsPadding()
                )
            }
        }
    }
}