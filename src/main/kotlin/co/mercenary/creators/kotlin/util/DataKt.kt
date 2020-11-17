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

typealias Applier<T> = T.() -> Unit

typealias Indexed<T> = (Int, T) -> Unit

typealias LazyMessage = Factory<Maybe>

typealias ContentResourceLookup = Convert<String, ContentResource>

@CreatorsDsl
const val DEFAULT_CONTENT_TYPE = "application/octet-stream"

val CONTENT_RESOURCE_LOADER: ContentResourceLoader
    get() = BaseContentResourceLoader.INSTANCE

val CACHED_CONTENT_RESOURCE_LOADER: CachedContentResourceLoader
    get() = BaseCachedContentResourceLoader.INSTANCE

@CreatorsDsl
val BREAK_STRING: String = System.lineSeparator()

@CreatorsDsl
val EMPTY_INTS_ARRAY = IntArray(0)

@CreatorsDsl
val EMPTY_BYTE_ARRAY = ByteArray(0)

@CreatorsDsl
val EMPTY_CHAR_ARRAY = CharArray(0)

@CreatorsDsl
inline fun getStandardInput(): InputStream = System.`in`

@CreatorsDsl
inline fun getStandardOutut(): PrintStream = System.out

@CreatorsDsl
inline fun getStandardError(): PrintStream = System.err

@CreatorsDsl
fun PrintStream.echo(data: Maybe): PrintStream {
    this.print(data)
    return this
}

@CreatorsDsl
fun PrintStream.echo(vararg args: Maybe): PrintStream {
    args.forEach { data -> echo(data) }
    return this
}

@CreatorsDsl
fun PrintStream.spaces(many: Int = 1): PrintStream {
    this.print(SPACE_STRING.repeat(many))
    return this
}

@CreatorsDsl
fun PrintStream.newline(): PrintStream {
    this.println()
    return this
}

@CreatorsDsl
inline fun <T> Indexed<T>.indexed(index: Int, value: T) = invoke(index, value)

@CreatorsDsl
inline fun <T> Factory<T>.convert() = invoke()

@CreatorsDsl
inline fun <T, R> Convert<T, R>.convert(args: T): R = invoke(args)

@CreatorsDsl
inline fun <T, R> T.transform(block: Convert<T, R>): R = block.convert(this)

@CreatorsDsl
inline fun <T, R> scope(value: T, block: T.() -> R): R = with(value, block)

@CreatorsDsl
inline fun <T : Clearable, R> T.finishOn(block: (T) -> R): R {
    try {
        return block(this)
    } catch (cause: Throwable) {
        Throwables.thrown(cause)
        throw cause
    } finally {
        try {
            clear()
        } catch (cause: Throwable) {
            Throwables.thrown(cause)
        }
    }
}

@CreatorsDsl
inline fun <T : Resetable, R> T.finishOn(block: (T) -> R): R {
    try {
        return block(this)
    } catch (cause: Throwable) {
        Throwables.thrown(cause)
        throw cause
    } finally {
        try {
            reset()
        } catch (cause: Throwable) {
            Throwables.thrown(cause)
        }
    }
}

@CreatorsDsl
inline fun <T : Closeable, R> T.finishOn(block: (T) -> R): R {
    try {
        return use(block)
    } catch (cause: Throwable) {
        Throwables.thrown(cause)
        throw cause
    }
}

@CreatorsDsl
inline fun String.isDefaultContentType(): Boolean = toLowerTrimEnglish() == DEFAULT_CONTENT_TYPE

@CreatorsDsl
fun String.toCommonContentType(type: String = DEFAULT_CONTENT_TYPE): String = when (IO.getPathExtension(this).toLowerTrimEnglish()) {
    ".json" -> "application/json"
    ".java" -> "text/x-java-source"
    ".css", ".scss" -> "text/css"
    ".htm", ".html" -> "text/html"
    ".yml", ".yaml" -> "application/x-yaml"
    ".properties" -> "text/x-java-properties"
    else -> type.toLowerTrimEnglish()
}

@CreatorsDsl
inline fun getDefaultContentTypeProbe(): ContentTypeProbe = IO.getContentTypeProbe()

@CreatorsDsl
inline fun isSystemWindows(): Boolean = IO.isSystemWindows()

@CreatorsDsl
inline fun isSystemUnixLike(): Boolean = IO.isSystemUnixLike()

@CreatorsDsl
fun getPathNormalizedOrElse(path: String?, other: String = EMPTY_STRING): String = toTrimOrElse(IO.getPathNormalized(path), other)

@CreatorsDsl
fun getPathNormalizedNoTail(path: String?, tail: Boolean): String {
    val norm = getPathNormalizedOrElse(path)
    if ((tail) && (norm.startsWith(IO.SINGLE_SLASH))) {
        return norm.substring(1)
    }
    return norm
}

@CreatorsDsl
inline fun URL.isFileURL(): Boolean = protocol.toLowerTrimEnglish() == IO.TYPE_IS_FILE

@CreatorsDsl
fun URL.toFileOrNull(skip: Boolean = false): File? = IO.toFileOrNull(this, skip)

@CreatorsDsl
fun getTempFile(prefix: String, suffix: String? = null, folder: File? = null): File = createTempFile(prefix, suffix, folder).apply { deleteOnExit() }

@CreatorsDsl
inline fun File.isValidToRead(): Boolean = isDirectory.isNotTrue() && canRead()

@CreatorsDsl
inline fun Path.isValidToRead(): Boolean = toFile().isValidToRead()

@CreatorsDsl
infix fun File.isSameFile(other: File): Boolean = isSameFile(other.toPath())

@CreatorsDsl
infix fun File.isSameFile(other: Path): Boolean = toPath().isSameFile(other)

@CreatorsDsl
infix fun Path.isSameFile(other: File): Boolean = isSameFile(other.toPath())

@CreatorsDsl
infix fun Path.isSameFile(other: Path): Boolean = Files.isSameFile(this, other)

@CreatorsDsl
infix fun File.isSameFileAndData(other: File): Boolean = isSameFileAndData(other.toPath())

@CreatorsDsl
infix fun File.isSameFileAndData(other: Path): Boolean = toPath().isSameFileAndData(other)

@CreatorsDsl
infix fun Path.isSameFileAndData(other: File): Boolean = isSameFileAndData(other.toPath())

@CreatorsDsl
infix fun Path.isSameFileAndData(other: Path): Boolean = isSameFile(other) || toFile().compareTo(other.toFile()) == 0

@CreatorsDsl
fun URL.toInputStream(): InputStream = when (val data = IO.getInputStream(this)) {
    null -> throw MercenaryExceptiion(toString())
    else -> data
}

@CreatorsDsl
fun URI.toInputStream(): InputStream = when (val data = IO.getInputStream(this)) {
    null -> throw MercenaryExceptiion(toString())
    else -> data
}

@CreatorsDsl
fun ByteArray.toByteBuffer(copy: Boolean = false): ByteBuffer = ByteBuffer.wrap(toByteArray(copy))

@CreatorsDsl
fun CharArray.toCharBuffer(copy: Boolean = false): CharBuffer = CharBuffer.wrap(toCharArray(copy))

@CreatorsDsl
inline fun getByteBuffer(size: Int): ByteBuffer = ByteBuffer.allocate(size.maxOf(0))

@CreatorsDsl
inline fun toByteArrayOfInt(data: Byte): ByteArray = toByteArrayOfInt(data.toMaskedInt())

@CreatorsDsl
inline fun toByteArrayOfInt(data: Char): ByteArray = toByteArrayOfInt(data.toMaskedInt())

@CreatorsDsl
inline fun toByteArrayOfInt(data: Long): ByteArray = toByteArrayOfInt(data.toMaskedInt())

@CreatorsDsl
inline fun toByteArrayOfInt(data: Int): ByteArray = getByteBuffer(Int.SIZE_BYTES).putInt(data).toByteArray(false)

@CreatorsDsl
fun CharSequence.toCharBuffer(beg: Int = 0, end: Int = length): CharBuffer = CharBuffer.wrap(toString(), beg, end)

@CreatorsDsl
fun CharSequence.getCharArray(copy: Boolean = false): CharArray = toString().toCharArray().toCharArray(copy)

@CreatorsDsl
fun ByteArray.toInputStream(copy: Boolean = false): InputStream = ByteArrayInputStream(toByteArray(copy))

@CreatorsDsl
fun File.toInputStream(): InputStream = toPath().toInputStream()

@CreatorsDsl
fun Path.toInputStream(): InputStream = if (isValidToRead()) FileInputStream(toFile()) else throw MercenaryExceptiion(toString())

@CreatorsDsl
inline fun FileDescriptor.toInputStream(): InputStream = FileInputStream(this)

@CreatorsDsl
inline fun ReadableByteChannel.toInputStream(): InputStream = Channels.newInputStream(this)

@CreatorsDsl
inline fun InputStreamSupplier.toInputStream(): InputStream = getInputStream()

@CreatorsDsl
fun Reader.toInputStream(charset: Charset = Charsets.UTF_8): InputStream = IO.getInputStream(this, charset)

@CreatorsDsl
fun Writer.toOutputStream(charset: Charset = Charsets.UTF_8): OutputStream = IO.getOutputStream(this, charset)

@CreatorsDsl
fun URL.toOutputStream(): OutputStream = when (val data = IO.getOutputStream(this)) {
    null -> throw MercenaryExceptiion(toString())
    else -> data
}

@CreatorsDsl
fun URI.toOutputStream(): OutputStream = when (val data = IO.getOutputStream(this)) {
    null -> throw MercenaryExceptiion(toString())
    else -> data
}

@CreatorsDsl
inline fun File.isValidToWrite(): Boolean = isDirectory.isNotTrue() && canWrite()

@CreatorsDsl
inline fun Path.isValidToWrite(): Boolean = toFile().isValidToWrite()

@CreatorsDsl
fun File.toOutputStream(append: Boolean = false): OutputStream = toPath().toOutputStream(append)

@CreatorsDsl
fun Path.toOutputStream(append: Boolean = false): OutputStream = if (isValidToWrite()) FileOutputStream(toFile(), append) else throw MercenaryExceptiion(toString())

@CreatorsDsl
inline fun FileDescriptor.toOutputStream(): OutputStream = FileOutputStream(this)

@CreatorsDsl
inline fun WritableByteChannel.toOutputStream(): OutputStream = Channels.newOutputStream(this)

@CreatorsDsl
inline fun OutputStreamSupplier.toOutputStream(): OutputStream = getOutputStream()

@CreatorsDsl
inline infix fun Reader.forEachLineIndexed(block: (Int, String) -> Unit) {
    buffered().forEachLineIndexed(block)
}

@CreatorsDsl
inline infix fun BufferedReader.forEachLineIndexed(block: (Int, String) -> Unit) {
    useLines { it.forEachIndexed(block) }
}

@CreatorsDsl
inline infix fun URL.forEachLineIndexed(block: (Int, String) -> Unit) {
    toInputStream().forEachLineIndexed(block)
}

@CreatorsDsl
inline infix fun URI.forEachLineIndexed(block: (Int, String) -> Unit) {
    toInputStream().forEachLineIndexed(block)
}

@CreatorsDsl
inline infix fun File.forEachLineIndexed(block: (Int, String) -> Unit) {
    toInputStream().forEachLineIndexed(block)
}

@CreatorsDsl
inline infix fun Path.forEachLineIndexed(block: (Int, String) -> Unit) {
    toInputStream().forEachLineIndexed(block)
}

@CreatorsDsl
inline infix fun InputStreamSupplier.forEachLineIndexed(block: (Int, String) -> Unit) {
    toInputStream().forEachLineIndexed(block)
}

@CreatorsDsl
inline infix fun InputStream.forEachLineIndexed(block: (Int, String) -> Unit) {
    toBufferedReader().forEachLineIndexed(block)
}

@CreatorsDsl
inline fun InputStream.toBufferedReader(charset: Charset = Charsets.UTF_8): BufferedReader {
    return reader(charset).buffered(getBufferSize())
}

@CreatorsDsl
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

@CreatorsDsl
inline fun URL.toRelative(path: String): URL = IO.getRelative(this, path)

@CreatorsDsl
inline fun File.toRelative(path: String): File = IO.getRelative(this, path)

@CreatorsDsl
inline fun String.toURL(): URL = URL(this)

@CreatorsDsl
inline fun String.toFileURL(): URL = IO.toFileURL(this)

@CreatorsDsl
fun URL.toByteArray(): ByteArray = toInputStream().toByteArray()

@CreatorsDsl
fun URI.toByteArray(): ByteArray = toInputStream().toByteArray()

@CreatorsDsl
fun InputStream.toByteArray(): ByteArray = finishOn { data ->
    BytesOutputStream(data.getBufferSize()).toByteArray()
}

@CreatorsDsl
fun File.toByteArray(): ByteArray = toInputStream().toByteArray()

@CreatorsDsl
fun Path.toByteArray(): ByteArray = toInputStream().toByteArray()

@CreatorsDsl
fun Reader.toByteArray(charset: Charset = Charsets.UTF_8): ByteArray = toInputStream(charset).toByteArray()

@CreatorsDsl
fun ReadableByteChannel.toByteArray(): ByteArray = toInputStream().toByteArray()

@CreatorsDsl
fun InputStreamSupplier.toByteArray(): ByteArray = when (this) {
    is ContentResource -> getContentData()
    else -> getInputStream().toByteArray()
}

@CreatorsDsl
inline fun ByteArrayOutputStream.getContentData(): ByteArray = toByteArray()

@CreatorsDsl
inline fun ByteArrayOutputStream.clear(): Unit = reset()

@CreatorsDsl
fun ByteArray.getContentText(copy: Boolean = false, charset: Charset = Charsets.UTF_8): String = toByteArray(copy).toString(charset)

@CreatorsDsl
fun CharSequence.getContentData(charset: Charset = Charsets.UTF_8): ByteArray = toString().toByteArray(charset)

@CreatorsDsl
fun ByteArray.toByteArray(copy: Boolean = true): ByteArray = if (copy) copyOf() else this

@CreatorsDsl
fun ByteBuffer.toByteArray(copy: Boolean = false): ByteArray {

    return if (hasArray()) array().toByteArray(copy) else EMPTY_BYTE_ARRAY
}

@CreatorsDsl
inline fun ByteArray.toContentSize(): Long = size.toLong()

@CreatorsDsl
fun CharArray.toCharArray(copy: Boolean = true): CharArray = if (copy) copyOf() else this

@CreatorsDsl
fun CharArray.toByteArray(copy: Boolean = true): ByteArray = toCharSequence(copy).getContentData()

@CreatorsDsl
fun CharArray.getContentData(copy: Boolean = true): ByteArray = toCharSequence(copy).getContentData()

@CreatorsDsl
fun CharArray.toCharSequence(copy: Boolean = true): CharSequence = if (isEmpty()) EMPTY_STRING else toCharArray(copy).concatToString()

@CreatorsDsl
fun CharArray.getContentText(copy: Boolean = true): String = toCharSequence(copy).toString()

@CreatorsDsl
fun IntArray.toIntArray(copy: Boolean = true): IntArray = (if (isEmpty()) EMPTY_INTS_ARRAY else this).let { if (copy) it.copyOf() else it }

@CreatorsDsl
fun Int.toIntArray(zero: Boolean = false): IntArray = if (this < 1) EMPTY_INTS_ARRAY else if (zero) IntArray(this) else IntArray(this) { it }

@CreatorsDsl
inline fun URL.toContentResource() = URLContentResource(this)

@CreatorsDsl
inline fun URI.toContentResource() = URLContentResource(this)

@CreatorsDsl
inline fun File.toContentResource() = FileContentResource(this)

@CreatorsDsl
inline fun Path.toContentResource() = FileContentResource(this)

@CreatorsDsl
inline infix fun CharSequence.forEachChar(noinline block: Convert<Char, Unit>) {
    if (length > 0) {
        for (i in 0 until length) {
            block.convert(this[i])
        }
    }
}

@CreatorsDsl
inline fun CharSequence.pluralOf(size: Int = length, tail: String = EMPTY_STRING): String {
    return if (size == 1) "${toString()}$tail" else "${toString()}s$tail"
}

@CreatorsDsl
inline infix fun CharSequence.forEachCharIndexed(noinline block: Indexed<Char>) {
    if (length > 0) {
        for (i in 0 until length) {
            block.indexed(i, this[i])
        }
    }
}

@CreatorsDsl
inline fun <R> CharSequence.transformTo(transform: Convert<CharSequence, R>): R {
    return transform.convert(this)
}

