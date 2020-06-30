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
class ByteArrayContentResource @JvmOverloads @CreatorsDsl constructor(data: ByteArray, path: String, type: String = DEFAULT_CONTENT_TYPE, time: Long = getTimeStamp(), private val make: ContentResourceLookup? = null, private val kind: String = IO.PREFIX_BYTES) : AbstractCachedContentResource(data, path, type, time) {

    @CreatorsDsl
    @IgnoreForSerialize
    override fun getContentLook(): ContentResourceLookup {
        return { make?.invoke(it)?.toContentCache() ?: this }
    }

    @CreatorsDsl
    @IgnoreForSerialize
    override fun getContentKind() = kind

    override fun toString() = getDescription()

    @CreatorsDsl
    override fun equals(other: Any?) = when (other) {
        is ByteArrayContentResource -> this === other || getContentPath() isSameAs other.getContentPath() && isDataSameAs(other)
        else -> false
    }

    override fun hashCode() = toDataHashOf()
}