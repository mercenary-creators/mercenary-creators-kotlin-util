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

package co.mercenary.creators.kotlin.util.test.security

import co.mercenary.creators.kotlin.util.*
import org.junit.jupiter.api.Test

class CBCFileFastTest : KotlinSecurityTest() {
    @Test
    fun test() {
        val pass = getGeneratedPass()
        val salt = getGeneratedSalt()
        info { pass }
        info { salt }
        val good = isValid(pass)
        info { good }
        good shouldBe true
        val temp = getTempFile(uuid(), ".txt")
        val code = getCopyCipher(pass, salt, CipherAlgorithm.CBC)
        val data = cached["data.txt"]
        code warmup data
        timed {
            code.encrypt(data, temp)
        }
        data shouldNotBeSameContent temp
        val copy = getTempFile(uuid(), ".txt")
        timed {
            code.decrypt(temp, copy)
        }
        copy forEachLineIndexed printer
        data shouldBeSameContent copy
    }
}