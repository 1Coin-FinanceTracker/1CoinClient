package com.finance_tracker.finance_tracker.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter as graphicsRememberVectorPainter

@Composable
expect fun rememberImageVector(
    id: String,
    isSupportDarkMode: Boolean = false
): ImageVector

@Composable
internal fun rememberVectorPainter(
    id: String,
    isSupportDarkMode: Boolean = false
): Painter {
    return graphicsRememberVectorPainter(rememberImageVector(id, isSupportDarkMode))
}