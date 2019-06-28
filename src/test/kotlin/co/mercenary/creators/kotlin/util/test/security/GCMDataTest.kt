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
import org.junit.jupiter.api.Test

class GCMDataTest : KotlinSecurityTest() {
    @Test
    fun text() {
        val rand = Randoms.toRandom()
        val pass = getGeneratedPass()
        val salt = getGeneratedSalt()
        info { pass }
        info { salt }
        val good = isGood(pass)
        info { good }
        good.shouldBe(true) {
            pass
        }
        val bits = Randoms.getByteArray(rand, 1024)
        info { Encoders.hex().encode(bits) }
        val code = getDataCipher(pass, salt, CipherAlgorithm.GCM).also { self ->
            self.decrypt(self.encrypt(bits))
        }
        val data = timed {
            code.encrypt(bits)
        }
        info { Encoders.hex().encode(data) }
        val back = timed {
            code.decrypt(data)
        }
        info { Encoders.hex().encode(back) }
        bits.shouldBe(back) {
            Encoders.hex().encode(back)
        }
    }
}