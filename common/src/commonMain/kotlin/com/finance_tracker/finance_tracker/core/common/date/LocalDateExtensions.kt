package com.finance_tracker.finance_tracker.core.common.date

import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus

fun LocalDate.isToday(): Boolean {
    val todayDate = Clock.System.currentLocalDate()
    return todayDate.dayOfYear == dayOfYear && todayDate.year == year
}

fun LocalDate.isYesterday(): Boolean {
    val yesterdayCalendar = Clock.System.currentLocalDate().minus(DateTimeUnit.DAY)
    return yesterdayCalendar.year == year &&
            yesterdayCalendar.dayOfYear == dayOfYear
}

fun LocalDate.isCurrentYear(): Boolean {
    val todayDate = Clock.System.currentLocalDate()
    return todayDate.year == year
}