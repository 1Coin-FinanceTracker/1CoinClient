package com.finance_tracker.finance_tracker.features.add_transaction

import com.finance_tracker.finance_tracker.MR
import dev.icerock.moko.parcelize.Parcelable
import dev.icerock.moko.parcelize.Parcelize
import dev.icerock.moko.resources.StringResource

@Parcelize
enum class EnterTransactionStep(val textId: StringResource? = null): Parcelable {
    Account(MR.strings.add_transaction_stage_account),
    Category(MR.strings.add_transaction_stage_category),
    Amount;

    fun previous(): EnterTransactionStep? {
        val steps = values()
        val previousIndex = ordinal - 1
        if (previousIndex < 0) return null

        return steps[previousIndex]
    }

    fun next(): EnterTransactionStep? {
        val steps = values()
        val nextIndex = ordinal + 1
        if (nextIndex >= steps.size) return null

        return steps[nextIndex]
    }
}