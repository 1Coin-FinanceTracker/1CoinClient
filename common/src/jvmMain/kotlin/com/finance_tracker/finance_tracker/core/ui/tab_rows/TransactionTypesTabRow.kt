package com.finance_tracker.finance_tracker.core.ui.tab_rows

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.icerock.moko.resources.compose.stringResource

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