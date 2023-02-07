package com.finance_tracker.finance_tracker.presentation.email_auth.enter_otp

import com.finance_tracker.finance_tracker.core.common.view_models.BaseViewModel
import com.finance_tracker.finance_tracker.domain.interactors.AuthInteractor
import com.finance_tracker.finance_tracker.presentation.email_auth.enter_otp.analytics.EnterOtpAnalytics
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class EnterOtpViewModel(
    private val enterOtpAnalytics: EnterOtpAnalytics,
    private val authInteractor: AuthInteractor,
    val email: String
) : BaseViewModel<EnterOtpAction>() {

    private val _requestAgainLeftSeconds = MutableStateFlow(REQUEST_AGAIN_DELAY_SECONDS)
    val requestAgainLeftSeconds = _requestAgainLeftSeconds.asStateFlow()
    val isRequestAgainEnabled = _requestAgainLeftSeconds.map { it == 0 }
        .stateIn(viewModelScope, started = SharingStarted.Lazily, initialValue = false)

    private val _isWrongCodeStateEnabled = MutableStateFlow(false)
    val isWrongCodeStateEnabled = _isWrongCodeStateEnabled.asStateFlow()

    private var timerJob: Job? = null

    private val _otp = MutableStateFlow("")
    val otp = _otp.asStateFlow()

    val icCreateAccountEnabled = otp.map { it.length == OTP_LENGTH }
        .stateIn(viewModelScope, started = SharingStarted.Lazily, initialValue = false)

    init {
        enterOtpAnalytics.trackScreenOpen()
        startRequestAgainTimer()
    }

    private fun requestOtpCode() {
        viewModelScope.launch {
            runCatching { authInteractor.requestOtpCode() }
                .onFailure {
                    // TODO:
                }
        }
    }

    private fun startRequestAgainTimer() {
        timerJob = viewModelScope.launch {
            _requestAgainLeftSeconds.value = REQUEST_AGAIN_DELAY_SECONDS
            for (second in REQUEST_AGAIN_DELAY_SECONDS downTo 0) {
                delay(1.seconds.inWholeMilliseconds)
                _requestAgainLeftSeconds.value = second
            }
        }
    }

    fun onBackClick() {
        enterOtpAnalytics.trackBackClick()
        viewAction = EnterOtpAction.Close
    }

    fun onCreateAccountClick() {
        enterOtpAnalytics.trackCreateAccountClick()
        viewModelScope.launch {
            val otpCode = otp.value.toIntOrNull() ?: return@launch
            val isCodeVerified = authInteractor.verifyOtpCode(otpCode)
            if (isCodeVerified) {
                viewAction = EnterOtpAction.OpenMainScreen
            } else {
                showWrongCodeState()
            }
        }
    }

    private fun showWrongCodeState() {
        viewModelScope.launch {
            _isWrongCodeStateEnabled.value = true
            delay(WRONG_STATE_DELAY)
            _isWrongCodeStateEnabled.value = false
        }
    }

    fun onRequestCodeAgainClick() {
        enterOtpAnalytics.trackRequestCodeAgainClick()
        startRequestAgainTimer()
        requestOtpCode()
    }

    fun onOtpChange(otp: String) {
        _otp.value = otp
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }

    companion object {

        const val OTP_LENGTH = 4

        private const val REQUEST_AGAIN_DELAY_SECONDS = 30
        private const val WRONG_STATE_DELAY = 1200L
    }
}