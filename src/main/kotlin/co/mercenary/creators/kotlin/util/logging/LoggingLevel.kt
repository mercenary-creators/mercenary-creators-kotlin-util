/*
 * Copyright (c) 2021, Mercenary Creators Company. All rights reserved.
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
enum class LoggingLevel(value: Level) {

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

    @FrameworkDsl
    private val level = value

    @FrameworkDsl
    fun toLevel() = level

    @FrameworkDsl
    fun getValue() = toLevel().getValue()

    @FrameworkDsl
    override fun toString() = name.toUpperCaseEnglish()

    @FrameworkDsl
    operator fun compareTo(value: Int): Int {
        return getValue().compareTo(value)
    }

    @FrameworkDsl
    operator fun compareTo(value: Level): Int {
        return compareTo(value.getValue())
    }

    companion object {

        @FrameworkDsl
        fun Level.getValue(): Int = toInt()

        @JvmStatic
        @FrameworkDsl
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
        @FrameworkDsl
        fun from(level: Level) = from(level.getValue())

        @JvmStatic
        @FrameworkDsl
        fun from(level: CharSequence) = from(Level.toLevel(level.toValid()))
    }
}