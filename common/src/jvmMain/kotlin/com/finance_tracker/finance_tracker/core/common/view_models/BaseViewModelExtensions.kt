package com.finance_tracker.finance_tracker.core.common.view_models

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.finance_tracker.finance_tracker.core.common.Context
import com.finance_tracker.finance_tracker.core.common.LocalContext
import kotlinx.coroutines.CoroutineScope
import ru.alexgladkov.odyssey.compose.RootController
import ru.alexgladkov.odyssey.compose.local.LocalRootController

data class BaseLocalsStorage(
    val rootController: RootController,
    val context: Context,
    val coroutineScope: CoroutineScope
)

@Composable
inline fun <Action> BaseViewModel<Action>.watchViewActions(
    crossinline onExecute: (action: Action, baseLocalsStorage: BaseLocalsStorage) -> Unit
) {
    val rootController = LocalRootController.current
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val baseLocalsStorage = remember(rootController, context, coroutineScope) {
        BaseLocalsStorage(
            rootController,
            context,
            coroutineScope
        )
    }

    DisposableEffect(Unit) {
        val flow = viewActions().watch { action ->
            action ?: return@watch
            onExecute.invoke(action, baseLocalsStorage)
        }
        onDispose {
            flow.close()
        }
    }
}