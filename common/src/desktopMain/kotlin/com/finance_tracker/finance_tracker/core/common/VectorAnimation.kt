package com.finance_tracker.finance_tracker.core.common

import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameMillis
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import dev.icerock.moko.resources.FileResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.skia.Data
import org.jetbrains.skia.Rect
import org.jetbrains.skia.skottie.Animation
import org.jetbrains.skia.sksg.InvalidationController
import kotlin.time.Duration.Companion.seconds

@Suppress("ModifierParameterPosition", "MissingModifierDefaultValue")
@Composable
actual fun VectorAnimation(
    fileResource: FileResource,
    modifier: Modifier,
    iterations: Int,
) {
    var seconds: Float by remember { mutableStateOf(0f) }
    var previousMillis: Long by remember { mutableStateOf(0L) }
    val invalidationController = remember { InvalidationController() }
    val animationState: MutableState<Animation?> = remember { mutableStateOf(null) }
    LaunchedEffect(Unit) {
        animationState.value = withContext(Dispatchers.Default) {
            val animationBytes = fileResource.readText().encodeToByteArray()
            Animation.makeFromData(Data.makeFromBytes(animationBytes))
        }
    }
    animationState.value.let { animation ->
        if (animation == null) {
            Spacer(modifier = modifier) // Placeholder while loading
        } else {
            animation.seekFrameTime(seconds % animation.duration, invalidationController)
            Surface(
                modifier = modifier
                    .drawWithContent {
                        drawIntoCanvas { canvas ->
                            animation.render(
                                canvas = canvas.nativeCanvas,
                                dst = Rect.makeWH(size.width, size.height)
                            )
                        }
                    }
            ) {
                // Content will be drawn by modifier
            }
            LaunchedEffect(Unit) {
                @Suppress("UnusedPrivateMember")
                for (iteration in 0..iterations) {
                    withFrameMillis { absoluteMillis ->
                        if (previousMillis > 0L) {
                            val deltaMillis = absoluteMillis - previousMillis
                            seconds += deltaMillis / 1.seconds.inWholeMilliseconds
                        }
                        previousMillis = absoluteMillis
                    }
                }
            }
        }
    }
}