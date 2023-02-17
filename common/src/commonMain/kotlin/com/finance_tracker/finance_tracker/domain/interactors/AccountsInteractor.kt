package com.finance_tracker.finance_tracker.domain.interactors

import com.finance_tracker.finance_tracker.data.repositories.AccountsRepository
import kotlinx.coroutines.flow.Flow

class AccountsInteractor(
    private val accountsRepository: AccountsRepository
) {

    fun getAccountsCountFlow(): Flow<Int> {
        return accountsRepository.getAccountsCountFlow()
    }
}