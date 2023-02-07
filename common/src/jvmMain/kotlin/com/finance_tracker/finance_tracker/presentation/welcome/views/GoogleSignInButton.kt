package com.finance_tracker.finance_tracker.presentation.welcome.views

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
import dev.icerock.moko.resources.compose.stringResource

@Composable
internal fun GoogleSignInButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    BaseButton(
        modifier = modifier,
        contentColor = CoinTheme.color.primary,
        backgroundColor = CoinTheme.color.primaryVariant,
        onClick = onClick
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