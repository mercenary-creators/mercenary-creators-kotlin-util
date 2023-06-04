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
import java.net.*

@IgnoreForSerialize
class URLContentResource @JvmOverloads @FrameworkDsl constructor(data: URL, private val type: String = DEFAULT_CONTENT_TYPE) : AbstractContentResource(data.toString(), type), OutputContentResource {

    @FrameworkDsl
    @JvmOverloads
    constructor(data: URI, type: String = DEFAULT_CONTENT_TYPE) : this(data.linkOf(), type)

    @FrameworkDsl
    private val link = data

    @FrameworkDsl
    @IgnoreForSerialize
    override fun getContentKind() = link.protocol.orEmpty()

    @FrameworkDsl
    @IgnoreForSerialize
    override fun isContentThere() = IO.isContentThere(link)

    @FrameworkDsl
    @IgnoreForSerialize
    override fun getContentMime() = IO.getContentMime(link)

    @FrameworkDsl
    @IgnoreForSerialize
    override fun getContentSize() = IO.getContentSize(link)

    @FrameworkDsl
    @IgnoreForSerialize
    override fun getContentTime() = IO.getContentTime(link)

    @FrameworkDsl
    @IgnoreForSerialize
    override fun getContentType() = IO.getContentType(link, type)

    @FrameworkDsl
    @IgnoreForSerialize
    override fun getInputStream() = link.toInputStream()

    @FrameworkDsl
    @IgnoreForSerialize
    override fun getOutputStream() = link.toOutputStream()

    @FrameworkDsl
    @IgnoreForSerialize
    override fun getContentLook(): ContentResourceLookup = { link.toRelative(it).toContentResource() }

    @FrameworkDsl
    override fun equals(other: Any?) = when (other) {
        is URLContentResource -> this === other || link == other.link
        else -> false
    }

    @FrameworkDsl
    override fun hashCode() = link.hashOf()

    @FrameworkDsl
    override fun toString() = getDescription()
}