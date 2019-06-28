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
import co.mercenary.creators.kotlin.util.io.IO
import org.junit.jupiter.api.Test

class LoaderTest : KotlinDataTest() {
    @Test
    fun text() {
        val load = DefaultContentResourceLoader()
        info { getContentResourceDetails(load.getContentResource("test.zip")) }
        info { getContentResourceDetails(load.getContentResource("test.jpg")) }
        info { getContentResourceDetails(load.getContentResource("test.css")) }
        info { getContentResourceDetails(load.getContentResource("test.doc")) }
        info { getContentResourceDetails(load.getContentResource("http://jsonplaceholder.typicode.com/posts")) }
        val path = getTempFileNamedPath(uuid(), ".json")
        info { getContentResourceDetails(load.getContentResource(path)) }
        info { getContentResourceDetails(load.getContentResource(IO.PREFIX_FILES + path)) }
        val data = getContentResourceByteURL(load.getContentResource("test.jpg"))
        info { data }
        info { getContentResourceDetails(load.getContentResource(data)) }
    }
}