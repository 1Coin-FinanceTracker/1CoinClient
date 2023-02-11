package com.finance_tracker.finance_tracker.features.category_settings

import com.finance_tracker.finance_tracker.core.ui.tab_rows.TransactionTypeTab
import com.finance_tracker.finance_tracker.domain.models.Category
import com.finance_tracker.finance_tracker.domain.models.TransactionType

sealed interface CategorySettingsAction {
    data class OpenAddCategoryScreen(
        val selectedTransactionTypeTab: TransactionTypeTab
    ): CategorySettingsAction

    data class OpenEditCategoryScreen(
        val transactionType: TransactionType,
        val category: Category
    ) : CategorySettingsAction
    object CloseScreen: CategorySettingsAction
    data class OpenDeleteDialog(val category: Category): CategorySettingsAction
    data class DismissDialog(val dialogKey: String): CategorySettingsAction
}