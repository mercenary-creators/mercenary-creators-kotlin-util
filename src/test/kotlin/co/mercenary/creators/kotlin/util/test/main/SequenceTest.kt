/*
 * Copyright (c) 2022, Mercenary Creators Company. All rights reserved.
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
        list.sizeOf() shouldBe size
        val ints = sequenceOf(1..16).toList()
        ints.forEach {
            info { it }
        }
        ints.sizeOf() shouldBe 16
        dashes()
        val maps = LRUCacheMap<String, String>(4)
        maps["author"] = author
        info { maps.sizeOf() }
        dashes()
        16 forEach {
            info { maps }
            val temp = "%04d".format(it)
            debug { temp }
            maps[temp] = temp
            info { maps.sizeOf() }
            dashes()
        }
        maps.computeIfAbsent("author") { name ->
            warn { name.center(16) }
            author
        }
        info { maps.sizeOf() }
        info { maps }
        dashes()
        val prim = sequenceOf(2) {
            if (it < (1 shl 30)) it * 2 else null
        }.getIntArray()
        warn { prim }
        warn { prim.sizeOf() }
        error { dash(100) }
        info { 1 shl 15 }
        info { 1 shl 16 }
        info { 1 shl 20 }
        info { 1 shl 30 }
        dashes()
        val more = sequenceOf(2L) {
            if (it < (1L shl 62)) it * 2L else null
        }.getLongArray()
        error { dash(100) }
        warn { more }
        warn { more.sizeOf() }
        error { dash(100) }
        info { 1L shl 60 }
        info { 1L shl 61 }
        info { 1L shl 62 }
        info { 1L shl 63 }
        info { Long.MAX_VALUE }
        error { dash(100) }
        info { 5 distTo 10 }
        info { 10 distTo 5 }
        error { dash(100) }
        info { 5 diffOf 10 }
        info { 10 diffOf 5 }
        info { dash(100) }
        info { 1L shl 60 }
        info { 1L shl 61 }
        info { 1L shl 62 }
        info { 1L shl 63 }
        info { Long.MAX_VALUE }
    }
}