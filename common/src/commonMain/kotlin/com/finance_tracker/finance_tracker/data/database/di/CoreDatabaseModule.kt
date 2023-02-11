package com.finance_tracker.finance_tracker.data.database.di

import com.finance_tracker.finance_tracker.AppDatabase
import com.finance_tracker.finance_tracker.core.common.date.Format
import com.finance_tracker.finance_tracker.core.common.date.format
import com.finance_tracker.finance_tracker.data.database.DatabaseInitializer
import com.finance_tracker.finance_tracker.data.database.DriverFactory
import com.financetracker.financetracker.data.AccountsEntity
import com.financetracker.financetracker.data.TransactionsEntity
import com.squareup.sqldelight.ColumnAdapter
import com.squareup.sqldelight.EnumColumnAdapter
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val coreDatabaseModule = module {
    singleOf(::DatabaseInitializer)
    singleOf(::provideAppDatabase)
    factory { get<AppDatabase>().transactionsEntityQueries }
    factory { get<AppDatabase>().smsMessageEntityQueries }
    factory { get<AppDatabase>().accountsEntityQueries }
    factory { get<AppDatabase>().categoriesEntityQueries }
    factory { get<AppDatabase>().currencyRatesEntityQueries }
    factory { get<AppDatabase>().dashboardWidgetEntityQueries }
}

private fun provideAppDatabase(driverFactory: DriverFactory): AppDatabase {

    val dateAdapter = object : ColumnAdapter<LocalDateTime, String> {

        override fun decode(databaseValue: String): LocalDateTime {
            return Instant.parse(databaseValue).toLocalDateTime(TimeZone.UTC)
        }

        override fun encode(value: LocalDateTime): String {
            return value.format(Format.Iso)
        }
    }

    val driver = driverFactory.createDriver()
    return AppDatabase(
        driver = driver,
        TransactionsEntityAdapter = TransactionsEntity.Adapter(
            typeAdapter = EnumColumnAdapter(),
            insertionDateAdapter = dateAdapter,
            dateAdapter = dateAdapter
        ),
        AccountsEntityAdapter = AccountsEntity.Adapter(
            typeAdapter = EnumColumnAdapter(),
        )
    )
}