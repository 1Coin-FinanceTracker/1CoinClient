package com.finance_tracker.finance_tracker.features.tabs_navigation

import androidx.compose.runtime.Composable
import ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation.CustomNavConfiguration
import ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation.TabsNavModel

class CustomConfiguration(private val content: @Composable (params: Any?) -> Unit) :
    TabsNavModel<CustomNavConfiguration>() {

    override val navConfiguration: CustomNavConfiguration
        @Composable
        get() {
            return CustomNavConfiguration(
               content = content
            )
        }
}
