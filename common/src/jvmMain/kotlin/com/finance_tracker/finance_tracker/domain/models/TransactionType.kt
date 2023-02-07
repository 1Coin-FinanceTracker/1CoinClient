package com.finance_tracker.finance_tracker.domain.models

enum class TransactionType(
    val analyticsName: String
) {
    Expense(analyticsName = "Expense"),
    Income(analyticsName = "Income")
}