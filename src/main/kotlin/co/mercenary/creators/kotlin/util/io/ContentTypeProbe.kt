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
import java.io.*
import java.net.*
import java.nio.channels.ReadableByteChannel
import java.nio.file.Path

interface ContentTypeProbe : FileTypeMapSupplier {

    operator fun get(name: String): String = getContentType(name)

    @FrameworkDsl
    fun getContentType(data: URI, type: String = DEFAULT_CONTENT_TYPE): String

    @FrameworkDsl
    fun getContentType(data: URL, type: String = DEFAULT_CONTENT_TYPE): String

    @FrameworkDsl
    fun getContentType(data: File, type: String = DEFAULT_CONTENT_TYPE): String

    @FrameworkDsl
    fun getContentType(data: Path, type: String = DEFAULT_CONTENT_TYPE): String

    @FrameworkDsl
    fun getContentType(name: String, type: String = DEFAULT_CONTENT_TYPE): String

    @FrameworkDsl
    fun getContentType(data: ByteArray, type: String = DEFAULT_CONTENT_TYPE): String

    @FrameworkDsl
    fun getContentType(data: InputStream, type: String = DEFAULT_CONTENT_TYPE): String

    @FrameworkDsl
    fun getContentType(data: ReadableByteChannel, type: String = DEFAULT_CONTENT_TYPE): String

    companion object {

        @FrameworkDsl
        private val maps: DefaultContentFileTypeMap by lazy {
            DefaultContentFileTypeMap()
        }

        @JvmStatic
        @FrameworkDsl
        fun getDefaultFileTypeMap(): DefaultContentFileTypeMap = maps
    }
}