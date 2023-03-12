package com.finance_tracker.finance_tracker.features.settings.views.items

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.common.toDp
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.rememberVectorPainter
import com.finance_tracker.finance_tracker.domain.models.ThemeMode
import com.finance_tracker.finance_tracker.features.settings.views.ListItem
import com.finance_tracker.finance_tracker.features.settings.views.dropdown_menus.ThemesDropdownMenu
import dev.icerock.moko.resources.compose.stringResource

@Composable
internal fun SettingsThemeItem(
    themes: List<ThemeMode>,
    themeMode: ThemeMode,
    onClick: (themeMode: ThemeMode) -> Unit,
    modifier: Modifier = Modifier
) {
    val currencyMenuExpanded = remember { mutableStateOf(false) }
    val menuOffsetX = remember { mutableStateOf(0) }
    val menuOffsetY = remember { mutableStateOf(0) }

    Box(
        modifier = modifier
    ) {
        ListItem(
            modifier = Modifier
                .onGloballyPositioned {
                    menuOffsetX.value = it.positionInParent().x.toInt() + it.size.width
                    menuOffsetY.value = it.positionInParent().y.toInt() - it.size.height
                },
            iconLeftPainter = rememberVectorPainter("ic_privacy"),
            text = stringResource(MR.strings.settings_theme),
            onClick = remember { { currencyMenuExpanded.value = true } },
            iconRight = {
                Text(
                    modifier = Modifier
                        .padding(end = 16.dp),
                    text = stringResource(themeMode.text),
                    color = CoinTheme.color.primary,
                    style = CoinTheme.typography.body1_medium
                )
            }
        )

        ThemesDropdownMenu(
            items = themes,
            expandedState = currencyMenuExpanded,
            onThemeSelect = onClick,
            xOffset = menuOffsetX.value.toDp() - 124.dp,
            yOffset = menuOffsetY.value.toDp()
        )
    }
}