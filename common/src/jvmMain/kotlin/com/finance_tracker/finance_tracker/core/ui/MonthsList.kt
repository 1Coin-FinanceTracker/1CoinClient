package com.finance_tracker.finance_tracker.core.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.date.currentYearMonth
import com.finance_tracker.finance_tracker.core.common.date.localizedName
import com.finance_tracker.finance_tracker.core.common.date.minusMonth
import com.finance_tracker.finance_tracker.core.common.date.models.YearMonth
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import kotlinx.datetime.Clock

@Composable
internal fun MonthsList(
    modifier: Modifier = Modifier,
    selectedMonth: YearMonth = Clock.System.currentYearMonth(),
    lastMonth: YearMonth = Clock.System.currentYearMonth(),
    monthCount: Int = 12,
    onYearMonthSelect: (YearMonth) -> Unit = {}
) {
    require(monthCount > 0) { "monthCount must be more than 0" }

    val yearMonths by remember(monthCount) {
        derivedStateOf {
            (monthCount - 1 downTo 0).map { index ->
                lastMonth.minusMonth(index)
            }
        }
    }
    val scrollState = rememberLazyListState()
    LaunchedEffect(Unit) {
        scrollState.scrollToItem(yearMonths.lastIndex)
    }
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        contentPadding = PaddingValues(
            horizontal = 8.dp
        ),
        state = scrollState
    ) {
        items(yearMonths) { yearMonth ->
            Button(
                modifier = Modifier
                    .padding(),
                enabled = selectedMonth != yearMonth,
                onClick = { onYearMonthSelect.invoke(yearMonth) },
                elevation = ButtonDefaults.elevation(
                    defaultElevation = 0.dp,
                    pressedElevation = 0.dp,
                    focusedElevation = 0.dp,
                    hoveredElevation = 0.dp
                ),
                colors = ButtonDefaults.textButtonColors(
                    disabledContentColor = CoinTheme.color.content,
                    contentColor = CoinTheme.color.secondary
                ),
                contentPadding = PaddingValues(
                    vertical = 8.dp,
                    horizontal = 12.dp
                )
            ) {
                Text(
                    text = yearMonth.month.localizedName(),
                    style = CoinTheme.typography.body1_medium
                )
            }
        }
    }
}