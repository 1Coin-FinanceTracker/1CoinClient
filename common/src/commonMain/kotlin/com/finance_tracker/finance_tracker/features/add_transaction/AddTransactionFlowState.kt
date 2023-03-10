package com.finance_tracker.finance_tracker.features.add_transaction

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

data class AddTransactionFlowState(
    val flow: AddTransactionFlow
) {
    private val currentStepIndex = MutableStateFlow(-1)
    val currentStepFlow = currentStepIndex.map { flow.steps.getOrNull(it) }
    val hasPreviousStep = MutableStateFlow(currentStepIndex.value > 0)

    fun previous() {
        val previousIndex = currentStepIndex.value - 1
        if (previousIndex < 0) return

        setCurrentStepIndex(previousIndex)
    }

    fun next() {
        val nextIndex = currentStepIndex.value + 1
        if (nextIndex >= flow.steps.size) return

        setCurrentStepIndex(nextIndex)
    }

    fun setStep(step: EnterTransactionStep?) {
        val index = flow.steps.indexOf(step)
        setCurrentStepIndex(index)
    }

    fun setStepAfter(step: EnterTransactionStep) {
        val nextStepIndex = flow.steps.indexOf(step) + 1
        if (nextStepIndex in flow.steps.indices) {
            setCurrentStepIndex(nextStepIndex)
        }
    }

    private fun setCurrentStepIndex(step: Int) {
        currentStepIndex.value = step
        hasPreviousStep.value = step > 0
    }

    companion object {

        fun createNewStatesForAllFlow(): Map<AddTransactionFlow, AddTransactionFlowState> {
            return AddTransactionFlow.values()
                .associateWith { AddTransactionFlowState(it) }
        }
    }
}