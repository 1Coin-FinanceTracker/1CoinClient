package com.finance_tracker.finance_tracker.presentation.add_transaction.analytics

import com.benasher44.uuid.uuid4
import com.finance_tracker.finance_tracker.domain.models.Account
import com.finance_tracker.finance_tracker.domain.models.Transaction
import com.finance_tracker.finance_tracker.domain.models.TransactionType
import com.finance_tracker.finance_tracker.presentation.common.formatters.Category
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate

class AddTransactionAnalytics: com.finance_tracker.finance_tracker.core.analytics.BaseAnalytics() {

    override val screenName = "AddTransactionScreen"

    private var actionId = ""
    private var startOfAction: Instant? = null

    fun trackAddTransactionScreenOpen() {
        actionId = uuid4().toString()
        startOfAction = Clock.System.now()
        trackScreenOpen(mapOf(
            ActionIdKey to actionId
        ))
    }

    fun trackTransactionTypeSelect(transactionType: TransactionType) {
        trackSelect(
            eventName = "TransactionType",
            properties = mapOf(
                ActionIdKey to actionId,
                "transaction_type" to transactionType.analyticsName
            )
        )
    }

    fun trackAccountSelect(account: Account) {
        trackSelect(
            eventName = "Account",
            properties = mapOf(
                ActionIdKey to actionId,
                "account_name" to account.name
            )
        )
    }

    fun trackAccountClick() {
        trackClick(
            eventName = "Account",
            properties = mapOf(
                ActionIdKey to actionId
            )
        )
    }

    fun trackCategorySelect(category: Category) {
        trackSelect(
            eventName = "Category",
            properties = mapOf(
                ActionIdKey to actionId,
                "category_name" to category.name
            )
        )
    }

    fun trackCategoryClick() {
        trackClick(
            eventName = "Category",
            properties = mapOf(
                ActionIdKey to actionId
            )
        )
    }

    fun trackCalendarClick() {
        trackClick(
            eventName = "Date",
            properties = mapOf(
                ActionIdKey to actionId
            )
        )
    }

    fun trackDateSelect(date: LocalDate) {
        trackSelect(
            eventName = "Date",
            properties = mapOf(
                ActionIdKey to actionId,
                "date" to date.toString()
            )
        )
    }

    fun trackAmountClick(amountText: String) {
        trackClick(
            eventName = "Amount",
            properties = mapOf(
                ActionIdKey to actionId,
                "amount_text" to amountText
            )
        )
    }

    fun trackEditClick(
        isFromButtonClick: Boolean,
        oldTransaction: Transaction,
        newTransaction: Transaction
    ) {
        if (isFromButtonClick) {
            trackButtonEditClick(oldTransaction, newTransaction)
        } else {
            trackMenuEditClick(oldTransaction, newTransaction)
        }
    }

    fun trackAddClick(
        isFromButtonClick: Boolean,
        transaction: Transaction
    ) {
        if (isFromButtonClick) {
            trackButtonAddClick(transaction = transaction)
        } else {
            trackMenuAddClick(transaction = transaction)
        }
    }

    private fun trackMenuAddClick(transaction: Transaction) {
        val transactionProperties = transaction.getTransactionProperties()
        trackClick(
            eventName = "MenuAdd",
            properties = mapOf(
                ActionIdKey to actionId,
                DurationKey to getActionDuration()
            ) + transactionProperties
        )
    }

    private fun getActionDuration(): Long? {
        return startOfAction?.let { Clock.System.now().minus(it).inWholeMilliseconds }
    }

    private fun trackButtonAddClick(transaction: Transaction) {
        val transactionProperties = transaction.getTransactionProperties()
        trackClick(
            eventName = "ButtonAdd",
            properties = mapOf(
                ActionIdKey to actionId,
                DurationKey to getActionDuration()
            ) + transactionProperties
        )
    }

    private fun trackMenuEditClick(
        oldTransaction: Transaction,
        newTransaction: Transaction
    ) {
        val oldTransactionProperties = oldTransaction.getTransactionProperties(
            keyPrefix = "old_"
        )
        val newTransactionProperties = newTransaction.getTransactionProperties(
            keyPrefix = "new_"
        )
        trackClick(
            eventName = "MenuEdit",
            properties = mapOf(
                ActionIdKey to actionId,
                DurationKey to getActionDuration()
            ) + oldTransactionProperties + newTransactionProperties
        )
    }

    private fun trackButtonEditClick(
        oldTransaction: Transaction,
        newTransaction: Transaction
    ) {
        val oldTransactionProperties = oldTransaction.getTransactionProperties(
            keyPrefix = "old_"
        )
        val newTransactionProperties = newTransaction.getTransactionProperties(
            keyPrefix = "new_"
        )
        trackClick(
            eventName = "ButtonEdit",
            properties = mapOf(
                ActionIdKey to actionId,
                DurationKey to getActionDuration()
            ) + oldTransactionProperties + newTransactionProperties
        )
    }

    fun trackAddAccountClick() {
        trackClick(
            eventName = "AddAccount",
            properties = mapOf(
                ActionIdKey to actionId
            )
        )
    }

    fun trackAddCategoryClick(transactionType: TransactionType) {
        trackClick(
            eventName = "AddCategory",
            properties = mapOf(
                ActionIdKey to actionId,
                "transaction_type" to transactionType.analyticsName
            )
        )
    }

    fun trackDeleteTransactionClick(transaction: Transaction) {
        val transactionProperties = transaction.getTransactionProperties()
        trackClick(
            eventName = "Delete",
            properties = mapOf(ActionIdKey to actionId) + transactionProperties
        )
    }

    fun trackDuplicateTransactionClick(transaction: Transaction) {
        val transactionProperties = transaction.getTransactionProperties()
        trackClick(
            eventName = "Duplicate",
            properties = mapOf(ActionIdKey to actionId) + transactionProperties
        )
    }

    private fun Transaction.getTransactionProperties(keyPrefix: String = ""): Map<String, Any?> {
        return mapOf(
            "${keyPrefix}transaction_type" to type.analyticsName,
            "${keyPrefix}account_name" to account.name,
            "${keyPrefix}category_name" to _category?.name,
            "${keyPrefix}date" to dateTime.toString(),
            "${keyPrefix}amount_value" to amount.amountValue,
            "${keyPrefix}amount_currency" to amount.currency.code
        )
    }

    companion object {
        private const val ActionIdKey = "action_id"
        private const val DurationKey = "duration"
    }
}