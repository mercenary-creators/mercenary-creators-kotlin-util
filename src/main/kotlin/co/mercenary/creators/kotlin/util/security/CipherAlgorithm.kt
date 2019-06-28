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

import java.security.spec.AlgorithmParameterSpec
import javax.crypto.spec.*

interface CipherAlgorithm {
    fun getCipherKeyLength(): Int
    fun getCipherIteration(): Int
    fun getCipherSecretKey(): String
    fun getCipherAlgorithm(): String
    fun getCipherTransform(): String
    fun getAlgorithmParams(vector: ByteArray): AlgorithmParameterSpec

    companion object {

        val GCM = CipherAlgorithmFactory("AES/GCM/NoPadding") {
            GCMParameterSpec(128, it)
        }

        val CBC = CipherAlgorithmFactory("AES/CBC/PKCS5Padding") {
            IvParameterSpec(it)
        }

        open class CipherAlgorithmFactory(private val tran: String, private val type: String = "PBKDF2WithHmacSHA512", private val size: Int = 256, private val iter: Int = 4096, private val algo: String = "AES", private val factory: (ByteArray) -> AlgorithmParameterSpec) : CipherAlgorithm {
            override fun getCipherKeyLength(): Int = size
            override fun getCipherIteration(): Int = iter
            override fun getCipherSecretKey(): String = type
            override fun getCipherAlgorithm(): String = algo
            override fun getCipherTransform(): String = tran
            override fun getAlgorithmParams(vector: ByteArray) = factory(vector)
        }
    }
}