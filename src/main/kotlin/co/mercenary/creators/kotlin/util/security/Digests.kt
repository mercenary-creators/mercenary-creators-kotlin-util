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
import java.nio.ByteBuffer
import java.security.MessageDigest

object Digests {

    @JvmStatic
    @CreatorsDsl
    fun sha256() = getMessageDigest("SHA-256")

    @JvmStatic
    @CreatorsDsl
    fun sha512() = getMessageDigest("SHA-512")

    @JvmStatic
    @CreatorsDsl
    @IgnoreForSerialize
    fun getAlgorithms(): Algorithm = Algorithm.forName("MessageDigest")

    @JvmStatic
    @CreatorsDsl
    fun getMessageDigest(named: String): MessageDigest = MessageDigest.getInstance(named)

    @JvmStatic
    @CreatorsDsl
    fun proxyOf(digest: MessageDigest) = MessageDigestProxy(digest)

    @IgnoreForSerialize
    class MessageDigestProxy @CreatorsDsl internal constructor(private val digest: MessageDigest) {

        @CreatorsDsl
        @JvmOverloads
        fun digest(buffer: ByteArray, target: ByteArray = buffer, finish: Boolean = true): ByteArray {
            update(buffer).digest().copyInto(target)
            if (finish) {
                finish()
            }
            return target
        }

        @CreatorsDsl
        fun update(buffer: ByteArray): MessageDigestProxy {
            digest.update(buffer)
            return this
        }

        @CreatorsDsl
        fun update(list: List<ByteArray>): MessageDigestProxy {
            list.forEach { buffer ->
                update(buffer)
            }
            return this
        }

        @CreatorsDsl
        fun update(buffer: ByteBuffer): MessageDigestProxy {
            digest.update(buffer)
            return this
        }

        @CreatorsDsl
        fun update(list: Iterable<ByteBuffer>): MessageDigestProxy {
            list.forEach { buffer ->
                update(buffer)
            }
            return this
        }

        @CreatorsDsl
        fun finish(): MessageDigestProxy {
            digest.reset()
            return this
        }

        @CreatorsDsl
        fun digest(): ByteArray {
            return digest.digest()
        }
    }
}