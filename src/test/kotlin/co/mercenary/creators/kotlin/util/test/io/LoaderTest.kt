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
import org.junit.jupiter.api.Test

class LoaderTest : KotlinDataTest() {
    @Test
    fun test() {
        info { getContentResourceDetails(RESOURCE_LOADER["test.zip"]) }
        info { getContentResourceDetails(RESOURCE_LOADER["test.jpg"]) }
        info { getContentResourceDetails(RESOURCE_LOADER["test.css"]) }
        info { getContentResourceDetails(RESOURCE_LOADER["test.doc"]) }
        info { getContentResourceDetails(RESOURCE_LOADER["http://jsonplaceholder.typicode.com/posts"]) }
        val path = getTempFileNamedPath(uuid(), ".json")
        info { getContentResourceDetails(RESOURCE_LOADER[path]) }
        val data = getContentResourceByteURL(RESOURCE_LOADER["test.jpg"])
        info { data }
        info { getContentResourceDetails(RESOURCE_LOADER[data]) }
        val temp = getTempFileNamedPath(uuid(), ".yaml")
        info { getContentResourceDetails(RESOURCE_LOADER[temp]) }
    }
}