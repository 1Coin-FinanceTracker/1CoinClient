package com.finance_tracker.finance_tracker.data.database

import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.common.Context
import com.finance_tracker.finance_tracker.core.common.getRaw
import com.finance_tracker.finance_tracker.core.common.localizedString
import com.finance_tracker.finance_tracker.domain.models.Account
import com.finance_tracker.finance_tracker.domain.models.AccountColorModel
import com.finance_tracker.finance_tracker.domain.models.Currency
import com.finance_tracker.finance_tracker.presentation.common.formatters.Category
import com.financetracker.financetracker.data.AccountsEntityQueries
import com.financetracker.financetracker.data.CategoriesEntityQueries
import com.financetracker.financetracker.data.CurrencyRatesEntityQueries
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.double
import kotlinx.serialization.json.jsonPrimitive

class DatabaseInitializer(
    private val jsonFactory: Json,
    private val categoriesEntityQueries: CategoriesEntityQueries,
    private val accountsEntityQueries: AccountsEntityQueries,
    private val currencyRatesEntityQueries: CurrencyRatesEntityQueries
) {

    fun init(context: Context) {
        initAccounts(context)
        initCategories(context)
        initCurrencyRates(context)
    }

    private fun initAccounts(context: Context) {
        accountsEntityQueries.transaction {
            accountsEntityQueries.insertAccount(
                id = 1,
                type = Account.Type.Cash,
                name = MR.strings.account_type_cash.localizedString(context),
                balance = 0.0,
                colorId = AccountColorModel.EastBay.id,
                currency = Currency.default.code
            )
        }
    }

    private fun initCategories(context: Context) {
        categoriesEntityQueries.transaction {
            val databaseCategories = categoriesEntityQueries.getAllCategories().executeAsList()

            if (databaseCategories.isEmpty()) {
                createDefaultExpenseCategories(context).forEach { category ->
                    categoriesEntityQueries.insertCategory(
                        id = category.id,
                        name = category.name,
                        icon = category.iconId,
                        position = category.id,
                        isExpense = true,
                        isIncome = false,
                    )
                }
                createDefaultIncomeCategories(context).forEach { category ->
                    categoriesEntityQueries.insertCategory(
                        id = category.id,
                        name = category.name,
                        icon = category.iconId,
                        position = category.id,
                        isExpense = false,
                        isIncome = true,
                    )
                }
            }
        }
    }

    private fun createDefaultExpenseCategories(context: Context): List<Category> {
        return listOf(
            Category(
                id = 0,
                name = MR.strings.category_restoraunt.localizedString(context),
                iconId = "ic_category_1"
            ),
            Category(
                id = 1,
                name = MR.strings.category_health.localizedString(context),
                iconId = "ic_category_2"
            ),
            Category(
                id = 2,
                name = MR.strings.category_child.localizedString(context),
                iconId = "ic_category_3"
            ),
            Category(
                id = 3,
                name = MR.strings.category_car.localizedString(context),
                iconId = "ic_category_4"
            ),
            Category(
                id = 4,
                name = MR.strings.category_education.localizedString(context),
                iconId = "ic_category_5"
            ),
            Category(
                id = 5,
                name = MR.strings.category_entertainment.localizedString(context),
                iconId = "ic_category_6"
            ),
            Category(
                id = 6,
                name = MR.strings.category_sport.localizedString(context),
                iconId = "ic_category_7"
            ),
            Category(
                id = 7,
                name = MR.strings.category_public_transport.localizedString(context),
                iconId = "ic_category_8"
            ),
            Category(
                id = 8,
                name = MR.strings.category_shop.localizedString(context),
                iconId = "ic_category_9"
            ),
            Category(
                id = 9,
                name = MR.strings.category_utilities.localizedString(context),
                iconId = "ic_category_10"
            ),
            Category(
                id = 10,
                name = MR.strings.category_clothes.localizedString(context),
                iconId = "ic_category_11"
            ),
            Category(
                id = 11,
                name = MR.strings.category_electronics.localizedString(context),
                iconId = "ic_category_12"
            ),
            Category(
                id = 12,
                name = MR.strings.category_correct.localizedString(context),
                iconId = "ic_category_13"
            )
        )
    }

    private fun createDefaultIncomeCategories(context: Context): List<Category> {
        return listOf(
            Category(
                id = 13,
                name = MR.strings.category_salary.localizedString(context),
                iconId = "ic_category_14"
            ),
            Category(
                id = 14,
                name = MR.strings.category_allowance.localizedString(context),
                iconId = "ic_category_15"
            ),
            Category(
                id = 15,
                name = MR.strings.category_gift.localizedString(context),
                iconId = "ic_category_16"
            )
        )
    }

    private fun initCurrencyRates(context: Context) {
        val currencyRatesJson = getRaw(context, "base_rates", "json")
        currencyRatesEntityQueries.transaction {
            val databaseCurrencyRates = currencyRatesEntityQueries.getAllRates().executeAsList()

            if (databaseCurrencyRates.isEmpty()) {
                val currencyRatesObject = jsonFactory.decodeFromString<JsonObject>(currencyRatesJson)
                currencyRatesObject.forEach { (key, value) ->
                    currencyRatesEntityQueries.insertNewRate(
                        currency = key,
                        rate = value.jsonPrimitive.double
                    )
                }
            }
        }
    }
}