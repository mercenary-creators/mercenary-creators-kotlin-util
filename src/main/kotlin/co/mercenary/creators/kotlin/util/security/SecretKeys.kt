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
import javax.crypto.*
import javax.crypto.spec.*

object SecretKeys {

    @JvmStatic
    @CreatorsDsl
    fun getSecret(pass: CharArray, salt: ByteArray, algorithm: CipherAlgorithm): SecretKey {
        return SecretKeySpec(SecretKeyFactory.getInstance(algorithm.getCipherSecretKey()).generateSecret(PBEKeySpec(pass.toCharArray(), salt.toByteArray(), algorithm.getCipherIteration(), algorithm.getCipherKeyLength())).encoded, algorithm.getCipherAlgorithm())
    }

    @JvmStatic
    @CreatorsDsl
    fun getSecret(pass: ByteArray, salt: ByteArray, algorithm: CipherAlgorithm): SecretKey {
        return getSecret(pass.toCharArray(), salt.toByteArray(), algorithm)
    }

    @JvmStatic
    @CreatorsDsl
    fun getSecret(pass: CharSequence, salt: CharSequence, algorithm: CipherAlgorithm): SecretKey {
        return getSecret(pass.toString().toCharArray(), Encoders.hex().decode(salt.toString()), algorithm)
    }

    @JvmStatic
    @CreatorsDsl
    fun getSecret(pass: SecureChars, salt: SecureBytes, algorithm: CipherAlgorithm): SecretKey {
        return getSecret(pass.toCharArray(), salt.toByteArray(), algorithm)
    }

    @JvmStatic
    @CreatorsDsl
    fun getSecret(pass: SecureChars, salt: SecureChars, algorithm: CipherAlgorithm): SecretKey {
        return getSecret(pass.toCharArray().toCharSequence(), salt.toCharArray().toCharSequence(), algorithm)
    }

    @JvmStatic
    @CreatorsDsl
    fun getSecret(pass: SecureBytes, salt: SecureBytes, algorithm: CipherAlgorithm): SecretKey {
        return getSecret(pass.toByteArray(), salt.toByteArray(), algorithm)
    }

    @JvmStatic
    @CreatorsDsl
    @IgnoreForSerialize
    fun getAlgorithms(): Algorithm = Algorithm.forName("SecretKeyFactory")
}