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

package co.mercenary.creators.kotlin.util.security

import co.mercenary.creators.kotlin.util.*
import javax.crypto.SecretKey

@IgnoreForSerialize
class CipherEncryptingText @JvmOverloads @FrameworkDsl constructor(secret: SecretKey, algorithm: CipherAlgorithm = CipherAlgorithm.CBC) : CipherEncrypting<String, String> {

    @FrameworkDsl
    @JvmOverloads
    constructor(pass: CharSequence, salt: CharSequence, algorithm: CipherAlgorithm = CipherAlgorithm.CBC) : this(SecretKeys.getSecret(pass, salt, algorithm), algorithm)

    @FrameworkDsl
    private val proxy = Ciphers.text(secret, algorithm)

    @FrameworkDsl
    override fun encrypt(data: String): String = proxy.encrypt(data)

    @FrameworkDsl
    override fun decrypt(data: String): String = proxy.decrypt(data)
}