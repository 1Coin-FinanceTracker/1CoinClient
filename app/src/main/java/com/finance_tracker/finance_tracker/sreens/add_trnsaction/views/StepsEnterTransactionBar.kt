package com.finance_tracker.finance_tracker.sreens.add_trnsaction.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.R
import com.finance_tracker.finance_tracker.data.models.Account
import com.finance_tracker.finance_tracker.data.models.Category
import com.finance_tracker.finance_tracker.sreens.add_trnsaction.views.enter_transaction_controller.AccountCard
import com.finance_tracker.finance_tracker.theme.CoinTheme

data class StepsEnterTransactionBarData(
    val currentStep: EnterTransactionStep = EnterTransactionStep.Account,
    val accountData: Account? = null,
    val categoryData: Category? = null
)

@Composable
fun StepsEnterTransactionBar(
    data: StepsEnterTransactionBarData,
    modifier: Modifier = Modifier,
    onStepSelect: (EnterTransactionStep) -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(CoinTheme.color.background),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        AccountStage(
            data = data,
            onStageSelect = onStepSelect
        )

        NextIcon()

        CategoryStage(
            data = data,
            onStageSelect = onStepSelect
        )
    }
}

@Composable
fun RowScope.AccountStage(
    data: StepsEnterTransactionBarData,
    modifier: Modifier = Modifier,
    onStageSelect: (EnterTransactionStep) -> Unit
) {
    StageText(
        modifier = modifier,
        currentStep = EnterTransactionStep.Account,
        data = data.accountData,
        selectedStep = data.currentStep,
        onStepSelect = onStageSelect,
        dataContent = { AccountCard(account = it) }
    )
}

@Composable
fun RowScope.CategoryStage(
    data: StepsEnterTransactionBarData,
    modifier: Modifier = Modifier,
    onStageSelect: (EnterTransactionStep) -> Unit
) {
    StageText(
        modifier = modifier,
        currentStep = EnterTransactionStep.Category,
        data = data.categoryData,
        selectedStep = data.currentStep,
        onStepSelect = onStageSelect,
        dataContent = { CategoryRow(category = it) }
    )
}

@Composable
fun CategoryRow(
    category: Category,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
    ) {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(category.icon),
                contentDescription = null
            )
            Text(
                modifier = Modifier.padding(start = 4.dp),
                text = category.name,
                style = CoinTheme.typography.body2,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun NextIcon(
    modifier: Modifier = Modifier
) {
    Icon(
        modifier = modifier
            .padding(horizontal = 8.dp),
        painter = painterResource(R.drawable.ic_arrow_next_small),
        contentDescription = null
    )
}

@Composable
private fun <T: Any> RowScope.StageText(
    currentStep: EnterTransactionStep,
    data: T?,
    selectedStep: EnterTransactionStep,
    onStepSelect: (EnterTransactionStep) -> Unit,
    modifier: Modifier = Modifier,
    dataContent: @Composable (data: T) -> Unit = {}
) {
    val isActiveStage = currentStep == selectedStep
    Box(
        modifier = modifier
            .weight(1f)
            .clickable(enabled = !isActiveStage) { onStepSelect.invoke(currentStep) }
            .padding(vertical = 16.dp, horizontal = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        if (data == null) {
            Text(
                text = currentStep.textRes?.let { stringResource(it) }.orEmpty(),
                style = CoinTheme.typography.subtitle1,
                textAlign = TextAlign.Center,
                color = if (isActiveStage) {
                    LocalContentColor.current
                } else {
                    LocalContentColor.current.copy(alpha = 0.3f)
                }
            )
        } else {
            dataContent.invoke(data)
        }
    }
}

@Preview
@Composable
fun StagesNavigationBarPreview() {
    StepsEnterTransactionBar(
        data = StepsEnterTransactionBarData()
    )
}