/*
 * Copyright (c) 2021, Mercenary Creators Company. All rights reserved.
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
import java.security.SecureRandom
import java.security.spec.AlgorithmParameterSpec
import javax.crypto.spec.*

@IgnoreForSerialize
interface CipherAlgorithm : HasMapNames {

    @FrameworkDsl
    @IgnoreForSerialize
    fun getFactoryKeysSize(): Int

    @FrameworkDsl
    @IgnoreForSerialize
    fun getCipherKeyLength(): Int

    @FrameworkDsl
    @IgnoreForSerialize
    fun getCipherIteration(): Int

    @FrameworkDsl
    @IgnoreForSerialize
    fun getCipherSecretKey(): String

    @FrameworkDsl
    @IgnoreForSerialize
    fun getCipherAlgorithm(): String

    @FrameworkDsl
    @IgnoreForSerialize
    fun getCipherTransform(): String

    @FrameworkDsl
    @IgnoreForSerialize
    fun getFactoryKeysRand(): Factory<SecureRandom>

    @FrameworkDsl
    @IgnoreForSerialize
    fun getAlgorithmParams(vector: ByteArray): AlgorithmParameterSpec

    companion object {

        @FrameworkDsl
        val GCM = CipherAlgorithmFactory("AES/GCM/NoPadding") { vector ->
            GCMParameterSpec(128, vector)
        }

        @FrameworkDsl
        val CBC = CipherAlgorithmFactory("AES/CBC/PKCS5Padding") { vector ->
            IvParameterSpec(vector)
        }

        @IgnoreForSerialize
        class CipherAlgorithmFactory @JvmOverloads @FrameworkDsl constructor(private val tran: String, private val type: String = "PBKDF2WithHmacSHA512", private val size: Int = 16, private val leng: Int = 256, private val iter: Int = 4096, private val algo: String = "AES", private val factory: Convert<ByteArray, AlgorithmParameterSpec>) : CipherAlgorithm {

            @FrameworkDsl
            @IgnoreForSerialize
            override fun getFactoryKeysSize() = size.copyOf()

            @FrameworkDsl
            @IgnoreForSerialize
            override fun getCipherKeyLength() = leng.copyOf()

            @FrameworkDsl
            @IgnoreForSerialize
            override fun getCipherIteration() = iter.copyOf()

            @FrameworkDsl
            @IgnoreForSerialize
            override fun getCipherSecretKey() = type.copyOf()

            @FrameworkDsl
            @IgnoreForSerialize
            override fun getCipherAlgorithm() = algo.copyOf()

            @FrameworkDsl
            @IgnoreForSerialize
            override fun getCipherTransform() = tran.copyOf()

            @FrameworkDsl
            @IgnoreForSerialize
            override fun getFactoryKeysRand() = Randoms.getStrongInstanceFactory()

            @FrameworkDsl
            @IgnoreForSerialize
            override fun getAlgorithmParams(vector: ByteArray) = factory.convert(vector)

            @FrameworkDsl
            override fun toString() = toMapNames().toSafeString()

            @FrameworkDsl
            override fun hashCode() = idenOf()

            @FrameworkDsl
            override fun toMapNames() = dictOfType<CipherAlgorithmFactory>("transform" to getCipherTransform())
        }
    }
}