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
import java.io.File
import java.nio.file.Path

@IgnoreForSerialize
class FileContentResource @JvmOverloads constructor(private val data: File, type: String = DEFAULT_CONTENT_TYPE) : AbstractContentResource(data.path, type), OutputContentResource {

    @JvmOverloads
    constructor(data: Path, type: String = DEFAULT_CONTENT_TYPE) : this(data.toFile(), type)

    private val type = resolved()

    private val kind = IO.PREFIX_FILES

    @IgnoreForSerialize
    override fun getContentKind() = kind

    @IgnoreForSerialize
    override fun getContentType() = type

    @IgnoreForSerialize
    override fun getContentSize() = data.length()

    @IgnoreForSerialize
    override fun getContentTime() = data.lastModified()

    @AssumptionDsl
    @IgnoreForSerialize
    override fun isContentThere() = data.isValidToRead()

    @IgnoreForSerialize
    override fun getInputStream() = data.toInputStream()

    @IgnoreForSerialize
    override fun getOutputStream() = data.toOutputStream()

    @IgnoreForSerialize
    override fun getContentLook(): ContentResourceLookup = { data.toRelative(it).toContentResource() }

    override fun toString() = getDescription()

    @AssumptionDsl
    override fun equals(other: Any?) = when (other) {
        is FileContentResource -> this === other || data isSameFileAndData other.data
        else -> false
    }

    override fun hashCode() = data.hashCode()
}