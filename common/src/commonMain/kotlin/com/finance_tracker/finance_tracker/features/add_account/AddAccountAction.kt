package com.finance_tracker.finance_tracker.features.add_account

import com.finance_tracker.finance_tracker.domain.models.Account
import dev.icerock.moko.resources.StringResource

sealed interface AddAccountAction {
    object Close: AddAccountAction
    data class ShowToast(
        val textId: StringResource
    ): AddAccountAction
    data class ShowDeleteDialog(val account: Account): AddAccountAction
    data class DismissDeleteDialog(val dialogKey: String): AddAccountAction
    data class BackToScreen(val screenName: String): AddAccountAction
}