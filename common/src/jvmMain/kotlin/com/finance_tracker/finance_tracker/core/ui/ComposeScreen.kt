package com.finance_tracker.finance_tracker.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.finance_tracker.finance_tracker.core.common.StoredViewModel
import com.finance_tracker.finance_tracker.core.common.view_models.BaseViewModel
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import org.koin.core.parameter.ParametersDefinition

@Composable
inline fun <reified T : BaseViewModel<*>> ComposeScreen(
    noinline parameters: ParametersDefinition? = null,
    crossinline block: @Composable (viewModel: T) -> Unit
) {
    CoinTheme {
        StoredViewModel<T>(parameters = parameters) { viewModel ->
            Box(
                modifier = Modifier.background(CoinTheme.color.background)
            ) {
                block(viewModel)
            }
        }
    }
}