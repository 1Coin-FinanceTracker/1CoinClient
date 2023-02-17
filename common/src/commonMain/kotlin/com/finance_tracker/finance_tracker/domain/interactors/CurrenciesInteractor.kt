package com.finance_tracker.finance_tracker.domain.interactors

import com.finance_tracker.finance_tracker.data.repositories.CurrenciesRepository
import com.finance_tracker.finance_tracker.domain.models.Currency
import com.finance_tracker.finance_tracker.domain.models.CurrencyRates
import kotlinx.coroutines.flow.Flow

class CurrenciesInteractor(
    private val currenciesRepository: CurrenciesRepository
) {

    fun getCurrencyRatesFlow(): Flow<CurrencyRates> {
        return currenciesRepository.getCurrencyRatesFlow()
    }

    suspend fun updateCurrencyRates() {
        currenciesRepository.updateCurrencyRates()
    }

    suspend fun savePrimaryCurrency(currency: Currency) {
        currenciesRepository.savePrimaryCurrency(currency)
    }

    fun getPrimaryCurrencyFlow(): Flow<Currency> {
        return currenciesRepository.getPrimaryCurrencyFlow()
    }

    suspend fun getPrimaryCurrency(): Currency {
        return currenciesRepository.getPrimaryCurrency()
    }
}