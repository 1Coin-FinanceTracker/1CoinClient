package com.finance_tracker.finance_tracker.data.database.mappers

import com.finance_tracker.finance_tracker.domain.models.Account
import com.finance_tracker.finance_tracker.domain.models.AccountColorModel
import com.finance_tracker.finance_tracker.domain.models.Amount
import com.finance_tracker.finance_tracker.domain.models.Category
import com.finance_tracker.finance_tracker.domain.models.Currency
import com.finance_tracker.finance_tracker.domain.models.Transaction
import com.finance_tracker.finance_tracker.domain.models.TransactionType
import kotlinx.datetime.LocalDateTime

val fullTransactionMapper: (

    // Transaction
    id: Long,
    type: TransactionType,
    amount: Double,
    amountCurrency: String,
    categoryId: Long?,
    accountId: Long?,
    insertionDate: LocalDateTime,
    date: LocalDateTime,
    secondaryAmount: Double?,
    secondaryAmountCurrency: String?,
    secondaryAccountId: Long?,

    // Primary Account
    id_: Long,
    type_: Account.Type,
    name: String,
    balance: Double,
    colorId: Int,
    currency: String,

    // Secondary Account
    id__: Long?,
    type__: Account.Type?,
    name_: String?,
    balance_: Double?,
    colorId_: Int?,
    currency_: String?,

    // Category
    id___: Long?,
    name__: String?,
    icon: String?,
    position: Long?,
    isExpense: Boolean?,
    isIncome: Boolean?
) -> Transaction = {
        // Transaction
        id, type, amount, amountCurrency, categoryId, accountId, insertionDate, date,
        secondaryAmount, secondaryAmountCurrency, secondaryAccountId,
        // Primary Account
        _, accountType, accountName, balance, accountColorId, _,
        // Secondary Account
        _, secondaryAccountType, secondaryAccountName, secondaryBalance, secondaryAccountColorId, _,
        // Category
        _, categoryName, categoryIcon, _, _, _ ->

    val currency = Currency.getByCode(amountCurrency)
    val secondaryCurrency = secondaryAmountCurrency?.let(Currency::getByCode)
    Transaction(
        id = id,
        type = type,
        primaryAccount = Account(
            id = accountId ?: 0L,
            type = accountType,
            colorModel = AccountColorModel.from(accountColorId),
            name = accountName,
            balance = Amount(
                amountValue = balance,
                currency = currency
            )
        ),
        secondaryAccount = if (secondaryAccountId != null && secondaryAccountType != null) {
            Account(
                id = secondaryAccountId,
                type = secondaryAccountType,
                colorModel = secondaryAccountColorId?.let(AccountColorModel::from)
                    ?: AccountColorModel.defaultAccountColor,
                name = secondaryAccountName.orEmpty(),
                balance = Amount(
                    amountValue = secondaryBalance ?: 0.0,
                    currency = secondaryCurrency ?: Currency.default
                )
            )
        } else {
            null
        },
        _category = if (categoryId != null && categoryIcon != null) {
            Category(
                id = categoryId,
                name = categoryName.orEmpty(),
                iconId = categoryIcon
            )
        } else {
            null
        },
        primaryAmount = Amount(
            currency = currency,
            amountValue = amount
        ),
        secondaryAmount = Amount(
            currency = secondaryCurrency ?: Currency.default,
            amountValue = secondaryAmount ?: 0.0
        ),
        dateTime = date,
        insertionDateTime = insertionDate
    )
}