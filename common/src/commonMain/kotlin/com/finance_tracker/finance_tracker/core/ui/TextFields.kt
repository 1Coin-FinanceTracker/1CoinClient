package com.finance_tracker.finance_tracker.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.theme.CoinTextFieldDefaults
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import dev.icerock.moko.resources.compose.stringResource

@Composable
internal fun CoinOutlinedTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    onValueChange: (String) -> Unit = {},
    label: @Composable() (() -> Unit)? = null,
    placeholder: @Composable() (() -> Unit)? = null,
    leadingIcon: @Composable() (() -> Unit)? = null,
    trailingIcon: @Composable() (() -> Unit)? = null,
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    charsLimit: Int = Int.MAX_VALUE,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        label = label,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        onValueChange = {
            if (it.length <= charsLimit) {
                onValueChange.invoke(it)
            }
        },
        colors = CoinTextFieldDefaults.outlinedTextFieldColors(),
        shape = RoundedCornerShape(12.dp),
        maxLines = maxLines,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
    )
}

@Composable
internal fun CoinOutlinedSelectTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    onValueChange: (String) -> Unit = {},
    leadingIcon: @Composable (() -> Unit)? = null,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    selected: Boolean = false
) {
    val isEmpty = value.isBlank()
    ClickableOutlinedTextField(
        modifier = modifier,
        value = value,
        label = label,
        placeholder = placeholder,
        onValueChange = onValueChange,
        colors = CoinTextFieldDefaults.outlinedSelectTextFieldColors(
            borderColor = if (selected) {
                CoinTheme.color.primary
            } else {
                CoinTheme.color.dividers
            },
            labelColor = if (selected && !isEmpty) {
                CoinTheme.color.primary
            } else {
                CoinTheme.color.content.copy(alpha = 0.5f)
            }
        ),
        shape = RoundedCornerShape(12.dp),
        maxLines = 1,
        singleLine = true,
        leadingIcon = leadingIcon,
        trailingIcon = {
            Icon(
                modifier = Modifier
                    .size(24.dp),
                painter = rememberVectorPainter("ic_expand_more_small"),
                contentDescription = null,
                tint = CoinTheme.color.content
            )
        },
        borderThickness = if (selected) 2.dp else 1.dp
    )
}

@Composable
private fun ClickableOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = MaterialTheme.shapes.small,
    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(),
    borderThickness: Dp = 1.dp
) {
    val enabled = false
    // If color is not provided via the text style, use content color as a default
    val textColor = textStyle.color.takeOrElse {
        colors.textColor(enabled).value
    }
    val mergedTextStyle = textStyle.merge(TextStyle(color = textColor))

    @OptIn(ExperimentalMaterialApi::class)
    BasicTextField(
        value = value,
        modifier = if (label != null) {
            modifier
                .semantics(mergeDescendants = true) {}
                .padding(top = OutlinedTextFieldTopPadding)
        } else {
            modifier
        }
            .background(colors.backgroundColor(enabled).value, shape)
            .defaultMinSize(
                minWidth = TextFieldDefaults.MinWidth,
                minHeight = TextFieldDefaults.MinHeight
            ),
        onValueChange = onValueChange,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = mergedTextStyle,
        cursorBrush = SolidColor(colors.cursorColor(isError).value),
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        interactionSource = interactionSource,
        singleLine = singleLine,
        maxLines = maxLines,
        decorationBox = @Composable { innerTextField ->
            TextFieldDefaults.OutlinedTextFieldDecorationBox(
                value = value,
                visualTransformation = visualTransformation,
                innerTextField = innerTextField,
                placeholder = placeholder,
                label = label,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                singleLine = singleLine,
                enabled = enabled,
                isError = isError,
                interactionSource = interactionSource,
                colors = colors,
                border = {
                    TextFieldDefaults.BorderBox(
                        enabled = enabled,
                        isError = isError,
                        interactionSource = interactionSource,
                        colors = colors,
                        shape = shape,
                        focusedBorderThickness = borderThickness,
                        unfocusedBorderThickness = borderThickness
                    )
                }
            )
        }
    )
}

@Composable
internal fun CoinCodeTextField(
    code: String,
    onCodeChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    length: Int = 4,
) {
    BasicTextField(
        modifier = modifier,
        value = code,
        onValueChange = {
            if (it.take(length).all { char -> char.isDigit() }) {
                onCodeChange(it.take(length))
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        decorationBox = {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                repeat(length) { index ->
                    ConfirmationTextBox(
                        index = index,
                        text = code
                    )
                }
            }
        }
    )
}

@Suppress("MagicNumber")
@Composable
internal fun SearchCurrencyTextField(
    text: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    length: Int = 20,
) {
    TextField(
        modifier = modifier,
        value = text,
        onValueChange = {
            if (it.length <= length) {
                onTextChange(it)
            }
        },
        placeholder = {
            Text(
                text = stringResource(MR.strings.currency_screen_search),
                style = CoinTheme.typography.body1,
                color = CoinTheme.color.content.copy(alpha = 0.4f),
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            textColor = CoinTheme.color.content,
            disabledTextColor = Color.Transparent,
            backgroundColor = CoinTheme.color.backgroundSurface,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            cursorColor = CoinTheme.color.primary,
        ),
        textStyle = CoinTheme.typography.body1,
        singleLine = true,
    )
}

@Composable
private fun ConfirmationTextBox(
    index: Int,
    text: String,
    modifier: Modifier = Modifier,
) {
    val isFocused by remember (text.length, index) {
        derivedStateOf { text.length == index }
    }
    val char = text.getOrNull(index)?.toString().orEmpty()
    Box(
        modifier = modifier
            .size(
                width = 56.dp,
                height = 70.dp
            )
            .border(
                width = 1.dp,
                color = if (isFocused) {
                    CoinTheme.color.primary
                } else {
                    CoinTheme.color.dividers
                },
                shape = RoundedCornerShape(12.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = char,
            style = CoinTheme.typography.h2,
            color = CoinTheme.color.content,
        )
    }
}

internal val OutlinedTextFieldTopPadding = 8.dp