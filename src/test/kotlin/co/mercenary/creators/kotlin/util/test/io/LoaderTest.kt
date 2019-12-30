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

package co.mercenary.creators.kotlin.util.test.io

import co.mercenary.creators.kotlin.util.*
import co.mercenary.creators.kotlin.util.io.*
import org.junit.jupiter.api.Test

class LoaderTest : KotlinDataTest() {
    @Test
    fun test() {
        info { getContentResourceDetails(contentResourceLoader["test.zip"]) }
        info { getContentResourceDetails(contentResourceLoader["test.jpg"]) }
        info { getContentResourceDetails(contentResourceLoader["test.css"]) }
        info { getContentResourceDetails(contentResourceLoader["test.doc"]) }
        info { getContentResourceDetails(contentResourceLoader["test.htm"]) }
        warn { getContentResourceDetails(contentResourceLoader["test.htm"].toRelativePath("../test.txt")) }
        info { getContentResourceDetails(contentResourceLoader["http://jsonplaceholder.typicode.com/posts"]) }
        val path = getTempFileNamedPath(uuid(), ".json")
        info { getContentResourceDetails(contentResourceLoader[path]) }
        val temp = getTempFileNamedPath(uuid(), ".yaml")
        info { getContentResourceDetails(contentResourceLoader[temp]) }
        val look = object : ContentProtocolResolver {
            override fun resolve(path: String, load: ContentResourceLoader): ContentResource? {
                if (load.isContentCache()) {
                    warn { path }
                }
                return null
            }
        }
        info { cachedContentResourceLoader.contains(look) }
        cachedContentResourceLoader += look
        info { cachedContentResourceLoader.contains(look) }
        info { getContentResourceDetails(cachedContentResourceLoader["test.htm"]) }
        val dara = EmptyContentResource
        info { getContentResourceDetails(EmptyContentResource) }
        info { dara == EmptyContentResource }
        info { getContentResourceDetails(contentResourceLoader["http://jsonplaceholder.typicode.com/posts"].toRelativePath("../todos")) }
        info { getContentResourceDetails(contentResourceLoader["http://jsonplaceholder.typicode.com/todos"]["../posts"]) }
    }
}