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

@file:kotlin.jvm.JvmName("MainKt")
@file:kotlin.jvm.JvmMultifileClass

package co.mercenary.creators.kotlin.util

import co.mercenary.creators.kotlin.util.type.Validated
import java.util.*
import java.util.concurrent.atomic.*

const val IS_NOT_FOUND = -1

const val EMPTY_STRING = ""

const val SPACE_STRING = " "

const val CREATORS_AUTHOR_INFO = "Dean S. Jones, Copyright (C) 2019, Mercenary Creators Company."

typealias Logging = co.mercenary.creators.kotlin.util.logging.Logging

typealias ILogging = co.mercenary.creators.kotlin.util.logging.ILogging

typealias LoggingFactory = co.mercenary.creators.kotlin.util.logging.LoggingFactory

typealias Randoms = co.mercenary.creators.kotlin.util.security.Randoms

typealias Digests = co.mercenary.creators.kotlin.util.security.Digests

typealias Encoders = co.mercenary.creators.kotlin.util.security.Encoders

typealias Throwables = co.mercenary.creators.kotlin.util.type.Throwables

typealias CipherAlgorithm = co.mercenary.creators.kotlin.util.security.CipherAlgorithm

open class MercenaryExceptiion(text: String?, root: Throwable?) : RuntimeException(text, root) {
    constructor(text: String) : this(text, null)
    constructor(root: Throwable) : this(root.message, root)

    companion object {
        private const val serialVersionUID = 2L
    }
}

open class MercenaryFatalExceptiion(text: String?, root: Throwable?) : MercenaryExceptiion(text, root) {
    constructor(text: String) : this(text, null)
    constructor(root: Throwable) : this(root.message, root)

    companion object {
        private const val serialVersionUID = 2L
    }
}

fun Class<*>.isKotlinClass(): Boolean {
    return declaredAnnotations.any { it.annotationClass.java.name == "kotlin.Metadata" }
}

fun uuid(): String = UUID.randomUUID().toString()

fun getCurrentThreadName(): String = Thread.currentThread().name

fun getProcessors(): Int = Runtime.getRuntime().availableProcessors()

fun toTrimOrNull(data: String?): String? = data?.trim().takeUnless { it.isNullOrEmpty() }

fun toTrimOrElse(data: String?, other: () -> String): String = toTrimOrNull(data) ?: other()

@JvmOverloads
fun toTrimOrElse(data: String?, other: String = EMPTY_STRING): String = toTrimOrNull(data) ?: other

inline fun toSafeString(block: () -> Any?): String = try {
    when (val data = block()) {
        null -> "null"
        else -> data.toString()
    }
}
catch (cause: Throwable) {
    Throwables.thrown(cause)
    "toSafeString(${cause.message})"
}

fun getCheckedString(data: String): String {
    val size = data.length
    for (posn in 0 until size) {
        if (data[posn] == Char.MIN_VALUE) {
            throw MercenaryFatalExceptiion("Null byte present in file/path name. There are no known legitimate use cases for such data, but several injection attacks may use it.")
        }
    }
    return data
}

fun CharSequence.toLowerTrim(): String = trim().toString().toLowerCase()

fun isValid(value: Any?): Boolean = when (value) {
    null -> false
    is Validated -> {
        try {
            value.isValid()
        }
        catch (cause: Throwable) {
            Throwables.thrown(cause)
            false
        }
    }
    is Boolean -> value
    is AtomicBoolean -> value.get()
    else -> true
}

inline fun isValid(block: () -> Any?): Boolean = try {
    isValid(block())
}
catch (cause: Throwable) {
    Throwables.thrown(cause)
    false
}

fun Long.toAtomic(): AtomicLong = AtomicLong(this)

fun AtomicLong.asLong(): Long = this.get()

fun Int.toAtomic(): AtomicInteger = AtomicInteger(this)

fun AtomicInteger.asInt(): Int = this.get()

fun Boolean.toAtomic(): AtomicBoolean = AtomicBoolean(this)

fun AtomicBoolean.asBoolean(): Boolean = this.get()

operator fun AtomicBoolean.not(): Boolean = this.get().not()

open class MercenarySequence<out T>(protected val iterator: Iterator<T>) : Sequence<T> {
    constructor() : this(listOf())
    constructor(source: Iterable<T>) : this(source.iterator())
    constructor(source: Sequence<T>) : this(source.iterator())

    override operator fun iterator() = iterator
}

fun <T> sequenceOf(): Sequence<T> = MercenarySequence()

fun <T> sequenceOf(vararg args: T): Sequence<T> = MercenarySequence(args.iterator())

fun sequenceOf(args: IntProgression): Sequence<Int> = MercenarySequence(args)

fun sequenceOf(args: LongProgression): Sequence<Long> = MercenarySequence(args)

fun sequenceOf(args: CharProgression): Sequence<Char> = MercenarySequence(args)

fun <T : Any> Iterable<T>.toSequence(): Sequence<T> = MercenarySequence(iterator())

fun <T : Any> sequenceOf(next: () -> T?): Sequence<T> = MercenarySequence(generateSequence(next))

fun <T : Any> sequenceOf(seed: T?, next: (T) -> T?): Sequence<T> = MercenarySequence(generateSequence(seed, next))

inline fun <T : Any> T?.orElse(block: () -> T): T = this ?: block()

inline fun <T> withLoggingContext(args: Pair<String, Any>, block: () -> T): T {
    return mu.withLoggingContext(args.first to args.second.toString(), block)
}

inline fun <T> withLoggingContext(args: Map<String, Any>, block: () -> T): T {
    val hash = LinkedHashMap<String, String>(args.size)
    for ((k, v) in args) {
        hash[k] = v.toString()
    }
    return mu.withLoggingContext(hash, block)
}

inline fun <T> withLoggingContext(vararg args: Pair<String, Any>, block: () -> T): T {
    val hash = LinkedHashMap<String, String>(args.size)
    for ((k, v) in args) {
        hash[k] = v.toString()
    }
    return mu.withLoggingContext(hash, block)
}