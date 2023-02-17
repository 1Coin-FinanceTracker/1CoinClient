package com.finance_tracker.finance_tracker.features.welcome

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.Composable
import com.google.android.gms.common.api.ApiException


@Suppress("TooGenericExceptionCaught")
@Composable
internal fun rememberGoogleSignInResultLauncher(
    onSuccess: (token: String) -> Unit,
    onError: (Exception) -> Unit
): ActivityResultLauncher<Int> {
    return rememberLauncherForActivityResult(contract = GoogleApiContract()) { task ->
        try {
            val googleSignInAccount = task?.getResult(ApiException::class.java)
            if (googleSignInAccount == null) {
                onError.invoke(NullPointerException("GoogleSignInAccount isn't selected"))
                return@rememberLauncherForActivityResult
            }

            val token = googleSignInAccount.idToken
            if (token == null) {
                onError.invoke(NullPointerException("GoogleSignInAccount\'s token is null"))
                return@rememberLauncherForActivityResult
            }

            onSuccess.invoke(token)

        } catch (e: Exception) {
            onError.invoke(e)
        }
    }
}