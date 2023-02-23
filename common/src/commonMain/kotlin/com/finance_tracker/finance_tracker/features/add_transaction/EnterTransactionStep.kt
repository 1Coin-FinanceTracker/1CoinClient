package com.finance_tracker.finance_tracker.features.add_transaction

import com.finance_tracker.finance_tracker.MR
import dev.icerock.moko.parcelize.Parcelable
import dev.icerock.moko.parcelize.Parcelize
import dev.icerock.moko.resources.StringResource

@Parcelize
enum class EnterTransactionStep(val textId: StringResource? = null): Parcelable {
    PrimaryAccount(MR.strings.add_transaction_stage_account),
    SecondaryAccount(MR.strings.add_transaction_stage_account),
    Category(MR.strings.add_transaction_stage_category),
    PrimaryAmount,
    SecondaryAmount;
}