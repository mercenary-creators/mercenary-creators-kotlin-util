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
class ClassPathContentResource @JvmOverloads @CreatorsDsl constructor(path: String, type: String = DEFAULT_CONTENT_TYPE, private val claz: Class<*>?, private val load: ClassLoader?) : AbstractContentResource(path, type) {

    @CreatorsDsl
    @JvmOverloads
    constructor(path: String, type: String = DEFAULT_CONTENT_TYPE) : this(getPathNormalizedNoTail(path, true), type, null, IO.getDefaultClassLoader())

    @CreatorsDsl
    @JvmOverloads
    constructor(path: String, claz: Class<*>, type: String = DEFAULT_CONTENT_TYPE) : this(getPathNormalizedNoTail(path, false), type, claz, null)

    @CreatorsDsl
    @JvmOverloads
    constructor(path: String, claz: kotlin.reflect.KClass<*>, type: String = DEFAULT_CONTENT_TYPE) : this(path, claz.java, type)

    private val resolved = resolved()

    private val resource = IO.getResouce(getContentPath(), claz, load)

    @CreatorsDsl
    @IgnoreForSerialize
    override fun isContentThere(): Boolean {
        if (resource != null) {
            return IO.isContentThere(resource)
        }
        return false
    }

    @CreatorsDsl
    @IgnoreForSerialize
    override fun getContentLook(): ContentResourceLookup = { ClassPathContentResource(IO.getPathRelative(getContentPath(), it), DEFAULT_CONTENT_TYPE, claz, load) }

    @CreatorsDsl
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

    @CreatorsDsl
    @IgnoreForSerialize
    override fun getContentSize(): Long {
        if (resource != null) {
            return IO.getContentSize(resource) {
                super.getContentSize()
            }
        }
        throw MercenaryExceptiion(getDescription())
    }

    @CreatorsDsl
    @IgnoreForSerialize
    override fun getContentType() = resolved

    @CreatorsDsl
    @IgnoreForSerialize
    override fun getContentKind() = IO.PREFIX_CLASS

    @CreatorsDsl
    @IgnoreForSerialize
    override fun getInputStream() = when (val data = IO.getInputStream(resource)) {
        null -> throw MercenaryExceptiion(getDescription())
        else -> data
    }

    @CreatorsDsl
    override fun equals(other: Any?) = when (other) {
        is ClassPathContentResource -> this === other || getContentPath() isSameAs other.getContentPath() && claz isSameAs other.claz && load isSameAs other.load
        else -> false
    }

    @CreatorsDsl
    override fun hashCode() = getContentPath().hashCode()
}