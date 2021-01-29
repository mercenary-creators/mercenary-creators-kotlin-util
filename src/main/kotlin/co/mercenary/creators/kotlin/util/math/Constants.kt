package co.mercenary.creators.kotlin.util.math

import co.mercenary.creators.kotlin.util.*

@FrameworkDsl
@IgnoreForSerialize
object Constants {

    @FrameworkDsl
    @IgnoreForSerialize
    object IntConstants {

        @FrameworkDsl
        const val ZERO = 0

        @FrameworkDsl
        const val MONO = 1

        @FrameworkDsl
        const val DUAL = 2

        @FrameworkDsl
        const val NONE = -1

        @FrameworkDsl
        const val MOST = Int.MAX_VALUE

        @FrameworkDsl
        const val BITS = Int.SIZE_BITS

        @FrameworkDsl
        const val SIZE = Int.SIZE_BYTES
    }
}