/*
 * Copyright (c) 2019, Mercenary Creators Company. All rights reserved.
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

import java.util.concurrent.atomic.AtomicBoolean
import kotlin.reflect.KClass

@Suppress("NOTHING_TO_INLINE")
object LoggingFactory {

    const val KE = "$"

    const val KT = "Kt$"

    private val opem = AtomicBoolean(false)

    init {
        if (opem.compareAndSet(false, true)) {
            (org.slf4j.LoggerFactory.getILoggerFactory() as  ch.qos.logback.classic.LoggerContext).frameworkPackages.add(ILoggingBase::class.java.`package`.name)
        }
    }

    @JvmStatic
    fun logger(name: String): ILogging = Logging(name)

    @JvmStatic
    fun logger(self: Any): ILogging = logger(self.javaClass)

    @JvmStatic
    fun logger(type: Class<*>): ILogging = logger(type.name)

    @JvmStatic
    fun logger(type: KClass<*>): ILogging = logger(type.java)

    @JvmStatic
    inline fun logger(noinline func: () -> Unit): ILogging {
        val name = func.javaClass.name
        return when {
            name.contains(KT) -> logger(name.substringBefore(KT))
            name.contains(KE) -> logger(name.substringBefore(KE))
            else -> logger(name)
        }
    }
}