package com.finance_tracker.finance_tracker.core.common

import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.AlertConfiguration
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.ModalSheetConfiguration

object DialogConfigurations {

    val alert = AlertConfiguration(
        cornerRadius = 8,
        maxWidth = 0.93f
    )

    val bottomSheet = ModalSheetConfiguration(
        cornerRadius = 12
    )
}