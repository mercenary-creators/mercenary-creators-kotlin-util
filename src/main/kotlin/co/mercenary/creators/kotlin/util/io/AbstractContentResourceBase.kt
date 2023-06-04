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

@IgnoreForSerialize
abstract class AbstractContentResourceBase @JvmOverloads constructor(private val path: String, private val type: String = DEFAULT_CONTENT_TYPE, private val time: Long = 0L) : ContentResource {

    @FrameworkDsl
    private val kind: String by lazy {
        getContentKind().toLowerTrim().let { if (getContentPath().startsWith(it, true)) EMPTY_STRING else it }
    }

    @FrameworkDsl
    private val desc: String by lazy {
        "${nameOf()}($kind${getContentPath()}, ${getContentType()}, ${isContentCache()})"
    }

    @FrameworkDsl
    override fun toMapNames(): AnyDictionary {
        return dictOf("name" to nameOf(), "path" to "$kind${getContentPath()}", "type" to getContentType(), "time" to getContentTime().toDate())
    }

    @FrameworkDsl
    override fun toString() = getDescription()

    @FrameworkDsl
    @IgnoreForSerialize
    override fun getContentPath() = path.toChecked()

    @FrameworkDsl
    @IgnoreForSerialize
    override fun getContentTime() = time

    @FrameworkDsl
    @IgnoreForSerialize
    override fun getDescription() = desc.toChecked()

    @FrameworkDsl
    @IgnoreForSerialize
    override fun getContentType() = type.toLowerTrim()

    @FrameworkDsl
    @IgnoreForSerialize
    override fun getContentMime() = ContentMimeType(getContentType())

    @FrameworkDsl
    override fun hashCode() = getContentPath().hashOf()

    @FrameworkDsl
    override fun equals(other: Any?) = when (other) {
        is AbstractContentResourceBase -> this === other && getContentPath() == other.getContentPath()&& isContentCache() == other.isContentCache()
        else -> false
    }
}