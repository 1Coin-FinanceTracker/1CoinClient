package com.finance_tracker.finance_tracker.presentation.analytics

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import com.finance_tracker.finance_tracker.core.theme.CoinPaddings
import com.finance_tracker.finance_tracker.core.ui.tab_rows.toTransactionType
import com.finance_tracker.finance_tracker.presentation.widgets.AnalyticsByCategoryWidget
import com.finance_tracker.finance_tracker.presentation.widgets.AnalyticsTrendWidget

val PieChartSize = 240.dp
val PieChartLabelSize = 20.dp

@Composable
internal fun AnalyticsScreen() {
    StoredViewModel<AnalyticsViewModel> { viewModel ->

        LaunchedEffect(Unit) {
            viewModel.onScreenComposed()
        }

        Column(modifier = Modifier.fillMaxSize()) {
            val selectedTransactionTypeTab by viewModel.transactionTypeTab.collectAsState()

            LaunchedEffect(selectedTransactionTypeTab) {
                viewModel.trendsAnalyticsDelegate.setSelectedTransactionType(
                    transactionType = selectedTransactionTypeTab.toTransactionType()
                )
                viewModel.monthTxsByCategoryDelegate.setSelectedTransactionType(
                    transactionType = selectedTransactionTypeTab.toTransactionType()
                )
            }

            AnalyticsScreenAppBar(
                selectedTransactionTypeTab = selectedTransactionTypeTab,
                onTransactionTypeSelect = viewModel::onTransactionTypeSelect
            )

            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(top = 12.dp)
            ) {
                val primaryCurrency by viewModel.primaryCurrency.collectAsState()
                AnalyticsByCategoryWidget(
                    primaryCurrency = primaryCurrency,
                    monthTxsByCategoryDelegate = viewModel.monthTxsByCategoryDelegate,
                    selectedTransactionTypeTab = selectedTransactionTypeTab
                )

                AnalyticsTrendWidget(
                    trendsAnalyticsDelegate = viewModel.trendsAnalyticsDelegate,
                    transactionTypeTab = selectedTransactionTypeTab
                )

                Spacer(
                    modifier = Modifier
                        .navigationBarsPadding()
                        .padding(bottom = CoinPaddings.bottomNavigationBar)
                )
            }
        }
    }
}