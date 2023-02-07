package com.finance_tracker.finance_tracker.presentation.analytics.models

import androidx.compose.runtime.Composable

data class TrendBarDetails(
    val title: @Composable () -> String,
    val formattedValue: @Composable () -> String,
    val value: Double
)