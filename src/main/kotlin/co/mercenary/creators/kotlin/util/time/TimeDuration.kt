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

import co.mercenary.creators.kotlin.util.*
import java.time.Duration

open class TimeDuration(val duration: Duration, val unit: TimeDurationUnit) : Comparable<TimeDuration> {

    operator fun plus(other: TimeDuration): TimeDuration = TimeDuration(copyOf(duration).plus(other.duration), unit)

    operator fun minus(other: TimeDuration): TimeDuration = TimeDuration(copyOf(duration).minus(other.duration), unit)

    operator fun div(other: Int): TimeDuration = div(other.toLong())

    operator fun div(other: Long): TimeDuration = TimeDuration(copyOf(duration).dividedBy(other), unit)

    operator fun times(other: Int): TimeDuration = times(other.toLong())

    operator fun times(other: Long): TimeDuration = TimeDuration(copyOf(duration).multipliedBy(other), unit)

    operator fun unaryPlus(): TimeDuration = TimeDuration(copyOf(duration).abs(), unit)

    operator fun unaryMinus(): TimeDuration = TimeDuration(copyOf(duration).negated(), unit)

    override operator fun compareTo(other: TimeDuration): Int {
        return duration.compareTo(other.duration)
    }

    override fun equals(other: Any?): Boolean {
        return when (other) {
            is TimeDuration -> duration == other.duration
            else -> false
        }
    }

    override fun hashCode(): Int {
        return duration.hashCode()
    }

    override fun toString(): String {
        return twxt(duration, unit)
    }

    companion object {

        private val name = mutableMapOf<TimeDurationUnit, String>()

        init {
            TimeDurationUnit.values().forEach {
                name[it] = it.name.toLowerCase().capitalize()
            }
        }

        private fun name(unit: TimeDurationUnit): String {
            return name[unit].orElse { "Unknown" }
        }

        private fun twxt(unit: TimeDurationUnit, time: Long = 0): String {
            return time.toString().plus(SPACE_STRING).plus(name(unit))
        }

        private fun twxt(time: Duration, unit: TimeDurationUnit): String {
            val save = when (unit) {
                TimeDurationUnit.DAYS -> time.toDays()
                TimeDurationUnit.HOURS -> time.toHours()
                TimeDurationUnit.WEEKS -> time.toDays().div(7L)
                TimeDurationUnit.YEARS -> time.toDays().div(365L)
                TimeDurationUnit.MINUTES -> time.toMinutes()
                TimeDurationUnit.SECONDS -> time.seconds
                TimeDurationUnit.NANOSECONDS -> time.seconds.times(1000_000_000L).plus(time.nano)
                TimeDurationUnit.MILLISECONDS -> time.toMillis()
            }
            return twxt(unit, save)
        }

        @JvmStatic
        fun weeks(time: Int) = TimeDuration(Duration.ofDays(time * 7L), TimeDurationUnit.WEEKS)

        @JvmStatic
        fun years(time: Int) = TimeDuration(Duration.ofDays(time * 365L), TimeDurationUnit.YEARS)

        @JvmStatic
        fun days(time: Int) = TimeDuration(Duration.ofDays(time.toLong()), TimeDurationUnit.DAYS)

        @JvmStatic
        fun hours(time: Int) = TimeDuration(Duration.ofHours(time.toLong()), TimeDurationUnit.HOURS)

        @JvmStatic
        fun minutes(time: Int) = TimeDuration(Duration.ofMinutes(time.toLong()), TimeDurationUnit.MINUTES)

        @JvmStatic
        fun seconds(time: Int) = TimeDuration(Duration.ofSeconds(time.toLong()), TimeDurationUnit.SECONDS)

        @JvmStatic
        fun nanoseconds(time: Int) = TimeDuration(Duration.ofNanos(time.toLong()), TimeDurationUnit.NANOSECONDS)

        @JvmStatic
        fun milliseconds(time: Int) = TimeDuration(Duration.ofMillis(time.toLong()), TimeDurationUnit.MILLISECONDS)
    }
}