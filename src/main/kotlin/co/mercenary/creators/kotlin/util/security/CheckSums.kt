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

object CheckSums {

    @JvmStatic
    @CreatorsDsl
    fun crc32(): CheckSum = CheckSumFactory(CRC32())

    @JvmStatic
    @CreatorsDsl
    fun adl32(): CheckSum = CheckSumFactory(Adler32())

    @IgnoreForSerialize
    private class CheckSumFactory @CreatorsDsl constructor(private val factory: Checksum) : CheckSum {

        @CreatorsDsl
        override fun clear() = factory.reset()

        @CreatorsDsl
        override fun decoder(data: String): Long = updater(data.getContentData())

        @CreatorsDsl
        override fun encoder(data: String): String = Encoders.hex().encode(buffers(decoder(data)))

        @CreatorsDsl
        override fun updater(data: ByteArray): Long = factory.also { it.update(data, 0, data.size) }.value

        @CreatorsDsl
        override fun buffers(data: Long): ByteArray = getByteBuffer(Int.SIZE_BYTES).putInt(data.toMasked()).toByteArray()
    }
}