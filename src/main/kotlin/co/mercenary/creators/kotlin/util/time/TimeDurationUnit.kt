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

package co.mercenary.creators.kotlin.util.time

import co.mercenary.creators.kotlin.util.*

enum class TimeDurationUnit {

    @CreatorsDsl
    YEARS,

    @CreatorsDsl
    WEEKS,

    @CreatorsDsl
    DAYS,

    @CreatorsDsl
    HOURS,

    @CreatorsDsl
    MINUTES,

    @CreatorsDsl
    SECONDS,

    @CreatorsDsl
    MILLISECONDS,

    @CreatorsDsl
    NANOSECONDS;

    @CreatorsDsl
    private val lows = name.toLowerTrimEnglish()

    @CreatorsDsl
    private val tail = name.toLowerTrimEnglish().tail()

    @CreatorsDsl
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

    @CreatorsDsl
    @JvmOverloads
    fun toLowerCase(full: Boolean = true) = if (full) lows else tail
}