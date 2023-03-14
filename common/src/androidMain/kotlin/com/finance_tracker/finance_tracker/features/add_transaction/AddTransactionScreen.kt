package com.finance_tracker.finance_tracker.features.add_transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.common.BackHandler
import com.finance_tracker.finance_tracker.core.common.DialogConfigurations
import com.finance_tracker.finance_tracker.core.common.asSp
import com.finance_tracker.finance_tracker.core.common.date.currentLocalDateTime
import com.finance_tracker.finance_tracker.core.common.`if`
import com.finance_tracker.finance_tracker.core.common.toDateTime
import com.finance_tracker.finance_tracker.core.common.view_models.watchViewActions
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.ComposeScreen
import com.finance_tracker.finance_tracker.core.ui.DeleteDialog
import com.finance_tracker.finance_tracker.core.ui.tab_rows.TransactionTypeTab
import com.finance_tracker.finance_tracker.core.ui.tab_rows.toTransactionType
import com.finance_tracker.finance_tracker.domain.models.Amount
import com.finance_tracker.finance_tracker.domain.models.Transaction
import com.finance_tracker.finance_tracker.features.add_transaction.views.ActionButtonsSection
import com.finance_tracker.finance_tracker.features.add_transaction.views.AmountTextField
import com.finance_tracker.finance_tracker.features.add_transaction.views.CalendarDayView
import com.finance_tracker.finance_tracker.features.add_transaction.views.CategoriesAppBar
import com.finance_tracker.finance_tracker.features.add_transaction.views.StepsEnterTransactionBar
import com.finance_tracker.finance_tracker.features.add_transaction.views.StepsEnterTransactionBarData
import com.finance_tracker.finance_tracker.features.add_transaction.views.enter_transaction_controller.EnterTransactionController
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.datetime.Clock
import org.koin.core.parameter.parametersOf
import ru.alexgladkov.odyssey.compose.controllers.ModalController
import ru.alexgladkov.odyssey.compose.extensions.present
import ru.alexgladkov.odyssey.compose.local.LocalRootController

@Composable
@Suppress("CyclomaticComplexMethod", "LongMethod")
internal fun AddTransactionScreen(
    params: AddTransactionScreenParams
) {
    ComposeScreen<AddTransactionViewModel>(
        parameters = { parametersOf(params) }
    ) { viewModel ->
        val navController = LocalRootController.current.findRootController()
        val modalNavController = navController.findModalController()
        LaunchedEffect(Unit) { viewModel.onScreenComposed() }

        viewModel.watchViewActions { action, baseLocalsStorage ->
            handleAction(action, baseLocalsStorage)
        }

        val selectedTransactionType by viewModel.selectedTransactionType.collectAsState()
        val currentStep by viewModel.currentStep.collectAsState()
        Column(
            modifier = Modifier.fillMaxSize()
                .background(CoinTheme.color.background)
        ) {
            val primaryAmountText by viewModel.primaryAmountText.collectAsState()
            val secondaryAmountText by viewModel.secondaryAmountText.collectAsState()
            val primaryAccountData by viewModel.selectedPrimaryAccount.collectAsState()
            val secondaryAccountData by viewModel.selectedSecondaryAccount.collectAsState()
            val categoryData by viewModel.selectedCategory.collectAsState()
            val localDate by viewModel.selectedDate.collectAsState()
            val primaryCurrencyData by viewModel.primaryCurrency.collectAsState()
            val secondaryCurrencyData by viewModel.secondaryCurrency.collectAsState()
            val primaryAmountDouble = primaryAmountText.toDoubleOrNull() ?: 0.0
            val secondaryAmountDouble = secondaryAmountText.toDoubleOrNull() ?: 0.0
            val currentFlow by viewModel.currentFlow.collectAsState()
            val isAddTransactionEnabled by viewModel.isAddTransactionEnabled.collectAsState()
            val transactionInsertionDate = viewModel.transactionInsertionDate
            val onUpdateTransaction = { fromButtonClick: Boolean ->
                val primaryAccount = primaryAccountData
                val primaryCurrency = primaryCurrencyData
                val isEdit = viewModel.isEditMode

                val insertionDateTime = if (isEdit) {
                    transactionInsertionDate
                } else {
                    Clock.System.currentLocalDateTime()
                }

                var transaction: Transaction? = null
                if (currentFlow == AddTransactionFlow.Transfer) {
                    val secondaryAccount = secondaryAccountData
                    val secondaryCurrency = secondaryCurrencyData

                    @Suppress("ComplexCondition")
                    if (
                        primaryAccount != null &&
                        secondaryAccount != null &&
                        primaryCurrency != null &&
                        secondaryCurrency != null
                    ) {
                        transaction = Transaction(
                            type = selectedTransactionType.toTransactionType(),
                            primaryAccount = primaryAccount,
                            _category = null,
                            primaryAmount = Amount(
                                currency = primaryCurrency,
                                amountValue = primaryAmountDouble
                            ),
                            secondaryAmount = Amount(
                                currency = secondaryCurrency,
                                amountValue = secondaryAmountDouble
                            ),
                            secondaryAccount = secondaryAccount,
                            dateTime = localDate.toDateTime(),
                            insertionDateTime = insertionDateTime
                        )
                    }
                } else {
                    if (primaryAccount != null && primaryCurrency != null) {
                        transaction = Transaction(
                            type = selectedTransactionType.toTransactionType(),
                            primaryAccount = primaryAccount,
                            _category = categoryData,
                            primaryAmount = Amount(
                                currency = primaryCurrency,
                                amountValue = primaryAmountDouble
                            ),
                            dateTime = localDate.toDateTime(),
                            insertionDateTime = insertionDateTime
                        )
                    }
                }

                if (transaction != null) {
                    if (isEdit) {
                        viewModel.onEditTransactionClick(
                            transaction = transaction,
                            isFromButtonClick = fromButtonClick
                        )
                    } else {
                        viewModel.onAddTransactionClick(
                            transaction = transaction,
                            isFromButtonClick = fromButtonClick
                        )
                    }
                }
            }


            CategoriesAppBar(
                doneButtonEnabled = isAddTransactionEnabled,
                selectedTransactionType = selectedTransactionType,
                featuresManager = viewModel.featuresManager,
                onTransactionTypeSelect = viewModel::onTransactionTypeSelect,
                onDoneClick = {
                    val fromButtonClick = false
                    onUpdateTransaction.invoke(fromButtonClick)
                }
            )

            Column(
                modifier = Modifier
                    .weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AmountTextField(
                    currency = primaryCurrencyData,
                    amount = primaryAmountText,
                    amountFontSize = if (currentFlow == AddTransactionFlow.Transfer) {
                        38.dp.asSp()
                    } else {
                        42.dp.asSp()
                    },
                    active = currentStep == EnterTransactionStep.PrimaryAmount,
                    label = if (currentFlow == AddTransactionFlow.Transfer) {
                        stringResource(
                            MR.strings.add_transaction_amount_from,
                            primaryAccountData?.name.orEmpty()
                        )
                    } else {
                        stringResource(MR.strings.add_transaction_amount)
                    },
                    onClick = viewModel::onPrimaryAmountClick
                )

                if (currentFlow == AddTransactionFlow.Transfer) {
                    AmountTextField(
                        modifier = Modifier
                            .padding(top = 24.dp),
                        currency = secondaryCurrencyData,
                        amount = secondaryAmountText,
                        amountFontSize = 24.dp.asSp(),
                        active = currentStep == EnterTransactionStep.SecondaryAmount,
                        label = stringResource(
                            MR.strings.add_transaction_amount_to,
                            secondaryAccountData?.name.orEmpty()
                        ),
                        onClick = viewModel::onSecondaryAmountClick
                    )
                }
            }

            CalendarDayView(
                date = localDate,
                onDateChange = viewModel::onDateSelect,
                onCalendarClick = viewModel::onCalendarClick
            )

            Surface(
                modifier = Modifier
                    .`if`(currentStep != null) {
                        weight(2f)
                    },
                elevation = 8.dp
            ) {
                var previousStepIndex by rememberSaveable {
                    mutableStateOf(currentStep?.let { it.ordinal - 1 })
                }
                BackHandler {
                    viewModel.onBackClick()
                }
                LaunchedEffect(currentStep) {
                    previousStepIndex = currentStep?.ordinal
                }

                CompositionLocalProvider(LocalContentColor provides CoinTheme.color.content) {
                    Column {
                        if (isSystemInDarkTheme()) {
                            Divider(
                                thickness = 0.5.dp,
                                color = CoinTheme.color.dividers
                            )
                        }

                        StepsEnterTransactionBar(
                            data = StepsEnterTransactionBarData(
                                flow = currentFlow,
                                currentStep = currentStep,
                                primaryAccountData = primaryAccountData,
                                secondaryAccountData = secondaryAccountData,
                                categoryData = categoryData
                            ),
                            onStepSelect = viewModel::onCurrentStepSelect
                        )

                        val categoriesFlow = when (selectedTransactionType) {
                            TransactionTypeTab.Expense -> viewModel.expenseCategories
                            TransactionTypeTab.Income -> viewModel.incomeCategories
                            TransactionTypeTab.Transfer -> MutableStateFlow(emptyList())
                        }
                        val accounts by viewModel.accounts.collectAsState()
                        val categories by categoriesFlow.collectAsState()

                        EnterTransactionController(
                            modifier = Modifier
                                .`if`(currentStep == null) {
                                    height(0.dp)
                                }
                                .`if`(currentStep != null) {
                                    weight(1f)
                                },
                            accounts = accounts,
                            categories = categories,
                            currentStep = currentStep,
                            animationDirection = when {
                                currentStep == null || previousStepIndex == null -> 0
                                currentStep!!.ordinal >= previousStepIndex!! -> 1
                                else -> -1
                            },
                            onAccountSelect = viewModel::onAccountSelect,
                            onAccountAdd = viewModel::onAccountAdd,
                            onCategorySelect = viewModel::onCategorySelect,
                            onCategoryAdd = viewModel::onCategoryAdd,
                            onKeyboardButtonClick = { command ->
                                viewModel.onKeyboardButtonClick(command)
                            }
                        )

                        ActionButtonsSection(
                            hasActiveStep = currentStep != null,
                            enabled = isAddTransactionEnabled,
                            onAddClick = {
                                val fromButtonClick = true
                                onUpdateTransaction.invoke(fromButtonClick)
                            },
                            onEditClick = {
                                val fromButtonClick = true
                                onUpdateTransaction.invoke(fromButtonClick)
                            },
                            isEditMode = viewModel.isEditMode,
                            onDeleteClick = {
                                modalNavController.present(DialogConfigurations.alert) { dialogKey ->
                                    DeleteTransactionDialog(
                                        key = dialogKey,
                                        transaction = params.transaction,
                                        modalNavController = modalNavController,
                                        onDeleteTransactionClick = { transaction ->
                                            viewModel.onDeleteTransactionClick(
                                                transaction,
                                                dialogKey
                                            )
                                        }
                                    )
                                }
                            },
                            onDuplicateClick = {
                                viewModel.onDuplicateTransactionClick(params.transaction)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DeleteTransactionDialog(
    key: String,
    transaction: Transaction?,
    modalNavController: ModalController,
    onDeleteTransactionClick: (Transaction) -> Unit
) {
    DeleteDialog(
        titleEntity = stringResource(MR.strings.deleting_transaction),
        onCancelClick = {
            modalNavController.popBackStack(key, animate = false)
        },
        onDeleteClick = {
            transaction?.let {
                onDeleteTransactionClick.invoke(it)
            }
        }
    )
}