package com.finance_tracker.finance_tracker.core.feature_flags

class FeaturesManager {

    fun isEnabled(featureFlag: FeatureFlag): Boolean {
        return featureFlag.isEnabled
    }
}