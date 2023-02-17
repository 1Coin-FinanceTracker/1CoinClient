package com.finance_tracker.finance_tracker.data.repositories

class AuthRepository {

    suspend fun requestOtpCode() {
        // TODO
    }

    @Suppress("MagicNumber")
    suspend fun verifyOtpCode(otp: Int): Boolean {
        return otp >= 2000
    }

    @Suppress("UnusedPrivateMember")
    suspend fun sendGoogleAuthToken(token: String) {
        // TODO
    }
}