package com.finance_tracker.finance_tracker.features.category_settings.analytcis

import com.finance_tracker.finance_tracker.core.analytics.BaseAnalytics
import com.finance_tracker.finance_tracker.core.ui.tab_rows.TransactionTypeTab
import com.finance_tracker.finance_tracker.domain.models.Category

class CategorySettingsAnalytics: BaseAnalytics() {

    override val screenName = "CategorySettingsScreen"

    fun trackTransactionTypeClick(transactionTypeTab: TransactionTypeTab) {
        trackClick(
            eventName = "TransactionTypeChange",
            properties = mapOf(
                "transaction_type" to transactionTypeTab.analyticsName
            )
        )
    }

    fun trackAddCategoryClick() {
        trackClick(eventName = "AddCategory")
    }

    fun trackDeleteCategoryClick(category: Category) {
        trackClick(
            eventName = "DeleteCategory",
            properties = mapOf(
                "name" to category.name
            )
        )
    }

    fun trackConfirmDeleteCategoryClick(category: Category) {
        trackClick(
            eventName = "ConfirmDeleteCategory",
            properties = mapOf(
                "name" to category.name
            )
        )
    }

    fun trackCancelDeleteCategoryClick(category: Category) {
        trackClick(
            eventName = "CancelDeleteCategory",
            properties = mapOf(
                "name" to category.name
            )
        )
    }

    fun trackSwapItems(from: Int, to: Int, category: Category) {
        trackEvent(
            eventName = "CategorySwap",
            properties = mapOf(
                "from" to from,
                "to" to to,
                "category_name" to category.name,
            )
        )
    }

    fun trackCategoryClick(category: Category) {
        trackClick(
            eventName = "Category",
            properties = mapOf(
                "name" to category.name
            )
        )
    }
}