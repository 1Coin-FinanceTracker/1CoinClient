package com.finance_tracker.finance_tracker.domain.models

import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.common.Context
import com.finance_tracker.finance_tracker.core.common.localizedString

data class Category(
    val id: Long,
    val name: String,
    val iconId: String
) {
    companion object {

        fun empty(context: Context): Category {
            return Category(
                id = -1,
                name = MR.strings.category_empty.localizedString(context),
                iconId = "ic_category_13"
            )
        }
    }
}