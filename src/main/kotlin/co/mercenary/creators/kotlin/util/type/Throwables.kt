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

package co.mercenary.creators.kotlin.util.type

import co.mercenary.creators.kotlin.util.*
import co.mercenary.creators.kotlin.util.collection.BasicLinkedSet
import kotlin.reflect.KClass

@FrameworkDsl
@IgnoreForSerialize
object Throwables {

    enum class Mode {
        @FrameworkDsl
        FAILURE,
        @FrameworkDsl
        IGNORED }

    @FrameworkDsl
    private val failure = BasicLinkedSet<Class<*>>()

    @FrameworkDsl
    private val ignored = BasicLinkedSet<Class<*>>()

    @FrameworkDsl
    private val logger: ILogging by lazy {
        logsOfType<Throwables>()
    }

    init {
        defaults()
    }

    @JvmStatic
    @FrameworkDsl
    fun thrown(cause: Throwable?) {
        if (cause != null) {
            if (isIgnored(cause)) {
                logger.debug {
                    "${cause.nameOf()}(${cause.message}) IGNORED."
                }
            } else if (isFailure(cause)) {
                logger.error {
                    "${cause.nameOf()}(${cause.message}) FAILURE."
                }
                throw cause
            }
        }
    }

    @JvmStatic
    @FrameworkDsl
    fun <T : Throwable> isFailure(type: Class<T>): Boolean {
        return type in failure
    }

    @JvmStatic
    @FrameworkDsl
    fun <T : Throwable> isFailure(type: KClass<T>): Boolean {
        return isFailure(type.java)
    }

    @JvmStatic
    @FrameworkDsl
    fun isFailure(cause: Throwable): Boolean {
        return isFailure(cause.javaClass)
    }

    @JvmStatic
    @FrameworkDsl
    fun <T : Throwable> isIgnored(type: Class<T>): Boolean {
        return type in ignored
    }

    @JvmStatic
    @FrameworkDsl
    fun <T : Throwable> isIgnored(type: KClass<T>): Boolean {
        return isIgnored(type.java)
    }

    @JvmStatic
    @FrameworkDsl
    fun isIgnored(cause: Throwable): Boolean {
        return isIgnored(cause.javaClass)
    }

    @JvmStatic
    @FrameworkDsl
    @Synchronized
    @JvmOverloads
    fun <T : Throwable> append(type: Class<T>, mode: Mode = Mode.FAILURE) {
        when (mode) {
            Mode.FAILURE -> failure.add(type)
            Mode.IGNORED -> ignored.add(type)
        }
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun <T : Throwable> append(type: KClass<T>, mode: Mode = Mode.FAILURE) {
        append(type.java, mode)
    }

    @JvmStatic
    @FrameworkDsl
    fun <T : Throwable> ignored(type: Class<T>) {
        append(type, Mode.IGNORED)
    }

    @JvmStatic
    @FrameworkDsl
    fun <T : Throwable> ignored(type: KClass<T>) {
        append(type.java, Mode.IGNORED)
    }

    @JvmStatic
    @FrameworkDsl
    @Synchronized
    @JvmOverloads
    fun <T : Throwable> remove(type: Class<T>, mode: Mode = Mode.FAILURE) {
        when (mode) {
            Mode.FAILURE -> failure.remove(type)
            Mode.IGNORED -> ignored.remove(type)
        }
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun <T : Throwable> remove(type: KClass<T>, mode: Mode = Mode.FAILURE) {
        remove(type.java, mode)
    }

    @JvmStatic
    @FrameworkDsl
    @Synchronized
    fun clear(vararg args: Mode) {
        if (args.isExhausted()) {
            failure.clear()
            ignored.clear()
        } else {
            args.forEach { kind ->
                when (kind) {
                    Mode.FAILURE -> failure.clear()
                    Mode.IGNORED -> ignored.clear()
                }
            }
        }
    }

    @JvmStatic
    @FrameworkDsl
    @Synchronized
    @JvmOverloads
    fun reset(defaults: Boolean = true) {
        clear()
        if (defaults.isTrue()) {
            defaults()
        }
    }

    @JvmStatic
    @FrameworkDsl
    fun <T> fatal(cause: Throwable, block: Factory<T>): T {
        when (cause) {
            is OutOfMemoryError -> throw cause
            is StackOverflowError -> throw cause
        }
        return block.create()
    }

    @JvmStatic
    @FrameworkDsl
    fun <T> fatal(cause: Throwable, value: T): T {
        when (cause) {
            is OutOfMemoryError -> throw cause
            is StackOverflowError -> throw cause
        }
        return value
    }

    @JvmStatic
    @FrameworkDsl
    fun check(cause: Throwable): Throwable {
        return fatal(cause, cause)
    }

    @JvmStatic
    @FrameworkDsl
    private fun defaults() {
        append(OutOfMemoryError::class)
        append(StackOverflowError::class)
        append(NullPointerException::class)
        append(MercenaryFatalExceptiion::class)
        append(MercenaryAssertionExceptiion::class)
    }
}