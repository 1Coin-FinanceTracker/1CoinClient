package com.finance_tracker.finance_tracker.core.ui.transactions

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.LocalContext
import com.finance_tracker.finance_tracker.core.common.formatters.format
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.rememberVectorPainter
import com.finance_tracker.finance_tracker.domain.models.TransactionListModel
import com.finance_tracker.finance_tracker.domain.models.TransactionType

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun TransactionItem(
    transactionData: TransactionListModel.Data,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    onLongClick: () -> Unit = {}
) {
    val context = LocalContext.current
    val transaction = transactionData.transaction
    val category = transaction.getCategoryOrUncategorized(context)
    val isSelected by transactionData.isSelected.collectAsState()
    Row(
        modifier = modifier
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick,
            )
            .background(
                color = if (isSelected) {
                    CoinTheme.color.content.copy(alpha = 0.1f)
                } else {
                    Color.Transparent
                }
            )
            .padding(vertical = 6.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .size(44.dp)
                .background(color = CoinTheme.color.secondaryBackground, shape = CircleShape)
                .padding(12.dp),
            painter = if (transaction.type == TransactionType.Transfer) {
                rememberVectorPainter("ic_transfer")
            } else {
                rememberVectorPainter(category.iconId)
            },
            contentDescription = null
        )

        Column(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .weight(1f)
        ) {
            Text(
                text = if (transaction.type == TransactionType.Transfer) {
                    transaction.secondaryAccount?.name.orEmpty()
                } else {
                    category.name
                },
                style = CoinTheme.typography.body1
            )
            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = transaction.primaryAccount.name,
                style = CoinTheme.typography.subtitle2,
                color = CoinTheme.color.secondary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }

        val primaryAmountSign = when (transaction.type) {
            TransactionType.Expense -> "-"
            TransactionType.Income,
            TransactionType.Transfer -> "+"
        }
        val secondaryAmountSign = when (transaction.type) {
            TransactionType.Expense,
            TransactionType.Income -> ""
            TransactionType.Transfer -> "-"
        }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End
        ) {
            val amount = if (transaction.type == TransactionType.Transfer) {
                transaction.secondaryAmount?.format().orEmpty()
            } else {
                transaction.primaryAmount.format()
            }
            Text(
                text = primaryAmountSign + amount,
                style = CoinTheme.typography.body1,
                color = if (transaction.type == TransactionType.Income) {
                    CoinTheme.color.accentGreen
                } else {
                    CoinTheme.color.content
                }
            )

            if (transaction.type == TransactionType.Transfer) {

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = secondaryAmountSign + transaction.primaryAmount.format(),
                    style = CoinTheme.typography.subtitle2,
                    color = CoinTheme.color.secondary
                )
            }
        }
    }
}