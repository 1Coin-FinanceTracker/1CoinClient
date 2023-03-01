package com.finance_tracker.finance_tracker.domain.models

enum class ThemeMode(val modeId: String) {
    Light(modeId = "light"),
    Dark(modeId = "dark"),
    System(modeId = "system");

    companion object {

        fun from(modeId: String?): ThemeMode? {
            if (modeId == null) return null
            return ThemeMode.values().first { it.modeId == modeId }
        }
    }
}