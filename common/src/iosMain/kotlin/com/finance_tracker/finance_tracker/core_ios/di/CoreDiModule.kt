package com.finance_tracker.finance_tracker.core_ios.di

import com.finance_tracker.finance_tracker.core.common.AppInitializer
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class CoreDiModule: KoinComponent {
    fun appInitializer() = get<AppInitializer>()
}