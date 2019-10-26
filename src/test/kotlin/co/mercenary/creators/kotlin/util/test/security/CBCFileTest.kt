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

class CBCFileTest : KotlinSecurityTest() {
    @Test
    fun test() {
        val pass = getGeneratedPass()
        val salt = getGeneratedSalt()
        info { pass }
        info { salt }
        val good = isGood(pass)
        info { good }
        good.shouldBe(true) {
            pass
        }
        val temp = getTempFile(uuid(), ".txt")
        val baos = ByteArrayOutputStream(DEFAULT_BUFFER_SIZE)
        val save = ByteArrayOutputStream(DEFAULT_BUFFER_SIZE)
        val code = getCopyCipher(pass, salt, CipherAlgorithm.CBC)
        val data = RESOURCE_LOADER["test.txt"]
        repeat(7) {
            baos.reset()
            save.reset()
            code.encrypt(data, baos)
            code.decrypt(baos.toByteArray().toInputStream(), save)
        }
        timed {
            code.encrypt(data, temp)
        }
        data.toByteArray().shouldNotBe(temp.toByteArray()) {
            "files should not be the same."
        }
        val copy = getTempFile(uuid(), ".txt")
        timed {
            code.decrypt(temp, copy)
        }
        copy.readLines().forEachIndexed { i, s ->
            info { "%2d : %s".format(i + 1, s) }
        }
        data.toByteArray().shouldBe(copy.toByteArray()) {
            "files should not be different."
        }
    }
}