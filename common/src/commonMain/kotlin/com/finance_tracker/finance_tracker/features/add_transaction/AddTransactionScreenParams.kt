package com.finance_tracker.finance_tracker.features.add_transaction

import com.finance_tracker.finance_tracker.domain.models.Account
import com.finance_tracker.finance_tracker.domain.models.Transaction

data class AddTransactionScreenParams(
    val transaction: Transaction? = null,
    val preselectedAccount: Account? = null
)