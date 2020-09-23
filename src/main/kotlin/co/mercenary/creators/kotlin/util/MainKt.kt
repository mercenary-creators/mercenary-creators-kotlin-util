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
@file:Suppress("NOTHING_TO_INLINE")

package co.mercenary.creators.kotlin.util

import co.mercenary.creators.kotlin.util.security.SecureByteArray
import java.math.BigInteger
import java.nio.charset.Charset
import java.util.*
import java.util.concurrent.atomic.*

@CreatorsDsl
const val IS_NOT_FOUND = -1

@CreatorsDsl
const val EMPTY_STRING = ""

@CreatorsDsl
const val SPACE_STRING = " "

@CreatorsDsl
const val NULLS_STRING = "null"

@CreatorsDsl
const val DUNNO_STRING = "unknown"

@CreatorsDsl
const val KOTLIN_METAS = "kotlin.Metadata"

@CreatorsDsl
const val CREATORS_AUTHOR_INFO = "Dean S. Jones, Copyright (C) 2020, Mercenary Creators Company."

typealias Inflaters = co.mercenary.creators.kotlin.util.io.Inflaters

typealias Logging = co.mercenary.creators.kotlin.util.logging.Logging

typealias ILogging = co.mercenary.creators.kotlin.util.logging.ILogging

typealias LoggingFactory = co.mercenary.creators.kotlin.util.logging.LoggingFactory

typealias LoggingMarker = co.mercenary.creators.kotlin.util.logging.LoggingMarker

typealias LoggingLevel = co.mercenary.creators.kotlin.util.logging.LoggingLevel

typealias Randoms = co.mercenary.creators.kotlin.util.security.Randoms

typealias Ciphers = co.mercenary.creators.kotlin.util.security.Ciphers

typealias Digests = co.mercenary.creators.kotlin.util.security.Digests

typealias Encoders = co.mercenary.creators.kotlin.util.security.Encoders

typealias SecureString = co.mercenary.creators.kotlin.util.security.SecureString

typealias ServiceLoading = co.mercenary.creators.kotlin.util.security.ServiceLoading

typealias Throwables = co.mercenary.creators.kotlin.util.type.Throwables

typealias SameAndHashCode = co.mercenary.creators.kotlin.util.type.SameAndHashCode

typealias CipherAlgorithm = co.mercenary.creators.kotlin.util.security.CipherAlgorithm

typealias AtomicHashMap<K, V> = java.util.concurrent.ConcurrentHashMap<K, V>

typealias LRUCacheMap<K, V> = co.mercenary.creators.kotlin.util.collection.LRUCacheMap<K, V>

open class MercenaryExceptiion @CreatorsDsl constructor(text: String?, root: Throwable?) : RuntimeException(text, root) {

    @CreatorsDsl
    constructor() : this(null, null)

    @CreatorsDsl
    constructor(text: String) : this(text, null)

    @CreatorsDsl
    constructor(root: Throwable) : this(root.message, root)

    companion object {

        private const val serialVersionUID = 2L
    }
}

open class MercenaryFatalExceptiion @CreatorsDsl constructor(text: String?, root: Throwable?) : RuntimeException(text, root) {

    @CreatorsDsl
    constructor(text: String) : this(text, null)

    @CreatorsDsl
    constructor(root: Throwable) : this(root.message, root)

    companion object {

        private const val serialVersionUID = 2L
    }
}

open class MercenaryAssertExceptiion @CreatorsDsl constructor(text: String?, root: Throwable?) : AssertionError(text, root) {

    @CreatorsDsl
    constructor(text: String) : this(text, null)

    @CreatorsDsl
    constructor(root: Throwable) : this(root.message, root)

    companion object {

        private const val serialVersionUID = 2L
    }
}

@CreatorsDsl
fun java.security.MessageDigest.proxyOf() = Digests.proxyOf(this)

@CreatorsDsl
fun Class<*>.isKotlinClass(): Boolean {
    return declaredAnnotations.any { it.annotationClass.java.name == KOTLIN_METAS }
}

@CreatorsDsl
fun <K, V> atomicMapOf(): AtomicHashMap<K, V> {
    return AtomicHashMap()
}

@CreatorsDsl
fun <K, V> atomicMapOf(vararg args: Pair<K, V>): AtomicHashMap<K, V> {
    return if (args.isNotEmpty()) AtomicHashMap(args.toMap()) else AtomicHashMap()
}

@CreatorsDsl
fun <K, V> atomicMapOf(args: Map<K, V>): AtomicHashMap<K, V> {
    return if (args.isNotEmpty()) AtomicHashMap(args.toMap()) else AtomicHashMap()
}

@CreatorsDsl
fun <K, V> Map<K, V>.toAtomic(): AtomicHashMap<K, V> {
    return if (this is AtomicHashMap) this else AtomicHashMap(this)
}

@CreatorsDsl
fun <E> List<E>.whenNotEmpty(block: (List<E>) -> Unit) {
    if (isNotEmpty()) block(this)
}

@CreatorsDsl
fun getCurrentThreadName(): String = Thread.currentThread().name

@CreatorsDsl
inline fun getProcessors(): Int = Runtime.getRuntime().availableProcessors()

@CreatorsDsl
fun toTrimOrNull(data: String?): String? = data?.trim().takeUnless { it.isNullOrEmpty() }

@CreatorsDsl
fun toTrimOrElse(data: String?, other: String = EMPTY_STRING): String = toTrimOrNull(data) ?: other

@CreatorsDsl
inline fun toTrimOrElse(data: String?, other: () -> String): String = toTrimOrNull(data) ?: other.invoke()

@CreatorsDsl
inline fun String?.toTrimOr(other: String = EMPTY_STRING): String = toTrimOrNull(this) ?: other

@CreatorsDsl
inline fun String?.toTrimOr(other: () -> String): String = toTrimOrNull(this) ?: other.invoke()

@CreatorsDsl
inline fun String.head(many: Int = 1): String = if (many >= 0) drop(many) else this

@CreatorsDsl
inline fun String.tail(many: Int = 1): String = if (many >= 0) dropLast(many) else this

@CreatorsDsl
fun String.center(many: Int, pads: Char = ' ', trim: Boolean = true): String {
    return if (many <= 0) this else (if (trim) trim() else this).let { it.padStart((it.length + many) / 2, pads).padEnd(many, pads) }
}

@CreatorsDsl
fun CharSequence.toChecked(): String {
    return if (any { it == Char.MIN_VALUE })
        throw MercenaryFatalExceptiion("null byte present. there are no known legitimate use cases for such data, but several injection attacks may use it.")
    else toString()
}

@CreatorsDsl
fun CharArray.toSecureString(copy: Boolean = true): SecureString = SecureString(toCharArray(copy))

@CreatorsDsl
fun CharSequence.toSecureString(): SecureString = SecureString(toString())

@CreatorsDsl
fun ByteArray.toSecureByteArray(copy: Boolean = true): SecureByteArray = SecureByteArray(toByteArray(copy))

@CreatorsDsl
fun ByteArray.toCharArray(charset: Charset = Charsets.UTF_8, copy: Boolean = true): CharArray = toByteArray(copy).toString(charset).toCharArray()

@CreatorsDsl
fun CharSequence.toSecureByteArray(): SecureByteArray = SecureByteArray(toString())

@CreatorsDsl
fun CharSequence.toLowerTrim(): String = trim().toString().toLowerCase()

@CreatorsDsl
fun CharSequence.toUpperTrim(): String = trim().toString().toUpperCase()

@CreatorsDsl
fun CharSequence.toLowerTrimEnglish(): String = trim().toLowerCaseEnglish()

@CreatorsDsl
fun CharSequence.toUpperTrimEnglish(): String = trim().toUpperCaseEnglish()

@CreatorsDsl
fun CharSequence.toLowerCaseEnglish(): String = toString().toLowerCase(Locale.ENGLISH)

@CreatorsDsl
fun CharSequence.toUpperCaseEnglish(): String = toString().toUpperCase(Locale.ENGLISH)

@CreatorsDsl
fun <T, R : Any> List<T>.whenNotNull(block: (T) -> R?): List<R> {
    return when (isEmpty()) {
        true -> listOf()
        else -> mapNotNull(block).toList()
    }
}

@CreatorsDsl
fun <T : Any> ThreadLocal<T>.toValue(): T = get()

@CreatorsDsl
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
    is Boolean -> value.isTrue()
    is AtomicBoolean -> value.isTrue()
    else -> true
}

@CreatorsDsl
inline fun isValid(block: () -> Any?): Boolean = try {
    isValid(block.invoke())
}
catch (cause: Throwable) {
    Throwables.thrown(cause)
    false
}

@CreatorsDsl
fun Long.toAtomic(): AtomicLong = AtomicLong(this)

@CreatorsDsl
fun AtomicLong.toAtomic(): AtomicLong = toLong().toAtomic()

@CreatorsDsl
fun AtomicLong.copyOf(): AtomicLong = toAtomic()

@CreatorsDsl
operator fun AtomicLong.div(value: Int): AtomicLong {
    value.toValidDivisor()
    updateAndGet { it / value }
    return this
}

@CreatorsDsl
operator fun AtomicLong.times(value: Int): AtomicLong {
    updateAndGet { it * value }
    return this
}

@CreatorsDsl
operator fun AtomicLong.plus(value: Int): AtomicLong {
    updateAndGet { it + value }
    return this
}

@CreatorsDsl
operator fun AtomicLong.minus(value: Int): AtomicLong {
    updateAndGet { it - value }
    return this
}

@CreatorsDsl
operator fun AtomicLong.div(value: Long): AtomicLong {
    value.toValidDivisor()
    updateAndGet { it / value }
    return this
}

@CreatorsDsl
operator fun AtomicLong.times(value: Long): AtomicLong {
    updateAndGet { it * value }
    return this
}

@CreatorsDsl
operator fun AtomicLong.plus(value: Long): AtomicLong {
    updateAndGet { it + value }
    return this
}

@CreatorsDsl
operator fun AtomicLong.minus(value: Long): AtomicLong {
    updateAndGet { it - value }
    return this
}

@CreatorsDsl
fun AtomicLong.increment(): AtomicLong {
    getAndIncrement()
    return this
}

@CreatorsDsl
operator fun AtomicLong.inc(): AtomicLong {
    return increment()
}

@CreatorsDsl
fun AtomicLong.decrement(): AtomicLong {
    getAndDecrement()
    return this
}

@CreatorsDsl
operator fun AtomicLong.dec(): AtomicLong {
    return decrement()
}

@CreatorsDsl
infix fun AtomicLong.maxOf(value: Int): AtomicLong {
    if (value > get()) {
        set(value.toLong())
    }
    return this
}

@CreatorsDsl
infix fun AtomicLong.maxOf(value: AtomicInteger): AtomicLong {
    if (value.get() > get()) {
        set(value.get().toLong())
    }
    return this
}

@CreatorsDsl
infix fun AtomicLong.minOf(value: Long): AtomicLong {
    if (value < get()) {
        set(value)
    }
    return this
}

@CreatorsDsl
infix fun AtomicLong.minOf(value: AtomicLong): AtomicLong {
    if (value.get() < get()) {
        set(value.get())
    }
    return this
}

@CreatorsDsl
infix fun AtomicLong.maxOf(value: Long): AtomicLong {
    if (value > get()) {
        set(value)
    }
    return this
}

@CreatorsDsl
infix fun AtomicLong.maxOf(value: AtomicLong): AtomicLong {
    if (value.get() > get()) {
        set(value.get())
    }
    return this
}

@CreatorsDsl
infix fun AtomicLong.minOf(value: Int): AtomicLong {
    if (value < get()) {
        set(value.toLong())
    }
    return this
}

@CreatorsDsl
infix fun AtomicLong.minOf(value: AtomicInteger): AtomicLong {
    if (value.get() < get()) {
        set(value.get().toLong())
    }
    return this
}

@CreatorsDsl
operator fun AtomicLong.compareTo(value: Int): Int = get().compareTo(value)

@CreatorsDsl
operator fun AtomicLong.compareTo(value: Long): Int = get().compareTo(value)

@CreatorsDsl
operator fun AtomicLong.compareTo(value: AtomicLong): Int = get().compareTo(value.toLong())

@CreatorsDsl
operator fun AtomicLong.compareTo(value: AtomicInteger): Int = get().compareTo(value.toLong())

@CreatorsDsl
fun AtomicLong.toBigInteger(): BigInteger = toLong().toBigInteger()

@CreatorsDsl
fun AtomicLong.isNegative(): Boolean {
    return get() < 0
}

@CreatorsDsl
fun AtomicLong.isEven(): Boolean {
    return get().isEven()
}

@CreatorsDsl
fun AtomicLong.isNotEven(): Boolean {
    return get().isNotEven()
}

@CreatorsDsl
fun Int.toAtomic(): AtomicInteger = AtomicInteger(this)

@CreatorsDsl
fun AtomicInteger.toAtomic(): AtomicInteger = toInt().toAtomic()

@CreatorsDsl
fun AtomicInteger.copyOf(): AtomicInteger = toAtomic()

@CreatorsDsl
operator fun AtomicInteger.div(value: Int): AtomicInteger {
    value.toValidDivisor()
    updateAndGet { it / value }
    return this
}

@CreatorsDsl
operator fun AtomicInteger.times(value: Int): AtomicInteger {
    updateAndGet { it * value }
    return this
}

@CreatorsDsl
operator fun AtomicInteger.plus(value: Int): AtomicInteger {
    updateAndGet { it + value }
    return this
}

@CreatorsDsl
operator fun AtomicInteger.minus(value: Int): AtomicInteger {
    updateAndGet { it - value }
    return this
}

@CreatorsDsl
fun AtomicInteger.increment(): AtomicInteger {
    getAndIncrement()
    return this
}

@CreatorsDsl
operator fun AtomicInteger.inc(): AtomicInteger {
    return increment()
}

@CreatorsDsl
fun AtomicInteger.decrement(): AtomicInteger {
    getAndDecrement()
    return this
}

@CreatorsDsl
operator fun AtomicInteger.dec(): AtomicInteger {
    return decrement()
}

@CreatorsDsl
infix fun AtomicInteger.maxOf(value: Int): AtomicInteger {
    if (value > get()) {
        set(value)
    }
    return this
}

@CreatorsDsl
infix fun AtomicInteger.maxOf(value: AtomicInteger): AtomicInteger {
    if (value.get() > get()) {
        set(value.get())
    }
    return this
}

@CreatorsDsl
infix fun AtomicInteger.minOf(value: Int): AtomicInteger {
    if (value < get()) {
        set(value)
    }
    return this
}

@CreatorsDsl
infix fun AtomicInteger.minOf(value: AtomicInteger): AtomicInteger {
    if (value.get() < get()) {
        set(value.get())
    }
    return this
}

@CreatorsDsl
fun AtomicInteger.toBigInteger(): BigInteger = toInt().toBigInteger()

@CreatorsDsl
fun AtomicInteger.isNegative(): Boolean {
    return get().isNegative()
}

@CreatorsDsl
fun AtomicInteger.isEven(): Boolean {
    return get().isEven()
}

@CreatorsDsl
fun AtomicInteger.isNotEven(): Boolean {
    return get().isNotEven()
}

@CreatorsDsl
operator fun AtomicInteger.compareTo(value: Int): Int = get().compareTo(value)

@CreatorsDsl
operator fun AtomicInteger.compareTo(value: AtomicInteger): Int = get().compareTo(value.get())

@CreatorsDsl
inline fun Boolean.toAtomic() = AtomicBoolean(this)

@CreatorsDsl
inline fun Boolean.isTrue(): Boolean = this

@CreatorsDsl
inline fun Boolean.toBoolean(): Boolean = this

@CreatorsDsl
inline fun Boolean.isNotTrue(): Boolean = toBoolean().not()

@CreatorsDsl
fun AtomicBoolean.copyOf(): AtomicBoolean = toAtomic()

@CreatorsDsl
fun AtomicBoolean.toAtomic(): AtomicBoolean = toBoolean().toAtomic()

@CreatorsDsl
inline fun AtomicBoolean.toBoolean(): Boolean = get()

@CreatorsDsl
inline fun AtomicBoolean.isTrue(): Boolean = toBoolean()

@CreatorsDsl
inline fun AtomicBoolean.isNotTrue(): Boolean = isTrue().not()

@CreatorsDsl
fun AtomicBoolean.isTrueToFalse(): Boolean = isUpdateTo(true.toBoolean(), false.toBoolean())

@CreatorsDsl
fun AtomicBoolean.isFalseToTrue(): Boolean = isUpdateTo(false.toBoolean(), true.toBoolean())

@CreatorsDsl
fun AtomicBoolean.isUpdateTo(expect: Boolean, update: Boolean): Boolean = compareAndSet(expect, update)

@CreatorsDsl
fun AtomicBoolean.isUpdateTo(expect: Boolean, update: AtomicBoolean): Boolean = isUpdateTo(expect.toBoolean(), update.toBoolean())

@CreatorsDsl
fun AtomicBoolean.isUpdateTo(expect: AtomicBoolean, update: Boolean): Boolean = isUpdateTo(expect.toBoolean(), update.toBoolean())

@CreatorsDsl
fun AtomicBoolean.isUpdateTo(expect: AtomicBoolean, update: AtomicBoolean): Boolean = isUpdateTo(expect.toBoolean(), update.toBoolean())

@CreatorsDsl
fun AtomicBoolean.toFlip(): AtomicBoolean {
    val last = isTrue()
    val next = isNotTrue()
    isUpdateTo(last, next)
    return this
}

@CreatorsDsl
fun AtomicBoolean.toTrue(): AtomicBoolean {
    isFalseToTrue()
    return this
}

@CreatorsDsl
fun AtomicBoolean.toNotTrue(): AtomicBoolean {
    isTrueToFalse()
    return this
}

@CreatorsDsl
operator fun AtomicBoolean.not(): Boolean = toBoolean().not()

@CreatorsDsl
infix fun Boolean.or(value: AtomicBoolean): Boolean = or(value.toBoolean())

@CreatorsDsl
infix fun AtomicBoolean.or(value: Boolean): Boolean = toBoolean().or(value)

@CreatorsDsl
infix fun AtomicBoolean.or(value: AtomicBoolean): Boolean = toBoolean().or(value.toBoolean())

@CreatorsDsl
infix fun Boolean.and(value: AtomicBoolean): Boolean = and(value.toBoolean())

@CreatorsDsl
infix fun AtomicBoolean.and(value: Boolean): Boolean = toBoolean().and(value)

@CreatorsDsl
infix fun AtomicBoolean.and(value: AtomicBoolean): Boolean = toBoolean().and(value.toBoolean())

@CreatorsDsl
infix fun Boolean.xor(value: AtomicBoolean): Boolean = xor(value.toBoolean())

@CreatorsDsl
infix fun AtomicBoolean.xor(value: Boolean): Boolean = toBoolean().xor(value)

@CreatorsDsl
infix fun AtomicBoolean.xor(value: AtomicBoolean): Boolean = toBoolean().xor(value.toBoolean())

@CreatorsDsl
infix fun <T : Any?> T.isSameAs(value: T) = SameAndHashCode.isSameAs(this, value)

@CreatorsDsl
infix fun <T : Any?> T.isNotSameAs(value: T) = SameAndHashCode.isNotSameAs(this, value)

@CreatorsDsl
infix fun <T : Any?> T.isContentSameAs(value: T) = SameAndHashCode.isContentSameAs(this, value)

@CreatorsDsl
infix fun <T : Any?> T.isContentNotSameAs(value: T) = SameAndHashCode.isContentNotSameAs(this, value)

@CreatorsDsl
fun <T : Any?> T.hashOf() = SameAndHashCode.hashOf(this)

@CreatorsDsl
fun <T : Any?> T.hashOf(vararg args: Any?) = SameAndHashCode.hashOf(hashOf().toAtomic(), *args)

@CreatorsDsl
fun <T : Any?> T.idenOf() = SameAndHashCode.idenOf(this)

@CreatorsDsl
fun <V> dictOf(vararg args: Pair<String, V>): Map<String, V> = mapOf(*args)

@CreatorsDsl
fun <V> dictOfMutable(vararg args: Pair<String, V>): MutableMap<String, V> = mutableMapOf(*args)

@CreatorsDsl
inline fun stringOf(action: StringBuilder.() -> Unit): String = StringBuilder().apply(action).toString()

@CreatorsDsl
inline fun stringOf(size: Int, action: StringBuilder.() -> Unit): String = StringBuilder(size).apply(action).toString()

@CreatorsDsl
inline fun stringOf(data: String, action: StringBuilder.() -> Unit): String = StringBuilder(data).apply(action).toString()

@CreatorsDsl
inline fun stringOf(data: CharSequence, action: StringBuilder.() -> Unit): String = StringBuilder(data).apply(action).toString()

@CreatorsDsl
inline fun StringBuilder.add(data: Int): StringBuilder = append(data)

@CreatorsDsl
inline fun StringBuilder.add(data: Char): StringBuilder = append(data)

@CreatorsDsl
inline fun StringBuilder.add(data: Long): StringBuilder = append(data)

@CreatorsDsl
fun StringBuilder.add(data: IntProgression): StringBuilder {
    if (data.isEmpty().isNotTrue()) {
        data.forEach {
            append(it)
        }
    }
    return this
}

@CreatorsDsl
fun StringBuilder.add(data: CharProgression): StringBuilder {
    if (data.isEmpty().isNotTrue()) {
        data.forEach {
            append(it)
        }
    }
    return this
}

@CreatorsDsl
fun StringBuilder.add(data: LongProgression): StringBuilder {
    if (data.isEmpty().isNotTrue()) {
        data.forEach {
            append(it)
        }
    }
    return this
}

@CreatorsDsl
inline fun StringBuilder.add(vararg args: Any?): StringBuilder = append(*args)

@CreatorsDsl
inline fun StringBuilder.add(vararg args: String?): StringBuilder = append(*args)

@CreatorsDsl
fun <T : Any?> T.nameOf(): String = SameAndHashCode.nameOf(this)

@CreatorsDsl
infix fun Array<*>.isSameArrayAs(args: Array<*>): Boolean = size == args.size && this contentDeepEquals args

@CreatorsDsl
infix fun IntArray.isSameArrayAs(args: IntArray): Boolean = size == args.size && this contentEquals args

@CreatorsDsl
infix fun ByteArray.isSameArrayAs(args: ByteArray): Boolean = size == args.size && this contentEquals args

@CreatorsDsl
infix fun CharArray.isSameArrayAs(args: CharArray): Boolean = size == args.size && this contentEquals args

@CreatorsDsl
infix fun LongArray.isSameArrayAs(args: LongArray): Boolean = size == args.size && this contentEquals args

@CreatorsDsl
infix fun ShortArray.isSameArrayAs(args: ShortArray): Boolean = size == args.size && this contentEquals args

@CreatorsDsl
infix fun FloatArray.isSameArrayAs(args: FloatArray): Boolean = size == args.size && this contentEquals args

@CreatorsDsl
infix fun DoubleArray.isSameArrayAs(args: DoubleArray): Boolean = size == args.size && this contentEquals args

@CreatorsDsl
infix fun BooleanArray.isSameArrayAs(args: BooleanArray): Boolean = size == args.size && this contentEquals args

open class MercenarySequence<out T> @CreatorsDsl constructor(private val iterator: Iterator<T>) : Sequence<T> {

    @CreatorsDsl
    override operator fun iterator(): Iterator<T> = iterator

    @CreatorsDsl
    constructor() : this(listOf())

    @CreatorsDsl
    constructor(vararg source: T) : this(source.iterator())

    @CreatorsDsl
    constructor(source: Iterable<T>) : this(source.iterator())

    @CreatorsDsl
    constructor(source: Sequence<T>) : this(source.iterator())
}

@CreatorsDsl
fun <T> sequenceOf(): Sequence<T> = MercenarySequence()

@CreatorsDsl
fun <T> sequenceOf(vararg args: T): Sequence<T> = MercenarySequence(*args)

@CreatorsDsl
fun sequenceOf(args: IntProgression): Sequence<Int> = MercenarySequence(args)

@CreatorsDsl
fun sequenceOf(args: LongProgression): Sequence<Long> = MercenarySequence(args)

@CreatorsDsl
fun sequenceOf(args: CharProgression): Sequence<Char> = MercenarySequence(args)

@CreatorsDsl
fun <T : Any> Iterable<T>.toSequence(): Sequence<T> = MercenarySequence(this)

@CreatorsDsl
fun <T : Any> Iterator<T>.toSequence(): Sequence<T> = MercenarySequence(this)

@CreatorsDsl
fun <T : Any> sequenceOf(next: () -> T?): Sequence<T> = MercenarySequence(generateSequence(next))

@CreatorsDsl
fun <T : Any> sequenceOf(seed: T?, next: (T) -> T?): Sequence<T> = MercenarySequence(generateSequence(seed, next))

@CreatorsDsl
fun <T : Any> sequenceOf(seed: () -> T?, next: (T) -> T?): Sequence<T> = MercenarySequence(generateSequence(seed, next))

@CreatorsDsl
fun Sequence<String>.uniqueTrimmedOf(): List<String> = asIterable().uniqueTrimmedOf()

@CreatorsDsl
fun Iterable<String>.uniqueTrimmedOf(): List<String> = mapNotNull { toTrimOrNull(it) }.distinct()

@CreatorsDsl
inline fun <T : Any> T?.orElse(block: () -> T): T = this ?: block.invoke()

@CreatorsDsl
inline fun <T : Any> T?.orElse(value: T): T = this ?: value

@CreatorsDsl
inline fun String?.orElse(value: String = EMPTY_STRING): String = this ?: value

@CreatorsDsl
inline fun <reified T : Any> logsOf(): ILogging = LoggingFactory.logger(T::class)

@CreatorsDsl
fun mu.KLogger.getLevel(): LoggingLevel = LoggingFactory.getLevel(this)

@CreatorsDsl
fun mu.KLogger.setLevel(level: LoggingLevel) = LoggingFactory.setLevel(this, level)

@CreatorsDsl
fun mu.KLogger.withLevel(level: LoggingLevel, block: () -> Unit) {
    LoggingFactory.withLevel(this, level, block)
}

@CreatorsDsl
inline fun <T> withLoggingContext(args: Pair<String, Any>, block: () -> T): T {
    return mu.withLoggingContext(args.first to args.second.toString(), block)
}

@CreatorsDsl
inline fun <T> withLoggingContext(args: Map<String, Any>, block: () -> T): T {
    val hash = LinkedHashMap<String, String>(args.size)
    for ((k, v) in args) {
        hash[k] = v.toString()
    }
    return mu.withLoggingContext(hash, block)
}

@CreatorsDsl
inline fun <T> withLoggingContext(vararg args: Pair<String, Any>, block: () -> T): T {
    val hash = LinkedHashMap<String, String>(args.size)
    for ((k, v) in args) {
        hash[k] = v.toString()
    }
    return mu.withLoggingContext(hash, block)
}