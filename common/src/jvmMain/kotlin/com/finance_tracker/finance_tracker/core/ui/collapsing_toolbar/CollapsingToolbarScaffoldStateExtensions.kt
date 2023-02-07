package com.finance_tracker.finance_tracker.core.ui.collapsing_toolbar

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

fun CollapsingToolbarScaffoldState.animate(start: Dp, end: Dp): Dp {
    return start + (end - start) * (1f - toolbarState.progress)
}

fun CollapsingToolbarScaffoldState.animate(start: Int, end: Int): Int {
    return (start + (end - start) * (1f - toolbarState.progress)).toInt()
}

fun CollapsingToolbarScaffoldState.animate(start: Float, end: Float): Float {
    return start + (end - start) * (1f - toolbarState.progress)
}

fun CollapsingToolbarScaffoldState.animate(start: TextUnit, end: TextUnit): TextUnit {
    return (start.value + (end.value - start.value) * (1f - toolbarState.progress)).sp
}