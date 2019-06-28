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
import java.io.*
import java.net.URL
import java.nio.file.Path
import javax.activation.FileTypeMap

interface ContentTypeProbe : FileTypeMapSupplier {
    fun getContentType(data: URL, type: String = DEFAULT_CONTENT_TYPE): String
    fun getContentType(data: File, type: String = DEFAULT_CONTENT_TYPE): String
    fun getContentType(data: Path, type: String = DEFAULT_CONTENT_TYPE): String
    fun getContentType(name: String, type: String = DEFAULT_CONTENT_TYPE): String
    fun getContentType(data: ByteArray, type: String = DEFAULT_CONTENT_TYPE): String
    fun getContentType(data: InputStream, type: String = DEFAULT_CONTENT_TYPE): String

    companion object {

        private val maps = ContentFileTypeMap()

        @JvmStatic
        fun isDefault(type: String): Boolean = getLowerTrim(type) == DEFAULT_CONTENT_TYPE

        @JvmStatic
        fun toCommon(name: String, type: String = DEFAULT_CONTENT_TYPE): String = when (getLowerTrim(IO.getPathExtension(name))) {
            ".json" -> "application/json"
            ".java" -> "text/x-java-source"
            ".yaml" -> "application/x-yaml"
            ".yml" -> "application/x-yaml"
            ".properties" -> "text/x-java-properties"
            else -> getLowerTrim(type)
        }

        @JvmStatic
        fun getDefaultFileTypeMap(): FileTypeMap = maps
    }
}