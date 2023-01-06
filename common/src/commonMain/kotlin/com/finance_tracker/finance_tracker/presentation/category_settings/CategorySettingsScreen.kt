package com.finance_tracker.finance_tracker.presentation.category_settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.LazyDragColumn
import com.finance_tracker.finance_tracker.core.common.LocalFixedInsets
import com.finance_tracker.finance_tracker.core.common.StoredViewModel
import com.finance_tracker.finance_tracker.core.common.view_models.watchViewActions
import com.finance_tracker.finance_tracker.core.ui.CategoryCard
import com.finance_tracker.finance_tracker.core.ui.ItemWrapper
import com.finance_tracker.finance_tracker.core.ui.tab_rows.TransactionTypeTab
import com.finance_tracker.finance_tracker.domain.models.Category

private val CategoriesListContentPadding = 16.dp

@Composable
fun CategorySettingsScreen() {
    StoredViewModel<CategorySettingsViewModel> { viewModel ->

        viewModel.watchViewActions { action, baseLocalsStorage ->
            handleAction(
                action = action,
                baseLocalsStorage = baseLocalsStorage,
                onCancelClick = viewModel::onCancelDeleting,
                onConfirmDeleteClick = viewModel::onConfirmDeleteCategory
            )
        }

        LaunchedEffect(Unit) {
            viewModel.onScreenComposed()
        }

        Column(modifier = Modifier.fillMaxSize()) {

            val incomeCategories by viewModel.incomeCategories.collectAsState()
            val expenseCategories by viewModel.expenseCategories.collectAsState()
            val selectedTransactionTypeTab by viewModel.selectedTransactionType.collectAsState()

            CategorySettingsAppBar(
                selectedTransactionTypeTab = selectedTransactionTypeTab,
                onTransactionTypeSelect = viewModel::onTransactionTypeSelect,
                onAddCategoryClick = viewModel::onAddCategoryClick,
                onBackClick = viewModel::onBackClick
            )

            when (selectedTransactionTypeTab) {
                TransactionTypeTab.Expense -> {
                    CategoriesLazyColumn(
                        categories = expenseCategories,
                        onSwap = viewModel::swapExpenseCategories,
                        onCrossDeleteClick = viewModel::onDeleteCategoryClick
                    )
                }
                TransactionTypeTab.Income -> {
                    CategoriesLazyColumn(
                        categories = incomeCategories,
                        onSwap = viewModel::swapIncomeCategories,
                        onCrossDeleteClick = viewModel::onDeleteCategoryClick
                    )
                }
            }
        }
    }
}

@Composable
private fun CategoriesLazyColumn(
    categories: List<Category>,
    onSwap: (Int, Int) -> Unit,
    onCrossDeleteClick: (Category) -> Unit,
) {
    val navigationBarsHeight = LocalFixedInsets.current.navigationBarsHeight
    LazyDragColumn(
        items = categories,
        onSwap = onSwap,
        contentPaddingValues = PaddingValues(
            top = CategoriesListContentPadding,
            start = CategoriesListContentPadding,
            end = CategoriesListContentPadding,
            bottom = CategoriesListContentPadding + navigationBarsHeight
        )
    ) { index, category ->
        ItemWrapper(
            isFirstItem = index == 0,
            isLastItem = index == categories.lastIndex
        ) {
            CategoryCard(
                data = category,
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        top = 8.dp,
                        bottom = 8.dp,
                        end = 16.dp
                    ),
                onCrossDeleteClick = {
                    onCrossDeleteClick.invoke(category)
                }
            )
        }
    }
}
