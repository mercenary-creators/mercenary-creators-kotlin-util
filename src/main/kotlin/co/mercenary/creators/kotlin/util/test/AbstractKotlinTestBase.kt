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

package co.mercenary.creators.kotlin.util.test

import co.mercenary.creators.kotlin.util.*
import java.util.*
import kotlin.reflect.KClass

@IgnoreForSerialize
abstract class AbstractKotlinTestBase : Logging() {

    init {
        Encoders
    }

    private val conf: Properties by lazy {
        getConfigPropertiesBuilder().invoke()
    }

    @IgnoreForSerialize
    protected val printer: (Int, String) -> Unit = { i, s -> info { "%2d : %s".format(i + 1, s) } }

    @IgnoreForSerialize
    protected open fun getConfigPropertiesBuilder(): () -> Properties = { Properties() }

    @JvmOverloads
    fun dash(loop: Int = 64): String = "-".repeat(loop.abs())

    @JvmOverloads
    fun getConfigProperty(name: String, other: String = EMPTY_STRING): String = conf.getProperty(name, other)

    fun setConfigProperty(vararg args: Pair<String, Any?>) {
        if (args.isNotEmpty()) {
            val temp = conf
            for ((k, v) in args) {
                when (v == null) {
                    true -> temp.remove(k)
                    else -> temp.setProperty(k, v.toString())
                }
            }
        }
    }

    fun here(): Map<String, Any?> {
        val type = javaClass.name
        Exception().stackTrace.forEach {
            if (it.className == type) {
                return mapOf("func" to it.methodName, "type" to type, "file" to it.fileName, "line" to it.lineNumber)
            }
        }
        return mapOf("type" to type)
    }

    override fun toString(): String {
        return javaClass.name
    }

    fun measured(loop: Int, call: (Int) -> Unit) {
        val list = LongArray(loop)
        loop forEach {
            val time = TimeAndDate.nanos()
            call.invoke(it)
            list[it] = TimeAndDate.nanos() - time
        }
        info { TimeAndDate.toElapsedString(list.average().toLong()) }
    }

    fun annotations(data: Any) {
        annotations(data.javaClass)
    }

    fun annotations(type: KClass<*>) {
        annotations(type.java)
    }

    fun annotations(type: Class<*>) {
        if (isLoggingInfoEnabled) {
            info { type.name }
            type.annotations.forEach {
                val name = it.annotationClass.java.name
                if (name != "kotlin.Metadata") {
                    info { name }
                }
            }
        }
    }

    fun String.toLink() = toURL()

    protected open fun fail(text: String): Nothing {
        throw MercenaryAssertExceptiion(text)
    }

    protected open fun fail(func: () -> Any?): Nothing {
        fail(Formatters.toSafeString(func))
    }

    protected open fun assertTrueOf(condition: Boolean, func: () -> Any?) {
        if (!condition) {
            fail(func)
        }
    }

    protected open fun assumeThat(func: () -> Unit): Throwable? {
        return try {
            func.invoke()
            null
        }
        catch (oops: Throwable) {
            if (oops is OutOfMemoryError) {
                throw oops
            }
            if (oops is StackOverflowError) {
                throw oops
            }
            oops
        }
    }

    protected open fun assumeEach(vararg args: () -> Unit) {
        if (args.isNotEmpty()) {
            val list = args.mapNotNull { assumeThat(it) }
            if (list.isNotEmpty()) {
                val oops = MercenaryMultipleAssertExceptiion(list)
                list.forEach {
                    oops.addSuppressed(it)
                }
                throw oops
            }
        }
    }

    infix fun <T : Any?> T.shouldBe(value: T) = assertTrueOf(value isSameAs this) {
        "shouldBe failed"
    }

    infix fun <T : Any?> T.shouldNotBe(value: T) = assertTrueOf(value isNotSameAs this) {
        "shouldNotBe failed"
    }
}