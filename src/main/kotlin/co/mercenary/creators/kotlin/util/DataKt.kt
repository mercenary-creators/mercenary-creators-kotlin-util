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

package co.mercenary.creators.kotlin.util

import co.mercenary.creators.kotlin.util.io.*
import java.io.*
import java.net.*
import java.nio.ByteBuffer
import java.nio.channels.*
import java.nio.file.*

typealias DefaultContentTypeProbe = MimeContentTypeProbe

typealias DefaultContentFileTypeMap = ContentFileTypeMap

typealias ContentResourceLookup = (String) -> ContentResource

typealias ByteArrayOutputStream = java.io.ByteArrayOutputStream

typealias EmptyOutputStream = co.mercenary.creators.kotlin.util.io.EmptyOutputStream

typealias Escapers = co.mercenary.creators.kotlin.util.text.Escapers

typealias Formatters = co.mercenary.creators.kotlin.util.text.Formatters

const val DEFAULT_CONTENT_TYPE = "application/octet-stream"

val CONTENT_RESOURCE_LOADER: ContentResourceLoader
    get() = BaseContentResourceLoader.INSTANCE

val CACHED_CONTENT_RESOURCE_LOADER: CachedContentResourceLoader
    get() = BaseCachedContentResourceLoader.INSTANCE

@AssumptionDsl
fun String.isDefaultContentType(): Boolean = toLowerTrim() == DEFAULT_CONTENT_TYPE

@JvmOverloads
fun String.toCommonContentType(type: String = DEFAULT_CONTENT_TYPE): String = when (IO.getPathExtension(this).toLowerTrim()) {
    ".json" -> "application/json"
    ".java" -> "text/x-java-source"
    ".css", ".scss" -> "text/css"
    ".htm", ".html" -> "text/html"
    ".yml", ".yaml" -> "application/x-yaml"
    ".properties" -> "text/x-java-properties"
    else -> type.toLowerTrim()
}

fun getDefaultContentTypeProbe(): ContentTypeProbe = IO.getContentTypeProbe()

@JvmOverloads
fun getPathNormalizedOrElse(path: String?, other: String = EMPTY_STRING): String = toTrimOrElse(IO.getPathNormalized(path), other)

fun getPathNormalizedNoTail(path: String?, tail: Boolean): String {
    val norm = getPathNormalizedOrElse(path)
    if ((tail) && (norm.startsWith(IO.SINGLE_SLASH))) {
        return norm.substring(1)
    }
    return norm
}

@AssumptionDsl
fun URL.isFileURL(): Boolean = protocol.toLowerTrim() == IO.TYPE_IS_FILE

@JvmOverloads
fun URL.toFileOrNull(skip: Boolean = false): File? = IO.toFileOrNull(this, skip)

@JvmOverloads
fun getTempFile(prefix: String, suffix: String? = null, folder: File? = null): File = createTempFile(prefix, suffix, folder).apply { deleteOnExit() }

@AssumptionDsl
infix fun File.isSameFile(other: File): Boolean = isSameFile(other.toPath())

@AssumptionDsl
infix fun File.isSameFile(other: Path): Boolean = toPath().isSameFile(other)

@AssumptionDsl
infix fun Path.isSameFile(other: File): Boolean = isSameFile(other.toPath())

@AssumptionDsl
infix fun Path.isSameFile(other: Path): Boolean = Files.isSameFile(this, other)

@AssumptionDsl
infix fun File.isSameFileAndData(other: File): Boolean = isSameFileAndData(other.toPath())

@AssumptionDsl
infix fun File.isSameFileAndData(other: Path): Boolean = toPath().isSameFileAndData(other)

@AssumptionDsl
infix fun Path.isSameFileAndData(other: File): Boolean = isSameFileAndData(other.toPath())

@AssumptionDsl
infix fun Path.isSameFileAndData(other: Path): Boolean = Files.isSameFile(this, other) || toFile().compareTo(other.toFile()) == 0

@AssumptionDsl
fun File.isValidToRead(): Boolean = exists() && isFile && canRead() && isDirectory.not()

@AssumptionDsl
fun Path.isValidToRead(): Boolean = toFile().isValidToRead()

fun URL.toInputStream(): InputStream = when (val data = IO.getInputStream(this)) {
    null -> throw MercenaryExceptiion(toString())
    else -> data
}

fun URI.toInputStream(): InputStream = when (val data = IO.getInputStream(this)) {
    null -> throw MercenaryExceptiion(toString())
    else -> data
}

@JvmOverloads
fun ByteArray.toByteBuffer(copy: Boolean = false): ByteBuffer = if (copy) ByteBuffer.wrap(copyOf()) else ByteBuffer.wrap(this)

fun ByteArray.toInputStream(): InputStream = ByteArrayInputStream(this)

fun File.toInputStream(vararg args: OpenOption): InputStream = toPath().toInputStream(*args)

fun Path.toInputStream(vararg args: OpenOption): InputStream = if (isValidToRead()) Files.newInputStream(this, *args) else throw MercenaryExceptiion(toString())

fun ReadableByteChannel.toInputStream(): InputStream = Channels.newInputStream(this)

fun InputStreamSupplier.toInputStream(): InputStream = getInputStream()

fun Reader.toInputStream(): InputStream = ReaderInputStream(this)

fun URL.toOutputStream(): OutputStream = when (val data = IO.getOutputStream(this)) {
    null -> throw MercenaryExceptiion(toString())
    else -> data
}

@AssumptionDsl
fun File.isValidToWrite(): Boolean = canWrite() && isDirectory.not()

@AssumptionDsl
fun Path.isValidToWrite(): Boolean = toFile().isValidToWrite()

fun File.toOutputStream(vararg args: OpenOption): OutputStream = toPath().toOutputStream(*args)

fun Path.toOutputStream(vararg args: OpenOption): OutputStream = if (isValidToWrite()) Files.newOutputStream(this, *args) else throw MercenaryExceptiion(toString())

fun WritableByteChannel.toOutputStream(): OutputStream = Channels.newOutputStream(this)

fun OutputStreamSupplier.toOutputStream(): OutputStream = this.getOutputStream()

inline fun Reader.forEachLineIndexed(block: (Int, String) -> Unit) {
    useLines { it.forEachIndexed(block) }
}

inline fun URL.forEachLineIndexed(block: (Int, String) -> Unit) {
    toInputStream().forEachLineIndexed(block)
}

inline fun File.forEachLineIndexed(block: (Int, String) -> Unit) {
    toInputStream().forEachLineIndexed(block)
}

inline fun Path.forEachLineIndexed(block: (Int, String) -> Unit) {
    toInputStream().forEachLineIndexed(block)
}

inline fun InputStreamSupplier.forEachLineIndexed(block: (Int, String) -> Unit) {
    toInputStream().forEachLineIndexed(block)
}

inline fun InputStream.forEachLineIndexed(block: (Int, String) -> Unit) {
    reader().forEachLineIndexed(block)
}

fun URL.toRelative(path: String) = IO.getRelative(this, path)

fun File.toRelative(path: String) = IO.getRelative(this, path)

fun String.toURL(): URL = URL(this)

fun String.toFileURL(): URL = IO.toFileURL(this)

fun URL.toByteArray(): ByteArray = toInputStream().toByteArray()

fun InputStream.toByteArray(): ByteArray = use { it.readBytes() }

fun File.toByteArray(): ByteArray = toInputStream().toByteArray()

fun Path.toByteArray(): ByteArray = toInputStream().toByteArray()

fun ReadableByteChannel.toByteArray(): ByteArray = toInputStream().toByteArray()

fun Reader.toByteArray(): ByteArray = toInputStream().toByteArray()

fun InputStreamSupplier.toByteArray(): ByteArray = when (this) {
    is ContentResource -> getContentData()
    else -> getInputStream().toByteArray()
}

fun URL.toContentResource() = URLContentResource(this)

fun URI.toContentResource() = URLContentResource(this)

fun File.toContentResource() = FileContentResource(this)

fun Path.toContentResource() = FileContentResource(this)
