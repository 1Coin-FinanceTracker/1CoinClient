package com.finance_tracker.finance_tracker.core.exceptions

class NoFieldException(
    private val fieldName: String
): IllegalStateException() {
    override val message: String
        get() = "'$fieldName' is missing"
}