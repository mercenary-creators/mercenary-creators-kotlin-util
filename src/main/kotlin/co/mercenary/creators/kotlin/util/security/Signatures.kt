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
import java.security.*

object Signatures {

    @JvmStatic
    @CreatorsDsl
    @IgnoreForSerialize
    fun getAlgorithms(): Algorithm = Algorithm.forName("Signature")

    @JvmStatic
    fun data(name: String, keys: SignerKeysFactory): Signer<ByteArray, ByteArray> = InternalSigner(name, keys)

    private class InternalSigner(private val name: String, private val keys: SignerKeysFactory) : Signer<ByteArray, ByteArray> {

        override fun signed(data: ByteArray): ByteArray {
            val signature = Signature.getInstance(name)
            signature.initSign(keys.getSignerKey())
            signature.update(data)
            return signature.sign()
        }

        override fun verify(data: ByteArray): Boolean {
            val signature = Signature.getInstance(name)
            signature.initVerify(keys.getVerifyKey())
            return signature.verify(data)
        }
    }
}