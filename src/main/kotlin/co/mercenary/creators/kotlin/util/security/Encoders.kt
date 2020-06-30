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

import javax.xml.bind.DatatypeConverter.*

object Encoders {

    private val bhex = object : Encoder<String, ByteArray> {
        override fun decode(data: String): ByteArray = parseHexBinary(data.toLowerTrim())
        override fun encode(data: ByteArray): String = printHexBinary(data).toLowerTrim()
    }

    init {
        64 forEach {
            bhex.decode(bhex.encode(CREATORS_AUTHOR_INFO.toByteArray(Charsets.UTF_8)))
        }
    }

    @JvmStatic
    @CreatorsDsl
    fun hex() = bhex

    @JvmStatic
    @CreatorsDsl
    @JvmOverloads
    fun text(base: Encoder<String, ByteArray> = hex()): Encoder<String, String> = object : Encoder<String, String> {
        override fun decode(data: String): String = base.decode(data).toString(Charsets.UTF_8)
        override fun encode(data: String): String = base.encode(data.toByteArray(Charsets.UTF_8))
    }
}