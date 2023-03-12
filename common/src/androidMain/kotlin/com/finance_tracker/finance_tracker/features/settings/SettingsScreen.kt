package com.finance_tracker.finance_tracker.features.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.common.StoredViewModel
import com.finance_tracker.finance_tracker.core.common.navigationBarsPadding
import com.finance_tracker.finance_tracker.core.common.view_models.watchViewActions
import com.finance_tracker.finance_tracker.core.feature_flags.FeatureFlag
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.features.settings.views.ListGroupHeader
import com.finance_tracker.finance_tracker.features.settings.views.ListItemDivider
import com.finance_tracker.finance_tracker.features.settings.views.SettingsScreenTopBar
import com.finance_tracker.finance_tracker.features.settings.views.SettingsVersionAndUserIdInfo
import com.finance_tracker.finance_tracker.features.settings.views.items.SettingsCategoriesItem
import com.finance_tracker.finance_tracker.features.settings.views.items.SettingsDashboardItem
import com.finance_tracker.finance_tracker.features.settings.views.items.SettingsMainCurrencyItem
import com.finance_tracker.finance_tracker.features.settings.views.items.SettingsMyProfileItem
import com.finance_tracker.finance_tracker.features.settings.views.items.SettingsPrivacyItem
import com.finance_tracker.finance_tracker.features.settings.views.items.SettingsSendingUsageDataItem
import com.finance_tracker.finance_tracker.features.settings.views.items.SettingsTelegramChatItem
import com.finance_tracker.finance_tracker.features.settings.views.items.SettingsThemeItem
import dev.icerock.moko.resources.compose.stringResource

@Composable
internal fun SettingsScreen() {
    StoredViewModel<SettingsScreenViewModel> { viewModel ->

        val uriHandler = LocalUriHandler.current
        val clipboardManager = LocalClipboardManager.current

        viewModel.watchViewActions { action, baseLocalsStorage ->
            handleAction(
                action = action,
                baseLocalsStorage = baseLocalsStorage,
                uriHandler = uriHandler,
                clipboardManager = clipboardManager,
            )
        }

        Column(
            modifier = Modifier
                .background(CoinTheme.color.background)
                .fillMaxHeight()
        ) {
            SettingsScreenTopBar(
                onBackClick = viewModel::onBackClick
            )
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
                    .navigationBarsPadding(),
            ) {

                Spacer(modifier = Modifier.height(12.dp))

                val featuresManager = viewModel.featuresManager
                if (featuresManager.isEnabled(FeatureFlag.Authorization)) {
                    ListGroupHeader(
                        text = stringResource(MR.strings.settings_my_profile)
                    )

                    val userEmail by viewModel.userEmail.collectAsState()
                    val isUserAuthorized by viewModel.isUserAuthorized.collectAsState()
                    SettingsMyProfileItem(
                        userEmail = userEmail,
                        isUserAuthorized = isUserAuthorized,
                    )

                    ListItemDivider()
                }

                ListGroupHeader(
                    text = stringResource(MR.strings.settings_configuration)
                )

                val primaryCurrency by viewModel.primaryCurrency.collectAsState()

                SettingsMainCurrencyItem(
                    onClick = viewModel::onSelectCurrencyClick,
                    primaryCurrency = primaryCurrency
                )

                SettingsCategoriesItem(
                    onClick = viewModel::onCategorySettingsClick
                )

                if (featuresManager.isEnabled(FeatureFlag.WidgetsSettings)) {
                    SettingsDashboardItem(
                        onClick = viewModel::onDashboardSettingsClick
                    )
                }

                ListItemDivider()

                ListGroupHeader(
                    text = stringResource(MR.strings.settings_other)
                )

                val isSendingUsageDataEnabled by viewModel.isSendingUsageDataEnabled.collectAsState()
                SettingsSendingUsageDataItem(
                    isEnabled = isSendingUsageDataEnabled,
                    onChange = viewModel::onSendingUsageDataClick,
                    onInfoClick = viewModel::onSendingUsageDataInfoClick
                )

                SettingsPrivacyItem(
                    onClick = viewModel::onPrivacyClick
                )

                if (viewModel.featuresManager.isEnabled(FeatureFlag.ChooseTheme)) {
                    val themeMode by viewModel.currentTheme.collectAsState()
                    SettingsThemeItem(
                        themes = viewModel.themes,
                        themeMode = themeMode,
                        onClick = viewModel::onThemeChange
                    )
                }

                SettingsTelegramChatItem(
                    onClick = viewModel::onTelegramCommunityClick
                )

                val userId by viewModel.userId.collectAsState()
                SettingsVersionAndUserIdInfo(
                    versionName = viewModel.versionName,
                    userId = userId,
                    onCopyUserId = viewModel::onCopyUserId
                )
            }
        }
    }
}