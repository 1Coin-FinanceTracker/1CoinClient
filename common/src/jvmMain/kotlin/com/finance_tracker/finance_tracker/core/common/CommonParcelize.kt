package com.finance_tracker.finance_tracker.core.common

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY)
expect annotation class Parcelize()

expect interface Parcelable