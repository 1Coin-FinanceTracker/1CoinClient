package com.finance_tracker.finance_tracker.features.add_transaction.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.TabRowDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.statusBarsPadding
import com.finance_tracker.finance_tracker.core.feature_flags.FeatureFlag
import com.finance_tracker.finance_tracker.core.feature_flags.FeaturesManager
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.AppBarIcon
import com.finance_tracker.finance_tracker.core.ui.rememberVectorPainter
import com.finance_tracker.finance_tracker.core.ui.tab_rows.TransactionTypeTab
import com.finance_tracker.finance_tracker.core.ui.tab_rows.TransactionTypesMode
import com.finance_tracker.finance_tracker.core.ui.tab_rows.TransactionTypesTabRow
import ru.alexgladkov.odyssey.compose.local.LocalRootController

@Composable
internal fun CategoriesAppBar(
    doneButtonEnabled: Boolean,
    featuresManager: FeaturesManager,
    modifier: Modifier = Modifier,
    selectedTransactionType: TransactionTypeTab = TransactionTypeTab.Expense,
    onTransactionTypeSelect: (TransactionTypeTab) -> Unit = {},
    onDoneClick: () -> Unit = {}
) {
    val rootController = LocalRootController.current
    Column(
        modifier = modifier
            .background(CoinTheme.color.backgroundSurface)
            .statusBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .height(56.dp)
                .padding(horizontal = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AppBarIcon(
                painter = rememberVectorPainter("ic_arrow_back"),
                onClick = { rootController.popBackStack() }
            )

            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(horizontal = 16.dp)
                    .weight(1f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom
            ) {
                TransactionTypesTabRow(
                    modifier = Modifier.wrapContentWidth(),
                    transactionTypesMode = if (featuresManager.isEnabled(FeatureFlag.Transfer)) {
                        TransactionTypesMode.Full
                    } else {
                        TransactionTypesMode.Main
                    },
                    selectedType = selectedTransactionType,
                    hasBottomDivider = false,
                    isHorizontallyCentered = true,
                    onSelect = onTransactionTypeSelect
                )
            }

            AppBarIcon(
                painter = rememberVectorPainter("ic_done"),
                onClick = onDoneClick,
                enabled = doneButtonEnabled
            )
        }

        TabRowDefaults.Divider(
            color = CoinTheme.color.dividers
        )
    }
}