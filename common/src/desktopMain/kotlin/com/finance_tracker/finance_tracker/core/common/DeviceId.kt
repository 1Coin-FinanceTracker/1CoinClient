package com.finance_tracker.finance_tracker.core.common

import java.util.UUID

actual fun getUniqueDeviceId(context: Context): String {
    return UUID.randomUUID().toString()
}