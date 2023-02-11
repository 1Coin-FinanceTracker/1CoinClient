package com.finance_tracker.finance_tracker.features.analytics.models

import com.finance_tracker.finance_tracker.core.common.Context
import com.finance_tracker.finance_tracker.core.common.formatters.AmountFormatMode
import com.finance_tracker.finance_tracker.domain.models.Amount

data class TrendBarDetails(
    val title: (context: Context) -> String,
    val provideAmountWithFormat: () -> Pair<Amount, AmountFormatMode>,
    val value: Double
)