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

open class BaseContentResourceLoader @JvmOverloads constructor(private val loader: ClassLoader? = null, private val parent: ContentResourceLoader? = null) : ContentResourceLoader {

    private val list = ArrayList<ContentProtocolResolver>()

    override operator fun get(path: String): ContentResource {
        if (list.isNotEmpty()) {
            list.forEach { resolver ->
                val data = resolver.resolve(path, this)
                if (data != null) {
                    return data
                }
            }
        }
        if (path.startsWith(IO.SINGLE_SLASH)) {
            return getContentResourceByPath(IO.getPathNormalized(path).orEmpty())
        }
        if (path.startsWith(IO.PREFIX_CLASS)) {
            return ClassPathContentResource(path.removePrefix(IO.PREFIX_CLASS), DEFAULT_CONTENT_TYPE, null, getClassLoader())
        }
        if (path.contains(IO.PREFIX_COLON)) {
            try {
                val data = path.toURL()
                if (data.isFileURL()) {
                    val file = data.toFileOrNull(true)
                    if (file != null) {
                        return file.toContentResource()
                    }
                }
                return data.toContentResource()
            }
            catch (cause: Throwable) {
                Throwables.thrown(cause)
            }
        }
        return getContentResourceByPath(path)
    }

    override fun getClassLoader() = loader

    override fun getParentMaybe() = parent

    protected open fun getContentResourceByPath(path: String): ContentResource {
        if (path.startsWith(IO.SINGLE_SLASH)) {
            try {
                val file = path.toFileURL().toFileOrNull()
                if (file != null) {
                    return file.toContentResource()
                }
            }
            catch (cause: Throwable) {
                Throwables.thrown(cause)
            }
        }
        return ClassPathContentResource(path, DEFAULT_CONTENT_TYPE, null, getClassLoader())
    }

    override operator fun plusAssign(args: ContentProtocolResolver) {
        synchronized(list) {
            if (!contains(args)) {
                list += args
            }
        }
    }

    override operator fun minusAssign(args: ContentProtocolResolver) {
        synchronized(list) {
            if (contains(args)) {
                list -= args
            }
        }
    }

    override operator fun contains(args: ContentProtocolResolver): Boolean {
        if (list.contains(args)) {
            return true
        }
        list.forEach {
            if (it === args) {
                return true
            }
        }
        return false
    }

    companion object {
        val INSTANCE: ContentResourceLoader by lazy {
            BaseContentResourceLoader()
        }
    }
}