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

package co.mercenary.creators.kotlin.util.test.io

import co.mercenary.creators.kotlin.util.*
import co.mercenary.creators.kotlin.util.io.*
import org.junit.jupiter.api.Test

class LoaderTest : KotlinDataTest() {
    @Test
    fun test() {
        info { getContentResourceDetails(CONTENT_RESOURCE_LOADER["test.zip"]) }
        info { getContentResourceDetails(CONTENT_RESOURCE_LOADER["test.jpg"]) }
        info { getContentResourceDetails(CONTENT_RESOURCE_LOADER["test.css"]) }
        info { getContentResourceDetails(CONTENT_RESOURCE_LOADER["test.doc"]) }
        info { getContentResourceDetails(CONTENT_RESOURCE_LOADER["test.htm"]) }
        warn { getContentResourceDetails(CONTENT_RESOURCE_LOADER["test.htm"].toRelativePath("../test.txt")) }
        info { getContentResourceDetails(CONTENT_RESOURCE_LOADER["http://jsonplaceholder.typicode.com/posts"]) }
        val path = getTempFileNamedPath(uuid(), ".json")
        info { getContentResourceDetails(CONTENT_RESOURCE_LOADER[path]) }
        val temp = getTempFileNamedPath(uuid(), ".yaml")
        info { getContentResourceDetails(CONTENT_RESOURCE_LOADER[temp]) }
        val look = object : ContentProtocolResolver {
            override fun resolve(path: String, load: ContentResourceLoader): ContentResource? {
                if (load.isContentCache()) {
                    warn { path }
                }
                return null
            }
        }
        info { CACHED_CONTENT_RESOURCE_LOADER.contains(look) }
        CACHED_CONTENT_RESOURCE_LOADER += look
        info { CACHED_CONTENT_RESOURCE_LOADER.contains(look) }
        val dara = EmptyContentResource
        info { getContentResourceDetails(EmptyContentResource) }
        info { dara == EmptyContentResource }
        info { getContentResourceDetails(CONTENT_RESOURCE_LOADER["http://jsonplaceholder.typicode.com/posts"].toRelativePath("../todos")) }
        info { getContentResourceDetails(CONTENT_RESOURCE_LOADER["http://jsonplaceholder.typicode.com/todos"]["../posts"]) }
        info { dash() }
        info { getContentResourceDetails(CACHED_CONTENT_RESOURCE_LOADER["test.htm"]) }
        info { getContentResourceDetails(CACHED_CONTENT_RESOURCE_LOADER["test.htm"].toRelativePath("../test.txt")) }
        info { getContentResourceDetails(CACHED_CONTENT_RESOURCE_LOADER["test.txt"].toRelativePath("../test.pdf")) }
        info { CACHED_CONTENT_RESOURCE_LOADER.keys }
        CACHED_CONTENT_RESOURCE_LOADER.keys.clear()
        info { CACHED_CONTENT_RESOURCE_LOADER.keys }
        info { getContentResourceDetails(CACHED_CONTENT_RESOURCE_LOADER["test.pdf"].toRelativePath("../test.doc")) }
        info { CACHED_CONTENT_RESOURCE_LOADER.keys }
    }
}