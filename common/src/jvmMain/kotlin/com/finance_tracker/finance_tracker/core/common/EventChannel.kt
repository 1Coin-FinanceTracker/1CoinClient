package com.finance_tracker.finance_tracker.core.common

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel

@Suppress("FunctionName")
fun <T> EventChannel(): Channel<T> {
    return Channel(
        capacity = Int.MAX_VALUE,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
}