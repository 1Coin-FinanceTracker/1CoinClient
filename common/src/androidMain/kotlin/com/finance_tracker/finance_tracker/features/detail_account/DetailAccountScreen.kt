package com.finance_tracker.finance_tracker.features.detail_account

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.LocalFixedInsets
import com.finance_tracker.finance_tracker.core.common.StoredViewModel
import com.finance_tracker.finance_tracker.core.common.UpdateSystemBarsConfigEffect
import com.finance_tracker.finance_tracker.core.common.pagination.AutoRefreshList
import com.finance_tracker.finance_tracker.core.common.pagination.collectAsLazyPagingItems
import com.finance_tracker.finance_tracker.core.common.statusBarsPadding
import com.finance_tracker.finance_tracker.core.common.toUIColor
import com.finance_tracker.finance_tracker.core.common.view_models.watchViewActions
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.AppBarIcon
import com.finance_tracker.finance_tracker.core.ui.collapsing_toolbar.CollapsingToolbarScaffold
import com.finance_tracker.finance_tracker.core.ui.collapsing_toolbar.CollapsingToolbarScaffoldScopeInstance.align
import com.finance_tracker.finance_tracker.core.ui.collapsing_toolbar.ScrollStrategy
import com.finance_tracker.finance_tracker.core.ui.collapsing_toolbar.rememberCollapsingToolbarScaffoldState
import com.finance_tracker.finance_tracker.core.ui.rememberVectorPainter
import com.finance_tracker.finance_tracker.core.ui.transactions.CommonTransactionsList
import com.finance_tracker.finance_tracker.domain.models.Account
import com.finance_tracker.finance_tracker.features.detail_account.views.AccountNameText
import com.finance_tracker.finance_tracker.features.detail_account.views.DetailAccountAppBar
import com.finance_tracker.finance_tracker.features.detail_account.views.DetailAccountExpandedAppBar
import com.finance_tracker.finance_tracker.features.detail_account.views.EditButton
import org.koin.core.parameter.parametersOf

@Composable
@Suppress("MagicNumber")
internal fun DetailAccountScreen(
    account: Account
) {
    StoredViewModel<DetailAccountViewModel>(
        parameters = { parametersOf(account) }
    ) { viewModel ->
        UpdateSystemBarsConfigEffect {
            isStatusBarLight = false
        }

        viewModel.watchViewActions { action, baseLocalsStorage ->
            handleAction(action, baseLocalsStorage)
        }

        val accountData by viewModel.accountData.collectAsState()
        val state = rememberCollapsingToolbarScaffoldState()
        CollapsingToolbarScaffold(
            modifier = Modifier.fillMaxSize(),
            state = state,
            scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
            toolbar = {
                DetailAccountAppBar(
                    modifier = Modifier
                        .graphicsLayer {
                            alpha = 1f - state.toolbarState.progress
                        },
                    color = accountData.colorModel.color.toUIColor()
                )
                DetailAccountExpandedAppBar(
                    modifier = Modifier
                        .parallax(0.4f),
                    contentAlpha = state.toolbarState.progress,
                    color = accountData.colorModel.color.toUIColor(),
                    amount = accountData.balance,
                    icon = accountData.icon,
                    onIconClick = viewModel::onIconClick
                )

                AppBarIcon(
                    modifier = Modifier
                        .padding(
                            horizontal = 4.dp,
                            vertical = 6.dp
                        )
                        .statusBarsPadding()
                        .align(Alignment.TopStart),
                    painter = rememberVectorPainter("ic_arrow_back"),
                    onClick = viewModel::onBackClick,
                    tint = CoinTheme.color.white
                )

                AccountNameText(
                    name = accountData.name,
                    state = state
                )

                EditButton(
                    state = state,
                    tint = accountData.colorModel.color.toUIColor(),
                    onClick = viewModel::onEditClick
                )
            }
        ) {
            val transactions = viewModel.paginatedTransactions.collectAsLazyPagingItems()

            AutoRefreshList(transactions)

            val navigationBarsHeight = LocalFixedInsets.current.navigationBarsHeight
            CommonTransactionsList(
                modifier = Modifier
                    .background(CoinTheme.color.background),
                transactions = transactions,
                contentPadding = PaddingValues(
                    bottom = navigationBarsHeight
                ),
                onClick = {
                    viewModel.onTransactionClick(transaction = it.transaction)
                },
                onAddTransactionClick = viewModel::onAddTransactionClick,
                stubHeightAlignment = 0.6f,
            )
        }
    }
}