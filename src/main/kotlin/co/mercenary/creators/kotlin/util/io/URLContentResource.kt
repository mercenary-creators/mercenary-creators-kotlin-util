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
import java.net.*

@IgnoreForSerialize
class URLContentResource @JvmOverloads @CreatorsDsl constructor(private val data: URL, private val type: String = DEFAULT_CONTENT_TYPE) : AbstractContentResource(data.toString(), type), OutputContentResource {

    @CreatorsDsl
    @JvmOverloads
    constructor(data: URI, type: String = DEFAULT_CONTENT_TYPE) : this(data.toURL(), type)

    @CreatorsDsl
    @IgnoreForSerialize
    override fun getContentKind() = data.protocol.orEmpty()

    @CreatorsDsl
    @IgnoreForSerialize
    override fun isContentThere() = IO.isContentThere(data)

    @CreatorsDsl
    @IgnoreForSerialize
    override fun getContentMime() = IO.getContentMime(data)

    @CreatorsDsl
    @IgnoreForSerialize
    override fun getContentSize() = IO.getContentSize(data)

    @CreatorsDsl
    @IgnoreForSerialize
    override fun getContentTime() = IO.getContentTime(data)

    @CreatorsDsl
    @IgnoreForSerialize
    override fun getContentType() = IO.getContentType(data, type)

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
        is URLContentResource -> this === other || data isSameAs other.data
        else -> false
    }

    override fun hashCode() = data.hashCode()

    override fun toString() = getDescription()
}