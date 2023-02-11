package com.finance_tracker.finance_tracker.domain.models

import com.finance_tracker.finance_tracker.core.common.Context
import com.finance_tracker.finance_tracker.core.common.date.currentLocalDateTime
import com.finance_tracker.finance_tracker.presentation.common.formatters.Category
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime

data class Transaction(
    val id: Long? = null,
    val type: TransactionType,
    val account: Account,
    val amount: Amount = Amount.default,
    @Suppress("ConstructorParameterNaming")
    val _category: Category? = null,
    val dateTime: LocalDateTime,
    val insertionDateTime: LocalDateTime?,
) {

    fun getCategoryOrUncategorized(context: Context): Category {
        return _category ?: Category.empty(context)
    }

    companion object {
        val EMPTY = Transaction(
            id = null,
            type = TransactionType.Expense,
            account = Account.EMPTY,
            amount = Amount.default,
            _category = null,
            dateTime = Clock.System.currentLocalDateTime(),
            insertionDateTime = null
        )
    }
}