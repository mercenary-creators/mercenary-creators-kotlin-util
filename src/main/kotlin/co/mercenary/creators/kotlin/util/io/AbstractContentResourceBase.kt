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

@IgnoreForSerialize
abstract class AbstractContentResourceBase @JvmOverloads constructor(private val path: String, private val type: String = DEFAULT_CONTENT_TYPE, private val time: Long = 0L) : ContentResource {

    private val kind: String by lazy {
        getContentKind().toLowerTrim().let { if (getContentPath().startsWith(it, true)) EMPTY_STRING else it }
    }

    private val desc: String by lazy {
        "${javaClass.name}($kind${getContentPath()}, ${getContentType()}, ${isContentCache()})"
    }

    @CreatorsDsl
    override fun toMapNames(): Map<String, Any?> {
        return dictOf("name" to javaClass.name, "path" to "$kind${getContentPath()}", "type" to getContentType(), "time" to getContentTime().toDate())
    }

    @CreatorsDsl
    override fun toString() = getDescription()

    @CreatorsDsl
    @IgnoreForSerialize
    override fun getContentPath() = path

    @CreatorsDsl
    @IgnoreForSerialize
    override fun getContentTime() = time

    @CreatorsDsl
    @IgnoreForSerialize
    override fun getDescription() = desc

    @CreatorsDsl
    @IgnoreForSerialize
    override fun getContentType() = type.toLowerTrim()

    @CreatorsDsl
    @IgnoreForSerialize
    override fun getContentMime() = ContentMimeType(getContentType())
}