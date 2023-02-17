package com.finance_tracker.finance_tracker.features.add_category.analytics

import com.finance_tracker.finance_tracker.core.analytics.BaseAnalytics
import com.finance_tracker.finance_tracker.domain.models.TransactionType

class AddCategoryAnalytics: BaseAnalytics() {

    override val screenName = "AddCategoryScreen"

    fun onBackClick() {
        trackClick(eventName = "Back")
    }

    fun onAddOrEditCategoryClick(
        transactionType: TransactionType,
        iconName: String,
        categoryName: String,
        isEdit: Boolean,
    ) {
        trackClick(
            eventName = if (isEdit) {
                "Edit"
            } else {
                "Add"
            },
            properties = mapOf(
                "type" to transactionType.analyticsName,
                "icon_name" to iconName,
                "category_name" to categoryName
            )
        )
    }
}