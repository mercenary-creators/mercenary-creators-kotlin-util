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

@file:kotlin.jvm.JvmName("MainKt")

package co.mercenary.creators.kotlin.util

import co.mercenary.creators.kotlin.util.type.Validated
import java.util.*
import java.util.concurrent.atomic.*

const val IS_NOT_FOUND = -1

const val EMPTY_STRING = ""

const val SPACE_STRING = " "

const val NULLS_STRING = "null"

const val DUNNO_STRING = "unknown"

const val KOTLIN_METAS = "kotlin.Metadata"

const val CREATORS_AUTHOR_INFO = "Dean S. Jones, Copyright (C) 2020, Mercenary Creators Company."

typealias Inflaters = co.mercenary.creators.kotlin.util.io.Inflaters

typealias Logging = co.mercenary.creators.kotlin.util.logging.Logging

typealias ILogging = co.mercenary.creators.kotlin.util.logging.ILogging

typealias LoggingFactory = co.mercenary.creators.kotlin.util.logging.LoggingFactory

typealias LoggingMarker = co.mercenary.creators.kotlin.util.logging.LoggingMarker

typealias Randoms = co.mercenary.creators.kotlin.util.security.Randoms

typealias Ciphers = co.mercenary.creators.kotlin.util.security.Ciphers

typealias Digests = co.mercenary.creators.kotlin.util.security.Digests

typealias Encoders = co.mercenary.creators.kotlin.util.security.Encoders

typealias ServiceLoading = co.mercenary.creators.kotlin.util.security.ServiceLoading

typealias Throwables = co.mercenary.creators.kotlin.util.type.Throwables

typealias SameAndHashCode = co.mercenary.creators.kotlin.util.type.SameAndHashCode

typealias CipherAlgorithm = co.mercenary.creators.kotlin.util.security.CipherAlgorithm

typealias AtomicHashMap<K, V> = java.util.concurrent.ConcurrentHashMap<K, V>

typealias AtomicDictionary<V> = java.util.concurrent.ConcurrentHashMap<String, V>

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

open class MercenaryAssertExceptiion(text: String?, root: Throwable?) : MercenaryFatalExceptiion(text, root) {
    constructor(text: String) : this(text, null)
    constructor(root: Throwable) : this(root.message, root)

    companion object {
        private const val serialVersionUID = 2L
    }
}

interface HasMapNames {
    fun toMapNames(): Map<String, Any?>
}

fun Class<*>.isKotlinClass(): Boolean {
    return declaredAnnotations.any { it.annotationClass.java.name == KOTLIN_METAS }
}

fun getCurrentThreadName(): String = Thread.currentThread().name

fun getProcessors(): Int = Runtime.getRuntime().availableProcessors()

fun toTrimOrNull(data: String?): String? = data?.trim().takeUnless { it.isNullOrEmpty() }

fun toTrimOrElse(data: String?, other: () -> String): String = toTrimOrNull(data) ?: other()

@JvmOverloads
fun toTrimOrElse(data: String?, other: String = EMPTY_STRING): String = toTrimOrNull(data) ?: other

fun CharSequence.toChecked(): String {
    return if (this.any { it == Char.MIN_VALUE })
        throw MercenaryFatalExceptiion("null byte present. there are no known legitimate use cases for such data, but several injection attacks may use it.")
    else toString()
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

operator fun AtomicLong.div(value: Int): AtomicLong {
    if (value == 0) throw MercenaryFatalExceptiion(MATH_ZERO_DIVISOR_ERROR)
    updateAndGet { it / value }
    return this
}

operator fun AtomicLong.times(value: Int): AtomicLong {
    updateAndGet { it * value }
    return this
}

operator fun AtomicLong.plus(value: Int): AtomicLong {
    updateAndGet { it + value }
    return this
}

operator fun AtomicLong.minus(value: Int): AtomicLong {
    updateAndGet { it - value }
    return this
}

operator fun AtomicLong.div(value: Long): AtomicLong {
    if (value == 0L) throw MercenaryFatalExceptiion(MATH_ZERO_DIVISOR_ERROR)
    updateAndGet { it / value }
    return this
}

operator fun AtomicLong.times(value: Long): AtomicLong {
    updateAndGet { it * value }
    return this
}

operator fun AtomicLong.plus(value: Long): AtomicLong {
    updateAndGet { it + value }
    return this
}

operator fun AtomicLong.minus(value: Long): AtomicLong {
    updateAndGet { it - value }
    return this
}

fun AtomicLong.increment(): AtomicLong {
    getAndIncrement()
    return this
}

fun AtomicLong.decrement(): AtomicLong {
    getAndDecrement()
    return this
}

infix fun AtomicLong.maxOf(value: Int): AtomicLong {
    if (value > get()) {
        set(value.toLong())
    }
    return this
}

infix fun AtomicLong.maxOf(value: AtomicInteger): AtomicLong {
    if (value.get() > get()) {
        set(value.get().toLong())
    }
    return this
}

infix fun AtomicLong.minOf(value: Long): AtomicLong {
    if (value < get()) {
        set(value)
    }
    return this
}

infix fun AtomicLong.minOf(value: AtomicLong): AtomicLong {
    if (value.get() < get()) {
        set(value.get())
    }
    return this
}
infix fun AtomicLong.maxOf(value: Long): AtomicLong {
    if (value > get()) {
        set(value)
    }
    return this
}

infix fun AtomicLong.maxOf(value: AtomicLong): AtomicLong {
    if (value.get() > get()) {
        set(value.get())
    }
    return this
}

infix fun AtomicLong.minOf(value: Int): AtomicLong {
    if (value < get()) {
        set(value.toLong())
    }
    return this
}

infix fun AtomicLong.minOf(value: AtomicInteger): AtomicLong {
    if (value.get() < get()) {
        set(value.get().toLong())
    }
    return this
}
fun Int.toAtomic(): AtomicInteger = AtomicInteger(this)

operator fun AtomicInteger.div(value: Int): AtomicInteger {
    if (value == 0) throw MercenaryFatalExceptiion(MATH_ZERO_DIVISOR_ERROR)
    updateAndGet { it / value }
    return this
}

operator fun AtomicInteger.times(value: Int): AtomicInteger {
    updateAndGet { it * value }
    return this
}

operator fun AtomicInteger.plus(value: Int): AtomicInteger {
    updateAndGet { it + value }
    return this
}

operator fun AtomicInteger.minus(value: Int): AtomicInteger {
    updateAndGet { it - value }
    return this
}

fun AtomicInteger.increment(): AtomicInteger {
    getAndIncrement()
    return this
}

fun AtomicInteger.decrement(): AtomicInteger {
    getAndDecrement()
    return this
}

infix fun AtomicInteger.maxOf(value: Int): AtomicInteger {
    if (value > get()) {
        set(value)
    }
    return this
}

infix fun AtomicInteger.maxOf(value: AtomicInteger): AtomicInteger {
    if (value.get() > get()) {
        set(value.get())
    }
    return this
}

infix fun AtomicInteger.minOf(value: Int): AtomicInteger {
    if (value < get()) {
        set(value)
    }
    return this
}

infix fun AtomicInteger.minOf(value: AtomicInteger): AtomicInteger {
    if (value.get() < get()) {
        set(value.get())
    }
    return this
}

fun Boolean.toAtomic() = AtomicBoolean(this)

fun AtomicBoolean.toBoolean(): Boolean = get()

operator fun AtomicBoolean.not(): Boolean = get().not()

infix fun Boolean.or(value: AtomicBoolean): Boolean = or(value.get())

infix fun AtomicBoolean.or(value: Boolean): Boolean = get().or(value)

infix fun AtomicBoolean.or(value: AtomicBoolean): Boolean = get().or(value.get())

infix fun Boolean.and(value: AtomicBoolean): Boolean = and(value.get())

infix fun AtomicBoolean.and(value: Boolean): Boolean = get().and(value)

infix fun AtomicBoolean.and(value: AtomicBoolean): Boolean = get().and(value.get())

infix fun Boolean.xor(value: AtomicBoolean): Boolean = xor(value.get())

infix fun AtomicBoolean.xor(value: Boolean): Boolean = get().xor(value)

infix fun AtomicBoolean.xor(value: AtomicBoolean): Boolean = get().xor(value.get())

infix fun <T : Any?> T.isSameAs(value: T) = SameAndHashCode.isSameAs(this, value)

infix fun <T : Any?> T.isNotSameAs(value: T) = SameAndHashCode.isNotSameAs(this, value)

fun isEverySameAs(vararg args: Pair<Any?, Any?>) = SameAndHashCode.isEverySameAs(*args)

fun <T : Any?> T.hashOf() = SameAndHashCode.hashOf(this)

fun <T : Any?> T.hashOf(vararg args: Any?) = SameAndHashCode.hashOf(hashOf().toAtomic(), *args)

fun <T : Any?> T.hashOfSystem() = SameAndHashCode.hashOfSystem(this)

open class MercenarySequence<out T>(protected val iterator: Iterator<T>) : Sequence<T> {
    constructor() : this(emptySequence())
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

fun Sequence<String>.uniqueTrimmedOf(): List<String> = asIterable().uniqueTrimmedOf()

fun Iterable<String>.uniqueTrimmedOf(): List<String> = mapNotNull { toTrimOrNull(it) }.distinct()

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