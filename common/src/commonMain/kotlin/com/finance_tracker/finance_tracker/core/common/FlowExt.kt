package com.finance_tracker.finance_tracker.core.common

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

@Suppress("MagicNumber")
inline fun <T1, T2, T3, T4, T5, T6, R> combineExtended(
    flow: Flow<T1>,
    flow2: Flow<T2>,
    flow3: Flow<T3>,
    flow4: Flow<T4>,
    flow5: Flow<T5>,
    flow6: Flow<T6>,
    crossinline transform: suspend (T1, T2, T3, T4, T5, T6) -> R
): Flow<R> = kotlinx.coroutines.flow.combine(
    flow, flow2, flow3, flow4, flow5, flow6
) { args: Array<*> ->
    @Suppress("UNCHECKED_CAST")
    transform(
        args[0] as T1,
        args[1] as T2,
        args[2] as T3,
        args[3] as T4,
        args[4] as T5,
        args[5] as T6,
    )
}

fun <T> Flow<T>.stateIn(
    scope: CoroutineScope,
    initialValue: T
): StateFlow<T> {
    return stateIn(scope, SharingStarted.Lazily, initialValue)
}