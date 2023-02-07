package com.finance_tracker.finance_tracker.presentation.detail_account

import app.cash.paging.cachedIn
import com.finance_tracker.finance_tracker.core.common.view_models.BaseViewModel
import com.finance_tracker.finance_tracker.data.repositories.AccountsRepository
import com.finance_tracker.finance_tracker.domain.interactors.TransactionsInteractor
import com.finance_tracker.finance_tracker.domain.models.Account
import com.finance_tracker.finance_tracker.domain.models.Transaction
import com.finance_tracker.finance_tracker.presentation.detail_account.analytics.DetailAccountAnalytics
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class DetailAccountViewModel(
    private val account: Account,
    transactionsInteractor: TransactionsInteractor,
    accountsRepository: AccountsRepository,
    private val detailAccountAnalytics: DetailAccountAnalytics
): BaseViewModel<DetailAccountAction>() {

    val paginatedTransactions = transactionsInteractor.getPaginatedTransactionsByAccountId(account.id)
        .cachedIn(viewModelScope)

    val accountData = accountsRepository.getAccountByIdFlow(account.id)
        .stateIn(viewModelScope, started = SharingStarted.Lazily, initialValue = account)

    init {
        detailAccountAnalytics.trackScreenOpen()
    }

    fun onBackClick() {
        detailAccountAnalytics.trackBackClick()
        viewAction = DetailAccountAction.CloseScreen
    }

    fun onEditClick() {
        detailAccountAnalytics.trackEditAccountClick(account)
        viewAction = DetailAccountAction.OpenEditAccountScreen(account)
    }

    fun onTransactionClick(transaction: Transaction) {
        detailAccountAnalytics.trackTransactionClick(transaction)
        viewAction = DetailAccountAction.OpenEditTransactionScreen(transaction)
    }

    fun onIconClick() {
        detailAccountAnalytics.trackIconClick(account)
        viewAction = DetailAccountAction.OpenEditAccountScreen(account)
    }

    fun onAddTransactionClick() {
        detailAccountAnalytics.trackAddTransactionClick(account)
        viewAction = DetailAccountAction.OpenAddTransactionScreen(account)
    }
}