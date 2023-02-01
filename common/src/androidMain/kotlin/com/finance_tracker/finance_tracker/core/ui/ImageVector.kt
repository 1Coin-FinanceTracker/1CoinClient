package com.finance_tracker.finance_tracker.core.ui

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource

@SuppressLint("DiscouragedApi")
@Composable
actual fun rememberImageVector(
    id: String,
    isSupportDarkMode: Boolean
): ImageVector {
    val context = LocalContext.current
    val identifier = remember(context, id) {
        context.resources.getIdentifier(id, "drawable", context.packageName)
    }
    val imageVector = ImageVector.vectorResource(identifier)
    return remember(id) { imageVector }
}