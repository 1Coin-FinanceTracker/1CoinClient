package com.finance_tracker.finance_tracker.presentation.add_account

import androidx.compose.material.ScaffoldState
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.common.DialogConfigurations
import com.finance_tracker.finance_tracker.core.common.localizedString
import com.finance_tracker.finance_tracker.core.common.view_models.BaseLocalsStorage
import com.finance_tracker.finance_tracker.core.ui.DeleteDialog
import com.finance_tracker.finance_tracker.domain.models.Account
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.launch
import ru.alexgladkov.odyssey.compose.extensions.present

sealed interface AddAccountAction {
    object Close: AddAccountAction
    data class ShowToast(
        val textId: StringResource
    ): AddAccountAction
    data class ShowDeleteDialog(val account: Account): AddAccountAction
    data class DismissDeleteDialog(val dialogKey: String): AddAccountAction
    data class BackToScreen(val screenName: String): AddAccountAction
}

fun handleAction(
    action: AddAccountAction,
    baseLocalsStorage: BaseLocalsStorage,
    scaffoldState: ScaffoldState,
    onConfirmDeletingClick: (account: Account, dialogKey: String) -> Unit,
    onCancelDeletingClick: (account: Account, dialogKey: String) -> Unit
) {
    val rootController = baseLocalsStorage.rootController
    when (action) {
        AddAccountAction.Close -> {
            baseLocalsStorage.rootController.popBackStack()
        }
        is AddAccountAction.ShowToast -> {
            baseLocalsStorage.coroutineScope.launch {
                scaffoldState.snackbarHostState.showSnackbar(
                    message = action.textId.localizedString(baseLocalsStorage.context),
                    actionLabel = MR.strings.got_it.localizedString(baseLocalsStorage.context)
                )
            }
        }
        is AddAccountAction.ShowDeleteDialog -> {
            val modalNavController = rootController.findModalController()
            modalNavController.present(DialogConfigurations.alert) { key ->
                DeleteDialog(
                    titleEntity = stringResource(MR.strings.deleting_account),
                    onCancelClick = { onCancelDeletingClick.invoke(action.account, key) },
                    onDeleteClick = { onConfirmDeletingClick.invoke(action.account, key) }
                )
            }
        }
        is AddAccountAction.DismissDeleteDialog -> {
            val modalNavController = rootController.findModalController()
            modalNavController.popBackStack(action.dialogKey, animate = false)
        }
        is AddAccountAction.BackToScreen -> {
            val navController = rootController.findRootController()
            navController.backToScreen(action.screenName)
        }
    }
}