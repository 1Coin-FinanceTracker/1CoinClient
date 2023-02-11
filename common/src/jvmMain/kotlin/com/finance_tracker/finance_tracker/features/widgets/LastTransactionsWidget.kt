package com.finance_tracker.finance_tracker.features.widgets

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.ui.CoinWidget
import com.finance_tracker.finance_tracker.domain.models.Transaction
import com.finance_tracker.finance_tracker.domain.models.TransactionListModel
import com.finance_tracker.finance_tracker.features.home.LastTransactionsWidgetContent
import dev.icerock.moko.resources.compose.stringResource

@Composable
internal fun LastTransactionsWidget(
    lastTransactions: List<TransactionListModel.Data>,
    onClick: () -> Unit,
    onTransactionClick: (transaction: Transaction) -> Unit,
    modifier: Modifier = Modifier
) {
    CoinWidget(
        modifier = modifier,
        title = stringResource(MR.strings.home_last_transactions),
        withBorder = true,
        onClick = onClick
    ) {
        LastTransactionsWidgetContent(
            lastTransactions = lastTransactions,
            onTransactionClick = onTransactionClick,
        )
    }
}