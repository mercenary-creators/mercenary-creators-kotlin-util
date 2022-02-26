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

package co.mercenary.creators.kotlin.util.text

import co.mercenary.creators.kotlin.util.*
import co.mercenary.creators.kotlin.util.Formatters
import java.lang.reflect.Field
import java.time.LocalDateTime
import java.util.*
import java.util.concurrent.atomic.*
import kotlin.reflect.KClass

@IgnoreForSerialize
class DefaultStringFormatterService : StringFormatterService(Int.MIN_VALUE) {

    @FrameworkDsl
    private val deep = ThreadLocal.withInitial { 0L.toAtomic() }

    @FrameworkDsl
    private fun format(data: Any?): String = Formatters.toSafeString { data }

    @FrameworkDsl
    override fun toSafeString(data: Any): String {
        return when (data) {
            is String -> data
            is CharArray -> data.getContentText(false)
            is ByteArray -> Encoders.hex().encode(data)
            is Map<*, *> -> joined(data)
            is Array<*> -> joined(data.toIterator(), false)
            is Iterable<*> -> joined(data.toIterator(), false)
            is Sequence<*> -> joined(data.toIterator(), false)
            is Iterator<*> -> joined(data.toIterator(), false)
            is IntArray -> joined(data.toIterator(), true)
            is LongArray -> joined(data.toIterator(), true)
            is ShortArray -> joined(data.toIterator(), true)
            is FloatArray -> joined(data.toIterator(), false)
            is DoubleArray -> joined(data.toIterator(), false)
            is BooleanArray -> joined(data.toIterator(), true)
            is Date -> data.formatDate()
            is LocalDateTime -> data.formatDate()
            is Function0<*> -> Formatters.toSafeString(data)
            is Map.Entry<*, *> -> stringOf {
                entry(data)
            }
            is HasMapNames -> format(data.toMapNames())
            is Pair<*, *> -> format(toMapOf(data))
            is AtomicLong -> format(data.getValue())
            is AtomicBoolean -> format(data.getValue())
            is AtomicInteger -> format(data.getValue())
            is Class<*> -> toString(data.nameOf())
            is KClass<*> -> toString(data.nameOf())
            else -> {
                if (data.isDataClass()) {
                    format(toMapNames(data))
                }
                data.toString()
            }
        }
    }

    @FrameworkDsl
    override fun isValidClass(data: Any): Boolean {
        return when (data) {
            is String -> true
            is Number -> true
            is Boolean -> true
            is Class<*> -> true
            is Array<*> -> true
            is KClass<*> -> true
            is Map<*, *> -> true
            is Iterable<*> -> true
            is Iterator<*> -> true
            is Sequence<*> -> true
            is IntArray -> true
            is ByteArray -> true
            is CharArray -> true
            is LongArray -> true
            is ShortArray -> true
            is FloatArray -> true
            is DoubleArray -> true
            is BooleanArray -> true
            is Date -> true
            is LocalDateTime -> true
            is AtomicBoolean -> true
            is Function0<*> -> true
            is Map.Entry<*, *> -> true
            is HasMapNames -> true
            is CharSequence -> true
            else -> data.isDataClass()
        }
    }

    @FrameworkDsl
    private fun toMapNames(data: Any): Map<String, Class<*>> {
        if (data.isDataClass().isNotTrue()) {
            fail(data.nameOf())
        }
        return data.javaClass.declaredFields.map { it.name to data.javaClass }.mapTo()
    }

    @FrameworkDsl
    private fun toFieldValue(data: Any, look: Field): Any? {
        if (data.isDataClass().isNotTrue()) {
            fail(data.nameOf())
        }
        return look.type.kotlin.objectInstance
    }

    @FrameworkDsl
    private fun isPrimitive(data: Any?): Boolean {
        return when (data) {
            null -> true
            is Int -> true
            is Byte -> true
            is Char -> true
            is Long -> true
            is Short -> true
            is Boolean -> true
            else -> false
        }
    }

    @FrameworkDsl
    private fun isString(data: Any?): Boolean {
        return when (data) {
            is String -> true
            is CharSequence -> true
            else -> false
        }
    }

    @FrameworkDsl
    private fun toString(data: Any?): String {
        return when (data) {
            is CharSequence -> Escapers.toEscapedString(data.copyOf(), true)
            else -> format(data)
        }
    }

    @FrameworkDsl
    private fun escape(data: Any?): String {
        val flag = quoted(data)
        deep.toValue().increment()
        val next = format(data)
        val look = flag.isNotTrue().and(deep.toValue() > 0).isNotTrue()
        deep.toValue().decrement()
        return if (look.isTrue()) Escapers.toEscapedString(next, flag.isTrue()) else next
    }

    @FrameworkDsl
    private fun joined(data: Iterator<*>, prim: Boolean): String {
        if (data.isExhausted()) {
            return "[]"
        }
        return stringOf("[") {
            data.forEachIndexed { index, value ->
                if (index > 0) {
                    add(", ")
                }
                if (prim.isTrue()) add(value) else if (isPrimitive(value)) add(value) else if (isString(value)) add(toString(value)) else add(format(value))
            }
            add("]")
        }
    }

    @FrameworkDsl
    private fun StringBuilder.entry(data: Map.Entry<*, *>): StringBuilder {
        return add(escape(data.key), ": ", escape(data.value))
    }

    @FrameworkDsl
    private fun joined(data: Map<*, *>): String {
        if (data.isExhausted()) {
            return "{}"
        }
        return stringOf("{") {
            data.forEachIndexed { index, entry ->
                if (index > 0) {
                    add(", ")
                }
                entry(entry)
            }
            add("}")
        }
    }

    @FrameworkDsl
    private fun quoted(data: Any?): Boolean {
        return when (data) {
            null -> false
            is String -> true
            is Number -> true
            is Boolean -> true
            is Array<*> -> false
            is Map<*, *> -> false
            is Iterable<*> -> false
            is Sequence<*> -> false
            is IntArray -> false
            is ByteArray -> true
            is CharArray -> true
            is LongArray -> false
            is ShortArray -> false
            is FloatArray -> false
            is DoubleArray -> false
            is BooleanArray -> false
            is AtomicBoolean -> true
            is Date -> true
            is LocalDateTime -> true
            is Function0<*> -> false
            is HasMapNames -> false
            is Map.Entry<*, *> -> false
            else -> true
        }
    }
}