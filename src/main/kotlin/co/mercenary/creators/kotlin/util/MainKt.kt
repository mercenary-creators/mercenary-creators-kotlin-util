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
@file:Suppress("NOTHING_TO_INLINE", "UNCHECKED_CAST")

package co.mercenary.creators.kotlin.util

import co.mercenary.creators.kotlin.util.security.*
import java.math.BigInteger
import java.nio.ByteBuffer
import java.nio.charset.Charset
import java.util.*
import java.util.concurrent.atomic.*
import java.util.stream.*
import kotlin.reflect.KClass
import kotlin.streams.*

@CreatorsDsl
const val IS_NOT_FOUND = -1

@CreatorsDsl
const val EMPTY_STRING = ""

@CreatorsDsl
const val SPACE_STRING = " "

@CreatorsDsl
const val SPACE_LETTER = ' '

@CreatorsDsl
const val MINUS_STRING = "-"

@CreatorsDsl
const val MINUS_LETTER = '-'

@CreatorsDsl
const val NULLS_STRING = "null"

@CreatorsDsl
const val DUNNO_STRING = "unknown"

@CreatorsDsl
const val KOTLIN_METAS = "kotlin.Metadata"

@CreatorsDsl
const val CREATORS_AUTHOR_INFO = "Dean S. Jones, Copyright (C) 2020, Mercenary Creators Company."

@CreatorsDsl
const val DEFAULT_MAP_FACTOR = 0.75

@CreatorsDsl
const val DEFAULT_MAP_CAPACITY = 16

@CreatorsDsl
const val DEFAULT_LIST_CAPACITY = 10

@CreatorsDsl
const val DEFAULT_PARALLEL_CUTOFF = 2

@CreatorsDsl
const val DEFAULT_STRINGOF_CAPACITY = 16

@CreatorsDsl
const val DEFAULT_LRU_THRESHOLD = DEFAULT_MAP_CAPACITY * 8

@CreatorsDsl
const val DEFAULT_LIST_THRESHOLD = DEFAULT_LIST_CAPACITY * 16

typealias Inflaters = co.mercenary.creators.kotlin.util.io.Inflaters

typealias Logging = co.mercenary.creators.kotlin.util.logging.Logging

typealias ILogging = co.mercenary.creators.kotlin.util.logging.ILogging

typealias ILoggingBase = co.mercenary.creators.kotlin.util.logging.ILoggingBase

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

typealias Manager = co.mercenary.creators.kotlin.util.system.Manager

typealias Launcher = co.mercenary.creators.kotlin.util.system.Launcher

typealias AtomicHashMap<K, V> = java.util.concurrent.ConcurrentHashMap<K, V>

typealias AtomicHashMapKeysView<K, V> = java.util.concurrent.ConcurrentHashMap.KeySetView<K, V>

typealias LRUCacheMap<K, V> = co.mercenary.creators.kotlin.util.collection.LRUCacheMap<K, V>

typealias IterableBuilder<V> = co.mercenary.creators.kotlin.util.collection.IterableBuilder<V>

typealias BasicDictionaryMap<V> = co.mercenary.creators.kotlin.util.collection.BasicDictionaryMap<V>

typealias StringDictionary = co.mercenary.creators.kotlin.util.collection.StringDictionary

typealias Dictionary<V> = Map<String, V>

typealias AnyDictionary = Dictionary<Any?>

typealias MutableDictionary<V> = MutableMap<String, V>

typealias MutableAnyDictionary = MutableDictionary<Any?>

typealias MessageDigestProxy = co.mercenary.creators.kotlin.util.security.Digests.MessageDigestProxy

@CreatorsDsl
fun <T : Any?> T.toSafeHashUf(): Int = hashOf()

@CreatorsDsl
fun <T : Any?> T.toSafeString(): String = Formatters.toSafeString { this }

@CreatorsDsl
fun Iterator<*>.toIteratorHashOf(): Int {
    val code = 1.toAtomic()
    for (each in this) {
        code.setValue(31 * code.getValue() + each.hashOf())
    }
    return code.getValue()
}

@CreatorsDsl
fun Iterable<*>.toIterableHashOf(): Int {
    if (this is Collection) {
        if (isEmpty()) {
            return 1
        }
    }
    return toIterator().toIteratorHashOf()
}

@CreatorsDsl
inline fun List<*>.toListHashOf(): Int {
    return if (isEmpty()) 1 else toIterator().toIteratorHashOf()
}

@CreatorsDsl
inline fun <E : Enum<E>> E.toOrdinalLong(): Long = ordinal.toLong()

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

open class MercenaryAssertionExceptiion @CreatorsDsl constructor(text: String?, root: Throwable?) : AssertionError(text, root) {

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
fun Properties.toStringDictionary() = StringDictionary(this)

@CreatorsDsl
fun Int.toListCapacity(): Int = if (isNegative()) DEFAULT_LIST_CAPACITY else minOf(DEFAULT_LIST_THRESHOLD)

@CreatorsDsl
fun Double.toMapFactorOrElse(value: Double = DEFAULT_MAP_FACTOR): Float = toFiniteOrElse(value.toFiniteOrElse(DEFAULT_MAP_FACTOR)).toFloat()

@CreatorsDsl
fun <K, V, T : MutableMap<in K, in V>> T.append(k: K, v: V): T {
    this[k] = v
    return this
}

@CreatorsDsl
fun <K, V, T : MutableMap<in K, in V>> T.append(args: Pair<K, V>): T {
    this[args.first] = args.second
    return this
}

@CreatorsDsl
fun <K, V, T : MutableMap<in K, in V>> T.append(vararg args: Pair<K, V>): T {
    for ((k, v) in args) {
        this[k] = v
    }
    return this
}

@CreatorsDsl
fun <K, V, T : MutableMap<in K, in V>> T.append(args: Iterator<Pair<K, V>>): T {
    for ((k, v) in args) {
        this[k] = v
    }
    return this
}

@CreatorsDsl
fun <K, V, T : MutableMap<in K, in V>> T.append(args: Iterable<Pair<K, V>>): T {
    for ((k, v) in args) {
        this[k] = v
    }
    return this
}

@CreatorsDsl
fun <K, V, T : MutableMap<in K, in V>> T.append(args: Sequence<Pair<K, V>>): T {
    for ((k, v) in args) {
        this[k] = v
    }
    return this
}

@CreatorsDsl
fun <K, V, T : MutableMap<in K, in V>> T.append(args: Map<out K, V>): T {
    for ((k, v) in args) {
        this[k] = v
    }
    return this
}

@CreatorsDsl
inline infix fun <K, V> Map<K, V>.isKeyDefined(key: K): Boolean = containsKey(key)

@CreatorsDsl
inline infix fun <K, V> Map<K, V>.isKeyNotDefined(key: K): Boolean = isKeyDefined(key).isNotTrue()

@CreatorsDsl
inline fun <K, V> toMapOf(): Map<K, V> = mapOf()

@CreatorsDsl
inline fun <K, V> toMapOf(vararg args: Pair<K, V>): Map<K, V> = mapOf(*args)

@CreatorsDsl
inline fun <T> toListOf(): List<T> = listOf()

@CreatorsDsl
fun <T> toListOf(args: T): List<T> = listOf(args)

@CreatorsDsl
fun <T> toListOf(vararg args: T): List<T> = listOf(*args)

@CreatorsDsl
fun <T> toListOf(args: Stream<T>): List<T> = args.toList()

@CreatorsDsl
fun <T> toListOf(args: Iterable<T>): List<T> = args.toList()

@CreatorsDsl
fun <T> toListOf(args: Iterator<T>): List<T> = args.toList()

@CreatorsDsl
fun <T> toListOf(args: Sequence<T>): List<T> = args.toList()

@CreatorsDsl
fun MessageDigestProxy.update(vararg args: ByteArray): MessageDigestProxy {
    if (args.isNotEmpty()) {
        args.forEach { buffer ->
            update(buffer)
        }
    }
    return this
}

@CreatorsDsl
fun MessageDigestProxy.update(args: List<ByteArray>): MessageDigestProxy {
    if (args.isNotEmpty()) {
        args.forEach { buffer ->
            update(buffer)
        }
    }
    return this
}

@CreatorsDsl
fun MessageDigestProxy.update(vararg args: ByteBuffer): MessageDigestProxy {
    if (args.isNotEmpty()) {
        args.forEach { buffer ->
            update(buffer)
        }
    }
    return this
}

@CreatorsDsl
fun MessageDigestProxy.update(args: Iterable<ByteBuffer>): MessageDigestProxy {
    val list = args.toList()
    if (list.isNotEmpty()) {
        list.forEach { buffer ->
            update(buffer)
        }
    }
    return this
}

@CreatorsDsl
fun Encoder<String, ByteArray>.toText(): Encoder<String, String> = Encoders.toText(this)

@CreatorsDsl
fun onExitOfProcess(push: Boolean = false, func: () -> Unit) {
    SecureAccess.onExitOfProcess(push, func)
}

@CreatorsDsl
fun Class<*>.isKotlinClass(): Boolean {
    return declaredAnnotations.any { it.annotationClass.java.name == KOTLIN_METAS }
}

@CreatorsDsl
fun Class<*>.toPackageName(): String = this.`package`.name

@CreatorsDsl
fun KClass<*>.toPackageName(): String = java.`package`.name

@CreatorsDsl
inline fun <reified T : Any> packageNameOf(): String = T::class.java.`package`.name

@CreatorsDsl
inline fun <T : Any> T.typeOf(): KClass<T> {
    if (this is KClass<*>) {
        return this as KClass<T>
    }
    if (this is Class<*>) {
        return this.kotlin as KClass<T>
    }
    return javaClass.kotlin
}

@CreatorsDsl
infix fun <T : Any> T.isType(other: Any): Boolean {
    return javaClass.kotlin == other.javaClass.kotlin
}

@CreatorsDsl
fun <K, V> atomicMapOf(): AtomicHashMap<K, V> {
    return AtomicHashMap(DEFAULT_MAP_CAPACITY)
}

@CreatorsDsl
fun <K, V> atomicMapOf(size: Int): AtomicHashMap<K, V> {
    return AtomicHashMap(size.maxOf(DEFAULT_MAP_CAPACITY))
}

@CreatorsDsl
fun <K, V> atomicMapOf(k: K, v: V): AtomicHashMap<K, V> {
    return atomicMapOf<K, V>().append(k, v)
}

@CreatorsDsl
fun <K, V> atomicMapOf(args: Map<K, V>): AtomicHashMap<K, V> {
    return atomicMapOf<K, V>(args.size).append(args)
}

@CreatorsDsl
fun <K, V> atomicMapOf(args: Pair<K, V>): AtomicHashMap<K, V> {
    return atomicMapOf<K, V>().append(args)
}

@CreatorsDsl
fun <K, V> atomicMapOf(vararg args: Pair<K, V>): AtomicHashMap<K, V> {
    return atomicMapOf<K, V>(args.size).append(*args)
}

@CreatorsDsl
fun <K, V> Map<K, V>.toAtomic(): AtomicHashMap<K, V> {
    return if (this is AtomicHashMap) this else atomicMapOf(this)
}

@CreatorsDsl
fun <K, V> AtomicHashMap<K, V>.toMap(): Map<K, V> {
    return if (isNotEmpty()) LinkedHashMap(this).toMap() else toMapOf()
}

@CreatorsDsl
fun <K, V> AtomicHashMap<K, V>.toMutableMap(): MutableMap<K, V> {
    return this
}

@CreatorsDsl
fun <K, V> AtomicHashMap<K, V>.copyOf(): AtomicHashMap<K, V> {
    return atomicMapOf(this)
}

@CreatorsDsl
fun <E> List<E>.whenNotEmpty(block: (List<E>) -> Unit) {
    if (isNotEmpty()) block(this)
}

@CreatorsDsl
fun <E, T : MutableList<E>> T.push(args: E): T {
    add(0, args)
    return this
}

@CreatorsDsl
fun <E, T : MutableList<E>> T.append(args: E): T {
    this += args
    return this
}

@CreatorsDsl
fun <E, T : MutableList<E>> T.append(vararg args: E): T {
    for (item in args) {
        this += item
    }
    return this
}

@CreatorsDsl
fun <E, T : MutableList<E>> T.append(args: Iterator<E>): T {
    for (item in args) {
        this += item
    }
    return this
}

@CreatorsDsl
fun <E, T : MutableList<E>> T.append(args: Iterable<E>): T {
    for (item in args) {
        this += item
    }
    return this
}

@CreatorsDsl
fun <E, T : MutableList<E>> T.append(args: Sequence<E>): T {
    for (item in args) {
        this += item
    }
    return this
}

@CreatorsDsl
fun getCurrentThreadName(): String = Thread.currentThread().name

@CreatorsDsl
inline fun getProcessors(): Int = Runtime.getRuntime().availableProcessors()

@CreatorsDsl
fun toTrimOrNull(data: CharSequence?): String? {
    return when (data == null) {
        true -> null
        else -> data.toString().trim().let { look ->
            when (look.isNotEmpty()) {
                true -> look
                else -> null
            }
        }
    }
}

@CreatorsDsl
fun toTrimOrElse(data: CharSequence?, other: String = EMPTY_STRING): String = toTrimOrNull(data) ?: other

@CreatorsDsl
inline fun toTrimOrElse(data: CharSequence?, other: () -> String): String = toTrimOrNull(data) ?: other.invoke()

@CreatorsDsl
inline fun CharSequence?.toTrimOr(other: String = EMPTY_STRING): String = toTrimOrNull(this) ?: other

@CreatorsDsl
inline fun CharSequence?.toTrimOr(other: () -> String): String = toTrimOrNull(this) ?: other.invoke()

@CreatorsDsl
inline fun String.head(many: Int = 1): String = if (many >= 0) drop(many) else this

@CreatorsDsl
inline fun String.tail(many: Int = 1): String = if (many >= 0) dropLast(many) else this

@CreatorsDsl
fun String.filter(pads: Char = SPACE_LETTER, trim: Boolean = true): String = when (trim.isNotTrue()) {
    true -> this
    else -> when (pads.isWhitespace()) {
        true -> trim()
        else -> trim {
            it == pads
        }
    }
}

@CreatorsDsl
fun <R> CharSequence?.whenNotEmptyDo(data: R, action: (String) -> R): R {
    toTrimOr(EMPTY_STRING).also { buff ->
        return if (buff.isNotSameAs(EMPTY_STRING)) action(buff) else data
    }
}

@CreatorsDsl
fun String.padout(many: Int, pads: Char = SPACE_LETTER): String = if (many <= 0) this else padStart((length + many) / 2, pads).padEnd(many, pads)

@CreatorsDsl
fun String.center(many: Int, pads: Char = SPACE_LETTER, trim: Boolean = true): String = if (many <= 0) this else filter(pads, trim).padout(many, pads)

@CreatorsDsl
inline fun CharSequence.isZeroCharPresent(): Boolean = any { it == Char.MIN_VALUE }

@CreatorsDsl
inline fun CharSequence.isZeroCharNotPresent(): Boolean = isZeroCharPresent().isNotTrue()

@CreatorsDsl
fun CharSequence.toChecked(): String {
    return if (isZeroCharPresent())
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
inline fun Validated.isNotValid(): Boolean = isValid().isNotTrue()

@CreatorsDsl
fun isValid(value: Maybe): Boolean = when (value) {
    null -> false
    is Throwable -> false
    is Float -> value.isValid()
    is Double -> value.isValid()
    is Boolean -> value.isTrue()
    is Validated -> value.isValid()
    is AtomicBoolean -> value.isTrue()
    else -> true
}

@CreatorsDsl
inline fun isValid(block: LazyMessage): Boolean = try {
    isValid(block.invoke())
} catch (cause: Throwable) {
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
fun AtomicLong.setValue(value: Int): AtomicLong {
    return setValue(value.toLong())
}

@CreatorsDsl
fun AtomicLong.setValue(value: Long): AtomicLong {
    set(value)
    return this
}

@CreatorsDsl
fun AtomicLong.setValue(value: AtomicLong): AtomicLong {
    return setValue(value.getValue())
}

@CreatorsDsl
fun AtomicLong.setValue(value: AtomicInteger): AtomicLong {
    return setValue(value.getValue())
}

@CreatorsDsl
fun AtomicLong.getValue(): Long {
    return get()
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
fun AtomicInteger.setValue(value: Int): AtomicInteger {
    set(value)
    return this
}

@CreatorsDsl
fun AtomicInteger.setValue(value: AtomicInteger): AtomicInteger {
    return setValue(value.getValue())
}

@CreatorsDsl
fun AtomicInteger.getValue(): Int {
    return get()
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
inline fun Boolean.isNotTrue(): Boolean = !this

@CreatorsDsl
fun AtomicBoolean.copyOf(): AtomicBoolean = toAtomic()

@CreatorsDsl
fun AtomicBoolean.toAtomic(): AtomicBoolean = toBoolean().toAtomic()

@CreatorsDsl
inline fun AtomicBoolean.toBoolean(): Boolean = get()

@CreatorsDsl
inline fun AtomicBoolean.isTrue(): Boolean = get()

@CreatorsDsl
inline fun AtomicBoolean.isNotTrue(): Boolean = !get()

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
inline operator fun AtomicBoolean.not(): Boolean = !get()

@CreatorsDsl
inline infix fun Boolean.or(value: AtomicBoolean): Boolean = toBoolean() || value.toBoolean()

@CreatorsDsl
inline infix fun AtomicBoolean.or(value: Boolean): Boolean = toBoolean() || value.toBoolean()

@CreatorsDsl
inline infix fun AtomicBoolean.or(value: AtomicBoolean): Boolean = toBoolean() || value.toBoolean()

@CreatorsDsl
inline infix fun Boolean.and(value: AtomicBoolean): Boolean = toBoolean() && value.toBoolean()

@CreatorsDsl
inline infix fun AtomicBoolean.and(value: Boolean): Boolean = toBoolean() && value.toBoolean()

@CreatorsDsl
inline infix fun AtomicBoolean.and(value: AtomicBoolean): Boolean = toBoolean() && value.toBoolean()

@CreatorsDsl
inline infix fun Boolean.xor(value: AtomicBoolean): Boolean = toBoolean() != value.toBoolean()

@CreatorsDsl
inline infix fun AtomicBoolean.xor(value: Boolean): Boolean = toBoolean() != value.toBoolean()

@CreatorsDsl
inline infix fun AtomicBoolean.xor(value: AtomicBoolean): Boolean = toBoolean() != value.toBoolean()

@CreatorsDsl
fun <T : Any?> T.hashOf() = SameAndHashCode.hashOf(this)

@CreatorsDsl
fun <T : Any?> T.hashOf(vararg args: Any?) = SameAndHashCode.hashOf(hashOf().toAtomic(), *args)

@CreatorsDsl
fun <T : Any?> T.idenOf() = SameAndHashCode.idenOf(this)

@CreatorsDsl
infix fun <B> String.to(that: B): Pair<String, B> = Pair(this, that)

@CreatorsDsl
fun <V> dictOf(): Dictionary<V> = toMapOf()

@CreatorsDsl
fun <V> dictOf(vararg args: Pair<String, V>): Dictionary<V> = toMapOf(*args)

@CreatorsDsl
fun <V> dictOfMutable(): MutableDictionary<V> = mutableMapOf()

@CreatorsDsl
fun <V> dictOfMutable(vararg args: Pair<String, V>): MutableDictionary<V> = mutableMapOf(*args)

@CreatorsDsl
inline fun stringBuilderOf() = StringBuilder()

@CreatorsDsl
inline fun stringBuilderOf(size: Int) = StringBuilder(size)

@CreatorsDsl
inline fun stringBuilderOf(data: String) = StringBuilder(data)

@CreatorsDsl
inline fun stringBuilderOf(data: CharSequence) = StringBuilder(data)

@CreatorsDsl
inline fun StringBuilder.finish(action: StringBuilder.() -> Unit): String = apply(action).toString()

@CreatorsDsl
inline fun stringOf(action: StringBuilder.() -> Unit): String = stringBuilderOf().finish(action)

@CreatorsDsl
inline fun stringOf(size: Int, action: StringBuilder.() -> Unit): String = stringBuilderOf(size).finish(action)

@CreatorsDsl
inline fun stringOf(data: String, action: StringBuilder.() -> Unit): String = stringBuilderOf(data).finish(action)

@CreatorsDsl
inline fun stringOf(data: CharSequence, action: StringBuilder.() -> Unit): String = stringBuilderOf(data).finish(action)

@CreatorsDsl
inline fun StringBuilder.add(data: Int): StringBuilder = append(data)

@CreatorsDsl
inline fun StringBuilder.add(data: Char): StringBuilder = append(data)

@CreatorsDsl
inline fun StringBuilder.add(data: Long): StringBuilder = append(data)

@CreatorsDsl
inline fun StringBuilder.add(data: CharArray): StringBuilder = append(data)

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
inline fun Char.toCode(): Int = toInt()

@CreatorsDsl
fun StringBuilder.encode(data: Int): StringBuilder {
    data.toHexString().toUpperCaseEnglish().let { buff ->
        when ((4 - buff.length).boxIn(0, 3)) {
            0 -> add("\\u", buff)
            1 -> add("\\u0", buff)
            2 -> add("\\u00", buff)
            3 -> add("\\u000", buff)
            else -> add(EMPTY_STRING)
        }
    }
    return this
}

@CreatorsDsl
fun StringBuilder.escape(data: Char): StringBuilder {
    return add(Escapers.ESCAPE_SLASH).add(data)
}

@CreatorsDsl
fun StringBuilder.push(data: Char): StringBuilder {
    insert(0, data)
    return this
}

@CreatorsDsl
fun StringBuilder.push(data: String): StringBuilder {
    insert(0, data)
    return this
}

@CreatorsDsl
fun StringBuilder.push(data: CharSequence): StringBuilder {
    insert(0, data.toString())
    return this
}

@CreatorsDsl
fun StringBuilder.wrap(data: Char): StringBuilder {
    return push(data).add(data)
}

@CreatorsDsl
fun StringBuilder.wrap(data: String): StringBuilder {
    return push(data).add(data)
}

@CreatorsDsl
fun StringBuilder.wrap(data: CharSequence): StringBuilder {
    return wrap(data.toString())
}

@CreatorsDsl
@JvmOverloads
fun <T> Array<T>.parrallel(cutoff: Int = DEFAULT_PARALLEL_CUTOFF, transform: (Int) -> T): Array<T> {
    if (size > 0) {
        if (size <= cutoff) {
            size.forEach { index ->
                this[index] = transform.invoke(index)
            }
        } else {
            IntStream.range(0, size).parallel().forEach { index ->
                this[index] = transform.invoke(index)
            }
        }
    }
    return this
}

@CreatorsDsl
@JvmOverloads
fun IntArray.parrallel(cutoff: Int = DEFAULT_PARALLEL_CUTOFF, transform: (Int) -> Int): IntArray {
    if (size > 0) {
        if (size <= cutoff) {
            size.forEach { index ->
                this[index] = transform.invoke(index)
            }
        } else {
            IntStream.range(0, size).parallel().forEach { index ->
                this[index] = transform.invoke(index)
            }
        }
    }
    return this
}

@CreatorsDsl
@JvmOverloads
fun ByteArray.parrallel(cutoff: Int = DEFAULT_PARALLEL_CUTOFF, transform: (Int) -> Byte): ByteArray {
    if (size > 0) {
        if (size <= cutoff) {
            size.forEach { index ->
                this[index] = transform.invoke(index)
            }
        } else {
            IntStream.range(0, size).parallel().forEach { index ->
                this[index] = transform.invoke(index)
            }
        }
    }
    return this
}

@CreatorsDsl
@JvmOverloads
fun CharArray.parrallel(cutoff: Int = DEFAULT_PARALLEL_CUTOFF, transform: (Int) -> Char): CharArray {
    if (size > 0) {
        if (size <= cutoff) {
            size.forEach { index ->
                this[index] = transform.invoke(index)
            }
        } else {
            IntStream.range(0, size).parallel().forEach { index ->
                this[index] = transform.invoke(index)
            }
        }
    }
    return this
}

@CreatorsDsl
@JvmOverloads
fun LongArray.parrallel(cutoff: Int = DEFAULT_PARALLEL_CUTOFF, transform: (Int) -> Long): LongArray {
    if (size > 0) {
        if (size <= cutoff) {
            size.forEach { index ->
                this[index] = transform.invoke(index)
            }
        } else {
            IntStream.range(0, size).parallel().forEach { index ->
                this[index] = transform.invoke(index)
            }
        }
    }
    return this
}

@CreatorsDsl
@JvmOverloads
fun ShortArray.parrallel(cutoff: Int = DEFAULT_PARALLEL_CUTOFF, transform: (Int) -> Short): ShortArray {
    if (size > 0) {
        if (size <= cutoff) {
            size.forEach { index ->
                this[index] = transform.invoke(index)
            }
        } else {
            IntStream.range(0, size).parallel().forEach { index ->
                this[index] = transform.invoke(index)
            }
        }
    }
    return this
}

@CreatorsDsl
@JvmOverloads
fun FloatArray.parrallel(cutoff: Int = DEFAULT_PARALLEL_CUTOFF, transform: (Int) -> Float): FloatArray {
    if (size > 0) {
        if (size <= cutoff) {
            size.forEach { index ->
                this[index] = transform.invoke(index)
            }
        } else {
            IntStream.range(0, size).parallel().forEach { index ->
                this[index] = transform.invoke(index)
            }
        }
    }
    return this
}

@CreatorsDsl
@JvmOverloads
fun DoubleArray.parrallel(cutoff: Int = DEFAULT_PARALLEL_CUTOFF, transform: (Int) -> Double): DoubleArray {
    if (size > 0) {
        if (size <= cutoff) {
            size.forEach { index ->
                this[index] = transform.invoke(index)
            }
        } else {
            IntStream.range(0, size).parallel().forEach { index ->
                this[index] = transform.invoke(index)
            }
        }
    }
    return this
}

@CreatorsDsl
@JvmOverloads
fun BooleanArray.parrallel(cutoff: Int = DEFAULT_PARALLEL_CUTOFF, transform: (Int) -> Boolean): BooleanArray {
    if (size > 0) {
        if (size <= cutoff) {
            size.forEach { index ->
                this[index] = transform.invoke(index)
            }
        } else {
            IntStream.range(0, size).parallel().forEach { index ->
                this[index] = transform.invoke(index)
            }
        }
    }
    return this
}

@CreatorsDsl
inline fun StringBuilder.add(vararg args: Any?): StringBuilder = append(*args)

@CreatorsDsl
inline fun StringBuilder.add(vararg args: String?): StringBuilder = append(*args)

@CreatorsDsl
inline fun StringBuilder.newline(): StringBuilder = add(BREAK_STRING)

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

@CreatorsDsl
infix fun Array<*>.isNotSameArrayAs(args: Array<*>): Boolean = this.isSameArrayAs(args).isNotTrue()

@CreatorsDsl
infix fun IntArray.isNotSameArrayAs(args: IntArray): Boolean = this.isSameArrayAs(args).isNotTrue()

@CreatorsDsl
infix fun ByteArray.isNotSameArrayAs(args: ByteArray): Boolean = this.isSameArrayAs(args).isNotTrue()

@CreatorsDsl
infix fun CharArray.isNotSameArrayAs(args: CharArray): Boolean = this.isSameArrayAs(args).isNotTrue()

@CreatorsDsl
infix fun LongArray.isNotSameArrayAs(args: LongArray): Boolean = this.isSameArrayAs(args).isNotTrue()

@CreatorsDsl
infix fun ShortArray.isNotSameArrayAs(args: ShortArray): Boolean = this.isSameArrayAs(args).isNotTrue()

@CreatorsDsl
infix fun FloatArray.isNotSameArrayAs(args: FloatArray): Boolean = this.isSameArrayAs(args).isNotTrue()

@CreatorsDsl
infix fun DoubleArray.isNotSameArrayAs(args: DoubleArray): Boolean = this.isSameArrayAs(args).isNotTrue()

@CreatorsDsl
infix fun BooleanArray.isNotSameArrayAs(args: BooleanArray): Boolean = this.isSameArrayAs(args).isNotTrue()

@CreatorsDsl
inline fun IntArray.toContentHashOf(): Int = contentHashCode()

@CreatorsDsl
inline fun ByteArray.toContentHashOf(): Int = contentHashCode()

@CreatorsDsl
inline fun CharArray.toContentHashOf(): Int = contentHashCode()

@CreatorsDsl
inline fun LongArray.toContentHashOf(): Int = contentHashCode()

@CreatorsDsl
inline fun ShortArray.toContentHashOf(): Int = contentHashCode()

@CreatorsDsl
inline fun FloatArray.toContentHashOf(): Int = contentHashCode()

@CreatorsDsl
inline fun DoubleArray.toContentHashOf(): Int = contentHashCode()

@CreatorsDsl
inline fun BooleanArray.toContentHashOf(): Int = contentHashCode()

@CreatorsDsl
inline fun Array<*>.toContentHashOf(): Int = contentDeepHashCode()

@CreatorsDsl
fun Constrained.isNotConstrained(): Boolean = isConstrained().isNotTrue()

@CreatorsDsl
fun Alive.isNotAlive(): Boolean = isAlive().isNotTrue()

@CreatorsDsl
inline fun Iterator<String>.toNoEmptyChars(): List<String> {
    return toList().filter { it.isZeroCharNotPresent() }
}

open class MercenarySequence<out T> @CreatorsDsl constructor(private val iterator: Iterator<T>) : Sequence<T> {

    @CreatorsDsl
    override operator fun iterator(): Iterator<T> = iterator

    @CreatorsDsl
    constructor() : this(toListOf())

    @CreatorsDsl
    constructor(vararg source: T) : this(source.toIterator())

    @CreatorsDsl
    constructor(source: Stream<T>) : this(source.toIterator())

    @CreatorsDsl
    constructor(source: Iterable<T>) : this(source.toIterator())

    @CreatorsDsl
    constructor(source: Sequence<T>) : this(source.toIterator())
}

class MercenaryConstrainedSequence<out T> @CreatorsDsl constructor(source: Iterator<T>) : MercenarySequence<T>(source), Sequence<T>

@CreatorsDsl
inline fun <T> Sequence<T>.constrain(): Sequence<T> {
    return if (this is MercenaryConstrainedSequence) this else MercenaryConstrainedSequence(constrainOnce().toIterator())
}

@CreatorsDsl
inline fun <T> Array<T>.toIterator(): Iterator<T> = iterator()

@CreatorsDsl
inline fun <T> Stream<T>.toIterator(): Iterator<T> = iterator()

@CreatorsDsl
inline fun <T> Sequence<T>.toIterator(): Iterator<T> = iterator()

@CreatorsDsl
inline fun <T> Iterator<T>.toIterator(): Iterator<T> = iterator()

@CreatorsDsl
inline fun <T> Iterable<T>.toIterator(): Iterator<T> = iterator()

@CreatorsDsl
fun <T> Array<T>.toIterable(): Iterable<T> = Iterable { toIterator() }

@CreatorsDsl
fun <T> Sequence<T>.toIterable(): Iterable<T> = Iterable { toIterator() }

@CreatorsDsl
fun <T> Iterator<T>.toIterable(): Iterable<T> = Iterable { toIterator() }

@CreatorsDsl
fun <T> Iterator<T>.toList(): List<T> = toIterable().toList()

@CreatorsDsl
inline fun <T> Sequence<T>.toStream(): Stream<T> = asStream()

@CreatorsDsl
fun <T> sequenceOf(): Sequence<T> = MercenarySequence()

@CreatorsDsl
fun sequenceOf(args: IntProgression): Sequence<Int> = MercenarySequence(args)

@CreatorsDsl
fun sequenceOf(args: LongProgression): Sequence<Long> = MercenarySequence(args)

@CreatorsDsl
fun sequenceOf(args: CharProgression): Sequence<Char> = MercenarySequence(args)

@CreatorsDsl
fun IntStream.toSequence(): Sequence<Int> = iterator().toSequence()

@CreatorsDsl
fun LongStream.toSequence(): Sequence<Long> = iterator().toSequence()

@CreatorsDsl
fun DoubleStream.toSequence(): Sequence<Double> = iterator().toSequence()

@CreatorsDsl
fun <T : Any> Stream<T>.toSequence(): Sequence<T> = MercenarySequence(this)

@CreatorsDsl
fun <T : Any> Iterable<T>.toSequence(): Sequence<T> = iterator().toSequence()

@CreatorsDsl
fun <T : Any> Iterator<T>.toSequence(): Sequence<T> = MercenarySequence(this)

@CreatorsDsl
fun <T> sequenceOf(vararg args: T): Sequence<T> = MercenarySequence(args.toIterator())

@CreatorsDsl
fun <T : Any> sequenceOf(next: () -> T?): Sequence<T> = MercenarySequence(generateSequence(next))

@CreatorsDsl
fun <T : Any> sequenceOf(seed: T?, next: (T) -> T?): Sequence<T> = MercenarySequence(generateSequence(seed, next))

@CreatorsDsl
fun <T : Any> sequenceOf(seed: () -> T?, next: (T) -> T?): Sequence<T> = MercenarySequence(generateSequence(seed, next))

@CreatorsDsl
inline fun <T : Any> iteratorOf(vararg args: T): Iterator<T> = args.toIterator()

@CreatorsDsl
inline fun <T : Any> unique(vararg args: T): List<T> = args.toIterator().toList().distinct()

@CreatorsDsl
fun Sequence<String>.uniqueTrimmedOf(): List<String> = toIterable().uniqueTrimmedOf()

@CreatorsDsl
fun Iterable<String>.uniqueTrimmedOf(): List<String> = mapNotNull { toTrimOrNull(it) }.distinct()

@CreatorsDsl
fun <T : Comparable<T>> Iterable<T>.toSortedListOf(): List<T> = sorted()

@CreatorsDsl
fun <T : Comparable<T>> Iterable<T>.toReverseSortedListOf(): List<T> = sorted().reversed()

@CreatorsDsl
fun <T : Any> Iterator<T>.toEnumeration(): Enumeration<T> = object : Enumeration<T> {
    override fun nextElement(): T = next()
    override fun hasMoreElements(): Boolean = hasNext()
}

@CreatorsDsl
inline fun <T : Any> Iterable<T>.toEnumeration(): Enumeration<T> = iterator().toEnumeration()

@CreatorsDsl
inline fun <T : Any> Sequence<T>.toEnumeration(): Enumeration<T> = iterator().toEnumeration()

@CreatorsDsl
inline fun <T : Any> T?.otherwise(block: () -> T): T = this ?: block.invoke()

@CreatorsDsl
inline fun <T : Any> T?.otherwise(value: T): T = this ?: value

@CreatorsDsl
inline fun String?.otherwise(value: String = EMPTY_STRING): String = this ?: value

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