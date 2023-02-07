package com.finance_tracker.finance_tracker.core.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.icerock.moko.resources.FileResource

@Composable
expect fun VectorAnimation(
    fileResource: FileResource,
    modifier: Modifier = Modifier,
    iterations: Int = LottieConstants.IterateForever,
)

object LottieConstants {
    const val IterateForever = Int.MAX_VALUE
}