package com.finance_tracker.finance_tracker.features.welcome.views

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.BaseButton
import com.finance_tracker.finance_tracker.core.ui.rememberVectorPainter
import com.finance_tracker.finance_tracker.features.ActivityRequestCodes
import com.finance_tracker.finance_tracker.features.welcome.rememberGoogleSignInResultLauncher
import dev.icerock.moko.resources.compose.stringResource

@Suppress("MissingModifierDefaultValue")
@Composable
internal actual fun GoogleSignInButton(
    onClick: () -> Unit,
    onSuccess: (token: String) -> Unit,
    onError: (exception: Exception) -> Unit,
    modifier: Modifier
) {
    val signInLauncher = rememberGoogleSignInResultLauncher(
        onSuccess = onSuccess,
        onError = onError
    )
    BaseButton(
        modifier = modifier,
        contentColor = CoinTheme.color.primary,
        backgroundColor = CoinTheme.color.primaryVariant,
        onClick = {
            onClick.invoke()
            signInLauncher.launch(ActivityRequestCodes.GoogleSignIn)
        }
    ) {
        Icon(
            modifier = Modifier.size(20.dp),
            painter = rememberVectorPainter("ic_google"),
            contentDescription = null,
            tint = LocalContentColor.current
        )
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = stringResource(MR.strings.welcome_continue_google),
            style = CoinTheme.typography.body1_medium,
            color = LocalContentColor.current
        )
    }
}