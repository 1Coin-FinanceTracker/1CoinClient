package com.finance_tracker.finance_tracker.features.welcome.views

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
internal expect fun GoogleSignInButton(
    onClick: () -> Unit,
    onSuccess: (token: String) -> Unit,
    onError: (exception: Exception) -> Unit,
    modifier: Modifier = Modifier
)