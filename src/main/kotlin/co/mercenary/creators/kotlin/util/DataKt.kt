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
@file:Suppress("NOTHING_TO_INLINE")

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

typealias ContentResourceLookup = (String) -> ContentResource

typealias ByteArrayOutputStream = java.io.ByteArrayOutputStream

typealias EmptyOutputStream = co.mercenary.creators.kotlin.util.io.EmptyOutputStream

typealias Escapers = co.mercenary.creators.kotlin.util.text.Escapers

typealias Formatters = co.mercenary.creators.kotlin.util.text.Formatters

@CreatorsDsl
const val DEFAULT_CONTENT_TYPE = "application/octet-stream"

val CONTENT_RESOURCE_LOADER: ContentResourceLoader
    get() = BaseContentResourceLoader.INSTANCE

val CACHED_CONTENT_RESOURCE_LOADER: CachedContentResourceLoader
    get() = BaseCachedContentResourceLoader.INSTANCE

val BREAK_STRING: String
    get() = System.lineSeparator()

@CreatorsDsl
val EMPTY_INTS_ARRAY = IntArray(0)

@CreatorsDsl
val EMPTY_BYTE_ARRAY = ByteArray(0)

@CreatorsDsl
val EMPTY_CHAR_ARRAY = CharArray(0)

@CreatorsDsl
inline fun String.isDefaultContentType(): Boolean = toLowerTrimEnglish() == DEFAULT_CONTENT_TYPE

@CreatorsDsl
@JvmOverloads
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
@JvmOverloads
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
@JvmOverloads
fun URL.toFileOrNull(skip: Boolean = false): File? = IO.toFileOrNull(this, skip)

@CreatorsDsl
@JvmOverloads
fun getTempFile(prefix: String, suffix: String? = null, folder: File? = null): File = createTempFile(prefix, suffix, folder).apply { deleteOnExit() }

@CreatorsDsl
@JvmOverloads
fun baos(size: Int = DEFAULT_BUFFER_SIZE): ByteArrayOutputStream = ByteArrayOutputStream(size)

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
@JvmOverloads
fun ByteArray.toByteBuffer(copy: Boolean = false): ByteBuffer = ByteBuffer.wrap(toByteArray(copy))

@CreatorsDsl
@JvmOverloads
fun CharArray.toCharBuffer(copy: Boolean = false): CharBuffer = CharBuffer.wrap(toCharArray(copy))

@CreatorsDsl
inline fun getByteBuffer(size: Int): ByteBuffer = ByteBuffer.allocate(size.maxOf(0))

@CreatorsDsl
@JvmOverloads
fun CharSequence.toCharBuffer(beg: Int = 0, end: Int = length): CharBuffer = CharBuffer.wrap(toString(), beg, end)

@CreatorsDsl
@JvmOverloads
fun ByteArray.toInputStream(copy: Boolean = false): InputStream = ByteArrayInputStream(toByteArray(copy))

@CreatorsDsl
fun File.toInputStream(vararg args: OpenOption): InputStream = toPath().toInputStream(*args)

@CreatorsDsl
fun Path.toInputStream(vararg args: OpenOption): InputStream = if (isValidToRead()) Files.newInputStream(this, *args) else throw MercenaryExceptiion(toString())

@CreatorsDsl
inline fun ReadableByteChannel.toInputStream(): InputStream = Channels.newInputStream(this)

@CreatorsDsl
inline fun InputStreamSupplier.toInputStream(): InputStream = getInputStream()

@CreatorsDsl
@JvmOverloads
fun Reader.toInputStream(charset: Charset = Charsets.UTF_8): InputStream = IO.getInputStream(this, charset)

@CreatorsDsl
@JvmOverloads
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
inline fun File.isValidToWrite(): Boolean = canWrite() && isDirectory.isNotTrue()

@CreatorsDsl
inline fun Path.isValidToWrite(): Boolean = toFile().isValidToWrite()

@CreatorsDsl
fun File.toOutputStream(vararg args: OpenOption): OutputStream = toPath().toOutputStream(*args)

@CreatorsDsl
fun Path.toOutputStream(vararg args: OpenOption): OutputStream = if (isValidToWrite()) Files.newOutputStream(this, *args) else throw MercenaryExceptiion(toString())

@CreatorsDsl
inline fun WritableByteChannel.toOutputStream(): OutputStream = Channels.newOutputStream(this)

@CreatorsDsl
inline fun OutputStreamSupplier.toOutputStream(): OutputStream = getOutputStream()

@CreatorsDsl
inline fun Reader.forEachLineIndexed(block: (Int, String) -> Unit) {
    buffered().forEachLineIndexed(block)
}

@CreatorsDsl
inline fun BufferedReader.forEachLineIndexed(block: (Int, String) -> Unit) {
    useLines { it.forEachIndexed(block) }
}

@CreatorsDsl
inline fun URL.forEachLineIndexed(block: (Int, String) -> Unit) {
    toInputStream().forEachLineIndexed(block)
}

@CreatorsDsl
inline fun URI.forEachLineIndexed(block: (Int, String) -> Unit) {
    toInputStream().forEachLineIndexed(block)
}

@CreatorsDsl
inline fun File.forEachLineIndexed(block: (Int, String) -> Unit) {
    toInputStream().forEachLineIndexed(block)
}

@CreatorsDsl
inline fun Path.forEachLineIndexed(block: (Int, String) -> Unit) {
    toInputStream().forEachLineIndexed(block)
}

@CreatorsDsl
inline fun InputStreamSupplier.forEachLineIndexed(block: (Int, String) -> Unit) {
    toInputStream().forEachLineIndexed(block)
}

@CreatorsDsl
inline fun InputStream.forEachLineIndexed(block: (Int, String) -> Unit) {
    reader().buffered().useLines { it.forEachIndexed(block) }
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
fun InputStream.toByteArray(): ByteArray = use { it.readBytes() }

@CreatorsDsl
fun File.toByteArray(): ByteArray = toInputStream().toByteArray()

@CreatorsDsl
fun Path.toByteArray(): ByteArray = toInputStream().toByteArray()

@CreatorsDsl
@JvmOverloads
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
@JvmOverloads
fun ByteArray.getContentText(copy: Boolean = false, charset: Charset = Charsets.UTF_8): String = toByteArray(copy).toString(charset)

@CreatorsDsl
@JvmOverloads
fun CharSequence.getContentData(charset: Charset = Charsets.UTF_8): ByteArray = toString().toByteArray(charset)

@CreatorsDsl
@JvmOverloads
fun ByteArray.toByteArray(copy: Boolean = true): ByteArray = if (copy) copyOf() else this

@CreatorsDsl
@JvmOverloads
fun ByteBuffer.toByteArray(copy: Boolean = false): ByteArray {
    return if (hasArray()) array().toByteArray(copy) else EMPTY_BYTE_ARRAY.toByteArray(copy)
}

@CreatorsDsl
inline fun ByteArray.toContentSize(): Long = size.toLong()

@CreatorsDsl
@JvmOverloads
fun CharArray.toCharArray(copy: Boolean = true): CharArray = if (copy) copyOf() else this

@CreatorsDsl
@JvmOverloads
fun CharArray.toCharSequence(copy: Boolean = true): CharSequence = if (isEmpty()) EMPTY_STRING else toCharArray(copy).concatToString()

@CreatorsDsl
@JvmOverloads
fun CharArray.getContentText(copy: Boolean = true): String = toCharSequence(copy).toString()

@CreatorsDsl
@JvmOverloads
fun IntArray.toIntArray(copy: Boolean = true): IntArray = (if (isEmpty()) EMPTY_INTS_ARRAY else this).let { if (copy) it.copyOf() else it }

@CreatorsDsl
@JvmOverloads
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
inline fun CharSequence.forEachChar(noinline block: (Char) -> Unit) {
    if (length > 0) {
        for (i in 0 until length) {
            block(this[i])
        }
    }
}

