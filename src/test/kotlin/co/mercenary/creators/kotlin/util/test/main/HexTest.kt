/*
 * Copyright (c) 2023, Mercenary Creators Company. All rights reserved.
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

class HexTest : KotlinTest() {

    @Test
    fun test() {
        val buff = author
        val bits = buff.getContentData()
        val hexa = Encoders.hex().encode(bits)
        val back = Encoders.hex().decode(hexa)
        dashes()
        error { buff }
        dashes()
        error { hexa }
        dashes()
        error { back isSameAs bits }
        dashes()
        warn { kindOf<Int>().descOf() }
        dashes()
        warn { kindOf<List<DoubleArray>>().descOf() }
        dashes()
        warn { kindOf<Sequence<Array<DoubleArray>>>().descOf() }
        dashes()
    }
}