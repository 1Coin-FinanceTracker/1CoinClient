package com.finance_tracker.finance_tracker.core.common

import com.finance_tracker.finance_tracker.BuildKonfig

// AppBuildConfig is created because IDE don't see BuildInfo for autocomplete
object AppBuildConfig {

    val isReleaseBuild: Boolean = BuildInfo.buildType == BuildType.Release
    val isTestBuild: Boolean = BuildInfo.buildType != BuildType.Release

    val DEBUG_BUILD_TYPE = BuildInfo.isDebug
    val AMPLITUDE_API_KEY = if (isReleaseBuild) {
        "53a89fb85c5895ac9fb42631eacd28c2"
    } else {
        "6236b52f5efeb4208d20f85cc8697971"
    }

    val appVersion: String
        get() {
            val suffix = if (isReleaseBuild) {
                ""
            } else {
                "-${BuildInfo.buildType.buildTypeName}"
            }
            return BuildKonfig.appVersion + suffix
        }
}