package com.finance_tracker.finance_tracker.domain.models

data class Amount(
    val currency: Currency,
    val amountValue: Double
) {

    @Throws(IllegalStateException::class)
    fun convertToCurrency(
        currencyRates: CurrencyRates,
        toCurrency: Currency
    ): Double {
        return if (currency == toCurrency) {
            amountValue
        } else {
            val fromCurrencyRates = currencyRates[currency.code]
                ?: return 0.0
            val toCurrencyRates = currencyRates[toCurrency.code]
                ?: return 0.0

            return amountValue / fromCurrencyRates.rate * toCurrencyRates.rate
        }
    }

    companion object {

        val default = Amount(
            currency = Currency.default,
            amountValue = 0.0
        )
    }
}