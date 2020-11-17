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
import java.util.zip.*

@IgnoreForSerialize
object CheckSums : HasMapNames {

    @FrameworkDsl
    private const val CRC_32_NAME = "crc32"

    @FrameworkDsl
    private const val ADL_32_NAME = "adl32"

    @JvmStatic
    @CreatorsDsl
    fun crc32(): CheckSum = CheckSumFactory(CRC32(), CRC_32_NAME)

    @JvmStatic
    @CreatorsDsl
    fun adl32(): CheckSum = CheckSumFactory(Adler32(), ADL_32_NAME)

    @CreatorsDsl
    override fun toString() = toMapNames().toSafeString()

    @FrameworkDsl
    override fun toMapNames() = dictOf("type" to nameOf(), "sums" to unique(CRC_32_NAME, ADL_32_NAME))

    @IgnoreForSerialize
    private class CheckSumFactory @FrameworkDsl constructor(private val factory: Checksum, private val name: String) : CheckSum {

        @FrameworkDsl
        override val total: Long
            @IgnoreForSerialize
            get() = factory.value

        @FrameworkDsl
        private fun toName() = name

        @FrameworkDsl
        private fun updateGetTotalOf(data: ByteArray): Long {
            return factory.update(data, 0, data.size).let { total }
        }

        @CreatorsDsl
        override fun reset() = factory.reset()

        @CreatorsDsl
        override fun toMapNames() = dictOf("type" to nameOf(), "name" to toName())

        @CreatorsDsl
        override fun toString() = toName()

        @CreatorsDsl
        override fun hashCode() = toName().hashCode()

        @CreatorsDsl
        override fun equals(other: Any?) = when (other) {
            is CheckSumFactory -> other === this || toName() == other.toName()
            else -> false
        }

        @FrameworkDsl
        override fun decoder(data: String): Long = updater(data.getContentData())

        @FrameworkDsl
        override fun encoder(data: String): String = Encoders.hex().encode(buffers(decoder(data)))

        @FrameworkDsl
        override fun updater(data: ByteArray): Long = updateGetTotalOf(data)

        @FrameworkDsl
        override fun buffers(data: Long): ByteArray = toByteArrayOfInt(data)
    }
}