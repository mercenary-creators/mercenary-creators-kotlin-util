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
class FileContentResource @JvmOverloads @CreatorsDsl constructor(private val data: File, type: String = DEFAULT_CONTENT_TYPE) : AbstractContentResource(data.path, type), OutputContentResource {

    @CreatorsDsl
    @JvmOverloads
    constructor(data: Path, type: String = DEFAULT_CONTENT_TYPE) : this(data.toFile(), type)

    private val type = resolved()

    private val kind = IO.PREFIX_FILES

    @CreatorsDsl
    @IgnoreForSerialize
    override fun getContentKind() = kind

    @CreatorsDsl
    @IgnoreForSerialize
    override fun getContentType() = type

    @CreatorsDsl
    @IgnoreForSerialize
    override fun getContentSize() = data.length()

    @CreatorsDsl
    @IgnoreForSerialize
    override fun getContentTime() = data.lastModified()

    @CreatorsDsl
    @IgnoreForSerialize
    override fun isContentThere() = data.isValidToRead()

    @CreatorsDsl
    @IgnoreForSerialize
    override fun getInputStream() = data.toInputStream()

    @CreatorsDsl
    @IgnoreForSerialize
    override fun getOutputStream() = data.toOutputStream()

    @CreatorsDsl
    @IgnoreForSerialize
    override fun getContentLook(): ContentResourceLookup = { data.toRelative(it).toContentResource() }

    @CreatorsDsl
    override fun equals(other: Any?) = when (other) {
        is FileContentResource -> this === other || data isSameFileAndData other.data
        else -> false
    }

    @CreatorsDsl
    override fun hashCode() = data.hashCode()

    @CreatorsDsl
    override fun toString() = getDescription()
}