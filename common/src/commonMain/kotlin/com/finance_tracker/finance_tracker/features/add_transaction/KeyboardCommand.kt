package com.finance_tracker.finance_tracker.features.add_transaction

sealed interface KeyboardCommand {
    data class Digit(val value: Int): KeyboardCommand
    object Point: KeyboardCommand
    object Delete: KeyboardCommand
}