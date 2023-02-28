package com.finance_tracker.finance_tracker.features.add_transaction

import com.finance_tracker.finance_tracker.core.common.combineExtended
import com.finance_tracker.finance_tracker.core.common.date.currentLocalDate
import com.finance_tracker.finance_tracker.core.common.date.currentLocalDateTime
import com.finance_tracker.finance_tracker.core.common.stateIn
import com.finance_tracker.finance_tracker.core.common.toString
import com.finance_tracker.finance_tracker.core.common.view_models.BaseViewModel
import com.finance_tracker.finance_tracker.core.feature_flags.FeaturesManager
import com.finance_tracker.finance_tracker.core.ui.tab_rows.TransactionTypeTab
import com.finance_tracker.finance_tracker.core.ui.tab_rows.toTransactionType
import com.finance_tracker.finance_tracker.core.ui.tab_rows.toTransactionTypeTab
import com.finance_tracker.finance_tracker.data.database.mappers.accountToDomainModel
import com.finance_tracker.finance_tracker.data.database.mappers.categoryToDomainModel
import com.finance_tracker.finance_tracker.domain.interactors.TransactionsInteractor
import com.finance_tracker.finance_tracker.domain.models.Account
import com.finance_tracker.finance_tracker.domain.models.Category
import com.finance_tracker.finance_tracker.domain.models.Transaction
import com.finance_tracker.finance_tracker.features.add_transaction.analytics.AddTransactionAnalytics
import com.financetracker.financetracker.data.AccountsEntityQueries
import com.financetracker.financetracker.data.CategoriesEntityQueries
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate

class AddTransactionViewModel(
    private val transactionsInteractor: TransactionsInteractor,
    private val accountsEntityQueries: AccountsEntityQueries,
    private val categoriesEntityQueries: CategoriesEntityQueries,
    params: AddTransactionScreenParams,
    private val addTransactionAnalytics: AddTransactionAnalytics,
    val featuresManager: FeaturesManager
): BaseViewModel<AddTransactionAction>() {

    private val preselectedAccount: Account? = params.preselectedAccount
    private val transaction: Transaction? = params.transaction

    val isEditMode = transaction != null

    private val _accounts: MutableStateFlow<List<Account>> = MutableStateFlow(emptyList())
    val accounts: StateFlow<List<Account>> = _accounts.asStateFlow()

    private val _selectedPrimaryAccount: MutableStateFlow<Account?> = MutableStateFlow(
        value = transaction?.primaryAccount ?: params.preselectedAccount
    )
    val selectedPrimaryAccount: StateFlow<Account?> = _selectedPrimaryAccount.asStateFlow()

    private val _selectedSecondaryAccount: MutableStateFlow<Account?> = MutableStateFlow(
        value = transaction?.secondaryAccount
    )
    val selectedSecondaryAccount: StateFlow<Account?> = _selectedSecondaryAccount.asStateFlow()

    val transactionInsertionDate = transaction?.insertionDateTime
    val primaryCurrency = selectedPrimaryAccount
        .map { it?.balance?.currency }
        .stateIn(viewModelScope, initialValue = null)

    val secondaryCurrency = selectedSecondaryAccount
        .map { it?.balance?.currency }
        .stateIn(viewModelScope, initialValue = null)

    private val selectedIncomeCategory = MutableStateFlow(transaction?._category)
    private val selectedExpenseCategory = MutableStateFlow(transaction?._category)

    private val initialSelectedDate = transaction?.dateTime?.date ?: Clock.System.currentLocalDate()
    private val _selectedDate: MutableStateFlow<LocalDate> = MutableStateFlow(initialSelectedDate)
    val selectedDate: StateFlow<LocalDate> = _selectedDate.asStateFlow()

    private val initialAmount = transaction?.primaryAmount
    private val _primaryAmountText: MutableStateFlow<String> = MutableStateFlow(
        initialAmount?.amountValue?.toString(precision = 2) ?: "0"
    )
    val primaryAmountText: StateFlow<String> = _primaryAmountText.asStateFlow()

    private val _secondaryAmountText: MutableStateFlow<String> = MutableStateFlow(
        transaction?.secondaryAmount?.amountValue?.toString(precision = 2) ?: "0"
    )
    val secondaryAmountText: StateFlow<String> = _secondaryAmountText.asStateFlow()

    private val _expenseCategories: MutableStateFlow<List<Category>> = MutableStateFlow(emptyList())
    val expenseCategories: StateFlow<List<Category>> = _expenseCategories.asStateFlow()

    private val _incomeCategories: MutableStateFlow<List<Category>> = MutableStateFlow(emptyList())
    val incomeCategories: StateFlow<List<Category>> = _incomeCategories.asStateFlow()

    private val initialSelectedTransactionType = transaction?.type?.toTransactionTypeTab() ?: TransactionTypeTab.Expense
    private val _selectedTransactionType: MutableStateFlow<TransactionTypeTab> =
        MutableStateFlow(initialSelectedTransactionType)
    val selectedTransactionType: StateFlow<TransactionTypeTab> = _selectedTransactionType.asStateFlow()

    private val flowStates = AddTransactionFlowState.createNewStatesForAllFlow()
    private val defaultFlow = AddTransactionFlow.Expense
    private val currentFlowState = selectedTransactionType
        .map {
            flowStates[it.toAddTransactionFlow()]
                ?: error("No FlowState for transactionTypeTab $it")
        }
        .stateIn(viewModelScope, initialValue = AddTransactionFlowState(defaultFlow))
    val currentFlow = currentFlowState
        .map { it.flow }
        .stateIn(viewModelScope, initialValue = defaultFlow)

    val selectedCategory = combine(currentFlow, selectedIncomeCategory, selectedExpenseCategory) {
            currentFlow, selectedIncomeCategory, selectedExpenseCategory ->
        when (currentFlow) {
            AddTransactionFlow.Expense -> selectedExpenseCategory
            AddTransactionFlow.Income -> selectedIncomeCategory
            AddTransactionFlow.Transfer -> null
        }
    }.stateIn(viewModelScope, initialValue = null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val currentStep = currentFlowState
        .flatMapLatest { it.currentStepFlow }
        .stateIn(viewModelScope, initialValue = null)

    private val hasPreviousStep = currentFlowState.value.hasPreviousStep

    @Suppress("UnnecessaryParentheses")
    val isAddTransactionEnabled = combineExtended(
        currentFlow, selectedPrimaryAccount, selectedSecondaryAccount,
        selectedCategory, primaryAmountText, secondaryAmountText
    ) { currentFlow, selectedPrimaryAccount, selectedSecondaryAccount,
        selectedCategory, primaryAmountText, secondaryAmountText ->

        when (currentFlow) {
            AddTransactionFlow.Expense,
            AddTransactionFlow.Income -> {
                selectedPrimaryAccount != null &&
                        selectedCategory != null &&
                        primaryAmountText.isNotEmptyAmount()
            }
            AddTransactionFlow.Transfer -> {
                selectedPrimaryAccount != null &&
                        selectedSecondaryAccount != null &&
                        primaryAmountText.isNotEmptyAmount() &&
                        secondaryAmountText.isNotEmptyAmount()
            }
        }
    }
        .stateIn(viewModelScope, initialValue = false)

    init {
        addTransactionAnalytics.trackAddTransactionScreenOpen()
        observeCurrentFlowState()
        initCurrentStep()
        observePrimaryAmount()
    }

    @Suppress("UnnecessaryParentheses")
    private fun String.isNotEmptyAmount(): Boolean {
        return (this.toDoubleOrNull() ?: 0.0) > 0.0
    }

    private fun observePrimaryAmount() {
        primaryAmountText
            .onEach { primaryAmount ->
                if (primaryCurrency.value == secondaryCurrency.value) {
                    _secondaryAmountText.value = primaryAmount
                }
            }
            .launchIn(viewModelScope)
    }

    private fun observeCurrentFlowState() {
        viewModelScope.launch {
            currentFlowState
                .onEach { currentFlowState ->
                    currentFlowState.setCurrentStepForFlowState(
                        isPrimaryAccountEmpty = selectedPrimaryAccount.value == null,
                        isSecondaryAccountEmpty = selectedSecondaryAccount.value == null,
                        isExpenseCategoryEmpty = selectedExpenseCategory.value == null,
                        isIncomeCategoryEmpty = selectedIncomeCategory.value == null
                    )
                }
                .launchIn(this)
        }
    }

    private fun initCurrentStep() {
        if (preselectedAccount != null) {
            currentFlowState.value.setStepAfter(EnterTransactionStep.PrimaryAccount)
        }
    }

    fun onScreenComposed() {
        loadAccounts()
        loadCategories()
    }

    private fun loadAccounts() {
        viewModelScope.launch {
            _accounts.value = accountsEntityQueries.getAllAccounts().executeAsList()
                .map { it.accountToDomainModel() }
        }
    }

    private fun loadCategories() {
        viewModelScope.launch {
            _expenseCategories.value = categoriesEntityQueries.getAllExpenseCategories().executeAsList()
                .map { it.categoryToDomainModel() }
            _incomeCategories.value = categoriesEntityQueries.getAllIncomeCategories().executeAsList()
                .map { it.categoryToDomainModel() }
        }
    }

    fun onAddTransactionClick(
        transaction: Transaction,
        isFromButtonClick: Boolean
    ) {
        addTransactionAnalytics.trackAddClick(isFromButtonClick, transaction)
        viewModelScope.launch {
            transactionsInteractor.addTransaction(transaction)
            viewAction = AddTransactionAction.Close
        }
    }

    fun onEditTransactionClick(
        transaction: Transaction,
        isFromButtonClick: Boolean
    ) {
        viewModelScope.launch {
            val oldTransaction = this@AddTransactionViewModel.transaction ?: return@launch
            val newTransaction = transaction.copy(id = oldTransaction.id)

            addTransactionAnalytics.trackEditClick(isFromButtonClick, oldTransaction, newTransaction)

            transactionsInteractor.updateTransaction(
                oldTransaction = oldTransaction,
                newTransaction = newTransaction
            )
            viewAction = AddTransactionAction.Close
        }
    }

    fun onDeleteTransactionClick(transaction: Transaction, dialogKey: String) {
        addTransactionAnalytics.trackDeleteTransactionClick(transaction)
        viewModelScope.launch {
            transactionsInteractor.deleteTransaction(transaction)
            viewAction = AddTransactionAction.DismissDialog(dialogKey)
            viewAction = AddTransactionAction.Close
        }
    }

    fun onBackClick() {
        val flowState = currentFlowState.value

        if (hasPreviousStep.value) {
            flowState.previous()
        } else {
            viewAction = AddTransactionAction.Close
        }
    }

    fun onDuplicateTransactionClick(transaction: Transaction?) {
        if (transaction == null) return

        addTransactionAnalytics.trackDuplicateTransactionClick(transaction)
        viewModelScope.launch {
            transactionsInteractor.addTransaction(
                transaction = transaction.copy(id = null, insertionDateTime = Clock.System.currentLocalDateTime())
            )
            viewAction = AddTransactionAction.Close
        }
    }

    fun onAccountSelect(account: Account) {
        addTransactionAnalytics.trackAccountSelect(account)
        when (currentStep.value) {
            EnterTransactionStep.PrimaryAccount -> {
                _selectedPrimaryAccount.value = account
            }
            EnterTransactionStep.SecondaryAccount -> {
                _selectedSecondaryAccount.value = account
            }
            else -> {
            }
        }
        currentFlowState.value.next()
    }

    fun onCategorySelect(category: Category) {
        addTransactionAnalytics.trackCategorySelect(category)
        when (currentFlow.value) {
            AddTransactionFlow.Expense -> {
                selectedExpenseCategory.value = category
            }
            AddTransactionFlow.Income -> {
                selectedIncomeCategory.value = category
            }
            else -> {
            }
        }
        currentFlowState.value.next()
    }

    fun onDateSelect(date: LocalDate) {
        addTransactionAnalytics.trackDateSelect(date)
        _selectedDate.value = date
    }

    fun onCalendarClick() {
        addTransactionAnalytics.trackCalendarClick()
    }

    fun onKeyboardButtonClick(command: KeyboardCommand) {
        val amountTextStateFlow = if (currentStep.value == EnterTransactionStep.SecondaryAmount) {
            _secondaryAmountText
        } else {
            _primaryAmountText
        }
        amountTextStateFlow.update {
            amountTextStateFlow.value.applyKeyboardCommand(command)
        }
    }

    fun onTransactionTypeSelect(transactionTypeTab: TransactionTypeTab) {
        addTransactionAnalytics.trackTransactionTypeSelect(
            transactionType = transactionTypeTab.toTransactionType()
        )
        _selectedTransactionType.value = transactionTypeTab
    }

    fun onAccountAdd() {
        addTransactionAnalytics.trackAddAccountClick()
        viewAction = AddTransactionAction.OpenAddAccountScreen
    }

    fun onCategoryAdd() {
        val transactionTypeTab = selectedTransactionType.value
        addTransactionAnalytics.trackAddCategoryClick(
            transactionType = transactionTypeTab.toTransactionType()
        )
        viewAction = AddTransactionAction.OpenAddCategoryScreen(
            type = transactionTypeTab
        )
    }

    fun onCurrentStepSelect(step: EnterTransactionStep) {
        trackCurrentStepSelect(step)
        currentFlowState.value.setStep(step)
    }

    private fun trackCurrentStepSelect(step: EnterTransactionStep) {
        when (step) {
            EnterTransactionStep.PrimaryAccount -> {
                addTransactionAnalytics.trackPrimaryAccountClick()
            }
            EnterTransactionStep.Category -> {
                addTransactionAnalytics.trackCategoryClick()
            }
            EnterTransactionStep.PrimaryAmount -> {
                addTransactionAnalytics.trackPrimaryAmountClick(primaryAmountText.value)
            }
            EnterTransactionStep.SecondaryAccount -> {
                addTransactionAnalytics.trackSecondaryAccountClick()
            }
            EnterTransactionStep.SecondaryAmount -> {
                addTransactionAnalytics.trackSecondaryAmountClick(secondaryAmountText.value)
            }
        }
    }

    fun onPrimaryAmountClick() {
        onCurrentStepSelect(EnterTransactionStep.PrimaryAmount)
    }

    fun onSecondaryAmountClick() {
        onCurrentStepSelect(EnterTransactionStep.SecondaryAmount)
    }

    private fun TransactionTypeTab.toAddTransactionFlow(): AddTransactionFlow {
        return when (this) {
            TransactionTypeTab.Expense -> AddTransactionFlow.Expense
            TransactionTypeTab.Income -> AddTransactionFlow.Income
            TransactionTypeTab.Transfer -> AddTransactionFlow.Transfer
        }
    }
}