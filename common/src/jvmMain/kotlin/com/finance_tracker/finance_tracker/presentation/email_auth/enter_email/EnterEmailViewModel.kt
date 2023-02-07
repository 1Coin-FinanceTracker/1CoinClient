package com.finance_tracker.finance_tracker.presentation.email_auth.enter_email

import com.finance_tracker.finance_tracker.core.common.view_models.BaseViewModel
import com.finance_tracker.finance_tracker.domain.interactors.AuthInteractor
import com.finance_tracker.finance_tracker.presentation.email_auth.enter_email.analytics.EnterEmailAnalytics
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class EnterEmailViewModel(
    private val enterEmailAnalytics: EnterEmailAnalytics,
    private val authInteractor: AuthInteractor
): BaseViewModel<EnterEmailAction>() {

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    val isContinueEnabled = email.map { it.isNotBlank() }
        .stateIn(viewModelScope, started = SharingStarted.Lazily, initialValue = false)

    fun onBackClick() {
        enterEmailAnalytics.trackBackClick()
        viewAction = EnterEmailAction.Close
    }

    fun onContinueClick() {
        enterEmailAnalytics.trackContinueClick()
        // TODO: Loading
        viewModelScope.launch {
            runCatching { authInteractor.requestOtpCode() }
                .onSuccess {
                    viewAction = EnterEmailAction.OpenEnterOtpScreen(email.value)
                }
                .onFailure {
                    // TODO
                }
        }
    }

    fun onEmailChange(email: String) {
        _email.value = email
    }
}