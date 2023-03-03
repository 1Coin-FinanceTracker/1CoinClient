package com.finance_tracker.finance_tracker.features.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.ui.transactions.TransactionItem
import com.finance_tracker.finance_tracker.domain.models.Transaction
import com.finance_tracker.finance_tracker.domain.models.TransactionListModel

@Composable
internal fun LastTransactionsWidgetContent(
    lastTransactions: List<TransactionListModel.Data>,
    onTransactionClick: (transaction: Transaction) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (lastTransactions.isEmpty()) {
        LastTransactionsEmptyStub()
    } else {
        LastTransactionsColumn(
            modifier = modifier,
            transactions = lastTransactions,
            onTransactionClick = onTransactionClick
        )
    }
}

@Composable
private fun LastTransactionsColumn(
    transactions: List<TransactionListModel.Data>,
    onTransactionClick: (transaction: Transaction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(
                vertical = 10.dp
            )
    ) {
        transactions.forEach { transactionData ->
            TransactionItem(
                transactionData = transactionData,
                onClick = {
                    onTransactionClick.invoke(transactionData.transaction)
                }
            )
        }
    }
}
