package com.finance_tracker.finance_tracker.core.common

import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.round

fun Float.toString(precision: Int): String {
    return this.toDouble().toString(precision)
}

fun Double.toString(precision: Int): String {
    val leftShifted = round(abs(this) * 10.0.pow(precision)).toInt()
    val s = StringBuilder(leftShifted.toString())

    // left-pad with 0's to ensure enough digits
    (1..precision + 1 - s.length).forEach { _ ->
        s.insert(0, "0")
    }

    // insert decimal point
    if (precision != 0) {
        s.insert(s.lastIndex - (precision - 1), ".")
    }

    // (re)insert negative sign
    if (this < 0) {
        s.insert(0, "-")
    }

    return s.toString()
}