package com.finance_tracker.finance_tracker.domain.models

import com.finance_tracker.finance_tracker.core.common.toString
import com.finance_tracker.finance_tracker.core.common.transparent
import dev.icerock.moko.graphics.Color

data class TxsByCategoryChart(
    val mainPieces: List<Piece>,
    val allPieces: List<Piece>,
    val total: Amount
) {
    data class Piece(
        val category: Category?,
        val amount: Amount,
        val percentValue: Float,
        val transactionsCount: Int,
    ) {
        @Suppress("DataClassShouldBeImmutable")
        var color: Color = Color.transparent
        val percentage = percentValue.toString(PERCENT_PRECISION)

        companion object {
            const val PERCENT_PRECISION = 0
        }
    }
}