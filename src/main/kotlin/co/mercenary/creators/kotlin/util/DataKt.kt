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

@file:kotlin.jvm.JvmName("DataKt")
@file:Suppress("NOTHING_TO_INLINE", "UNCHECKED_CAST")

package co.mercenary.creators.kotlin.util

import co.mercenary.creators.kotlin.util.io.*
import java.io.*
import java.net.*
import java.nio.*
import java.nio.channels.*
import java.nio.charset.Charset
import java.nio.file.*
import java.util.concurrent.atomic.*

typealias SystemProperties = java.util.Properties

typealias DefaultContentTypeProbe = MimeContentTypeProbe

typealias DefaultContentFileTypeMap = ContentFileTypeMap

typealias ByteArrayOutputStream = java.io.ByteArrayOutputStream

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

typealias ContentResourceLookup = Convert<String, ContentResource>

@FrameworkDsl
const val DEFAULT_CONTENT_TYPE = "application/octet-stream"

@FrameworkDsl
val CONTENT_RESOURCE_LOADER: ContentResourceLoader
    get() = BaseContentResourceLoader.INSTANCE

@FrameworkDsl
val CACHED_CONTENT_RESOURCE_LOADER: CachedContentResourceLoader
    get() = BaseCachedContentResourceLoader.INSTANCE

@FrameworkDsl
val BREAK_STRING: String = System.lineSeparator()

@FrameworkDsl
val EMPTY_INTS_ARRAY = IntArray(0)

@FrameworkDsl
val EMPTY_BYTE_ARRAY = ByteArray(0)

@FrameworkDsl
val EMPTY_CHAR_ARRAY = CharArray(0)

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
inline fun <T, R> scope(value: T, block: T.() -> R): R = with(value, block)

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
        return norm.substring(1)
    }
    return norm
}

@FrameworkDsl
inline fun URL.isFileURL(): Boolean = protocol.toLowerTrimEnglish() == IO.TYPE_IS_FILE

@FrameworkDsl
inline fun URL.toFileOrNull(skip: Boolean = false): File? = IO.toFileOrNull(this, skip)

@FrameworkDsl
fun getTempFile(prefix: String, suffix: String? = null, folder: File? = null): File = File.createTempFile(prefix, suffix, folder).apply { deleteOnExit() }

@FrameworkDsl
inline fun File.isValidToRead(): Boolean = isDirectory.isNotTrue() && canRead()

@FrameworkDsl
inline fun Path.isValidToRead(): Boolean = fileOf().isValidToRead()

@FrameworkDsl
infix fun File.isSameFile(other: File): Boolean = isSameFile(other.pathOf())

@FrameworkDsl
infix fun File.isSameFile(other: Path): Boolean = toPath().isSameFile(other)

@FrameworkDsl
infix fun Path.isSameFile(other: File): Boolean = isSameFile(other.toPath())

@FrameworkDsl
infix fun Path.isSameFile(other: Path): Boolean = Files.isSameFile(this, other)

@FrameworkDsl
infix fun File.isSameFileAndData(other: File): Boolean = isSameFileAndData(other.toPath())

@FrameworkDsl
infix fun File.isSameFileAndData(other: Path): Boolean = toPath().isSameFileAndData(other)

@FrameworkDsl
infix fun Path.isSameFileAndData(other: File): Boolean = isSameFileAndData(other.toPath())

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
inline fun getByteBuffer(size: Int): ByteBuffer = ByteBuffer.allocate(size.maxOf(0))

@FrameworkDsl
inline fun toByteArrayOfInt(data: Byte): ByteArray = toByteArrayOfInt(data.toMaskedInt())

@FrameworkDsl
inline fun toByteArrayOfInt(data: Char): ByteArray = toByteArrayOfInt(data.toMaskedInt())

@FrameworkDsl
inline fun toByteArrayOfInt(data: Long): ByteArray = toByteArrayOfInt(data.toMaskedInt())

@FrameworkDsl
inline fun toByteArrayOfInt(data: Int): ByteArray = getByteBuffer(Int.SIZE_BYTES).putInt(data).toByteArray(false)

@FrameworkDsl
fun CharSequence.toCharBuffer(beg: Int = 0, end: Int = sizeOf()): CharBuffer = CharBuffer.wrap(toString(), beg, end)

@FrameworkDsl
fun CharSequence.getCharArray(copy: Boolean = false): CharArray = toString().toCharArray().toCharArray(copy)

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
fun Reader.toInputStream(charset: Charset = Charsets.UTF_8): InputStream = IO.getInputStream(this, charset)

@FrameworkDsl
fun Writer.toOutputStream(charset: Charset = Charsets.UTF_8): OutputStream = IO.getOutputStream(this, charset)

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
inline fun File.isValidToWrite(): Boolean = isDirectory.isNotTrue() && canWrite()

@FrameworkDsl
inline fun Path.isValidToWrite(): Boolean = fileOf().isValidToWrite()

@FrameworkDsl
fun File.toOutputStream(append: Boolean = false): OutputStream = pathOf().toOutputStream(append)

@FrameworkDsl
fun Path.toOutputStream(append: Boolean = false): OutputStream = if (isValidToWrite()) FileOutputStream(fileOf(), append) else throw MercenaryExceptiion(toString())

@FrameworkDsl
inline fun WritableByteChannel.toOutputStream(): OutputStream = Channels.newOutputStream(this)

@FrameworkDsl
inline fun OutputStreamSupplier.toOutputStream(): OutputStream = getOutputStream()

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
inline fun InputStream.toBufferedReader(charset: Charset = Charsets.UTF_8): BufferedReader {
    return reader(charset).buffered(getBufferSize())
}

@FrameworkDsl
fun InputStream.getBufferSize(most: Int = DEFAULT_BUFFER_SIZE * 4): Int {
    if (this is EmptyInputStream) {
        return 0
    }
    return try {
        available().maxOf(DEFAULT_BUFFER_SIZE).minOf(most)
    } catch (cause: Throwable) {
        Throwables.fatal(cause, DEFAULT_BUFFER_SIZE)
    }
}

@FrameworkDsl
inline fun URL.toRelative(path: String): URL = IO.getRelative(this, path)

@FrameworkDsl
inline fun File.toRelative(path: String): File = IO.getRelative(this, path)

@FrameworkDsl
inline fun File.pathOf(): Path = toPath()

@FrameworkDsl
inline fun Path.fileOf(): File = toFile()

@FrameworkDsl
inline fun String.linkOf(): URL = URL(this)

@FrameworkDsl
inline fun String.fileOf(): File = File(this)

@FrameworkDsl
inline fun URI.pathOf(): Path = Paths.get(this)

@FrameworkDsl
inline fun String.pathOf(): Path = Paths.get(this)

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
inline operator fun Path.div(other: String): Path = this.resolve(other)

@FrameworkDsl
inline fun String.toFileURL(): URL = IO.toFileURL(this)

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
fun Reader.toByteArray(charset: Charset = Charsets.UTF_8): ByteArray = toInputStream(charset).toByteArray()

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
fun ByteArray.getContentText(copy: Boolean = false, charset: Charset = Charsets.UTF_8): String {
    return if (isExhausted()) EMPTY_STRING else toByteArray(copy).toString(charset)
}

@FrameworkDsl
fun CharSequence.getContentData(charset: Charset = Charsets.UTF_8): ByteArray {
    return if (isExhausted()) EMPTY_BYTE_ARRAY else toString().toByteArray(charset)
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
inline fun ByteArray.toContentSize(): Long = size.toContentSize()

@FrameworkDsl
fun CharArray.toCharArray(copy: Boolean = true): CharArray = if (copy.isTrue() && sizeOf() > 0) copyOf() else this

@FrameworkDsl
fun CharArray.toByteArray(copy: Boolean = true): ByteArray = toCharSequence(copy).getContentData()

@FrameworkDsl
fun CharArray.getContentData(copy: Boolean = true): ByteArray = toCharSequence(copy).getContentData()

@FrameworkDsl
fun CharArray.toCharSequence(copy: Boolean = true): CharSequence = if (isExhausted()) EMPTY_STRING else toCharArray(copy).concatToString()

@FrameworkDsl
fun CharArray.getContentText(copy: Boolean = true): String = toCharSequence(copy).toString()

@FrameworkDsl
fun IntArray.toIntArray(copy: Boolean = true): IntArray = if (copy.isTrue() && sizeOf() > 0) copyOf() else this

@FrameworkDsl
fun Int.toIntArray(zero: Boolean = false): IntArray = if (this < 1) EMPTY_INTS_ARRAY else if (zero) IntArray(this) else IntArray(this) { it }

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
inline fun Boolean.toInsertOrdered(): InsertOrdered = InsertOrdered { this }

@FrameworkDsl
inline fun AtomicBoolean.toInsertOrdered(): InsertOrdered = toBoolean().toInsertOrdered()

@FrameworkDsl
inline fun Int.toInsertLimited(): InsertLimited = InsertLimited { this }

@FrameworkDsl
inline fun AtomicInteger.toInsertLimited(): InsertLimited = InsertLimited { getValue() }

@FrameworkDsl
inline fun InsertLimited.isNegative(): Boolean = getLimit() < 0

@FrameworkDsl
inline fun InsertLimited.isNotNegative(): Boolean = getLimit() >= 0
