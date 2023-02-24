package com.finance_tracker.finance_tracker.core_ios.di

import com.finance_tracker.finance_tracker.core.common.EmptyContext
import com.finance_tracker.finance_tracker.core.common.di.Di

object DiManager {

    val core = CoreDiModule()
    val settings = SettingsDiModule()

    fun configure() {
        Di.init(EmptyContext)
    }
}