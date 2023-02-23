package com.finance_tracker.finance_tracker.features.add_transaction

enum class AddTransactionFlow(
    val steps: List<EnterTransactionStep>
) {
    Expense(
        steps = listOf(
            EnterTransactionStep.PrimaryAccount,
            EnterTransactionStep.Category,
            EnterTransactionStep.PrimaryAmount
        )
    ),
    Income(
        steps = listOf(
            EnterTransactionStep.PrimaryAccount,
            EnterTransactionStep.Category,
            EnterTransactionStep.PrimaryAmount
        )
    ),
    Transfer(
        steps = listOf(
            EnterTransactionStep.PrimaryAccount,
            EnterTransactionStep.SecondaryAccount,
            EnterTransactionStep.PrimaryAmount,
            EnterTransactionStep.SecondaryAmount
        )
    );
}