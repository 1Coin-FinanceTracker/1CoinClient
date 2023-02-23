package com.finance_tracker.finance_tracker.features.add_transaction

fun AddTransactionFlowState.setCurrentStepForFlowState(
    isPrimaryAccountEmpty: Boolean,
    isSecondaryAccountEmpty: Boolean,
    isExpenseCategoryEmpty: Boolean,
    isIncomeCategoryEmpty: Boolean
) {
    when (flow) {
        AddTransactionFlow.Expense -> {
            when {
                isPrimaryAccountEmpty -> {
                    setStep(EnterTransactionStep.PrimaryAccount)
                }
                isExpenseCategoryEmpty -> {
                    setStep(EnterTransactionStep.Category)
                }
                else -> {
                    setStep(EnterTransactionStep.PrimaryAmount)
                }
            }
        }
        AddTransactionFlow.Income -> {
            when {
                isPrimaryAccountEmpty -> {
                    setStep(EnterTransactionStep.PrimaryAccount)
                }
                isIncomeCategoryEmpty -> {
                    setStep(EnterTransactionStep.Category)
                }
                else -> {
                    setStep(EnterTransactionStep.PrimaryAmount)
                }
            }
        }
        AddTransactionFlow.Transfer -> {
            when {
                isPrimaryAccountEmpty -> {
                    setStep(EnterTransactionStep.PrimaryAccount)
                }
                isSecondaryAccountEmpty -> {
                    setStep(EnterTransactionStep.SecondaryAccount)
                }
                else -> {
                    setStep(EnterTransactionStep.PrimaryAmount)
                }
            }
        }
    }
}