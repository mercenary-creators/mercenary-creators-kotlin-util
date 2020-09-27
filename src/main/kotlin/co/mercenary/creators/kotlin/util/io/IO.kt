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

package co.mercenary.creators.kotlin.util.io

import co.mercenary.creators.kotlin.util.*
import org.apache.commons.io.FilenameUtils
import org.apache.commons.io.input.ReaderInputStream
import org.apache.commons.io.output.WriterOutputStream
import java.io.*
import java.net.*
import java.nio.channels.ReadableByteChannel
import java.nio.charset.Charset
import java.nio.file.Paths

@IgnoreForSerialize
object IO : HasMapNames {

    const val EXT_SEPARATOR_CHAR = '.'
    const val SINGLE_PREFIX_CHAR = '~'
    const val COL_SEPARATOR_CHAR = ','
    const val UNX_SEPARATOR_CHAR = '/'
    const val WIN_SEPARATOR_CHAR = '\\'
    const val SINGLE_TILDE = SINGLE_PREFIX_CHAR.toString()
    const val SINGLE_SLASH = UNX_SEPARATOR_CHAR.toString()
    const val DOUBLE_SLASH = SINGLE_SLASH + SINGLE_SLASH
    const val TILDE_PREFIX = SINGLE_TILDE + SINGLE_SLASH
    const val PREFIX_TILDE = SINGLE_SLASH + SINGLE_TILDE
    const val SINGLE_MINUS = "-"
    const val PREFIX_COLON = ":"
    const val TYPE_IS_FILE = "file"
    const val PREFIX_FILES = "file:"
    const val PREFIX_BYTES = "data:"
    const val PREFIX_CLASS = "classpath:"
    private val SYSTEM_SEPARATOR_CHAR = File.separatorChar
    private val OTHERS_SEPARATOR_CHAR = if (isSystemWindows()) UNX_SEPARATOR_CHAR else WIN_SEPARATOR_CHAR

    private val probe: ContentTypeProbe by lazy {
        DefaultContentTypeProbe()
    }

    @CreatorsDsl
    private fun patch(path: String): String = path.trim().let { if (it.contains(DOUBLE_SLASH)) patch(it.replace(DOUBLE_SLASH, SINGLE_SLASH)) else it }

    @JvmStatic
    @CreatorsDsl
    @IgnoreForSerialize
    fun getContentTypeProbe(): ContentTypeProbe = probe

    @JvmStatic
    @CreatorsDsl
    @IgnoreForSerialize
    fun isSystemWindows(): Boolean = SYSTEM_SEPARATOR_CHAR == WIN_SEPARATOR_CHAR

    @JvmStatic
    @CreatorsDsl
    @IgnoreForSerialize
    fun isSystemUnixLike(): Boolean = SYSTEM_SEPARATOR_CHAR != WIN_SEPARATOR_CHAR

    @JvmStatic
    @CreatorsDsl
    fun getFileExtension(file: File): String {
        return getPathExtension(file.path)
    }

    @JvmStatic
    @CreatorsDsl
    fun getPathNormalized(path: String?): String? {
        val temp = toTrimOrNull(path)
        if (temp != null) {
            val norm = toTrimOrNull(FilenameUtils.normalizeNoEndSeparator(patch(temp), true))
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

    @JvmStatic
    @CreatorsDsl
    @JvmOverloads
    fun getPathRelative(path: String, tail: String, root: Boolean = true): String {
        return FilenameUtils.concat(path, tail).orElse().let { if (root) it.removePrefix(SINGLE_SLASH) else it }
    }

    @JvmStatic
    @CreatorsDsl
    fun getPathExtension(path: String?): String {
        val temp = getPathNormalized(path).orElse()
        if ((temp.isNotEmpty()) && (temp.indexOf(EXT_SEPARATOR_CHAR) != IS_NOT_FOUND)) {
            val last = toTrimOrNull(FilenameUtils.getExtension(temp)).orElse()
            if (last.isNotEmpty()) {
                if (last.startsWith(EXT_SEPARATOR_CHAR)) {
                    return last
                }
                return EXT_SEPARATOR_CHAR + last
            }
        }
        return EMPTY_STRING
    }

    @JvmStatic
    @CreatorsDsl
    fun toFileURL(path: String): URL {
        return PREFIX_FILES.plus(SINGLE_SLASH).plus(getPathNormalized(path.removePrefix(PREFIX_FILES)).orElse().removePrefix(SINGLE_SLASH).trim()).toURL()
    }

    @JvmStatic
    @CreatorsDsl
    fun getRelative(data: URL, path: String): URL {
        return when (val file = toFileOrNull(data)) {
            null -> URL(data, getPathRelative(data.path, path).replace("#", "%23"))
            else -> getRelative(file, path).toURI().toURL()
        }
    }

    @JvmStatic
    @CreatorsDsl
    fun getRelative(data: File, path: String): File {
        return File(getPathRelative(data.path, path))
    }

    @JvmStatic
    @CreatorsDsl
    fun getHeadConnection(data: URL): HttpURLConnection? {
        try {
            val conn = data.openConnection()
            if (conn is HttpURLConnection) {
                conn.requestMethod = "HEAD"
                return conn
            }
            conn.getInputStream().close()
        }
        catch (cause: Throwable) {
            Throwables.thrown(cause)
        }
        return null
    }

    @JvmStatic
    @CreatorsDsl
    @IgnoreForSerialize
    fun getDefaultClassLoader(): ClassLoader {
        try {
            val load = Thread.currentThread().contextClassLoader
            if (load != null) {
                return load
            }
        }
        catch (cause: Throwable) {
            Throwables.thrown(cause)
        }
        try {
            val load = IO.javaClass.classLoader
            if (load != null) {
                return load
            }
        }
        catch (cause: Throwable) {
            Throwables.thrown(cause)
        }
        try {
            val load = ClassLoader.getSystemClassLoader()
            if (load != null) {
                return load
            }
        }
        catch (cause: Throwable) {
            Throwables.thrown(cause)
        }
        return IO.javaClass.classLoader
    }

    @JvmStatic
    @CreatorsDsl
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
    }
    catch (cause: Throwable) {
        Throwables.thrown(cause)
        null
    }

    @JvmStatic
    @CreatorsDsl
    @JvmOverloads
    fun getInputStream(path: String, claz: Class<*>? = null, load: ClassLoader? = null): InputStream? = try {
        getInputStream(getResouce(path, claz, load))
    }
    catch (cause: Throwable) {
        Throwables.thrown(cause)
        null
    }

    @JvmStatic
    @CreatorsDsl
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
        }
        catch (cause: Throwable) {
            Throwables.thrown(cause)
        }
        return null
    }

    @JvmStatic
    @CreatorsDsl
    fun getInputStream(data: URI?): InputStream? {
        if (data == null) {
            return null
        }
        try {
            return Paths.get(data).toInputStream()
        }
        catch (cause: Throwable) {
            Throwables.thrown(cause)
        }
        try {
            return data.toURL().toInputStream()
        }
        catch (cause: Throwable) {
            Throwables.thrown(cause)
        }
        return null
    }

    @JvmStatic
    @CreatorsDsl
    @JvmOverloads
    fun getInputStream(data: Reader, charset: Charset = Charsets.UTF_8): InputStream {
        return ReaderInputStream(data, charset)
    }

    @JvmStatic
    @CreatorsDsl
    @JvmOverloads
    fun getOutputStream(data: Writer, charset: Charset = Charsets.UTF_8): OutputStream {
        return WriterOutputStream(data, charset)
    }

    @JvmStatic
    @CreatorsDsl
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
            }
            else {
                return data.openConnection().let {
                    it.doOutput = true
                    it.getOutputStream()
                }
            }
        }
        catch (cause: Throwable) {
            Throwables.thrown(cause)
        }
        return null
    }

    @JvmStatic
    @CreatorsDsl
    fun getOutputStream(data: URI?): OutputStream? {
        if (data == null) {
            return null
        }
        try {
            return Paths.get(data).toOutputStream()
        }
        catch (cause: Throwable) {
            Throwables.thrown(cause)
        }
        try {
            return data.toURL().toOutputStream()
        }
        catch (cause: Throwable) {
            Throwables.thrown(cause)
        }
        return null
    }

    @JvmStatic
    @CreatorsDsl
    @JvmOverloads
    fun toFileOrNull(data: URL, skip: Boolean = false): File? {
        try {
            if (skip || data.isFileURL()) {
                val path = getPathNormalized(data.file).orElse()
                if (path.isNotEmpty()) {
                    return File(path)
                }
            }
            return null
        }
        catch (cause: Throwable) {
            Throwables.thrown(cause)
        }
        return null
    }

    @JvmStatic
    @CreatorsDsl
    fun isContentThere(data: URL): Boolean {
        try {
            if (data.isFileURL()) {
                val file = toFileOrNull(data, true)
                return ((file != null) && (file.isValidToRead()))
            }
            else {
                val conn = getHeadConnection(data)
                if (conn != null) {
                    val code = conn.responseCode
                    conn.disconnect()
                    return (code in HttpURLConnection.HTTP_OK..HttpURLConnection.HTTP_ACCEPTED)
                }
                data.openStream().close()
                return true
            }
        }
        catch (cause: Throwable) {
            Throwables.thrown(cause)
        }
        return false
    }

    @JvmStatic
    @CreatorsDsl
    fun getContentSize(data: URL): Long {
        if (data.isFileURL()) {
            val file = toFileOrNull(data, true)
            if ((file != null) && (file.isValidToRead())) {
                return file.length()
            }
        }
        else {
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
    @CreatorsDsl
    inline fun getContentSize(data: URL, func: () -> Long): Long {
        if (data.isFileURL()) {
            val file = toFileOrNull(data, true)
            if ((file != null) && (file.isValidToRead())) {
                return file.length()
            }
        }
        return func.invoke()
    }

    @JvmStatic
    @CreatorsDsl
    fun getContentTime(data: URL): Long {
        if (data.isFileURL()) {
            val file = toFileOrNull(data, true)
            if ((file != null) && (file.isValidToRead())) {
                return file.lastModified()
            }
        }
        else {
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
    @CreatorsDsl
    fun getContentType(data: URL, type: String): String {
        if (type.isDefaultContentType()) {
            if (data.isFileURL()) {
                val path = getPathNormalized(data.file).orElse()
                if (path.isNotEmpty()) {
                    return getContentTypeProbe().getContentType(path, type)
                }
            }
            else {
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
    @CreatorsDsl
    fun getContentMime(data: URL): ContentMimeType {
        try {
            val conn = getHeadConnection(data)
            if (conn != null) {
                val send = toTrimOrElse(conn.contentType, DEFAULT_CONTENT_TYPE)
                conn.disconnect()
                return if (send.isDefaultContentType()) ContentMimeType.DEFAULT_CONTENT_MIME_TYPE else ContentMimeType(send)
            }
        }
        catch (cause: Throwable) {
            Throwables.thrown(cause)
        }
        return ContentMimeType.DEFAULT_CONTENT_MIME_TYPE
    }

    @JvmStatic
    @CreatorsDsl
    fun getContentType(data: URI, type: String): String {
        if (type.isDefaultContentType() && data.isAbsolute) {
            try {
                return getContentType(data.toURL(), type)
            }
            catch (cause: Throwable) {
                Throwables.thrown(cause)
            }
        }
        return type.toLowerTrim()
    }

    @JvmStatic
    @CreatorsDsl
    fun getContentType(data: ByteArray, type: String): String {
        if (type.isDefaultContentType()) {
            return getContentType(data.toInputStream(), type)
        }
        return type.toLowerTrim()
    }

    @JvmStatic
    @CreatorsDsl
    fun getContentType(data: ReadableByteChannel, type: String): String {
        if (type.isDefaultContentType()) {
            return getContentType(data.toInputStream(), type)
        }
        return type.toLowerTrim()
    }

    @JvmStatic
    @CreatorsDsl
    fun getContentType(data: InputStream, type: String): String {
        if (type.isDefaultContentType() && data.markSupported()) {
            val send = toTrimOrElse(HttpURLConnection.guessContentTypeFromStream(data)).toLowerTrim()
            if (send.isNotEmpty()) {
                return send
            }
        }
        return type.toLowerTrim()
    }

    @CreatorsDsl
    override fun toMapNames() = dictOf("name" to nameOf(), "unix" to isSystemUnixLike())
}