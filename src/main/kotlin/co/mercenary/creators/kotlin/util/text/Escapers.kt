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

package co.mercenary.creators.kotlin.util.text

import co.mercenary.creators.kotlin.util.*

@IgnoreForSerialize
object Escapers : HasMapNames {

    @FrameworkDsl
    const val ASCII_FFCODE = 12

    @FrameworkDsl
    const val ASCII_MINVAL = 32

    @FrameworkDsl
    const val ASCII_MAXVAL = 127

    @FrameworkDsl
    const val ESCAPE_SLASH = '\\'

    @FrameworkDsl
    const val SINGLE_QUOTE = '\''

    @FrameworkDsl
    const val DOUBLE_QUOTE = '"'

    @FrameworkDsl
    const val ASCII_BSCHAR = '\b'

    @FrameworkDsl
    const val ASCII_NLCHAR = '\n'

    @FrameworkDsl
    const val ASCII_VTCHAR = '\t'

    @FrameworkDsl
    const val ASCII_CRCHAR = '\r'

    @FrameworkDsl
    const val ASCII_FFCHAR = ASCII_FFCODE.toChar()

    @FrameworkDsl
    private val ASCII_TABLE: BooleanArray by lazy {
        BooleanArray(128) { code ->
            ((code in (32..126)) && ((code >= 93) || (code in (48..91)) || (code in (32..33)) || (code in (35..38)) || (code in (40..46))))
        }
    }

    @FrameworkDsl
    override fun toString() = toMapNames().toSafeString()

    @FrameworkDsl
    override fun toMapNames() = dictOf("type" to nameOf())

    @JvmStatic
    @FrameworkDsl
    fun isAscii(code: Int): Boolean {
        return if (code < ASCII_MINVAL || code > ASCII_MAXVAL) false else ASCII_TABLE[code]
    }

    @JvmStatic
    @FrameworkDsl
    fun isAscii(code: Char): Boolean {
        return isAscii(code.toCode())
    }

    @JvmStatic
    @FrameworkDsl
    fun isAscii(string: CharSequence): Boolean {
        if (string.isNotExhausted()) {
            for (char in string) {
                if (isAscii(char.toCode()).isNotTrue()) {
                    return false
                }
            }
        }
        return true
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun toEscapedString(string: String, quoted: Boolean = false): String {
        if (isAscii(string)) {
            return if (quoted.isTrue()) "\"$string\"" else string
        }
        return stringOf(string.sizeOf() * 2) {
            if (quoted.isTrue()) {
                add(DOUBLE_QUOTE)
            }
            for (char in string) {
                val code = char.toCode()
                if (isAscii(code).isTrue()) {
                    add(char)
                    continue
                }
                when {
                    code > ASCII_MAXVAL -> encode(code)
                    code < ASCII_MINVAL -> when (char) {
                        ASCII_BSCHAR -> escape('b')
                        ASCII_NLCHAR -> escape('n')
                        ASCII_VTCHAR -> escape('t')
                        ASCII_CRCHAR -> escape('r')
                        ASCII_FFCHAR -> escape('f')
                        else -> encode(code)
                    }
                    else -> when (char) {
                        SINGLE_QUOTE -> escape(SINGLE_QUOTE)
                        DOUBLE_QUOTE -> escape(DOUBLE_QUOTE)
                        ESCAPE_SLASH -> escape(ESCAPE_SLASH)
                        else -> add(char)
                    }
                }
            }
            if (quoted.isTrue()) {
                add(DOUBLE_QUOTE)
            }
        }
    }
}