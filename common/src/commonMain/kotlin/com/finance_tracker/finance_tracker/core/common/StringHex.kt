package com.finance_tracker.finance_tracker.core.common

import dev.icerock.moko.graphics.Color

@Suppress("MagicNumber")
fun Color.toHexString(): String {
    return "#${argb.toUInt().toString(16).takeLast(6)}"
}