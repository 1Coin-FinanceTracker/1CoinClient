package com.finance_tracker.finance_tracker.core.navigation.tabs

import com.finance_tracker.finance_tracker.core.navigation.main.MainNavigationTree
import com.finance_tracker.finance_tracker.presentation.tabs_navigation.CustomConfiguration
import com.finance_tracker.finance_tracker.presentation.tabs_navigation.TabsNavigationScreen
import ru.alexgladkov.odyssey.compose.extensions.customNavigation
import ru.alexgladkov.odyssey.compose.extensions.screen
import ru.alexgladkov.odyssey.compose.extensions.tab
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder

fun RootComposeBuilder.tabsNavigationGraph() {
    customNavigation(
        name = MainNavigationTree.Main.name,
        tabsNavModel = CustomConfiguration(
            content = { TabsNavigationScreen() }
        )
    ) {
        TabsNavigationTree.values().forEach { tabNavItem ->
            tab(tabNavItem.tab) {
                screen(name = tabNavItem.name) {
                    tabNavItem.screen()
                }
            }
        }
    }
}