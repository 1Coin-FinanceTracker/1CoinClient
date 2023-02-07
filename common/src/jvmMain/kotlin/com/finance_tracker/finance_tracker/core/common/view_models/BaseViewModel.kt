package com.finance_tracker.finance_tracker.core.common.view_models

import com.adeo.kviewmodel.KViewModel
import com.adeo.kviewmodel.WrappedSharedFlow
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

abstract class BaseViewModel<Action>: KViewModel() {

    private val _viewActions = MutableSharedFlow<Action?>(
        replay = 0,
        extraBufferCapacity = Int.MAX_VALUE,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    protected var viewAction: Action?
        get() = null
        set(value) {
            _viewActions.tryEmit(value)
        }

    fun viewActions(): WrappedSharedFlow<Action?> = WrappedSharedFlow(_viewActions.asSharedFlow())
}