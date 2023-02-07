package com.finance_tracker.finance_tracker.core.ui.transactions

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.common.date.Format
import com.finance_tracker.finance_tracker.core.common.date.format
import com.finance_tracker.finance_tracker.core.common.date.isCurrentYear
import com.finance_tracker.finance_tracker.core.common.date.isToday
import com.finance_tracker.finance_tracker.core.common.date.isYesterday
import com.finance_tracker.finance_tracker.core.common.pagination.LazyPagingItems
import com.finance_tracker.finance_tracker.core.common.pagination.items
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.EmptyStub
import com.finance_tracker.finance_tracker.core.ui.rememberVectorPainter
import com.finance_tracker.finance_tracker.domain.models.TransactionListModel
import dev.icerock.moko.resources.compose.stringResource

@Composable
internal fun CommonTransactionsList(
    transactions: LazyPagingItems<TransactionListModel>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
    onClick: (TransactionListModel.Data) -> Unit = {},
    onLongClick: (TransactionListModel.Data) -> Unit = {},
    onAddTransactionClick: () -> Unit = {},
    stubHeightAlignment: Float = 0.75f,
) {
    if (transactions.itemCount == 0) {
        EmptyStub(
            modifier = modifier,
            image = rememberVectorPainter("transactions_empty", isSupportDarkMode = true),
            text = stringResource(MR.strings.add_transaction),
            onClick = { onAddTransactionClick.invoke() },
            stubHeightAlignment = stubHeightAlignment,
        )
    } else {
        TransactionsList(
            modifier = modifier,
            transactions = transactions,
            contentPadding = contentPadding,
            onClick = onClick,
            onLongClick = onLongClick
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun TransactionsList(
    transactions: LazyPagingItems<TransactionListModel>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
    onClick: (TransactionListModel.Data) -> Unit = {},
    onLongClick: (TransactionListModel.Data) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = contentPadding
    ) {
        items(transactions, key = { it.id }) { transactionModel ->
            if (transactionModel == null) return@items

            when (transactionModel) {
                is TransactionListModel.Data -> {
                    TransactionItem(
                        modifier = Modifier.animateItemPlacement(),
                        transactionData = transactionModel,
                        onClick = { onClick.invoke(transactionModel) },
                        onLongClick = { onLongClick.invoke(transactionModel) }
                    )
                }
                is TransactionListModel.DateAndDayTotal -> {
                    DayTotalHeader(dayTotalModel = transactionModel)
                }
            }
        }
    }
}

@Composable
private fun DayTotalHeader(
    dayTotalModel: TransactionListModel.DateAndDayTotal,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(top = 24.dp, bottom = 8.dp)
    ) {
        val formattedDate = when {
            dayTotalModel.dateTime.date.isToday() -> {
                stringResource(MR.strings.transactions_today)
            }
            dayTotalModel.dateTime.date.isYesterday() -> {
                stringResource(MR.strings.transactions_yesterday)
            }
            dayTotalModel.dateTime.date.isCurrentYear() -> {
                dayTotalModel.dateTime.format(Format.ShortDate)
            }
            else -> {
                dayTotalModel.dateTime.format(Format.FullDate)
            }
        }
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = formattedDate,
            style = CoinTheme.typography.subtitle2,
            color = CoinTheme.color.secondary
        )
        Spacer(
            modifier = Modifier
                .weight(1f)
                .defaultMinSize(minWidth = 16.dp)
        )
    }
}