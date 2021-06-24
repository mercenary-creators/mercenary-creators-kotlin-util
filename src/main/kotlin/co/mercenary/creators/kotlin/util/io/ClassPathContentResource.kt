/*
 * Copyright (c) 2021, Mercenary Creators Company. All rights reserved.
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
class ClassPathContentResource @JvmOverloads @FrameworkDsl constructor(path: String, type: String = DEFAULT_CONTENT_TYPE, private val claz: Class<*>?, private val load: ClassLoader?) : AbstractContentResource(path, type) {

    @FrameworkDsl
    @JvmOverloads
    constructor(path: String, type: String = DEFAULT_CONTENT_TYPE) : this(getPathNormalizedNoTail(path, true), type, null, IO.getDefaultClassLoader())

    @FrameworkDsl
    @JvmOverloads
    constructor(path: String, claz: Class<*>, type: String = DEFAULT_CONTENT_TYPE) : this(getPathNormalizedNoTail(path, false), type, claz, null)

    @FrameworkDsl
    @JvmOverloads
    constructor(path: String, claz: kotlin.reflect.KClass<*>, type: String = DEFAULT_CONTENT_TYPE) : this(path, claz.java, type)

    private val resolved = resolved()

    private val resource = IO.getResouce(getContentPath(), claz, load)

    @FrameworkDsl
    @IgnoreForSerialize
    override fun isContentThere(): Boolean {
        if (resource != null) {
            return IO.isContentThere(resource)
        }
        return false
    }

    @FrameworkDsl
    @IgnoreForSerialize
    override fun getContentLook(): ContentResourceLookup = { ClassPathContentResource(IO.getPathRelative(getContentPath(), it), DEFAULT_CONTENT_TYPE, claz, load) }

    @FrameworkDsl
    @IgnoreForSerialize
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

    @FrameworkDsl
    @IgnoreForSerialize
    override fun getContentSize(): Long {
        if (resource != null) {
            return IO.getContentSize(resource) {
                super.getContentSize()
            }
        }
        throw MercenaryExceptiion(getDescription())
    }

    @FrameworkDsl
    @IgnoreForSerialize
    override fun getContentType() = resolved

    @FrameworkDsl
    @IgnoreForSerialize
    override fun getContentKind() = IO.PREFIX_CLASS

    @FrameworkDsl
    @IgnoreForSerialize
    override fun getInputStream() = when (val data = IO.getInputStream(resource)) {
        null -> throw MercenaryExceptiion(getDescription())
        else -> data
    }

    @FrameworkDsl
    override fun equals(other: Any?) = when (other) {
        is ClassPathContentResource -> this === other || getContentPath() isSameAs other.getContentPath() && claz isSameAs other.claz && load isSameAs other.load
        else -> false
    }

    @FrameworkDsl
    override fun hashCode() = getContentPath().hashCode()
}