package com.finance_tracker.finance_tracker.presentation.email_auth.enter_otp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.common.StoredViewModel
import com.finance_tracker.finance_tracker.core.common.clicks.scaleClickAnimation
import com.finance_tracker.finance_tracker.core.common.imePadding
import com.finance_tracker.finance_tracker.core.common.navigationBarsPadding
import com.finance_tracker.finance_tracker.core.common.noRippleClickable
import com.finance_tracker.finance_tracker.core.common.statusBarsPadding
import com.finance_tracker.finance_tracker.core.common.view_models.watchViewActions
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.AppBarIcon
import com.finance_tracker.finance_tracker.core.ui.CoinCodeTextField
import com.finance_tracker.finance_tracker.core.ui.PrimaryButton
import com.finance_tracker.finance_tracker.core.ui.rememberVectorPainter
import dev.icerock.moko.resources.compose.stringResource
import org.koin.core.parameter.parametersOf

@Composable
internal fun EnterOtpScreen(email: String) {
    StoredViewModel<EnterOtpViewModel>(
        parameters = { parametersOf(email) }
    ) { viewModel ->

        viewModel.watchViewActions { action, baseLocalsStorage ->
            handleAction(action, baseLocalsStorage)
        }

        val focusRequester = remember { FocusRequester() }
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }

        val isWrongCodeStateEnabled by viewModel.isWrongCodeStateEnabled.collectAsState()
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EnterOtpAppBar(
                modifier = Modifier
                    .statusBarsPadding(),
                onBackClick = viewModel::onBackClick
            )

            Spacer(modifier = Modifier.height(72.dp))

            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                text = stringResource(MR.strings.enter_otp_title),
                style = CoinTheme.typography.h4,
                textAlign = TextAlign.Center
            )

            Text(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .padding(top = 8.dp),
                text = buildAnnotatedString {
                    append(stringResource(MR.strings.enter_otp_subtitle))
                    withStyle(SpanStyle(fontWeight = FontWeight.Medium)) {
                        append(viewModel.email)
                    }
                },
                style = CoinTheme.typography.body1,
                color = CoinTheme.color.secondary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.padding(top = 24.dp))

            val otp by viewModel.otp.collectAsState()

            CoinCodeTextField(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .focusRequester(focusRequester),
                code = otp,
                onCodeChange = viewModel::onOtpChange
            )

            Spacer(modifier = Modifier.height(24.dp))

            if (isWrongCodeStateEnabled) {
                Text(
                    text = stringResource(MR.strings.enter_otp_wrong_code),
                    style = CoinTheme.typography.body1,
                    color = CoinTheme.color.accentRed,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            val requestAgainLeftSeconds by viewModel.requestAgainLeftSeconds.collectAsState()
            val isRequestAgainEnabled by viewModel.isRequestAgainEnabled.collectAsState()
            @Suppress("MagicNumber")
            Text(
                modifier = Modifier
                    .alpha(alpha = if (isRequestAgainEnabled) 1f else 0.4f)
                    .padding(bottom = 12.dp)
                    .scaleClickAnimation(enabled = isRequestAgainEnabled)
                    .clip(RoundedCornerShape(4.dp))
                    .noRippleClickable(enabled = isRequestAgainEnabled) { viewModel.onRequestCodeAgainClick() }
                    .padding(
                        vertical = 2.dp,
                        horizontal = 4.dp
                    ),
                text = if (isRequestAgainEnabled) {
                    stringResource(MR.strings.enter_otp_request_again)
                } else {
                    stringResource(MR.strings.enter_otp_request_again_time, requestAgainLeftSeconds)
                },
                color = CoinTheme.color.primary,
                style = CoinTheme.typography.subtitle2_medium,
                textAlign = TextAlign.Center
            )

            val isContinueEnabled by viewModel.icCreateAccountEnabled.collectAsState()
            PrimaryButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp)
                    .navigationBarsPadding()
                    .imePadding(),
                text = stringResource(MR.strings.enter_otp_btn_create_account),
                enabled = isContinueEnabled,
                onClick = viewModel::onCreateAccountClick
            )
        }
    }
}

@Composable
private fun EnterOtpAppBar(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(56.dp)
            .fillMaxWidth()
    ) {
        AppBarIcon(
            modifier = Modifier
                .padding(start = 8.dp)
                .align(Alignment.CenterStart),
            painter = rememberVectorPainter("ic_arrow_back"),
            onClick = onBackClick,
        )
    }
}