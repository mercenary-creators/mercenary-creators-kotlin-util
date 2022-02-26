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

enum class TimeDurationUnit(private val system: ChronoUnit) {

    @FrameworkDsl
    YEARS(ChronoUnit.YEARS),

    @FrameworkDsl
    WEEKS(ChronoUnit.WEEKS),

    @FrameworkDsl
    DAYS(ChronoUnit.DAYS),

    @FrameworkDsl
    HOURS(ChronoUnit.HOURS),

    @FrameworkDsl
    MINUTES(ChronoUnit.MINUTES),

    @FrameworkDsl
    SECONDS(ChronoUnit.SECONDS),

    @FrameworkDsl
    MILLISECONDS(ChronoUnit.MILLIS),

    @FrameworkDsl
    NANOSECONDS(ChronoUnit.NANOS);

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
    @JvmOverloads
    fun toLowerCase(full: Boolean = true) = if (full.isTrue()) lows else tail
}