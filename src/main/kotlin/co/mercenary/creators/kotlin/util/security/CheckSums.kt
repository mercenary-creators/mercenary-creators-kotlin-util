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
import java.util.zip.*

@IgnoreForSerialize
object CheckSums : HasMapNames {

    @FrameworkDsl
    private const val CRC_32_NAME = "crc32"

    @FrameworkDsl
    private const val ADL_32_NAME = "adl32"

    @JvmStatic
    @FrameworkDsl
    fun crc32(): CheckSum = CheckSumFactory(CRC32(), CRC_32_NAME)

    @JvmStatic
    @FrameworkDsl
    fun adl32(): CheckSum = CheckSumFactory(Adler32(), ADL_32_NAME)

    @FrameworkDsl
    override fun toString() = toMapNames().toSafeString()

    @FrameworkDsl
    override fun toMapNames() = dictOfType<CheckSums>("methods" to Common.getExposedMethods(CheckSums, true))

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
            return factory.update(data, 0, data.sizeOf()).let { total }
        }

        @FrameworkDsl
        override fun reset() = factory.reset()

        @FrameworkDsl
        override fun toMapNames() = dictOf("type" to nameOf(), "name" to toName())

        @FrameworkDsl
        override fun toString() = toName()

        @FrameworkDsl
        override fun hashCode() = toName().hashCode()

        @FrameworkDsl
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