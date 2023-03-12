package com.finance_tracker.finance_tracker.features.add_transaction.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.common.date.Format
import com.finance_tracker.finance_tracker.core.common.date.format
import com.finance_tracker.finance_tracker.core.common.date.isCurrentYear
import com.finance_tracker.finance_tracker.core.common.date.isToday
import com.finance_tracker.finance_tracker.core.common.date.isYesterday
import com.finance_tracker.finance_tracker.core.common.date.minus
import com.finance_tracker.finance_tracker.core.common.toDateTime
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.CalendarDialog
import com.finance_tracker.finance_tracker.core.ui.CalendarDialogController
import com.finance_tracker.finance_tracker.core.ui.StubCalendarDialogController
import com.finance_tracker.finance_tracker.core.ui.rememberVectorPainter
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate

@Composable
internal fun CalendarDayView(
    date: LocalDate,
    modifier: Modifier = Modifier,
    onDateChange: (LocalDate) -> Unit = {},
    onCalendarClick: () -> Unit = {}
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        var calendarDialogController: CalendarDialogController by remember {
            mutableStateOf(StubCalendarDialogController)
        }
        val minDate = Clock.System.now().minus(DateTimeUnit.YEAR)
        val maxDate = Clock.System.now()
        CalendarDialog(
            minDate = minDate.toEpochMilliseconds(),
            maxDate = maxDate.toEpochMilliseconds(),
            onControllerCreate = {
                calendarDialogController = it
            },
            onDateChangeListener = onDateChange
        )

        Row(
            modifier = Modifier
                .background(CoinTheme.color.background)
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .clip(RoundedCornerShape(12.dp))
                .clickable {
                    onCalendarClick.invoke()
                    calendarDialogController.show()
                }
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            CompositionLocalProvider(
                LocalContentColor provides CoinTheme.color.secondary
            ) {
                Icon(
                    painter = rememberVectorPainter("ic_calendar"),
                    contentDescription = null,
                    tint = LocalContentColor.current
                )

                val shortFormattedDate by remember(date) {
                    derivedStateOf { date.toDateTime().format(Format.ShortDate) }
                }
                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = when {
                        date.isToday() -> {
                            "${stringResource(MR.strings.add_transaction_today)}, $shortFormattedDate"
                        }
                        date.isYesterday() -> {
                            "${stringResource(MR.strings.add_transaction_yesterday)}, $shortFormattedDate"
                        }
                        date.isCurrentYear() -> {
                            shortFormattedDate
                        }
                        else -> {
                            date.toDateTime().format(Format.FullDate)
                        }
                    },
                    style = CoinTheme.typography.subtitle1,
                    color = LocalContentColor.current
                )
            }
        }
    }
}