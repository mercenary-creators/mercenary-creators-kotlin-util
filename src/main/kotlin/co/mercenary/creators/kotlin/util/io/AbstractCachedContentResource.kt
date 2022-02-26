/*
 * Copyright (c) 2022, Mercenary Creators Company. All rights reserved.
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
abstract class AbstractCachedContentResource @JvmOverloads constructor(data: ByteArray, path: String, type: String = DEFAULT_CONTENT_TYPE, time: Long = getTimeStamp(), sort: String = EMPTY_STRING) : AbstractContentResourceBase(path, type, time), CachedContentResource {

    @FrameworkDsl
    private val kind = sort.copyOf()

    @FrameworkDsl
    private val save = data.toByteArray()

    @FrameworkDsl
    @IgnoreForSerialize
    override fun getContentKind() = kind.copyOf()

    @FrameworkDsl
    @IgnoreForSerialize
    override fun getContentData() = save.toByteArray()

    @FrameworkDsl
    @IgnoreForSerialize
    override fun getInputStream() = save.toInputStream()

    @FrameworkDsl
    @IgnoreForSerialize
    override fun getContentSize() = save.toContentSize()

    @FrameworkDsl
    @IgnoreForSerialize
    override fun isContentThere() = true

    @FrameworkDsl
    @IgnoreForSerialize
    override fun isContentCache() = true

    @FrameworkDsl
    override fun toContentCache() = this

    @FrameworkDsl
    override fun hashCode() = save.hashOf() * HASH_NEXT_VALUE + super.hashCode()

    @FrameworkDsl
    override fun equals(other: Any?) = when (other) {
        is AbstractCachedContentResource -> this === other || getContentPath() == other.getContentPath() && save isSameArrayAs other.save
        else -> false
    }
}