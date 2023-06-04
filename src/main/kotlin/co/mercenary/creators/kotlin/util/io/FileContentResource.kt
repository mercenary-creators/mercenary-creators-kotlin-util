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
import java.io.File
import java.nio.file.Path

@IgnoreForSerialize
class FileContentResource @JvmOverloads @FrameworkDsl constructor(private val data: File, type: String = DEFAULT_CONTENT_TYPE) : AbstractContentResource(data.path, type), OutputContentResource {

    @FrameworkDsl
    @JvmOverloads
    constructor(data: Path, type: String = DEFAULT_CONTENT_TYPE) : this(data.fileOf(), type)

    private val type = resolved()

    private val kind = IO.PREFIX_FILES

    @FrameworkDsl
    @IgnoreForSerialize
    override fun getContentKind() = kind

    @FrameworkDsl
    @IgnoreForSerialize
    override fun getContentType() = type

    @FrameworkDsl
    @IgnoreForSerialize
    override fun getContentSize() = data.getContentSize()

    @FrameworkDsl
    @IgnoreForSerialize
    override fun getContentTime() = data.getContentTime()

    @FrameworkDsl
    @IgnoreForSerialize
    override fun isContentThere() = data.isValidToRead()

    @FrameworkDsl
    @IgnoreForSerialize
    override fun getInputStream() = data.toInputStream()

    @FrameworkDsl
    @IgnoreForSerialize
    override fun getOutputStream() = data.toOutputStream()

    @FrameworkDsl
    @IgnoreForSerialize
    override fun getContentLook(): ContentResourceLookup = { data.toRelative(it).toContentResource() }

    @FrameworkDsl
    override fun equals(other: Any?) = when (other) {
        is FileContentResource -> this === other || data isSameFileAndData other.data
        else -> false
    }

    @FrameworkDsl
    override fun hashCode() = data.hashCode()

    @FrameworkDsl
    override fun toString() = getDescription()
}