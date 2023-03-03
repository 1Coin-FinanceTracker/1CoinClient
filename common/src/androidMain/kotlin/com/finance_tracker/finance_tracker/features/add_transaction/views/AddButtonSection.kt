package com.finance_tracker.finance_tracker.features.add_transaction.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.common.navigationBarsPadding
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.IconActionButton
import com.finance_tracker.finance_tracker.core.ui.PrimaryButton
import com.finance_tracker.finance_tracker.core.ui.rememberVectorPainter
import dev.icerock.moko.resources.compose.stringResource

@Composable
internal fun ActionButtonsSection(
    modifier: Modifier = Modifier,
    hasActiveStep: Boolean = true,
    isEditMode: Boolean = false,
    enabled: Boolean = true,
    onAddClick: () -> Unit = {},
    onEditClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {},
    onDuplicateClick: () -> Unit = {}
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .background(CoinTheme.color.background),
        elevation = if (hasActiveStep) 8.dp else 0.dp,
        color = CoinTheme.color.background
    ) {
        Row(
            modifier = Modifier
                .navigationBarsPadding()
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (isEditMode) {
                IconActionButton(
                    painter = rememberVectorPainter("ic_delete"),
                    tint = CoinTheme.color.accentRed,
                    onClick = onDeleteClick
                )
                IconActionButton(
                    painter = rememberVectorPainter("ic_duplicate"),
                    tint = CoinTheme.color.content,
                    onClick = onDuplicateClick
                )
            }

            PrimaryButton(
                modifier = Modifier.weight(1f),
                text = if (isEditMode) {
                    stringResource(MR.strings.add_transaction_btn_edit)
                } else {
                    stringResource(MR.strings.add_transaction_btn_add)
                },
                onClick = {
                    if (isEditMode) {
                        onEditClick.invoke()
                    } else {
                        onAddClick.invoke()
                    }
                },
                enabled = enabled
            )
        }
    }
}