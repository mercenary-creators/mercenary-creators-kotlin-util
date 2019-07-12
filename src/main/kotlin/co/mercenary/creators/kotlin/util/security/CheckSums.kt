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

import java.nio.ByteBuffer
import java.util.zip.*

object CheckSums {

    @JvmStatic
    fun crc32(): CheckSum = CheckSumFactory(CRC32())

    @JvmStatic
    fun adl32(): CheckSum = CheckSumFactory(Adler32())

    private class CheckSumFactory(private val factory: Checksum) : CheckSum {
        override fun decoder(data: String): Long = updater(data.toByteArray(Charsets.UTF_8))
        override fun encoder(data: String): String = Encoders.hex().encode(buffers(decoder(data)))
        override fun updater(data: ByteArray): Long = factory.also { it.update(data, 0, data.size) }.value
        override fun buffers(data: Long): ByteArray = ByteBuffer.allocate(Int.SIZE_BYTES).putInt((data and 0xffffffffL).toInt()).array()
    }
}