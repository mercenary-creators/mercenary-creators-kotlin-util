/*
 * Copyright (c) 2023, Mercenary Creators Company. All rights reserved.
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

package co.mercenary.creators.kotlin.util.system

import co.mercenary.creators.kotlin.util.*

fun interface MemoryTypeFormatter {

    @FrameworkDsl
    operator fun invoke(size: Long): String

    companion object {

        @FrameworkDsl
        const val COMMA_CHAR = ','

        @FrameworkDsl
        const val START_CHAR = '('

        @FrameworkDsl
        const val CLOSE_CHAR = ')'

        @FrameworkDsl
        const val CLOSE_LINE = "$COMMA_CHAR$SPACE_STRING"

        @FrameworkDsl
        const val KILO_BYTES = 1000L

        @FrameworkDsl
        const val MEGA_BYTES = KILO_BYTES * 1000L

        @FrameworkDsl
        const val GIGA_BYTES = MEGA_BYTES * 1000L

        @FrameworkDsl
        const val TERA_BYTES = GIGA_BYTES * 1000L

        @JvmStatic
        @FrameworkDsl
        fun format(size: Long): String {
            return sized(stringBuilderOf(), size.maxOf(0L)).finish()
        }

        @FrameworkDsl
        internal fun StringBuilder.finish(): String {
            return removeSuffix(CLOSE_LINE).toLowerTrimEnglish()
        }

        @FrameworkDsl
        internal fun sized(builder: StringBuilder, size: Long): StringBuilder {
            when {
                size >= TERA_BYTES -> {
                    builder.add(START_CHAR, size / TERA_BYTES, SPACE_STRING, "TERA_BYTES", CLOSE_CHAR, CLOSE_LINE)
                    sized(builder, size % TERA_BYTES)
                }
                size >= GIGA_BYTES -> {
                    builder.add(START_CHAR, size / GIGA_BYTES, SPACE_STRING, "GIGA_BYTES", CLOSE_CHAR, CLOSE_LINE)
                    sized(builder, size % GIGA_BYTES)
                }
                size >= MEGA_BYTES -> {
                    builder.add(START_CHAR, size / MEGA_BYTES, SPACE_STRING, "MEGA_BYTES", CLOSE_CHAR, CLOSE_LINE)
                    sized(builder, size % MEGA_BYTES)
                }
                size >= KILO_BYTES -> {
                    builder.add(START_CHAR, size / KILO_BYTES, SPACE_STRING, "KILO_BYTES", CLOSE_CHAR, CLOSE_LINE)
                    sized(builder, size % KILO_BYTES)
                }
                size > 0 -> {
                    builder.add(START_CHAR, size, SPACE_STRING, "BYTES", CLOSE_CHAR, CLOSE_LINE)
                }
            }
            return builder
        }
    }
}