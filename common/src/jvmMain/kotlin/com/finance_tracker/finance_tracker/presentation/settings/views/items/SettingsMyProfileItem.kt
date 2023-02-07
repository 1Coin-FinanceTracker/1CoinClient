package com.finance_tracker.finance_tracker.presentation.settings.views.items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.common.`if`
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.rememberVectorPainter
import com.finance_tracker.finance_tracker.presentation.settings.views.ListItem
import dev.icerock.moko.resources.compose.stringResource

@Composable
internal fun SettingsMyProfileItem(
    userEmail: String,
    isUserAuthorized: Boolean,
    modifier: Modifier = Modifier,
    onLogOutClick: () -> Unit = {},
    onLogInClick: () -> Unit = {},
) {
    ListItem(
        modifier = modifier,
        iconLeftPainter = rememberVectorPainter("ic_user"),
        text = if (isUserAuthorized) {
            userEmail
        } else {
            stringResource(MR.strings.settings_authorization)
        },
        onClick = onLogInClick.takeIf { !isUserAuthorized },
        iconRight = {
            Icon(
                modifier = Modifier
                    .padding(end = 16.dp)
                    .size(28.dp)
                    .clip(CircleShape)
                    .`if`(isUserAuthorized) {
                        clickable { onLogOutClick.invoke() }
                    }
                    .padding(2.dp),
                painter = if (isUserAuthorized) {
                    rememberVectorPainter("ic_logout")
                } else {
                    rememberVectorPainter("ic_arrow_authorize")
                },
                contentDescription = null,
                tint = CoinTheme.color.content
            )
        }
    )
}