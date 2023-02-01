package com.finance_tracker.finance_tracker.presentation.select_currency.views

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.AppBarIcon
import com.finance_tracker.finance_tracker.core.ui.CoinTopAppBar
import com.finance_tracker.finance_tracker.core.ui.rememberVectorPainter
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun SelectCurrencyTopBar(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onSearchClick: () -> Unit = {},
) {

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