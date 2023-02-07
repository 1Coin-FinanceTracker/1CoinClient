package com.finance_tracker.finance_tracker.core.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
internal fun Modifier.`if`(
    condition: Boolean,
    then: @Composable Modifier.() -> Modifier
): Modifier {
    return if (condition) {
        then()
    } else {
        this
    }
}

expect fun Modifier.statusBarsPadding(): Modifier

expect fun Modifier.navigationBarsPadding(): Modifier

expect fun Modifier.imePadding(): Modifier