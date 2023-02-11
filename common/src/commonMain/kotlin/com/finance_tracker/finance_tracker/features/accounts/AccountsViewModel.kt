package com.finance_tracker.finance_tracker.features.accounts

import com.finance_tracker.finance_tracker.core.common.view_models.BaseViewModel
import com.finance_tracker.finance_tracker.data.repositories.AccountsRepository
import com.finance_tracker.finance_tracker.domain.models.Account
import com.finance_tracker.finance_tracker.features.accounts.analytics.AccountsAnalytics
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AccountsViewModel(
    private val repository: AccountsRepository,
    private val accountsAnalytics: AccountsAnalytics
): BaseViewModel<AccountsAction>() {

    private val _accounts = MutableStateFlow<List<Account>>(emptyList())
    val accounts = _accounts.asStateFlow()

    init {
        accountsAnalytics.trackScreenOpen()
    }

    fun onScreenComposed() {
        loadAccounts()
    }

    fun onAccountClick(account: Account) {
        accountsAnalytics.trackAccountClick(account)
        viewAction = AccountsAction.OpenEditAccountScreen(account)
    }

    fun onAddAccountClick() {
        accountsAnalytics.trackAddAccountClick()
        viewAction = AccountsAction.OpenAddAccountScreen
    }

    private fun loadAccounts() {
        viewModelScope.launch {
            _accounts.value = repository.getAllAccountsFromDatabase()
        }
    }
}
