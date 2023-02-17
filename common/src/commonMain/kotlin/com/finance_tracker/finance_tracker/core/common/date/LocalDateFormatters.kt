package com.finance_tracker.finance_tracker.core.common.date

import com.finance_tracker.finance_tracker.core.common.zeroPrefixed
import kotlinx.datetime.LocalDateTime

enum class Format {
    Iso,
    ShortDate,
    FullDate
}

fun LocalDateTime.format(format: Format): String {
    return when (format) {
        Format.Iso -> toIsoFormat()
        Format.ShortDate -> toShortDateFormat()
        Format.FullDate -> toFullDateFormat()
    }
}

// Format: "yyyy-MM-ddTHH:mm:ssZ"
private fun LocalDateTime.toIsoFormat(): String {
    return "${year}-${monthNumber.zeroPrefixed(2)}-" +
            "${dayOfMonth.zeroPrefixed(2)}T" +
            "${hour.zeroPrefixed(2)}:" +
            "${minute.zeroPrefixed(2)}:" +
            "${second.zeroPrefixed(2)}Z"
}

// Format: "dd.MM"
private fun LocalDateTime.toShortDateFormat(): String {
    return "${dayOfMonth.zeroPrefixed(2)}." +
            monthNumber.zeroPrefixed(2)
}

// Format: "dd.MM.yyyy"
private fun LocalDateTime.toFullDateFormat(): String {
    return "${dayOfMonth.zeroPrefixed(2)}." +
            "${monthNumber.zeroPrefixed(2)}.$year"
}