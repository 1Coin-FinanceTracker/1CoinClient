package com.finance_tracker.finance_tracker.core.common

private val floatNumberRegex = Regex("^\\d+\\.?\\d{0,2}\$")
private val floatNumberWithCommaRegex = Regex("^\\d+(\\.|,)?\\d{0,2}\$")

fun String.isFloatNumber(withComma: Boolean = false): Boolean {
    val regex = if (withComma) {
        floatNumberWithCommaRegex
    } else {
        floatNumberRegex
    }
    return regex.matches(this)
}