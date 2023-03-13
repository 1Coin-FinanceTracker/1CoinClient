package com.finance_tracker.finance_tracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import com.finance_tracker.finance_tracker.core.common.AppInitializer
import com.finance_tracker.finance_tracker.core.navigation.main.navigationGraph
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.alexgladkov.odyssey.compose.setup.OdysseyConfiguration
import ru.alexgladkov.odyssey.compose.setup.StartScreen
import ru.alexgladkov.odyssey.compose.setup.setNavigationContent
import ru.alexgladkov.odyssey.core.configuration.DisplayType

class MainActivity : ComponentActivity(), KoinComponent {

    private val appInitializer by inject<AppInitializer>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = Color.Transparent.toArgb()
        window.navigationBarColor = Color.Transparent.toArgb()

        setContent {
            CoinTheme {
                setNavigationContent(
                    configuration = OdysseyConfiguration(
                        canvas = this,
                        startScreen = StartScreen.Custom(appInitializer.startScreen),
                        backgroundColor = CoinTheme.color.background,
                        displayType = DisplayType.EdgeToEdge
                    ),
                    onApplicationFinish = ::finish
                ) {
                    navigationGraph()
                }
            }
        }
    }
}