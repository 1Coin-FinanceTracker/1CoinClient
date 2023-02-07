package com.finance_tracker.finance_tracker.core.ui.tab_rows

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.domain.models.TransactionType
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.compose.stringResource

enum class TransactionTypeTab(
    val textId: StringResource,
    val analyticsName: String
) {
    Expense(
        textId = MR.strings.tab_expense,
        analyticsName = "Expense"
    ),
    Income(
        textId = MR.strings.tab_income,
        analyticsName = "Income"
    ),
}

fun TransactionTypeTab.toTransactionType(): TransactionType {
    return when (this) {
        TransactionTypeTab.Expense -> TransactionType.Expense
        TransactionTypeTab.Income -> TransactionType.Income
    }
}

fun TransactionType.toTransactionTypeTab(): TransactionTypeTab {
    return when (this) {
        TransactionType.Expense -> TransactionTypeTab.Expense
        TransactionType.Income -> TransactionTypeTab.Income
    }
}

@Composable
internal fun TransactionTypesTabRow(
    modifier: Modifier = Modifier,
    selectedType: TransactionTypeTab = TransactionTypeTab.Expense,
    hasBottomDivider: Boolean = true,
    isHorizontallyCentered: Boolean = false,
    onSelect: (TransactionTypeTab) -> Unit = {}
) {
    val tabs = TransactionTypeTab.values()
    val tabToIndexMap = tabs
        .mapIndexed { index, tab -> tab to index }
        .toMap()

    CoinTabRow(
        data = tabs.map { stringResource(it.textId) },
        modifier = modifier,
        selectedTabIndex = tabToIndexMap.getValue(selectedType),
        hasBottomDivider = hasBottomDivider,
        isHorizontallyCentered = isHorizontallyCentered,
        onTabSelect = { index -> onSelect.invoke(tabs[index]) }
    )
}