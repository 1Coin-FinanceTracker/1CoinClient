package com.finance_tracker.finance_tracker.data.repositories

import com.finance_tracker.finance_tracker.data.network.CurrenciesNetworkDataSource
import com.finance_tracker.finance_tracker.data.settings.AccountSettings
import com.finance_tracker.finance_tracker.domain.models.Currency
import com.finance_tracker.finance_tracker.domain.models.CurrencyRates
import com.financetracker.financetracker.data.CurrencyRatesEntityQueries
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.double
import kotlinx.serialization.json.jsonPrimitive

class CurrenciesRepository(
    private val currencyRatesEntityQueries: CurrencyRatesEntityQueries,
    private val currenciesNetworkDataSource: CurrenciesNetworkDataSource,
    private val accountSettings: AccountSettings
) {

    fun getCurrencyRatesFlow(): Flow<CurrencyRates> {
        return currencyRatesEntityQueries.getAllRates().asFlow()
                .mapToList()
                .map { currencyRatesEntities ->
                    currencyRatesEntities.associateBy { it.currency }
                }
                .flowOn(Dispatchers.Default)
    }

    suspend fun updateCurrencyRates() {
        return withContext(Dispatchers.Default) {
            val currencyRates = currenciesNetworkDataSource.getCurrenciesRates()
                .getOrNull() ?: return@withContext
            currencyRatesEntityQueries.transaction {
                currencyRates.forEach { (key, value) ->
                    currencyRatesEntityQueries.insertNewRate(
                        currency = key,
                        rate = value.jsonPrimitive.double
                    )
                }
            }
        }
    }

    suspend fun savePrimaryCurrency(currency: Currency) {
        accountSettings.savePrimaryCurrency(currency.code)
    }

    fun getPrimaryCurrencyFlow(): Flow<Currency> {
        return accountSettings.getPrimaryCurrencyCodeFlow()
            .map(::mapCodeToCurrency)
    }

    suspend fun getPrimaryCurrency(): Currency {
        val currencyCode = accountSettings.getPrimaryCurrencyCode()
        return mapCodeToCurrency(currencyCode)
    }

    private fun mapCodeToCurrency(currencyCode: String?): Currency {
        return currencyCode?.let(Currency::getByCode) ?: Currency.default
    }
}