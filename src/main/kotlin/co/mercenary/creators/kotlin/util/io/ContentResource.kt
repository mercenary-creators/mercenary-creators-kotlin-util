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

interface ContentResource : InputStreamSupplier, HasMapNames {

    @CreatorsDsl
    @IgnoreForSerialize
    fun getContentSize(): Long

    @CreatorsDsl
    @IgnoreForSerialize
    fun getContentTime(): Long

    @CreatorsDsl
    @IgnoreForSerialize
    fun getContentPath(): String

    @CreatorsDsl
    @IgnoreForSerialize
    fun getContentType(): String

    @CreatorsDsl
    @IgnoreForSerialize
    fun getDescription(): String

    @CreatorsDsl
    @IgnoreForSerialize
    fun getContentKind(): String

    @CreatorsDsl
    @IgnoreForSerialize
    fun isContentThere(): Boolean

    @CreatorsDsl
    @IgnoreForSerialize
    fun isContentCache(): Boolean

    @CreatorsDsl
    @IgnoreForSerialize
    fun getContentData(): ByteArray

    @CreatorsDsl
    @IgnoreForSerialize
    fun toContentCache(): CachedContentResource

    @CreatorsDsl
    @IgnoreForSerialize
    fun getContentLook(): ContentResourceLookup

    @CreatorsDsl
    @IgnoreForSerialize
    fun getContentMime(): ContentMimeType

    @CreatorsDsl
    @IgnoreForSerialize
    fun toRelativePath(path: String): ContentResource = getContentLook().invoke(path)

    @CreatorsDsl
    operator fun get(path: CharSequence): ContentResource = toRelativePath(path.toString())
}