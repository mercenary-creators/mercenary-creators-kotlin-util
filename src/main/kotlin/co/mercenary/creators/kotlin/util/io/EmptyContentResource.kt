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

@CreatorsDsl
@IgnoreForSerialize
object EmptyContentResource : CachedContentResource {

    private val path: String by lazy {
        IO.PREFIX_FILES.plus("/dev/null").toLowerTrim()
    }

    private val desc: String by lazy {
        "${nameOf()}(${getContentPath()}, ${getContentType()}, ${isContentCache()})"
    }

    @CreatorsDsl
    override fun toMapNames(): Map<String, Any?> {
        return dictOf("name" to nameOf(), "path" to getContentPath(), "type" to getContentType(), "time" to getContentTime().toDate())
    }

    @CreatorsDsl
    override fun toString() = getDescription()

    @CreatorsDsl
    @IgnoreForSerialize
    override fun getContentSize() = 0L

    @CreatorsDsl
    @IgnoreForSerialize
    override fun getContentTime() = TimeAndDate.getTimeStamp()

    @CreatorsDsl
    @IgnoreForSerialize
    override fun getContentPath() = path

    @CreatorsDsl
    @IgnoreForSerialize
    override fun getDescription() = desc

    @CreatorsDsl
    @IgnoreForSerialize
    override fun getContentKind() = EMPTY_STRING

    @CreatorsDsl
    @IgnoreForSerialize
    override fun isContentCache() = true

    @CreatorsDsl
    @IgnoreForSerialize
    override fun isContentThere() = true

    @CreatorsDsl
    @IgnoreForSerialize
    override fun getContentData() = EMPTY_BYTE_ARRAY

    @CreatorsDsl
    @IgnoreForSerialize
    override fun getInputStream() = EmptyInputStream

    @CreatorsDsl
    @IgnoreForSerialize
    override fun toContentCache() = EmptyContentResource

    @CreatorsDsl
    @IgnoreForSerialize
    override fun getContentType() = DEFAULT_CONTENT_TYPE

    @CreatorsDsl
    @IgnoreForSerialize
    override fun getContentMime() = ContentMimeType.getDefaultContentMimeType()

    @CreatorsDsl
    @IgnoreForSerialize
    override fun getContentLook(): ContentResourceLookup = { _ -> EmptyContentResource }
}