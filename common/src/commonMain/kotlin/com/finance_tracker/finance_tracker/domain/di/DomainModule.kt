package com.finance_tracker.finance_tracker.domain.di

import com.finance_tracker.finance_tracker.domain.interactors.AccountsInteractor
import com.finance_tracker.finance_tracker.domain.interactors.AuthInteractor
import com.finance_tracker.finance_tracker.domain.interactors.CategoriesInteractor
import com.finance_tracker.finance_tracker.domain.interactors.CurrenciesInteractor
import com.finance_tracker.finance_tracker.domain.interactors.DashboardSettingsInteractor
import com.finance_tracker.finance_tracker.domain.interactors.ThemeInteractor
import com.finance_tracker.finance_tracker.domain.interactors.TransactionsInteractor
import com.finance_tracker.finance_tracker.domain.interactors.UserInteractor
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

internal val domainModule = module {
    factoryOf(::TransactionsInteractor)
    factoryOf(::UserInteractor)
    factoryOf(::CurrenciesInteractor)
    factoryOf(::CategoriesInteractor)
    factoryOf(::AccountsInteractor)
    factoryOf(::DashboardSettingsInteractor)
    factoryOf(::AuthInteractor)
    factoryOf(::ThemeInteractor)
}