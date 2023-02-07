package com.finance_tracker.finance_tracker.data.database.mappers

import com.finance_tracker.finance_tracker.domain.models.Account
import com.finance_tracker.finance_tracker.domain.models.AccountColorModel
import com.finance_tracker.finance_tracker.domain.models.Amount
import com.finance_tracker.finance_tracker.domain.models.Currency
import com.financetracker.financetracker.data.AccountsEntity

fun AccountsEntity.accountToDomainModel(): Account {
    return Account(
        id = id,
        type = type,
        name = name,
        balance = Amount(
            amountValue = balance,
            currency = Currency.getByCode(currency)
        ),
        colorModel = AccountColorModel.from(colorId)
    )
}