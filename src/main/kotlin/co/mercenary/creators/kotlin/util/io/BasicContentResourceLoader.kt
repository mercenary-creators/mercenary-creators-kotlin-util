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

open class BasicContentResourceLoader @JvmOverloads constructor(private val load: ClassLoader? = null) : ContentResourceLoader {

    private val list = arrayListOf<ContentProtocolResolver>()

    override operator fun get(path: String): ContentResource {
        if (list.isNotEmpty()) {
            list.forEach {
                val data = it.resolve(path, this)
                if (data != null) {
                    return data
                }
            }
        }
        if (path.startsWith(IO.SINGLE_SLASH)) {
            return getContentResourceByPath(IO.getPathNormalized(path).orEmpty())
        }
        if (path.startsWith(IO.PREFIX_CLASS)) {
            val name = path.removePrefix(IO.PREFIX_CLASS)
            return ClassPathContentResource(name, getDefaultContentTypeProbe().getContentType(name), null, getClassLoader())
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

    override fun getClassLoader(): ClassLoader? {
        return load ?: IO.getDefaultClassLoader()
    }

    protected open fun getContentResourceByPath(path: String): ContentResource {
        if (path.startsWith(IO.SINGLE_SLASH)) {
            try {
                val file = IO.toFileOrNull(IO.PREFIX_FILES.plus(path).toURL())
                if (file != null) {
                    return file.toContentResource()
                }
            }
            catch (cause: Throwable) {
                Throwables.thrown(cause)
            }
        }
        return ClassPathContentResource(path, IO.getContentTypeProbe().getContentType(path), null, getClassLoader())
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
        val INSTANCE = BasicContentResourceLoader()
    }
}