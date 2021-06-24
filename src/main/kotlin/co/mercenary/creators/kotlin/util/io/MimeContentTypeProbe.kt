/*
 * Copyright (c) 2021, Mercenary Creators Company. All rights reserved.
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

@IgnoreForSerialize
class MimeContentTypeProbe @JvmOverloads @FrameworkDsl constructor(private val maps: ContentFileTypeMap = ContentTypeProbe.getDefaultFileTypeMap()) : ContentTypeProbe {

    @FrameworkDsl
    override fun getContentType(data: ByteArray, type: String): String {
        return IO.getContentType(data, type)
    }

    @FrameworkDsl
    override fun getContentType(data: InputStream, type: String): String {
        return IO.getContentType(data, type)
    }

    @FrameworkDsl
    override fun getContentType(data: ReadableByteChannel, type: String): String {
        return IO.getContentType(data, type)
    }

    @FrameworkDsl
    override fun getContentType(data: URI, type: String): String {
        return IO.getContentType(data, type)
    }

    @FrameworkDsl
    override fun getContentType(data: URL, type: String): String {
        return IO.getContentType(data, type)
    }

    @FrameworkDsl
    override fun getContentType(data: File, type: String): String = getContentType(data.name, type)

    @FrameworkDsl
    override fun getContentType(data: Path, type: String): String = getContentType(data.toFile(), type)

    @FrameworkDsl
    override fun getContentType(name: String, type: String): String {
        if (type.isDefaultContentType()) {
            val path = getPathNormalizedOrElse(name)
            val look = getFileTypeMap().getContentType(path).toLowerTrim()
            if (look.isDefaultContentType()) {
                return name.toCommonContentType()
            }
            return look
        }
        return type.toLowerTrim()
    }

    @FrameworkDsl
    @IgnoreForSerialize
    override fun getFileTypeMap(): ContentFileTypeMap = maps
}