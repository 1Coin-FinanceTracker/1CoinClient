package com.finance_tracker.finance_tracker.domain.interactors

import com.finance_tracker.finance_tracker.data.repositories.AuthRepository

class AuthInteractor(
    private val authRepository: AuthRepository
) {

    suspend fun requestOtpCode() {
        authRepository.requestOtpCode()
    }

    suspend fun verifyOtpCode(otp: Int): Boolean {
        return authRepository.verifyOtpCode(otp)
    }

    suspend fun sendGoogleAuthToken(token: String) {
        authRepository.sendGoogleAuthToken(token)
    }
}