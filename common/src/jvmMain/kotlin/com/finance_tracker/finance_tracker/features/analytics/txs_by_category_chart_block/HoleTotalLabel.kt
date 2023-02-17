package com.finance_tracker.finance_tracker.features.analytics.txs_by_category_chart_block

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.LocalContext
import com.finance_tracker.finance_tracker.core.common.date.currentYearMonth
import com.finance_tracker.finance_tracker.core.common.date.localizedName
import com.finance_tracker.finance_tracker.core.common.date.models.YearMonth
import com.finance_tracker.finance_tracker.core.common.formatters.ReductionMode
import com.finance_tracker.finance_tracker.core.common.formatters.format
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.theme.staticTextSize
import com.finance_tracker.finance_tracker.domain.models.Amount
import kotlinx.datetime.Clock

sealed class HoleTotalLabelData {

    abstract val yearMonth: YearMonth

    data class Content(
        override val yearMonth: YearMonth,
        val amount: Amount
    ): HoleTotalLabelData()

    data class Loading(
        override val yearMonth: YearMonth
    ): HoleTotalLabelData()
}

@Suppress("MagicNumber")
@Composable
internal fun HoleTotalLabel(
    data: HoleTotalLabelData,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (data is HoleTotalLabelData.Content) {
            Text(
                text = data.amount.format(
                    reductionMode = ReductionMode.Hard
                ),
                color = CoinTheme.color.content,
                style = CoinTheme.typography.h2.staticTextSize()
            )
        } else {
            Box(
                modifier = Modifier
                    .size(width = 100.dp, 36.dp)
                    .padding(bottom = 8.dp)
                    .clip(RoundedCornerShape(percent = 50))
                    .background(CoinTheme.color.secondaryBackground)
            )
        }
        val context = LocalContext.current
        val currentYear = remember(Unit) { Clock.System.currentYearMonth().year }
        val periodText = if (currentYear == data.yearMonth.year) {
            data.yearMonth.month.localizedName(context)
        } else {
            "${data.yearMonth.month.localizedName(context)} ${data.yearMonth.year}"
        }
        Text(
            text = periodText,
            color = CoinTheme.color.secondary,
            style = CoinTheme.typography.subtitle2_medium.staticTextSize()
        )
    }
}