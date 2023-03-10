package com.finance_tracker.finance_tracker.features.settings

import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.text.AnnotatedString
import com.finance_tracker.finance_tracker.core.common.DialogConfigurations
import com.finance_tracker.finance_tracker.core.common.view_models.BaseLocalsStorage
import com.finance_tracker.finance_tracker.core.navigtion.main.MainNavigationTree
import com.finance_tracker.finance_tracker.features.settings.views.SendingUsageDataDialog
import ru.alexgladkov.odyssey.compose.extensions.present
import ru.alexgladkov.odyssey.compose.extensions.push

fun handleAction(
    action: SettingsScreenAction,
    baseLocalsStorage: BaseLocalsStorage,
    uriHandler: UriHandler,
    clipboardManager: ClipboardManager
) {
    val rootController = baseLocalsStorage.rootController

    when (action) {
        SettingsScreenAction.Close -> {
            rootController.popBackStack()
        }

        SettingsScreenAction.OpenCategorySettingsScreen -> {
            rootController.findRootController().push(MainNavigationTree.CategorySettings.name)
        }

        SettingsScreenAction.ShowUsageDataInfoDialog -> {
            val modalNavController = rootController.findModalController()
            modalNavController.present(DialogConfigurations.alert) { key ->
                SendingUsageDataDialog(
                    onOkClick = {
                        modalNavController.popBackStack(key, animate = false)
                    }
                )
            }
        }

        SettingsScreenAction.OpenPrivacyScreen -> {
            // TODO
        }

        is SettingsScreenAction.OpenUri -> {
            uriHandler.openUri(action.uri)
        }

        is SettingsScreenAction.CopyUserId -> {
            clipboardManager.setText(AnnotatedString(action.userId))
        }

        SettingsScreenAction.OpenDashboardSettingsScreen -> {
            rootController.findRootController().push(MainNavigationTree.DashboardSettings.name)
        }

        SettingsScreenAction.OpenSelectCurrencyScreen -> {
            rootController.findRootController().push(MainNavigationTree.SelectCurrency.name)
        }
    }

}