package com.finance_tracker.finance_tracker.features.email_auth.enter_email

import com.finance_tracker.finance_tracker.core.common.TextFieldValue
import com.finance_tracker.finance_tracker.core.common.view_models.BaseViewModel
import com.finance_tracker.finance_tracker.domain.interactors.AuthInteractor
import com.finance_tracker.finance_tracker.features.email_auth.enter_email.analytics.EnterEmailAnalytics
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
    private val regex = Regex("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}\$")

    private val _emailTextFieldValue = MutableStateFlow(TextFieldValue())
    val emailTextFieldValue = _emailTextFieldValue.asStateFlow()

    val isContinueEnabled = _emailTextFieldValue.map { regex.matches(it.text) }
        .stateIn(viewModelScope, started = SharingStarted.Lazily, initialValue = false)

    private val isLoading = MutableStateFlow(false)
    val isLoadingButton = isLoading.asStateFlow()
    val isEmailTextFieldReadOnly = isLoading.asStateFlow()

    fun onBackClick() {
        enterEmailAnalytics.trackBackClick()
        viewAction = EnterEmailAction.Close
    }

    fun onContinueClick() {
        enterEmailAnalytics.trackContinueClick()
        isLoading.value = true
        viewModelScope.launch {
            runCatching { authInteractor.requestOtpCode() }
                .onSuccess {
                    isLoading.value = false
                    viewAction =
                        EnterEmailAction.OpenEnterOtpScreen(_emailTextFieldValue.value.text)
                }
                .onFailure {
                    isLoading.value = false
                    // TODO
                }
        }
    }

    fun onEmailChange(emailTextFieldValue: TextFieldValue) {
        _emailTextFieldValue.value = emailTextFieldValue
    }
}