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

@SerialIgnore
class MimeContentTypeProbe @JvmOverloads constructor(private val maps: FileTypeMap = ContentTypeProbe.getDefaultFileTypeMap()) : ContentTypeProbe {

    override fun getContentType(data: ByteArray, type: String): String {
        return IO.getContentType(data, type)
    }

    override fun getContentType(data: InputStream, type: String): String {
        return IO.getContentType(data, type)
    }

    override fun getContentType(data: URL, type: String): String {
        return IO.getContentType(data, type)
    }

    override fun getContentType(data: File, type: String): String = getContentType(data.name, type)

    override fun getContentType(data: Path, type: String): String = getContentType(data.toFile(), type)

    override fun getContentType(name: String, type: String): String {
        if (isDefaultContentType(type)) {
            val path = getPathNormalizedOrElse(name)
            val look = getFileTypeMap().getContentType(path).toLowerTrim()
            if (isDefaultContentType(look)) {
                return toCommonContentTypes(name)
            }
            return look
        }
        return type.toLowerTrim()
    }

    @SerialIgnore
    override fun getFileTypeMap(): FileTypeMap = maps
}