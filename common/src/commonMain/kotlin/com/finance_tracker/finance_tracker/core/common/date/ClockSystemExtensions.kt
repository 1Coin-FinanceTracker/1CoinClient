package com.finance_tracker.finance_tracker.core.common.date

import com.finance_tracker.finance_tracker.core.common.date.models.YearMonth
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun Clock.System.currentYearMonth(): YearMonth {
    val instant = now().toLocalDateTime(TimeZone.currentSystemDefault())
    return YearMonth(
        year = instant.year,
        month = instant.month
    )
}

fun Clock.System.currentLocalDateTime(): LocalDateTime {
    return now().toLocalDateTime(TimeZone.currentSystemDefault())
}

fun Clock.System.currentLocalDate(): LocalDate {
    return currentLocalDateTime().date
}