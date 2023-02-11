package com.finance_tracker.finance_tracker.core.common

import dev.icerock.moko.graphics.Color

val Color.Companion.transparent: Color by lazy {
    Color(red = 0, green = 0, blue = 0, alpha = 0)
}