package com.finance_tracker.finance_tracker.core.common

import io.github.aakira.napier.Napier

inline fun <R> runSafeCatching(block: () -> R): Result<R> {
    return runCatching(block)
        .onFailure { Napier.e("runSafeCatching", it) }
}