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

interface ContentResourceLoader {
    fun getClassLoader(): ClassLoader?
    fun getContentResource(path: String): ContentResource
    operator fun get(path: String) = getContentResource(path)
    operator fun plusAssign(args: ContentProtocolResolver)

    operator fun plusAssign(args: Array<ContentProtocolResolver>) {
        args.forEach { plusAssign(it) }
    }

    operator fun plusAssign(args: Iterable<ContentProtocolResolver>) {
        args.forEach { plusAssign(it) }
    }

    operator fun plusAssign(args: Sequence<ContentProtocolResolver>) {
        args.forEach { plusAssign(it) }
    }
}