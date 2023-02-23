package com.finance_tracker.finance_tracker.features.add_transaction

fun String.applyKeyboardCommand(command: KeyboardCommand): String {
    val amountText = this
    if (!amountText.matches(Regex("^\\d*\\.?\\d*"))) {
        return amountText
    }

    var newAmountText = amountText
    when (command) {
        KeyboardCommand.Delete -> {
            when {
                amountText.length <= 1 && amountText.toDouble() == 0.0 -> {
                    /* ignore */
                }

                amountText.length <= 1 && amountText.toDouble() != 0.0 -> {
                    newAmountText = "0"
                }

                else -> {
                    newAmountText = newAmountText.dropLast(1)
                }
            }
        }

        is KeyboardCommand.Digit -> {
            if (amountText == "0") {
                newAmountText = command.value.toString()
            } else {
                newAmountText += command.value.toString()
            }
        }

        KeyboardCommand.Point -> {
            if (!newAmountText.contains(".")) {
                newAmountText += "."
            }
        }
    }
    if (newAmountText.matches(Regex("^\\d*\\.?\\d*"))) {
        return newAmountText
    }
    return amountText
}