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

object Escapers {

    private const val ESCAPE_SLASH = '\\'

    private const val SINGLE_QUOTE = '\''

    private const val DOUBLE_QUOTE = '"'

    private val INTS = intArrayOf(33, 35, 36, 37, 38, 40, 41, 42, 43, 44, 45, 46)

    private val LOOK = BooleanArray(128) { code ->
        (code in 32..126 && (code >= 93 || code in 48..91 || code == 32 || code in INTS))
    }

    private fun StringBuilder.escape(code: Int) {
        val string = code.toString(16).toUpperCase()
        when ((4 - string.length).coerceIn(0, 3)) {
            0 -> append("\\u")
            1 -> append("\\u0")
            2 -> append("\\u00")
            3 -> append("\\u000")
        }
        append(string)
    }

    @JvmStatic
    private fun isAscii(code: Char): Boolean {
        val look = code.toInt()
        if (look < 32 || look > 127) {
            return false
        }
        return LOOK[look]
    }

    @JvmStatic
    private fun isAscii(string: String): Boolean {
        if (string.isEmpty()) {
            return true
        }
        for (c in string) {
            if (!isAscii(c)) {
                return false
            }
        }
        return true
    }

    @JvmStatic
    @JvmOverloads
    fun toEscapedString(string: String, quote: Boolean = false): String {
        if (isAscii(string)) {
            return if (quote) "\"$string\"" else string
        }
        val leng = string.length
        val buff = StringBuilder(leng * 2).also { if (quote) it.append(DOUBLE_QUOTE) }
        for (n in 0 until leng) {
            val c = string[n]
            if (isAscii(c)) {
                buff.append(c) // ASCII will be most common, this improves write speed about 5%, FWIW.
                continue
            }
            val i = c.toInt()
            when {
                i > 127 -> {
                    buff.escape(i)
                }
                i == 12 -> {
                    buff.append(ESCAPE_SLASH).append('f')
                }
                i < 32 -> {
                    when (c) {
                        '\b' -> buff.append(ESCAPE_SLASH).append('b')
                        '\n' -> buff.append(ESCAPE_SLASH).append('n')
                        '\t' -> buff.append(ESCAPE_SLASH).append('t')
                        '\r' -> buff.append(ESCAPE_SLASH).append('r')
                        else -> buff.escape(i)
                    }
                }
                else -> {
                    when (c) {
                        SINGLE_QUOTE -> buff.append(ESCAPE_SLASH).append(SINGLE_QUOTE)
                        DOUBLE_QUOTE -> buff.append(ESCAPE_SLASH).append(DOUBLE_QUOTE)
                        ESCAPE_SLASH -> buff.append(ESCAPE_SLASH).append(ESCAPE_SLASH)
                        else -> buff.append(c)
                    }
                }
            }
        }
        if (quote) {
            buff.append(DOUBLE_QUOTE)
        }
        return buff.toString()
    }
}