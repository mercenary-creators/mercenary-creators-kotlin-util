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

package co.mercenary.creators.kotlin.util.security

import co.mercenary.creators.kotlin.util.*
import java.io.*
import javax.crypto.SecretKey

@IgnoreForSerialize
class CipherEncryptingCopy @JvmOverloads @CreatorsDsl constructor(secret: SecretKey, algorithm: CipherAlgorithm = CipherAlgorithm.CBC) : CipherCopyStreams {

    @CreatorsDsl
    @JvmOverloads
    constructor(pass: CharSequence, salt: CharSequence, algorithm: CipherAlgorithm = CipherAlgorithm.CBC) : this(SecretKeys.getSecret(pass, salt, algorithm), algorithm)

    @CreatorsDsl
    private val proxy = Ciphers.copy(secret, algorithm)

    @CreatorsDsl
    override fun encrypt(data: InputStream, copy: OutputStream) = proxy.encrypt(data, copy)

    @CreatorsDsl
    override fun decrypt(data: InputStream, copy: OutputStream) = proxy.decrypt(data, copy)
}