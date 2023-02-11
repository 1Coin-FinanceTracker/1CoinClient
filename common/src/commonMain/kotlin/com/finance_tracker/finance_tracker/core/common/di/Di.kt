package com.finance_tracker.finance_tracker.core.common.di

import com.finance_tracker.finance_tracker.core.common.Context
import com.finance_tracker.finance_tracker.core.di.commonModules
import com.finance_tracker.finance_tracker.core.di.featureModules
import org.koin.core.context.startKoin

object Di {

    fun init(context: Context) {
        startKoin {
            platformKoinSetup(context)
            modules(commonModules() + featureModules())
        }
    }
}