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

package co.mercenary.creators.kotlin.util.test.main

import co.mercenary.creators.kotlin.util.*
import org.junit.jupiter.api.Test

class SequenceTest : KotlinDataTest() {
    @Test
    fun test() {
        val size = 10
        val data = sequenceOf(1) {
            if (it < size) it.inc() else null
        }
        data.forEach {
            info { it }
        }
        val list = sequenceOf(1) {
            if (it < size) it.inc() else null
        }.toList()
        list.forEach {
            info { it }
        }
        list.size shouldBe size
        val ints = sequenceOf(1..16).toList()
        ints.forEach {
            info { it }
        }
        ints.size shouldBe 16
        dashes()
        val maps = LRUCacheMap<String, String>(4)
        maps["author"] = author
        info { maps.size }
        dashes()
        16 forEach {
            info { maps }
            val temp = "%04d".format(it)
            error { temp }
            maps[temp] = temp
            info { maps.size }
            dashes()
        }
        maps.computeIfAbsent("author") { name ->
            warn { name.center(16) }
            author
        }
        info { maps.size }
        info { maps }
        dashes()
    }
}