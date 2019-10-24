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

import co.mercenary.creators.kotlin.util.io.*
import co.mercenary.creators.kotlin.util.math.Numeric
import co.mercenary.creators.kotlin.util.time.NanoTicker
import co.mercenary.creators.kotlin.util.type.Validated
import org.reactivestreams.Publisher
import reactor.core.publisher.*
import java.io.*
import java.net.URL
import java.nio.channels.*
import java.nio.file.*
import java.util.*
import java.util.concurrent.*
import java.util.concurrent.atomic.*
import java.util.stream.Collectors
import kotlin.math.*

const val IS_NOT_FOUND = -1

const val EMPTY_STRING = ""

const val SPACE_STRING = " "

const val CHAR_INVALID = Char.MIN_VALUE

const val DEFAULT_CONTENT_TYPE = "application/octet-stream"

const val DEFAULT_ZONE_STRING = "UTC"

const val DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss,SSS z"

const val CREATORS_AUTHOR_INFO = "Dean S. Jones, Copyright (C) 2019, Mercenary Creators Company."

private typealias DVector = Array<Double>

private typealias DMatrix = Array<DoubleArray>

private typealias DMatrixArray = Array<Array<Double>>

typealias TimeUnit = java.util.concurrent.TimeUnit

typealias TimeDuration = co.mercenary.creators.kotlin.util.time.TimeDuration

typealias Numeric = Numeric

typealias Logging = co.mercenary.creators.kotlin.util.logging.Logging

typealias LoggingFactory = co.mercenary.creators.kotlin.util.logging.LoggingFactory

typealias Randoms = co.mercenary.creators.kotlin.util.security.Randoms

typealias Digests = co.mercenary.creators.kotlin.util.security.Digests

typealias Encoders = co.mercenary.creators.kotlin.util.security.Encoders

typealias Throwables = co.mercenary.creators.kotlin.util.type.Throwables

typealias CipherAlgorithm = co.mercenary.creators.kotlin.util.security.CipherAlgorithm

typealias DefaultContentTypeProbe = MimeContentTypeProbe

typealias DefaultContentFileTypeMap = ContentFileTypeMap

typealias SingleScheduler = co.mercenary.creators.kotlin.util.reactive.SingleScheduler

typealias ElasticScheduler = co.mercenary.creators.kotlin.util.reactive.ElasticScheduler

typealias ParallelScheduler = co.mercenary.creators.kotlin.util.reactive.ParallelScheduler

typealias ClassPathContentResource = co.mercenary.creators.kotlin.util.io.ClassPathContentResource

typealias DefaultContentResourceLoader = co.mercenary.creators.kotlin.util.io.DefaultContentResourceLoader

open class MercenaryExceptiion(text: String?, root: Throwable?) : RuntimeException(text, root) {
    constructor(text: String) : this(text, null)
    constructor(root: Throwable) : this(null, root)
    constructor(func: () -> String) : this(func(), null)

    companion object {
        private const val serialVersionUID = 2L
    }
}

open class MercenaryFatalExceptiion(text: String?, root: Throwable?) : MercenaryExceptiion(text, root) {
    constructor(text: String) : this(text, null)
    constructor(root: Throwable) : this(null, root)
    constructor(func: () -> String) : this(func(), null)

    companion object {
        private const val serialVersionUID = 2L
    }
}

private const val METADATA_FQ_NAME = "kotlin.Metadata"

fun Class<*>.isKotlinClass(): Boolean {
    return declaredAnnotations.any { it.annotationClass.java.name == METADATA_FQ_NAME }
}

val Int.days: TimeDuration
    get() = TimeDuration.days(this)

val Int.hours: TimeDuration
    get() = TimeDuration.hours(this)

val Int.weeks: TimeDuration
    get() = TimeDuration.weeks(this)

val Int.years: TimeDuration
    get() = TimeDuration.years(this)

val Int.minutes: TimeDuration
    get() = TimeDuration.minutes(this)

val Int.seconds: TimeDuration
    get() = TimeDuration.seconds(this)

val Int.milliseconds: TimeDuration
    get() = TimeDuration.milliseconds(this)

val Int.nanoseconds: TimeDuration
    get() = TimeDuration.nanoseconds(this)

val Long.days: TimeDuration
    get() = TimeDuration.days(this)

val Long.hours: TimeDuration
    get() = TimeDuration.hours(this)

val Long.weeks: TimeDuration
    get() = TimeDuration.weeks(this)

val Long.years: TimeDuration
    get() = TimeDuration.years(this)

val Long.minutes: TimeDuration
    get() = TimeDuration.minutes(this)

val Long.seconds: TimeDuration
    get() = TimeDuration.seconds(this)

val Long.milliseconds: TimeDuration
    get() = TimeDuration.milliseconds(this)

val Long.nanoseconds: TimeDuration
    get() = TimeDuration.nanoseconds(this)

fun uuid(): String = UUID.randomUUID().toString()

fun getProcessors(): Int = Runtime.getRuntime().availableProcessors()

fun getTimeStamp(nano: Boolean = false): Long = if (nano) System.nanoTime() else System.currentTimeMillis()

fun getLowerTrim(data: String): String = data.trim().toLowerCase()

fun toTrimOrNull(data: String?): String? = data?.trim().takeUnless { it.isNullOrEmpty() }

fun toTrimOrElse(data: String?, other: () -> String): String = toTrimOrNull(data) ?: other()

fun toTrimOrElse(data: String?, other: String = EMPTY_STRING): String = toTrimOrNull(data) ?: other

fun toDecimalPlaces3(data: Double, tail: String = EMPTY_STRING): String = "(%.3f)%s".format(data, tail)

fun toElapsedString(data: Long): String = if (data < 1000000L) "($data) nanoseconds." else if (data < 1000000000L) toDecimalPlaces3(1.0E-6 * data, " milliseconds.") else toDecimalPlaces3(1.0E-9 * data, " seconds.")

fun toSafeString(block: () -> Any?): String = try {
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
        if (data[posn] == CHAR_INVALID) {
            throw MercenaryFatalExceptiion("Null byte present in file/path name. There are no known legitimate use cases for such data, but several injection attacks may use it.")
        }
    }
    return data
}

fun sleepFor(duration: Int, unit: TimeUnit = TimeUnit.MILLISECONDS) {
    if (duration > 0) {
        try {
            unit.sleep(duration.toLong())
        }
        catch (cause: Throwable) {
            Throwables.thrown(cause)
        }
    }
}

fun sleepFor(duration: Long, unit: TimeUnit = TimeUnit.MILLISECONDS) {
    if (duration > 0) {
        try {
            unit.sleep(duration)
        }
        catch (cause: Throwable) {
            Throwables.thrown(cause)
        }
    }
}

fun sleepFor(duration: TimeDuration) {
    duration.sleepFor()
}

fun Date?.copyOf(): Date = when (this) {
    null -> Date()
    else -> Date(time)
}

fun String.toLowerTrim(): String = getLowerTrim(this)

fun isDefaultContentType(type: String): Boolean = type.toLowerTrim() == DEFAULT_CONTENT_TYPE

fun toCommonContentTypes(name: String, type: String = DEFAULT_CONTENT_TYPE): String = when (IO.getPathExtension(name).toLowerTrim()) {
    ".json" -> "application/json"
    ".java" -> "text/x-java-source"
    ".yaml" -> "application/x-yaml"
    ".yml" -> "application/x-yaml"
    ".properties" -> "text/x-java-properties"
    else -> type.toLowerTrim()
}

fun getDefaultContentTypeProbe(): ContentTypeProbe = IO.getContentTypeProbe()

fun getPathNormalizedOrElse(path: String?, other: String = EMPTY_STRING): String = toTrimOrElse(IO.getPathNormalized(path), other)

fun getPathNormalizedNoTail(path: String?, tail: Boolean): String {
    val norm = getPathNormalizedOrElse(path)
    if ((tail) && (norm.startsWith(IO.SINGLE_SLASH))) {
        return norm.substring(1)
    }
    return norm
}

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
    else -> true
}

fun isValid(block: () -> Any?): Boolean = try {
    isValid(block())
}
catch (cause: Throwable) {
    Throwables.thrown(cause)
    false
}

fun powNegative1(value: Int): Double = Numeric.powNegative1(value)

fun powNegative1(value: Long): Double = Numeric.powNegative1(value)

fun powNegative1(value: Int, other: Int): Double = Numeric.powNegative1(value, other)

fun pow2Round(value: Int, down: Boolean = true): Int = Numeric.pow2Round(value, down)

fun pow2Round(value: Long, down: Boolean = true): Long = Numeric.pow2Round(value, down)

fun gcd(vararg args: Int): Int = Numeric.gcd(args)

fun gcd(vararg args: Long): Long = Numeric.gcd(args)

fun gcd(value: Int): Int = abs(value)

fun gcd(value: Long): Long = abs(value)

fun gcd(value: Int, other: Int): Int = Numeric.gcd(value, other)

fun gcd(value: Long, other: Long): Long = Numeric.gcd(value, other)

fun gcd(value: Int, other: Long): Long = Numeric.gcd(value.toLong(), other)

fun gcd(value: Long, other: Int): Long = Numeric.gcd(value, other.toLong())

fun lcm(vararg args: Int): Int = Numeric.lcm(args)

fun lcm(vararg args: Long): Long = Numeric.lcm(args)

fun lcm(value: Int): Int = abs(value)

fun lcm(value: Long): Long = abs(value)

fun lcm(value: Int, other: Int): Int = Numeric.lcm(value, other)

fun lcm(value: Long, other: Long): Long = Numeric.lcm(value, other)

fun lcm(value: Int, other: Long): Long = Numeric.lcm(value.toLong(), other)

fun lcm(value: Long, other: Int): Long = Numeric.lcm(value, other.toLong())

fun Double.toDecimalPlacesString(scale: Int = 2, places: Int = abs(scale)): String {
    return Numeric.toDecimalPlacesString(this, scale, places)
}

fun Double.closeEnough(value: Double, precision: Double = Numeric.DEFAULT_PRECISION): Boolean {
    return Numeric.closeEnough(this, value, precision)
}

fun DoubleArray.closeEnough(value: DoubleArray, precision: Double = Numeric.DEFAULT_PRECISION): Boolean {
    return Numeric.closeEnough(this, value, precision)
}

fun DVector.closeEnough(value: DVector, precision: Double = Numeric.DEFAULT_PRECISION): Boolean {
    return Numeric.closeEnough(this, value, precision)
}

fun DMatrix.closeEnough(value: DMatrix, precision: Double = Numeric.DEFAULT_PRECISION): Boolean {
    return Numeric.closeEnough(this, value, precision)
}

fun DMatrixArray.closeEnough(value: DMatrixArray, precision: Double = Numeric.DEFAULT_PRECISION): Boolean {
    return Numeric.closeEnough(this, value, precision)
}

fun closeEnough(v1: Double, v2: Double, o1: Double, o2: Double, precision: Double = Numeric.DEFAULT_PRECISION): Boolean {
    return Numeric.closeEnough(v1, v2, o1, o2, precision)
}

fun Double.rounded(scale: Int = 2): Double = Numeric.rounded(this, scale)

fun Double.root(root: Int = 2): Double = Numeric.root(this, root)

fun toDoubleArrayOf(vararg args: Double): DoubleArray = doubleArrayOf(*args)

fun toDoubleArrayOf(vararg args: Number): DoubleArray = DoubleArray(args.size) { i -> args[i].toDouble() }

fun toArrayOfDoubleArray(cols: Int, args: DoubleArray): DMatrix {
    return if (cols < 1) throw MercenaryFatalExceptiion("invalid size") else toArrayOfDoubleArray(args.size / cols, cols, args)
}

fun toArrayOfDoubleArray(rows: Int, cols: Int, args: DoubleArray): DMatrix {
    return if (args.size != (rows * cols)) throw MercenaryFatalExceptiion("invalid size")
    else Array(rows) { r ->
        (r * cols).let { args.copyOfRange(it, it + cols) }
    }
}

fun squared(value: Double): Double = (value * value)

fun distance(dx: Double, dy: Double): Double = sqrt((dx * dx) + (dy * dy))

fun Date.toLong(): Long = this.time

fun Long.toDate(): Date = Date(max(this, 0))

fun Long.toAtomic(): AtomicLong = AtomicLong(this)

fun AtomicLong.toValue(): Long = this.get()

fun Int.toAtomic(): AtomicInteger = AtomicInteger(this)

fun AtomicInteger.toValue(): Int = this.get()

fun Boolean.toAtomic(): AtomicBoolean = AtomicBoolean(this)

fun AtomicBoolean.toValue(): Boolean = this.get()

fun AtomicLong.assign(value: Long): Boolean {
    return this.compareAndSet(value, value)
}

fun AtomicInteger.assign(value: Int): Boolean {
    return this.compareAndSet(value, value)
}

fun AtomicBoolean.assign(value: Boolean): Boolean {
    return this.compareAndSet(value, value)
}

operator fun AtomicInteger.inc(): AtomicInteger {
    this.incrementAndGet()
    return this
}

operator fun AtomicInteger.dec(): AtomicInteger {
    this.decrementAndGet()
    return this
}

operator fun AtomicInteger.plusAssign(delta: Int) {
    this.addAndGet(delta)
}

operator fun AtomicInteger.plusAssign(delta: AtomicInteger) {
    this.addAndGet(delta.get())
}

operator fun AtomicInteger.minusAssign(delta: Int) {
    this.addAndGet(delta)
}

operator fun AtomicInteger.minusAssign(delta: AtomicInteger) {
    this.addAndGet(delta.get())
}

operator fun AtomicLong.inc(): AtomicLong {
    this.incrementAndGet()
    return this
}

operator fun AtomicLong.dec(): AtomicLong {
    this.decrementAndGet()
    return this
}

operator fun AtomicLong.plusAssign(delta: Int) {
    this.addAndGet(delta.toLong())
}

operator fun AtomicLong.plusAssign(delta: Long) {
    this.addAndGet(delta)
}

operator fun AtomicLong.minusAssign(delta: Int) {
    this.addAndGet(delta.toLong())
}

operator fun AtomicLong.minusAssign(delta: Long) {
    this.addAndGet(delta)
}

fun <T> timed(after: (String) -> Unit, block: () -> T): T = NanoTicker().let { block().also { after(it(false)) } }

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

fun isFileURL(data: URL): Boolean = getLowerTrim(data.toString()).startsWith(IO.PREFIX_FILES)

fun getTempFile(prefix: String, suffix: String? = null, folder: File? = null): File = createTempFile(prefix, suffix, folder).apply { deleteOnExit() }

fun File.isSame(other: File): Boolean = isSame(other.toPath())

fun File.isSame(other: Path): Boolean = toPath().isSame(other)

fun Path.isSame(other: File): Boolean = isSame(other.toPath())

fun Path.isSame(other: Path): Boolean = Files.isSameFile(this, other)

fun File.isValidToRead(): Boolean = exists() && isFile && canRead()

fun Path.isValidToRead(): Boolean = toFile().isValidToRead()

fun URL.toInputStream(): InputStream = when (val data = IO.getInputStream(this)) {
    null -> throw MercenaryExceptiion(toString())
    else -> data
}

fun ByteArray.toInputStream(): ByteArrayInputStream = ByteArrayInputStream(this)

fun File.toInputStream(vararg args: OpenOption): InputStream = toPath().toInputStream(*args)

fun Path.toInputStream(vararg args: OpenOption): InputStream = if (isValidToRead()) Files.newInputStream(this, *args) else throw MercenaryExceptiion(toString())

fun ReadableByteChannel.toInputStream(): InputStream = Channels.newInputStream(this)

fun InputStreamSupplier.toInputStream(): InputStream = getInputStream()

fun Reader.toInputStream(): InputStream = ReaderInputStream(this)

fun URL.toOutputStream(): OutputStream = when (val data = IO.getOutputStream(this)) {
    null -> throw MercenaryExceptiion(toString())
    else -> data
}

fun File.toOutputStream(vararg args: OpenOption): OutputStream = toPath().toOutputStream(*args)

fun Path.toOutputStream(vararg args: OpenOption): OutputStream = Files.newOutputStream(this, *args)

fun URL.toByteArray(): ByteArray = readBytes()

fun InputStream.toByteArray(): ByteArray = use { it.readBytes() }

fun File.toByteArray(): ByteArray = toInputStream().toByteArray()

fun <T : Any> Flux<T>.toList(): List<T> = collect(Collectors.toList<T>()).block().orElse { emptyList() }

fun <T : Any> Flux<T>.limit(size: Long): Flux<T> = limitRequest(size)

fun <T : Any> Flux<T>.limit(size: Int): Flux<T> = limitRequest(size.toLong())

fun <T : Any> Flux<T>.rated(size: Int): Flux<T> = limitRate(size)

fun <T : Any> Flux<T>.rated(high: Int, lows: Int): Flux<T> = limitRate(high, lows)

fun <T : Any> Flux<T>.cache(time: TimeDuration): Flux<T> = cache(time.duration)

fun <T : Any> Flux<T>.cache(size: Int, time: TimeDuration): Flux<T> = cache(size, time.duration)

fun <T : Any> T.toMono(): Mono<T> = Mono.just(this)

fun <T> Throwable.toMono(): Mono<T> = Mono.error(this)

fun <T> Publisher<T>.toMono(): Mono<T> = Mono.from(this)

fun <T> (() -> T?).toMono(): Mono<T> = Mono.fromSupplier(this)

fun <T> Callable<T?>.toMono(): Mono<T> = Mono.fromCallable(this::call)

fun <T> CompletableFuture<out T?>.toMono(): Mono<T> = Mono.fromFuture(this)

inline fun <reified T : Any> Mono<*>.cast(): Mono<T> {
    return this.cast(T::class.java)
}

inline fun <reified T : Any> Mono<*>.ofType(): Mono<T> {
    return ofType(T::class.java)
}

fun <T> Throwable.toFlux(): Flux<T> = Flux.error(this)

fun <T> Array<out T>.toFlux(): Flux<T> = Flux.fromArray(this)

fun <T : Any> Publisher<T>.toFlux(): Flux<T> = Flux.from(this)

fun <T : Any> Iterable<T>.toFlux(): Flux<T> = Flux.fromIterable(this)

fun <T : Any> Sequence<T>.toFlux(): Flux<T> = Flux.fromIterable(object : Iterable<T> {
    override fun iterator(): Iterator<T> = this@toFlux.iterator()
})

inline fun <reified T : Any> Flux<*>.cast(): Flux<T> {
    return cast(T::class.java)
}

inline fun <reified T : Any> Flux<*>.ofType(): Flux<T> {
    return ofType(T::class.java)
}

fun <T : Any> Flux<out Iterable<T>>.split(): Flux<T> = flatMapIterable { it }

fun Path.toByteArray(): ByteArray = toInputStream().toByteArray()

fun Reader.toByteArray(): ByteArray = toInputStream().toByteArray()

fun InputStreamSupplier.toByteArray(): ByteArray = when (this) {
    is ContentResource -> getContentData()
    else -> getInputStream().toByteArray()
}

fun ContentResource.cache() = if (isContentCache()) this else toContentCache()

fun <T : Any> T?.orElse(block: () -> T): T = this ?: block()

val RESOURCE_LOADER = DefaultContentResourceLoader()

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