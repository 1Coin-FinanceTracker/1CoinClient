package com.finance_tracker.finance_tracker.presentation.widgets

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.ui.CoinWidget
import com.finance_tracker.finance_tracker.domain.models.Account
import com.finance_tracker.finance_tracker.presentation.home.AccountsWidgetContent
import dev.icerock.moko.resources.compose.stringResource

@Composable
internal fun MyAccountsWidget(
    accounts: List<Account>,
    accountsLazyListState: LazyListState,
    onAccountClick: (account: Account) -> Unit,
    onAddAccountClick: () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    CoinWidget(
        modifier = modifier,
        title = stringResource(MR.strings.home_my_accounts),
        withHorizontalPadding = false,
        onClick = onClick
    ) {
        AccountsWidgetContent(
            data = accounts,
            state = accountsLazyListState,
            onAccountClick = onAccountClick,
            onAddAccountClick = onAddAccountClick
        )
    }
}