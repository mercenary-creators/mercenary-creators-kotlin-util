package co.mercenary.creators.kotlin.util.test.main

import co.mercenary.creators.kotlin.util.*
import co.mercenary.creators.kotlin.util.security.Encoder

@FrameworkDsl
object MainDean {

    @JvmStatic
    @FrameworkDsl
    fun none() {
        getStandardError().echo(CREATORS_VERSION_INFO).newline()
    }

    @JvmStatic
    @FrameworkDsl
    fun data(): MainData = MainData()

    @JvmStatic
    @FrameworkDsl
    fun text(): Encoder<String, String> {
        return Encoders.text()
    }
}