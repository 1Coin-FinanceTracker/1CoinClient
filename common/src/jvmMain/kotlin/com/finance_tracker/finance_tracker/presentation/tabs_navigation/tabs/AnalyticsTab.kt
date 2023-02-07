package com.finance_tracker.finance_tracker.presentation.tabs_navigation.tabs

import androidx.compose.runtime.Composable
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.rememberVectorPainter
import dev.icerock.moko.resources.compose.stringResource
import ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation.TabConfiguration
import ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation.TabItem

class AnalyticsTab : TabItem() {

    override val configuration: TabConfiguration
        @Composable
        get() {
            return TabConfiguration(
                title = stringResource(MR.strings.tab_analytics),
                selectedIcon = rememberVectorPainter("ic_analytics_active"),
                unselectedIcon = rememberVectorPainter("ic_analytics_inactive"),
                selectedColor = CoinTheme.color.primary,
                unselectedColor = CoinTheme.color.content,
            )
        }
}