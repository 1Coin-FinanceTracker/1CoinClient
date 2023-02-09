package com.finance_tracker.finance_tracker.data.data_sources

import android.app.Activity
import android.content.Intent
import com.finance_tracker.finance_tracker.common.R
import com.finance_tracker.finance_tracker.core.common.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlin.coroutines.suspendCoroutine

actual class GoogleAuthDataSource actual constructor(
    private val context: Context
) {

    private val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(context.getString(R.string.google_cloud_server_client_id))
        .requestEmail()
        .build()

    private val googleSignInClient = GoogleSignIn.getClient(context, googleSignInOptions)

    fun getIntent(): Intent {
        return googleSignInClient.signInIntent
    }

    actual fun isUserSignedIn(): Boolean {
        return GoogleSignIn.getLastSignedInAccount(context) != null
    }

    suspend fun getFreshIdToken(context: Context): String? {
        return suspendCoroutine { cont ->
            if (!isUserSignedIn()) {
                cont.resumeWith(Result.success(null))
                return@suspendCoroutine
            }

            googleSignInClient.silentSignIn()
                .addOnCompleteListener(context as Activity) {
                    if (it.isSuccessful) {
                        cont.resumeWith(Result.success(it.result.idToken))
                    } else {
                        cont.resumeWith(Result.failure(it.exception!!))
                    }
                }
        }
    }

    fun signOut(context: Context) {
        googleSignInClient.signOut()
            .addOnCompleteListener(context as Activity) {
                if (it.isSuccessful) {
                    // TODO: Handle SignOutSuccess
                } else {
                    // TODO: Handle SignOutError
                }
            }
    }
}