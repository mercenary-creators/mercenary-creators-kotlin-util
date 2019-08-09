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

open class DefaultContentResourceLoader(private val loader: ClassLoader? = null) : ContentResourceLoader {

    private val resolvers = mutableSetOf<ContentProtocolResolver>()

    override fun getContentResource(path: String): ContentResource {
        if (resolvers.isNotEmpty()) {
            for (resolver in resolvers) {
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
            Throwables.assert(cause)
        }
        return getContentResourceByPath(path)
    }

    override fun getClassLoader(): ClassLoader? {
        return loader ?: IO.getDefaultClassLoader()
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
                Throwables.assert(cause)
            }
        }
        return ClassPathContentResource(path, getDefaultContentTypeProbe().getContentType(path), null, getClassLoader())
    }

    open fun add(args: ContentProtocolResolver) {
        resolvers += args
    }

    open fun add(args: Array<ContentProtocolResolver>) {
        resolvers += args
    }

    open fun add(args: Iterable<ContentProtocolResolver>) {
        resolvers += args
    }

    open fun add(args: Sequence<ContentProtocolResolver>) {
        resolvers += args
    }

    override operator fun plusAssign(args: ContentProtocolResolver) {
        add(args)
    }
}