package com.finance_tracker.finance_tracker.core.common

import com.finance_tracker.finance_tracker.AppDatabase
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

suspend fun <T> AppDatabase.suspendTransaction(block: suspend () -> T): T = coroutineScope {
    val result: Deferred<T> = transactionWithResult {
        async {
            block()
        }
    }
    result.await()
}