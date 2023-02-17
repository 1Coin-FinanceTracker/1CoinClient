package com.finance_tracker.finance_tracker.domain.models

import com.finance_tracker.finance_tracker.MR
import dev.icerock.moko.graphics.Color
import dev.icerock.moko.resources.StringResource

@Suppress("MagicNumber")
enum class AccountColorModel(
    val id: Int,
    val color: Color,
    val colorName: StringResource
) {
    EastBay(
        id = 0,
        color = Color(0x4B3F72FF),
        colorName = MR.strings.color_purple
    ),
    RobinsEggBlue(
        id = 1,
        color = Color(0x07BEB8FF),
        colorName = MR.strings.color_blue
    ),
    GoldenTainoi(
        id = 2,
        color = Color(0xC75146FF),
        colorName = MR.strings.color_red
    ),
    CopperRose(
        id = 3,
        color = Color(0x9B6A6CFF),
        colorName = MR.strings.color_brown
    ),
    Bouquet(
        id = 4,
        color = Color(0xB37BA4FF),
        colorName = MR.strings.color_lavender
    ),
    DeepBlush(
        id = 5,
        color = Color(0xE36588FF),
        colorName = MR.strings.color_pink
    ),
    BreakerBay(
        id = 6,
        color = Color(0x519E8AFF),
        colorName = MR.strings.color_green
    ),
    RedDamask(
        id = 7,
        color = Color(0xD56F3EFF),
        colorName = MR.strings.color_orange
    ),
    Elm(
        id = 8,
        color = Color(0x1F7A8CFF),
        colorName = MR.strings.color_seaweed
    ),
    Stack(
        id = 9,
        color = Color(0x838E83FF),
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