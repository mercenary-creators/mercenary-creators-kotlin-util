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
        info { loader["test.zip"] }
        info { loader["test.jpg"] }
        info { loader["test.css"] }
        info { loader["test.doc"] }
        info { loader["test.htm"] }
        info { loader["test.htm"].toRelativePath("../test.txt") }
        warn { loader["http://jsonplaceholder.typicode.com/posts"] }
        val path = getTempFileNamedPath(uuid(), ".json")
        info { loader[path] }
        val temp = getTempFileNamedPath(uuid(), ".yaml")
        info { loader[temp] }
        val look = object : ContentProtocolResolver {
            override fun resolve(path: String, load: ContentResourceLoader): ContentResource? {
                if (load.isContentCache()) {
                    warn { path }
                }
                return null
            }
        }
        info { look in cached }
        false shouldBe (look in cached)
        cached += look
        info { look in cached }
        true shouldBe (look in cached)
        val dara = EmptyContentResource
        info { EmptyContentResource }
        info { dara == EmptyContentResource }
        info { dara isSameAs EmptyContentResource }
        dara shouldBe EmptyContentResource
        info { loader["http://jsonplaceholder.typicode.com/posts"].toRelativePath("../todos") }
        info { loader["http://jsonplaceholder.typicode.com/todos"]["../posts"] }
        info { dash() }
        info { cached["test.htm"] }
        info { cached["test.htm"].toRelativePath("../test.txt") }
        info { cached["test.txt"].toRelativePath("../test.pdf") }
        info { cached.keys }
        cached.keys.size shouldNotBe 0
        cached.keys.clear()
        info { cached.keys }
        cached.keys.size shouldBe 0
        info { cached["test.pdf"].toRelativePath("../test.doc") }
        info { cached.keys }
        cached.keys.size shouldNotBe 0
    }
}