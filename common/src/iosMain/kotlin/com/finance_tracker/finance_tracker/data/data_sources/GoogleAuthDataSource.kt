package com.finance_tracker.finance_tracker.data.data_sources

import com.finance_tracker.finance_tracker.core.common.Context

@Suppress("UnusedPrivateMember")
actual class GoogleAuthDataSource actual constructor(
    private val context: Context
) {
    @Suppress("NotImplementedDeclaration")
    actual fun isUserSignedIn(): Boolean {
        TODO()
    }
}