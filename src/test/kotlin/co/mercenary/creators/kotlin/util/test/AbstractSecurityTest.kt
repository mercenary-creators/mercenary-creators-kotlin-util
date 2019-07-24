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

package co.mercenary.creators.kotlin.util.test

import co.mercenary.creators.kotlin.util.*
import co.mercenary.creators.kotlin.util.security.*
import co.mercenary.creators.kotlin.util.security.CipherAlgorithm

abstract class AbstractSecurityTest(private val text: String = CREATORS_AUTHOR_INFO) : AbstractKotlinTest() {

    protected open fun getGeneratedText(): String = text.toValidated()

    protected open fun getGeneratedPass(): CharSequence = Passwords.pass()

    protected open fun getGeneratedSalt(): CharSequence = Passwords.salt()

    protected fun isGood(pass: CharSequence): Boolean = Passwords.good(pass)

    protected fun getTextCipher(pass: CharSequence, salt: CharSequence, algorithm: CipherAlgorithm = CipherAlgorithm.CBC) = CipherEncryptingText(pass, salt, algorithm)

    protected fun getDataCipher(pass: CharSequence, salt: CharSequence, algorithm: CipherAlgorithm = CipherAlgorithm.CBC) = CipherEncryptingData(pass, salt, algorithm)

    protected fun getCopyCipher(pass: CharSequence, salt: CharSequence, algorithm: CipherAlgorithm = CipherAlgorithm.CBC) = CipherEncryptingCopy(pass, salt, algorithm)
}