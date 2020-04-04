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
import co.mercenary.creators.kotlin.util.ByteArrayOutputStream
import co.mercenary.creators.kotlin.util.io.InputStreamSupplier
import java.io.*
import java.math.*
import java.net.*
import java.nio.channels.ReadableByteChannel
import java.nio.file.Path
import java.util.concurrent.atomic.*

object SameAndHashCode {

    @JvmStatic
    @AssumptionDsl
    fun isEverySameAs(vararg args: Pair<Any?, Any?>): Boolean {
        for ((value, other) in args) {
            if (isNotSameAs(value, other)) {
                return false
            }
        }
        return true
    }

    @JvmStatic
    @AssumptionDsl
    fun isEverySameAs(args: Iterable<Pair<Any?, Any?>>): Boolean {
        for ((value, other) in args) {
            if (isNotSameAs(value, other)) {
                return false
            }
        }
        return true
    }

    @JvmStatic
    @AssumptionDsl
    fun isEverySameAs(args: Sequence<Pair<Any?, Any?>>): Boolean {
        for ((value, other) in args) {
            if (isNotSameAs(value, other)) {
                return false
            }
        }
        return true
    }

    @JvmStatic
    @AssumptionDsl
    fun isSameAs(value: Any?, other: Any?): Boolean {
        if (value === other) {
            return true
        }
        return when (value) {
            null -> other == null
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
    @AssumptionDsl
    fun isNotSameAs(value: Any?, other: Any?): Boolean = !isSameAs(value, other)

    @JvmStatic
    @AssumptionDsl
    fun isDataComparableType(value: Any?): Boolean {
        return when(value) {
            null -> false
            is URI -> true
            is URL -> true
            is File -> true
            is Path -> true
            is Reader -> true
            is ByteArray -> true
            is InputStream -> true
            is InputStreamSupplier -> true
            is ReadableByteChannel -> true
            is ByteArrayOutputStream -> true
            else -> false
        }
    }

    @JvmStatic
    fun hashOf(vararg args: Any?, accumulator: (Int) -> Unit) {
        args.forEach {
            accumulator(hashOf(it))
        }
    }

    @JvmStatic
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
    fun hashOf(vararg args: Any?): Int {
        return hashOf(1.toAtomic(), *args)
    }

    @JvmStatic
    private fun hashOf(hash: AtomicInteger, vararg args: Any?): Int {
        hashOf(*args) {
            hash * 31 + it
        }
        return hash.get()
    }
}