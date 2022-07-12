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

package co.mercenary.creators.kotlin.util.time

import co.mercenary.creators.kotlin.util.*
import java.time.Duration
import java.time.temporal.ChronoUnit

enum class TimeDurationUnit(private val system: ChronoUnit, private val mult: Long, private val flag: Boolean = false) {

    @FrameworkDsl
    YEARS(ChronoUnit.YEARS, 31556952L),

    @FrameworkDsl
    WEEKS(ChronoUnit.WEEKS, 7 * 86400L),

    @FrameworkDsl
    DAYS(ChronoUnit.DAYS, 86400L),

    @FrameworkDsl
    HOURS(ChronoUnit.HOURS, 3600),

    @FrameworkDsl
    MINUTES(ChronoUnit.MINUTES, 60),

    @FrameworkDsl
    SECONDS(ChronoUnit.SECONDS, 1),

    @FrameworkDsl
    MILLISECONDS(ChronoUnit.MILLIS, 1000000, true),

    @FrameworkDsl
    NANOSECONDS(ChronoUnit.NANOS, 1, true);

    @FrameworkDsl
    private val lows = name.toLowerCaseEnglish()

    @FrameworkDsl
    private val tail = name.toLowerCaseEnglish().tail()

    @FrameworkDsl
    fun next(): TimeDurationUnit? = when (this) {
        MILLISECONDS -> NANOSECONDS
        SECONDS -> MILLISECONDS
        MINUTES -> SECONDS
        HOURS -> MINUTES
        YEARS -> WEEKS
        WEEKS -> DAYS
        DAYS -> HOURS
        else -> null
    }

    @FrameworkDsl
    fun toSystemTimeUnit(): ChronoUnit = system

    @FrameworkDsl
    fun toSystemDuration(): Duration = system.duration

    @FrameworkDsl
    @IgnoreForSerialize
    fun getZeroDuration(): Duration = Duration.ZERO

    @FrameworkDsl
    @IgnoreForSerialize
    fun isNanoSeconds(): Boolean = flag

    @FrameworkDsl
    @IgnoreForSerialize
    fun getMultiplier(): Long = mult

    @FrameworkDsl
    fun toSystemDuration(time: Long): Duration {
        if (time.isZero()) {
            return getZeroDuration()
        }
        if (isNotEstimated()) {
            return Duration.of(time, toSystemTimeUnit())
        }
        return when (isNanoSeconds()) {
            true -> Duration.ofNanos(time multipliedBy getMultiplier())
            else -> Duration.ofSeconds(time multipliedBy getMultiplier())
        }
    }

    @FrameworkDsl
    @IgnoreForSerialize
    fun isEstimated(): Boolean = toSystemTimeUnit().isDurationEstimated

    @FrameworkDsl
    @IgnoreForSerialize
    fun isNotEstimated(): Boolean = isEstimated().isNotTrue()

    @FrameworkDsl
    infix fun isSameAs(unit: TimeDurationUnit): Boolean {
        return this == unit
    }

    @FrameworkDsl
    infix fun isNotSameAs(unit: TimeDurationUnit): Boolean = isSameAs(unit).isNotTrue()

    @FrameworkDsl
    fun toOrdinal(): Int {
        return ordinal.copyOf()
    }

    @FrameworkDsl
    fun toOrdinalLong(): Long {
        return toOrdinal().longOf()
    }

    @FrameworkDsl
    @JvmOverloads
    fun toLowerCase(full: Boolean = true) = if (full.isTrue()) lows else tail


}