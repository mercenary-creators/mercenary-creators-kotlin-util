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

package co.mercenary.creators.kotlin.util.logging

import ch.qos.logback.classic.Level
import co.mercenary.creators.kotlin.util.*

@IgnoreForSerialize
enum class LoggingLevel(private val value: Level) {

    @CreatorsDsl
    OFF(Level.OFF),

    @CreatorsDsl
    ALL(Level.ALL),

    @CreatorsDsl
    TRACE(Level.TRACE),

    @CreatorsDsl
    DEBUG(Level.DEBUG),

    @LoggingInfoDsl
    INFO(Level.INFO),

    @LoggingWarnDsl
    WARN(Level.WARN),

    @CreatorsDsl
    ERROR(Level.ERROR);

    @CreatorsDsl
    fun toLevel() = value

    @CreatorsDsl
    fun toInteger() = toLevel().toInt()

    @CreatorsDsl
    fun isEqualTo(level: LoggingLevel) = toInteger() isSameAs level.toInteger()

    @CreatorsDsl
    fun isNotEqualTo(level: LoggingLevel) = toInteger() isNotSameAs level.toInteger()

    @CreatorsDsl
    fun isLessThan(level: LoggingLevel) = toInteger() < level.toInteger()

    @CreatorsDsl
    fun isLessThanOrEqual(level: LoggingLevel) = toInteger() <= level.toInteger()

    @CreatorsDsl
    fun isMoreThan(level: LoggingLevel) = toInteger() > level.toInteger()

    @CreatorsDsl
    fun isMoreThanOrEqual(level: LoggingLevel) = toInteger() >= level.toInteger()

    @CreatorsDsl
    override fun toString() = name.toUpperCaseEnglish()

    companion object {

        @JvmStatic
        @CreatorsDsl
        fun from(level: Int) = when (level) {
            Level.OFF_INT -> OFF
            Level.ALL_INT -> ALL
            Level.INFO_INT -> INFO
            Level.WARN_INT -> WARN
            Level.DEBUG_INT -> DEBUG
            Level.TRACE_INT -> TRACE
            Level.ERROR_INT -> ERROR
            else -> DEBUG
        }

        @JvmStatic
        @CreatorsDsl
        fun from(level: Level) = from(level.toInt())

        @JvmStatic
        @CreatorsDsl
        fun from(level: String) = from(Level.valueOf(level))
    }
}