package com.finance_tracker.finance_tracker.core.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import dev.icerock.moko.resources.FileResource

@Suppress("ModifierParameterPosition", "MissingModifierDefaultValue")
@Composable
actual fun VectorAnimation(
    fileResource: FileResource,
    modifier: Modifier,
    iterations: Int,
) {
    val context = LocalContext.current
    val text = fileResource.readText(context)
    val composition by rememberLottieComposition(LottieCompositionSpec.JsonString(text))
    LottieAnimation(
        modifier = modifier,
        composition = composition,
        iterations = iterations
    )
}