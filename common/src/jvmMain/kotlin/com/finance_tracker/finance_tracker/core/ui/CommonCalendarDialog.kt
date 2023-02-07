package com.finance_tracker.finance_tracker.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.datetime.LocalDate

@Composable
expect fun CalendarDialog(
    minDate: Long,
    maxDate: Long,
    modifier: Modifier = Modifier,
    onControllerCreate: (CalendarDialogController) -> Unit = {},
    onDateChangeListener: (LocalDate) -> Unit = {}
)

interface CalendarDialogController {

    fun show()

    fun cancel()
}

object StubCalendarDialogController: CalendarDialogController {

    override fun show() {
    }

    override fun cancel() {
    }
}