package com.finance_tracker.finance_tracker.domain.models

import com.finance_tracker.finance_tracker.MR
import dev.icerock.moko.resources.StringResource

enum class ThemeMode(
    val modeId: String,
    val text: StringResource
) {
    Light(
        modeId = "light",
        text = MR.strings.settings_theme_light
    ),
    Dark(
        modeId = "dark",
        text = MR.strings.settings_theme_dark
    ),
    System(
        modeId = "system",
        text = MR.strings.settings_theme_system
    );

    companion object {

        fun from(modeId: String?): ThemeMode? {
            if (modeId == null) return null
            return ThemeMode.values().first { it.modeId == modeId }
        }

        fun getAllThemes(): List<ThemeMode> {
            return ThemeMode.values().toList()
        }
    }
}