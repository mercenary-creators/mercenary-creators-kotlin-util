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

class GCMFileFastTest : KotlinSecurityTest() {
    @Test
    fun test() {
        val pass = getGeneratedPass()
        val salt = getGeneratedSalt()
        info { pass }
        info { salt }
        val good = isGood(pass)
        info { good }
        good.shouldBe(true)
        val temp = getTempFile(uuid(), ".txt")
        val baos = ByteArrayOutputStream(DEFAULT_BUFFER_SIZE)
        val save = ByteArrayOutputStream(DEFAULT_BUFFER_SIZE)
        val code = getCopyCipher(pass, salt, CipherAlgorithm.GCM)
        val data = CACHED_CONTENT_RESOURCE_LOADER["test.txt"]
        repeat(7) {
            baos.reset()
            save.reset()
            code.encrypt(data, baos)
            code.decrypt(baos.toByteArray().toInputStream(), save)
        }
        timed {
            code.encrypt(data, temp)
        }
        data.toByteArray().shouldNotBe(temp.toByteArray())
        val copy = getTempFile(uuid(), ".txt")
        timed {
            code.decrypt(temp, copy)
        }
        copy.forEachLineIndexed(block = printer)
        data.toByteArray().shouldBe(copy.toByteArray())
    }
}