package com.finance_tracker.finance_tracker.presentation.email_auth.enter_email

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.common.StoredViewModel
import com.finance_tracker.finance_tracker.core.common.imePadding
import com.finance_tracker.finance_tracker.core.common.navigationBarsPadding
import com.finance_tracker.finance_tracker.core.common.statusBarsPadding
import com.finance_tracker.finance_tracker.core.common.view_models.watchViewActions
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.AppBarIcon
import com.finance_tracker.finance_tracker.core.ui.CoinOutlinedTextField
import com.finance_tracker.finance_tracker.core.ui.PrimaryButton
import com.finance_tracker.finance_tracker.core.ui.rememberVectorPainter
import dev.icerock.moko.resources.compose.stringResource

@Composable
internal fun EnterEmailScreen() {
    StoredViewModel<EnterEmailViewModel> { viewModel ->

        viewModel.watchViewActions { action, baseLocalsStorage ->
            handleAction(action, baseLocalsStorage)
        }

        val focusRequester = remember { FocusRequester() }

        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }

        Column(
            modifier = Modifier.background(CoinTheme.color.background),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EnterEmailAppBar(
                modifier = Modifier
                    .statusBarsPadding(),
                onBackClick = viewModel::onBackClick
            )

            Spacer(modifier = Modifier.height(72.dp))

            Text(
                text = stringResource(MR.strings.enter_email_title),
                style = CoinTheme.typography.h4,
                textAlign = TextAlign.Center
            )

            val email by viewModel.email.collectAsState()
            CoinOutlinedTextField(
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .fillMaxWidth()
                    .padding(
                        top = 24.dp,
                        start = 16.dp,
                        end = 16.dp
                    ),
                value = email,
                onValueChange = viewModel::onEmailChange,
                label = {
                    Text(
                        text = stringResource(MR.strings.enter_email_field_email_label),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
                singleLine = true,
                maxLines = 1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            Spacer(modifier = Modifier.weight(1f))

            val isContinueEnabled by viewModel.isContinueEnabled.collectAsState()
            PrimaryButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 16.dp)
                    .navigationBarsPadding()
                    .imePadding(),
                text = stringResource(MR.strings.enter_email_btn_continue),
                enabled = isContinueEnabled,
                onClick = viewModel::onContinueClick
            )
        }
    }
}

@Composable
private fun EnterEmailAppBar(
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

        Icon(
            modifier = Modifier
                .align(Alignment.Center)
                .size(
                    width = 92.1.dp,
                    height = 22.3.dp
                ),
            painter = rememberVectorPainter("ic_logo_1coin"),
            contentDescription = null,
            tint = CoinTheme.color.primary
        )
    }
}