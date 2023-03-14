package com.finance_tracker.finance_tracker.core.common

import androidx.compose.runtime.Composable
import com.adeo.kviewmodel.KViewModel
import org.koin.core.parameter.ParametersDefinition
import com.adeo.kviewmodel.odyssey.StoredViewModel as OdysseyStoredViewModel

@Composable
inline fun <reified T : KViewModel> StoredViewModel(
    noinline parameters: ParametersDefinition?,
    noinline content: @Composable (T) -> Unit
) {
    OdysseyStoredViewModel(
        factory = { getKoin().get(parameters = parameters) },
        content = content
    )
}