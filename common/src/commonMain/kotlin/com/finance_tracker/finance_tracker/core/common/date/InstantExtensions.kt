package com.finance_tracker.finance_tracker.core.common.date

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus

fun Instant.minus(unit: DateTimeUnit): Instant {
    return minus(unit, TimeZone.currentSystemDefault())
}