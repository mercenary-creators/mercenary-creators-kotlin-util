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

object Escapers {

    @CreatorsDsl
    private const val ASCII_MINVAL = 32

    @CreatorsDsl
    private const val ASCII_MAXVAL = 127

    @CreatorsDsl
    private const val ESCAPE_SLASH = '\\'

    @CreatorsDsl
    private const val SINGLE_QUOTE = '\''

    @CreatorsDsl
    private const val DOUBLE_QUOTE = '"'

    @CreatorsDsl
    private const val ASCII_BSCHAR = '\b'

    @CreatorsDsl
    private const val ASCII_NLCHAR = '\n'

    @CreatorsDsl
    private const val ASCII_VTCHAR = '\t'

    @CreatorsDsl
    private const val ASCII_CRCHAR = '\r'

    @CreatorsDsl
    private const val ASCII_FFCHAR = 12.toChar()

    @CreatorsDsl
    private val ASCII_TABLE: BooleanArray by lazy {
        BooleanArray(128) { code ->
            (code in 32..126 && (code >= 93 || code in 48..91 || code in 32..33 || code in 35..38 || code in 40..46))
        }
    }

    @CreatorsDsl
    private fun Char.toCode(): Int = toInt()

    @CreatorsDsl
    private fun StringBuilder.escape(code: Int): StringBuilder {
        return code.toHexString().toUpperCaseEnglish().let { buff ->
            when ((4 - buff.length).boxIn(0, 3)) {
                0 -> add("\\u")
                1 -> add("\\u0")
                2 -> add("\\u00")
                3 -> add("\\u000")
            }
            add(buff)
        }
    }

    @CreatorsDsl
    private fun StringBuilder.encode(code: Char): StringBuilder {
        return add(ESCAPE_SLASH, code)
    }

    @CreatorsDsl
    private fun StringBuilder.quoted(code: Boolean): StringBuilder {
        return if (code) add(DOUBLE_QUOTE) else this
    }

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
            quoted(quoted)
            for (char in string) {
                val code = char.toCode()
                if (isAscii(code).isTrue()) {
                    add(char)
                    continue
                }
                when {
                    code > ASCII_MAXVAL -> escape(code)
                    code < ASCII_MINVAL -> when (char) {
                        ASCII_BSCHAR -> encode('b')
                        ASCII_NLCHAR -> encode('n')
                        ASCII_VTCHAR -> encode('t')
                        ASCII_CRCHAR -> encode('r')
                        ASCII_FFCHAR -> encode('f')
                        else -> escape(code)
                    }
                    else -> when (char) {
                        SINGLE_QUOTE -> encode(SINGLE_QUOTE)
                        DOUBLE_QUOTE -> encode(DOUBLE_QUOTE)
                        ESCAPE_SLASH -> encode(ESCAPE_SLASH)
                        else -> add(char)
                    }
                }
            }
            quoted(quoted)
        }
    }
}