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

val BREAK_STRING: String = System.lineSeparator()

@CreatorsDsl
fun String.isDefaultContentType(): Boolean = toLowerTrim() == DEFAULT_CONTENT_TYPE

@CreatorsDsl
fun String.toCommonContentType(type: String = DEFAULT_CONTENT_TYPE): String = when (IO.getPathExtension(this).toLowerTrim()) {
    ".json" -> "application/json"
    ".java" -> "text/x-java-source"
    ".css", ".scss" -> "text/css"
    ".htm", ".html" -> "text/html"
    ".yml", ".yaml" -> "application/x-yaml"
    ".properties" -> "text/x-java-properties"
    else -> type.toLowerTrim()
}

@CreatorsDsl
fun getDefaultContentTypeProbe(): ContentTypeProbe = IO.getContentTypeProbe()

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
fun URL.isFileURL(): Boolean = protocol.toLowerTrim() == IO.TYPE_IS_FILE

@CreatorsDsl
fun URL.toFileOrNull(skip: Boolean = false): File? = IO.toFileOrNull(this, skip)

@CreatorsDsl
fun getTempFile(prefix: String, suffix: String? = null, folder: File? = null): File = createTempFile(prefix, suffix, folder).apply { deleteOnExit() }

@CreatorsDsl
fun baos(size: Int = DEFAULT_BUFFER_SIZE): ByteArrayOutputStream = ByteArrayOutputStream(size)

@CreatorsDsl
fun File.isValidToRead(): Boolean {
    return try {
        isFile && canRead()
    }
    catch (cause: Throwable) {
        Throwables.thrown(cause)
        false
    }
}

@CreatorsDsl
fun Path.isValidToRead(): Boolean = toFile().isValidToRead()

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
fun CharSequence.toCharBuffer(beg: Int = 0, end: Int = length): CharBuffer = CharBuffer.wrap(toString(), beg, end)

@CreatorsDsl
fun ByteArray.toInputStream(copy: Boolean = false): InputStream = ByteArrayInputStream(toByteArray(copy))

@CreatorsDsl
fun File.toInputStream(vararg args: OpenOption): InputStream = toPath().toInputStream(*args)

@CreatorsDsl
fun Path.toInputStream(vararg args: OpenOption): InputStream = if (isValidToRead()) Files.newInputStream(this, *args) else throw MercenaryExceptiion(toString())

@CreatorsDsl
fun ReadableByteChannel.toInputStream(): InputStream = Channels.newInputStream(this)

@CreatorsDsl
fun InputStreamSupplier.toInputStream(): InputStream = getInputStream()

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
fun File.isValidToWrite(): Boolean = canWrite() && isDirectory.isNotTrue()

@CreatorsDsl
fun Path.isValidToWrite(): Boolean = toFile().isValidToWrite()

@CreatorsDsl
fun File.toOutputStream(vararg args: OpenOption): OutputStream = toPath().toOutputStream(*args)

@CreatorsDsl
fun Path.toOutputStream(vararg args: OpenOption): OutputStream = if (isValidToWrite()) Files.newOutputStream(this, *args) else throw MercenaryExceptiion(toString())

@CreatorsDsl
fun WritableByteChannel.toOutputStream(): OutputStream = Channels.newOutputStream(this)

@CreatorsDsl
fun OutputStreamSupplier.toOutputStream(): OutputStream = getOutputStream()

@CreatorsDsl
inline fun Reader.forEachLineIndexed(block: (Int, String) -> Unit) {
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
    reader().forEachLineIndexed(block)
}

@CreatorsDsl
fun URL.toRelative(path: String) = IO.getRelative(this, path)

@CreatorsDsl
fun File.toRelative(path: String) = IO.getRelative(this, path)

@CreatorsDsl
fun String.toURL(): URL = URL(this)

@CreatorsDsl
fun String.toFileURL(): URL = IO.toFileURL(this)

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
fun Reader.toByteArray(charset: Charset = Charsets.UTF_8): ByteArray = toInputStream(charset).toByteArray()

@CreatorsDsl
fun ReadableByteChannel.toByteArray(): ByteArray = toInputStream().toByteArray()

@CreatorsDsl
fun InputStreamSupplier.toByteArray(): ByteArray = when (this) {
    is ContentResource -> getContentData()
    else -> getInputStream().toByteArray()
}

@CreatorsDsl
fun ByteArrayOutputStream.getContentData(): ByteArray = toByteArray()

@CreatorsDsl
fun ByteArrayOutputStream.clear(): Unit = reset()

@CreatorsDsl
fun CharSequence.getContentData(charset: Charset = Charsets.UTF_8): ByteArray = toString().toByteArray(charset)

@CreatorsDsl
fun ByteArray.toByteArray(copy: Boolean = true): ByteArray = if (copy) copyOf() else this

@CreatorsDsl
fun ByteArray.toContentSize(): Long = size.toLong()

@CreatorsDsl
fun CharArray.toCharArray(copy: Boolean = true): CharArray = if (copy) copyOf() else this

@CreatorsDsl
fun CharArray.toCharSequence(copy: Boolean = true): CharSequence = StringBuilder(size).append(toCharArray(copy))

@CreatorsDsl
fun IntArray.toIntArray(copy: Boolean = true): IntArray = if (copy) copyOf() else this

@CreatorsDsl
fun Int.toIntArray(zero: Boolean = false): IntArray = if (this < 1) IntArray(0) else if (zero) IntArray(this) else IntArray(this) { it }

@CreatorsDsl
fun URL.toContentResource() = URLContentResource(this)

@CreatorsDsl
fun URI.toContentResource() = URLContentResource(this)

@CreatorsDsl
fun File.toContentResource() = FileContentResource(this)

@CreatorsDsl
fun Path.toContentResource() = FileContentResource(this)
