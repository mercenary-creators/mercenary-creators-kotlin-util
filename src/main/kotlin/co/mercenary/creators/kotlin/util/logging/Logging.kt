/*
 * Copyright (c) 2020, Mercenary Creators Company. All rights reserved.
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

open class Logging @JvmOverloads @CreatorsDsl constructor(name: String? = null) : ILoggingBase {

    init {
        LoggingFactory
    }

    private val logs: mu.KLogger by lazy {
        if (name == null) LoggingFactory.loggerOf(this) else LoggingFactory.loggerOf(name)
    }

    @CreatorsDsl
    override fun loggerOf(): mu.KLogger = logs

    @LoggingInfoDsl
    fun <T> timed(block: () -> T): T = timed({ info { it } }, block)
}