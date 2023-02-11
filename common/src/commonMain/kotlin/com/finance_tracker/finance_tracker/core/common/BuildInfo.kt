package com.finance_tracker.finance_tracker.core.common

expect object BuildInfo {
    val isDebug: Boolean
    val buildType: BuildType
}