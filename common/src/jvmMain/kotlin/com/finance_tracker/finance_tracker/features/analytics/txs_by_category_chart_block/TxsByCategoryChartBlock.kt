package com.finance_tracker.finance_tracker.features.analytics.txs_by_category_chart_block

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.date.models.YearMonth
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.MonthsList
import com.finance_tracker.finance_tracker.core.ui.rememberVectorPainter
import com.finance_tracker.finance_tracker.domain.models.Currency
import com.finance_tracker.finance_tracker.domain.models.TxsByCategoryChart
import com.finance_tracker.finance_tracker.features.analytics.views.NoTransactionsStub

private const val ExpandAnimationDuration = 500

@Suppress("MagicNumber")
@Composable
internal fun TxsByCategoryChartBlock(
    primaryCurrency: Currency,
    isLoading: Boolean,
    monthTransactionsByCategory: TxsByCategoryChart?,
    selectedYearMonth: YearMonth,
    modifier: Modifier = Modifier,
    onYearMonthSelect: (YearMonth) -> Unit = {}
) {
    val mainTxsByCategoryPieces = monthTransactionsByCategory?.mainPieces.orEmpty()

    Column(
        modifier = modifier
            .padding(bottom = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        MonthsList(
            modifier = Modifier.padding(
                top = 8.dp,
                bottom = 24.dp
            ),
            selectedMonth = selectedYearMonth,
            onYearMonthSelect = onYearMonthSelect
        )

        when {
            isLoading -> {
                LoadingPieChart(
                    selectedYearMonth = selectedYearMonth
                )
            }
            !isLoading && monthTransactionsByCategory != null && mainTxsByCategoryPieces.isNotEmpty() -> {
                ContentPieChartLayout(
                    monthTransactionsByCategory = monthTransactionsByCategory,
                    selectedYearMonth = selectedYearMonth
                )
            }
            else -> {
                EmptyPieChartLayout(
                    primaryCurrency = primaryCurrency,
                    selectedYearMonth = selectedYearMonth
                )
            }
        }
    }
}

@Composable
private fun ContentPieChartLayout(
    monthTransactionsByCategory: TxsByCategoryChart,
    selectedYearMonth: YearMonth,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MainTxsByCategoryChart(
            monthTransactionsByCategory = monthTransactionsByCategory,
            selectedYearMonth = selectedYearMonth
        )

        // Opening Animation
        val expandTransition = remember {
            expandVertically(
                expandFrom = Alignment.Top,
                animationSpec = tween(ExpandAnimationDuration)
            ) + fadeIn(
                animationSpec = tween(ExpandAnimationDuration)
            )
        }

        // Closing Animation
        val collapseTransition = remember {
            shrinkVertically(
                shrinkTowards = Alignment.Top,
                animationSpec = tween(ExpandAnimationDuration)
            ) + fadeOut(
                animationSpec = tween(ExpandAnimationDuration)
            )
        }

        var isCategoriesExpanded by rememberSaveable { mutableStateOf(false) }
        ExpandButton(
            modifier = Modifier
                .padding(bottom = 4.dp),
            expand = isCategoriesExpanded,
            onClick = {
                isCategoriesExpanded = !isCategoriesExpanded
            }
        )

        AnimatedVisibility(
            visible = isCategoriesExpanded,
            enter = expandTransition,
            exit = collapseTransition
        ) {
            CategoriesList(
                pieces = monthTransactionsByCategory.allPieces,
                totalAmount = monthTransactionsByCategory.total.amountValue
            )
        }
    }
}

@Composable
private fun EmptyPieChartLayout(
    primaryCurrency: Currency,
    selectedYearMonth: YearMonth,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmptyPieChart(
            primaryCurrency = primaryCurrency,
            selectedYearMonth = selectedYearMonth
        )

        NoTransactionsStub(
            modifier = Modifier.padding(
                bottom = 32.dp
            )
        )
    }
}

@Composable
@Suppress("MagicNumber")
private fun ExpandButton(
    modifier: Modifier = Modifier,
    expand: Boolean = false,
    onClick: () -> Unit = {}
) {
    val rotateDegrees = if (expand) 180f else 0f
    val rotateDegreesAnimatable by animateFloatAsState(
        targetValue = rotateDegrees,
        animationSpec = tween(ExpandAnimationDuration)
    )

    Icon(
        modifier = modifier
            .size(24.dp)
            .clip(CircleShape)
            .rotate(rotateDegreesAnimatable)
            .clickable { onClick.invoke() },
        painter = rememberVectorPainter("ic_expand_more"),
        contentDescription = null,
        tint = CoinTheme.color.primary
    )
}