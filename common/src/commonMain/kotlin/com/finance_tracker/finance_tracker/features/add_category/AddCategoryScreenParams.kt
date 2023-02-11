package com.finance_tracker.finance_tracker.features.add_category

import com.finance_tracker.finance_tracker.domain.models.Category
import com.finance_tracker.finance_tracker.domain.models.TransactionType

data class AddCategoryScreenParams(
    val transactionType: TransactionType,
    val category: Category? = null
)