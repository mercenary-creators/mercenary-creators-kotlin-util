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
import java.security.SecureRandom

@IgnoreForSerialize
class SimpleCipherKeysFactory private constructor(bits: Int, rand: Factory<SecureRandom>) : CipherKeysFactory, HasMapNames {

    @FrameworkDsl
    private val size = bits

    @FrameworkDsl
    private val fact = rand

    @FrameworkDsl
    constructor(cipher: CipherAlgorithm) : this(cipher.getFactoryKeysSize(), cipher.getFactoryKeysRand())

    @FrameworkDsl
    @IgnoreForSerialize
    override fun getSize(): Int = size.copyOf()

    @FrameworkDsl
    @IgnoreForSerialize
    override fun getKeys(): ByteArray = Randoms.getByteArray(fact.create(), getSize())

    @FrameworkDsl
    override fun toMapNames() = dictOf("type" to nameOf())
}