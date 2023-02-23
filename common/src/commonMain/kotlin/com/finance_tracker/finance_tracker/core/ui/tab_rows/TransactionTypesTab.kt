package com.finance_tracker.finance_tracker.core.ui.tab_rows

import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.domain.models.TransactionType
import dev.icerock.moko.resources.StringResource

enum class TransactionTypeTab(
    val textId: StringResource,
    val analyticsName: String
) {
    Expense(
        textId = MR.strings.tab_expense,
        analyticsName = "Expense"
    ),
    Income(
        textId = MR.strings.tab_income,
        analyticsName = "Income"
    ),
    Transfer(
        textId = MR.strings.tab_transfer,
        analyticsName = "Transfer"
    ),
}

fun TransactionTypeTab.toTransactionType(): TransactionType {
    return when (this) {
        TransactionTypeTab.Expense -> TransactionType.Expense
        TransactionTypeTab.Income -> TransactionType.Income
        TransactionTypeTab.Transfer -> TransactionType.Transfer
    }
}

fun TransactionType.toTransactionTypeTab(): TransactionTypeTab {
    return when (this) {
        TransactionType.Expense -> TransactionTypeTab.Expense
        TransactionType.Income -> TransactionTypeTab.Income
        TransactionType.Transfer -> TransactionTypeTab.Transfer
    }
}