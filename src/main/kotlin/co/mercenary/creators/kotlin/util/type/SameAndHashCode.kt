/*
 * Copyright (c) 2022, Mercenary Creators Company. All rights reserved.
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

@file:Suppress("NOTHING_TO_INLINE", "UNCHECKED_CAST")

package co.mercenary.creators.kotlin.util.type

import co.mercenary.creators.kotlin.util.*
import co.mercenary.creators.kotlin.util.io.*
import co.mercenary.creators.kotlin.util.io.BytesOutputStream
import java.io.*
import java.math.*
import java.net.*
import java.nio.ByteBuffer
import java.nio.channels.ReadableByteChannel
import java.nio.file.Path
import java.util.concurrent.atomic.*

@FrameworkDsl
@IgnoreForSerialize
object SameAndHashCode {

    @JvmStatic
    @FrameworkDsl
    fun isSameAs(value: Array<*>, other: Array<*>): Boolean {
        if (value === other) {
            return true
        }
        if (value.sizeOf() != other.sizeOf()) {
            return false
        }
        if (value.sizeOf() == 0) {
            return true
        }
        for (i in value.indices) {
            val v = value[i]
            val o = other[i]
            if (v === o) {
                continue
            }
            if (v == null) {
                return false
            }
            if (isSameAs(v, o)) {
                continue
            }
        }
        return true
    }

    @JvmStatic
    @FrameworkDsl
    fun isSameAs(value: List<*>, other: List<*>): Boolean {
        if (value === other) {
            return true
        }
        if (value.sizeOf() != other.sizeOf()) {
            return false
        }
        if (value.sizeOf() == 0) {
            return true
        }
        for (i in value.indices) {
            val v = value[i]
            val o = other[i]
            if (v === o) {
                continue
            }
            if (v == null) {
                return false
            }
            if (isSameAs(v, o)) {
                continue
            }
        }
        return true
    }

    @JvmStatic
    @FrameworkDsl
    fun isSameAs(value: Set<*>, other: Set<*>): Boolean {
        if (value === other) {
            return true
        }
        if (value.sizeOf() != other.sizeOf()) {
            return false
        }
        if (value.sizeOf() == 0) {
            return true
        }
        return value.containsAll(other)
    }

    @JvmStatic
    @FrameworkDsl
    fun isSameAs(value: Collection<*>, other: Collection<*>): Boolean {
        if (value === other) {
            return true
        }
        if (value.sizeOf() != other.sizeOf()) {
            return false
        }
        if (value.sizeOf() == 0) {
            return true
        }
        return value.containsAll(other)
    }

    @JvmStatic
    @FrameworkDsl
    fun isSameAs(value: Iterable<*>, other: Iterable<*>): Boolean {
        if (value === other) {
            return true
        }
        if (value.isExhausted() != other.isExhausted()) {
            return false
        }
        return when (value) {
            is List<*> -> if (other is List<*>) isSameAs(value, other) else false
            is Collection<*> -> if (other is Collection<*>) isSameAs(value, other) else false
            else -> isSameAs(value.toCollection(), other.toCollection())
        }
    }

    @JvmStatic
    @FrameworkDsl
    fun isSameAs(value: Map<*, *>, other: Map<*, *>): Boolean {
        if (value === other) {
            return true
        }
        if (value.sizeOf() != other.sizeOf()) {
            return false
        }
        if (value.sizeOf() == 0) {
            return true
        }
        try {
            for ((k, v) in value) {
                if (other isKeyNotDefined k) {
                    return false
                }
                val o = other[k]
                if (o === v) {
                    continue
                }
                if (v == null) {
                    return false
                }
                if (isSameAs(v, o)) {
                    continue
                }
            }
        } catch (cause: Throwable) {
            return Throwables.fatal(cause, false)
        }
        return true
    }

    @JvmStatic
    @FrameworkDsl
    fun isSameAs(value: Map.Entry<*, *>, other: Map.Entry<*, *>): Boolean {
        if (value === other) {
            return true
        }
        return isSameAs(value.key, other.key) && isSameAs(value.value, other.value)
    }

    @JvmStatic
    @FrameworkDsl
    fun isSameAs(value: Any?, other: Any?): Boolean {
        if (value === other) {
            return true
        }
        return when (value) {
            is String -> if (other is String) value == other else false
            is List<*> -> if (other is List<*>) isSameAs(value, other) else false
            is Array<*> -> if (other is Array<*>) isSameAs(value, other) else false
            is Map<*, *> -> if (other is Map<*, *>) isSameAs(value, other) else false
            is Set<*> -> if (other is Set<*>) isSameAs(value, other) else false
            is Collection<*> -> if (other is Collection<*>) isSameAs(value, other) else false
            is Iterable<*> -> if (other is Iterable<*>) isSameAs(value, other) else false
            is Map.Entry<*, *> -> if (other is Map.Entry<*, *>) isSameAs(value, other) else false
            is IntArray -> if (other is IntArray) value isSameArrayAs other else false
            is ByteArray -> if (other is ByteArray) value isSameArrayAs other else false
            is CharArray -> if (other is CharArray) value isSameArrayAs other else false
            is LongArray -> if (other is LongArray) value isSameArrayAs other else false
            is ShortArray -> if (other is ShortArray) value isSameArrayAs other else false
            is FloatArray -> if (other is FloatArray) value isSameArrayAs other else false
            is DoubleArray -> if (other is DoubleArray) value isSameArrayAs other else false
            is BooleanArray -> if (other is BooleanArray) value isSameArrayAs other else false
            is AtomicBoolean -> if (other is Boolean) value.toBoolean() == other else if (other is AtomicBoolean) value.toBoolean() == other.toBoolean() else false
            is Boolean -> if (other is Boolean) value == other else if (other is AtomicBoolean) value == other.toBoolean() else false
            is Int -> if (other is Int) value == other else if (other is AtomicInteger) value == other.intsOf() else if (other is BigInteger) value.bigsOf() == other else false
            is Byte -> if (other is Byte) value == other else false
            is Char -> if (other is Char) value == other else false
            is Short -> if (other is Short) value == other else false
            is Float -> if (other is Float) value == other else if (other is BigDecimal) value.toBigDecimal() == other else false
            is Double -> if (other is Double) value == other else false
            is Number -> when (other) {
                is Number -> when (value) {
                    is Long -> if (other is Long) value == other else if (other is AtomicLong) value == other.longOf() else if (other is BigInteger) value.toBigInteger() == other else false
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
                    is AtomicLong -> if (other is Long) value.longOf() == other else if (other is AtomicLong) value == other else if (other is BigInteger) value.toBigInteger() == other else false
                    is AtomicInteger -> if (other is Int) value.intsOf() == other else if (other is AtomicInteger) value.intsOf() == other.intsOf() else if (other is BigInteger) value.toBigInteger() == other else false
                    else -> false
                }
                else -> false
            }
            else -> value == other
        }
    }

    @JvmStatic
    @FrameworkDsl
    fun isNotSameAs(value: Any?, other: Any?): Boolean = isSameAs(value, other).isNotTrue()

    @JvmStatic
    @FrameworkDsl
    fun isContentCapable(value: Any?): Boolean = when (value) {
        null -> false
        is URI -> true
        is URL -> true
        is File -> true.toBoolean()
        is Path -> true.toBoolean()
        is Reader -> true.toBoolean()
        is ByteArray -> true.toBoolean()
        is CharArray -> true.toBoolean()
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
    @FrameworkDsl
    fun isContentSameAs(value: Any?, other: Any?): Boolean {
        if (value != null && other != null) {
            if (isContentCapable(value) && isContentCapable(other)) {
                return try {
                    contentOf(value) isSameArrayAs contentOf(other)
                } catch (cause: Throwable) {
                    Throwables.fatal(cause, false)
                }
            }
        }
        return false
    }

    @JvmStatic
    @FrameworkDsl
    fun isContentNotSameAs(value: Any?, other: Any?): Boolean = isContentSameAs(value, other).isNotTrue()

    @JvmSynthetic
    @FrameworkDsl
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
            is ByteArraySupplier -> value.getContentData()
            is ByteArrayOutputStream -> value.getContentData()
            else -> EMPTY_BYTE_ARRAY
        }
    }

    @JvmStatic
    @FrameworkDsl
    fun hashOf(value: Any?): Int {
        return when (value) {
            null -> HASH_NULL_VALUE
            is Path -> value.hashOf()
            is Array<*> -> value.hashOf()
            is IntArray -> value.hashOf()
            is ByteArray -> value.hashOf()
            is CharArray -> value.hashOf()
            is LongArray -> value.hashOf()
            is ShortArray -> value.hashOf()
            is FloatArray -> value.hashOf()
            is DoubleArray -> value.hashOf()
            is BooleanArray -> value.hashOf()
            is Map<*, *> -> value.hashOf()
            is Set<*> -> value.hashOf()
            is List<*> -> value.hashOf()
            is Iterable<*> -> value.hashOf()
            is Iterator<*> -> value.hashOf()
            is Map.Entry<*, *> -> value.hashOf()
            else -> value.hashCode()
        }
    }

    @JvmStatic
    @FrameworkDsl
    fun idenOf(value: Any?): Int {
        return when (value) {
            null -> HASH_NULL_VALUE
            else -> System.identityHashCode(value)
        }
    }
}