package com.finance_tracker.finance_tracker.core.common

abstract class IosContext

actual typealias Context = IosContext

object EmptyContext: IosContext()