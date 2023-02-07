package com.finance_tracker.finance_tracker.domain.models

import androidx.compose.ui.graphics.Color
import io.github.koalaplot.core.util.toString

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
        var color: Color = Color.Transparent
        val percentage = percentValue.toString(PERCENT_PRECISION)

        companion object {
            const val PERCENT_PRECISION = 0
        }
    }
}