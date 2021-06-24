package co.mercenary.creators.kotlin.util.math

import co.mercenary.creators.kotlin.util.*

interface Solver<T, R> : Validated {

    @FrameworkDsl
    operator fun invoke(data: T, vararg more: T): R
}