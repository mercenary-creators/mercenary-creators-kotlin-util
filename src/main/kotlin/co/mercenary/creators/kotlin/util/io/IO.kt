/*
 * Copyright (c) 2023, Mercenary Creators Company. All rights reserved.
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

package co.mercenary.creators.kotlin.util.io

import co.mercenary.creators.kotlin.util.*
import co.mercenary.creators.kotlin.util.collection.EmptyIterator
import org.apache.commons.io.FilenameUtils
import org.apache.commons.io.input.ReaderInputStream
import org.apache.commons.io.output.WriterOutputStream
import java.io.*
import java.net.*
import java.nio.channels.ReadableByteChannel
import java.nio.charset.Charset
import java.nio.file.Path
import kotlin.reflect.KClass

@IgnoreForSerialize
object IO : HasMapNames {

    @FrameworkDsl
    val NULLS_BYTE_ARRAY = 4.toByteArray {
        NULLS_STRING[it].byteOf()
    }

    @FrameworkDsl
    const val EXT_SEPARATOR_CHAR = '.'

    @FrameworkDsl
    const val SINGLE_PREFIX_CHAR = '~'

    @FrameworkDsl
    const val COL_SEPARATOR_CHAR = ','

    @FrameworkDsl
    const val UNX_SEPARATOR_CHAR = '/'

    @FrameworkDsl
    const val WIN_SEPARATOR_CHAR = '\\'

    @FrameworkDsl
    const val SINGLE_TILDE = SINGLE_PREFIX_CHAR.toString()

    @FrameworkDsl
    const val SINGLE_SLASH = UNX_SEPARATOR_CHAR.toString()

    @FrameworkDsl
    const val DOUBLE_SLASH = SINGLE_SLASH + SINGLE_SLASH

    @FrameworkDsl
    const val TILDE_PREFIX = SINGLE_TILDE + SINGLE_SLASH

    @FrameworkDsl
    const val PREFIX_TILDE = SINGLE_SLASH + SINGLE_TILDE

    @FrameworkDsl
    const val SINGLE_MINUS = "-"

    @FrameworkDsl
    const val PREFIX_COLON = ":"

    @FrameworkDsl
    const val TYPE_IS_FILE = "file"

    @FrameworkDsl
    const val PREFIX_FILES = "file:"

    @FrameworkDsl
    const val PREFIX_BYTES = "data:"

    @FrameworkDsl
    const val PREFIX_CLASS = "classpath:"

    @FrameworkDsl
    private val SYSTEM_SEPARATOR_CHAR = File.separatorChar

    @FrameworkDsl
    private val OTHERS_SEPARATOR_CHAR = if (isSystemWindows()) UNX_SEPARATOR_CHAR else WIN_SEPARATOR_CHAR

    @FrameworkDsl
    private val probe: ContentTypeProbe by lazy {
        DefaultContentTypeProbe()
    }

    @JvmStatic
    @FrameworkDsl
    @IgnoreForSerialize
    fun getContentTypeProbe(): ContentTypeProbe = probe

    @JvmStatic
    @FrameworkDsl
    @IgnoreForSerialize
    fun isSystemWindows(): Boolean = SYSTEM_SEPARATOR_CHAR == WIN_SEPARATOR_CHAR

    @JvmStatic
    @FrameworkDsl
    @IgnoreForSerialize
    fun isSystemUnixLike(): Boolean = SYSTEM_SEPARATOR_CHAR != WIN_SEPARATOR_CHAR

    @JvmStatic
    @FrameworkDsl
    fun getFileExtension(file: File): String {
        return getPathExtension(file.getPathName())
    }

    @JvmStatic
    @FrameworkDsl
    fun getPathNormalized(path: CharSequence?): String? {
        val temp = toTrimOrNull(path)
        if (temp != null) {
            val norm = toTrimOrNull(FilenameUtils.normalizeNoEndSeparator(fixp(temp), true))
            if ((norm != null) && (norm.indexOf(SINGLE_PREFIX_CHAR) != IS_NOT_FOUND)) {
                if (norm.startsWith(SINGLE_TILDE)) {
                    return getPathNormalized(norm.substring(1))
                }
                if (norm.startsWith(TILDE_PREFIX)) {
                    return getPathNormalized(norm.substring(2))
                }
                if (norm.startsWith(PREFIX_TILDE)) {
                    return getPathNormalized(SINGLE_SLASH + norm.substring(2))
                }
            }
            return norm
        }
        return null
    }

    @FrameworkDsl
    private tailrec fun fixp(path: String): String {
        if (path.isEmptyOrBlank()) {
            return EMPTY_STRING
        }
        if (path.indexOf(DOUBLE_SLASH) == IS_NOT_FOUND) {
            return path
        }
        return fixp(path.replace(DOUBLE_SLASH, SINGLE_SLASH).trim())
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun getPathRelative(path: String, tail: String, root: Boolean = true): String {
        return FilenameUtils.concat(path, tail).otherwise().let { if (root) it.removePrefix(SINGLE_SLASH) else it }
    }

    @JvmStatic
    @FrameworkDsl
    fun getPathExtension(path: String?): String {
        val temp = getPathNormalized(path).otherwise()
        if ((temp.isNotExhausted()) && (temp.indexOf(EXT_SEPARATOR_CHAR) != IS_NOT_FOUND)) {
            val last = toTrimOrNull(FilenameUtils.getExtension(temp)).otherwise()
            if (last.isNotExhausted()) {
                if (last.startsWith(EXT_SEPARATOR_CHAR)) {
                    return last
                }
                return EXT_SEPARATOR_CHAR + last
            }
        }
        return EMPTY_STRING
    }

    @JvmStatic
    @FrameworkDsl
    fun toFileURL(path: String): URL {
        return PREFIX_FILES.plus(SINGLE_SLASH)
            .plus(getPathNormalized(path.removePrefix(PREFIX_FILES)).otherwise().removePrefix(SINGLE_SLASH).trim())
            .linkOf()
    }

    @JvmStatic
    @FrameworkDsl
    fun toURI(path: CharSequence): URI {
        try {
            return URI(path.copyOf())
        } catch (cause: Throwable) {
            fatal(cause)
        }
    }

    @JvmStatic
    @FrameworkDsl
    fun getRelative(data: URL, path: String): URL {
        return when (val file = toFileOrNull(data)) {
            null -> URL(data, getPathRelative(data.getPathName(), path).replace("#", "%23"))
            else -> getRelative(file, path).linkOf()
        }
    }

    @JvmStatic
    @FrameworkDsl
    fun getRelative(data: File, path: String): File {
        return File(getPathRelative(data.getPathName(), path))
    }

    @JvmStatic
    @FrameworkDsl
    fun getHeadConnection(data: URL): HttpURLConnection? {
        try {
            val conn = data.openConnection()
            if (conn is HttpURLConnection) {
                conn.requestMethod = "HEAD"
                return conn
            }
            conn.getInputStream().close()
        } catch (cause: Throwable) {
            Throwables.thrown(cause)
        }
        return null
    }

    @JvmStatic
    @FrameworkDsl
    @IgnoreForSerialize
    fun getDefaultClassLoader(): ClassLoader {
        try {
            val load = Thread.currentThread().contextClassLoader
            if (load != null) {
                return load
            }
        } catch (cause: Throwable) {
            Throwables.thrown(cause)
        }
        try {
            val load = IO.javaClass.classLoader
            if (load != null) {
                return load
            }
        } catch (cause: Throwable) {
            Throwables.thrown(cause)
        }
        try {
            val load = ClassLoader.getSystemClassLoader()
            if (load != null) {
                return load
            }
        } catch (cause: Throwable) {
            Throwables.thrown(cause)
        }
        return IO.javaClass.classLoader
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun getResouce(path: String, claz: Class<*>? = null, load: ClassLoader? = null): URL? = try {
        when {
            claz != null -> claz.getResource(path)
            load != null -> load.getResource(path)
            else -> when (val data = ClassLoader.getSystemResource(path)) {
                null -> getDefaultClassLoader().getResource(path)
                else -> data
            }
        }
    } catch (cause: Throwable) {
        Throwables.thrown(cause)
        null
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun getInputStream(path: String, claz: Class<*>? = null, load: ClassLoader? = null): InputStream? = try {
        getInputStream(getResouce(path, claz, load))
    } catch (cause: Throwable) {
        Throwables.thrown(cause)
        null
    }

    @JvmStatic
    @FrameworkDsl
    fun getInputStream(path: String, claz: KClass<*>): InputStream = try {
        getInputStream(getResouce(path, claz.java)).otherwise(EmptyInputStream)
    } catch (cause: Throwable) {
        Throwables.thrown(cause)
        EmptyInputStream
    }

    @JvmStatic
    @FrameworkDsl
    fun getInputStream(data: URL?): InputStream? {
        if (data == null) {
            return null
        }
        try {
            if (data.isFileURL()) {
                val file = toFileOrNull(data, true)
                if ((file != null) && (file.isValidToRead())) {
                    return file.toInputStream()
                }
                return null
            }
            return data.openStream()
        } catch (cause: Throwable) {
            Throwables.thrown(cause)
        }
        return null
    }

    @JvmStatic
    @FrameworkDsl
    fun getInputStream(data: URI?): InputStream? {
        if (data == null) {
            return null
        }
        try {
            return data.pathOf().toInputStream()
        } catch (cause: Throwable) {
            Throwables.thrown(cause)
        }
        try {
            return data.linkOf().toInputStream()
        } catch (cause: Throwable) {
            Throwables.thrown(cause)
        }
        return null
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun getInputStream(data: Reader, charset: Charset = DEFAULT_CHARSET_UTF_8): InputStream {
        return ReaderInputStream.builder().setCharset(charset).setReader(data).get()
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun getOutputStream(data: Writer, charset: Charset = DEFAULT_CHARSET_UTF_8): OutputStream {
        return WriterOutputStream.builder().setCharset(charset).setWriter(data).get()
    }

    @JvmStatic
    @FrameworkDsl
    fun getOutputStream(data: URL?): OutputStream? {
        if (data == null) {
            return null
        }
        try {
            if (data.isFileURL()) {
                val file = toFileOrNull(data, true)
                if (file != null) {
                    return file.toOutputStream()
                }
                return null
            } else {
                return data.openConnection().let {
                    it.doOutput = true
                    it.getOutputStream()
                }
            }
        } catch (cause: Throwable) {
            Throwables.thrown(cause)
        }
        return null
    }

    @JvmStatic
    @FrameworkDsl
    fun getOutputStream(data: URI?): OutputStream? {
        if (data == null) {
            return null
        }
        try {
            return data.pathOf().toOutputStream()
        } catch (cause: Throwable) {
            Throwables.thrown(cause)
        }
        try {
            return data.linkOf().toOutputStream()
        } catch (cause: Throwable) {
            Throwables.thrown(cause)
        }
        return null
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun toFileOrNull(data: URL, skip: Boolean = false): File? {
        try {
            if (skip || data.isFileURL()) {
                val path = getPathNormalized(data.getFileName()).otherwise()
                if (path.isNotExhausted()) {
                    return path.fileOf()
                }
            }
            return null
        } catch (cause: Throwable) {
            Throwables.thrown(cause)
        }
        return null
    }

    @JvmStatic
    @FrameworkDsl
    fun isContentThere(data: URL): Boolean {
        try {
            if (data.isFileURL()) {
                val file = toFileOrNull(data, true)
                return ((file != null) && (file.isValidToRead()))
            } else {
                val conn = getHeadConnection(data)
                if (conn != null) {
                    val code = conn.responseCode
                    conn.disconnect()
                    return (code in HttpURLConnection.HTTP_OK..HttpURLConnection.HTTP_ACCEPTED)
                }
                data.openStream().close()
                return true
            }
        } catch (cause: Throwable) {
            Throwables.thrown(cause)
        }
        return false
    }

    @JvmStatic
    @FrameworkDsl
    fun getContentSize(data: URL): Long {
        if (data.isFileURL()) {
            val file = toFileOrNull(data, true)
            if ((file != null) && (file.isValidToRead())) {
                return file.getContentSize()
            }
        } else {
            val conn = getHeadConnection(data)
            if (conn != null) {
                val size = conn.contentLengthLong
                conn.disconnect()
                if (size > 0) {
                    return size
                }
            }
            return data.toInputStream().use { it.copyTo(EmptyOutputStream) }
        }
        throw MercenaryExceptiion(data.toString())
    }

    @JvmStatic
    @FrameworkDsl
    fun getContentSize(data: URL, func: Factory<Long>): Long {
        if (data.isFileURL()) {
            val file = toFileOrNull(data, true)
            if ((file != null) && (file.isValidToRead())) {
                return file.getContentSize()
            }
        }
        return func.create()
    }

    @JvmStatic
    @FrameworkDsl
    fun getContentTime(data: URL): Long {
        if (data.isFileURL()) {
            val file = toFileOrNull(data, true)
            if ((file != null) && (file.isValidToRead())) {
                return file.getContentTime()
            }
        } else {
            val conn = getHeadConnection(data)
            if (conn != null) {
                val time = conn.lastModified
                conn.disconnect()
                if (time > 0) {
                    return time
                }
            }
            val temp = getHeadConnection(data)
            if (temp != null) {
                val time = temp.date
                temp.disconnect()
                return time
            }
        }
        throw MercenaryExceptiion(data.toString())
    }

    @JvmStatic
    @FrameworkDsl
    fun getContentType(data: URL, type: String): String {
        if (type.isDefaultContentType()) {
            if (data.isFileURL()) {
                val path = getPathNormalized(data.getFileName()).otherwise()
                if (path.isNotExhausted()) {
                    return getContentTypeProbe().getContentType(path, type)
                }
            } else {
                val conn = getHeadConnection(data)
                if (conn != null) {
                    val send = toTrimOrElse(conn.contentType, DEFAULT_CONTENT_TYPE)
                    conn.disconnect()
                    return send.toLowerTrim()
                }
            }
        }
        return type.toLowerTrim()
    }

    @JvmStatic
    @FrameworkDsl
    fun getContentMime(data: URL): ContentMimeType {
        try {
            val conn = getHeadConnection(data)
            if (conn != null) {
                val send = toTrimOrElse(conn.contentType, DEFAULT_CONTENT_TYPE)
                conn.disconnect()
                return if (send.isDefaultContentType()) ContentMimeType.getDefaultContentMimeType() else ContentMimeType(
                    send
                )
            }
        } catch (cause: Throwable) {
            Throwables.thrown(cause)
        }
        return ContentMimeType.getDefaultContentMimeType()
    }

    @JvmStatic
    @FrameworkDsl
    fun getContentType(data: URI, type: String): String {
        if (type.isDefaultContentType() && data.isAbsolutePath()) {
            try {
                return getContentType(data.linkOf(), type)
            } catch (cause: Throwable) {
                Throwables.thrown(cause)
            }
        }
        return type.toLowerTrim()
    }

    @JvmStatic
    @FrameworkDsl
    fun getContentType(data: ByteArray, type: String): String {
        if (type.isDefaultContentType()) {
            return getContentType(data.toInputStream(), type)
        }
        return type.toLowerTrim()
    }

    @JvmStatic
    @FrameworkDsl
    fun getContentType(data: ReadableByteChannel, type: String): String {
        if (type.isDefaultContentType()) {
            return getContentType(data.toInputStream(), type)
        }
        return type.toLowerTrim()
    }

    @JvmStatic
    @FrameworkDsl
    fun getContentType(data: InputStream, type: String): String {
        if (type.isDefaultContentType() && data.markSupported()) {
            val send = toTrimOrElse(HttpURLConnection.guessContentTypeFromStream(data)).toLowerTrim()
            if (send.isNotExhausted()) {
                return send
            }
        }
        return type.toLowerTrim()
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun getCharset(charset: CharSequence, default: Charset = DEFAULT_CHARSET_UTF_8): Charset {
        if (Charset.isSupported(charset.copyOf())) {
            return try {
                Charset.forName(charset.copyOf())
            } catch (cause: Throwable) {
                Throwables.fatal(cause, default)
            }
        }
        return default
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun getSupportedCharsetName(charset: CharSequence, default: Charset = DEFAULT_CHARSET_UTF_8): String {
        if (Charset.isSupported(charset.toChecked())) {
            return charset.toChecked()
        }
        return default.toString()
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun getSupportedCharsetName(charset: Charset, default: Charset = DEFAULT_CHARSET_UTF_8): String {
        return getSupportedCharsetName(charset.toString(), default)
    }

    @JvmStatic
    @FrameworkDsl
    private fun buffer(size: Long): Int {
        return if (size.isMoreThan(4096)) 4096 else size.intsOf().maxOf(512)
    }

    @JvmStatic
    @FrameworkDsl
    fun compare(value: Reader, other: Reader): Boolean {
        if (value is EmptyReader && other is EmptyReader) {
            return true
        }
        return compare(value.toInputStream(), other.toInputStream())
    }

    @JvmStatic
    @FrameworkDsl
    fun compare(value: InputStream, other: InputStream): Boolean {
        if (value is EmptyInputStream && other is EmptyInputStream) {
            return true
        }
        return compare(
            value.toBufferedInputStream(DEFAULT_BUFFERED_DATA_SIZE),
            other.toBufferedInputStream(DEFAULT_BUFFERED_DATA_SIZE)
        )
    }

    @JvmStatic
    @FrameworkDsl
    fun compare(value: Path, other: Path): Boolean {
        return compare(value.fileOf(), other.fileOf())
    }

    @JvmStatic
    @FrameworkDsl
    fun compare(value: File, other: File): Boolean {
        if (value === other) {
            return true
        }
        if (value isSameFile other) {
            return true
        }
        if (value.isValidToRead() && other.isValidToRead()) {
            if (value.getContentSize() == other.getContentSize()) {
                return compare(
                    value.toInputStream().toBufferedInputStream(buffer(value.getContentSize())),
                    other.toInputStream().toBufferedInputStream(buffer(other.getContentSize()))
                )
            }
        }
        return false
    }

    @JvmStatic
    @FrameworkDsl
    fun compare(value: BufferedInputStream, other: BufferedInputStream): Boolean {
        value.use { data ->
            other.use { copy ->
                do {
                    try {
                        data.read().let { byte ->
                            copy.read().let { code ->
                                if (code != byte) {
                                    return false
                                }
                                if (code.isNegative()) {
                                    return true
                                }
                            }
                        }
                    } catch (cause: Throwable) {
                        return Throwables.fatal(cause, false)
                    }
                } while (true)
            }
        }
    }

    fun lines(args: Reader): Sequence<String> {
        return args.buffered(DEFAULT_BUFFERED_DATA_SIZE).use { data ->
            sequence {
                try {
                    data.readLine().let { line ->
                        if (line == null) yieldAll(EmptyIterator) else yield(line)
                    }
                } catch (cause: Throwable) {
                    yieldAll(EmptyIterator)
                }
            }
        }
    }

    fun lines(args: BufferedReader): Sequence<String> {
        return args.use { data ->
            sequence {
                try {
                    data.readLine().let { line ->
                        if (line == null) yieldAll(EmptyIterator) else yield(line)
                    }
                } catch (cause: Throwable) {
                    yieldAll(EmptyIterator)
                }
            }
        }
    }

    @FrameworkDsl
    override fun toString() = toMapNames().toSafeString()

    @FrameworkDsl
    override fun toMapNames() = dictOf("type" to nameOf())
}