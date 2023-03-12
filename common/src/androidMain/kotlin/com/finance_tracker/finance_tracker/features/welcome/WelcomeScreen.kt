package com.finance_tracker.finance_tracker.features.welcome

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.common.StoredViewModel
import com.finance_tracker.finance_tracker.core.common.UpdateSystemBarsConfigEffect
import com.finance_tracker.finance_tracker.core.common.VectorAnimation
import com.finance_tracker.finance_tracker.core.common.navigationBarsPadding
import com.finance_tracker.finance_tracker.core.common.statusBarsPadding
import com.finance_tracker.finance_tracker.core.common.view_models.watchViewActions
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.rememberVectorPainter
import com.finance_tracker.finance_tracker.core.ui.welcome.EmailSignInButton
import com.finance_tracker.finance_tracker.core.ui.welcome.SkipButton
import com.finance_tracker.finance_tracker.features.welcome.views.GoogleSignInButton
import dev.icerock.moko.resources.compose.stringResource

private const val TopSpaceWeight = 0.252f
private const val BottomSpaceWeight = 0.126f
private const val MiddleContentWight = 1f - (TopSpaceWeight + BottomSpaceWeight)
private val MaxButtonWidth = 560.dp

@Composable
internal fun WelcomeScreen() {
    StoredViewModel<WelcomeViewModel> { viewModel ->
        UpdateSystemBarsConfigEffect {
            isStatusBarLight = true
        }

        viewModel.watchViewActions { action, baseLocalsStorage ->
            handleAction(action, baseLocalsStorage)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(CoinTheme.color.primary)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier
                    .padding(top = 24.dp)
                    .statusBarsPadding()
                    .size(
                        width = 92.1.dp,
                        height = 22.3.dp
                    ),
                painter = rememberVectorPainter("ic_logo_1coin"),
                contentDescription = null,
                tint = CoinTheme.color.primaryVariant
            )

            Spacer(modifier = Modifier.weight(TopSpaceWeight))

            VectorAnimation(
                modifier = Modifier
                    .weight(MiddleContentWight)
                    .aspectRatio(1f),
                fileResource = MR.files.anim_welcome
            )

            Spacer(modifier = Modifier.weight(BottomSpaceWeight))

            Text(
                text = stringResource(MR.strings.welcome_header),
                style = CoinTheme.typography.h2,
                color = CoinTheme.color.primaryVariant,
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = stringResource(MR.strings.welcome_subheader),
                style = CoinTheme.typography.body1_medium,
                color = CoinTheme.color.primaryVariant.copy(
                    alpha = 0.5f
                ),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(54.dp))

            GoogleSignInButton(
                modifier = Modifier
                    .padding(bottom = 12.dp)
                    .widthIn(max = MaxButtonWidth)
                    .fillMaxWidth(),
                onClick = viewModel::onContinueWithGoogleClick,
                onSuccess = viewModel::onGoogleSignInSuccess,
                onError = viewModel::onGoogleSignInError
            )

            EmailSignInButton(
                modifier = Modifier
                    .widthIn(max = MaxButtonWidth)
                    .fillMaxWidth(),
                onClick = viewModel::onContinueWithEmailClick
            )
            Spacer(modifier = Modifier.height(4.dp))
            SkipButton(
                modifier = Modifier
                    .widthIn(max = MaxButtonWidth)
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(bottom = 2.dp),
                onClick = viewModel::onSkipClick
            )
        }
    }
}