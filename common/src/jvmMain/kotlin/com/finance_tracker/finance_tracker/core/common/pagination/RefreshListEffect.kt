package com.finance_tracker.finance_tracker.core.common.pagination

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import app.cash.paging.LoadStateLoading
import kotlinx.coroutines.delay

@Suppress("MagicNumber")
@Composable
internal fun AutoRefreshList(lazyPagingItems: LazyPagingItems<*>) {
    LaunchedEffect(Unit) {
        while (lazyPagingItems.loadState.refresh == LoadStateLoading) {
            delay(10)
        }
        lazyPagingItems.refresh()
    }
}