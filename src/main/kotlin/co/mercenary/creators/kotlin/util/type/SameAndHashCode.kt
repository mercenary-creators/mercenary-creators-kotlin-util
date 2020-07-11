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
import java.io.*
import java.io.ByteArrayOutputStream
import java.math.*
import java.net.*
import java.nio.channels.ReadableByteChannel
import java.nio.file.Path
import java.util.concurrent.atomic.*
import kotlin.reflect.KClass

object SameAndHashCode {

    @JvmStatic
    @CreatorsDsl
    fun isEverySameAs(vararg args: Pair<Any?, Any?>): Boolean {
        for ((value, other) in args) {
            if (isNotSameAs(value, other)) {
                return false
            }
        }
        return true
    }

    @JvmStatic
    @CreatorsDsl
    fun isEverySameAs(args: Iterable<Pair<Any?, Any?>>): Boolean {
        for ((value, other) in args) {
            if (isNotSameAs(value, other)) {
                return false
            }
        }
        return true
    }

    @JvmStatic
    @CreatorsDsl
    fun isEverySameAs(args: Sequence<Pair<Any?, Any?>>): Boolean {
        for ((value, other) in args) {
            if (isNotSameAs(value, other)) {
                return false
            }
        }
        return true
    }

    @JvmStatic
    @CreatorsDsl
    fun isSameAs(value: Any?, other: Any?): Boolean {
        if (value === other) {
            return true
        }
        return when (value) {
            null -> other == null
            is String -> if (other is String) value == other else false
            is Array<*> -> if (other is Array<*>) value contentDeepEquals other else false
            is IntArray -> if (other is IntArray) value contentEquals other else false
            is ByteArray -> if (other is ByteArray) value contentEquals other else false
            is CharArray -> if (other is CharArray) value contentEquals other else false
            is LongArray -> if (other is LongArray) value contentEquals other else false
            is ShortArray -> if (other is ShortArray) value contentEquals other else false
            is DoubleArray -> if (other is DoubleArray) value contentEquals other else false
            is BooleanArray -> if (other is BooleanArray) value contentEquals other else false
            is AtomicBoolean -> if (other is Boolean) value.toBoolean() == other else if (other is AtomicBoolean) value.toBoolean() == other.toBoolean() else false
            is Boolean -> if (other is Boolean) value == other else if (other is AtomicBoolean) value == other.toBoolean() else false
            is Number -> when (other) {
                is Number -> when (value) {
                    is Int -> if (other is Int) value == other else if (other is AtomicInteger) value == other.toInt() else if (other is BigInteger) value.toBigInteger() == other else false
                    is Byte -> if (other is Byte) value == other else false
                    is Char -> if (other is Char) value == other else false
                    is Short -> if (other is Short) value == other else false
                    is Float -> if (other is Float) value.toBits() == other.toBits() else if (other is BigDecimal) value.toBigDecimal() == other else false
                    is Double -> if (other is Double) value.toBits() == other.toBits() else if (other is BigDecimal) value.toBigDecimal() == other else false
                    is Long -> if (other is Long) value == other else if (other is AtomicLong) value == other.toLong() else if (other is BigInteger) value.toBigInteger() == other else false
                    is BigDecimal -> when (other) {
                        is Float -> other.toBigDecimal() == value
                        is Double -> other.toBigDecimal() == value
                        is BigDecimal -> value == other
                        else -> false
                    }
                    is BigInteger -> when (other) {
                        is Int -> other.toBigInteger() == value
                        is Long -> other.toBigInteger() == value
                        is AtomicLong -> other.toBigInteger() == value
                        is AtomicInteger -> other.toBigInteger() == value
                        is BigInteger -> value == other
                        else -> false
                    }
                    is AtomicLong -> if (other is Long) value.toLong() == other else if (other is AtomicLong) value == other else if (other is BigInteger) value.toBigInteger() == other else false
                    is AtomicInteger -> if (other is Int) value.toInt() == other else if (other is AtomicInteger) value.toInt() == other.toInt() else if (other is BigInteger) value.toBigInteger() == other else false
                    else -> false
                }
                else -> false
            }
            else -> value == other
        }
    }

    @JvmStatic
    @CreatorsDsl
    fun isNotSameAs(value: Any?, other: Any?): Boolean = !isSameAs(value, other)

    @JvmStatic
    @CreatorsDsl
    fun isContentCapable(value: Any?): Boolean = when (value) {
        null -> false
        is URI -> true
        is URL -> true
        is File -> true
        is Path -> true
        is Reader -> true
        is ByteArray -> true
        is InputStream -> true
        is CharSequence -> true
        is ContentResource -> true
        is ReadableByteChannel -> true
        is InputStreamSupplier -> true
        is ByteArrayOutputStream -> true
        else -> false
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
                                return false
                            }
                        }
                        return true
                    }
                }
                catch (cause: Throwable) {
                }
            }
        }
        return false
    }

    @JvmStatic
    @CreatorsDsl
    fun isContentNotSameAs(value: Any?, other: Any?): Boolean = !isContentSameAs(value, other)

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
            is InputStream -> value.toByteArray()
            is CharSequence -> value.getContentData()
            is ContentResource -> value.getContentData()
            is ReadableByteChannel -> value.toByteArray()
            is InputStreamSupplier -> value.toByteArray()
            is ByteArrayOutputStream -> value.getContentData()
            else -> ByteArray(0)
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