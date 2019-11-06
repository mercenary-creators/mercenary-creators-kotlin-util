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
import java.net.URL

open class BasicContentResourceLoader @JvmOverloads constructor(private val load: ClassLoader? = null) : ContentResourceLoader {

    private val maps = mutableSetOf<ContentProtocolResolver>()

    override val size: Int
        get() = maps.size

    override fun clear() {
        maps.clear()
    }

    override operator fun get(path: String): ContentResource {
        if (maps.isNotEmpty()) {
            maps.forEach {
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
        if (path.startsWith(IO.PREFIX_BYTES)) {
            val list = path.removePrefix(IO.PREFIX_BYTES).split(IO.COL_SEPARATOR_CHAR)
            if (list.size >= 3) {
                val name = list[0]
                val type = list[1]
                val data = list[2]
                return ByteArrayContentResource(Encoders.hex().decode(data), name, type)
            }
        }
        if (path.contains(IO.PREFIX_COLON)) {
            try {
                val data = URL(path)
                if (isFileURL(data)) {
                    val file = IO.toFileOrNull(data)
                    if (file != null) {
                        return FileContentResource(file)
                    }
                }
                return URLContentResource(data)
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
                val file = IO.toFileOrNull(URL(IO.PREFIX_FILES + path))
                if (file != null) {
                    return FileContentResource(file)
                }
            }
            catch (cause: Throwable) {
                Throwables.thrown(cause)
            }
        }
        return ClassPathContentResource(path, getDefaultContentTypeProbe().getContentType(path), null, getClassLoader())
    }

    open fun add(args: ContentProtocolResolver) {
        maps += args
    }

    open fun add(args: Array<ContentProtocolResolver>) {
        maps += args
    }

    open fun add(args: Iterable<ContentProtocolResolver>) {
        maps += args
    }

    open fun add(args: Sequence<ContentProtocolResolver>) {
        maps += args
    }

    override operator fun plusAssign(args: ContentProtocolResolver) {
        add(args)
    }

    override operator fun plusAssign(args: Array<ContentProtocolResolver>) {
        add(args)
    }

    override operator fun plusAssign(args: Iterable<ContentProtocolResolver>) {
        add(args)
    }

    override operator fun plusAssign(args: Sequence<ContentProtocolResolver>) {
        add(args)
    }

    companion object {
        val INSTANCE = BasicContentResourceLoader()
    }
}