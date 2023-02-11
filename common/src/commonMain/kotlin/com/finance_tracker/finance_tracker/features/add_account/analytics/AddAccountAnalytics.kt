package com.finance_tracker.finance_tracker.features.add_account.analytics

import com.finance_tracker.finance_tracker.core.analytics.BaseAnalytics
import com.finance_tracker.finance_tracker.core.common.toHexString
import com.finance_tracker.finance_tracker.domain.models.Account
import com.finance_tracker.finance_tracker.domain.models.AccountColorModel

class AddAccountAnalytics: BaseAnalytics() {

    override val screenName = "AddAccountScreen"

    fun trackScreenOpen(account: Account?) {
        val properties = mutableMapOf<String, Any>()
        if (account != null) {
            properties["account_name"] = account.name
        }
        trackScreenOpen(properties = properties)
    }

    fun trackDeleteClick(account: Account) {
        trackClick(
            eventName = "DeleteAccount",
            properties = mapOf(
                "name" to account.name,
                "type" to account.type.analyticsName,
                "color" to account.colorModel.color.toHexString(),
                "color_name" to account.colorModel.name
            )
        )
    }

    fun trackConfirmDeletingClick(account: Account) {
        trackClick(
            eventName = "ConfirmDeletingAccount",
            properties = mapOf(
                "name" to account.name,
                "type" to account.type.analyticsName,
                "color" to account.colorModel.color.toHexString(),
                "color_name" to account.colorModel.name
            )
        )
    }

    fun trackCancelDeletingClick(account: Account) {
        trackClick(
            eventName = "CancelDeletingAccount",
            properties = mapOf(
                "name" to account.name,
                "type" to account.type.analyticsName,
                "color" to account.colorModel.color.toHexString(),
                "color_name" to account.colorModel.name
            )
        )
    }

    fun trackSaveClick(
        accountName: String?,
        accountType: Account.Type?,
        colorModel: AccountColorModel?,
    ) {
        trackClick(
            eventName = "SaveAccount",
            properties = mapOf(
                "name" to accountName,
                "type" to accountType?.analyticsName,
                "color" to colorModel?.color?.toHexString(),
                "color_name" to colorModel?.name
            )
        )
    }

    fun trackAddClick(
        accountName: String?,
        accountType: Account.Type?,
        colorModel: AccountColorModel?,
    ) {
        trackClick(
            eventName = "AddAccount",
            properties = mapOf(
                "name" to accountName,
                "type" to accountType?.analyticsName,
                "color" to colorModel?.color?.toHexString(),
                "color_name" to colorModel?.name
            )
        )
    }
}