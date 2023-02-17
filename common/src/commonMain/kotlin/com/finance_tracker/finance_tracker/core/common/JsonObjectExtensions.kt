package com.finance_tracker.finance_tracker.core.common

import com.finance_tracker.finance_tracker.core.exceptions.NoFieldException
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject

fun JsonObject.getOrThrow(key: String): JsonElement {
    return get(key) ?: throw NoFieldException(key)
}