package com.finance_tracker.finance_tracker.features.analytics

import com.finance_tracker.finance_tracker.MR
import dev.icerock.moko.resources.StringResource

enum class PeriodChip(
    val textId: StringResource,
    val analyticsName: String
) {
    Week(
        textId = MR.strings.week,
        analyticsName = "Week"
    ),
    Month(
        textId = MR.strings.month,
        analyticsName = "Month"
    ),
    Year(
        textId = MR.strings.year,
        analyticsName = "Year"
    )
}