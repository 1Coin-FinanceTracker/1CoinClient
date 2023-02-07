package com.finance_tracker.finance_tracker.core.common.date

import com.finance_tracker.finance_tracker.core.common.date.models.YearMonth
import kotlinx.datetime.Month
import kotlinx.datetime.number

private const val MonthCount = 12

@Suppress("UnnecessaryParentheses")
fun YearMonth.minusMonth(value: Int): YearMonth {
    var newYear = year - value / MonthCount
    var newMonthNumber = month.number - (value % MonthCount)
    if (newMonthNumber < 1) {
        newMonthNumber += MonthCount
        newYear -= 1
    }
    return YearMonth(
        year = newYear,
        month = Month(newMonthNumber)
    )
}