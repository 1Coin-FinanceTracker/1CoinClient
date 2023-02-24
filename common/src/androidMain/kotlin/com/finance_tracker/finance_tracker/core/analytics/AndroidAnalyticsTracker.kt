package com.finance_tracker.finance_tracker.core.analytics

import com.amplitude.android.Amplitude
import com.amplitude.android.Configuration
import com.amplitude.common.Logger
import com.amplitude.core.events.Identify
import com.finance_tracker.finance_tracker.core.common.AppBuildConfig
import com.finance_tracker.finance_tracker.core.common.Context
import com.finance_tracker.finance_tracker.data.settings.AnalyticsSettings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class AndroidAnalyticsTracker(
    analyticsSettings: AnalyticsSettings
): AnalyticsTracker, CoroutineScope {

    @Suppress("LateinitUsage")
    private lateinit var amplitude: Amplitude

    override val coroutineContext: CoroutineContext = SupervisorJob() + Dispatchers.IO
    private val isAnalyticsEnabledFlow = analyticsSettings.isAnalyticsEnabledFlow()

    private var isAnalyticsDisabled = true

    override fun init(context: Context, userId: String) {
        amplitude = Amplitude(
            Configuration(
                apiKey = AppBuildConfig.AMPLITUDE_API_KEY,
                context = context
            )
        ).apply {
            logger.logMode = if (AppBuildConfig.DEBUG_BUILD_TYPE) {
                Logger.LogMode.DEBUG
            } else {
                Logger.LogMode.OFF
            }
        }
        setUserId(userId)
        launch {
            isAnalyticsEnabledFlow
                .onEach { isEnabled -> isAnalyticsDisabled = !isEnabled }
                .launchIn(this)
        }
    }

    private fun setUserId(userId: String) {
        launch {
            amplitude.setUserId(userId) // is blocking code
        }
    }

    override fun setUserProperty(property: String, value: Any) {
        if (isAnalyticsDisabled) return

        amplitude.identify(Identify().apply { set(property, value) })
        logUserProperties(property, value)
    }

    override fun track(event: AnalyticsEvent) {
        if (isAnalyticsDisabled) return

        amplitude.track(event.name, event.properties)
        log(event)
    }

    private fun log(event: AnalyticsEvent) {
        var message = "${event.name}. "
        if (event.properties.isNotEmpty()) {
            message += "properties: ${event.properties}"
        }
        amplitude.logger.debug(message)
    }

    private fun logUserProperties(property: String, value: Any) {
        amplitude.logger.debug("UserProperties: {$property=$value}")
    }
}