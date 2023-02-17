package com.finance_tracker.finance_tracker.core.common.math

val Double.negativeSignOrEmpty: String
    get() = if (this < 0.0) "-" else ""
