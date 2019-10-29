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

import co.mercenary.creators.kotlin.util.*
import org.apache.commons.codec.binary.Hex

object Encoders {

    private val bhex = object : Encoder<String, ByteArray> {
        override fun decode(data: String): ByteArray = Hex.decodeHex(data.toLowerTrim())
        override fun encode(data: ByteArray): String = Hex.encodeHexString(data).toLowerTrim()
    }

    init {
        bhex.decode(bhex.encode(EMPTY_STRING.toByteArray(Charsets.UTF_8)))
    }

    @JvmStatic
    fun hex() = bhex

    @JvmStatic
    fun text(base: Encoder<String, ByteArray>): Encoder<String, String> = object : Encoder<String, String> {
        override fun decode(data: String): String = String(base.decode(data), Charsets.UTF_8)
        override fun encode(data: String): String = base.encode(data.toByteArray(Charsets.UTF_8))
    }
}