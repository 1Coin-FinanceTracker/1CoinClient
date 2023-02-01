package com.finance_tracker.finance_tracker.presentation.tabs_navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.ui.Modifier
import com.finance_tracker.finance_tracker.core.common.clicks.scaleClickAnimation
import com.finance_tracker.finance_tracker.core.common.getKoin
import com.finance_tracker.finance_tracker.core.navigation.main.MainNavigationTree
import com.finance_tracker.finance_tracker.core.navigation.tabs.TabsNavigationTree
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.theme.NoRippleTheme
import com.finance_tracker.finance_tracker.core.ui.BottomNavigationBar
import com.finance_tracker.finance_tracker.core.ui.rememberVectorPainter
import com.finance_tracker.finance_tracker.presentation.tabs_navigation.analytics.TabsNavigationAnalytics
import ru.alexgladkov.odyssey.compose.base.AnimatedHost
import ru.alexgladkov.odyssey.compose.controllers.MultiStackRootController
import ru.alexgladkov.odyssey.compose.controllers.TabNavigationModel
import ru.alexgladkov.odyssey.compose.extensions.push
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.core.toScreenBundle

@Composable
internal fun TabsNavigationScreen() {

    val rootController = LocalRootController.current as MultiStackRootController
    val nullableSelectedTabItem by rootController.stackChangeObserver.collectAsState()
    val selectedTabItem = nullableSelectedTabItem ?: return
    val analytics: TabsNavigationAnalytics = remember { getKoin().get() }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                selectedTabItem = selectedTabItem,
                onItemSelect = { tab ->
                    analytics.trackTabClick(tab)

                    val position = rootController.tabItems.indexOf(tab)
                    rootController.switchTab(position)
                }
            )
        },
        floatingActionButton = {
            CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
                FloatingActionButton(
                    modifier = Modifier
                        .scaleClickAnimation(),
                    backgroundColor = CoinTheme.color.primary,
                    contentColor = CoinTheme.color.primaryVariant,
                    onClick = {
                        analytics.trackAddTransactionClick()
                        rootController.findRootController()
                            .push(MainNavigationTree.AddTransaction.name)
                    }
                ) {
                    Icon(
                        painter = rememberVectorPainter("ic_plus"),
                        contentDescription = null,
                        tint = CoinTheme.color.white
                    )
                }
            }
        },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center
    ) {
        TabNavigator(
            modifier = Modifier.fillMaxSize(),
            startScreen = TabsNavigationTree.Home.name,
            currentTab = selectedTabItem
        )
    }
}

@Composable
private fun TabNavigator(
    startScreen: String?,
    currentTab: TabNavigationModel,
    modifier: Modifier = Modifier
) {
    val configuration = currentTab.rootController.currentScreen.collectAsState()
    val saveableStateHolder = rememberSaveableStateHolder()

    saveableStateHolder.SaveableStateProvider(currentTab.tabInfo.tabItem.name) {
        Box(modifier = modifier) {
            CompositionLocalProvider(
                LocalRootController provides currentTab.rootController
            ) {
                configuration.value?.let { navConfig ->
                    AnimatedHost(
                        currentScreen = navConfig.screen.toScreenBundle(),
                        animationType = navConfig.screen.animationType,
                        screenToRemove = navConfig.screenToRemove?.toScreenBundle(),
                        isForward = navConfig.screen.isForward,
                        onScreenRemove = currentTab.rootController.onScreenRemove
                    ) {
                        val rootController = currentTab.rootController
                        rootController.renderScreen(it.realKey, it.params)
                    }
                }
            }
        }
    }

    LaunchedEffect(currentTab) {
        currentTab.rootController.drawCurrentScreen(startScreen = startScreen)
    }
}