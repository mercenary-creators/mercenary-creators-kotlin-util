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
import java.nio.ByteBuffer
import java.security.*

@IgnoreForSerialize
object Digests : HasMapNames {

    @JvmStatic
    @FrameworkDsl
    fun sha256() = getMessageDigest("SHA-256")

    @JvmStatic
    @FrameworkDsl
    fun sha512() = getMessageDigest("SHA-512")

    @JvmStatic
    @FrameworkDsl
    @IgnoreForSerialize
    fun getAlgorithms(): Algorithm = Algorithm.forName("MessageDigest")

    @FrameworkDsl
    override fun toString() = toMapNames().toSafeString()

    @FrameworkDsl
    override fun toMapNames() = dictOf("type" to nameOf(), "digests" to getAlgorithms())

    @JvmStatic
    @FrameworkDsl
    fun getMessageDigest(named: String): MessageDigest = MessageDigest.getInstance(named)

    @JvmStatic
    @FrameworkDsl
    fun proxyOf(digest: MessageDigest) = MessageDigestProxy(digest)

    @IgnoreForSerialize
    class MessageDigestProxy @FrameworkDsl @JvmOverloads internal constructor(private val digest: MessageDigest, private val algorithm: String = digest.algorithm, private val provider: Provider = digest.provider) : Clearable, Resetable, HasMapNames {

        @FrameworkDsl
        override fun toString() = toMapNames().toSafeString()

        @FrameworkDsl
        override fun hashCode() = toMapNames().hashOf()

        @FrameworkDsl
        override fun equals(other: Any?) = when (other) {
            is MessageDigestProxy -> this === other
            else -> false
        }

        @FrameworkDsl
        override fun toMapNames() = dictOf("type" to nameOf(), "algorithm" to algorithm, "provider" to dictOf("name" to provider.name, "version" to provider.version))

        @FrameworkDsl
        @JvmOverloads
        fun digest(buffer: ByteArray, target: ByteArray = buffer, finish: Boolean = true): ByteArray {
            update(buffer).digest().copyInto(target)
            if (finish.isTrue()) {
                clear()
            }
            return target
        }

        @FrameworkDsl
        fun update(buffer: Byte): MessageDigestProxy {
            digest.update(buffer)
            return this
        }

        @FrameworkDsl
        fun update(buffer: ByteArray): MessageDigestProxy {
            digest.update(buffer)
            return this
        }

        @FrameworkDsl
        fun update(buffer: ByteArray, off: Int, len: Int): MessageDigestProxy {
            digest.update(buffer, off, len)
            return this
        }

        @FrameworkDsl
        fun update(buffer: ByteBuffer): MessageDigestProxy {
            digest.update(buffer)
            return this
        }

        @FrameworkDsl
        fun finish(): MessageDigestProxy {
            clear()
            return this
        }

        @FrameworkDsl
        fun digest(): ByteArray {
            return digest.digest()
        }

        @FrameworkDsl
        override fun clear() {
            reset()
        }

        @FrameworkDsl
        override fun reset() {
            digest.reset()
        }
    }
}