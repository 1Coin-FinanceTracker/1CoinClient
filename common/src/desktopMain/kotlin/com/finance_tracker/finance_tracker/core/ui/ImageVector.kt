package com.finance_tracker.finance_tracker.core.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.loadXmlImageVector
import androidx.compose.ui.res.useResource
import org.xml.sax.InputSource

@Composable
actual fun rememberImageVector(
    id: String,
    isSupportDarkMode: Boolean
): ImageVector {
    val density = LocalDensity.current
    val rootPath = rememberDrawablePath(isSupportDarkMode)
    return remember(density, id, rootPath) {
        useResource("$rootPath/$id.xml") { stream ->
            loadXmlImageVector(InputSource(stream), density)
        }
    }
}

@Composable
private fun rememberDrawablePath(hasDarkMode: Boolean): String {
    val isDarkDrawablePath = hasDarkMode && isSystemInDarkTheme()
    return remember(isDarkDrawablePath) {
        if (isDarkDrawablePath) {
            "drawable-night"
        } else {
            "drawable"
        }
    }
}