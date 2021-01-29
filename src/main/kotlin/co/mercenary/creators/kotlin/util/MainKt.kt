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

import co.mercenary.creators.kotlin.util.collection.BasicLinkedMap
import co.mercenary.creators.kotlin.util.security.*
import java.math.BigInteger
import java.nio.ByteBuffer
import java.nio.charset.Charset
import java.nio.file.Path
import java.util.*
import java.util.concurrent.atomic.*
import java.util.stream.*
import kotlin.collections.LinkedHashSet
import kotlin.reflect.KClass
import kotlin.streams.*

@FrameworkDsl
const val IS_NOT_FOUND = -1

@FrameworkDsl
const val EMPTY_STRING = ""

@FrameworkDsl
const val SPACE_STRING = " "

@FrameworkDsl
const val SPACE_LETTER = ' '

@FrameworkDsl
const val MINUS_STRING = "-"

@FrameworkDsl
const val MINUS_LETTER = '-'

@FrameworkDsl
const val NULLS_STRING = "null"

@FrameworkDsl
const val DUNNO_STRING = "unknown"

@FrameworkDsl
const val KOTLIN_METAS = "kotlin.Metadata"

@FrameworkDsl
const val DIGIT_STRING = "0123456789ABCDEF"

@FrameworkDsl
const val CREATORS_AUTHOR_INFO = "Dean S. Jones, Copyright (C) 2020, Mercenary Creators Company."

@FrameworkDsl
const val DEFAULT_MAP_FACTOR = 0.75

@FrameworkDsl
const val DEFAULT_MAP_CAPACITY = 16

@FrameworkDsl
const val DEFAULT_LIST_CAPACITY = 10

@FrameworkDsl
const val DEFAULT_STRINGOF_CAPACITY = 16

@FrameworkDsl
const val DEFAULT_LRU_THRESHOLD = DEFAULT_MAP_CAPACITY * 8

@FrameworkDsl
const val DEFAULT_LIST_THRESHOLD = DEFAULT_LIST_CAPACITY * 16

@FrameworkDsl
const val HASH_NULL_VALUE = 0

@FrameworkDsl
const val HASH_BASE_VALUE = 1

@FrameworkDsl
const val HASH_NEXT_VALUE = 31

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

typealias BasicDictionaryMap<V> = co.mercenary.creators.kotlin.util.collection.BasicDictionaryMap<V>

typealias BasicArrayList<V> = co.mercenary.creators.kotlin.util.collection.BasicArrayList<V>

typealias StringDictionary = co.mercenary.creators.kotlin.util.collection.StringDictionary

typealias Dictionary<V> = Map<String, V>

typealias AnyDictionary = Dictionary<Any?>

typealias MutableDictionary<V> = MutableMap<String, V>

typealias MutableAnyDictionary = MutableDictionary<Any?>

typealias MessageDigestProxy = co.mercenary.creators.kotlin.util.security.Digests.MessageDigestProxy

@FrameworkDsl
fun <T : Any?> T.toSafeHashOf(): Int = this?.hashOf() ?: HASH_NULL_VALUE

@FrameworkDsl
fun <T : Any?> T.toSafeString(): String = Formatters.toSafeString { this }

@FrameworkDsl
inline fun Set<*>.isExhausted(): Boolean = isEmpty()

@FrameworkDsl
inline fun Set<*>.isNotExhausted(): Boolean = isExhausted().isNotTrue()

@FrameworkDsl
inline fun List<*>.isExhausted(): Boolean = isEmpty()

@FrameworkDsl
inline fun List<*>.isNotExhausted(): Boolean = isExhausted().isNotTrue()

@FrameworkDsl
inline fun Array<*>.isExhausted(): Boolean = sizeOf() == 0

@FrameworkDsl
inline fun Array<*>.isNotExhausted(): Boolean = isExhausted().isNotTrue()

@FrameworkDsl
inline fun IntArray.isExhausted(): Boolean = sizeOf() == 0

@FrameworkDsl
inline fun IntArray.isNotExhausted(): Boolean = isExhausted().isNotTrue()

@FrameworkDsl
inline fun ByteArray.isExhausted(): Boolean = sizeOf() == 0

@FrameworkDsl
inline fun ByteArray.isNotExhausted(): Boolean = isExhausted().isNotTrue()

@FrameworkDsl
inline fun CharArray.isExhausted(): Boolean = sizeOf() == 0

@FrameworkDsl
inline fun CharArray.isNotExhausted(): Boolean = isExhausted().isNotTrue()

@FrameworkDsl
inline fun LongArray.isExhausted(): Boolean = sizeOf() == 0

@FrameworkDsl
inline fun LongArray.isNotExhausted(): Boolean = isExhausted().isNotTrue()

@FrameworkDsl
inline fun ShortArray.isExhausted(): Boolean = sizeOf() == 0

@FrameworkDsl
inline fun ShortArray.isNotExhausted(): Boolean = isExhausted().isNotTrue()

@FrameworkDsl
inline fun FloatArray.isExhausted(): Boolean = sizeOf() == 0

@FrameworkDsl
inline fun FloatArray.isNotExhausted(): Boolean = isExhausted().isNotTrue()

@FrameworkDsl
inline fun DoubleArray.isExhausted(): Boolean = sizeOf() == 0

@FrameworkDsl
inline fun DoubleArray.isNotExhausted(): Boolean = isExhausted().isNotTrue()

@FrameworkDsl
inline fun BooleanArray.isExhausted(): Boolean = sizeOf() == 0

@FrameworkDsl
inline fun BooleanArray.isNotExhausted(): Boolean = isExhausted().isNotTrue()

@FrameworkDsl
inline fun Map<*, *>.isExhausted(): Boolean = isEmpty()

@FrameworkDsl
inline fun Map<*, *>.isNotExhausted(): Boolean = isExhausted().isNotTrue()

@FrameworkDsl
inline fun Iterator<*>.isExhausted(): Boolean = hasNext().isNotTrue()

@FrameworkDsl
inline fun Iterator<*>.isNotExhausted(): Boolean = hasNext()

@FrameworkDsl
inline fun Sequence<*>.isExhausted(): Boolean = toIterator().isExhausted()

@FrameworkDsl
inline fun Sequence<*>.isNotExhausted(): Boolean = toIterator().isNotExhausted()

@FrameworkDsl
inline fun Iterable<*>.isExhausted(): Boolean = if (this is Collection) isEmpty() else toIterator().isExhausted()

@FrameworkDsl
inline fun Iterable<*>.isNotExhausted(): Boolean = if (this is Collection) isNotEmpty() else toIterator().isNotExhausted()

@FrameworkDsl
inline fun Int.isExhausted(): Boolean = this == 0

@FrameworkDsl
inline fun Int.isNotExhausted(): Boolean = this != 0

@FrameworkDsl
inline fun Long.isExhausted(): Boolean = this == 0L

@FrameworkDsl
inline fun Long.isNotExhausted(): Boolean = this != 0L

@FrameworkDsl
inline fun Double.isExhausted(): Boolean = if (isNotValid()) true else absOf() == 0.0

@FrameworkDsl
inline fun Double.isNotExhausted(): Boolean = isValid() && absOf() != 0.0

@FrameworkDsl
inline fun <T : Comparable<T>> T.isLessThan(other: T): Boolean = this < other

@FrameworkDsl
inline fun <T : Comparable<T>> T.isMoreThan(other: T): Boolean = this > other

@FrameworkDsl
inline infix fun <T : Comparable<T>> T.maxOf(other: T): T {
    return if (this < other) other else this
}

@FrameworkDsl
inline infix fun <T : Comparable<T>> T.minOf(other: T): T {
    return if (this < other) this else other
}

@FrameworkDsl
inline fun <T : Comparable<T>> boxInCheck(min: T, max: T) {
    if (min > max) throw MercenaryMathExceptiion("${max.nameOf()}.boxIn().invalid()")
}

@FrameworkDsl
inline fun <T : Comparable<T>> T.boxIn(min: T, max: T): T {
    boxInCheck(min, max)
    return when {
        this.isLessThan(min) -> min
        this.isMoreThan(max) -> max
        else -> this
    }
}

@FrameworkDsl
inline fun <K, V> Array<out Pair<K, V>>.mapTo(): Map<K, V> = if (isExhausted()) toMapOf() else toMap()

@FrameworkDsl
inline fun <K, V> Iterator<Pair<K, V>>.mapTo(): Map<K, V> = if (isExhausted()) toMapOf() else toIterable().toMap()

@FrameworkDsl
inline fun <K, V> Iterable<Pair<K, V>>.mapTo(): Map<K, V> = if (isExhausted()) toMapOf() else toMap()

@FrameworkDsl
inline fun <K, V> Sequence<Pair<K, V>>.mapTo(): Map<K, V> = if (isExhausted()) toMapOf() else toMap()

@FrameworkDsl
fun Iterator<*>.hashOf(): Int {
    if (isExhausted()) {
        return HASH_BASE_VALUE
    }
    var code = HASH_BASE_VALUE
    for (each in this) {
        code = code * HASH_NEXT_VALUE + each.hashOf()
    }
    return code
}

@FrameworkDsl
fun Iterable<*>.hashOf(): Int {
    if (isExhausted()) {
        return HASH_BASE_VALUE
    }
    var code = HASH_BASE_VALUE
    for (each in this) {
        code = code * HASH_NEXT_VALUE + each.hashOf()
    }
    return code
}

@FrameworkDsl
inline fun Set<*>.hashOf(): Int {
    if (isExhausted()) {
        return HASH_BASE_VALUE
    }
    var code = HASH_BASE_VALUE
    for (each in this) {
        code = code * HASH_NEXT_VALUE + each.hashOf()
    }
    return code
}

@FrameworkDsl
inline fun List<*>.hashOf(): Int {
    if (isExhausted()) {
        return HASH_BASE_VALUE
    }
    var code = HASH_BASE_VALUE
    for (each in this) {
        code = code * HASH_NEXT_VALUE + each.hashOf()
    }
    return code
}

@FrameworkDsl
inline fun Map<*, *>.hashOf(): Int {
    if (isExhausted()) {
        return HASH_BASE_VALUE
    }
    var code = HASH_BASE_VALUE
    for (each in this) {
        code = code * HASH_NEXT_VALUE + each.hashOf()
    }
    return code
}

@FrameworkDsl
inline fun <E : Enum<E>> E.toOrdinalLong(): Long = ordinal.longOf()

open class MercenaryExceptiion @FrameworkDsl constructor(text: String?, root: Throwable?) : RuntimeException(text, root) {

    @FrameworkDsl
    constructor() : this(null, null)

    @FrameworkDsl
    constructor(text: String) : this(text, null)

    @FrameworkDsl
    constructor(root: Throwable) : this(root.message, root)

    companion object {

        private const val serialVersionUID = 2L
    }
}

open class MercenaryFatalExceptiion @FrameworkDsl constructor(text: String?, root: Throwable?) : RuntimeException(text, root) {

    @FrameworkDsl
    constructor() : this(null, null)

    @FrameworkDsl
    constructor(text: String) : this(text, null)

    @FrameworkDsl
    constructor(root: Throwable) : this(root.message, root)

    companion object {

        private const val serialVersionUID = 2L
    }
}

open class MercenaryAssertionExceptiion @FrameworkDsl constructor(text: String?, root: Throwable?) : AssertionError(text, root) {

    @FrameworkDsl
    constructor(text: String) : this(text, null)

    @FrameworkDsl
    constructor(root: Throwable) : this(root.message, root)

    companion object {

        private const val serialVersionUID = 2L
    }
}

@FrameworkDsl
fun java.security.MessageDigest.proxyOf() = Digests.proxyOf(this)

@FrameworkDsl
inline fun SystemProperties.toStringDictionary() = StringDictionary(this)

@FrameworkDsl
fun SystemProperties.mapTo(): Map<String, String> {
    if (isExhausted()) {
        return toMapOf()
    }
    val look = stringPropertyNames().filterNotNull().unique()
    if (look.isExhausted()) {
        return toMapOf()
    }
    val list = BasicArrayList<Pair<String, String>>(look.sizeOf())
    for (name in look) {
        val prop = getProperty(name)
        if (prop != null) {
            list.add(name to prop)
        }
    }
    return list.mapTo()
}

@FrameworkDsl
fun Int.toListCapacity(): Int = if (isNegative()) DEFAULT_LIST_CAPACITY else minOf(Int.MAX_VALUE - 1)

@FrameworkDsl
fun Double.toMapFactorOrElse(value: Double = DEFAULT_MAP_FACTOR): Float = toFiniteOrElse(value.toFiniteOrElse(DEFAULT_MAP_FACTOR)).toFloat()

@FrameworkDsl
fun <K, V, T : MutableMap<in K, in V>> T.append(k: K, v: V): T {
    return append(toMapOf(k, v))
}

@FrameworkDsl
fun <K, V, T : MutableMap<in K, in V>> T.append(args: Pair<K, V>): T {
    return append(toMapOf(args))
}

@FrameworkDsl
fun <K, V, T : MutableMap<in K, in V>> T.append(vararg args: Pair<K, V>): T {
    if (args.isNotExhausted()) {
        return append(args.mapTo())
    }
    return this
}

@FrameworkDsl
fun <K, V, T : MutableMap<in K, in V>> T.append(args: Iterator<Pair<K, V>>): T {
    if (args.isNotExhausted()) {
        return append(args.mapTo())
    }
    return this
}

@FrameworkDsl
fun <K, V, T : MutableMap<in K, in V>> T.append(args: Iterable<Pair<K, V>>): T {
    if (args.isNotExhausted()) {
        return append(args.mapTo())
    }
    return this
}

@FrameworkDsl
fun <K, V, T : MutableMap<in K, in V>> T.append(args: Sequence<Pair<K, V>>): T {
    if (args.isNotExhausted()) {
        return append(args.mapTo())
    }
    return this
}

@FrameworkDsl
fun <K, V, T : MutableMap<in K, in V>> T.append(args: Map<out K, V>): T {
    if (args.isNotExhausted()) {
        putAll(args)
    }
    return this
}

@FrameworkDsl
inline infix fun <K> Map<out K, *>.isKeyDefined(key: K): Boolean = containsKey(key)

@FrameworkDsl
inline infix fun <K> Map<out K, *>.isKeyNotDefined(key: K): Boolean = isKeyDefined(key).isNotTrue()

@FrameworkDsl
inline fun <K, V> toMapOf(): Map<K, V> = mapOf()

@FrameworkDsl
inline fun <K, V> toMapOf(k: K, v: V): Map<K, V> = toMapOf(k to v)

@FrameworkDsl
inline fun <K, V> toMapOf(args: Pair<K, V>): Map<K, V> = mapOf(args)

@FrameworkDsl
inline fun <K, V> Map<K, V>.toPairs(): List<Pair<K, V>> = if (isExhausted()) toListOf() else entries.map { it.key to it.value }.toList()

@FrameworkDsl
inline fun <K, V> toMapOf(vararg args: Pair<K, V>): Map<K, V> = if (args.isExhausted()) toMapOf() else args.mapTo()

@FrameworkDsl
inline fun <T> toListOf(): List<T> = listOf()

@FrameworkDsl
fun <T> toListOf(args: T): List<T> = listOf(args)

@FrameworkDsl
fun <T> toListOf(vararg args: T): List<T> = if (args.isExhausted()) toListOf() else args.toList()

@FrameworkDsl
fun <T> toListOf(args: Stream<T>): List<T> = args.toList()

@FrameworkDsl
fun <T> toListOf(args: Iterable<T>): List<T> = if (args.isExhausted()) toListOf() else args.toList()

@FrameworkDsl
fun <T> toListOf(args: Iterator<T>): List<T> = if (args.isExhausted()) toListOf() else args.toList()

@FrameworkDsl
fun <T> toListOf(args: Sequence<T>): List<T> = toListOf(args.toIterator())

@FrameworkDsl
fun MessageDigestProxy.update(vararg args: ByteArray): MessageDigestProxy {
    if (args.isNotExhausted()) {
        args.forEach { buffer ->
            update(buffer)
        }
    }
    return this
}

@FrameworkDsl
fun MessageDigestProxy.update(args: List<ByteArray>): MessageDigestProxy {
    if (args.isNotExhausted()) {
        args.forEach { buffer ->
            update(buffer)
        }
    }
    return this
}

@FrameworkDsl
fun MessageDigestProxy.update(vararg args: ByteBuffer): MessageDigestProxy {
    if (args.isNotExhausted()) {
        args.forEach { buffer ->
            update(buffer)
        }
    }
    return this
}

@FrameworkDsl
fun MessageDigestProxy.update(args: Iterable<ByteBuffer>): MessageDigestProxy {
    if (args.isNotExhausted()) {
        args.forEach { buffer ->
            update(buffer)
        }
    }
    return this
}

@FrameworkDsl
fun Encoder<String, ByteArray>.toText(): Encoder<String, String> = Encoders.toText(this)

@FrameworkDsl
fun onExitOfProcess(push: Boolean = false, func: () -> Unit) {
    SecureAccess.onExitOfProcess(push, func)
}

@FrameworkDsl
fun Class<*>.isKotlinClass(): Boolean {
    return declaredAnnotations.any { it.annotationClass.java.name == KOTLIN_METAS }
}

@FrameworkDsl
fun Class<*>.toPackageName(): String = this.`package`.name

@FrameworkDsl
fun KClass<*>.toPackageName(): String = java.`package`.name

@FrameworkDsl
inline fun <reified T : Any> packageNameOf(): String = T::class.java.`package`.name

@FrameworkDsl
inline infix fun <T : Maybe> T.isType(other: Maybe): Boolean {
    if (this == null) {
        return false
    }
    if (other == null) {
        return false
    }
    return javaClass.kotlin == other.javaClass.kotlin
}

@FrameworkDsl
inline infix fun <T : Maybe> T.isTypeAssignable(other: Maybe): Boolean {
    if (this == null) {
        return false
    }
    if (other == null) {
        return false
    }
    return isType(other) || javaClass.kotlin.isInstance(other) || other.javaClass.kotlin.isInstance(this)
}

@FrameworkDsl
fun <K, V> atomicMapOf(): AtomicHashMap<K, V> {
    return AtomicHashMap(DEFAULT_MAP_CAPACITY)
}

@FrameworkDsl
fun <K, V> atomicMapOf(size: Int): AtomicHashMap<K, V> {
    return AtomicHashMap(size.maxOf(DEFAULT_MAP_CAPACITY))
}

@FrameworkDsl
fun <K, V> atomicMapOf(k: K, v: V): AtomicHashMap<K, V> {
    return atomicMapOf<K, V>().append(k, v)
}

@FrameworkDsl
fun <K, V> atomicMapOf(args: Map<K, V>): AtomicHashMap<K, V> {
    return atomicMapOf<K, V>(args.sizeOf()).append(args)
}

@FrameworkDsl
fun <K, V> atomicMapOf(args: Pair<K, V>): AtomicHashMap<K, V> {
    return atomicMapOf<K, V>().append(args)
}

@FrameworkDsl
fun <K, V> atomicMapOf(vararg args: Pair<K, V>): AtomicHashMap<K, V> {
    return atomicMapOf<K, V>(args.sizeOf()).append(args.toIterator())
}

@FrameworkDsl
fun <K, V> Map<K, V>.toAtomic(): AtomicHashMap<K, V> {
    return if (this is AtomicHashMap) this else atomicMapOf(this)
}

@FrameworkDsl
fun <K, V> AtomicHashMap<K, V>.toMap(): Map<K, V> {
    return if (isNotExhausted()) BasicLinkedMap(this).toMap() else toMapOf()
}

@FrameworkDsl
fun <K, V> AtomicHashMap<K, V>.copyOf(): AtomicHashMap<K, V> {
    return atomicMapOf(this)
}

@FrameworkDsl
fun <K, V> AtomicHashMap<K, V>.computed(hash: K, threshold: Int, block: Convert<K, V>): V {
    val save = computeIfAbsent(hash, block)
    val most = threshold.boxIn(0, DEFAULT_MAP_CAPACITY + threshold.maxOf(DEFAULT_LRU_THRESHOLD))
    if (sizeOf() > most) {
        entries.drop(most)
    }
    return save
}

@FrameworkDsl
fun <E> List<E>.whenNotEmpty(block: (List<E>) -> Unit) {
    if (isNotExhausted()) block(this)
}

@FrameworkDsl
fun <E, T : MutableList<E>> T.push(args: E): T {
    add(0, args)
    return this
}

@FrameworkDsl
fun <E, T : MutableList<E>> T.append(args: E): T {
    add(args)
    return this
}

@FrameworkDsl
fun <E, T : MutableList<E>> T.append(vararg args: E): T {
    if (args.isNotExhausted()) {
        addAll(args.toCollection())
    }
    return this
}

@FrameworkDsl
fun <E, T : MutableList<E>> T.append(args: Iterator<E>): T {
    if (args.isNotExhausted()) {
        addAll(args.toCollection())
    }
    return this
}

@FrameworkDsl
fun <E, T : MutableList<E>> T.append(args: Iterable<E>): T {
    if (args.isNotExhausted()) {
        addAll(args.toCollection())
    }
    return this
}

@FrameworkDsl
fun <E, T : MutableList<E>> T.append(args: Sequence<E>): T {
    if (args.isNotExhausted()) {
        addAll(args.toCollection())
    }
    return this
}

@FrameworkDsl
fun getCurrentThreadName(): String = Thread.currentThread().name

@FrameworkDsl
inline fun getProcessors(): Int = Runtime.getRuntime().availableProcessors()

@FrameworkDsl
inline fun CharSequence.isExhausted(): Boolean = sizeOf() == 0

@FrameworkDsl
inline fun CharSequence.isNotExhausted(): Boolean = sizeOf() > 0

@FrameworkDsl
inline fun CharSequence.isEmptyOrBlank(): Boolean = isExhausted() || isBlank()

@FrameworkDsl
inline fun CharSequence.isNotEmptyOrBlank(): Boolean = isNotExhausted() && isNotBlank()

@FrameworkDsl
fun toTrimOrNull(data: CharSequence?): String? {
    return when (data == null) {
        true -> null
        else -> data.toString().trim().let { look ->
            when (look.isNotExhausted()) {
                true -> look
                else -> null
            }
        }
    }
}

@FrameworkDsl
fun toTrimOrElse(data: CharSequence?, other: String = EMPTY_STRING): String = toTrimOrNull(data) ?: other

@FrameworkDsl
inline fun toTrimOrElse(data: CharSequence?, other: Factory<String>): String = toTrimOrNull(data) ?: other.create()

@FrameworkDsl
inline fun CharSequence?.toTrimOr(other: String = EMPTY_STRING): String = toTrimOrNull(this) ?: other

@FrameworkDsl
inline fun CharSequence?.toTrimOr(other: Factory<String>): String = toTrimOrNull(this) ?: other.create()

@FrameworkDsl
inline fun String.head(many: Int = 1): String = if (many >= 0) drop(many) else this

@FrameworkDsl
inline fun String.tail(many: Int = 1): String = if (many >= 0) dropLast(many) else this

@FrameworkDsl
fun String.filter(pads: Char = SPACE_LETTER, trim: Boolean = true): String = when (trim.isNotTrue()) {
    true -> this
    else -> when (pads.isWhitespace()) {
        true -> trim()
        else -> trim {
            it == pads
        }
    }
}

@FrameworkDsl
fun String.padout(many: Int, pads: Char = SPACE_LETTER): String = if (many <= 0) this else padStart((length + many) / 2, pads).padEnd(many, pads)

@FrameworkDsl
fun String.center(many: Int, pads: Char = SPACE_LETTER, trim: Boolean = true): String = if (many <= 0) this else filter(pads, trim).padout(many, pads)

@FrameworkDsl
inline fun CharSequence.isZeroCharPresent(): Boolean = any { it == Char.MIN_VALUE }

@FrameworkDsl
inline fun CharSequence.isZeroCharNotPresent(): Boolean = none { it == Char.MIN_VALUE }

@FrameworkDsl
fun CharSequence.toChecked(): String {
    return if (isZeroCharPresent())
        throw MercenaryFatalExceptiion("null byte present. there are no known legitimate use cases for such data, but several injection attacks may use it.")
    else toString()
}

@FrameworkDsl
fun CharArray.toSecureString(copy: Boolean = true): SecureString = SecureString(toCharArray(copy))

@FrameworkDsl
fun CharSequence.toSecureString(): SecureString = SecureString(toString())

@FrameworkDsl
fun ByteArray.toSecureByteArray(copy: Boolean = true): SecureByteArray = SecureByteArray(toByteArray(copy))

@FrameworkDsl
fun ByteArray.toCharArray(charset: Charset = Charsets.UTF_8, copy: Boolean = true): CharArray = toByteArray(copy).toString(charset).toCharArray()

@FrameworkDsl
fun CharSequence.toSecureByteArray(): SecureByteArray = SecureByteArray(toString())

@FrameworkDsl
fun CharSequence.toLowerTrim(): String = if (isExhausted()) EMPTY_STRING else trim().toString().toLowerCase()

@FrameworkDsl
fun CharSequence.toUpperTrim(): String = if (isExhausted()) EMPTY_STRING else trim().toString().toUpperCase()

@FrameworkDsl
fun CharSequence.toLowerTrimEnglish(): String = if (isExhausted()) EMPTY_STRING else trim().toLowerCaseEnglish()

@FrameworkDsl
fun CharSequence.toUpperTrimEnglish(): String = if (isExhausted()) EMPTY_STRING else trim().toUpperCaseEnglish()

@FrameworkDsl
fun CharSequence.toLowerCaseEnglish(): String = if (isExhausted()) EMPTY_STRING else toString().toLowerCase(Locale.ENGLISH)

@FrameworkDsl
fun CharSequence.toUpperCaseEnglish(): String = if (isExhausted()) EMPTY_STRING else toString().toUpperCase(Locale.ENGLISH)

@FrameworkDsl
inline fun CharSequence.toCharArray(copy: Boolean): CharArray {
    if (isExhausted()) {
        return EMPTY_CHAR_ARRAY
    }
    return toString().toCharArray().toCharArray(copy)
}

@FrameworkDsl
fun <T, R : Any> List<T>.whenNotNull(block: Convert<T, R?>): List<R> {
    if (isNotExhausted()) {
        val list = BasicArrayList<R>(sizeOf())
        for (item in this) {
            val data = block.convert(item)
            if (data != null) {
                list.add(data)
            }
        }
        return list.toList()
    }
    return toListOf()
}

@FrameworkDsl
fun <T : Any> ThreadLocal<T>.toValue(): T = get()

@FrameworkDsl
inline fun Validated.isNotValid(): Boolean = !isValid()

@FrameworkDsl
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

@FrameworkDsl
inline fun isValid(block: LazyMessage): Boolean = try {
    isValid(block.create())
} catch (cause: Throwable) {
    Throwables.thrown(cause)
    false
}

@FrameworkDsl
inline fun AtomicLong.putValue(value: Long) {
    set(value)
}

@FrameworkDsl
inline fun AtomicLong.putValue(value: Int) {
    putValue(value.longOf())
}

@FrameworkDsl
inline fun AtomicLong.putValue(value: AtomicLong) {
    putValue(value.getValue())
}

@FrameworkDsl
inline fun AtomicLong.putValue(value: AtomicInteger) {
    putValue(value.getValue())
}

@FrameworkDsl
fun Long.toAtomic(): AtomicLong = AtomicLong(this)

@FrameworkDsl
fun AtomicLong.toAtomic(): AtomicLong = getValue().toAtomic()

@FrameworkDsl
fun AtomicLong.copyOf(): AtomicLong = toAtomic()

@FrameworkDsl
operator fun AtomicLong.div(value: Int): AtomicLong {
    value.toValidDivisor()
    updateAndGet { it / value }
    return this
}

@FrameworkDsl
operator fun AtomicLong.times(value: Int): AtomicLong {
    updateAndGet { it * value }
    return this
}

@FrameworkDsl
operator fun AtomicLong.plus(value: Int): AtomicLong {
    updateAndGet { it + value }
    return this
}

@FrameworkDsl
operator fun AtomicLong.minus(value: Int): AtomicLong {
    updateAndGet { it - value }
    return this
}

@FrameworkDsl
operator fun AtomicLong.div(value: Long): AtomicLong {
    value.toValidDivisor()
    updateAndGet { it / value }
    return this
}

@FrameworkDsl
operator fun AtomicLong.times(value: Long): AtomicLong {
    updateAndGet { it * value }
    return this
}

@FrameworkDsl
operator fun AtomicLong.plus(value: Long): AtomicLong {
    updateAndGet { it + value }
    return this
}

@FrameworkDsl
operator fun AtomicLong.minus(value: Long): AtomicLong {
    updateAndGet { it - value }
    return this
}

@FrameworkDsl
inline fun AtomicLong.increment(): AtomicLong {
    getAndIncrement()
    return this
}

@FrameworkDsl
operator fun AtomicLong.inc(): AtomicLong {
    return increment()
}

@FrameworkDsl
inline fun AtomicLong.decrement(): AtomicLong {
    getAndDecrement()
    return this
}

@FrameworkDsl
operator fun AtomicLong.dec(): AtomicLong {
    return decrement()
}

@FrameworkDsl
operator fun AtomicLong.compareTo(value: Int): Int = getValue().compareTo(value)

@FrameworkDsl
operator fun AtomicLong.compareTo(value: Long): Int = getValue().compareTo(value)

@FrameworkDsl
operator fun AtomicLong.compareTo(value: AtomicLong): Int = getValue().compareTo(value.getValue())

@FrameworkDsl
operator fun AtomicLong.compareTo(value: AtomicInteger): Int = getValue().compareTo(value.getValue())

@FrameworkDsl
infix fun AtomicLong.maxOf(value: Int): AtomicLong {
    if (value > getValue()) {
        putValue(value)
    }
    return this
}

@FrameworkDsl
infix fun AtomicLong.maxOf(value: AtomicInteger): AtomicLong {
    if (value.getValue() > getValue()) {
        putValue(value)
    }
    return this
}

@FrameworkDsl
infix fun AtomicLong.minOf(value: Long): AtomicLong {
    if (value < getValue()) {
        putValue(value)
    }
    return this
}

@FrameworkDsl
infix fun AtomicLong.minOf(value: AtomicLong): AtomicLong {
    if (value.getValue() < getValue()) {
        putValue(value)
    }
    return this
}

@FrameworkDsl
infix fun AtomicLong.maxOf(value: Long): AtomicLong {
    if (value > getValue()) {
        putValue(value)
    }
    return this
}

@FrameworkDsl
infix fun AtomicLong.maxOf(value: AtomicLong): AtomicLong {
    if (value.getValue() > getValue()) {
        putValue(value)
    }
    return this
}

@FrameworkDsl
infix fun AtomicLong.minOf(value: Int): AtomicLong {
    if (value < getValue()) {
        putValue(value)
    }
    return this
}

@FrameworkDsl
infix fun AtomicLong.minOf(value: AtomicInteger): AtomicLong {
    if (value.getValue() < getValue()) {
        putValue(value)
    }
    return this
}


@FrameworkDsl
fun AtomicLong.toBigInteger(): BigInteger = getValue().toBigInteger()

@FrameworkDsl
fun AtomicLong.isNegative(): Boolean {
    return getValue() < 0
}

@FrameworkDsl
fun AtomicLong.isEven(): Boolean {
    return getValue().isEven()
}

@FrameworkDsl
fun AtomicLong.isNotEven(): Boolean {
    return getValue().isNotEven()
}

@FrameworkDsl
fun AtomicLong.setValue(value: Int): AtomicLong {
    return setValue(value.longOf())
}

@FrameworkDsl
inline fun AtomicLong.setValue(value: Long): AtomicLong {
    set(value)
    return this
}

@FrameworkDsl
fun AtomicLong.setValue(value: AtomicLong): AtomicLong {
    return setValue(value.getValue())
}

@FrameworkDsl
fun AtomicLong.setValue(value: AtomicInteger): AtomicLong {
    return setValue(value.getValue())
}

@FrameworkDsl
inline fun AtomicLong.getValue(): Long = get()

@FrameworkDsl
fun Int.toAtomic(): AtomicInteger = AtomicInteger(this)

@FrameworkDsl
fun AtomicInteger.toAtomic(): AtomicInteger = getValue().toAtomic()

@FrameworkDsl
fun AtomicInteger.copyOf(): AtomicInteger = toAtomic()

@FrameworkDsl
operator fun AtomicInteger.div(value: Int): AtomicInteger {
    value.toValidDivisor()
    updateAndGet { it / value }
    return this
}

@FrameworkDsl
operator fun AtomicInteger.times(value: Int): AtomicInteger {
    updateAndGet { it * value }
    return this
}

@FrameworkDsl
operator fun AtomicInteger.plus(value: Int): AtomicInteger {
    updateAndGet { it + value }
    return this
}

@FrameworkDsl
operator fun AtomicInteger.minus(value: Int): AtomicInteger {
    updateAndGet { it - value }
    return this
}

@FrameworkDsl
fun AtomicInteger.increment(): AtomicInteger {
    getAndIncrement()
    return this
}

@FrameworkDsl
operator fun AtomicInteger.inc(): AtomicInteger {
    return increment()
}

@FrameworkDsl
fun AtomicInteger.decrement(): AtomicInteger {
    getAndDecrement()
    return this
}

@FrameworkDsl
operator fun AtomicInteger.dec(): AtomicInteger {
    return decrement()
}

@FrameworkDsl
infix fun AtomicInteger.maxOf(value: Int): AtomicInteger {
    if (value > getValue()) {
        return setValue(value)
    }
    return this
}

@FrameworkDsl
infix fun AtomicInteger.maxOf(value: AtomicInteger): AtomicInteger {
    if (value.getValue() > getValue()) {
        return setValue(value)
    }
    return this
}

@FrameworkDsl
infix fun AtomicInteger.minOf(value: Int): AtomicInteger {
    if (value < getValue()) {
        return setValue(value)
    }
    return this
}

@FrameworkDsl
infix fun AtomicInteger.minOf(value: AtomicInteger): AtomicInteger {
    if (value.getValue() < getValue()) {
        reset(value)
    }
    return this
}

@FrameworkDsl
inline fun AtomicInteger.reset(value: Int = 0) {
    set(value)
}

@FrameworkDsl
inline fun AtomicInteger.reset(value: AtomicInteger) {
    reset(value.getValue())
}

@FrameworkDsl
inline fun AtomicInteger.advance(value: Int = 1) {
    set(get() + value)
}

@FrameworkDsl
inline fun AtomicInteger.setValue(value: Int): AtomicInteger {
    set(value)
    return this
}

@FrameworkDsl
fun AtomicInteger.setValue(value: AtomicInteger): AtomicInteger {
    return setValue(value.getValue())
}

@FrameworkDsl
inline fun AtomicInteger.getValue(): Int = get()

@FrameworkDsl
fun AtomicInteger.toBigInteger(): BigInteger = getValue().toBigInteger()

@FrameworkDsl
fun AtomicInteger.isNegative(): Boolean {
    return getValue().isNegative()
}

@FrameworkDsl
fun AtomicInteger.isEven(): Boolean {
    return getValue().isEven()
}

@FrameworkDsl
fun AtomicInteger.isNotEven(): Boolean {
    return getValue().isNotEven()
}

@FrameworkDsl
operator fun AtomicInteger.compareTo(value: Int): Int = getValue().compareTo(value)

@FrameworkDsl
operator fun AtomicInteger.compareTo(value: AtomicInteger): Int = getValue().compareTo(value.getValue())

@FrameworkDsl
inline fun getAtomicTrue(): AtomicBoolean = true.toAtomic()

@FrameworkDsl
inline fun getAtomicNotTrue(): AtomicBoolean = false.toAtomic()

@FrameworkDsl
inline fun Boolean.toAtomic() = AtomicBoolean(this)

@FrameworkDsl
inline fun Boolean.isTrue(): Boolean = this

@FrameworkDsl
inline fun Boolean.toBoolean(): Boolean = this

@FrameworkDsl
inline fun Boolean.isNotTrue(): Boolean = !this

@FrameworkDsl
fun AtomicBoolean.copyOf(): AtomicBoolean = toAtomic()

@FrameworkDsl
fun AtomicBoolean.toAtomic(): AtomicBoolean = toBoolean().toAtomic()

@FrameworkDsl
inline fun AtomicBoolean.setAtomic(value: Boolean): Boolean = isUpdateTo(isAtomic(), value)

@FrameworkDsl
inline fun AtomicBoolean.setAtomic(value: AtomicBoolean): Boolean = setAtomic(value.isAtomic())

@FrameworkDsl
inline fun AtomicBoolean.isAtomic(): Boolean = get()

@FrameworkDsl
inline fun AtomicBoolean.toBoolean(): Boolean = get()

@FrameworkDsl
inline fun AtomicBoolean.isTrue(): Boolean = get()

@FrameworkDsl
inline fun AtomicBoolean.isNotTrue(): Boolean = !get()

@FrameworkDsl
fun AtomicBoolean.isTrueToFalse(): Boolean = isUpdateTo(true.toBoolean(), false.toBoolean())

@FrameworkDsl
fun AtomicBoolean.isFalseToTrue(): Boolean = isUpdateTo(false.toBoolean(), true.toBoolean())

@FrameworkDsl
fun AtomicBoolean.isUpdateTo(update: Boolean): Boolean = compareAndSet(toBoolean(), update)

@FrameworkDsl
fun AtomicBoolean.isUpdateTo(expect: Boolean, update: Boolean): Boolean = compareAndSet(expect, update)

@FrameworkDsl
fun AtomicBoolean.isUpdateTo(expect: Boolean, update: AtomicBoolean): Boolean = isUpdateTo(expect.toBoolean(), update.toBoolean())

@FrameworkDsl
fun AtomicBoolean.isUpdateTo(expect: AtomicBoolean, update: Boolean): Boolean = isUpdateTo(expect.toBoolean(), update.toBoolean())

@FrameworkDsl
fun AtomicBoolean.isUpdateTo(expect: AtomicBoolean, update: AtomicBoolean): Boolean = isUpdateTo(expect.toBoolean(), update.toBoolean())

@FrameworkDsl
fun AtomicBoolean.toFlip(): AtomicBoolean {
    val last = isTrue()
    val next = isNotTrue()
    isUpdateTo(last, next)
    return this
}

@FrameworkDsl
fun AtomicBoolean.toTrue(): AtomicBoolean {
    isFalseToTrue()
    return this
}

@FrameworkDsl
fun AtomicBoolean.toNotTrue(): AtomicBoolean {
    isTrueToFalse()
    return this
}

@FrameworkDsl
inline operator fun AtomicBoolean.not(): Boolean = !get()

@FrameworkDsl
inline infix fun Boolean.or(value: AtomicBoolean): Boolean = toBoolean() || value.toBoolean()

@FrameworkDsl
inline infix fun AtomicBoolean.or(value: Boolean): Boolean = toBoolean() || value.toBoolean()

@FrameworkDsl
inline infix fun AtomicBoolean.or(value: AtomicBoolean): Boolean = toBoolean() || value.toBoolean()

@FrameworkDsl
inline infix fun Boolean.and(value: AtomicBoolean): Boolean = toBoolean() && value.toBoolean()

@FrameworkDsl
inline infix fun AtomicBoolean.and(value: Boolean): Boolean = toBoolean() && value.toBoolean()

@FrameworkDsl
inline infix fun AtomicBoolean.and(value: AtomicBoolean): Boolean = toBoolean() && value.toBoolean()

@FrameworkDsl
inline infix fun Boolean.xor(value: AtomicBoolean): Boolean = toBoolean() xor value.toBoolean()

@FrameworkDsl
inline infix fun AtomicBoolean.xor(value: Boolean): Boolean = toBoolean() xor value.toBoolean()

@FrameworkDsl
inline infix fun AtomicBoolean.xor(value: AtomicBoolean): Boolean = toBoolean() xor value.toBoolean()

@FrameworkDsl
inline fun Path.hashOf(): Int = fileOf().hashCode()

@FrameworkDsl
inline fun Map.Entry<*, *>.hashOf(): Int = key.hashOf() xor value.hashOf()

@FrameworkDsl
inline fun Array<*>.hashOf(): Int = if (sizeOf() == 0) HASH_BASE_VALUE else toIterator().hashOf()

@FrameworkDsl
inline fun IntArray.hashOf(): Int = if (sizeOf() == 0) HASH_BASE_VALUE else contentHashCode()

@FrameworkDsl
inline fun ByteArray.hashOf(): Int = if (sizeOf() == 0) HASH_BASE_VALUE else contentHashCode()

@FrameworkDsl
inline fun CharArray.hashOf(): Int = if (sizeOf() == 0) HASH_BASE_VALUE else contentHashCode()

@FrameworkDsl
inline fun LongArray.hashOf(): Int = if (sizeOf() == 0) HASH_BASE_VALUE else contentHashCode()

@FrameworkDsl
inline fun ShortArray.hashOf(): Int = if (sizeOf() == 0) HASH_BASE_VALUE else contentHashCode()

@FrameworkDsl
inline fun FloatArray.hashOf(): Int = if (sizeOf() == 0) HASH_BASE_VALUE else contentHashCode()

@FrameworkDsl
inline fun DoubleArray.hashOf(): Int = if (sizeOf() == 0) HASH_BASE_VALUE else contentHashCode()

@FrameworkDsl
inline fun BooleanArray.hashOf(): Int = if (sizeOf() == 0) HASH_BASE_VALUE else contentHashCode()

@FrameworkDsl
inline fun <T : Any?> T.hashOf() = if (this == null) HASH_NULL_VALUE else SameAndHashCode.hashOf(this)

@FrameworkDsl
fun <T : Any?> T.hashOf(vararg args: Any?) = SameAndHashCode.hashOf(hashOf().toAtomic(), *args)

@FrameworkDsl
inline fun <T : Any?> T.idenOf() = if (this == null) HASH_NULL_VALUE else SameAndHashCode.idenOf(this)

@FrameworkDsl
inline infix fun <B> String.to(that: B): Pair<String, B> = Pair(this, that)

@FrameworkDsl
fun <V> dictOf(): Dictionary<V> = toMapOf()

@FrameworkDsl
fun <V> dictOf(vararg args: Pair<String, V>): Dictionary<V> = toMapOf(*args)

@FrameworkDsl
fun <V> dictOfMutable(): MutableDictionary<V> = BasicDictionaryMap()

@FrameworkDsl
fun <V> dictOfMutable(vararg args: Pair<String, V>): MutableDictionary<V> = BasicDictionaryMap(args.toIterable())

@FrameworkDsl
inline fun stringBuilderOf(size: Int = DEFAULT_STRINGOF_CAPACITY) = StringBuilder(size.maxOf(0))

@FrameworkDsl
inline fun stringBuilderOf(data: String) = StringBuilder(data)

@FrameworkDsl
inline fun stringBuilderOf(data: CharSequence) = StringBuilder(data)

@FrameworkDsl
inline fun StringBuilder.build(action: StringBuilder.() -> Unit): String = apply(action).toString()

@FrameworkDsl
inline fun stringOf(size: Int = DEFAULT_STRINGOF_CAPACITY, action: StringBuilder.() -> Unit): String = StringBuilder(size.maxOf(0)).build(action)

@FrameworkDsl
inline fun stringOf(data: String, action: StringBuilder.() -> Unit): String = StringBuilder(data).build(action)

@FrameworkDsl
inline fun stringOf(data: CharSequence, action: StringBuilder.() -> Unit): String = StringBuilder(data).build(action)

@FrameworkDsl
inline fun StringBuilder.add(data: Int): StringBuilder = append(data)

@FrameworkDsl
inline fun StringBuilder.add(data: Char): StringBuilder = append(data)

@FrameworkDsl
inline fun StringBuilder.add(data: Long): StringBuilder = append(data)

@FrameworkDsl
inline fun StringBuilder.add(data: CharArray): StringBuilder = append(data)

@FrameworkDsl
fun StringBuilder.add(data: IntProgression): StringBuilder {
    if (data.isNotExhausted()) {
        data.forEach {
            append(it)
        }
    }
    return this
}

@FrameworkDsl
fun StringBuilder.add(data: CharProgression): StringBuilder {
    if (data.isNotExhausted()) {
        data.forEach {
            append(it)
        }
    }
    return this
}

@FrameworkDsl
fun StringBuilder.add(data: LongProgression): StringBuilder {
    if (data.isNotExhausted()) {
        data.forEach {
            append(it)
        }
    }
    return this
}

@FrameworkDsl
inline fun Char.toCode(): Int = toInt()

@FrameworkDsl
inline fun Byte.toCode(): Int = toInt()

@FrameworkDsl
inline infix fun Int.mask(bits: Int): Int = if (bits == 0) (this and 0xF) else ((this shr bits) and 0xF)

@FrameworkDsl
fun StringBuilder.encode(data: Int): StringBuilder {
    data.toHexString().toUpperCaseEnglish().let { buff ->
        when ((4 - buff.sizeOf()).boxIn(0, 3)) {
            0 -> add("\\u", buff)
            1 -> add("\\u0", buff)
            2 -> add("\\u00", buff)
            3 -> add("\\u000", buff)
            else -> add(EMPTY_STRING)
        }
    }
    return this
}

@FrameworkDsl
fun StringBuilder.escape(data: Char): StringBuilder {
    return add(Escapers.ESCAPE_SLASH).add(data)
}

@FrameworkDsl
fun StringBuilder.push(data: Char): StringBuilder {
    insert(0, data)
    return this
}

@FrameworkDsl
fun StringBuilder.push(data: String): StringBuilder {
    insert(0, data)
    return this
}

@FrameworkDsl
fun StringBuilder.push(data: CharSequence): StringBuilder {
    insert(0, data.toString())
    return this
}

@FrameworkDsl
fun StringBuilder.wrap(data: Char): StringBuilder {
    return push(data).add(data)
}

@FrameworkDsl
fun StringBuilder.wrap(data: String): StringBuilder {
    return push(data).add(data)
}

@FrameworkDsl
fun StringBuilder.wrap(data: CharSequence): StringBuilder {
    return wrap(data.toString())
}

@FrameworkDsl
inline fun StringBuilder.add(vararg args: Any?): StringBuilder = append(*args)

@FrameworkDsl
inline fun StringBuilder.add(vararg args: String?): StringBuilder = append(*args)

@FrameworkDsl
inline fun StringBuilder.newline(): StringBuilder = add(BREAK_STRING)

@FrameworkDsl
fun <T : Any> T.nameOf(simple: Boolean = false): String = if (simple) SameAndHashCode.simpleNameOf(this) else SameAndHashCode.nameOf(this)

@FrameworkDsl
inline fun CharSequence.sizeOf(): Int = length

@FrameworkDsl
inline fun Array<*>.sizeOf(): Int = size

@FrameworkDsl
inline fun IntArray.sizeOf(): Int = size

@FrameworkDsl
inline fun ByteArray.sizeOf(): Int = size

@FrameworkDsl
inline fun CharArray.sizeOf(): Int = size

@FrameworkDsl
inline fun LongArray.sizeOf(): Int = size

@FrameworkDsl
inline fun ShortArray.sizeOf(): Int = size

@FrameworkDsl
inline fun FloatArray.sizeOf(): Int = size

@FrameworkDsl
inline fun DoubleArray.sizeOf(): Int = size

@FrameworkDsl
inline fun BooleanArray.sizeOf(): Int = size

@FrameworkDsl
inline fun Collection<*>.sizeOf(): Int = size

@FrameworkDsl
inline fun Map<*, *>.sizeOf(): Int = size

@FrameworkDsl
operator fun <T> Set<T>.get(index: Int): T = elementAt(index)

@FrameworkDsl
operator fun <T> Collection<T>.get(index: Int): T = elementAt(index)

@FrameworkDsl
infix fun Array<*>.isSameArrayAs(args: Array<*>): Boolean = sizeOf() == args.sizeOf() && SameAndHashCode.isSameAs(this, args)

@FrameworkDsl
infix fun IntArray.isSameArrayAs(args: IntArray): Boolean = sizeOf() == args.sizeOf() && this contentEquals args

@FrameworkDsl
infix fun ByteArray.isSameArrayAs(args: ByteArray): Boolean = sizeOf() == args.sizeOf() && this contentEquals args

@FrameworkDsl
infix fun CharArray.isSameArrayAs(args: CharArray): Boolean = sizeOf() == args.sizeOf() && this contentEquals args

@FrameworkDsl
infix fun LongArray.isSameArrayAs(args: LongArray): Boolean = sizeOf() == args.sizeOf() && this contentEquals args

@FrameworkDsl
infix fun ShortArray.isSameArrayAs(args: ShortArray): Boolean = sizeOf() == args.sizeOf() && this contentEquals args

@FrameworkDsl
infix fun FloatArray.isSameArrayAs(args: FloatArray): Boolean = sizeOf() == args.sizeOf() && this contentEquals args

@FrameworkDsl
infix fun DoubleArray.isSameArrayAs(args: DoubleArray): Boolean = sizeOf() == args.sizeOf() && this contentEquals args

@FrameworkDsl
infix fun BooleanArray.isSameArrayAs(args: BooleanArray): Boolean = sizeOf() == args.sizeOf() && this contentEquals args

@FrameworkDsl
infix fun Array<*>.isNotSameArrayAs(args: Array<*>): Boolean = this.isSameArrayAs(args).isNotTrue()

@FrameworkDsl
infix fun IntArray.isNotSameArrayAs(args: IntArray): Boolean = this.isSameArrayAs(args).isNotTrue()

@FrameworkDsl
infix fun ByteArray.isNotSameArrayAs(args: ByteArray): Boolean = this.isSameArrayAs(args).isNotTrue()

@FrameworkDsl
infix fun CharArray.isNotSameArrayAs(args: CharArray): Boolean = this.isSameArrayAs(args).isNotTrue()

@FrameworkDsl
infix fun LongArray.isNotSameArrayAs(args: LongArray): Boolean = this.isSameArrayAs(args).isNotTrue()

@FrameworkDsl
infix fun ShortArray.isNotSameArrayAs(args: ShortArray): Boolean = this.isSameArrayAs(args).isNotTrue()

@FrameworkDsl
infix fun FloatArray.isNotSameArrayAs(args: FloatArray): Boolean = this.isSameArrayAs(args).isNotTrue()

@FrameworkDsl
infix fun DoubleArray.isNotSameArrayAs(args: DoubleArray): Boolean = this.isSameArrayAs(args).isNotTrue()

@FrameworkDsl
infix fun BooleanArray.isNotSameArrayAs(args: BooleanArray): Boolean = this.isSameArrayAs(args).isNotTrue()

@FrameworkDsl
inline fun CharSequence.hashOf(): Int = if (sizeOf() == 0) HASH_NULL_VALUE else toString().hashCode()

@FrameworkDsl
inline fun Double.hashOf(): Int = if (this == 0.0) HASH_NULL_VALUE else hashCode()

@FrameworkDsl
inline fun Alive.isNotAlive(): Boolean = isAlive().isNotTrue()

@FrameworkDsl
inline fun Iterator<String>.toNoEmptyChars(): List<String> {
    return toList().filter { it.isZeroCharNotPresent() }
}

open class MercenarySequence<out T> @FrameworkDsl constructor(private val iterator: Iterator<T>) : Sequence<T> {

    @FrameworkDsl
    override operator fun iterator(): Iterator<T> = iterator

    @FrameworkDsl
    constructor() : this(toListOf())

    @FrameworkDsl
    constructor(vararg source: T) : this(source.toIterator())

    @FrameworkDsl
    constructor(source: Stream<T>) : this(source.toIterator())

    @FrameworkDsl
    constructor(source: Iterable<T>) : this(source.toIterator())

    @FrameworkDsl
    constructor(source: Sequence<T>) : this(source.toIterator())
}

class MercenaryConstrainedSequence<out T> @FrameworkDsl constructor(source: Iterator<T>) : MercenarySequence<T>(source), Sequence<T>

@FrameworkDsl
inline fun <T> Sequence<T>.constrain(): Sequence<T> {
    return if (this is MercenaryConstrainedSequence) this else MercenaryConstrainedSequence(constrainOnce().toIterator())
}

@FrameworkDsl
inline fun <T> Array<T>.toIterator(): Iterator<T> = iterator()

@FrameworkDsl
inline fun <T> Stream<T>.toIterator(): Iterator<T> = iterator()

@FrameworkDsl
inline fun <T> Sequence<T>.toIterator(): Iterator<T> = iterator()

@FrameworkDsl
inline fun <T> Iterator<T>.toIterator(): Iterator<T> = iterator()

@FrameworkDsl
inline fun <T> Iterable<T>.toIterator(): Iterator<T> = iterator()

@FrameworkDsl
inline fun <T> Enumeration<T>.toIterator(): Iterator<T> = iterator()

@FrameworkDsl
inline fun IntStream.toIterator(): Iterator<Int> = iterator()

@FrameworkDsl
inline fun LongStream.toIterator(): Iterator<Long> = iterator()

@FrameworkDsl
inline fun DoubleStream.toIterator(): Iterator<Double> = iterator()

@FrameworkDsl
inline fun <K, V> Map<out K, V>.toIterator(): Iterator<Map.Entry<K, V>> = iterator()

@FrameworkDsl
inline fun <T> Iterator<T>.toCollection(): Collection<T> = toListOf(this)

@FrameworkDsl
inline fun <T> Array<T>.toCollection(): Collection<T> = toList()

@FrameworkDsl
inline fun <T> Sequence<T>.toCollection(): Collection<T> = toList()

@FrameworkDsl
inline fun <T> Iterable<T>.toCollection(): Collection<T> = if (this is Collection) this else toList()

@FrameworkDsl
fun <T> Array<T>.toIterable(): Iterable<T> = Iterable { toIterator() }

@FrameworkDsl
fun <T> Sequence<T>.toIterable(): Iterable<T> = Iterable { toIterator() }

@FrameworkDsl
fun <T> Iterator<T>.toIterable(): Iterable<T> = Iterable { toIterator() }

@FrameworkDsl
fun <T> Iterator<T>.toList(): List<T> = toIterable().toList()

@FrameworkDsl
inline fun <T> Sequence<T>.toStream(): Stream<T> = asStream()

@FrameworkDsl
fun <T> sequenceOf(): Sequence<T> = MercenarySequence()

@FrameworkDsl
fun sequenceOf(args: IntProgression): Sequence<Int> = args.toIterator().toSequence()

@FrameworkDsl
fun sequenceOf(args: LongProgression): Sequence<Long> = args.toIterator().toSequence()

@FrameworkDsl
fun sequenceOf(args: CharProgression): Sequence<Char> = args.toIterator().toSequence()

@FrameworkDsl
fun IntStream.toSequence(): Sequence<Int> = toIterator().toSequence()

@FrameworkDsl
fun LongStream.toSequence(): Sequence<Long> = toIterator().toSequence()

@FrameworkDsl
fun DoubleStream.toSequence(): Sequence<Double> = toIterator().toSequence()

@FrameworkDsl
fun <T : Any> Stream<T>.toSequence(): Sequence<T> = toIterator().toSequence()

@FrameworkDsl
fun <T : Any> Iterable<T>.toSequence(): Sequence<T> = toIterator().toSequence()

@FrameworkDsl
fun <T : Any> Iterator<T>.toSequence(): Sequence<T> = MercenarySequence(this)

@FrameworkDsl
fun <T> sequenceOf(vararg args: T): Sequence<T> = MercenarySequence(args.toIterator())

@FrameworkDsl
fun <T : Any> sequenceOf(next: () -> T?): Sequence<T> = MercenarySequence(generateSequence(next))

@FrameworkDsl
fun <T : Any> sequenceOf(seed: T?, next: (T) -> T?): Sequence<T> = MercenarySequence(generateSequence(seed, next))

@FrameworkDsl
fun <T : Any> sequenceOf(seed: () -> T?, next: (T) -> T?): Sequence<T> = MercenarySequence(generateSequence(seed, next))

@FrameworkDsl
inline fun <T : Any> iteratorOf(vararg args: T): Iterator<T> = args.toIterator()

@FrameworkDsl
inline fun <T> Iterable<T>.unique(): List<T> {
    return when (this) {
        is Collection -> when (sizeOf()) {
            0 -> toListOf()
            1 -> toListOf(head())
            else -> LinkedHashSet(this).toList()
        }
        else -> toHashSet().toList()
    }
}

@FrameworkDsl
inline fun <T> List<T>.unique(): List<T> {
    return when (sizeOf().isLessThan(2)) {
        true -> this
        else -> distinct()
    }
}

@FrameworkDsl
inline fun <T> Iterator<T>.unique(): List<T> = toList().unique()

@FrameworkDsl
inline fun <T> Iterable<T>.tail(): T = last()

@FrameworkDsl
inline fun <T> Iterable<T>.head(): T = first()

@FrameworkDsl
inline fun <T : Any> unique(vararg args: T): List<T> = args.toIterator().toList().distinct()

@FrameworkDsl
fun Sequence<String>.uniqueTrimmedOf(): List<String> = toIterable().uniqueTrimmedOf()

@FrameworkDsl
fun Iterable<String>.uniqueTrimmedOf(): List<String> = mapNotNull { toTrimOrNull(it) }.distinct()

@FrameworkDsl
fun <T : Comparable<T>> Iterable<T>.toSortedListOf(): List<T> = sorted()

@FrameworkDsl
fun <T : Comparable<T>> Iterable<T>.toReverseSortedListOf(): List<T> = sorted().reversed()

@FrameworkDsl
fun <T : Any> Iterator<T>.toEnumeration(): Enumeration<T> = object : Enumeration<T> {
    override fun nextElement(): T = next()
    override fun hasMoreElements(): Boolean = hasNext()
}

@FrameworkDsl
inline fun <T : Any> Iterable<T>.toEnumeration(): Enumeration<T> = toIterator().toEnumeration()

@FrameworkDsl
inline fun <T : Any> Sequence<T>.toEnumeration(): Enumeration<T> = toIterator().toEnumeration()

@FrameworkDsl
inline fun <T : Any> T?.otherwise(block: Factory<T>): T = this ?: block.create()

@FrameworkDsl
inline fun <T : Any> T?.otherwise(value: T): T = this ?: value

@FrameworkDsl
inline fun String?.otherwise(value: String = EMPTY_STRING): String = this ?: value

@FrameworkDsl
inline fun <reified T : Any> logsOf(): ILogging = LoggingFactory.logger(T::class)

@FrameworkDsl
fun mu.KLogger.getLevel(): LoggingLevel = LoggingFactory.getLevel(this)

@FrameworkDsl
fun mu.KLogger.setLevel(level: LoggingLevel) = LoggingFactory.setLevel(this, level)

@FrameworkDsl
fun mu.KLogger.withLevel(level: LoggingLevel, block: () -> Unit) {
    LoggingFactory.withLevel(this, level, block)
}

@FrameworkDsl
inline fun <T> withLoggingContext(args: Pair<String, Any>, block: () -> T): T {
    return mu.withLoggingContext(args.first to args.second.toString(), block)
}

@FrameworkDsl
inline fun <T> withLoggingContext(args: Map<String, Any>, block: () -> T): T {
    val hash = LinkedHashMap<String, String>(args.sizeOf())
    for ((k, v) in args) {
        hash[k] = v.toString()
    }
    return mu.withLoggingContext(hash, block)
}

@FrameworkDsl
inline fun <T> withLoggingContext(vararg args: Pair<String, Any>, block: () -> T): T {
    val hash = LinkedHashMap<String, String>(args.sizeOf())
    for ((k, v) in args) {
        hash[k] = v.toString()
    }
    return mu.withLoggingContext(hash, block)
}