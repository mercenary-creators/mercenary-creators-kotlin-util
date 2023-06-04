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

@FrameworkDsl
@IgnoreForSerialize
object EmptyContentResource : CachedContentResource {

    private val path: String by lazy {
        IO.PREFIX_FILES.plus("/dev/null").toLowerTrim()
    }

    private val desc: String by lazy {
        "${nameOf()}(${getContentPath()}, ${getContentType()}, ${isContentCache()})"
    }

    @FrameworkDsl
    override fun toMapNames(): Map<String, Any?> {
        return dictOf("name" to nameOf(), "path" to getContentPath(), "type" to getContentType(), "time" to getContentTime().toDate())
    }

    @FrameworkDsl
    override fun toString() = getDescription()

    @FrameworkDsl
    @IgnoreForSerialize
    override fun getContentSize() = 0L

    @FrameworkDsl
    @IgnoreForSerialize
    override fun getContentTime() = TimeAndDate.getTimeStamp()

    @FrameworkDsl
    @IgnoreForSerialize
    override fun getContentPath() = path

    @FrameworkDsl
    @IgnoreForSerialize
    override fun getDescription() = desc

    @FrameworkDsl
    @IgnoreForSerialize
    override fun getContentKind() = EMPTY_STRING

    @FrameworkDsl
    @IgnoreForSerialize
    override fun isContentCache() = true

    @FrameworkDsl
    @IgnoreForSerialize
    override fun isContentThere() = true

    @FrameworkDsl
    @IgnoreForSerialize
    override fun getContentData() = EMPTY_BYTE_ARRAY

    @FrameworkDsl
    @IgnoreForSerialize
    override fun getInputStream() = EmptyInputStream

    @FrameworkDsl
    @IgnoreForSerialize
    override fun toContentCache() = EmptyContentResource

    @FrameworkDsl
    @IgnoreForSerialize
    override fun getContentType() = DEFAULT_CONTENT_TYPE

    @FrameworkDsl
    @IgnoreForSerialize
    override fun getContentMime() = ContentMimeType.getDefaultContentMimeType()

    @FrameworkDsl
    @IgnoreForSerialize
    override fun getContentLook(): ContentResourceLookup = { _ -> EmptyContentResource }
}