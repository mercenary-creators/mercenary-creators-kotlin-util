/*
 * Copyright (c) 2019, Mercenary Creators Company. All rights reserved.
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

abstract class AbstractContentResource @JvmOverloads constructor(path: String, type: String = DEFAULT_CONTENT_TYPE, time: Long = 0L) : AbstractContentResourceBase(path, type, time) {

    private val cache: CachedContentResource by lazy {
        ByteArrayContentResource(getContentData(), getContentPath(), getContentType(), getContentTime())
    }

    protected fun getResolvedContentType(): String {
        val keep = super.getContentType()
        if (isDefaultContentType(keep)) {
            val path = getContentPath()
            val look = getDefaultContentTypeProbe().getContentType(path)
            if (isDefaultContentType(look)) {
                return toCommonContentTypes(path)
            }
            return look
        }
        return keep.toLowerTrim()
    }

    override fun isContentCache() = false
    override fun toContentCache() = cache
    override fun getContentData() = getInputStream().toByteArray()
    override fun getContentSize() = getInputStream().use { it.copyTo(EmptyOutputStream) }
}