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

package co.mercenary.creators.kotlin.util.security

import co.mercenary.creators.kotlin.util.SerialIgnore
import java.security.SecureRandom
import java.security.spec.AlgorithmParameterSpec
import javax.crypto.spec.*

@SerialIgnore
interface CipherAlgorithm {

    fun getFactoryKeysSize(): Int
    fun getCipherKeyLength(): Int
    fun getCipherIteration(): Int
    fun getCipherSecretKey(): String
    fun getCipherAlgorithm(): String
    fun getCipherTransform(): String
    fun getFactoryKeysRand(): SecureRandom
    fun getAlgorithmParams(vector: ByteArray): AlgorithmParameterSpec

    companion object {

        val GCM = CipherAlgorithmFactory("AES/GCM/NoPadding") { vector ->
            GCMParameterSpec(128, vector)
        }

        val CBC = CipherAlgorithmFactory("AES/CBC/PKCS5Padding") { vector ->
            IvParameterSpec(vector)
        }

        @SerialIgnore
        open class CipherAlgorithmFactory @JvmOverloads constructor(private val tran: String, private val type: String = "PBKDF2WithHmacSHA512", private val size: Int = 16, private val leng: Int = 256, private val iter: Int = 4096, private val algo: String = "AES", private val factory: (ByteArray) -> AlgorithmParameterSpec) : CipherAlgorithm {
            override fun getFactoryKeysSize() = size
            override fun getCipherKeyLength() = leng
            override fun getCipherIteration() = iter
            override fun getCipherSecretKey() = type
            override fun getCipherAlgorithm() = algo
            override fun getCipherTransform() = tran
            override fun getFactoryKeysRand() = SecureRandom()
            override fun getAlgorithmParams(vector: ByteArray) = factory(vector)
        }
    }
}