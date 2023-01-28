package com.finance_tracker.finance_tracker.presentation.welcome

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.common.StoredViewModel
import com.finance_tracker.finance_tracker.core.common.navigationBarsPadding
import com.finance_tracker.finance_tracker.core.common.statusBarsPadding
import com.finance_tracker.finance_tracker.core.common.view_models.watchViewActions
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.rememberVectorPainter
import com.finance_tracker.finance_tracker.presentation.welcome.views.EmailSignInButton
import com.finance_tracker.finance_tracker.presentation.welcome.views.GoogleSignInButton
import com.finance_tracker.finance_tracker.presentation.welcome.views.SkipButton
import dev.icerock.moko.resources.compose.stringResource

private const val TopSpaceWeight = 0.252f
private const val BottomSpaceWeight = 0.126f
private const val MiddleContentWight = 1f - (TopSpaceWeight + BottomSpaceWeight)

@Composable
internal fun WelcomeScreen() {
    StoredViewModel<WelcomeViewModel> { viewModel ->

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

            Box(
                modifier = Modifier
                    .weight(MiddleContentWight)
                    .aspectRatio(1f)
                    .background(CoinTheme.color.primaryVariant)
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
                modifier = Modifier.fillMaxWidth(),
                onClick = viewModel::onContinueWithGoogleClick
            )
            Spacer(modifier = Modifier.height(12.dp))
            EmailSignInButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = viewModel::onContinueWithEmailClick
            )
            Spacer(modifier = Modifier.height(4.dp))
            SkipButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(bottom = 2.dp),
                onClick = viewModel::onSkipClick
            )
        }
    }
}