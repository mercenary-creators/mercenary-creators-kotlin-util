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

package co.mercenary.creators.kotlin.util.test.security

import co.mercenary.creators.kotlin.util.*
import org.junit.jupiter.api.Test

class EncodersTest : KotlinSecurityTest() {
    @Test
    fun test() {
        val name = getGeneratedText()
        val code = Encoders.text(Encoders.hex())
        info { name }
        val data = timed {
            code.encode(name)
        }
        info { data }
        val text = timed {
            code.decode(data)
        }
        info { text }
        name shouldBe text
        val temp = timed {
            code.encode(text)
        }
        info { temp }
        data shouldBe temp
        val last = timed {
            code.encode(text)
        }
        info { last }
        last shouldBe temp
        info { Ciphers.getAlgorithms() }
        warn { Digests.getAlgorithms() }
        info { Randoms.getAlgorithms() }
        Randoms.getDoubleSequence(10, 5.0, 10.0).forEach {
            info { it.toDecimalPlacesString() }
        }
        warn { Randoms.randomOf().algorithm }
        warn { Randoms.randomOf().provider.name }
        warn { Randoms.strongOf().algorithm }
        warn { Randoms.strongOf().provider.name }
        val buff = "DEAN".toCharArray()
        val rand = Randoms.randomOf()
        val list = (buff.indices)
        info { list }
        info { list.shuffled(rand) }
        info { list.shuffled(rand).shuffled(rand) }
        info { list }
        warn { dash() }
        val dean = "DEAN JONES".toSecureString()
        info { dean }
        info { dean.hashCode() }
        info { dean.toString() }
        info { dean.toCharArray() }
        info { dean.toCharArray(false) }
        val look = 4.toIntArray()
        warn { dash() }
        warn { look }
        warn { look.asIterable().shuffled() }
    }
}