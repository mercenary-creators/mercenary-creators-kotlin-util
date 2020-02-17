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
import org.junit.jupiter.api.*
import java.util.*

abstract class AbstractKotlinTest : Logging() {

    init {
        Encoders
    }

    private val conf: Properties by lazy {
        getConfigPropertiesBuilder().invoke()
    }

    protected val printer: (Int, String) -> Unit = { i, s -> info { "%2d : %s".format(i + 1, s) } }

    protected val form = TimeAndDate.getThreadLocalDefaultDateFormat()

    protected open fun getConfigPropertiesBuilder(): () -> Properties = { Properties() }

    @JvmOverloads
    fun getConfigProperty(name: String, other: String = EMPTY_STRING): String = conf.getProperty(name, other)

    fun setConfigProperty(vararg args: Pair<String, Any>) {
        if (args.isNotEmpty()) {
            val temp = conf
            for ((k, v) in args) {
                temp.setProperty(k, v.toString())
            }
        }
    }

    @JvmOverloads
    fun measured(loop: Int, logs: (Any?) -> Unit = { info { it } }, call: (Int) -> Unit) {
        val list = DoubleArray(loop)
        loop.forEach {
            val time = TimeAndDate.nanos()
            call(it)
            list[it] = TimeAndDate.nanos() - time.toDouble()
        }
        val time = list.average()
        logs(TimeAndDate.toElapsedString(time.toLong()))
    }

    @JvmOverloads
    fun dash(loop: Int = 48): String = "-".repeat(loop.abs())

    inline fun <reified T : Throwable, R> assumeThat(block: () -> R): R? {
        return try {
            block()
        }
        catch (cause: Throwable) {
            val good = when (cause) {
                is T -> true
                else -> false
            }
            if (good.not()) {
                throw AssertionError(null, cause)
            }
            null
        }
    }

    infix fun <T : Any?> T.shouldBe(value: T) = Assertions.assertTrue(value isSameAs this)

    infix fun <T : Any?> T.shouldNotBe(value: T) = Assertions.assertTrue(value isNotSameAs this)
}