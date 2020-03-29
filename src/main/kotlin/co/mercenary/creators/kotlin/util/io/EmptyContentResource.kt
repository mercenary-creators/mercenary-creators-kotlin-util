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
object EmptyContentResource : CachedContentResource {

    private val path: String by lazy {
        IO.PREFIX_FILES.plus("/dev/null").toLowerTrim()
    }

    private val desc: String by lazy {
        "${javaClass.name}(${getContentPath()}, ${getContentType()}, ${isContentCache()})"
    }

    override fun toMapNames(): Map<String, Any?> {
        return mapOf("name" to javaClass.name, "path" to getContentPath(), "type" to getContentType(), "time" to getContentTime().toDate())
    }

    override fun toString() = desc

    @IgnoreForSerialize
    override fun getContentSize() = 0L

    @IgnoreForSerialize
    override fun getContentTime() = 0L

    @IgnoreForSerialize
    override fun getContentPath() = path

    @IgnoreForSerialize
    override fun getDescription() = desc

    @AssumptionDsl
    @IgnoreForSerialize
    override fun isContentCache() = true

    @AssumptionDsl
    @IgnoreForSerialize
    override fun isContentThere() = false

    @IgnoreForSerialize
    override fun getContentData() = ByteArray(0)

    @IgnoreForSerialize
    override fun getInputStream() = EmptyInputStream

    @IgnoreForSerialize
    override fun toContentCache() = EmptyContentResource

    @IgnoreForSerialize
    override fun getContentType() = DEFAULT_CONTENT_TYPE

    @IgnoreForSerialize
    override fun getContentLook(): ContentResourceLookup = { _ -> EmptyContentResource }
}