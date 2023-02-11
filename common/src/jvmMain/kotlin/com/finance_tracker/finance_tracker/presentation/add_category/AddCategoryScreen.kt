package com.finance_tracker.finance_tracker.presentation.add_category

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.common.StoredViewModel
import com.finance_tracker.finance_tracker.core.common.view_models.watchViewActions
import com.finance_tracker.finance_tracker.core.ui.CoinOutlinedTextField
import com.finance_tracker.finance_tracker.core.ui.PrimaryButton
import com.finance_tracker.finance_tracker.domain.models.TransactionType
import com.finance_tracker.finance_tracker.presentation.add_category.views.AddCategoryAppBar
import com.finance_tracker.finance_tracker.presentation.add_category.views.ChooseIconButton
import com.finance_tracker.finance_tracker.presentation.common.formatters.Category
import dev.icerock.moko.resources.compose.stringResource
import org.koin.core.parameter.parametersOf

private const val MinCategoryNameLength = 2

data class AddCategoryScreenParams(
    val transactionType: TransactionType,
    val category: Category? = null
)

@Composable
internal fun AddCategoryScreen(
    addCategoryScreenParams: AddCategoryScreenParams
) {
    StoredViewModel<AddCategoryViewModel>(
        parameters = { parametersOf(addCategoryScreenParams) }
    ) { viewModel ->

        val focusRequester = remember { FocusRequester() }

        LaunchedEffect(Unit) {
            if (addCategoryScreenParams.category == null) {
                focusRequester.requestFocus()
            }
        }

        viewModel.watchViewActions { action, baseLocalsStorage ->
            handleAction(
                action = action,
                baseLocalsStorage = baseLocalsStorage
            )
        }

        Column(modifier = Modifier.fillMaxSize()) {
            AddCategoryAppBar(
                onBackClick = viewModel::onBackClick,
                textValue = if (viewModel.isEditMode) {
                    if (addCategoryScreenParams.transactionType == TransactionType.Expense) {
                        MR.strings.expense_category
                    } else {
                        MR.strings.income_category
                    }
                } else {
                    if (addCategoryScreenParams.transactionType == TransactionType.Expense) {
                        MR.strings.new_expense_category
                    } else {
                        MR.strings.new_income_category
                    }
                }
            )

            val chosenIcon by viewModel.chosenIcon.collectAsState()
            val newCategoryName by viewModel.categoryName.collectAsState()

            Row(
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        top = 24.dp
                    ),
            ) {

                val icons by viewModel.icons.collectAsState()
                val focusManager = LocalFocusManager.current
                ChooseIconButton(
                    chosenIcon = chosenIcon,
                    icons = icons,
                    onIconChoose = viewModel::onIconChoose,
                    onClick = { focusManager.clearFocus() }
                )

                CoinOutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .padding(
                            start = 16.dp,
                            end = 16.dp
                        )
                        .focusRequester(focusRequester),
                    value = newCategoryName,
                    onValueChange = {
                        viewModel.setCategoryName(it)
                    },
                )
            }

            PrimaryButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 16.dp,
                        start = 16.dp,
                        end = 16.dp,
                    ),
                text = if (viewModel.isEditMode) {
                    stringResource(MR.strings.edit_account_btn_save)
                } else {
                    stringResource(MR.strings.new_account_btn_add)
                },
                enabled = newCategoryName.length >= MinCategoryNameLength,
                onClick = viewModel::addOrUpdateCategory
            )
        }
    }
}