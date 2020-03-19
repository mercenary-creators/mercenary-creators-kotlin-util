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

class CBCTextTest : KotlinSecurityTest() {
    @Test
    fun test() {
        val name = getGeneratedText()
        val pass = getGeneratedPass()
        val salt = getGeneratedSalt()
        info { name }
        info { pass }
        info { salt }
        val good = isValid(pass)
        info { good }
        good.shouldBe(true)
        val code = getTextCipher(pass, salt, CipherAlgorithm.CBC).also {
            it.decrypt(it.encrypt(name))
        }
        val data = timed {
            code.encrypt(name)
        }
        info { data }
        val back = timed {
            code.decrypt(data)
        }
        info { back }
        name.shouldBe(back)
    }
}