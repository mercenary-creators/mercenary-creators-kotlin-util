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

class ClassPathContentResource @JvmOverloads constructor(path: String, type: String = DEFAULT_CONTENT_TYPE, internal val claz: Class<*>?, internal val load: ClassLoader?) : AbstractContentResource(path, type) {

    @JvmOverloads
    constructor(path: String, type: String = DEFAULT_CONTENT_TYPE) : this(getPathNormalizedNoTail(path, true), type, null, IO.getDefaultClassLoader())

    @JvmOverloads
    constructor(path: String, claz: Class<*>, type: String = DEFAULT_CONTENT_TYPE) : this(getPathNormalizedNoTail(path, false), type, claz, null)

    @JvmOverloads
    constructor(path: String, claz: kotlin.reflect.KClass<*>, type: String = DEFAULT_CONTENT_TYPE) : this(path, claz.java, type)

    private val resolved = getResolvedContentType()

    private val resource = IO.getResouce(getContentPath(), claz, load)

    override fun isContentThere(): Boolean {
        if (resource != null) {
            return IO.isContentThere(resource)
        }
        return false
    }

    override fun getContentTime(): Long {
        val time = super.getContentTime()
        if (time == 0L) {
            if (resource != null) {
                return IO.getContentTime(resource)
            }
            throw MercenaryExceptiion(getDescription())
        }
        return time
    }

    override fun getContentSize(): Long {
        if (resource != null) {
            return IO.getContentSize(resource) {
                super.getContentSize()
            }
        }
        throw MercenaryExceptiion(getDescription())
    }

    override fun getContentType() = resolved

    override fun getInputStream() = when (val data = IO.getInputStream(resource)) {
        null -> throw MercenaryExceptiion(getDescription())
        else -> data
    }

    override fun toString() = getDescription()

    override fun equals(other: Any?) = when (other) {
        is ClassPathContentResource -> {
            getContentPath() == other.getContentPath() && ((load != null) && (load == other.load)) && ((claz != null) && (claz == other.claz))
        }
        else -> false
    }

    override fun hashCode() = getContentPath().hashCode()
}