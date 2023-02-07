package com.finance_tracker.finance_tracker.core.common

import dev.icerock.moko.resources.StringResource

expect fun StringResource.localizedString(context: Context): String

expect fun getLocale(): String