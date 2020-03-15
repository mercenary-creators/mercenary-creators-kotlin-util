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
import java.io.File
import java.math.*
import java.nio.file.Path
import java.util.concurrent.atomic.*

object SameAndHashCode {

    @JvmStatic
    fun isEverySameAs(vararg args: Pair<Any?, Any?>): Boolean {
        for ((value, other) in args) {
            if (isNotSameAs(value, other)) {
                return false
            }
        }
        return true
    }

    @JvmStatic
    fun isEverySameAs(args: Iterable<Pair<Any?, Any?>>): Boolean {
        for ((value, other) in args) {
            if (isNotSameAs(value, other)) {
                return false
            }
        }
        return true
    }

    @JvmStatic
    fun isEverySameAs(args: Sequence<Pair<Any?, Any?>>): Boolean {
        for ((value, other) in args) {
            if (isNotSameAs(value, other)) {
                return false
            }
        }
        return true
    }

    @JvmStatic
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
            is AtomicBoolean -> if (other is Boolean) value.get() == other else if (other is AtomicBoolean) value.get() == other.get() else false
            is Boolean -> if (other is Boolean) value == other else if (other is AtomicBoolean) value == other.get() else if (other is Validated) value == other.isValid() else false
            is Number -> when (other) {
                is Number -> when (value) {
                    is BigDecimal -> if (other is BigDecimal) value == other else if (other is BigInteger) value == other.toBigDecimal() else value == other.toDouble().toBigDecimal()
                    is BigInteger -> if (other is BigInteger) value == other else if (other is BigDecimal) value == other.toBigInteger() else value == other.toDouble().toBigDecimal().toBigInteger()
                    else -> value.toDouble().toBits() == other.toDouble().toBits()
                }
                else -> false
            }
            is File -> if (other is File) value isSameFileAndData other else if (other is Path) value isSameFileAndData other else false
            is Path -> if (other is File) value isSameFileAndData other else if (other is Path) value isSameFileAndData other else false
            else -> value == other
        }
    }

    @JvmStatic
    fun isNotSameAs(value: Any?, other: Any?): Boolean = !isSameAs(value, other)

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