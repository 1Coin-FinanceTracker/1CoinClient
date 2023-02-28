package com.finance_tracker.finance_tracker

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type
import androidx.compose.ui.window.singleWindowApplication
import com.finance_tracker.finance_tracker.core.common.AppInitializer
import com.finance_tracker.finance_tracker.core.common.EmptyContext
import com.finance_tracker.finance_tracker.core.common.MessageKeyQueue
import com.finance_tracker.finance_tracker.core.common.di.Di
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import org.koin.java.KoinJavaComponent.inject

private val appInitializer: AppInitializer by inject(AppInitializer::class.java)

@OptIn(ExperimentalComposeUiApi::class)
fun main() = singleWindowApplication(
    title = "",
    onKeyEvent = {
        if (
            it.key == Key.DirectionLeft &&
            it.type == KeyEventType.KeyDown
        ) {
            MessageKeyQueue.onBackPressedChannel.trySend(Unit)
            return@singleWindowApplication true
        }
        false
    }
) {
    Di.init(EmptyContext)
    appInitializer.configure()

    CoinTheme {
        /*setNavigationContent(
            configuration = OdysseyConfiguration(
                startScreen = StartScreen.Custom(appInitializer.startScreen),
                backgroundColor = CoinTheme.color.background
            )
        ) {
        }*/
    }
}