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

    private const val ASCII_MINVAL = 32

    private const val ASCII_MAXVAL = 127

    private const val ESCAPE_SLASH = '\\'

    private const val SINGLE_QUOTE = '\''

    private const val DOUBLE_QUOTE = '"'

    private const val ASCII_BSCHAR = '\b'

    private const val ASCII_NLCHAR = '\n'

    private const val ASCII_VTCHAR = '\t'

    private const val ASCII_CRCHAR = '\r'

    private const val ASCII_FFCHAR = 12.toChar()

    private val ASCII_TABLE: BooleanArray by lazy {
        BooleanArray(128) { code ->
            (code in 32..126 && (code >= 93 || code in 48..91 || code in 32..33 || code in 35..38 || code in 40..46))
        }
    }

    @CreatorsDsl
    private fun StringBuilder.escape(code: Int): StringBuilder {
        return code.toString(16).toUpperCase().let { buff ->
            when ((4 - buff.length).boxIn(0, 3)) {
                0 -> append("\\u")
                1 -> append("\\u0")
                2 -> append("\\u00")
                3 -> append("\\u000")
            }
            append(buff)
        }
    }

    @CreatorsDsl
    private fun StringBuilder.encode(code: Char): StringBuilder {
        return append(ESCAPE_SLASH).append(code)
    }

    @CreatorsDsl
    private fun StringBuilder.quoted(code: Boolean): StringBuilder {
        return if (code) append(DOUBLE_QUOTE) else this
    }

    @JvmStatic
    @CreatorsDsl
    fun isAscii(code: Int): Boolean {
        return if (code < ASCII_MINVAL || code > ASCII_MAXVAL) false else ASCII_TABLE[code]
    }

    @JvmStatic
    @CreatorsDsl
    fun isAscii(code: Char): Boolean {
        return isAscii(code.toInt())
    }

    @JvmStatic
    @CreatorsDsl
    fun isAscii(string: String): Boolean {
        val leng = string.length
        if (leng > 0) {
            for (loop in 0 until leng) {
                if (!isAscii(string[loop].toInt())) {
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
        val leng = string.length
        val buff = StringBuilder(leng * 2).quoted(quoted)
        for (loop in 0 until leng) {
            val code = string[loop]
            if (isAscii(code)) {
                buff.append(code) // ASCII will be most common, this improves write speed about 5%, FWIW.
                continue
            }
            val ints = code.toInt()
            when {
                ints > ASCII_MAXVAL -> {
                    buff.escape(ints)
                }
                ints < ASCII_MINVAL -> {
                    when (code) {
                        ASCII_BSCHAR -> buff.encode('b')
                        ASCII_NLCHAR -> buff.encode('n')
                        ASCII_VTCHAR -> buff.encode('t')
                        ASCII_CRCHAR -> buff.encode('r')
                        ASCII_FFCHAR -> buff.encode('f')
                        else -> buff.escape(ints)
                    }
                }
                else -> {
                    when (code) {
                        SINGLE_QUOTE -> buff.encode(SINGLE_QUOTE)
                        DOUBLE_QUOTE -> buff.encode(DOUBLE_QUOTE)
                        ESCAPE_SLASH -> buff.encode(ESCAPE_SLASH)
                        else -> buff.append(code)
                    }
                }
            }
        }
        return buff.quoted(quoted).toString()
    }
}