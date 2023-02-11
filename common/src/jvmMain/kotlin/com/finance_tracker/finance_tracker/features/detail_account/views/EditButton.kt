package com.finance_tracker.finance_tracker.features.detail_account.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.common.clicks.scaleClickAnimation
import com.finance_tracker.finance_tracker.core.common.getLocale
import com.finance_tracker.finance_tracker.core.common.statusBarsPadding
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.theme.staticTextSize
import com.finance_tracker.finance_tracker.core.ui.collapsing_toolbar.CollapsingToolbarScaffoldState
import com.finance_tracker.finance_tracker.core.ui.collapsing_toolbar.CollapsingToolbarScopeInstance.road
import com.finance_tracker.finance_tracker.core.ui.collapsing_toolbar.animate
import com.finance_tracker.finance_tracker.core.ui.collapsing_toolbar.rememberCollapsingToolbarScaffoldState
import com.finance_tracker.finance_tracker.core.ui.rememberVectorPainter
import dev.icerock.moko.resources.compose.stringResource
import ru.alexgladkov.odyssey.compose.helpers.noRippleClickable

@Composable
internal fun EditButton(
    modifier: Modifier = Modifier,
    state: CollapsingToolbarScaffoldState = rememberCollapsingToolbarScaffoldState(),
    tint: Color = CoinTheme.color.content,
    onClick: () -> Unit = {}
) {
    val locale = getLocale()
    val width by remember(locale) {
        derivedStateOf {
            if (locale == "ru") {
                160.dp
            } else {
                84.dp
            }
        }
    }
    Row(
        modifier = modifier
            .statusBarsPadding()
            .padding(
                vertical = state.animate(16.dp, 8.dp),
                horizontal = 16.dp
            )
            .size(
                width = state.animate(width, 50.dp),
                height = state.animate(42.dp, 34.dp)
            )
            .scaleClickAnimation()
            .clip(RoundedCornerShape(percent = 50))
            .noRippleClickable { onClick.invoke() }
            .road(Alignment.BottomEnd, Alignment.BottomEnd)
            .background(CoinTheme.color.white)
            .padding(vertical = state.animate(12.dp, 8.dp))
            .padding(start = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            modifier = Modifier.size(18.dp),
            painter = rememberVectorPainter("ic_edit"),
            tint = tint,
            contentDescription = null
        )
        Text(
            modifier = Modifier
                .graphicsLayer {
                    alpha = state.toolbarState.progress
                }
                .padding(start = 4.dp),
            text = stringResource(MR.strings.detail_account_btn_edit),
            color = tint,
            style = CoinTheme.typography.body1_medium.staticTextSize(),
            maxLines = 1,
            overflow = TextOverflow.Clip
        )
    }
}