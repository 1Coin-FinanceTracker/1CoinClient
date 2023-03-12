package com.finance_tracker.finance_tracker.features.select_currency

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.LocalFixedInsets
import com.finance_tracker.finance_tracker.core.common.StoredViewModel
import com.finance_tracker.finance_tracker.core.common.imePadding
import com.finance_tracker.finance_tracker.core.common.view_models.watchViewActions
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.features.select_currency.views.CurrencyItem
import com.finance_tracker.finance_tracker.features.select_currency.views.SelectCurrencyTopBar
import java.util.Currency

@Composable
internal fun SelectCurrencyScreen() {
    StoredViewModel<SelectCurrencyViewModel> { viewModel ->

        viewModel.watchViewActions { action, baseLocalsStorage ->
            handleAction(
                action = action,
                baseLocalsStorage = baseLocalsStorage
            )
        }

        val focusRequester = remember { FocusRequester() }

        Column(
            modifier = Modifier
                .background(CoinTheme.color.background)
        ) {

            val searchText by viewModel.searchText.collectAsState()
            val isSearchActive by viewModel.isSearchActive.collectAsState()

            LaunchedEffect(isSearchActive) {
                if (isSearchActive) {
                    focusRequester.requestFocus()
                }
            }

            SelectCurrencyTopBar(
                onBackClick = viewModel::onBackClick,
                onBackClickWhileSearch = viewModel::onSearchBackClick,
                onSearchClick = viewModel::onSearchClick,
                searchText = searchText,
                onTextChange = viewModel::onSearchTextChange,
                isSearchActive = isSearchActive,
                focusRequester = focusRequester,
            )

            val currencies by viewModel.currencies.collectAsState()
            val primaryCurrency by viewModel.primaryCurrencyFlow.collectAsState()

            val navigationBarsHeight = LocalFixedInsets.current.navigationBarsHeight

            LazyColumn(
                modifier = Modifier
                    .imePadding()
                    .fillMaxSize(),
                contentPadding = PaddingValues(
                    top = 12.dp,
                    bottom = 12.dp + navigationBarsHeight
                )
            ) {
                items(currencies.filter { it.code.contains(searchText, ignoreCase = true)
                        || it.symbol.contains(searchText, ignoreCase = true)
                        || Currency.getInstance(it.code).displayName.contains(searchText, ignoreCase = true) })
                { currency ->
                    CurrencyItem(
                        currency = currency,
                        onCurrencyClick = viewModel::onCurrencySelect,
                        isCurrencySelected = currency == primaryCurrency
                    )
                }
            }
        }
    }
}