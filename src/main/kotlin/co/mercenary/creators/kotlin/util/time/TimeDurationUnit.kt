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

package co.mercenary.creators.kotlin.util.time

enum class TimeDurationUnit {

    YEARS, WEEKS, DAYS, HOURS, MINUTES, SECONDS, MILLISECONDS, NANOSECONDS;

    fun next() = next(this)

    fun prev() = prev(this)

    fun toLowerCase() = name.toLowerCase()

    companion object {

        @JvmStatic
        private fun next(unit: TimeDurationUnit): TimeDurationUnit? = when (unit) {
            MILLISECONDS -> NANOSECONDS
            SECONDS -> MILLISECONDS
            MINUTES -> SECONDS
            HOURS -> MINUTES
            YEARS -> WEEKS
            WEEKS -> DAYS
            DAYS -> HOURS
            else -> null
        }

        @JvmStatic
        private fun prev(unit: TimeDurationUnit): TimeDurationUnit? = when (unit) {
            NANOSECONDS -> MILLISECONDS
            MILLISECONDS -> SECONDS
            SECONDS -> MINUTES
            MINUTES -> HOURS
            WEEKS -> YEARS
            DAYS -> WEEKS
            HOURS -> DAYS
            else -> null
        }
    }
}