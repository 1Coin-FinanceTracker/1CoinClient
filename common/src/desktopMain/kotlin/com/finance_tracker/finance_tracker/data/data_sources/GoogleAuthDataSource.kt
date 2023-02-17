package com.finance_tracker.finance_tracker.data.data_sources

import com.finance_tracker.finance_tracker.core.common.Context

actual class GoogleAuthDataSource actual constructor(context: Context) {

    actual fun isUserSignedIn(): Boolean = false
}