package com.finance_tracker.finance_tracker.features.add_category

import com.finance_tracker.finance_tracker.core.common.view_models.BaseViewModel
import com.finance_tracker.finance_tracker.domain.interactors.CategoriesInteractor
import com.finance_tracker.finance_tracker.domain.models.TransactionType
import com.finance_tracker.finance_tracker.features.add_category.analytics.AddCategoryAnalytics
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val MaxCategoryLength = 28

class AddCategoryViewModel(
    private val categoriesInteractor: CategoriesInteractor,
    private val addCategoryAnalytics: AddCategoryAnalytics,
    private val screenParams: AddCategoryScreenParams
): BaseViewModel<AddCategoryAction>() {

    private val _icons = MutableStateFlow(List(63) { "ic_category_${it + 1}" })
    val icons = _icons.asStateFlow()

    private val _chosenIcon = MutableStateFlow(
        screenParams.category?.iconId ?: icons.value.first()
    )
    val chosenIcon = _chosenIcon.asStateFlow()

    private val _categoryName = MutableStateFlow(screenParams.category?.name.orEmpty())
    val categoryName = _categoryName.asStateFlow()

    private val transactionType = screenParams.transactionType
    val isEditMode = screenParams.category != null

    init {
        addCategoryAnalytics.trackScreenOpen()
    }

    fun onIconChoose(icon: String) {
        _chosenIcon.value = icon
    }

    private suspend fun addCategory(
        categoryName: String,
        categoryIcon: String,
        transactionType: TransactionType
    ) {
        categoriesInteractor.insertCategory(
            categoryName = categoryName,
            categoryIcon = categoryIcon,
            isExpense = transactionType == TransactionType.Expense,
            isIncome = transactionType == TransactionType.Income
        )
    }

    fun setCategoryName(name: String) {
        if (name.length <= MaxCategoryLength) {
            _categoryName.value = name
        }
    }

    fun onBackClick() {
        addCategoryAnalytics.onBackClick()
        viewAction = AddCategoryAction.CloseScreen
    }

    fun addOrUpdateCategory() {
        val newCategoryName = categoryName.value
        addCategoryAnalytics.onAddOrEditCategoryClick(
            transactionType = transactionType,
            iconName = chosenIcon.value,
            categoryName = newCategoryName,
            isEdit = isEditMode,
        )

        if (newCategoryName.isBlank()) return

        viewModelScope.launch {
            if (isEditMode) {
                val categoryId = screenParams.category?.id ?: return@launch
                categoriesInteractor.updateCategory(
                    id = categoryId,
                    name = newCategoryName,
                    iconId = chosenIcon.value
                )
            } else {
                addCategory(
                    categoryName = newCategoryName,
                    categoryIcon = chosenIcon.value,
                    transactionType = transactionType
                )
            }
            viewAction = AddCategoryAction.CloseScreen
        }
    }
}