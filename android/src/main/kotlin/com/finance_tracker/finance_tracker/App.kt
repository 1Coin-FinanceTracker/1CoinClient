package com.finance_tracker.finance_tracker

import android.app.Application
import com.finance_tracker.finance_tracker.core.common.AppInitializer
import com.finance_tracker.finance_tracker.core.common.di.Di
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class App : Application(), KoinComponent {

    private val appInitializer: AppInitializer by inject()

    override fun onCreate() {
        super.onCreate()

        Di.init(this)
        appInitializer.configure()
    }
}