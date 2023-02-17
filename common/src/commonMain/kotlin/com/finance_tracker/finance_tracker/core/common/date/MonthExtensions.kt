package com.finance_tracker.finance_tracker.core.common.date

import com.finance_tracker.finance_tracker.core.common.Context
import com.finance_tracker.finance_tracker.core.common.localizedString
import com.finance_tracker.finance_tracker.core.common.strings.getMonthNameStringResBy
import kotlinx.datetime.Month
import kotlinx.datetime.number

fun Month.next(): Month {
    return Month(number = number + 1)
}

fun Month.localizedName(context: Context): String {
    return getMonthNameStringResBy(number).localizedString(context)
}