package com.finance_tracker.finance_tracker.core.common

import androidx.compose.runtime.Composable

@Composable
@Suppress("ComposableParametersOrdering")
expect fun BackHandler(enabled: Boolean, onBack: () -> Unit)

@Composable
@Suppress("ComposableParametersOrdering")
fun BackHandler(onBack: () -> Unit) {
    BackHandler(enabled = true, onBack = onBack)
}