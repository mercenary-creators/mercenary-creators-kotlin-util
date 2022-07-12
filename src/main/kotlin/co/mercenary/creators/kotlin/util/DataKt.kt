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

@file:JvmName("DataKt")
@file:Suppress("NOTHING_TO_INLINE", "FunctionName", "HttpUrlsUsage")

package co.mercenary.creators.kotlin.util

import co.mercenary.creators.kotlin.util.io.*
import java.io.*
import java.net.*
import java.nio.*
import java.nio.channels.*
import java.nio.charset.Charset
import java.nio.file.*
import java.nio.file.attribute.FileTime
import java.util.*
import java.util.stream.IntStream

typealias SystemProperties = Properties

typealias DefaultContentTypeProbe = MimeContentTypeProbe

typealias DefaultContentFileTypeMap = ContentFileTypeMap

typealias IO = co.mercenary.creators.kotlin.util.io.IO

typealias BytesOutputStream = co.mercenary.creators.kotlin.util.io.BytesOutputStream

typealias EmptyOutputStream = co.mercenary.creators.kotlin.util.io.EmptyOutputStream

typealias Escapers = co.mercenary.creators.kotlin.util.text.Escapers

typealias Formatters = co.mercenary.creators.kotlin.util.text.Formatters

typealias Maybe = Any?

typealias Factory<T> = () -> T

typealias Convert<T, R> = (T) -> R

typealias Indexed<T, R> = (index: Int, T) -> R

typealias Applier<T, R> = T.() -> R

typealias LazyMessage = Factory<Maybe>

typealias CallBlock = () -> Unit

typealias ContentResourceLookup = Convert<String, ContentResource>

@FrameworkDsl
const val DEFAULT_BUFFERED_SIZE = 32

@FrameworkDsl
const val MINIMUM_BUFFERED_SIZE = 16

@FrameworkDsl
const val MAXIMUM_BUFFERED_SIZE = Int.MAX_VALUE - 8

@FrameworkDsl
const val DEFAULT_BUFFERED_DATA_SIZE = DEFAULT_BUFFER_SIZE

@FrameworkDsl
const val DEFAULT_CONTENT_TYPE = "application/octet-stream"

@FrameworkDsl
const val SINGLE_DOT_CHAR = '.'

@FrameworkDsl
const val SINGLE_DOT_STRING = "."

@FrameworkDsl
val DEFAULT_CONTENT_RESOURCE_LOADER: ContentResourceLoader
    get() = BaseContentResourceLoader.INSTANCE

@FrameworkDsl
val CACHED_CONTENT_RESOURCE_LOADER: CachedContentResourceLoader
    get() = BaseCachedContentResourceLoader.INSTANCE

@FrameworkDsl
val DEFAULT_CHARSET_UTF_8 = Charsets.UTF_8

@FrameworkDsl
val DEFAULT_CHARSET_UTF_16 = Charsets.UTF_16

@FrameworkDsl
val DEFAULT_LOCALE: Locale
    get() = Locale.getDefault()

@FrameworkDsl
val ENGLISH_LOCALE: Locale
    get() = Locale.ENGLISH

@FrameworkDsl
val BREAK_STRING: String = System.lineSeparator()

@FrameworkDsl
val EMPTY_INTS_ARRAY = IntArray(0)

@FrameworkDsl
val EMPTY_LONG_ARRAY = LongArray(0)

@FrameworkDsl
val EMPTY_BYTE_ARRAY = ByteArray(0)

@FrameworkDsl
val EMPTY_CHAR_ARRAY = CharArray(0)

@FrameworkDsl
val EMPTY_REAL_ARRAY = DoubleArray(0)

@FrameworkDsl
val EMPTY_BOOL_ARRAY = BooleanArray(0)

@FrameworkDsl
inline fun getContentResourceLoader(): ContentResourceLoader = DEFAULT_CONTENT_RESOURCE_LOADER

@FrameworkDsl
inline fun getCachedContentResourceLoader(): CachedContentResourceLoader = CACHED_CONTENT_RESOURCE_LOADER

@FrameworkDsl
inline fun getContentResourceByPath(path: CharSequence): ContentResource = DEFAULT_CONTENT_RESOURCE_LOADER[path.copyOf()]

@FrameworkDsl
inline fun getCachedContentResourceByPath(path: CharSequence): ContentResource = CACHED_CONTENT_RESOURCE_LOADER[path.copyOf()]

@FrameworkDsl
inline fun getStandardInput(): InputStream = System.`in`

@FrameworkDsl
inline fun getStandardOutut(): PrintStream = System.out

@FrameworkDsl
inline fun getStandardError(): PrintStream = System.err

@FrameworkDsl
fun PrintStream.echo(data: Maybe): PrintStream {
    print(data.otherwise(NULLS_STRING))
    return this
}

@FrameworkDsl
fun PrintStream.echo(vararg args: Maybe): PrintStream {
    if (args.isNotExhausted()) {
        args.forEach { data -> print(data.otherwise(NULLS_STRING)) }
    }
    return this
}

@FrameworkDsl
fun PrintStream.spaces(many: Int = 1): PrintStream {
    print(SPACE_STRING.repeat(many.maxOf(0)))
    return this
}

@FrameworkDsl
fun PrintStream.newline(): PrintStream {
    println()
    return this
}

@FrameworkDsl
inline fun <T> Factory<T>.create() = invoke()

@FrameworkDsl
inline fun <T, R> Convert<T, R>.convert(value: T): R = invoke(value)

@FrameworkDsl
inline fun <T, R> Indexed<T, R>.indexed(index: Int, value: T): R = invoke(index, value)

@FrameworkDsl
inline fun <T, R> T.withIn(block: T.() -> R): R = with(this, block)

@FrameworkDsl
fun stringOfChars(vararg args: Char): String {
    if (args.isExhausted()) {
        return EMPTY_STRING
    }
    return String(args)
}

@FrameworkDsl
fun stringOfChars(args: Iterable<Char>): String {
    if (args.isExhausted()) {
        return EMPTY_STRING
    }
    return String(args.toCollection().toCharArray())
}

@FrameworkDsl
inline fun Char.isNotLetter(): Boolean = isLetter().isNotTrue()

@FrameworkDsl
inline fun Char.isNotInMath(): Boolean = this != '.' && this != '+' && this != '-'

@FrameworkDsl
inline fun Char.isNotLetterOrDigit(): Boolean = isLetterOrDigit().isNotTrue()

@FrameworkDsl
inline fun String.isDefaultContentType(): Boolean = toLowerTrimEnglish() == DEFAULT_CONTENT_TYPE

@FrameworkDsl
fun String.toCommonContentType(type: String = DEFAULT_CONTENT_TYPE): String = when (IO.getPathExtension(this).toLowerTrimEnglish()) {
    ".json" -> "application/json"
    ".java" -> "text/x-java-source"
    ".css", ".scss" -> "text/css"
    ".htm", ".html" -> "text/html"
    ".yml", ".yaml" -> "application/x-yaml"
    ".properties" -> "text/x-java-properties"
    else -> type.toLowerTrimEnglish()
}

@FrameworkDsl
inline fun getDefaultContentTypeProbe(): ContentTypeProbe = IO.getContentTypeProbe()

@FrameworkDsl
inline fun isSystemWindows(): Boolean = IO.isSystemWindows()

@FrameworkDsl
inline fun isSystemUnixLike(): Boolean = IO.isSystemUnixLike()

@FrameworkDsl
fun getPathNormalizedOrElse(path: String?, other: String = EMPTY_STRING): String = toTrimOrElse(IO.getPathNormalized(path), other)

@FrameworkDsl
fun getPathNormalizedNoTail(path: String?, tail: Boolean): String {
    val norm = getPathNormalizedOrElse(path)
    if ((tail) && (norm.startsWith(IO.SINGLE_SLASH))) {
        return norm.head(1)
    }
    return norm
}

@FrameworkDsl
inline fun URL.isValidProtocol(): Boolean = toProtocol().let { buff ->
    if (buff.isExhausted() || buff.headOf().isNotLetter()) {
        false
    } else {
        buff.head(1).none { char -> char.isNotLetterOrDigit() && char.isNotInMath() }
    }
}

@FrameworkDsl
inline fun URL.isFileURL(): Boolean = toProtocol().toLowerTrimEnglish() == IO.TYPE_IS_FILE

@FrameworkDsl
inline fun URL.toFileOrNull(skip: Boolean = false): File? = IO.toFileOrNull(this, skip)

@FrameworkDsl
fun getTempFile(prefix: String, suffix: String? = null, folder: File? = null): File = File.createTempFile(prefix, suffix, folder).apply { deleteOnExit() }

@FrameworkDsl
inline fun Exhausted.isNotExhausted(): Boolean = isExhausted().isNotTrue()

@FrameworkDsl
inline fun File.isFolder(): Boolean = isDirectory.isTrue()

@FrameworkDsl
inline fun File.isNotFolder(): Boolean = isFolder().isNotTrue()

@FrameworkDsl
inline fun Path.isFolder(): Boolean = fileOf().isFolder()

@FrameworkDsl
inline fun Path.isNotFolder(): Boolean = isFolder().isNotTrue()

@FrameworkDsl
inline fun File.isContentThere(): Boolean = exists()

@FrameworkDsl
inline fun Path.isContentThere(): Boolean = fileOf().isContentThere()

@FrameworkDsl
inline fun File.isValidToRead(): Boolean = isNotFolder() && canRead()

@FrameworkDsl
inline fun Path.isValidToRead(): Boolean = fileOf().isValidToRead()

@FrameworkDsl
infix fun File.isSameFile(other: File): Boolean = isSameFile(other.pathOf())

@FrameworkDsl
infix fun File.isSameFile(other: Path): Boolean = pathOf().isSameFile(other)

@FrameworkDsl
infix fun Path.isSameFile(other: File): Boolean = isSameFile(other.pathOf())

@FrameworkDsl
infix fun Path.isSameFile(other: Path): Boolean = Files.isSameFile(this, other)

@FrameworkDsl
infix fun File.isSameFileAndData(other: File): Boolean = isSameFileAndData(other.pathOf())

@FrameworkDsl
infix fun File.isSameFileAndData(other: Path): Boolean = pathOf().isSameFileAndData(other)

@FrameworkDsl
infix fun Path.isSameFileAndData(other: File): Boolean = isSameFileAndData(other.pathOf())

@FrameworkDsl
infix fun Path.isSameFileAndData(other: Path): Boolean = isSameFile(other) || fileOf().compareTo(other.fileOf()) == 0

@FrameworkDsl
fun URL.toInputStream(): InputStream = when (val data = IO.getInputStream(this)) {
    null -> throw MercenaryExceptiion(toString())
    else -> data
}

@FrameworkDsl
fun URI.toInputStream(): InputStream = when (val data = IO.getInputStream(this)) {
    null -> throw MercenaryExceptiion(toString())
    else -> data
}

@FrameworkDsl
inline fun <T> Array<T>.toArray(copy: Boolean = false): Array<T> = if (copy.isTrue() && sizeOf() > 0) copyOf() else this

@FrameworkDsl
fun ByteArray.toByteBuffer(copy: Boolean = false): ByteBuffer = ByteBuffer.wrap(toByteArray(copy))

@FrameworkDsl
fun CharArray.toCharBuffer(copy: Boolean = false): CharBuffer = CharBuffer.wrap(toCharArray(copy))

@FrameworkDsl
fun getByteBuffer(size: Int, heap: Boolean = true): ByteBuffer {
    return when (heap.isTrue()) {
        true -> ByteBuffer.allocate(size.maxOf(0))
        else -> ByteBuffer.allocateDirect(size.maxOf(0))
    }
}

@FrameworkDsl
fun getFileTimeOfUnit(time: Int, unit: SystemTimeUnit = SYSTEM_TIME_UNIT_MILLISECONDS): FileTime {
    return getFileTimeOfUnit(time.longOf(), unit)
}

@FrameworkDsl
fun getFileTimeOfUnit(time: Long, unit: SystemTimeUnit = SYSTEM_TIME_UNIT_MILLISECONDS): FileTime {
    return FileTime.from(time, unit)
}

@FrameworkDsl
inline fun getIntStream(size: Int): IntStream {
    return IntStream.range(0, size - 1).parallel()
}

@FrameworkDsl
fun IntStream.forEachIndexed(action: (Int) -> Unit) {
    forEach(action)
}

@FrameworkDsl
fun <T> Array<T>.parallel(convert: Convert<Int, T>) {
    if (isNotExhausted()) {
        getIntStream(sizeOf()).forEachIndexed { index ->
            this[index] = try {
                convert.convert(index)
            } catch (cause: Throwable) {
                Throwables.fatal(cause, this[index])
            }
        }
    }
}

@FrameworkDsl
fun <T> Array<T>.computed(computer: Computed<T>) {
    if (isNotExhausted()) {
        getIntStream(sizeOf()).forEachIndexed { index ->
            try {
                this[index] = computer.compute(index, this[index])
            } catch (cause: Throwable) {
                Throwables.check(cause)
            }
        }
    }
}

@FrameworkDsl
fun IntArray.parallel(default: Int = 0, convert: Convert<Int, Int>) {
    if (isNotExhausted()) {
        getIntStream(sizeOf()).forEachIndexed { index ->
            this[index] = try {
                convert.convert(index)
            } catch (cause: Throwable) {
                Throwables.fatal(cause, default)
            }
        }
    }
}

@FrameworkDsl
fun IntArray.computed(default: Int = 0, computer: Computed<Int>) {
    if (isNotExhausted()) {
        getIntStream(sizeOf()).forEachIndexed { index ->
            this[index] = try {
                computer.compute(index, this[index])
            } catch (cause: Throwable) {
                Throwables.fatal(cause, default)
            }
        }
    }
}

@FrameworkDsl
fun LongArray.parallel(default: Long = 0L, convert: Convert<Int, Long>) {
    if (isNotExhausted()) {
        getIntStream(sizeOf()).forEachIndexed { index ->
            this[index] = try {
                convert.convert(index)
            } catch (cause: Throwable) {
                Throwables.fatal(cause, default)
            }
        }
    }
}

@FrameworkDsl
fun LongArray.computed(default: Long = 0L, computer: Computed<Long>) {
    if (isNotExhausted()) {
        getIntStream(sizeOf()).forEachIndexed { index ->
            this[index] = try {
                computer.compute(index, this[index])
            } catch (cause: Throwable) {
                Throwables.fatal(cause, default)
            }
        }
    }
}

@FrameworkDsl
fun DoubleArray.parallel(default: Double = 0.0, convert: Convert<Int, Double>) {
    if (isNotExhausted()) {
        getIntStream(sizeOf()).forEachIndexed { index ->
            this[index] = try {
                convert.convert(index)
            } catch (cause: Throwable) {
                Throwables.fatal(cause, default)
            }
        }
    }
}

@FrameworkDsl
fun DoubleArray.computed(default: Double = 0.0, computer: Computed<Double>) {
    if (isNotExhausted()) {
        getIntStream(sizeOf()).forEachIndexed { index ->
            this[index] = try {
                computer.compute(index, this[index])
            } catch (cause: Throwable) {
                Throwables.fatal(cause, default)
            }
        }
    }
}

@FrameworkDsl
inline fun toByteArrayOfInt(data: Byte): ByteArray = toByteArrayOfInt(data.toMaskedInt())

@FrameworkDsl
inline fun toByteArrayOfInt(data: Char): ByteArray = toByteArrayOfInt(data.toMaskedInt())

@FrameworkDsl
inline fun toByteArrayOfInt(data: Long): ByteArray = toByteArrayOfInt(data.toMaskedInt())

@FrameworkDsl
inline fun toByteArrayOfInt(data: Int): ByteArray = getByteBuffer(Int.SIZE_BYTES).putInt(data).toByteArray(false)

@FrameworkDsl
fun CharSequence.toCharBuffer(beg: Int = 0, end: Int = sizeOf()): CharBuffer = CharBuffer.wrap(copyOf(), beg, end)

@FrameworkDsl
fun CharSequence.getCharArray(copy: Boolean = false): CharArray = copyOf().toCharArray().toCharArray(copy)

@FrameworkDsl
fun ByteArray.toInputStream(copy: Boolean = false): InputStream = ByteArrayInputStream(toByteArray(copy))

@FrameworkDsl
fun File.toInputStream(): InputStream = if (isValidToRead()) FileInputStream(this) else throw MercenaryExceptiion(toString())

@FrameworkDsl
fun Path.toInputStream(): InputStream = if (isValidToRead()) FileInputStream(fileOf()) else throw MercenaryExceptiion(toString())

@FrameworkDsl
inline fun ReadableByteChannel.toInputStream(): InputStream = Channels.newInputStream(this)

@FrameworkDsl
inline fun InputStreamSupplier.toInputStream(): InputStream = getInputStream()

@FrameworkDsl
fun Reader.toInputStream(charset: Charset = DEFAULT_CHARSET_UTF_8): InputStream = IO.getInputStream(this, charset)

@FrameworkDsl
fun Writer.toOutputStream(charset: Charset = DEFAULT_CHARSET_UTF_8): OutputStream = IO.getOutputStream(this, charset)

@FrameworkDsl
fun URL.toOutputStream(): OutputStream = when (val data = IO.getOutputStream(this)) {
    null -> throw MercenaryExceptiion(toString())
    else -> data
}

@FrameworkDsl
fun URI.toOutputStream(): OutputStream = when (val data = IO.getOutputStream(this)) {
    null -> throw MercenaryExceptiion(toString())
    else -> data
}

@FrameworkDsl
inline fun File.isValidToWrite(): Boolean = isNotFolder() && canWrite()

@FrameworkDsl
inline fun Path.isValidToWrite(): Boolean = fileOf().isValidToWrite()

@FrameworkDsl
fun File.toOutputStream(append: Boolean = false): FileOutputStream = pathOf().toOutputStream(append.copyOf())

@FrameworkDsl
fun Path.toOutputStream(append: Boolean = false): FileOutputStream = if (isValidToWrite()) FileOutputStream(fileOf(), append.copyOf()) else throw MercenaryExceptiion(toString())

@FrameworkDsl
inline fun WritableByteChannel.toOutputStream(): OutputStream = Channels.newOutputStream(this)

@FrameworkDsl
inline fun OutputStreamSupplier.toOutputStream(): OutputStream = getOutputStream()

@FrameworkDsl
fun File.toWriter(append: Boolean = false, charset: Charset = DEFAULT_CHARSET_UTF_8): OutputStreamWriter = toOutputStream(append).toWriter(charset)

@FrameworkDsl
fun Path.toWriter(append: Boolean = false, charset: Charset = DEFAULT_CHARSET_UTF_8): OutputStreamWriter = toOutputStream(append).toWriter(charset)

@FrameworkDsl
fun OutputStream.sendTo(args: String) {
    toWriter().use { stream ->
        args.withSize { data, size ->
            when (size.isLessSame(DEFAULT_BUFFERED_DATA_SIZE)) {
                true -> stream.write(data)
                else -> data.chunkedSequence(DEFAULT_BUFFERED_DATA_SIZE).forEach {
                    stream.write(it)
                }
            }
        }
        stream.flush()
    }
}

@FrameworkDsl
inline infix fun Reader.forEachLineIndexed(block: (Int, String) -> Unit) {
    buffered().forEachLineIndexed(block)
}

@FrameworkDsl
inline infix fun BufferedReader.forEachLineIndexed(block: (Int, String) -> Unit) {
    useLines { it.forEachIndexed(block) }
}

@FrameworkDsl
inline infix fun URL.forEachLineIndexed(block: (Int, String) -> Unit) {
    toInputStream().forEachLineIndexed(block)
}

@FrameworkDsl
inline infix fun URI.forEachLineIndexed(block: (Int, String) -> Unit) {
    toInputStream().forEachLineIndexed(block)
}

@FrameworkDsl
inline infix fun File.forEachLineIndexed(block: (Int, String) -> Unit) {
    toInputStream().forEachLineIndexed(block)
}

@FrameworkDsl
inline infix fun Path.forEachLineIndexed(block: (Int, String) -> Unit) {
    toInputStream().forEachLineIndexed(block)
}

@FrameworkDsl
inline infix fun InputStreamSupplier.forEachLineIndexed(block: (Int, String) -> Unit) {
    toInputStream().forEachLineIndexed(block)
}

@FrameworkDsl
inline infix fun InputStream.forEachLineIndexed(block: (Int, String) -> Unit) {
    toBufferedReader().forEachLineIndexed(block)
}

@FrameworkDsl
inline fun InputStream.toReader(charset: Charset = DEFAULT_CHARSET_UTF_8): InputStreamReader {
    return reader(charset)
}

@FrameworkDsl
inline fun OutputStream.toWriter(charset: Charset = DEFAULT_CHARSET_UTF_8): OutputStreamWriter {
    return writer(charset)
}

@FrameworkDsl
inline fun InputStream.toBufferedReader(charset: Charset = DEFAULT_CHARSET_UTF_8, size: Int = getBufferSize()): BufferedReader {
    return toReader(charset).buffered(size)
}

@FrameworkDsl
inline fun InputStream.toBufferedInputStream(size: Int = getBufferSize()): BufferedInputStream {
    return buffered(size.minOf(DEFAULT_BUFFERED_DATA_SIZE).maxOf(512))
}

@FrameworkDsl
fun InputStream.getBufferSize(most: Int = DEFAULT_BUFFERED_DATA_SIZE * 4): Int {
    if (this is EmptyInputStream) {
        return 0
    }
    return try {
        available().maxOf(DEFAULT_BUFFERED_DATA_SIZE).minOf(most)
    } catch (cause: Throwable) {
        Throwables.fatal(cause, DEFAULT_BUFFERED_DATA_SIZE)
    }
}

@FrameworkDsl
inline fun InputStream.toDataInput() = DataInputStream(this)

@FrameworkDsl
inline fun File.toDataInput() = toInputStream().toDataInput()

@FrameworkDsl
fun DataInputStream.readByteArray(data: ByteArray): ByteArray {
    use { it.readFully(data) }
    return data
}

@FrameworkDsl
fun InputStream.getAvailableSize(): Int {
    if (this is EmptyInputStream) {
        return 0
    }
    return try {
        available()
    } catch (cause: Throwable) {
        Throwables.fatal(cause, 0)
    }
}

@FrameworkDsl
inline fun URL.toProtocol(otherwise: String = EMPTY_STRING): String = protocol.otherwise(otherwise)

@FrameworkDsl
inline fun URL.getPathName(otherwise: String = EMPTY_STRING): String = path.otherwise(otherwise)

@FrameworkDsl
inline fun URL.getFileName(otherwise: String = EMPTY_STRING): String = file.otherwise(otherwise)

@FrameworkDsl
inline fun URL.toRelative(path: String): URL = IO.getRelative(this, path)

@FrameworkDsl
inline fun File.toRelative(path: String): File = IO.getRelative(this, path)

@FrameworkDsl
inline fun File.getPathName(otherwise: String = EMPTY_STRING): String = path.otherwise(otherwise)

@FrameworkDsl
inline fun File.getFileName(otherwise: String = EMPTY_STRING): String = absolutePath.otherwise(otherwise)

@FrameworkDsl
inline fun File.getContentSize(): Long {
    return try {
        length()
    } catch (cause: Throwable) {
        Throwables.fatal(cause, -1L)
    }
}

@FrameworkDsl
inline fun File.getLastModifiedFileTime(): FileTime {
    return pathOf().getLastModifiedFileTime()
}

@FrameworkDsl
inline fun File.getContentTime(): Long {
    return pathOf().getContentTime()
}

@FrameworkDsl
inline fun File.getContentDate(): Date {
    return pathOf().getContentDate()
}

@FrameworkDsl
inline fun Path.getLastModifiedFileTime(): FileTime {
    return try {
        Files.getLastModifiedTime(this)
    } catch (cause: Throwable) {
        Throwables.fatal(cause, getFileTimeOfUnit(0L))
    }
}

@FrameworkDsl
inline fun Path.getContentTime(): Long {
    return getLastModifiedFileTime().toMillis()
}

@FrameworkDsl
inline fun Path.getContentDate(): Date {
    return getContentTime().toDate()
}

@FrameworkDsl
inline fun File.pathOf(): Path = toPath()

@FrameworkDsl
inline fun Path.fileOf(): File = toFile()

@FrameworkDsl
inline fun File.linkOf(): URL = toURI().linkOf()

@FrameworkDsl
inline fun URI.linkOf(): URL = toURL()

@FrameworkDsl
inline fun CharSequence.linkOf(): URL = URL(copyOf())

@FrameworkDsl
inline fun CharSequence.fileOf(): File = File(copyOf())

@FrameworkDsl
inline fun URI.pathOf(): Path = Paths.get(this)

@FrameworkDsl
inline fun CharSequence.pathOf(): Path = Paths.get(copyOf())

@FrameworkDsl
inline fun URI.isAbsolutePath(): Boolean = isAbsolute.isTrue()

@FrameworkDsl
inline fun URI.isNotAbsolutePath(): Boolean = isAbsolute.isNotTrue()

@FrameworkDsl
inline fun Path.isAbsolutePath(): Boolean = isAbsolute.isTrue()

@FrameworkDsl
inline fun Path.isNotAbsolutePath(): Boolean = isAbsolute.isNotTrue()

@FrameworkDsl
inline operator fun Path.div(other: Path): Path = this.resolve(other)

@FrameworkDsl
inline operator fun Path.div(other: CharSequence): Path = this.resolve(other.copyOf())

@FrameworkDsl
inline fun CharSequence.toFileURL(): URL = IO.toFileURL(copyOf())

@FrameworkDsl
inline fun Int.toCharArray(): CharArray = if (maxOf(0).isZero()) EMPTY_CHAR_ARRAY else CharArray(this)

@FrameworkDsl
inline fun Int.toCharArray(char: Char): CharArray = if (maxOf(0).isZero()) EMPTY_CHAR_ARRAY else CharArray(this) { char }

@FrameworkDsl
inline fun Int.toCharArray(block: (Int) -> Char): CharArray = if (maxOf(0).isZero()) EMPTY_CHAR_ARRAY else CharArray(this) { block(it) }

@FrameworkDsl
inline fun Int.toByteArray(): ByteArray = if (maxOf(0).isZero()) EMPTY_BYTE_ARRAY else ByteArray(this)

@FrameworkDsl
inline fun Int.toByteArray(byte: Byte): ByteArray = if (maxOf(0).isZero()) EMPTY_BYTE_ARRAY else ByteArray(this) { byte }

@FrameworkDsl
inline fun Int.toByteArray(block: (Int) -> Byte): ByteArray = if (maxOf(0).isZero()) EMPTY_BYTE_ARRAY else ByteArray(this) { block(it) }

@FrameworkDsl
fun URL.toByteArray(): ByteArray = toInputStream().toByteArray()

@FrameworkDsl
fun URI.toByteArray(): ByteArray = toInputStream().toByteArray()

@FrameworkDsl
fun InputStream.toByteArray(): ByteArray = use { data -> data.readBytes() }

@FrameworkDsl
fun File.toByteArray(): ByteArray = readBytes()

@FrameworkDsl
fun Path.toByteArray(): ByteArray = fileOf().toByteArray()

@FrameworkDsl
fun Reader.toByteArray(charset: Charset = DEFAULT_CHARSET_UTF_8): ByteArray = toInputStream(charset).toByteArray()

@FrameworkDsl
fun ReadableByteChannel.toByteArray(): ByteArray = toInputStream().toByteArray()

@FrameworkDsl
fun InputStreamSupplier.toByteArray(): ByteArray = when (this) {
    is ContentResource -> getContentData()
    else -> getInputStream().toByteArray()
}

@FrameworkDsl
inline fun ByteArrayOutputStream.getContentData(): ByteArray = toByteArray()

@FrameworkDsl
inline fun ByteArrayOutputStream.clear(): Unit = reset()

@FrameworkDsl
fun ByteArray.getContentText(copy: Boolean = false, charset: Charset = DEFAULT_CHARSET_UTF_8): String {
    return if (isExhausted()) EMPTY_STRING else toByteArray(copy).toString(charset)
}

@FrameworkDsl
fun CharSequence.getContentData(charset: Charset = DEFAULT_CHARSET_UTF_8): ByteArray {
    return if (isExhausted()) EMPTY_BYTE_ARRAY else copyOf().toByteArray(charset)
}

@FrameworkDsl
fun ByteArray.toByteArray(copy: Boolean = true): ByteArray = if (copy.isTrue() && sizeOf() > 0) copyOf() else this

@FrameworkDsl
fun ByteBuffer.toByteArray(copy: Boolean = false): ByteArray {
    return if (hasArray()) array().toByteArray(copy) else EMPTY_BYTE_ARRAY
}

@FrameworkDsl
inline fun Int.toContentSize(): Long = longOf()

@FrameworkDsl
inline fun ByteArray.toContentSize(): Long = sizeOf().toContentSize()

@FrameworkDsl
fun CharArray.toCharArray(copy: Boolean = true): CharArray = if (copy.isTrue() && sizeOf() > 0) copyOf() else this

@FrameworkDsl
fun CharArray.toByteArray(copy: Boolean = true): ByteArray = toCharSequence(copy).getContentData()

@FrameworkDsl
fun CharArray.getContentData(copy: Boolean = true): ByteArray = toCharSequence(copy).getContentData()

@FrameworkDsl
fun CharArray.toCharSequence(copy: Boolean = true): CharSequence = if (isExhausted()) EMPTY_STRING else toCharArray(copy).concatToString()

@FrameworkDsl
fun Char.toCharSequence(): CharSequence = String(1.toCharArray(this))

@FrameworkDsl
fun CharArray.getContentText(copy: Boolean = true): String = toCharSequence(copy).copyOf()

@FrameworkDsl
fun IntArray.toIntArray(copy: Boolean = true): IntArray = if (copy.isTrue() && sizeOf() > 0) copyOf() else this

@FrameworkDsl
fun LongArray.toLongArray(copy: Boolean = true): LongArray = if (copy.isTrue() && sizeOf() > 0) copyOf() else this

@FrameworkDsl
fun ShortArray.toShortArray(copy: Boolean = true): ShortArray = if (copy.isTrue() && sizeOf() > 0) copyOf() else this

@FrameworkDsl
fun FloatArray.toFloatArray(copy: Boolean = true): FloatArray = if (copy.isTrue() && sizeOf() > 0) copyOf() else this

@FrameworkDsl
fun BooleanArray.toBooleanArray(copy: Boolean = true): BooleanArray = if (copy.isTrue() && sizeOf() > 0) copyOf() else this

@FrameworkDsl
inline fun Int.toIntArray(init: Int = 0): IntArray = if (this < 1) EMPTY_INTS_ARRAY else IntArray(this) { init }

@FrameworkDsl
fun Int.toIntArray(init: (Int) -> Int): IntArray = if (this < 1) EMPTY_INTS_ARRAY else IntArray(this, init)

@FrameworkDsl
inline fun Int.toLongArray(init: Long = 0L): LongArray = if (this < 1) EMPTY_LONG_ARRAY else LongArray(this) { init }

@FrameworkDsl
fun Int.toLongArray(init: (Int) -> Long): LongArray = if (this < 1) EMPTY_LONG_ARRAY else LongArray(this, init)

@FrameworkDsl
inline fun Int.toBooleanArray(init: Boolean = false): BooleanArray = if (this < 1) EMPTY_BOOL_ARRAY else BooleanArray(this) { init }

@FrameworkDsl
fun Int.toBooleanArray(init: (Int) -> Boolean): BooleanArray = if (this < 1) EMPTY_BOOL_ARRAY else BooleanArray(this, init)

@FrameworkDsl
inline fun URL.toContentResource() = URLContentResource(this)

@FrameworkDsl
inline fun URI.toContentResource() = URLContentResource(this)

@FrameworkDsl
inline fun File.toContentResource() = FileContentResource(this)

@FrameworkDsl
inline fun Path.toContentResource() = FileContentResource(this)

@FrameworkDsl
inline fun CharSequence.pluralOf(size: Int = sizeOf(), tail: String = EMPTY_STRING): String {
    return if (size == 1) "${this}$tail" else "${this}s$tail"
}

@FrameworkDsl
inline fun OpenCloseState.isClosed(): Boolean = isOpen().isNotTrue()

@FrameworkDsl
inline fun InsertOrdered.isNotOrdered(): Boolean = isOrdered().isNotTrue()

@FrameworkDsl
inline fun SizeCapped.isNotSizeCapped(): Boolean = isSizeCapped().isNotTrue()

@FrameworkDsl
inline fun Boolean.toInsertOrdered(): InsertOrdered = InsertOrdered { this }

@FrameworkDsl
inline fun Boolean.toSizeCapped(): SizeCapped = SizeCapped { this }

@FrameworkDsl
inline fun Int.toInsertLimited(): InsertLimited = InsertLimited { absOf() }

@FrameworkDsl
inline fun InsertLimited.isNegative(): Boolean = getLimit() < 0

@FrameworkDsl
inline fun InsertLimited.isNotNegative(): Boolean = getLimit() >= 0

@FrameworkDsl
fun <T> Iterable<T>.withInEach(block: T.() -> Unit) {
    forEach {
        it.withIn(block)
    }
}

@FrameworkDsl
fun <T> Iterable<T>.withEach(block: (T) -> Unit) {
    forEach {
        block(it)
    }
}

@FrameworkDsl
fun <T, R> Iterable<T>.withEachMake(block: (T) -> R): Iterable<R> {
    return BasicArrayList<R>().let { list ->
        forEach { item ->
            list.append(block(item))
        }
        list.toReadOnly()
    }
}

@FrameworkDsl
fun <T, R> Iterable<T>.withEachNotNull(block: (T) -> R?): Iterable<R> {
    return BasicArrayList<R>().let { list ->
        forEach { item ->
            block(item).also { each ->
                if (each != null) {
                    list.append(each)
                }
            }
        }
        list.toReadOnly()
    }
}

@FrameworkDsl
fun <T> Iterable<T>.withEachIndexed(block: (Int, T) -> Unit) {
    forEachIndexed { index, element ->
        block(index, element)
    }
}

@FrameworkDsl
fun File.forEachLine(parallel: Boolean = false): Sequence<String> {
    return pathOf().forEachLine(parallel)
}

@FrameworkDsl
fun Path.forEachLine(parallel: Boolean = false): Sequence<String> {
    return when (parallel.isNotTrue()) {
        true -> Files.lines(this, DEFAULT_CHARSET_UTF_8).toSequence()
        else -> Files.lines(this, DEFAULT_CHARSET_UTF_8).parallel().toSequence()
    }
}

