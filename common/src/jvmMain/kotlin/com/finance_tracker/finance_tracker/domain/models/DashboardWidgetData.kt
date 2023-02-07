package com.finance_tracker.finance_tracker.domain.models

import com.finance_tracker.finance_tracker.MR
import dev.icerock.moko.resources.StringResource

data class DashboardWidgetData(
    val id: Int,
    val type: DashboardWidgetType,
    val isEnabled: Boolean,
    val position: Int
) {

    enum class DashboardWidgetType(
        val nameRes: StringResource,
        val isEditable: Boolean,
    ) {
        MyAccounts(
            nameRes = MR.strings.widget_settings_item_my_accounts,
            isEditable = false
        ),
        ExpenseTrend(
            nameRes = MR.strings.widget_settings_item_expense_trend,
            isEditable = true
        ),
        IncomeTrend(
            nameRes = MR.strings.widget_settings_item_income_trend,
            isEditable = true
        ),
        ExpenseByCategory(
            nameRes = MR.strings.widget_settings_item_expense_by_category,
            isEditable = true
        ),
        IncomeByCategory(
            nameRes = MR.strings.widget_settings_item_income_by_category,
            isEditable = true
        ),
        LastTransactions(
            nameRes = MR.strings.widget_settings_item_last_transactions,
            isEditable = true
        );
    }

    companion object {
        val default = listOf(
            DashboardWidgetData(
                id = 0,
                type = DashboardWidgetType.MyAccounts,
                isEnabled = true,
                position = 0
            ),
            DashboardWidgetData(
                id = 1,
                type = DashboardWidgetType.ExpenseTrend,
                isEnabled = false,
                position = 1
            ),
            DashboardWidgetData(
                id = 2,
                type = DashboardWidgetType.IncomeTrend,
                isEnabled = false,
                position = 2
            ),
            DashboardWidgetData(
                id = 3,
                type = DashboardWidgetType.ExpenseByCategory,
                isEnabled = false,
                position = 3
            ),
            DashboardWidgetData(
                id = 4,
                type = DashboardWidgetType.IncomeByCategory,
                isEnabled = false,
                position = 4
            ),
            DashboardWidgetData(
                id = 5,
                type = DashboardWidgetType.LastTransactions,
                isEnabled = true,
                position = 5
            ),
        )
    }
}

fun DashboardWidgetData.getAnalyticsName(): String {
    return when (type) {
        DashboardWidgetData.DashboardWidgetType.MyAccounts -> "MyAccounts"
        DashboardWidgetData.DashboardWidgetType.ExpenseTrend -> "ExpenseTrend"
        DashboardWidgetData.DashboardWidgetType.IncomeTrend -> "IncomeTrend"
        DashboardWidgetData.DashboardWidgetType.ExpenseByCategory -> "ExpenseByCategory"
        DashboardWidgetData.DashboardWidgetType.IncomeByCategory -> "IncomeByCategory"
        DashboardWidgetData.DashboardWidgetType.LastTransactions -> "LastTransactions"
    }
}