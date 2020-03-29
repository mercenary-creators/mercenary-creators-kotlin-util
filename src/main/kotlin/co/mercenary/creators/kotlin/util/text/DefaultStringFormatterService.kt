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

package co.mercenary.creators.kotlin.util.text

import co.mercenary.creators.kotlin.util.*
import java.time.LocalDateTime
import java.util.*
import java.util.concurrent.atomic.AtomicBoolean

class DefaultStringFormatterService : StringFormatterService(Int.MIN_VALUE) {

    private val safe = { data: Any? -> Formatters.toSafeString { data } }

    override fun toSafeString(data: Any): String {
        return when (data) {
            is CharArray -> String(data)
            is ByteArray -> Encoders.hex().encode(data)
            is Map<*, *> -> joinTo(data.toMap())
            is Array<*> -> joinTo(data.toList())
            is Iterable<*> -> joinTo(data.toList())
            is Sequence<*> -> joinTo(data.toList())
            is IntArray -> joinTo(data.toList())
            is LongArray -> joinTo(data.toList())
            is ShortArray -> joinTo(data.toList())
            is FloatArray -> joinTo(data.toList())
            is DoubleArray -> joinTo(data.toList())
            is BooleanArray -> joinTo(data.toList())
            is Date -> data.formatDate()
            is LocalDateTime -> data.formatDate()
            is Function0<*> -> Formatters.toSafeString(data)
            is Map.Entry<*, *> -> buildString {
                val (k, v) = data
                append(Escapers.toEscapedString(safe.invoke(k), quoted(k)))
                append(": ")
                append(Escapers.toEscapedString(safe.invoke(v), quoted(v)))
            }
            is HasMapNames -> safe.invoke(data.toMapNames())
            else -> data.toString()
        }
    }

    @AssumptionDsl
    override fun isValidClass(data: Any): Boolean {
        return when (data) {
            is String -> true
            is Number -> true
            is Boolean -> true
            is Array<*> -> true
            is Map<*, *> -> true
            is Iterable<*> -> true
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
            else -> false
        }
    }

    private fun joinTo(data: List<*>): String {
        return when(val size = data.size) {
            0 -> "[]"
            else -> {
                val buff = StringBuilder("[")
                for (i in 0 until size) {
                    if (i > 0) {
                        buff.append(", ")
                    }
                    buff.append(safe.invoke(data[i]))
                }
                buff.append("]").toString()
            }
        }
    }

    private fun joinTo(data: Map<*, *>): String {
        val list = data.entries.toList()
        return when(val size = list.size) {
            0 -> "{}"
            else -> {
                val buff = StringBuilder("{")
                for (i in 0 until size) {
                    if (i > 0) {
                        buff.append(", ")
                    }
                    buff.append(safe.invoke(list[i]))
                }
                buff.append("}").toString()
            }
        }
    }

    @AssumptionDsl
    private fun quoted(data: Any?): Boolean {
        return when (data) {
            null -> false
            is String -> true
            is Number -> false
            is Boolean -> false
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
            is AtomicBoolean -> false
            is Date -> true
            is LocalDateTime -> true
            is Function0<*> -> false
            is HasMapNames -> false
            is Map.Entry<*, *> -> false
            else -> true
        }
    }
}