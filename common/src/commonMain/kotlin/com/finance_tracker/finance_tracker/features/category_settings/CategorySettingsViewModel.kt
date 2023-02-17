package com.finance_tracker.finance_tracker.features.category_settings

import com.finance_tracker.finance_tracker.core.common.view_models.BaseViewModel
import com.finance_tracker.finance_tracker.core.ui.tab_rows.TransactionTypeTab
import com.finance_tracker.finance_tracker.core.ui.tab_rows.toTransactionType
import com.finance_tracker.finance_tracker.domain.interactors.CategoriesInteractor
import com.finance_tracker.finance_tracker.domain.models.Category
import com.finance_tracker.finance_tracker.features.category_settings.analytcis.CategorySettingsAnalytics
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CategorySettingsViewModel(
    private val categoriesInteractor: CategoriesInteractor,
    private val categorySettingsAnalytics: CategorySettingsAnalytics
): BaseViewModel<CategorySettingsAction>() {

    private val _expenseCategories = MutableStateFlow<List<Category>>(emptyList())
    val expenseCategories = _expenseCategories.asStateFlow()

    private val _incomeCategories = MutableStateFlow<List<Category>>(emptyList())
    val incomeCategories = _incomeCategories.asStateFlow()

    private val _selectedTransactionType = MutableStateFlow(TransactionTypeTab.Expense)
    val selectedTransactionType = _selectedTransactionType.asStateFlow()

    init {
        categorySettingsAnalytics.trackScreenOpen()
    }

    fun onTransactionTypeSelect(transactionType: TransactionTypeTab) {
        categorySettingsAnalytics.trackTransactionTypeClick(transactionType)
        _selectedTransactionType.value = transactionType
    }

    fun onScreenComposed() {
        loadAllCategories()
    }

    private fun loadAllCategories() {
        loadAllExpenseCategories()
        loadAllIncomeCategories()
    }

    private fun loadAllExpenseCategories() {
        viewModelScope.launch {
            _expenseCategories.value = categoriesInteractor.getAllExpenseCategories()
        }
    }

    private fun loadAllIncomeCategories() {
        viewModelScope.launch {
            _incomeCategories.value = categoriesInteractor.getAllIncomeCategories()
        }
    }

    fun swapExpenseCategories(from: Int, to: Int) {
        val fromItem = _expenseCategories.value[from]
        val toItem = _expenseCategories.value[to]
        val newList = _expenseCategories.value.toMutableList()
        newList[from] = toItem
        newList[to] = fromItem

        categorySettingsAnalytics.trackSwapItems(
            from = from,
            to = to,
            category = fromItem
        )

        _expenseCategories.value = newList
        saveCategoriesOrder(
            categoryId1 = fromItem.id, newPosition1 = to,
            categoryId2 = toItem.id, newPosition2 = from
        )
    }

    fun swapIncomeCategories(from: Int, to: Int) {
        val fromItem = _incomeCategories.value[from]
        val toItem = _incomeCategories.value[to]
        val newList = _incomeCategories.value.toMutableList()
        newList[from] = toItem
        newList[to] = fromItem

        categorySettingsAnalytics.trackSwapItems(
            from = from,
            to = to,
            category = fromItem
        )

        _incomeCategories.value = newList
        saveCategoriesOrder(
            categoryId1 = fromItem.id, newPosition1 = to,
            categoryId2 = toItem.id, newPosition2 = from
        )
    }

    private fun saveCategoriesOrder(
        categoryId1: Long, newPosition1: Int,
        categoryId2: Long, newPosition2: Int
    ) {
        viewModelScope.launch {
            categoriesInteractor.updateCategoryPosition(
                categoryId1, newPosition1,
                categoryId2, newPosition2
            )
        }
    }

    fun onConfirmDeleteCategory(category: Category, dialogKey: String) {
        categorySettingsAnalytics.trackConfirmDeleteCategoryClick(category)
        viewModelScope.launch {
            categoriesInteractor.deleteCategoryById(category.id)

            if (_selectedTransactionType.value == TransactionTypeTab.Expense) {
                loadAllExpenseCategories()
            } else {
                loadAllIncomeCategories()
            }
        }
        viewAction = CategorySettingsAction.DismissDialog(dialogKey)
    }

    fun onCancelDeleting(category: Category, dialogKey: String) {
        categorySettingsAnalytics.trackCancelDeleteCategoryClick(category)
        viewAction = CategorySettingsAction.DismissDialog(dialogKey)
    }

    fun onBackClick() {
        categorySettingsAnalytics.trackBackClick()
        viewAction = CategorySettingsAction.CloseScreen
    }

    fun onAddCategoryClick() {
        categorySettingsAnalytics.trackAddCategoryClick()
        viewAction = CategorySettingsAction.OpenAddCategoryScreen(
            selectedTransactionTypeTab = selectedTransactionType.value
        )
    }

    fun onDeleteCategoryClick(category: Category) {
        categorySettingsAnalytics.trackDeleteCategoryClick(category)
        viewAction = CategorySettingsAction.OpenDeleteDialog(category)
    }

    fun onCategoryCardClick(category: Category) {
        categorySettingsAnalytics.trackCategoryClick(category)
        viewAction = CategorySettingsAction.OpenEditCategoryScreen(
            category = category,
            transactionType = selectedTransactionType.value.toTransactionType()
        )
    }
}