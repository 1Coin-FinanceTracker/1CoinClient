package com.finance_tracker.finance_tracker.core.common.strings

import com.finance_tracker.finance_tracker.MR
import dev.icerock.moko.resources.StringResource

private val months = arrayOf(
    MR.strings.month_1,
    MR.strings.month_2,
    MR.strings.month_3,
    MR.strings.month_4,
    MR.strings.month_5,
    MR.strings.month_6,
    MR.strings.month_7,
    MR.strings.month_8,
    MR.strings.month_9,
    MR.strings.month_10,
    MR.strings.month_11,
    MR.strings.month_12,
)

private val dateMonths = arrayOf(
    MR.strings.date_month_1,
    MR.strings.date_month_2,
    MR.strings.date_month_3,
    MR.strings.date_month_4,
    MR.strings.date_month_5,
    MR.strings.date_month_6,
    MR.strings.date_month_7,
    MR.strings.date_month_8,
    MR.strings.date_month_9,
    MR.strings.date_month_10,
    MR.strings.date_month_11,
    MR.strings.date_month_12,
)

private val weeks = arrayOf(
    MR.strings.week_day_1,
    MR.strings.week_day_2,
    MR.strings.week_day_3,
    MR.strings.week_day_4,
    MR.strings.week_day_5,
    MR.strings.week_day_6,
    MR.strings.week_day_7
)

fun getMonthNameStringResBy(monthNumber: Int): StringResource {
    return months[monthNumber - 1]
}

fun getDateMonthNameStringResBy(monthNumber: Int): StringResource {
    return dateMonths[monthNumber - 1]
}

fun getWeekDayStringRes(dayOfWeek: Int): StringResource {
    return weeks[dayOfWeek - 1]
}