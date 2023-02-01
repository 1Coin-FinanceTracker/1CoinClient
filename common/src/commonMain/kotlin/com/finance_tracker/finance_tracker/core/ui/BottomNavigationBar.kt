package com.finance_tracker.finance_tracker.core.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.width
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.LocalFixedInsets
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.theme.NoRippleTheme
import ru.alexgladkov.odyssey.compose.controllers.MultiStackRootController
import ru.alexgladkov.odyssey.compose.controllers.TabNavigationModel
import ru.alexgladkov.odyssey.compose.local.LocalRootController

@Composable
internal fun BottomNavigationBar(
    selectedTabItem: TabNavigationModel,
    onItemSelect: (tab: TabNavigationModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    val rootController = LocalRootController.current as MultiStackRootController
    val tabItems = rootController.tabItems.take(2) + null + rootController.tabItems.takeLast(2)
    val navigationBarsHeight = LocalFixedInsets.current.navigationBarsHeight
    BottomAppBar(
        modifier = modifier,
        backgroundColor = CoinTheme.color.backgroundSurface,
        elevation = 12.dp,
        contentColor = CoinTheme.color.primary,
        contentPadding = PaddingValues(
            bottom = navigationBarsHeight
        )
    ) {
        tabItems.forEach { item ->
            if (item != null) {
                BottomNavigationItem(
                    item = item,
                    selected = selectedTabItem == item,
                    onClick = { onItemSelect.invoke(item) }
                )
            } else {
                EmptyBottomNavigationItem()
            }
        }
    }
}

@Composable
private fun EmptyBottomNavigationItem() {
    Box(modifier = Modifier.width(46.dp))
}

@Composable
private fun RowScope.BottomNavigationItem(
    item: TabNavigationModel,
    selected: Boolean,
    onClick: () -> Unit
) {
    val itemConfiguration = item.tabInfo.tabItem.configuration
    CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
        BottomNavigationItem(
            icon = {
                NavBarIcon(
                    selected = selected,
                    selectedIcon = itemConfiguration.selectedIcon!!,
                    unselectedIcon = itemConfiguration.unselectedIcon!!,
                    contentDescription = itemConfiguration.title
                )
            },
            label = {
                Text(
                    text = itemConfiguration.title,
                    style = CoinTheme.typography.subtitle4,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            selectedContentColor = CoinTheme.color.primary,
            unselectedContentColor = CoinTheme.color.secondary,
            alwaysShowLabel = true,
            selected = selected,
            onClick = {
                onClick.invoke()
            }
        )
    }
}

@Composable
private fun NavBarIcon(
    selected: Boolean,
    selectedIcon: Painter,
    unselectedIcon: Painter,
    contentDescription: String,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        AnimatedVisibility(visible = selected, enter = fadeIn(), exit = fadeOut()) {
            Icon(
                painter = selectedIcon,
                contentDescription = contentDescription
            )
        }
        AnimatedVisibility(visible = !selected, enter = fadeIn(), exit = fadeOut()) {
            Icon(
                painter = unselectedIcon,
                contentDescription = contentDescription
            )
        }
    }
}