package com.finance_tracker.finance_tracker.domain.models

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.datetime.LocalDateTime

sealed interface TransactionListModel {

    val id: Any
        get() = when (this) {
            is Data -> {
                transaction.id ?: -1
            }
            is DateAndDayTotal -> {
                dateTime.toString()
            }
        }

    data class DateAndDayTotal(
        val dateTime: LocalDateTime
    ): TransactionListModel

    data class Data(
        val transaction: Transaction,
        val isSelected: MutableStateFlow<Boolean> = MutableStateFlow(false)
    ): TransactionListModel
}