package com.finance_tracker.finance_tracker.core.common

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

@Composable
internal fun Modifier.`if`(
    condition: Boolean,
    then: @Composable Modifier.() -> Modifier
): Modifier {
    return if (condition) {
        then()
    } else {
        this
    }
}

expect fun Modifier.statusBarsPadding(): Modifier

expect fun Modifier.navigationBarsPadding(): Modifier

expect fun Modifier.imePadding(): Modifier

object ModifierConstants {
    const val PreventMultipleClicksDuration = 400L
}

/**
 * OnClick wrapper for prevention of multiple clicks on one or multiple object.
 * */
class MultipleEventsCutter {
    private val now: Long
        get() = System.currentTimeMillis()

    private var lastEventTimeMs: Long = 0

    operator fun invoke(event: () -> Unit) {
        if (now - lastEventTimeMs >= ModifierConstants.PreventMultipleClicksDuration) {
            event.invoke()
        }
        lastEventTimeMs = now
    }

    companion object {
        val preInitializedCutter = MultipleEventsCutter()
    }
}

/**
 * Clickable modifier wrapper to prevent multiple clicks on the same or multiple object of the screen.
 *
 * @param enabled by default true. Use it to dynamically modify clickability behavior of Composable
 * @param multipleEventsCutter by default uses shared object for all modifiers. If you need separate timings for
 * separate screens, pass locally stored in remember { MultipleEventsCutter() } cutter instance.
 * @param onClick defines on click behaviour
 * */
fun Modifier.clickableSingle(
    enabled: Boolean = true,
    multipleEventsCutter: MultipleEventsCutter? = null,
    onClick: () -> Unit
) = composed {
    val cutter = remember { multipleEventsCutter ?: MultipleEventsCutter.preInitializedCutter }

    Modifier.clickable(
        enabled = enabled,
    ) { cutter { onClick() } }
}