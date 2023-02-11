package com.finance_tracker.finance_tracker.core.common

enum class BuildType(
    val buildTypeName: String
) {
    Debug(buildTypeName = "debug"),
    Staging(buildTypeName = "staging"),
    Release(buildTypeName = "release");

    companion object {
        fun byName(name: String): BuildType {
            return BuildType.values()
                .first { it.buildTypeName == name }
        }
    }
}