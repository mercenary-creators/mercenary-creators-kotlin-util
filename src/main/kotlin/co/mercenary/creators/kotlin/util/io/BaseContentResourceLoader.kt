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
open class BaseContentResourceLoader @JvmOverloads @FrameworkDsl constructor(private val name: String = EMPTY_STRING, private val loader: ClassLoader? = null) : ContentResourceLoader {

    @FrameworkDsl
    private val list = BasicArrayList<ContentProtocolResolver>()

    @FrameworkDsl
    override operator fun get(path: CharSequence): ContentResource {
        if (list.isNotEmpty()) {
            list.forEach { resolver ->
                val data = resolver.resolve(path, this)
                if (data != null) {
                    return data
                }
            }
        }
        if (path.startsWith(IO.SINGLE_SLASH)) {
            return this.getContentResourceByClassLoader(path.copyOf())
        }
        if (path.startsWith(IO.PREFIX_CLASS)) {
            return ClassPathContentResource(path.removePrefix(IO.PREFIX_CLASS).copyOf(), DEFAULT_CONTENT_TYPE, null, getClassLoader())
        }
        if (path.contains(IO.PREFIX_COLON)) {
            try {
                val data = path.linkOf()
                if (data.isFileURL()) {
                    val file = data.toFileOrNull(true)
                    if (file != null) {
                        return file.toContentResource()
                    }
                }
                return data.toContentResource()
            } catch (cause: Throwable) {
                Throwables.thrown(cause)
            }
        }
        return this.getContentResourceByClassLoader(path.copyOf())
    }

    @FrameworkDsl
    @IgnoreForSerialize
    override fun isContentCache() = false

    @FrameworkDsl
    @IgnoreForSerialize
    override fun getClassLoader() = loader

    @FrameworkDsl
    @IgnoreForSerialize
    override fun getLoadersName() = name.toTrimOr { nameOf() }

    @FrameworkDsl
    protected open fun getContentResourceByClassLoader(path: String): ContentResource {
        if (path.startsWith(IO.SINGLE_SLASH)) {
            try {
                val file = path.toFileURL().toFileOrNull()
                if (file != null) {
                    return file.toContentResource()
                }
            } catch (cause: Throwable) {
                Throwables.thrown(cause)
            }
        }
        val look = ClassPathContentResource(path, DEFAULT_CONTENT_TYPE, null, getClassLoader())
        if (look.isContentThere()) {
            return look
        }
        return EmptyContentResource
    }

    @FrameworkDsl
    override operator fun plusAssign(args: ContentProtocolResolver) {
        synchronized(list) {
            if (!contains(args)) {
                list += args
            }
        }
    }

    @FrameworkDsl
    override operator fun minusAssign(args: ContentProtocolResolver) {
        synchronized(list) {
            if (contains(args)) {
                list -= args
            }
        }
    }

    @FrameworkDsl
    override operator fun contains(args: ContentProtocolResolver): Boolean {
        if (list.isNotEmpty()) {
            if (list.contains(args)) {
                return true
            }
            list.forEach {
                if (it === args) {
                    return true
                }
            }
        }
        return false
    }

    @FrameworkDsl
    override fun toMapNames() = dictOf("name" to getLoadersName(), "cached" to isContentCache())

    @FrameworkDsl
    override fun toString() = toMapNames().toSafeString()

    @FrameworkDsl
    override fun hashCode() = toMapNames().hashOf()

    @FrameworkDsl
    override fun equals(other: Any?) = when (other) {
        is BaseContentResourceLoader -> this === other || toMapNames() isSameAs other.toMapNames()
        else -> false
    }

    companion object {

        @FrameworkDsl
        val INSTANCE: ContentResourceLoader by lazy {
            BaseContentResourceLoader()
        }
    }
}