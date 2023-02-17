package com.finance_tracker.finance_tracker.features.add_transaction

import com.finance_tracker.finance_tracker.core.common.date.currentLocalDate
import com.finance_tracker.finance_tracker.core.common.date.currentLocalDateTime
import com.finance_tracker.finance_tracker.core.common.toString
import com.finance_tracker.finance_tracker.core.common.view_models.BaseViewModel
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate

class AddTransactionViewModel(
    private val transactionsInteractor: TransactionsInteractor,
    private val accountsEntityQueries: AccountsEntityQueries,
    private val categoriesEntityQueries: CategoriesEntityQueries,
    params: AddTransactionScreenParams,
    private val addTransactionAnalytics: AddTransactionAnalytics
): BaseViewModel<AddTransactionAction>() {

    private val preselectedAccount: Account? = params.preselectedAccount
    private val transaction: Transaction? = params.transaction

    val isEditMode = transaction != null

    private val _accounts: MutableStateFlow<List<Account>> = MutableStateFlow(emptyList())
    val accounts: StateFlow<List<Account>> = _accounts.asStateFlow()

    private val _selectedAccount: MutableStateFlow<Account?> = MutableStateFlow(
        value = transaction?.account ?: params.preselectedAccount
    )
    val selectedAccount: StateFlow<Account?> = _selectedAccount.asStateFlow()

    val transactionInsertionDate = transaction?.insertionDateTime
    val currency = selectedAccount
        .map { it?.balance?.currency }
        .stateIn(viewModelScope, SharingStarted.Lazily, null)

    private val _selectedCategory: MutableStateFlow<Category?> = MutableStateFlow(transaction?._category)
    val selectedCategory: StateFlow<Category?> = _selectedCategory.asStateFlow()

    private val initialSelectedDate = transaction?.dateTime?.date ?: Clock.System.currentLocalDate()
    private val _selectedDate: MutableStateFlow<LocalDate> = MutableStateFlow(initialSelectedDate)
    val selectedDate: StateFlow<LocalDate> = _selectedDate.asStateFlow()

    private val initialAmount = transaction?.amount
    private val _amountText: MutableStateFlow<String> = MutableStateFlow(
        initialAmount?.amountValue?.toString(precision = 2) ?: "0"
    )
    val amountText: StateFlow<String> = _amountText.asStateFlow()

    private val _expenseCategories: MutableStateFlow<List<Category>> = MutableStateFlow(emptyList())
    val expenseCategories: StateFlow<List<Category>> = _expenseCategories.asStateFlow()

    private val _incomeCategories: MutableStateFlow<List<Category>> = MutableStateFlow(emptyList())
    val incomeCategories: StateFlow<List<Category>> = _incomeCategories.asStateFlow()

    private val steps = EnterTransactionStep.values()
    val firstStep = when {
        preselectedAccount != null -> {
            EnterTransactionStep.Category
        }
        transaction == null -> {
            steps.first()
        }
        else -> {
            null
        }
    }

    @Suppress("UnnecessaryParentheses")
    val isAddTransactionEnabled = combine(selectedAccount, selectedCategory, amountText) {
            selectedAccount, selectedCategory, amountText ->
        selectedAccount != null && selectedCategory != null && (amountText.toDoubleOrNull() ?: 0.0) > 0.0
    }
        .stateIn(viewModelScope, started = SharingStarted.Lazily, initialValue = false)

    private val initialSelectedTransactionType = transaction?.type?.toTransactionTypeTab() ?: TransactionTypeTab.Expense
    private val _selectedTransactionType: MutableStateFlow<TransactionTypeTab> =
        MutableStateFlow(initialSelectedTransactionType)
    val selectedTransactionType: StateFlow<TransactionTypeTab> = _selectedTransactionType.asStateFlow()

    init {
        addTransactionAnalytics.trackAddTransactionScreenOpen()
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
        _selectedAccount.value = account
    }

    fun onCategorySelect(category: Category) {
        addTransactionAnalytics.trackCategorySelect(category)
        _selectedCategory.value = category
    }

    fun onDateSelect(date: LocalDate) {
        addTransactionAnalytics.trackDateSelect(date)
        _selectedDate.value = date
    }

    fun onCalendarClick() {
        addTransactionAnalytics.trackCalendarClick()
    }

    fun onKeyboardButtonClick(command: KeyboardCommand) {
        val amountText = _amountText.value
        var newAmountText = amountText
        when (command) {
            KeyboardCommand.Delete -> {
                when {
                    amountText.length <= 1 && amountText.toDouble() == 0.0 -> {
                        /* ignore */
                    }

                    amountText.length <= 1 && amountText.toDouble() != 0.0 -> {
                        newAmountText = "0"
                    }

                    else -> {
                        newAmountText = newAmountText.dropLast(1)
                    }
                }
            }

            is KeyboardCommand.Digit -> {
                if (amountText == "0") {
                    newAmountText = command.value.toString()
                } else {
                    newAmountText += command.value.toString()
                }
            }

            KeyboardCommand.Point -> {
                if (!newAmountText.contains(".")) {
                    newAmountText += "."
                }
            }
        }
        if (newAmountText.matches(Regex("^\\d*\\.?\\d*"))) {
            _amountText.value = newAmountText
        }
    }

    fun onTransactionTypeSelect(transactionTypeTab: TransactionTypeTab) {
        addTransactionAnalytics.trackTransactionTypeSelect(
            transactionType = transactionTypeTab.toTransactionType()
        )
        _selectedTransactionType.value = transactionTypeTab
        resetSelectedCategory(transactionTypeTab)
    }

    private fun resetSelectedCategory(transactionTypeTab: TransactionTypeTab) {
        val currentCategories = when (transactionTypeTab) {
            TransactionTypeTab.Income -> _incomeCategories.value
            TransactionTypeTab.Expense -> _expenseCategories.value
        }
        if (selectedCategory.value !in currentCategories) {
            _selectedCategory.value = null
        }
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
        when (step) {
            EnterTransactionStep.Account -> {
                addTransactionAnalytics.trackAccountClick()
            }
            EnterTransactionStep.Category -> {
                addTransactionAnalytics.trackCategoryClick()
            }
            EnterTransactionStep.Amount -> {
                addTransactionAnalytics.trackAmountClick(amountText.value)
            }
        }
    }
}