package com.finance_tracker.finance_tracker.features.tabs_navigation.tabs

import androidx.compose.runtime.Composable
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.common.rememberAsyncImagePainter
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import dev.icerock.moko.resources.compose.stringResource
import ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation.TabConfiguration
import ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation.TabItem

class AccountsTab : TabItem() {

    override val configuration: TabConfiguration
        @Composable
        get() {
            return TabConfiguration(
                title = stringResource(MR.strings.tab_accounts),
                selectedIcon = rememberAsyncImagePainter(MR.files.ic_wallet_active),
                unselectedIcon = rememberAsyncImagePainter(MR.files.ic_wallet_inactive),
                selectedColor = CoinTheme.color.primary,
                unselectedColor = CoinTheme.color.content
            )
        }
}