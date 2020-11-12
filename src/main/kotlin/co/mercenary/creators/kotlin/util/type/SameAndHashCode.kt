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
import co.mercenary.creators.kotlin.util.io.*
import co.mercenary.creators.kotlin.util.io.BytesOutputStream
import java.io.*
import java.io.ByteArrayOutputStream
import java.math.*
import java.net.*
import java.nio.ByteBuffer
import java.nio.channels.ReadableByteChannel
import java.nio.file.Path
import java.util.concurrent.atomic.*
import kotlin.reflect.KClass

object SameAndHashCode {

    @JvmStatic
    @CreatorsDsl
    fun isEverySameAs(vararg args: Maybe): Boolean {
        val size = args.size
        if (size.isNotEven()) {
            return false.toBoolean()
        }
        for (i in 0 until size step 2) {
            if (isNotSameAs(args[i], args[i + 1])) {
                return false.toBoolean()
            }
        }
        return true.toBoolean()
    }

    @JvmStatic
    @CreatorsDsl
    fun isEverySameAs(args: Iterable<Pair<Any?, Any?>>): Boolean {
        for ((value, other) in args) {
            if (isNotSameAs(value, other)) {
                return false.toBoolean()
            }
        }
        return true.toBoolean()
    }

    @JvmStatic
    @CreatorsDsl
    fun isEverySameAs(args: Sequence<Pair<Any?, Any?>>): Boolean {
        for ((value, other) in args) {
            if (isNotSameAs(value, other)) {
                return false.toBoolean()
            }
        }
        return true.toBoolean()
    }

    @JvmStatic
    @CreatorsDsl
    fun isSameAs(value: Any?, other: Any?): Boolean {
        if (value === other) {
            return true.toBoolean()
        }
        return when (value) {
            null -> other == null
            is String -> if (other is String) value == other else false.toBoolean()
            is Array<*> -> if (other is Array<*>) value isSameArrayAs other else false.toBoolean()
            is IntArray -> if (other is IntArray) value isSameArrayAs other else false.toBoolean()
            is ByteArray -> if (other is ByteArray) value isSameArrayAs other else false.toBoolean()
            is CharArray -> if (other is CharArray) value isSameArrayAs other else false.toBoolean()
            is LongArray -> if (other is LongArray) value isSameArrayAs other else false.toBoolean()
            is ShortArray -> if (other is ShortArray) value isSameArrayAs other else false.toBoolean()
            is FloatArray -> if (other is FloatArray) value isSameArrayAs other else false.toBoolean()
            is DoubleArray -> if (other is DoubleArray) value isSameArrayAs other else false.toBoolean()
            is BooleanArray -> if (other is BooleanArray) value isSameArrayAs other else false.toBoolean()
            is AtomicBoolean -> if (other is Boolean) value.toBoolean() == other else if (other is AtomicBoolean) value.toBoolean() == other.toBoolean() else false.toBoolean()
            is Boolean -> if (other is Boolean) value == other else if (other is AtomicBoolean) value == other.toBoolean() else false.toBoolean()
            is Number -> when (other) {
                is Number -> when (value) {
                    is Int -> if (other is Int) value == other else if (other is AtomicInteger) value == other.toInt() else if (other is BigInteger) value.toBigInteger() == other else false.toBoolean()
                    is Byte -> if (other is Byte) value == other else false.toBoolean()
                    is Char -> if (other is Char) value == other else false.toBoolean()
                    is Short -> if (other is Short) value == other else false.toBoolean()
                    is Float -> if (other is Float) value.toBits() == other.toBits() else if (other is BigDecimal) value.toBigDecimal() == other else false.toBoolean()
                    is Double -> if (other is Double) value.toBits() == other.toBits() else if (other is BigDecimal) value.toBigDecimal() == other else false.toBoolean()
                    is Long -> if (other is Long) value == other else if (other is AtomicLong) value == other.toLong() else if (other is BigInteger) value.toBigInteger() == other else false.toBoolean()
                    is BigDecimal -> when (other) {
                        is Float -> other.toBigDecimal() == value
                        is Double -> other.toBigDecimal() == value
                        is BigDecimal -> value == other
                        else -> false.toBoolean()
                    }
                    is BigInteger -> when (other) {
                        is Int -> other.toBigInteger() == value
                        is Long -> other.toBigInteger() == value
                        is AtomicLong -> other.toBigInteger() == value
                        is AtomicInteger -> other.toBigInteger() == value
                        is BigInteger -> value == other
                        else -> false.toBoolean()
                    }
                    is AtomicLong -> if (other is Long) value.toLong() == other else if (other is AtomicLong) value == other else if (other is BigInteger) value.toBigInteger() == other else false.toBoolean()
                    is AtomicInteger -> if (other is Int) value.toInt() == other else if (other is AtomicInteger) value.toInt() == other.toInt() else if (other is BigInteger) value.toBigInteger() == other else false.toBoolean()
                    else -> false.toBoolean()
                }
                else -> false.toBoolean()
            }
            else -> value == other
        }
    }

    @JvmStatic
    @CreatorsDsl
    fun isNotSameAs(value: Any?, other: Any?): Boolean = isSameAs(value, other).isNotTrue()

    @JvmStatic
    @CreatorsDsl
    fun isContentCapable(value: Any?): Boolean = when (value) {
        null -> false.toBoolean()
        is URI -> true.toBoolean()
        is URL -> true.toBoolean()
        is File -> true.toBoolean()
        is Path -> true.toBoolean()
        is Reader -> true.toBoolean()
        is ByteArray -> true.toBoolean()
        is ByteBuffer -> true.toBoolean()
        is InputStream -> true.toBoolean()
        is CharSequence -> true.toBoolean()
        is ContentResource -> true.toBoolean()
        is BytesOutputStream -> true.toBoolean()
        is ReadableByteChannel -> true.toBoolean()
        is InputStreamSupplier -> true.toBoolean()
        is ByteArrayOutputStream -> true.toBoolean()
        else -> false.toBoolean()
    }

    @JvmStatic
    @CreatorsDsl
    fun isContentSameAs(value: Any?, other: Any?): Boolean {
        if (value != null && other != null) {
            if (isContentCapable(value) && isContentCapable(other)) {
                try {
                    val v = contentOf(value)
                    val o = contentOf(other)
                    if (v.size == o.size) {
                        for (i in v.indices) {
                            if (v[i] != o[i]) {
                                return false.toBoolean()
                            }
                        }
                        return true.toBoolean()
                    }
                } catch (cause: Throwable) {
                }
            }
        }
        return isSameAs(value, other)
    }

    @JvmStatic
    @CreatorsDsl
    fun isContentNotSameAs(value: Any?, other: Any?): Boolean = isContentSameAs(value, other).isNotTrue()

    @JvmStatic
    @CreatorsDsl
    private fun contentOf(value: Any): ByteArray {
        return when (value) {
            is URI -> value.toByteArray()
            is URL -> value.toByteArray()
            is File -> value.toByteArray()
            is Path -> value.toByteArray()
            is Reader -> value.toByteArray()
            is ByteArray -> value.toByteArray()
            is CharArray -> value.toByteArray()
            is ByteBuffer -> value.toByteArray()
            is InputStream -> value.toByteArray()
            is CharSequence -> value.getContentData()
            is ContentResource -> value.getContentData()
            is ReadableByteChannel -> value.toByteArray()
            is InputStreamSupplier -> value.toByteArray()
            is ByteArrayOutputStream -> value.getContentData()
            else -> EMPTY_BYTE_ARRAY
        }
    }

    @JvmStatic
    @CreatorsDsl
    fun hashOf(vararg args: Any?, accumulator: (Int) -> Unit) {
        args.forEach {
            accumulator(hashOf(it))
        }
    }

    @JvmStatic
    @CreatorsDsl
    fun hashOf(value: Any?): Int {
        return when (value) {
            null -> 0
            is Path -> value.toFile().hashCode()
            is Array<*> -> value.contentDeepHashCode()
            is IntArray -> value.contentHashCode()
            is ByteArray -> value.contentHashCode()
            is CharArray -> value.contentHashCode()
            is LongArray -> value.contentHashCode()
            is ShortArray -> value.contentHashCode()
            is FloatArray -> value.contentHashCode()
            is DoubleArray -> value.contentHashCode()
            is BooleanArray -> value.contentHashCode()
            else -> value.hashCode()
        }
    }

    @JvmStatic
    @CreatorsDsl
    fun hashOf(vararg args: Any?): Int {
        return hashOf(1.toAtomic(), *args)
    }

    @JvmStatic
    @CreatorsDsl
    fun idenOf(value: Any?): Int {
        return when (value) {
            null -> 0
            else -> System.identityHashCode(value)
        }
    }

    @JvmStatic
    @CreatorsDsl
    private fun hashOf(hash: AtomicInteger, vararg args: Any?): Int {
        hashOf(*args) {
            hash * 31 + it
        }
        return hash.toInt()
    }

    @JvmStatic
    @CreatorsDsl
    fun nameOf(value: Any?): String {
        return when (value) {
            null -> NULLS_STRING
            is Class<*> -> value.name
            is KClass<*> -> value.java.name
            else -> value.javaClass.name
        }
    }
}