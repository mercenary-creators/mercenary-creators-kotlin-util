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

package co.mercenary.creators.kotlin.util.test

import co.mercenary.creators.kotlin.util.*
import org.junit.jupiter.api.*
import java.io.ByteArrayOutputStream
import java.util.*

abstract class AbstractKotlinTest : AbstractLogging() {

    init {
        Encoders
    }

    val loader = DefaultContentResourceLoader()

    private val conf: Properties by lazy {
        getConfigPropertiesBuilder().invoke()
    }

    protected open fun getConfigPropertiesBuilder(): () -> Properties = { Properties() }

    fun getConfigProperty(name: String, other: String = EMPTY_STRING): String = conf.getProperty(name, other)

    fun setConfigProperty(vararg args: Pair<String, Any>) {
        if (args.isNotEmpty()) {
            val temp = conf
            for ((k, v) in args) {
                temp.setProperty(k, v.toString())
            }
        }
    }

    fun baos(size: Int = DEFAULT_BUFFER_SIZE): ByteArrayOutputStream = ByteArrayOutputStream(size)

    fun assertEach(vararg list: Executable) {
        if (list.isNotEmpty()) {
            Assertions.assertAll(*list)
        }
    }

    fun assertEach(list: List<Executable>) {
        if (list.isNotEmpty()) {
            Assertions.assertAll(list)
        }
    }

    fun assumeThat(condition: Boolean, executable: Executable) {
        Assumptions.assumingThat(condition, executable)
    }

    fun assertTrue(condition: Boolean, block: () -> Any?) {
        Assertions.assertTrue(condition, toSafeString(block))
    }

    fun assertFalse(condition: Boolean, block: () -> Any?) {
        Assertions.assertFalse(condition, toSafeString(block))
    }

    fun assertEquals(expected: Any?, actual: Any?, block: () -> Any?) {
        Assertions.assertEquals(expected, actual, toSafeString(block))
    }

    fun assertEquals(expected: ByteArray?, actual: ByteArray?, block: () -> Any?) {
        Assertions.assertArrayEquals(expected, actual, toSafeString(block))
    }

    fun assertNotEquals(expected: ByteArray, actual: ByteArray, block: () -> Any?) {
        assertFalse(expected.contentEquals(actual), block)
    }

    fun assertNotEquals(expected: Any?, actual: Any?, block: () -> Any?) {
        Assertions.assertNotEquals(expected, actual, toSafeString(block))
    }

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

    fun <T : Any?> List<T>.shouldBe(value: Iterable<*>?, block: () -> Any?) = assertEquals(value?.toList(), this, block)

    fun <T : Any> T?.shouldBe(value: Any?, block: () -> Any?) = assertEquals(value, this, block)

    fun <T : Any> (() -> T?).shouldBe(value: Any?, block: () -> Any?) = assertEquals(value, this.invoke(), block)

    fun <T : Any> (() -> T?).shouldBe(value: () -> Any?, block: () -> Any?) = assertEquals(value.invoke(), this.invoke(), block)

    fun ByteArray?.shouldBe(value: ByteArray?, block: () -> Any?) = assertEquals(value, this, block)

    fun <T : Any?> List<T>.shouldNotBe(value: Iterable<*>?, block: () -> Any?) = assertNotEquals(value?.toList(), this, block)

    fun <T : Any> T?.shouldNotBe(value: Any?, block: () -> Any?) = assertNotEquals(value, this, block)

    fun <T : Any> (() -> T?).shouldNotBe(value: Any?, block: () -> Any?) = assertNotEquals(value, this.invoke(), block)

    fun <T : Any> (() -> T?).shouldNotBe(value: () -> Any?, block: () -> Any?) = assertNotEquals(value.invoke(), this.invoke(), block)

    fun ByteArray.shouldNotBe(value: ByteArray, block: () -> Any?) = assertNotEquals(value, this, block)

    fun <T> timed(block: () -> T): T = timed({ info { it } }, block)
}