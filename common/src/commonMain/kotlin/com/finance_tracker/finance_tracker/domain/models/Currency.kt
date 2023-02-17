package com.finance_tracker.finance_tracker.domain.models

data class Currency(
    val code: String,
    val symbol: String
) {
    companion object {

        private val USD = Currency(code = "USD", symbol = "$")
        private val EUR = Currency(code = "EUR", symbol = "€")
        private val UAH = Currency(code = "UAH", symbol = "₴")
        private val RUB = Currency(code = "RUB", symbol = "₽")
        private val BYN = Currency(code = "BYN", symbol = "Br")
        private val TRY = Currency(code = "TRY", symbol = "₺")
        private val GBP = Currency(code = "GBP", symbol = "£")
        private val KZT = Currency(code = "KZT", symbol = "₸")
        private val AMD = Currency(code = "AMD", symbol = "֏")
        private val GEL = Currency(code = "GEL", symbol = "₾")
        private val RSD = Currency(code = "RSD", symbol = "din")
        private val UZS = Currency(code = "UZS", symbol = "Sʻ")
        private val HUF = Currency(code = "HUF", symbol = "ƒ")
        private val AED = Currency(code = "AED", symbol = "Dh")
        private val COP = Currency(code = "COP", symbol = "COL$")
        private val MXN = Currency(code = "MXN", symbol = "$")

        val list = listOf(
            USD, EUR, UAH, RUB, BYN, TRY, GBP, KZT,
            AMD, GEL, RSD, UZS, HUF, AED, COP, MXN
        )
        val default = USD

        private val map = list.associateBy { it.code }

        fun getByCode(name: String): Currency {
            return map[name]!!
        }
    }
}