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

interface ContentResource : InputStreamSupplier, HasMapNames, HasContentSize {

    @FrameworkDsl
    @IgnoreForSerialize
    fun getContentTime(): Long

    @FrameworkDsl
    @IgnoreForSerialize
    fun getContentPath(): String

    @FrameworkDsl
    @IgnoreForSerialize
    fun getContentType(): String

    @FrameworkDsl
    @IgnoreForSerialize
    fun getDescription(): String

    @FrameworkDsl
    @IgnoreForSerialize
    fun getContentKind(): String

    @FrameworkDsl
    @IgnoreForSerialize
    fun isContentThere(): Boolean

    @FrameworkDsl
    @IgnoreForSerialize
    fun isContentCache(): Boolean

    @FrameworkDsl
    @IgnoreForSerialize
    fun getContentData(): ByteArray

    @FrameworkDsl
    fun toContentCache(): CachedContentResource

    @FrameworkDsl
    @IgnoreForSerialize
    fun getContentLook(): ContentResourceLookup

    @FrameworkDsl
    @IgnoreForSerialize
    fun getContentMime(): ContentMimeType

    @FrameworkDsl
    fun toRelativePath(path: String): ContentResource = getContentLook().convert(path)

    @FrameworkDsl
    operator fun get(path: CharSequence): ContentResource = toRelativePath(path.toString())
}