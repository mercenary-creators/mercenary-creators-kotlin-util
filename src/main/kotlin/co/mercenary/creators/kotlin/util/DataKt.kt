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
import java.io.*
import java.net.URL
import java.nio.channels.*
import java.nio.charset.Charset
import java.nio.file.*

typealias DefaultContentTypeProbe = MimeContentTypeProbe

typealias DefaultContentFileTypeMap = ContentFileTypeMap

const val DEFAULT_CONTENT_TYPE = "application/octet-stream"

val contentResourceLoader = BasicContentResourceLoader.INSTANCE

val cachedContentResourceLoader = CachedContentResourceLoader.INSTANCE

fun isDefaultContentType(type: String): Boolean = type.toLowerTrim() == DEFAULT_CONTENT_TYPE

fun toCommonContentTypes(name: String, type: String = DEFAULT_CONTENT_TYPE): String = when (IO.getPathExtension(name).toLowerTrim()) {
    ".json" -> "application/json"
    ".java" -> "text/x-java-source"
    ".css", ".scss" -> "text/css"
    ".htm", ".html" -> "text/html"
    ".yml", ".yaml" -> "application/x-yaml"
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

fun isFileURL(data: URL): Boolean = (data.protocol.toLowerTrim() == IO.TYPE_IS_FILE).or(data.toString().toLowerTrim().startsWith(IO.PREFIX_FILES))

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

fun Reader.forEachLineIndexed(block: (Int, String) -> Unit) {
    buffered().useLines { it.forEachIndexed(block) }
}

@JvmOverloads
fun URL.forEachLineIndexed(charset: Charset = Charsets.UTF_8, block: (Int, String) -> Unit) {
    toInputStream().forEachLineIndexed(charset, block)
}

@JvmOverloads
fun File.forEachLineIndexed(charset: Charset = Charsets.UTF_8, block: (Int, String) -> Unit) {
    toInputStream().forEachLineIndexed(charset, block)
}

@JvmOverloads
fun Path.forEachLineIndexed(charset: Charset = Charsets.UTF_8, block: (Int, String) -> Unit) {
    toInputStream().forEachLineIndexed(charset, block)
}

@JvmOverloads
fun InputStreamSupplier.forEachLineIndexed(charset: Charset = Charsets.UTF_8, block: (Int, String) -> Unit) {
    toInputStream().forEachLineIndexed(charset, block)
}

@JvmOverloads
fun InputStream.forEachLineIndexed(charset: Charset = Charsets.UTF_8, block: (Int, String) -> Unit) {
    InputStreamReader(this, charset).forEachLineIndexed(block)
}

fun Path.toByteArray(): ByteArray = toInputStream().toByteArray()

fun Reader.toByteArray(): ByteArray = toInputStream().toByteArray()

fun InputStreamSupplier.toByteArray(): ByteArray = when (this) {
    is ContentResource -> getContentData()
    else -> getInputStream().toByteArray()
}

