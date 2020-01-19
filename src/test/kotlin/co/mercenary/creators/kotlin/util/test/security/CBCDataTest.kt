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

class CBCDataTest : KotlinSecurityTest() {
    @Test
    fun test() {
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
        val code = getDataCipher(pass, salt, CipherAlgorithm.CBC).also {
            it.decrypt(it.encrypt(bits))
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
        info { bits isSameAs back }
        info { dash() }
        info { 1..16 isSameAs 1..16 }
        info { 1..16 isNotSameAs 1..1024 }
        info { dash() }
        info { 1.toAtomic() isSameAs 1 }
        info { 1.toAtomic() isSameAs 1L }
        info { dash() }
        info { 1.toAtomic() isSameAs 1.toAtomic() }
        info { 1.toAtomic() isSameAs 1L.toAtomic() }
        info { dash() }
        info { true.toAtomic() isSameAs true }
        info { true isSameAs true.toAtomic() }
        info { dash() }
        info { 1L.toAtomic() isSameAs 1.0 }
        info { 1.0 isSameAs 1L.toAtomic() }
        info { dash() }
        info { 1.0.toBigDecimal() isSameAs 1L.toAtomic() }
        info { 1.0.toBigDecimal() == 1.0.toBigDecimal() }
    }
}