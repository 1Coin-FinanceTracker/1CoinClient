package com.finance_tracker.finance_tracker.features.welcome

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.finance_tracker.finance_tracker.core.common.getKoin
import com.finance_tracker.finance_tracker.data.data_sources.GoogleAuthDataSource
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task

class GoogleApiContract : ActivityResultContract<Int, Task<GoogleSignInAccount>?>() {

    private val googleAuthDataSource: GoogleAuthDataSource by getKoin().inject()

    override fun createIntent(context: Context, input: Int): Intent {
        return googleAuthDataSource.getIntent()
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Task<GoogleSignInAccount>? {
        return if (resultCode == Activity.RESULT_OK) {
            GoogleSignIn.getSignedInAccountFromIntent(intent)
        } else {
            null
        }
    }
}