package com.finance_tracker.finance_tracker.domain.models

import androidx.compose.ui.graphics.Color
import com.finance_tracker.finance_tracker.MR
import dev.icerock.moko.resources.StringResource

@Suppress("MagicNumber")
enum class AccountColorModel(
    val id: Int,
    val color: Color,
    val colorName: StringResource
) {
    EastBay(
        id = 0,
        color = Color(0xFF4B3F72),
        colorName = MR.strings.color_purple
    ),
    RobinsEggBlue(
        id = 1,
        color = Color(0xFF07BEB8),
        colorName = MR.strings.color_blue
    ),
    GoldenTainoi(
        id = 2,
        color = Color(0xFFC75146),
        colorName = MR.strings.color_red
    ),
    CopperRose(
        id = 3,
        color = Color(0xFF9B6A6C),
        colorName = MR.strings.color_brown
    ),
    Bouquet(
        id = 4,
        color = Color(0xFFB37BA4),
        colorName = MR.strings.color_lavender
    ),
    DeepBlush(
        id = 5,
        color = Color(0xFFE36588),
        colorName = MR.strings.color_pink
    ),
    BreakerBay(
        id = 6,
        color = Color(0xFF519E8A),
        colorName = MR.strings.color_green
    ),
    RedDamask(
        id = 7,
        color = Color(0xFFD56F3E),
        colorName = MR.strings.color_orange
    ),
    Elm(
        id = 8,
        color = Color(0xFF1F7A8C),
        colorName = MR.strings.color_seaweed
    ),
    Stack(
        id = 9,
        color = Color(0xFF838E83),
        colorName = MR.strings.color_grey
    );

    companion object {

        val defaultAccountColor = EastBay

        fun from(id: Int): AccountColorModel {
            return AccountColorModel.values()
                .firstOrNull { it.id == id } ?: defaultAccountColor
        }
    }
}