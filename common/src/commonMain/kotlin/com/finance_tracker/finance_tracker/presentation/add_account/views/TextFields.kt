package com.finance_tracker.finance_tracker.presentation.add_account.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.theme.staticTextSize
import com.finance_tracker.finance_tracker.core.ui.CoinOutlinedSelectTextField
import com.finance_tracker.finance_tracker.core.ui.CoinOutlinedTextField
import com.finance_tracker.finance_tracker.domain.models.AccountColorModel
import com.finance_tracker.finance_tracker.presentation.add_account.AddAccountViewModel
import com.finance_tracker.finance_tracker.presentation.add_account.dropdown_menus.AccountColorsDropdownMenu
import com.finance_tracker.finance_tracker.presentation.add_account.dropdown_menus.AccountTypesDropdownMenu
import dev.icerock.moko.resources.compose.stringResource

private const val AccountNameCharsLimit = 40
private const val AmountCharsLimit = 24

@Composable
internal fun AccountNameTextField(
    viewModel: AddAccountViewModel,
    modifier: Modifier = Modifier
) {
    val titleAccount by viewModel.enteredAccountName.collectAsState()
    CoinOutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp),
        value = titleAccount,
        onValueChange = viewModel::onAccountNameChange,
        label = {
            Text(
                text = stringResource(MR.strings.new_account_field_name_label),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        placeholder = {
            Text(
                text = stringResource(MR.strings.new_account_field_name_placeholder),
                style = CoinTheme.typography.body1.staticTextSize()
            )
        },
        singleLine = true,
        maxLines = 1,
        charsLimit = AccountNameCharsLimit,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
    )
}

@Composable
internal fun AmountTextField(
    viewModel: AddAccountViewModel,
    modifier: Modifier = Modifier
) {
    val enteredBalance by viewModel.enteredBalance.collectAsState()
    val amountCurrencies by viewModel.amountCurrencies.collectAsState()
    val selectedCurrency by viewModel.selectedCurrency.collectAsState()
    CoinOutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp),
        value = enteredBalance,
        onValueChange = viewModel::onAmountChange,
        label = {
            Text(
                text = stringResource(MR.strings.new_account_field_amount_label),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        placeholder = {
            Text(
                text = stringResource(MR.strings.new_account_field_amount_placeholder),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = CoinTheme.typography.body1.staticTextSize()
            )
        },
        trailingIcon = {
            CurrencySelector(
                modifier = Modifier
                    .padding(end = 10.dp),
                items = amountCurrencies,
                selectedCurrency = selectedCurrency,
                onCurrencySelect = viewModel::onCurrencySelect
            )
        },
        singleLine = true,
        maxLines = 1,
        charsLimit = AmountCharsLimit,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}

@Composable
internal fun RowScope.AccountTypeTextField(
    viewModel: AddAccountViewModel,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    val accountTypeMenuExpanded = remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    val selectedType by viewModel.selectedType.collectAsState()
    val valueTextId = selectedType?.textId
    CoinOutlinedSelectTextField(
        value = if (valueTextId != null) {
            stringResource(valueTextId)
        } else {
            ""
        },
        modifier = modifier
            .weight(1f)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                focusManager.clearFocus()
                accountTypeMenuExpanded.value = true
            },
        label = {
            Text(
                text = stringResource(MR.strings.new_account_field_type_label),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        placeholder = {
            Text(
                text = stringResource(MR.strings.new_account_field_type_placeholder),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = CoinTheme.typography.body1.staticTextSize()
            )
        },
        selected = accountTypeMenuExpanded.value
    )

    val accountTypes by viewModel.types.collectAsState()
    AccountTypesDropdownMenu(
        items = accountTypes,
        expandedState = accountTypeMenuExpanded,
        onSelect = viewModel::onAccountTypeSelect
    )
}

@Composable
internal fun RowScope.AccountColorTextField(
    viewModel: AddAccountViewModel,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    val accountColorMenuOffsetX = remember { mutableStateOf(0) }
    val accountColorMenuExpanded = remember { mutableStateOf(false) }
    val selectedColor by viewModel.selectedColor.collectAsState()
    CoinOutlinedSelectTextField(
        value = selectedColor?.colorName?.let { stringResource(it) }.orEmpty(),
        modifier = modifier
            .onGloballyPositioned {
                accountColorMenuOffsetX.value = it.positionInParent().x.toInt()
            }
            .weight(1f)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                focusManager.clearFocus()
                accountColorMenuExpanded.value = true
            },
        label = {
            Text(
                text = stringResource(MR.strings.new_account_field_color_label),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        leadingIcon = if (selectedColor != null) {
            { ColorIcon(selectedColor) }
        } else {
            null
        },
        placeholder = {
            Text(
                text = stringResource(MR.strings.new_account_field_color_placeholder),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = CoinTheme.typography.body1.staticTextSize()
            )
        },
        selected = accountColorMenuExpanded.value
    )

    val accountColors by viewModel.colors.collectAsState()
    AccountColorsDropdownMenu(
        items = accountColors,
        expandedState = accountColorMenuExpanded,
        offsetXState = accountColorMenuOffsetX,
        onSelect = viewModel::onAccountColorSelect
    )
}

@Composable
private fun ColorIcon(accountColorModel: AccountColorModel?) {
    if (accountColorModel != null) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .background(
                    color = accountColorModel.color,
                    shape = CircleShape
                )
        )
    }
}