package com.finance_tracker.finance_tracker.presentation.select_currency.views

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.AppBarIcon
import com.finance_tracker.finance_tracker.core.ui.CoinTopAppBar
import com.finance_tracker.finance_tracker.core.ui.SearchCurrencyTextField
import com.finance_tracker.finance_tracker.core.ui.rememberVectorPainter
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun SelectCurrencyTopBar(
    focusRequester: FocusRequester,
    modifier: Modifier = Modifier,
    onBackClickWhileSearch: () -> Unit = {},
    onBackClick: () -> Unit = {},
    onSearchClick: () -> Unit = {},
    isSearchActive: Boolean = false,
    searchText: String = "",
    onTextChange: (String) -> Unit = {},
) {
    if (isSearchActive) {
        CoinTopAppBar(
            modifier = modifier,
            appBarHeight = 56.dp,
            navigationIcon = {
                AppBarIcon(
                    painter = rememberVectorPainter("ic_arrow_back"),
                    onClick = onBackClickWhileSearch,
                )
            },
            title = {
                SearchCurrencyTextField(
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    text = searchText,
                    onTextChange = onTextChange,
                )
            }
        )
    } else {
        CoinTopAppBar(
            modifier = modifier,
            appBarHeight = 56.dp,
            navigationIcon = {
                AppBarIcon(
                    painter = rememberVectorPainter("ic_arrow_back"),
                    onClick = onBackClick,
                )
            },
            title = {
                Text(
                    text = stringResource(MR.strings.currency_screen_topbar_text),
                    style = CoinTheme.typography.h4
                )
            },
            actions = {
                AppBarIcon(
                    painter = rememberVectorPainter("ic_search"),
                    onClick = onSearchClick,
                )
            }
        )
    }
}