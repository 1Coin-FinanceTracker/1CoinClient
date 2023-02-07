package com.finance_tracker.finance_tracker.data.database.mappers

import com.finance_tracker.finance_tracker.domain.models.Category
import com.financetracker.financetracker.data.CategoriesEntity

fun CategoriesEntity.categoryToDomainModel(): Category {
    return Category(
        id = id,
        name = name,
        iconId = icon
    )
}