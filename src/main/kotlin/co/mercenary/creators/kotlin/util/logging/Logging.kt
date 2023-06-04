/*
 * Copyright (c) 2023, Mercenary Creators Company. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package co.mercenary.creators.kotlin.util.logging

import co.mercenary.creators.kotlin.util.*
import kotlin.reflect.KClass

open class Logging @JvmOverloads @FrameworkDsl constructor(other: Any? = null) : ILoggingBase {

    init {
        LoggingFactory
    }

    @FrameworkDsl
    private val logs: mu.KLogger by lazy {
        when (other) {
            null -> LoggingFactory.loggerOf(this)
            is Class<*> -> LoggingFactory.loggerOf(other)
            is KClass<*> -> LoggingFactory.loggerOf(other)
            is CharSequence -> LoggingFactory.loggerOf(other)
            else -> LoggingFactory.loggerOf(other)
        }
    }

    @FrameworkDsl
    override fun loggerOf(): mu.KLogger = logs

    @LoggingInfoDsl
    fun <T> timed(block: () -> T): T {
        if (isLoggingInfoEnabled().isNotTrue()) {
            return block()
        }
        return TimeAndDate.nanos().let { time ->
            block().also {
                info { TimeAndDate.toElapsedString(TimeAndDate.nanos() - time) }
            }
        }
    }
}