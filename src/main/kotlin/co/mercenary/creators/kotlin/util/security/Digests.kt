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

import java.security.MessageDigest

object Digests {

    @JvmStatic
    fun sha512() = getMessageDigest("SHA-512")

    @JvmStatic
    fun getAlgorithms() = Algorithms.getAlgorithmForName("MessageDigest")

    @JvmStatic
    fun getMessageDigest(named: String): MessageDigest = MessageDigest.getInstance(named)

    @JvmStatic
    fun proxy(digest: MessageDigest) = MessageDigestProxy(digest)

    class MessageDigestProxy(private val digest: MessageDigest) {
        operator fun invoke(buffer: ByteArray) = invoke(buffer, buffer, true)
        operator fun invoke(buffer: ByteArray, target: ByteArray) = invoke(buffer, target, true)
        operator fun invoke(buffer: ByteArray, target: ByteArray, finish: Boolean) {
            digest.digest(buffer).copyInto(target)
            if (finish) {
                digest.reset()
            }
        }
    }
}