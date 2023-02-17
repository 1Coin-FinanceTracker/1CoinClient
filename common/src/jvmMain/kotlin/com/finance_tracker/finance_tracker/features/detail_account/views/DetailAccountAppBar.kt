package com.finance_tracker.finance_tracker.features.detail_account.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.statusBarsPadding

@Composable
internal fun DetailAccountAppBar(
    color: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(color)
            .statusBarsPadding()
            .fillMaxWidth()
            .height(56.dp)
    )
}