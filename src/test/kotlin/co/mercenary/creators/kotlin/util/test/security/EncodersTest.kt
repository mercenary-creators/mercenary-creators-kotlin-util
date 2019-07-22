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

package co.mercenary.creators.kotlin.util.test.security

import co.mercenary.creators.kotlin.util.*
import co.mercenary.creators.kotlin.util.security.Ciphers
import org.junit.jupiter.api.Test

class EncodersTest : KotlinSecurityTest() {
    @Test
    fun text() {
        val name = AUTHOR_INFO
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
        name.shouldBe(text) {
            "not $name"
        }
        val temp = timed {
            code.encode(text)
        }
        info { temp }
        data.shouldBe(temp) {
            "not $data"
        }
        val last = timed {
            code.encode(text)
        }
        info { last }
        last.shouldBe(temp) {
            "not $temp"
        }
        info { Ciphers.getAlgorithms() }
        info { Digests.getAlgorithms() }
    }
}