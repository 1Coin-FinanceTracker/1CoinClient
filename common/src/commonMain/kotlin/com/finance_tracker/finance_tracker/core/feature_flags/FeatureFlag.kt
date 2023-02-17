package com.finance_tracker.finance_tracker.core.feature_flags

import com.finance_tracker.finance_tracker.core.common.AppBuildConfig

enum class FeatureFlag(
    val isEnabled: Boolean
) {
    Authorization(isEnabled = AppBuildConfig.isTestBuild),
    WidgetsSettings(isEnabled = AppBuildConfig.isTestBuild)
}