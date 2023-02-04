package com.finance_tracker.finance_tracker.presentation.select_currency.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
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
import com.finance_tracker.finance_tracker.core.ui.AppBarHeight
import com.finance_tracker.finance_tracker.core.ui.AppBarIcon
import com.finance_tracker.finance_tracker.core.ui.CoinTopAppBar
import com.finance_tracker.finance_tracker.core.ui.SearchCurrencyTextField
import com.finance_tracker.finance_tracker.core.ui.rememberVectorPainter
import dev.icerock.moko.resources.compose.stringResource

private const val TopBarAnimDurationMillis = 150

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
    Box(
        modifier = modifier
    ) {
        CoinTopAppBar(
            appBarHeight = AppBarHeight,
            navigationIcon = {
                if (isSearchActive) {
                    AnimatedVisibility(
                        visible = isSearchActive,
                        enter = fadeIn(
                            animationSpec = tween(TopBarAnimDurationMillis)
                        ),
                        exit = fadeOut(
                            animationSpec = tween(TopBarAnimDurationMillis)
                        )
                    ) {
                        AppBarIcon(
                            painter = rememberVectorPainter("ic_arrow_back"),
                            onClick = onBackClickWhileSearch
                        )
                    }
                } else {
                    AnimatedVisibility(
                        visible = !isSearchActive,
                        enter = fadeIn(
                            animationSpec = tween(TopBarAnimDurationMillis)
                        ),
                        exit = fadeOut(
                            animationSpec = tween(TopBarAnimDurationMillis)
                        )
                    ) {
                        AppBarIcon(
                            painter = rememberVectorPainter("ic_arrow_back"),
                            onClick = onBackClick
                        )
                    }
                }
            },
            title = {
                if (isSearchActive) {
                    AnimatedVisibility(
                        visible = isSearchActive,
                        enter = fadeIn(
                            animationSpec = tween(TopBarAnimDurationMillis)
                        ),
                        exit = fadeOut(
                            animationSpec = tween(TopBarAnimDurationMillis)
                        )
                    ) {
                        SearchCurrencyTextField(
                            modifier = Modifier
                                .padding(end = 16.dp)
                                .fillMaxWidth()
                                .focusRequester(focusRequester),
                            text = searchText,
                            onTextChange = onTextChange,
                        )
                    }
                } else {
                    AnimatedVisibility(
                        visible = !isSearchActive,
                        enter = fadeIn(
                            animationSpec = tween(TopBarAnimDurationMillis)
                        ),
                        exit = fadeOut(
                            animationSpec = tween(TopBarAnimDurationMillis)
                        )
                    ) {
                        Text(
                            text = stringResource(MR.strings.currency_screen_topbar_text),
                            style = CoinTheme.typography.h4,
                            color = CoinTheme.color.content,
                        )
                    }
                }
            },
            actions = {
                if (!isSearchActive) {
                    AnimatedVisibility(
                        visible = !isSearchActive,
                        enter = fadeIn(
                            animationSpec = tween(TopBarAnimDurationMillis)
                        ),
                        exit = fadeOut(
                            animationSpec = tween(TopBarAnimDurationMillis)
                        )
                    ) {
                        AppBarIcon(
                            painter = rememberVectorPainter("ic_search"),
                            onClick = onSearchClick,
                        )
                    }
                }
            }
        )
    }
}