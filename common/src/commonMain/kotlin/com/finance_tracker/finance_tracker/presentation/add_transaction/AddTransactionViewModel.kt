package com.finance_tracker.finance_tracker.presentation.add_transaction

import com.finance_tracker.finance_tracker.core.common.toLocalDate
import com.finance_tracker.finance_tracker.core.common.view_models.BaseViewModel
import com.finance_tracker.finance_tracker.core.ui.tab_rows.TransactionTypeTab
import com.finance_tracker.finance_tracker.core.ui.tab_rows.toTransactionTypeTab
import com.finance_tracker.finance_tracker.data.database.mappers.accountToDomainModel
import com.finance_tracker.finance_tracker.data.database.mappers.categoryToDomainModel
import com.finance_tracker.finance_tracker.domain.interactors.TransactionsInteractor
import com.finance_tracker.finance_tracker.domain.models.Account
import com.finance_tracker.finance_tracker.domain.models.Category
import com.finance_tracker.finance_tracker.domain.models.Currency
import com.finance_tracker.finance_tracker.domain.models.Transaction
import com.finance_tracker.finance_tracker.presentation.add_transaction.views.EnterTransactionStep
import com.finance_tracker.finance_tracker.presentation.add_transaction.views.enter_transaction_controller.KeyboardCommand
import com.financetracker.financetracker.data.AccountsEntityQueries
import com.financetracker.financetracker.data.CategoriesEntityQueries
import io.github.koalaplot.core.util.toString
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.Date

class AddTransactionViewModel(
    private val transactionsInteractor: TransactionsInteractor,
    private val accountsEntityQueries: AccountsEntityQueries,
    private val categoriesEntityQueries: CategoriesEntityQueries,
    private val _transaction: Transaction
): BaseViewModel<AddTransactionAction>() {

    private val transaction: Transaction? = _transaction.takeIf { _transaction != Transaction.EMPTY }

    val isEditMode = transaction != null

    private val _accounts: MutableStateFlow<List<Account>> = MutableStateFlow(emptyList())
    val accounts: StateFlow<List<Account>> = _accounts.asStateFlow()

    private val _selectedAccount: MutableStateFlow<Account?> = MutableStateFlow(transaction?.account)
    val selectedAccount: StateFlow<Account?> = _selectedAccount.asStateFlow()

    val transactionInsertionDate = transaction?.insertionDate
    val currency = selectedAccount
        .map { it?.balance?.currency ?: Currency.default }
        .stateIn(viewModelScope, SharingStarted.Lazily, Currency.default)

    private val _selectedCategory: MutableStateFlow<Category?> = MutableStateFlow(transaction?.category)
    val selectedCategory: StateFlow<Category?> = _selectedCategory.asStateFlow()

    private val initialSelectedDate = transaction?.date?.toLocalDate() ?: LocalDate.now()
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
    val firstStep = if (transaction == null) steps.first() else null

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

    fun onAddTransactionClick(transaction: Transaction) {
        viewModelScope.launch {
            transactionsInteractor.addOrUpdateTransaction(transaction)
        }
    }

    fun onEditTransactionClick(transaction: Transaction) {
        viewModelScope.launch {
            val transactionId = this@AddTransactionViewModel.transaction?.id
            transactionsInteractor.addOrUpdateTransaction(
                transaction = transaction.copy(id = transactionId)
            )
        }
    }

    fun onDeleteTransactionClick(transaction: Transaction, dialogKey: String) {
        viewModelScope.launch {
            transactionsInteractor.deleteTransaction(transaction)
            viewAction = AddTransactionAction.DismissDialog(dialogKey)
            viewAction = AddTransactionAction.Close
        }
    }

    fun onDuplicateTransactionClick(transaction: Transaction?) {
        if (transaction == null) return

        viewModelScope.launch {
            transactionsInteractor.addOrUpdateTransaction(
                transaction = transaction.copy(id = null, insertionDate = Date())
            )
            viewAction = AddTransactionAction.Close
        }
    }

    fun onAccountSelect(account: Account) {
        _selectedAccount.value = account
    }

    fun onCategorySelect(category: Category) {
        _selectedCategory.value = category
    }

    fun onDateSelect(date: LocalDate) {
        _selectedDate.value = date
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
}