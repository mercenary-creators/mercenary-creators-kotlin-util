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

package co.mercenary.creators.kotlin.util.type

object Escapers {

    private const val ESCAPE_SLASH = '\\'

    private const val SINGLE_SLASH = '/'

    private const val SINGLE_QUOTE = '\''

    private const val DOUBLE_QUOTE = '"'

    private fun StringBuilder.escape(c: Char) {
        val string = c.toInt().toString(16).toUpperCase()
        when ((4 - string.length).coerceAtLeast(0).coerceAtMost(3)) {
            0 -> append("\\u").append(string)
            1 -> append("\\u0").append(string)
            2 -> append("\\u00").append(string)
            else -> append("\\u000").append(string)
        }
    }

    @JvmStatic
    @JvmOverloads
    fun toEscapedString(string: String, quote: Boolean = false): String {
        val leng = string.length
        val buff = StringBuilder(leng * 2)
        for (n in 0 until leng) {
            val c = string[n]
            if (c in ' '..'z' && (c >= 'a' || c in 'A'..'Z' || c == ' ' || c in '0'..'9')) {
                buff.append(c) // ASCII will be most common, this improves write speed about 5%, FWIW.
            }
            else {
                when {
                    c.toInt() > 0x7f -> {
                        buff.escape(c)
                    }
                    c.toInt() < 32 -> {
                        when (c) {
                            '\b' -> buff.append(ESCAPE_SLASH).append('b')
                            '\n' -> buff.append(ESCAPE_SLASH).append('n')
                            '\t' -> buff.append(ESCAPE_SLASH).append('t')
                            '\r' -> buff.append(ESCAPE_SLASH).append('r')
                            else -> buff.escape(c)
                        }
                    }
                    else -> {
                        when (c) {
                            SINGLE_QUOTE -> buff.append(ESCAPE_SLASH).append(SINGLE_QUOTE)
                            DOUBLE_QUOTE -> buff.append(ESCAPE_SLASH).append(DOUBLE_QUOTE)
                            ESCAPE_SLASH -> buff.append(ESCAPE_SLASH).append(ESCAPE_SLASH)
                            SINGLE_SLASH -> buff.append(ESCAPE_SLASH).append(SINGLE_SLASH)
                            else -> buff.append(c)
                        }
                    }
                }
            }
        }
        if (quote) {
            buff.insert(0, DOUBLE_QUOTE).append(DOUBLE_QUOTE)
        }
        return buff.toString()
    }
}