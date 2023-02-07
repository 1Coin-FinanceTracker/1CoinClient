package com.finance_tracker.finance_tracker.presentation.add_account.views

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.IconActionButton
import com.finance_tracker.finance_tracker.core.ui.PrimaryButton
import com.finance_tracker.finance_tracker.core.ui.rememberVectorPainter
import com.finance_tracker.finance_tracker.domain.models.Account
import com.finance_tracker.finance_tracker.presentation.add_account.AddAccountViewModel
import dev.icerock.moko.resources.compose.stringResource

@Composable
internal fun EditAccountActions(
    viewModel: AddAccountViewModel,
    addEnable: Boolean,
    account: Account,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(top = 24.dp)
    ) {
        IconActionButton(
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    end = 8.dp
                ),
            painter = rememberVectorPainter("ic_recycle_bin"),
            tint = CoinTheme.color.accentRed,
            onClick = { viewModel.onDeleteClick(account) }
        )
        PrimaryButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp),
            text = stringResource(MR.strings.edit_account_btn_save),
            onClick = viewModel::onAddAccountClick,
            enabled = addEnable
        )
    }
}