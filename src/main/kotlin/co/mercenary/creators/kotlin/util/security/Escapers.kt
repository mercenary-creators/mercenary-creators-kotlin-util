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

package co.mercenary.creators.kotlin.util.security

import kotlin.math.*

object Escapers {

    const val HT = '\t'
    const val NL = '\n'
    const val BS = '\b'
    const val CR = '\r'
    const val SINGLE_SLASH = '/'
    const val SINGLE_QUOTE = '\''
    const val DOUBLE_QUOTE = '\"'
    const val ESCAPE_SLASH = '\\'

    @JvmStatic
    fun isAscii(c: Char): Boolean = (((c <= 'z') && (c >= ' ')) && ((c >= 'a') || ((c >= 'A') && (c <= 'Z')) || (c == ' ') || ((c >= '0') && (c <= '9'))))

    @JvmStatic
    fun escape(value: String?, quote: Boolean = true): String {
        if (value == null) {
            return "null"
        }
        val builder = StringBuilder(value.length)
        for (c in value) {
            val i = c.toInt()
            when {
                isAscii(c) -> builder.append(c)
                i > 0x7f -> unicode(builder, i)
                i < 32 -> {
                    when (c) {
                        NL -> builder.append(ESCAPE_SLASH).append('n')
                        HT -> builder.append(ESCAPE_SLASH).append('t')
                        BS -> builder.append(ESCAPE_SLASH).append('b')
                        CR -> builder.append(ESCAPE_SLASH).append('r')
                        else -> unicode(builder, i)
                    }
                }
                else -> {
                    when (c) {
                        SINGLE_SLASH -> builder.append(ESCAPE_SLASH).append(SINGLE_SLASH)
                        DOUBLE_QUOTE -> builder.append(ESCAPE_SLASH).append(DOUBLE_QUOTE)
                        SINGLE_QUOTE -> builder.append(ESCAPE_SLASH).append(SINGLE_QUOTE)
                        ESCAPE_SLASH -> builder.append(ESCAPE_SLASH).append(ESCAPE_SLASH)
                        else -> builder.append(c)
                    }
                }
            }
        }
        if (quote) {
            builder.insert(0, DOUBLE_QUOTE).append(DOUBLE_QUOTE)
        }
        return builder.toString()
    }

    internal fun unicode(buff: StringBuilder, data: Int) {
        val text = data.toString(16).toUpperCase()
        when (min(max(4 - text.length, 0), 3)) {
            0 -> buff.append("\\u")
            1 -> buff.append("\\u0")
            2 -> buff.append("\\u00")
            else -> buff.append("\\u000")
        }
        buff.append(text)
    }
}