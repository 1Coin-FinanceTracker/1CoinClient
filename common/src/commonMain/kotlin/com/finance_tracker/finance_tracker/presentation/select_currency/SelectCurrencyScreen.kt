package com.finance_tracker.finance_tracker.presentation.select_currency

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.LocalFixedInsets
import com.finance_tracker.finance_tracker.core.common.StoredViewModel
import com.finance_tracker.finance_tracker.core.common.view_models.watchViewActions
import com.finance_tracker.finance_tracker.presentation.select_currency.views.CurrencyItem
import com.finance_tracker.finance_tracker.presentation.select_currency.views.SelectCurrencyTopBar
import java.util.Currency

@Composable
internal fun SelectCurrencyScreen() {
    StoredViewModel<SelectCurrencyViewModel> { viewModel ->

        viewModel.watchViewActions { action, baseLocalsStorage ->
            handleAction(
                action = action,
                baseLocalsStorage = baseLocalsStorage,
            )
        }

        Column {
            SelectCurrencyTopBar(
                onBackClick = viewModel::onBackClick,
                onSearchClick = viewModel::onSearchClick
            )

            val currencies by viewModel.currencies.collectAsState()
            val primaryCurrency by viewModel.primaryCurrencyFlow.collectAsState()

            val navigationBarsHeight = LocalFixedInsets.current.navigationBarsHeight

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(
                    top = 12.dp,
                    bottom = 12.dp + navigationBarsHeight
                )
            ) {
                items(currencies) { currency ->
                    CurrencyItem(
                        currency = currency,
                        currencyCode = currency.code,
                        currencyName = Currency.getInstance(currency.code).displayName,
                        onCurrencyClick = viewModel::onCurrencySelect,
                        isCurrencySelected = currency == primaryCurrency
                    )
                }
            }
        }
    }
}