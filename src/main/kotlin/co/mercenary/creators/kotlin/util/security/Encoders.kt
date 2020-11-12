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
import javax.xml.bind.DatatypeConverter

@IgnoreForSerialize
object Encoders {

    @CreatorsDsl
    private val DIGITS = "0123456789ABCDEF".toCharArray()

    @JvmStatic
    @CreatorsDsl
    fun getHexChar(code: Int): Char = DIGITS[code and 0xF]

    @CreatorsDsl
    private fun String.convert(): ByteArray = DatatypeConverter.parseHexBinary(toLowerTrim())

    @CreatorsDsl
    private fun ByteArray.convert(): String = DatatypeConverter.printHexBinary(this).toLowerTrim()

    @CreatorsDsl
    override fun toString() = nameOf()

    @CreatorsDsl
    private val bhex = object : Encoder<String, ByteArray> {

        @CreatorsDsl
        override fun toString() = "HexBinaryEncoder"

        @CreatorsDsl
        override fun decode(data: String): ByteArray = data.convert()

        @CreatorsDsl
        override fun encode(data: ByteArray): String = data.convert()
    }

    init {
        64 forEach {
            bhex.decode(bhex.encode(CREATORS_AUTHOR_INFO.getContentData()))
        }
    }

    @JvmStatic
    @CreatorsDsl
    fun hex() = bhex

    @JvmStatic
    @CreatorsDsl
    fun toText(base: Encoder<String, ByteArray>): Encoder<String, String> = object : Encoder<String, String> {

        @CreatorsDsl
        override fun toString() = "HexStringEncoder"

        @CreatorsDsl
        override fun decode(data: String): String = base.decode(data).getContentText()

        @CreatorsDsl
        override fun encode(data: String): String = base.encode(data.getContentData())
    }
}