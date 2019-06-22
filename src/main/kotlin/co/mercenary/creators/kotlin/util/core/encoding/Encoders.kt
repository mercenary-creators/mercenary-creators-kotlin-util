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

package co.mercenary.creators.kotlin.util.core.encoding

import co.mercenary.creators.kotlin.util.getLowerTrim
import org.apache.commons.codec.binary.Hex

object Encoders {

    private val base16 = object : EncoderDecoder<String, ByteArray> {
        override fun decode(data: String): ByteArray = Hex.decodeHex(getLowerTrim(data))
        override fun encode(data: ByteArray): String = getLowerTrim(Hex.encodeHexString(data))
    }

    @JvmStatic
    fun hex() = base16
}