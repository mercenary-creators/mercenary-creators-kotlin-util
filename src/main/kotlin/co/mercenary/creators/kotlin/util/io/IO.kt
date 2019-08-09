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

package co.mercenary.creators.kotlin.util.io

import co.mercenary.creators.kotlin.util.*
import org.apache.commons.io.FilenameUtils
import java.io.*
import java.net.*

object IO {

    const val EXT_SEPARATOR_CHAR = '.'
    const val SINGLE_PREFIX_CHAR = '~'
    const val COL_SEPARATOR_CHAR = ','
    const val UNX_SEPARATOR_CHAR = '/'
    const val SINGLE_TILDE = SINGLE_PREFIX_CHAR.toString()
    const val SINGLE_SLASH = UNX_SEPARATOR_CHAR.toString()
    const val DOUBLE_SLASH = SINGLE_SLASH + SINGLE_SLASH
    const val TILDE_PREFIX = SINGLE_TILDE + SINGLE_SLASH
    const val PREFIX_TILDE = SINGLE_SLASH + SINGLE_TILDE
    const val PREFIX_BYTES = "byte:"
    const val PREFIX_FILES = "file:"
    const val PREFIX_CLASS = "classpath:"

    private val probe: ContentTypeProbe by lazy {
        DefaultContentTypeProbe()
    }

    private fun patch(path: String): String = path.trim().let { if (it.contains(DOUBLE_SLASH)) patch(it.replace(DOUBLE_SLASH, SINGLE_SLASH)) else it }

    @JvmStatic
    fun getContentTypeProbe() = probe

    @JvmStatic
    fun getFileExtension(file: File): String {
        return getPathExtension(file.path)
    }

    @JvmStatic
    fun getPathNormalized(path: String?): String? {
        val temp = toTrimOrNull(path)
        if (temp != null) {
            val norm = toTrimOrNull(FilenameUtils.normalizeNoEndSeparator(getCheckedString(patch(temp)), true))
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
    fun getPathExtension(path: String?): String {
        val temp = getPathNormalized(path).orEmpty()
        if ((temp.isNotEmpty()) && (temp.indexOf(EXT_SEPARATOR_CHAR) != IS_NOT_FOUND)) {
            val last = getCheckedString(toTrimOrNull(FilenameUtils.getExtension(temp)).orEmpty())
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
            Throwables.assert(cause)
        }
        return null
    }

    @JvmStatic
    fun getDefaultClassLoader(): ClassLoader? {
        try {
            val load = Thread.currentThread().contextClassLoader
            if (load != null) {
                return load
            }
        }
        catch (cause: Throwable) {
            Throwables.assert(cause)
        }
        try {
            val load = IO.javaClass.classLoader
            if (load != null) {
                return load
            }
        }
        catch (cause: Throwable) {
            Throwables.assert(cause)
        }
        try {
            val load = ClassLoader.getSystemClassLoader()
            if (load != null) {
                return load
            }
        }
        catch (cause: Throwable) {
            Throwables.assert(cause)
        }
        return null
    }

    @JvmStatic
    fun getResouce(path: String, claz: Class<*>? = null, load: ClassLoader? = null): URL? = try {
        when {
            claz != null -> claz.getResource(path)
            load != null -> load.getResource(path)
            else -> when (val data = ClassLoader.getSystemResource(path)) {
                null -> getDefaultClassLoader()?.getResource(path)
                else -> data
            }
        }
    }
    catch (cause: Throwable) {
        Throwables.assert(cause)
        null
    }

    @JvmStatic
    fun getInputStream(path: String, claz: Class<*>? = null, load: ClassLoader? = null): InputStream? {
        try {
            return getInputStream(getResouce(path, claz, load))
        }
        catch (cause: Throwable) {
            Throwables.assert(cause)
        }
        return null
    }

    @JvmStatic
    fun getInputStream(data: URL?): InputStream? {
        if (data == null) {
            return null
        }
        try {
            if (isFileURL(data)) {
                val file = toFileOrNull(data)
                if ((file != null) && (file.isValidToRead())) {
                    return file.toInputStream()
                }
                return null
            }
            return data.openStream()
        }
        catch (cause: Throwable) {
            Throwables.assert(cause)
        }
        return null
    }

    @JvmStatic
    fun getOutputStream(data: URL?): OutputStream? {
        if (data == null) {
            return null
        }
        try {
            if (isFileURL(data)) {
                val file = toFileOrNull(data)
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
            Throwables.assert(cause)
        }
        return null
    }

    @JvmStatic
    fun toFileOrNull(data: URL): File? {
        try {
            if (isFileURL(data)) {
                val path = getPathNormalized(data.file).orEmpty()
                if (path.isNotEmpty()) {
                    return File(path)
                }
            }
            return null
        }
        catch (cause: Throwable) {
            Throwables.assert(cause)
        }
        return null
    }

    @JvmStatic
    fun isContentThere(data: URL): Boolean {
        try {
            if (isFileURL(data)) {
                val file = toFileOrNull(data)
                return ((file != null) && (file.isValidToRead()))
            }
            else {
                val conn = getHeadConnection(data)
                if (conn != null) {
                    val code = conn.responseCode
                    conn.disconnect()
                    if (code == HttpURLConnection.HTTP_OK) {
                        return true
                    }
                    if (code == HttpURLConnection.HTTP_NOT_FOUND) {
                        return false
                    }
                }
                data.openStream().close()
                return true
            }
        }
        catch (cause: Throwable) {
            Throwables.assert(cause)
        }
        return false
    }

    @JvmStatic
    fun getContentSize(data: URL): Long {
        if (isFileURL(data)) {
            val file = toFileOrNull(data)
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
            return data.toInputStream().use { it.copyTo(EmptyOutputStream()) }
        }
        throw MercenaryExceptiion(data.toString())
    }

    @JvmStatic
    fun getContentSize(data: URL, func: () -> Long): Long {
        if (isFileURL(data)) {
            val file = toFileOrNull(data)
            if ((file != null) && (file.isValidToRead())) {
                return file.length()
            }
        }
        return func()
    }

    @JvmStatic
    fun getContentTime(data: URL): Long {
        if (isFileURL(data)) {
            val file = toFileOrNull(data)
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
    fun getContentType(data: URL, type: String): String {
        if (isDefaultContentType(type)) {
            if (isFileURL(data)) {
                val path = getPathNormalized(data.file).orEmpty()
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
    fun getContentType(data: ByteArray, type: String): String {
        if (isDefaultContentType(type)) {
            return getContentType(ByteArrayInputStream(data), type)
        }
        return type.toLowerTrim()
    }

    @JvmStatic
    fun getContentType(data: InputStream, type: String): String {
        val kind = type.toLowerTrim()
        when {
            isDefaultContentType(kind) && data.markSupported() -> {
                val send = toTrimOrElse(HttpURLConnection.guessContentTypeFromStream(data), EMPTY_STRING).toLowerTrim()
                if (send.isNotEmpty()) {
                    return send
                }
            }
        }
        return kind
    }
}