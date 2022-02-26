/*
 * Copyright (c) 2022, Mercenary Creators Company. All rights reserved.
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

package co.mercenary.creators.kotlin.util

@FrameworkDsl
@IgnoreForSerialize
object Encoders : HasMapNames {

    @FrameworkDsl
    private fun String.convert(): ByteArray = Common.decodeHexBinary(this)

    @FrameworkDsl
    private fun ByteArray.convert(): String = Common.encodeHexBinary(this)

    @FrameworkDsl
    override fun toString() = toMapNames().toSafeString()

    @FrameworkDsl
    override fun toMapNames() = dictOf("type" to nameOf(), "methods" to Common.getExposedMethods(Encoders, true))

    @FrameworkDsl
    private val bhex = object : Encoder<String, ByteArray> {

        @FrameworkDsl
        override fun toString() = "HexBinaryEncoder"

        @FrameworkDsl
        override fun decode(data: String): ByteArray = data.convert()

        @FrameworkDsl
        override fun encode(data: ByteArray): String = data.convert()
    }

    init {
        64 forEach {
            bhex.decode(bhex.encode(CREATORS_AUTHOR_INFO.getContentData()))
        }
    }

    @JvmStatic
    @FrameworkDsl
    fun hex(): Encoder<String, ByteArray> = bhex

    @JvmStatic
    @FrameworkDsl
    fun text(): Encoder<String, String> = object : Encoder<String, String> {

        @FrameworkDsl
        override fun toString() = "HexStringEncoder"

        @FrameworkDsl
        override fun decode(data: String): String = hex().decode(data).getContentText()

        @FrameworkDsl
        override fun encode(data: String): String = hex().encode(data.getContentData())
    }
}