package com.finance_tracker.finance_tracker.core.common

actual object BuildInfo {

    @Suppress("NotImplementedDeclaration")
    actual val isDebug: Boolean
        get() = true // TODO

    @Suppress("NotImplementedDeclaration")
    actual val buildType: BuildType
        get() = BuildType.Debug //TODO
}