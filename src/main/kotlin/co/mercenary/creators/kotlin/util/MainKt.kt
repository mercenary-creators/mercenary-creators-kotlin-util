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

@file:JvmName("MainKt")
@file:Suppress("NOTHING_TO_INLINE", "UNCHECKED_CAST")

package co.mercenary.creators.kotlin.util

import co.mercenary.creators.kotlin.util.collection.*
import co.mercenary.creators.kotlin.util.security.SecureAccess
import co.mercenary.creators.kotlin.util.type.ParameterizedTypeReference
import reactor.core.publisher.Flux
import java.lang.reflect.*
import java.math.BigInteger
import java.nio.ByteBuffer
import java.nio.charset.Charset
import java.nio.file.Path
import java.util.*
import java.util.concurrent.atomic.*
import java.util.stream.*
import kotlin.collections.ArrayDeque
import kotlin.reflect.*
import kotlin.streams.*

@FrameworkDsl
const val POSITIVE_ONE = 1

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
const val SUB_LETTER = '-'

@FrameworkDsl
const val ADD_LETTER = '+'

@FrameworkDsl
const val DOT_LETTER = '.'

@FrameworkDsl
const val NULLS_STRING = "null"

@FrameworkDsl
const val DUNNO_STRING = "unknown"

@FrameworkDsl
const val KOTLIN_METAS = "kotlin.Metadata"

@FrameworkDsl
const val DIGIT_STRING = "0123456789ABCDEF"

@FrameworkDsl
const val CREATORS_AUTHOR_INFO = "Dean S. Jones, Copyright (C) 2022, Mercenary Creators Company."

@FrameworkDsl
const val CREATORS_VERSION_INFO = "9.9.9-SNAPSHOT"

@FrameworkDsl
const val DEFAULT_MAP_FACTOR = 0.75

@FrameworkDsl
const val DEFAULT_MAP_FACTOR_VALUE = 0.75f

@FrameworkDsl
const val DEFAULT_SET_FACTOR_VALUE = 0.75f

@FrameworkDsl
const val DEFAULT_MAP_CAPACITY = 16

@FrameworkDsl
const val MAXIMUM_INTS_POWER_OF_2 = 1 shl 30

@FrameworkDsl
const val MAXIMUM_LONG_POWER_OF_2 = 1L shl 62

@FrameworkDsl
const val MAXIMUM_MAP_CAPACITY = MAXIMUM_INTS_POWER_OF_2

@FrameworkDsl
const val DEFAULT_SET_CAPACITY = 16

@FrameworkDsl
const val MAXIMUM_SET_CAPACITY = MAXIMUM_MAP_CAPACITY

@FrameworkDsl
const val DEFAULT_LIST_CAPACITY = 10

@FrameworkDsl
const val DEFAULT_STRINGOF_CAPACITY = 16

@FrameworkDsl
const val DEFAULT_LRU_THRESHOLD = DEFAULT_MAP_CAPACITY * 8

@FrameworkDsl
const val DEFAULT_LIST_THRESHOLD = Int.MAX_VALUE - 8

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

typealias ServiceLoading = co.mercenary.creators.kotlin.util.security.ServiceLoading

typealias Throwables = co.mercenary.creators.kotlin.util.type.Throwables

typealias TypeTools = co.mercenary.creators.kotlin.util.type.TypeTools

typealias SameAndHashCode = co.mercenary.creators.kotlin.util.type.SameAndHashCode

typealias CipherAlgorithm = co.mercenary.creators.kotlin.util.security.CipherAlgorithm

typealias Manager = co.mercenary.creators.kotlin.util.system.Manager

typealias Launcher = co.mercenary.creators.kotlin.util.system.Launcher

typealias DeckArrayList<T> = ArrayDeque<T>

typealias LinkArrayList<T> = LinkedList<T>

typealias LinkDequeList<T> = java.util.concurrent.ConcurrentLinkedDeque<T>

typealias ReentrantAtomicLock = java.util.concurrent.locks.ReentrantLock

typealias CopyArrayList<T> = java.util.concurrent.CopyOnWriteArrayList<T>

typealias AtomicHashMap<K, V> = java.util.concurrent.ConcurrentHashMap<K, V>

typealias AtomicDictionary<V> = java.util.concurrent.ConcurrentHashMap<String, V>

typealias AtomicHashMapKeysView<K, V> = java.util.concurrent.ConcurrentHashMap.KeySetView<K, V>

typealias LRUCacheMap<K, V> = co.mercenary.creators.kotlin.util.collection.LRUCacheMap<K, V>

typealias BasicDictionaryMap<V> = co.mercenary.creators.kotlin.util.collection.BasicDictionaryMap<V>

typealias BasicArrayList<V> = co.mercenary.creators.kotlin.util.collection.BasicArrayList<V>

typealias StringDictionary = co.mercenary.creators.kotlin.util.collection.StringDictionary

typealias Dictionary<V> = Map<String, V>

typealias AnyDictionary = Dictionary<Maybe>

typealias DictionaryMap = Dictionary<String>

typealias MutableDictionary<V> = MutableMap<String, V>

typealias MutableAnyDictionary = MutableDictionary<Maybe>

typealias BasicAnyDictionary = BasicDictionaryMap<Any>

typealias MessageDigestProxy = co.mercenary.creators.kotlin.util.security.Digests.MessageDigestProxy

@FrameworkDsl
inline fun toReentrantLock(fair: Boolean = false) = ReentrantAtomicLock(fair)

@FrameworkDsl
inline fun <T> ReentrantAtomicLock.withLocking(block: () -> T): T {
    lock()
    return try {
        block()
    } finally {
        unlock()
    }
}

@FrameworkDsl
fun <T : Maybe> T.toSafeString(): String = Formatters.toSafeString { this }

@FrameworkDsl
fun <T> Iterable<T>.convertToSet(copy: Boolean = true): Set<T> {
    if (this is Set && copy.isNotTrue()) {
        return this
    }
    return toSet()
}

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
inline fun Iterator<*>.isNotExhausted(): Boolean = hasNext().isTrue()

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
inline fun Double.isExhausted(): Boolean = isNotValid() || absOf() == 0.0

@FrameworkDsl
inline fun Double.isNotExhausted(): Boolean = isValid() && absOf() != 0.0

@FrameworkDsl
inline fun Sized.isExhausted(): Boolean = sizeOf() == 0

@FrameworkDsl
inline fun Sized.isNotExhausted(): Boolean = sizeOf() > 0

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
    if (min > max) throw MercenaryMathExceptiion("${max.nameOf(true)}.boxIn().invalid()")
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
fun <T> Collection<T>.toLinkedDequeList(): LinkDequeList<T> {
    return LinkDequeList(this)
}

@FrameworkDsl
fun <T> Iterable<T>.toLinkedDequeList(): LinkDequeList<T> {
    return toCollection().toLinkedDequeList()
}

@FrameworkDsl
fun <T> Iterator<T>.toLinkedDequeList(): LinkDequeList<T> {
    return toCollection().toLinkedDequeList()
}

@FrameworkDsl
fun <T> Sequence<T>.toLinkedDequeList(): LinkDequeList<T> {
    return toCollection().toLinkedDequeList()
}

@FrameworkDsl
fun <T : Any> Array<T>.toLinkedDequeList(): LinkDequeList<T> {
    return toCollection().toLinkedDequeList()
}

@FrameworkDsl
fun <T : Any> Stream<T>.toLinkedDequeList(): LinkDequeList<T> {
    return toFlux().toLinkedDequeList()
}

@FrameworkDsl
fun <T : Any> Flux<T>.toLinkedDequeList(): LinkDequeList<T> {
    return toList().toLinkedDequeList()
}

@FrameworkDsl
fun <T : Any> Array<T>.toBasicLinkedSet(): BasicLinkedSet<T> {
    return BasicLinkedSet(toIterator())
}

@FrameworkDsl
fun <T : Any> Collection<T>.toBasicLinkedSet(): BasicLinkedSet<T> {
    return BasicLinkedSet(toIterator())
}

@FrameworkDsl
fun <T : Any> Iterable<T>.toBasicLinkedSet(): BasicLinkedSet<T> {
    return BasicLinkedSet(toIterator())
}

@FrameworkDsl
fun <T : Any> Iterator<T>.toBasicLinkedSet(): BasicLinkedSet<T> {
    return BasicLinkedSet(toIterator())
}

@FrameworkDsl
fun <T : Any> Sequence<T>.toBasicLinkedSet(): BasicLinkedSet<T> {
    return BasicLinkedSet(toIterator())
}

@FrameworkDsl
inline fun <T> Int.toArrayList(): ArrayList<T> {
    return ArrayList(toListCapacity())
}

@FrameworkDsl
fun <T> Collection<T>.toArrayList(): ArrayList<T> {
    return ArrayList(this)
}

@FrameworkDsl
fun <T : Any> Array<T>.toArrayList(): ArrayList<T> {
    return if (isExhausted()) ArrayList(DEFAULT_LIST_CAPACITY) else ArrayList<T>(sizeOf()).append(toIterator())
}

@FrameworkDsl
fun <T> Iterable<T>.toArrayList(): ArrayList<T> {
    return ArrayList<T>(DEFAULT_LIST_CAPACITY).append(this)
}

@FrameworkDsl
fun <T> Iterator<T>.toArrayList(): ArrayList<T> {
    return ArrayList<T>(DEFAULT_LIST_CAPACITY).append(this)
}

@FrameworkDsl
fun <T> Sequence<T>.toArrayList(): ArrayList<T> {
    return ArrayList<T>(DEFAULT_LIST_CAPACITY).append(this)
}

@FrameworkDsl
fun <T> ArrayList<T>.reset() {
    locked(this) {
        clear()
        trimToSize()
    }
}

@FrameworkDsl
fun <T> ArrayList<T>.trim(): ArrayList<T> {
    locked(this) {
        trimToSize()
    }
    return this
}

@FrameworkDsl
fun Sized.rangecheckget(index: Int): Int {
    if (index >= sizeOf()) {
        fail("illegal index get($index) for size(${sizeOf()})")
    }
    return index
}

@FrameworkDsl
fun Sized.rangecheckadd(index: Int): Int {
    if ((index < 0) || (index > sizeOf())) {
        fail("illegal index add($index) for size(${sizeOf()})")
    }
    return index
}

@FrameworkDsl
fun Sized.isOverLimit(limit: Int): Boolean {
    if (limit.isNegative()) {
        return false
    }
    return sizeOf() >= 0 && sizeOf() > limit
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
inline fun <reified T : Any> toParameterizedTypeReference(): ParameterizedTypeReference<T> = object : ParameterizedTypeReference<T>() {}

@FrameworkDsl
inline fun <reified T> kindOf(): KType = typeOf<T>()

@FrameworkDsl
inline fun <reified T : Any> kindOfType(): KClass<out T> = T::class

@FrameworkDsl
inline fun <reified T : Any> castTo(args: Any): T = args as T

@FrameworkDsl
inline fun <reified T : Annotation> kindOfAnnotation(): Class<out T> = T::class.java

@FrameworkDsl
inline fun <reified T> toArrayOf(vararg args: T): Array<T> = arrayOf(*args)

@FrameworkDsl
inline fun <reified T> Int.toArray(init: (Int) -> T): Array<T> = Array(maxOf(0)) { init(it) }

@FrameworkDsl
inline fun Array<*>.kindOfComponent(): Class<*> = javaClass.componentType

@FrameworkDsl
fun Class<*>.isAnnotationDefined(kind: Class<out Annotation>): Boolean {
    return isAnnotationPresent(kind)
}

@FrameworkDsl
inline fun <A : Annotation> Iterable<A>.isAnnotationDefined(kind: Class<*>): Boolean {
    return isNotExhausted() && any { it.annotationClass.java == kind }
}

@FrameworkDsl
inline fun <A : Annotation> Iterable<A>.isAnnotationDefined(list: List<Class<*>>): Boolean {
    return isNotExhausted() && any { it.annotationClass.java in list }
}

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
    if (this is Set) {
        return hashOf()
    }
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
fun Set<*>.hashOf(): Int {
    if (isExhausted()) {
        return HASH_NULL_VALUE
    }
    var code = HASH_NULL_VALUE
    for (each in this) {
        code += each.hashOf()
    }
    return code
}

@FrameworkDsl
fun List<*>.hashOf(): Int {
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
fun Map<*, *>.hashOf(): Int {
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

@FrameworkDsl
inline fun <T : Throwable> T.failed(): Nothing {
    throw this
}

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
fun Type.toErased(): Class<*> {
    return TypeTools.getErasedType(this)
}

@FrameworkDsl
fun KType.toErased(): Class<*> {
    return TypeTools.getErasedJavaClass(this)
}

@FrameworkDsl
inline fun SystemProperties.toStringDictionary() = StringDictionary(this)

@FrameworkDsl
fun StringDictionary.concat(args: AnyDictionary, push: Boolean = false, safe: Boolean = false): StringDictionary {
    if (args.isExhausted()) {
        return this
    }
    for ((k, v) in args) {
        if (isKeyDefined(k) && push.isNotTrue()) {
            continue
        }
        if (v == null) {
            if (safe && push) {
                this[k] = NULLS_STRING
            }
            continue
        }
        if (v is CharSequence) {
            this[k] = v.toValid()
        } else {
            this[k] = if (safe.isTrue()) v.toSafeString() else v.toString()
        }
    }
    return this
}

@FrameworkDsl
fun SystemProperties.mapTo(): DictionaryMap {
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
inline fun <R> locked(lock: Any, func: () -> R): R {
    return synchronized(lock, func)
}

@FrameworkDsl
fun Int.toListCapacity(): Int = if (isNegative()) DEFAULT_LIST_CAPACITY else minOf(DEFAULT_LIST_THRESHOLD)

@FrameworkDsl
inline infix fun Int.tabsOf(maximum: Int): Int {
    return when {
        isLessThan(0) -> 1
        isLessThan(3) -> 2
        isMoreSame(MAXIMUM_INTS_POWER_OF_2) -> MAXIMUM_INTS_POWER_OF_2
        else -> Numeric.tabsOf(this, maximum.absOf().minOf(MAXIMUM_INTS_POWER_OF_2))
    }
}

@FrameworkDsl
inline fun Int.toMapCapacity(): Int = tabsOf(MAXIMUM_MAP_CAPACITY)

@FrameworkDsl
inline fun Int.toSetCapacity(): Int = tabsOf(MAXIMUM_SET_CAPACITY)

@FrameworkDsl
fun Double.toMapFactorOrElse(value: Double = DEFAULT_MAP_FACTOR): Float = toFiniteOrElse(value.toFiniteOrElse(DEFAULT_MAP_FACTOR)).toFloat()

@FrameworkDsl
fun <K, V, T : MutableMap<in K, in V>> T.append(k: K, v: V): T {
    put(k, v)
    return this
}

@FrameworkDsl
fun <K, V, T : MutableMap<in K, in V>> T.append(args: Pair<K, V>): T {
    put(args.head(), args.tail())
    return this
}

@FrameworkDsl
fun <K, V, T : MutableMap<in K, in V>> T.append(vararg args: Pair<K, V>): T {
    if (args.isNotExhausted()) {
        putAll(args)
    }
    return this
}

@FrameworkDsl
fun <K, V, T : MutableMap<in K, in V>> T.append(args: Iterator<Pair<K, V>>): T {
    if (args.isNotExhausted()) {
        putAll(args.asSequence())
    }
    return this
}

@FrameworkDsl
fun <K, V, T : MutableMap<in K, in V>> T.append(args: Iterable<Pair<K, V>>): T {
    if (args.isNotExhausted()) {
        putAll(args)
    }
    return this
}

@FrameworkDsl
fun <K, V, T : MutableMap<in K, in V>> T.append(args: Sequence<Pair<K, V>>): T {
    if (args.isNotExhausted()) {
        putAll(args)
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
inline infix fun <K> Map<out K, *>.isKeyDefined(key: K): Boolean = sizeOf() > 0 && containsKey(key)

@FrameworkDsl
inline infix fun <K> Map<out K, *>.isKeyNotDefined(key: K): Boolean = isKeyDefined(key).isNotTrue()

@FrameworkDsl
inline fun <K, V> toMapOf(): Map<K, V> = emptyMap()

@FrameworkDsl
inline fun <K, V> toMapOf(k: K, v: V): Map<K, V> = mapOf(Pair(k, v))

@FrameworkDsl
inline fun <K, V> toMapOf(args: Pair<K, V>): Map<K, V> = mapOf(args)

@FrameworkDsl
inline fun <K, V> Map<K, V>.toPairs(): List<Pair<K, V>> = if (isExhausted()) toListOf() else entries.map { it.key to it.value }.toList()

@FrameworkDsl
inline fun <K, V> toMapOf(vararg args: Pair<K, V>): Map<K, V> = if (args.isExhausted()) toMapOf() else args.mapTo()

@FrameworkDsl
inline fun <T> toSetOf(): Set<T> = emptySet()

@FrameworkDsl
inline fun <T> toSetOf(args: T): Set<T> = setOf(args)

@FrameworkDsl
fun <T> toSetOf(vararg args: T): Set<T> {
    return when (args.sizeOf()) {
        0 -> toSetOf()
        1 -> toSetOf(args[0])
        else -> BasicLinkedSet(args.toCollection()).toReadOnly()
    }
}

@FrameworkDsl
fun <T> toSetOf(args: Iterator<T>): Set<T> = if (args.isExhausted()) toSetOf() else args.toList().toSet()

@FrameworkDsl
fun <T> toSetOf(args: Iterable<T>): Set<T> = if (args.isExhausted()) toSetOf() else toSetOf(args.toIterator())

@FrameworkDsl
fun <E, T : MutableSet<E>> T.append(args: E): T {
    add(args)
    return this
}

@FrameworkDsl
fun <E, T : MutableSet<E>> T.append(vararg args: E): T {
    if (args.isNotExhausted()) {
        addAll(args)
    }
    return this
}

@FrameworkDsl
fun <E, T : MutableSet<E>> T.append(args: Iterator<E>): T {
    if (args.isNotExhausted()) {
        addAll(args.asSequence())
    }
    return this
}

@FrameworkDsl
fun <E, T : MutableSet<E>> T.append(args: Iterable<E>): T {
    if (args.isNotExhausted()) {
        addAll(args)
    }
    return this
}

@FrameworkDsl
fun <E, T : MutableSet<E>> T.append(args: Sequence<E>): T {
    if (args.isNotExhausted()) {
        addAll(args)
    }
    return this
}

@FrameworkDsl
fun <T> List<T>.toOptimizd(): List<T> {
    return when (sizeOf()) {
        0 -> emptyList()
        1 -> listOf(this[0])
        else -> when (this) {
            is ArrayList -> this.trim().toList()
            else -> this.toList()
        }
    }
}

@FrameworkDsl
inline fun <T> toListOf(): List<T> = emptyList()

@FrameworkDsl
inline fun <T> toListOf(args: T): List<T> = listOf(args)

@FrameworkDsl
fun <T> toListOf(args: List<T>): List<T> {
    return when (args.sizeOf()) {
        0 -> toListOf()
        1 -> toListOf(args[0])
        else -> toListOf(args.toCollection())
    }
}

@FrameworkDsl
fun <T> toListOf(vararg args: T): List<T> = when (args.sizeOf()) {
    0 -> emptyList()
    1 -> toListOf(args[0])
    else -> args.asList()
}

@FrameworkDsl
fun <T> toListOf(args: Stream<T>): List<T> = args.toList()

@FrameworkDsl
fun toListOf(args: IntStream): List<Int> = args.toList()

@FrameworkDsl
fun toListOf(args: LongStream): List<Long> = args.toList()

@FrameworkDsl
fun toListOf(args: DoubleStream): List<Double> = args.toList()

@FrameworkDsl
fun <T> toListOf(args: Iterable<T>): List<T> = if (args.isExhausted()) toListOf() else args.toList()

@FrameworkDsl
fun <T> toListOf(args: Iterator<T>): List<T> = if (args.isExhausted()) toListOf() else args.toList()

@FrameworkDsl
fun <T> toListOf(args: Sequence<T>): List<T> = if (args.isExhausted()) toListOf() else args.toList()

@FrameworkDsl
fun <T : Any> T.isDataClass(): Boolean {
    return when (this) {
        is KClass<*> -> isData
        is Class<*> -> kotlin.isData
        else -> javaClass.kotlin.isData
    }
}

@FrameworkDsl
fun <T : Any> T.isValueClass(): Boolean {
    return when (this) {
        is KClass<*> -> isValue
        is Class<*> -> kotlin.isValue
        else -> javaClass.kotlin.isValue
    }
}

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
fun onExitOfProcess(push: Boolean = false, func: () -> Unit) {
    SecureAccess.onExitOfProcess(push, func)
}

@FrameworkDsl
fun Class<*>.isKotlinClass(): Boolean {
    return declaredAnnotations.any { it.annotationClass.java.name == KOTLIN_METAS }
}

@FrameworkDsl
fun Class<*>.isNotKotlinClass(): Boolean = isKotlinClass().isNotTrue()

@FrameworkDsl
inline fun Class<*>.forEachMethod(action: (Method) -> Unit) {
    declaredMethods.forEach(action)
}

@FrameworkDsl
fun Class<*>.toPackageName(): String = this.`package`.name

@FrameworkDsl
fun KClass<*>.toPackageName(): String = java.`package`.name

@FrameworkDsl
inline fun <reified T : Any> toPackageName(): String = T::class.java.`package`.name

@FrameworkDsl
inline fun <T : Any> T.toJavaClass(): Class<T> {
    return when (this) {
        is Class<*> -> this as Class<T>
        is KClass<*> -> this.java as Class<T>
        else -> javaClass
    }
}

@FrameworkDsl
inline fun <T : Any> T.toKotlinClass(): KClass<T> {
    return when (this) {
        is Class<*> -> this.kotlin as KClass<T>
        is KClass<*> -> this as KClass<T>
        else -> javaClass.kotlin
    }
}

@FrameworkDsl
inline infix fun <T : Maybe> T.isType(other: Maybe): Boolean {
    if (this == null) {
        return false
    }
    if (other == null) {
        return false
    }
    return toKotlinClass() == other.toKotlinClass()
}

@FrameworkDsl
inline infix fun <T : Maybe> T.isTypeAssignable(that: Maybe): Boolean {
    if (this == null) {
        return false
    }
    if (that == null) {
        return false
    }
    val selfkind = this.toKotlinClass()
    val thatkind = that.toKotlinClass()
    return (selfkind == thatkind) || (selfkind.isInstance(that)) || (thatkind.isInstance(this))
}

@FrameworkDsl
fun <K, V> atomicMapOf(): AtomicHashMap<K, V> {
    return AtomicHashMap(DEFAULT_MAP_CAPACITY)
}

@FrameworkDsl
fun <K, V> atomicMapOf(size: Int): AtomicHashMap<K, V> {
    return AtomicHashMap(size.toMapCapacity())
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
fun <K, V> AtomicHashMap<K, V>.toReadOnly(): Map<K, V> {
    return toMap()
}

@FrameworkDsl
fun <K, V> AtomicHashMap<K, V>.copyOf(): AtomicHashMap<K, V> {
    return atomicMapOf(this)
}

@FrameworkDsl
inline fun <K, V> AtomicHashMap<K, V>.viewOf(): AtomicHashMapKeysView<K, V> {
    return keys
}

@FrameworkDsl
inline fun <E> List<E>.ifNotEmptyList(block: List<E>.() -> Unit) {
    if (isNotExhausted()) this.apply(block)
}

@FrameworkDsl
inline fun <E> MutableList<E>.ifNotEmptyMutableList(block: MutableList<E>.() -> Unit) {
    if (isNotExhausted()) this.apply(block)
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
        addAll(args)
    }
    return this
}

@FrameworkDsl
fun <E, T : MutableList<E>> T.append(args: Iterator<E>): T {
    if (args.isNotExhausted()) {
        addAll(args.toSequence())
    }
    return this
}

@FrameworkDsl
fun <E, T : MutableList<E>> T.append(args: Iterable<E>): T {
    if (args.isNotExhausted()) {
        addAll(args)
    }
    return this
}

@FrameworkDsl
fun <E, T : MutableList<E>> T.append(args: Sequence<E>): T {
    if (args.isNotExhausted()) {
        addAll(args)
    }
    return this
}

@FrameworkDsl
fun getCurrentThreadName(): String = Thread.currentThread().name

@FrameworkDsl
fun getCurrentThreadContextClassLoader(): ClassLoader = Thread.currentThread().contextClassLoader

@FrameworkDsl
inline fun getProcessors(): Int = Runtime.getRuntime().availableProcessors()

@FrameworkDsl
inline fun CharSequence.codePointsOf(beg: Int = 0, end: Int = sizeOf()): Int = copyOf().codePointCount(beg.maxOf(0), end)

@FrameworkDsl
inline fun String.codePointsOf(beg: Int = 0, end: Int = sizeOf()): Int = codePointCount(beg.maxOf(0), end)

@FrameworkDsl
inline fun CharSequence.codePointsAt(index: Int): Int = copyOf().codePointAt(index)

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
        else -> data.copyOf().trim().let { look ->
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
inline fun CharSequence.headOf(): Char = if (isNotExhausted()) this[0] else fail(MATH_INVALID_SIZE_ERROR)

@FrameworkDsl
inline fun CharSequence?.trimOf(other: String = EMPTY_STRING): String = toTrimOrElse(this, other).copyOf()

@FrameworkDsl
inline fun String.head(many: Int = 1): String = if (many > 0) drop(many) else this

@FrameworkDsl
inline fun String.tail(many: Int = 1): String = if (many > 0) dropLast(many) else this

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
fun String.padout(many: Int, pads: Char = SPACE_LETTER): String = if (many <= 0) this else padStart((sizeOf() + many) / 2, pads).padEnd(many, pads)

@FrameworkDsl
fun String.center(many: Int, pads: Char = SPACE_LETTER, trim: Boolean = true): String = if (many <= 0) this else filter(pads, trim).padout(many, pads)

@FrameworkDsl
inline fun CharSequence.isZeroCharPresent(): Boolean = any { it == Char.MIN_VALUE }

@FrameworkDsl
inline fun CharSequence.isZeroCharNotPresent(): Boolean = none { it == Char.MIN_VALUE }

@FrameworkDsl
inline fun CharSequence.toValid(): String = filter { it != Char.MIN_VALUE }.copyOf()

@FrameworkDsl
inline fun CharSequence.toChecked(): String {
    return if (isZeroCharPresent())
        throw MercenaryFatalExceptiion("null byte present. there are no known legitimate use cases for such data, but several injection attacks may use it.")
    else copyOf()
}

@FrameworkDsl
inline fun CharSequence.copyOf(): String {
    return if (isExhausted()) EMPTY_STRING else toString()
}

@FrameworkDsl
fun ByteArray.toCharArray(charset: Charset = DEFAULT_CHARSET_UTF_8, copy: Boolean = true): CharArray = toByteArray(copy).toString(charset).toCharArray()

@FrameworkDsl
fun CharSequence.toLowerTrim(): String = if (isExhausted()) EMPTY_STRING else trim().copyOf().lowercase(DEFAULT_LOCALE)

@FrameworkDsl
fun CharSequence.toUpperTrim(): String = if (isExhausted()) EMPTY_STRING else trim().copyOf().uppercase(DEFAULT_LOCALE)

@FrameworkDsl
fun CharSequence.toLowerTrimEnglish(): String = if (isExhausted()) EMPTY_STRING else trim().toLowerCaseEnglish()

@FrameworkDsl
fun CharSequence.toUpperTrimEnglish(): String = if (isExhausted()) EMPTY_STRING else trim().toUpperCaseEnglish()

@FrameworkDsl
fun CharSequence.toLowerCaseEnglish(): String = if (isExhausted()) EMPTY_STRING else copyOf().lowercase(ENGLISH_LOCALE)

@FrameworkDsl
fun CharSequence.toUpperCaseEnglish(): String = if (isExhausted()) EMPTY_STRING else copyOf().uppercase(ENGLISH_LOCALE)

@FrameworkDsl
inline fun CharSequence.toCharArray(copy: Boolean): CharArray {
    if (isExhausted()) {
        return EMPTY_CHAR_ARRAY
    }
    return copyOf().toCharArray().toCharArray(copy)
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
        return list.toReadOnly()
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
    reset(getValue() + value)
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
inline fun Boolean.copyOf(): Boolean = this

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
inline fun AtomicBoolean.getValue(): Boolean = get()

@FrameworkDsl
inline fun AtomicBoolean.isNotTrue(): Boolean = !get()

@FrameworkDsl
fun AtomicBoolean.isTrueToFalse(): Boolean = isUpdateTo(true.isTrue(), false.toBoolean())

@FrameworkDsl
fun AtomicBoolean.isFalseToTrue(): Boolean = isUpdateTo(false.toBoolean(), true)

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
fun AtomicBoolean.setValue(value: Boolean): Boolean = toBoolean() != value && isUpdateTo(value)

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
inline fun Array<*>.hashOf(): Int = if (sizeOf() == 0) HASH_BASE_VALUE else contentDeepHashCode()

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
inline fun java.time.Duration.hashOf(): Int = if (isEmpty()) HASH_NULL_VALUE else hashCode()

@FrameworkDsl
inline fun java.time.Duration.isEmpty(): Boolean = isZero || isNegative

@FrameworkDsl
inline fun <T : Any?> T.hashOf() = if (this == null) HASH_NULL_VALUE else SameAndHashCode.hashOf(this)

@FrameworkDsl
inline fun <T : Any?> T.idenOf() = if (this == null) HASH_NULL_VALUE else SameAndHashCode.idenOf(this)

@FrameworkDsl
inline infix fun <B> String.to(that: B): Pair<String, B> = Pair(this, that)

@FrameworkDsl
inline fun <A> Pair<A, *>.head(): A = first

@FrameworkDsl
inline fun <B> Pair<*, B>.tail(): B = second

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
inline fun StringBuilder.build(action: StringBuilder.() -> Unit): String = apply(action).copyOf()

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
inline fun Char.toCode(): Int = code

@FrameworkDsl
inline fun Byte.toCode(): Int = intsOf()

@FrameworkDsl
inline infix fun Int.mask(bits: Int): Int = if (bits == 0) (this and 0xF) else ((this shr bits) and 0xF)

@FrameworkDsl
inline infix fun Byte.mask(bits: Int): Int = toCode().mask(bits)

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
    insert(0, data)
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
    return wrap(data.copyOf())
}

@FrameworkDsl
inline fun StringBuilder.add(args: String): StringBuilder = append(args)

@FrameworkDsl
inline fun StringBuilder.add(vararg args: Any?): StringBuilder = append(*args)

@FrameworkDsl
inline fun StringBuilder.add(vararg args: String?): StringBuilder = append(*args)

@FrameworkDsl
inline fun StringBuilder.formatted(text: String, vararg args: Any?): StringBuilder = append(text.format(*args))

@FrameworkDsl
inline fun StringBuilder.newline(): StringBuilder = append(BREAK_STRING)

@FrameworkDsl
fun <T : Any> T.nameOf(simple: Boolean = false): String = if (simple) TypeTools.simpleNameOf(this) else TypeTools.nameOf(this)

@FrameworkDsl
fun KType.descOf(): String = toString()

@FrameworkDsl
fun Type.isArrayType(): Boolean {
    return when (this) {
        is GenericArrayType -> true
        is Class<*> -> isArray.isTrue()
        else -> toErased().isArray.isTrue()
    }
}

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

@OptIn(ExperimentalUnsignedTypes::class)
inline fun UIntArray.sizeOf(): Int = size

@FrameworkDsl
inline fun Collection<*>.sizeOf(): Int = size

@FrameworkDsl
inline fun Map<*, *>.sizeOf(): Int = size

@FrameworkDsl
infix fun Array<*>.isSameArrayAs(args: Array<*>): Boolean = sizeOf() == args.sizeOf() && SameAndHashCode.isSameAs(this, args)

@FrameworkDsl
infix fun IntArray.isSameArrayAs(args: IntArray): Boolean {
    if (this === args) {
        return true
    }
    if (sizeOf() != args.sizeOf()) {
        return false
    }
    if (sizeOf() == 0) {
        return true
    }
    return this.contentEquals(args)
}

@FrameworkDsl
infix fun ByteArray.isSameArrayAs(args: ByteArray): Boolean {
    if (this === args) {
        return true
    }
    if (sizeOf() != args.sizeOf()) {
        return false
    }
    if (sizeOf() == 0) {
        return true
    }
    return this.contentEquals(args)
}

@FrameworkDsl
infix fun CharArray.isSameArrayAs(args: CharArray): Boolean {
    if (this === args) {
        return true
    }
    if (sizeOf() != args.sizeOf()) {
        return false
    }
    if (sizeOf() == 0) {
        return true
    }
    return this.contentEquals(args)
}

@FrameworkDsl
infix fun LongArray.isSameArrayAs(args: LongArray): Boolean {
    if (this === args) {
        return true
    }
    if (sizeOf() != args.sizeOf()) {
        return false
    }
    if (sizeOf() == 0) {
        return true
    }
    return this.contentEquals(args)
}

@FrameworkDsl
infix fun ShortArray.isSameArrayAs(args: ShortArray): Boolean {
    if (this === args) {
        return true
    }
    if (sizeOf() != args.sizeOf()) {
        return false
    }
    if (sizeOf() == 0) {
        return true
    }
    return this.contentEquals(args)
}

@FrameworkDsl
infix fun FloatArray.isSameArrayAs(args: FloatArray): Boolean {
    if (this === args) {
        return true
    }
    if (sizeOf() != args.sizeOf()) {
        return false
    }
    if (sizeOf() == 0) {
        return true
    }
    return this.contentEquals(args)
}

@FrameworkDsl
infix fun DoubleArray.isSameArrayAs(args: DoubleArray): Boolean {
    if (this === args) {
        return true
    }
    if (sizeOf() != args.sizeOf()) {
        return false
    }
    if (sizeOf() == 0) {
        return true
    }
    return this.contentEquals(args)
}

@FrameworkDsl
infix fun BooleanArray.isSameArrayAs(args: BooleanArray): Boolean {
    if (this === args) {
        return true
    }
    if (sizeOf() != args.sizeOf()) {
        return false
    }
    if (sizeOf() == 0) {
        return true
    }
    return this.contentEquals(args)
}

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
inline fun CharSequence.hashOf(): Int = if (sizeOf() == 0) HASH_NULL_VALUE else copyOf().hashCode()

@FrameworkDsl
inline fun Double.hashOf(): Int = if (this == 0.0) HASH_NULL_VALUE else hashCode()

@FrameworkDsl
inline fun Alive.isNotAlive(): Boolean = isAlive().isNotTrue()

@FrameworkDsl
inline fun Iterator<String>.toNoEmptyChars(): List<String> {
    return toList().filter { it.isZeroCharNotPresent() }
}

open class MercenarySequence<out T> @FrameworkDsl constructor(args: Iterator<T>) : Sequence<T> {

    @FrameworkDsl
    private val iterator = args

    @FrameworkDsl
    override operator fun iterator(): Iterator<T> = iterator

    @FrameworkDsl
    constructor() : this(EmptyIterator)

    @FrameworkDsl
    constructor(vararg source: T) : this(source.iterator())

    @FrameworkDsl
    constructor(source: Stream<T>) : this(source.iterator())

    @FrameworkDsl
    constructor(source: Iterable<T>) : this(source.iterator())

    @FrameworkDsl
    constructor(source: Sequence<T>) : this(source.iterator())
}

class MercenaryConstrainedSequence<out T> @FrameworkDsl constructor(source: Iterator<T>) : MercenarySequence<T>(source), Sequence<T>

@FrameworkDsl
inline fun <T> Sequence<T>.constrain(): Sequence<T> {
    return if (this is MercenaryConstrainedSequence) this else MercenaryConstrainedSequence(constrainOnce().iterator())
}

@FrameworkDsl
inline fun IntArray.toIterator(): Iterator<Int> = if (isExhausted()) EmptyIterator else iterator()

@FrameworkDsl
inline fun ByteArray.toIterator(): Iterator<Byte> = if (isExhausted()) EmptyIterator else iterator()

@FrameworkDsl
inline fun CharArray.toIterator(): Iterator<Char> = if (isExhausted()) EmptyIterator else iterator()

@FrameworkDsl
inline fun LongArray.toIterator(): Iterator<Long> = if (isExhausted()) EmptyIterator else iterator()

@FrameworkDsl
inline fun ShortArray.toIterator(): Iterator<Short> = if (isExhausted()) EmptyIterator else iterator()

@FrameworkDsl
inline fun FloatArray.toIterator(): Iterator<Float> = if (isExhausted()) EmptyIterator else iterator()

@FrameworkDsl
inline fun DoubleArray.toIterator(): Iterator<Double> = if (isExhausted()) EmptyIterator else iterator()

@FrameworkDsl
inline fun BooleanArray.toIterator(): Iterator<Boolean> = if (isExhausted()) EmptyIterator else iterator()

@FrameworkDsl
inline fun <T> Set<T>.toIterator(): Iterator<T> = if (isExhausted()) EmptyIterator else iterator()

@FrameworkDsl
inline fun <T> List<T>.toIterator(): Iterator<T> = if (isExhausted()) EmptyIterator else iterator()

@FrameworkDsl
inline fun <T> Array<T>.toIterator(): Iterator<T> = if (isExhausted()) EmptyIterator else iterator()

@FrameworkDsl
inline fun <T> Stream<T>.toIterator(): Iterator<T> = iterator().toIterator()

@FrameworkDsl
inline fun <T> Sequence<T>.toIterator(): Iterator<T> = iterator().toIterator()

@FrameworkDsl
inline fun <T> Iterator<T>.toIterator(): Iterator<T> = if (isExhausted()) EmptyIterator else this

@FrameworkDsl
inline fun <T> Iterable<T>.toIterator(): Iterator<T> = iterator().toIterator()

@FrameworkDsl
inline fun <T> Enumeration<T>.toIterator(): Iterator<T> = iterator().toIterator()

@FrameworkDsl
inline fun IntStream.toIterator(): Iterator<Int> = iterator().toIterator()

@FrameworkDsl
inline fun LongStream.toIterator(): Iterator<Long> = iterator().toIterator()

@FrameworkDsl
inline fun DoubleStream.toIterator(): Iterator<Double> = iterator().toIterator()

@FrameworkDsl
inline fun <K, V> Map<out K, V>.toIterator(): Iterator<Map.Entry<K, V>> = iterator().toIterator()

@FrameworkDsl
inline fun <T> Iterator<T>.toCollection(): Collection<T> = toListOf(this)

@FrameworkDsl
inline fun <T> Array<T>.toCollection(): Collection<T> = if (isExhausted()) toListOf() else toList()

@FrameworkDsl
inline fun <T> Sequence<T>.toCollection(): Collection<T> = toList()

@FrameworkDsl
inline fun <T> Iterable<T>.toCollection(): Collection<T> = if (this is Collection) this else toList()

@FrameworkDsl
fun <T> Array<T>.toIterable(): Iterable<T> = if (isExhausted()) toListOf() else Iterable { this.iterator() }

@FrameworkDsl
fun <T> Stream<T>.toIterable(): Iterable<T> = Iterable { this.iterator() }

@FrameworkDsl
fun <T> Sequence<T>.toIterable(): Iterable<T> = Iterable { this.iterator() }

@FrameworkDsl
fun <T> Iterator<T>.toIterable(): Iterable<T> = Iterable { this }

@FrameworkDsl
fun <T> Iterator<T>.toList(): List<T> = asSequence().toList()

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
fun IntStream.toSequence(): Sequence<Int> = MercenarySequence(iterator())

@FrameworkDsl
fun LongStream.toSequence(): Sequence<Long> = MercenarySequence(iterator())

@FrameworkDsl
fun DoubleStream.toSequence(): Sequence<Double> = MercenarySequence(iterator())

@FrameworkDsl
inline fun <T> Stream<T>.toSequence(): Sequence<T> = MercenarySequence(this)

@FrameworkDsl
inline fun <T> Iterable<T>.toSequence(): Sequence<T> = MercenarySequence(this)

@FrameworkDsl
inline fun <T> Iterator<T>.toSequence(): Sequence<T> = MercenarySequence(this)

@FrameworkDsl
inline fun <T> Sequence<T>.toSequence(): Sequence<T> = MercenarySequence(this)

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
inline fun <K, V> Map<out K, V>.forEachIndexed(action: (index: Int, Map.Entry<K, V>) -> Unit) {
    entries.forEachIndexed(action)
}

@FrameworkDsl
inline fun <T> Stream<T>.forEachIndexed(action: (index: Int, T) -> Unit) {
    toList().forEachIndexed(action)
}

@FrameworkDsl
inline fun <T> Array<T>.forEachIndexed(action: (index: Int, T) -> Unit) {
    asList().forEachIndexed(action)
}

@FrameworkDsl
inline fun <T> Iterator<T>.forEachIndexed(action: (index: Int, T) -> Unit) {
    toIterable().forEachIndexed(action)
}

@FrameworkDsl
inline fun <T> Sequence<T>.forEachIndexed(action: (index: Int, T) -> Unit) {
    toIterator().forEachIndexed(action)
}

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
inline fun <T> Array<out T?>?.uniqueOrOtherwise(): List<T> {
    return when (this) {
        null -> toListOf()
        else -> when (sizeOf()) {
            0 -> toListOf()
            else -> filterNotNull().unique()
        }
    }
}

@FrameworkDsl
inline fun <T> Array<out T>.unique(): List<T> {
    return when (sizeOf()) {
        0 -> toListOf()
        1 -> toListOf(this[0])
        else -> distinct()
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
inline fun <T> Iterator<T>.unique(): Iterator<T> = toList().unique().toIterator()

@FrameworkDsl
inline fun <T> Sequence<T>.unique(): Sequence<T> = distinct().toSequence()

@FrameworkDsl
inline fun <T> Iterable<T>.tail(): T = last()

@FrameworkDsl
inline fun <T> Iterable<T>.head(): T = first()

@FrameworkDsl
inline fun <T> Array<out T>.tail(): T = if (isExhausted()) fail(MATH_INVALID_SIZE_ERROR) else this[sizeOf() - 1]

@FrameworkDsl
inline fun <T> Array<out T>.head(): T = if (isExhausted()) fail(MATH_INVALID_SIZE_ERROR) else this[0]

@FrameworkDsl
fun <T> List<T>.forEachOther(block: (T, T) -> Unit) {
    sizeOf().forEachOther { i, j ->
        block(this[i], this[j])
    }
}

@FrameworkDsl
fun Sequence<String>.uniqueTrimmedOf(): List<String> = toIterable().uniqueTrimmedOf()

@FrameworkDsl
fun Iterable<String>.uniqueTrimmedOf(): List<String> = mapNotNull { toTrimOrNull(it) }.distinct()

@FrameworkDsl
inline fun CharSequence.withSizeOf(block: (String, Int) -> Unit) {
    block(copyOf(), sizeOf())
}

@FrameworkDsl
inline fun <R> CharSequence.withSize(block: (String, Int) -> R): R {
    return block(copyOf(), sizeOf())
}

@FrameworkDsl
inline fun <T : Comparable<T>> Array<T>.toSorted(reversed: Boolean = false): List<T> {
    return when (sizeOf()) {
        0 -> toListOf()
        1 -> toListOf(this[0])
        else -> toIterable().toSorted(reversed)
    }
}

@FrameworkDsl
fun <T : Comparable<T>> Iterable<T>.toSorted(reversed: Boolean = false): List<T> {
    return if (reversed.isNotTrue()) sorted() else sortedDescending()
}

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
inline fun CharSequence?.otherwise(value: String = EMPTY_STRING): String = this?.copyOf() ?: value

@FrameworkDsl
inline fun <reified T : Any> logsOfType(): ILogging = LoggingFactory.logger(T::class)

@FrameworkDsl
inline fun <T : Any> T.logsOfSelf(): ILogging = LoggingFactory.logger(this)

@FrameworkDsl
fun mu.KLogger.getLevel(): LoggingLevel = LoggingFactory.getLevel(this)

@FrameworkDsl
fun mu.KLogger.setLevel(level: LoggingLevel) = LoggingFactory.setLevel(this, level)

@FrameworkDsl
fun mu.KLogger.withLevel(level: LoggingLevel, block: () -> Unit) {
    LoggingFactory.withLevel(this, level, block)
}

@FrameworkDsl
inline fun <T> withLoggingContext(restore: Boolean = true, args: Pair<String, Maybe>, noinline block: () -> T): T {
    return withLoggingContext(restore, toMapOf(args), block)
}

@FrameworkDsl
inline fun <T> withLoggingContext(restore: Boolean = true, args: Map<String, Maybe>, noinline block: () -> T): T {
    if (args.isExhausted()) {
        return block()
    }
    return mu.withLoggingContext(args.toDataType<Map<String, String?>>(), restore, block)
}

@FrameworkDsl
inline fun <T> withLoggingContext(restore: Boolean = true, vararg args: Pair<String, Maybe>, noinline block: () -> T): T {
    if (args.isExhausted()) {
        return block()
    }
    return withLoggingContext(restore, args.mapTo(), block)
}