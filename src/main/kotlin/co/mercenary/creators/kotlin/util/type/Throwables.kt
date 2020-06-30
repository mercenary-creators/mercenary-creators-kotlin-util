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

package co.mercenary.creators.kotlin.util.type

import co.mercenary.creators.kotlin.util.*
import kotlin.reflect.KClass

object Throwables {

    enum class Mode { FAILURE, IGNORED }

    private val failure = mutableSetOf<Class<*>>()

    private val ignored = mutableSetOf<Class<*>>()

    private val logger: ILogging by lazy {
        LoggingFactory.logger(Throwables::class)
    }

    init {
        reset()
    }

    @JvmStatic
    @CreatorsDsl
    @Synchronized
    fun thrown(cause: Throwable?) {
        if (cause != null) {
            val type = cause.javaClass
            if (type in ignored) {
                logger.debug {
                    "${type.name}(${cause.message}) IGNORED."
                }
            }
            else if (type in failure) {
                logger.debug {
                    "${type.name}(${cause.message}) FAILURE."
                }
                throw cause
            }
        }
    }

    @JvmStatic
    @CreatorsDsl
    @Synchronized
    @JvmOverloads
    fun <T : Throwable> append(type: Class<T>, mode: Mode = Mode.FAILURE) {
        when (mode) {
            Mode.FAILURE -> failure.add(type)
            Mode.IGNORED -> ignored.add(type)
        }
    }

    @JvmStatic
    @CreatorsDsl
    @Synchronized
    @JvmOverloads
    fun <T : Throwable> append(type: KClass<T>, mode: Mode = Mode.FAILURE) {
        append(type.java, mode)
    }

    @JvmStatic
    @CreatorsDsl
    @Synchronized
    fun <T : Throwable> ignored(type: Class<T>) {
        ignored.add(type)
    }

    @JvmStatic
    @CreatorsDsl
    @Synchronized
    fun <T : Throwable> ignored(type: KClass<T>) {
        ignored(type.java)
    }

    @JvmStatic
    @CreatorsDsl
    @Synchronized
    @JvmOverloads
    fun <T : Throwable> remove(type: Class<T>, mode: Mode = Mode.FAILURE) {
        when (mode) {
            Mode.FAILURE -> failure.remove(type)
            Mode.IGNORED -> ignored.remove(type)
        }
    }

    @JvmStatic
    @CreatorsDsl
    @Synchronized
    @JvmOverloads
    fun <T : Throwable> remove(type: KClass<T>, mode: Mode = Mode.FAILURE) {
        remove(type.java, mode)
    }

    @JvmStatic
    @CreatorsDsl
    @Synchronized
    fun clear(vararg args: Mode) {
        if (args.isEmpty()) {
            failure.clear()
            ignored.clear()
        }
        else {
            args.forEach { kind ->
                when (kind) {
                    Mode.FAILURE -> failure.clear()
                    Mode.IGNORED -> ignored.clear()
                }
            }
        }
    }

    @JvmStatic
    @CreatorsDsl
    @Synchronized
    @JvmOverloads
    fun reset(defaults: Boolean = true) {
        clear()
        if (defaults) {
            append(OutOfMemoryError::class)
            append(StackOverflowError::class)
            append(NullPointerException::class)
            append(MercenaryFatalExceptiion::class)
            append(MercenaryAssertExceptiion::class)
        }
    }
}