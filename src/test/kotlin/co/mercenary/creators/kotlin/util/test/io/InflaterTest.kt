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
import org.junit.jupiter.api.Test

class InflaterTest : KotlinDataTest() {
    @Test
    fun test() {
        val data = loader["test.doc"]
        info { data.getContentSize() }
        val make = Inflaters.gzip().deflate(data)
        info { make.size }
        val back = Inflaters.gzip().inflate(make)
        info { back.size }
        info { Inflaters.gzip().deflate(data, EmptyOutputStream) }
        val file = getTempFileNamed()
        info { Inflaters.gzip().deflate(data, file) }
        info { Inflaters.gzip().inflate(file, EmptyOutputStream) }
        val list = file("/dev/random").data(ByteArray(DEFAULT_BUFFER_SIZE))
        warn { list.toByteArray() }
    }
}