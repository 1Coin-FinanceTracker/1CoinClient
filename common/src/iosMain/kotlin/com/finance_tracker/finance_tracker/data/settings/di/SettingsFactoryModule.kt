package com.finance_tracker.finance_tracker.data.settings.di

import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.Settings
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual class SettingsFactoryModule {

    actual fun create(): Module = module {
        singleOf<Settings.Factory>(NSUserDefaultsSettings::Factory)
    }
}