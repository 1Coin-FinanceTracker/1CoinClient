package com.finance_tracker.finance_tracker.core.common

import org.koin.core.Koin
import org.koin.mp.KoinPlatformTools

fun getKoin(): Koin {
    return KoinPlatformTools.defaultContext().get()
}