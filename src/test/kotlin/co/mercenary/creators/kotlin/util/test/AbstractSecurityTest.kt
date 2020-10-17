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

package co.mercenary.creators.kotlin.util.test

import co.mercenary.creators.kotlin.util.*
import co.mercenary.creators.kotlin.util.io.ContentResource
import co.mercenary.creators.kotlin.util.security.*
import co.mercenary.creators.kotlin.util.security.CipherAlgorithm

abstract class AbstractSecurityTest @JvmOverloads constructor(private val text: String = CREATORS_AUTHOR_INFO) : AbstractKotlinTest() {

    @CreatorsDsl
    protected open val rounds: Int
        @IgnoreForSerialize
        get() = 4

    @CreatorsDsl
    protected infix fun CipherEncryptingCopy.warmup(data: ContentResource) {
        if (rounds > 0) {
            val baos = BytesOutputStream()
            val save = BytesOutputStream()
            rounds forEach {
                baos.clear()
                save.clear()
                encrypt(data, baos)
                decrypt(baos.getContentData(), save)
            }
        }
    }

    @CreatorsDsl
    @IgnoreForSerialize
    protected open fun getGeneratedText(): String = text

    @CreatorsDsl
    @IgnoreForSerialize
    protected open fun getGeneratedPass(): CharSequence = Passwords.getGeneratedPass()

    @CreatorsDsl
    @IgnoreForSerialize
    protected open fun getGeneratedSalt(): CharSequence = Passwords.getGeneratedSalt()

    @CreatorsDsl
    protected open fun isValid(pass: CharSequence): Boolean = Passwords.isValid(pass)

    @CreatorsDsl
    @JvmOverloads
    protected fun getTextCipher(pass: CharSequence, salt: CharSequence, algorithm: CipherAlgorithm = CipherAlgorithm.CBC) = CipherEncryptingText(pass, salt, algorithm)

    @CreatorsDsl
    @JvmOverloads
    protected fun getDataCipher(pass: CharSequence, salt: CharSequence, algorithm: CipherAlgorithm = CipherAlgorithm.CBC) = CipherEncryptingData(pass, salt, algorithm)

    @CreatorsDsl
    @JvmOverloads
    protected fun getCopyCipher(pass: CharSequence, salt: CharSequence, algorithm: CipherAlgorithm = CipherAlgorithm.CBC) = CipherEncryptingCopy(pass, salt, algorithm)
}