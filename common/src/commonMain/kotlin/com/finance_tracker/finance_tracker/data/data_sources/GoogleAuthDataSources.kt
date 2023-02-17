package com.finance_tracker.finance_tracker.data.data_sources

import com.finance_tracker.finance_tracker.core.common.Context

expect class GoogleAuthDataSource(context: Context) {

    fun isUserSignedIn(): Boolean
}