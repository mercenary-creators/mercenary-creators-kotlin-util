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

package co.mercenary.creators.kotlin.util.text

import co.mercenary.creators.kotlin.util.*

@IgnoreForSerialize
object Escapers : HasMapNames {

    @CreatorsDsl
    const val ASCII_MINVAL = 32

    @CreatorsDsl
    const val ASCII_MAXVAL = 127

    @CreatorsDsl
    const val ESCAPE_SLASH = '\\'

    @CreatorsDsl
    const val SINGLE_QUOTE = '\''

    @CreatorsDsl
    const val DOUBLE_QUOTE = '"'

    @CreatorsDsl
    const val ASCII_BSCHAR = '\b'

    @CreatorsDsl
    const val ASCII_NLCHAR = '\n'

    @CreatorsDsl
    const val ASCII_VTCHAR = '\t'

    @CreatorsDsl
    const val ASCII_CRCHAR = '\r'

    @CreatorsDsl
    const val ASCII_FFCHAR = 12.toChar()

    @CreatorsDsl
    private val ASCII_TABLE: BooleanArray by lazy {
        BooleanArray(128) { code ->
            (code in 32..126 && (code >= 93 || code in 48..91 || code in 32..33 || code in 35..38 || code in 40..46))
        }
    }

    @CreatorsDsl
    override fun toString() = toMapNames().toSafeString()

    @CreatorsDsl
    override fun toMapNames() = dictOf("type" to nameOf())

    @JvmStatic
    @CreatorsDsl
    fun isAscii(code: Int): Boolean {
        return if (code < ASCII_MINVAL || code > ASCII_MAXVAL) false else ASCII_TABLE[code]
    }

    @JvmStatic
    @CreatorsDsl
    fun isAscii(code: Char): Boolean {
        return isAscii(code.toCode())
    }

    @JvmStatic
    @CreatorsDsl
    fun isAscii(string: String): Boolean {
        if (string.isNotEmpty()) {
            for (char in string) {
                if (isAscii(char.toCode()).isNotTrue()) {
                    return false
                }
            }
        }
        return true
    }

    @JvmStatic
    @CreatorsDsl
    @JvmOverloads
    fun toEscapedString(string: String, quoted: Boolean = false): String {
        if (isAscii(string)) {
            return if (quoted) "\"$string\"" else string
        }
        return stringOf(string.length * 2) {
            if (quoted) {
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
            if (quoted) {
                add(DOUBLE_QUOTE)
            }
        }
    }
}