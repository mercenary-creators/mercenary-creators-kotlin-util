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
import co.mercenary.creators.kotlin.util.type.Copyable
import java.time.Duration
import kotlin.math.abs

class TimeDuration private constructor(private val time: Duration, private val unit: TimeDurationUnit) : Comparable<TimeDuration>, Copyable<TimeDuration>, Cloneable {

    fun toUnit() = unit

    fun toDuration() = copyOf(time)

    operator fun plus(other: TimeDuration): TimeDuration {
        val make = copyOf(time).plus(other.time)
        return TimeDuration(make, less(make, unit))
    }

    operator fun minus(other: TimeDuration): TimeDuration {
        val make = copyOf(time).minus(other.time)
        return TimeDuration(make, less(make, unit))
    }

    operator fun div(other: Int): TimeDuration = div(other.toLong())

    operator fun div(other: Long): TimeDuration {
        if (other == 0L) {
            throw MercenaryFatalExceptiion(MATH_ZERO_DIVISOR_ERROR)
        }
        val make = copyOf(time).dividedBy(other)
        return TimeDuration(make, less(make, unit))
    }

    operator fun times(other: Int): TimeDuration = times(other.toLong())

    operator fun times(other: Long): TimeDuration {
        val make = copyOf(time).multipliedBy(other)
        return TimeDuration(make, less(make, unit))
    }

    override operator fun compareTo(other: TimeDuration): Int {
        return time.compareTo(other.time)
    }

    override fun clone(): Any {
        return copyOf()
    }

    override fun copyOf(): TimeDuration {
        return TimeDuration(copyOf(time), unit)
    }

    override fun equals(other: Any?): Boolean {
        return when (other) {
            is TimeDuration -> time == other.time
            else -> false
        }
    }

    override fun hashCode(): Int {
        return time.hashCode()
    }

    override fun toString(): String {
        return toTrimOrElse(text(copyOf(time), unit)) {
            text(unit)
        }
    }

    companion object {

        const val DAYS_PER_WEEK = 7L

        const val DAYS_PER_YEAR = 365L

        private val look = Regex("\\h+")

        private fun copyOf(time: Duration): Duration = Duration.ofSeconds(time.seconds, time.nano.toLong())

        private fun isEmpty(time: Duration): Boolean {
            return time.isZero.or(time.isNegative)
        }

        private fun text(unit: TimeDurationUnit, time: Long = 0): String {
            return "$time ${name(unit.toLowerCase(), time)}"
        }

        private fun name(name: String, time: Long): String {
            return if (abs(time) != 1L) name else name.removeSuffix("s")
        }

        private fun text(time: Duration, unit: TimeDurationUnit): String {
            if (isEmpty(time)) {
                return EMPTY_STRING
            }
            var copy = time
            val save = when (unit) {
                TimeDurationUnit.DAYS -> copy.toDays().also {
                    if (it > 0) {
                        copy = copy.minusDays(it)
                    }
                }
                TimeDurationUnit.HOURS -> copy.toHours().also {
                    if (it > 0) {
                        copy = copy.minusHours(it)
                    }
                }
                TimeDurationUnit.WEEKS -> copy.toDays().div(DAYS_PER_WEEK).also {
                    if (it > 0) {
                        copy = copy.minusDays(it.times(DAYS_PER_WEEK))
                    }
                }
                TimeDurationUnit.YEARS -> copy.toDays().div(DAYS_PER_YEAR).also {
                    if (it > 0) {
                        copy = copy.minusDays(it.times(DAYS_PER_YEAR))
                    }
                }
                TimeDurationUnit.MINUTES -> copy.toMinutes().also {
                    if (it > 0) {
                        copy = copy.minusMinutes(it)
                    }
                }
                TimeDurationUnit.SECONDS -> copy.seconds.also {
                    if (it > 0) {
                        copy = copy.minusSeconds(it)
                    }
                }
                TimeDurationUnit.NANOSECONDS -> copy.nano.toLong().also {
                    if (it > 0) {
                        copy = copy.minusNanos(it)
                    }
                }
                TimeDurationUnit.MILLISECONDS -> copy.toMillis().also {
                    if (it > 0) {
                        copy = copy.minusMillis(it)
                    }
                }
            }
            return when (val next = unit.next()) {
                null -> when ((unit == TimeDurationUnit.NANOSECONDS).and(save > 0)) {
                    true -> text(unit, save)
                    else -> EMPTY_STRING
                }
                else -> when (save < 1) {
                    true -> text(copy, next)
                    else -> text(unit, save).plus(SPACE_STRING).plus(text(copy, next))
                }
            }
        }

        @JvmStatic
        fun days(time: Int) = days(time.toLong())

        @JvmStatic
        fun weeks(time: Int) = weeks(time.toLong())

        @JvmStatic
        fun years(time: Int) = years(time.toLong())

        @JvmStatic
        fun hours(time: Int) = hours(time.toLong())

        @JvmStatic
        fun minutes(time: Int) = minutes(time.toLong())

        @JvmStatic
        fun seconds(time: Int) = seconds(time.toLong())

        @JvmStatic
        fun nanoseconds(time: Int) = nanoseconds(time.toLong())

        @JvmStatic
        fun milliseconds(time: Int) = milliseconds(time.toLong())

        @JvmStatic
        fun years(time: Long): TimeDuration {
            val make = Duration.ofDays(time * DAYS_PER_YEAR)
            return TimeDuration(make, less(make, TimeDurationUnit.YEARS))
        }

        @JvmStatic
        fun weeks(time: Long): TimeDuration {
            val make = Duration.ofDays(time * DAYS_PER_WEEK)
            return TimeDuration(make, less(make, TimeDurationUnit.WEEKS))
        }

        @JvmStatic
        fun days(time: Long): TimeDuration {
            val make = Duration.ofDays(time)
            return TimeDuration(make, less(make, TimeDurationUnit.DAYS))
        }

        @JvmStatic
        fun hours(time: Long): TimeDuration {
            val make = Duration.ofHours(time)
            return TimeDuration(make, less(make, TimeDurationUnit.HOURS))
        }

        @JvmStatic
        fun minutes(time: Long): TimeDuration {
            val make = Duration.ofMinutes(time)
            return TimeDuration(make, less(make, TimeDurationUnit.MINUTES))
        }

        @JvmStatic
        fun seconds(time: Long): TimeDuration {
            val make = Duration.ofSeconds(time)
            return TimeDuration(make, less(make, TimeDurationUnit.SECONDS))
        }

        @JvmStatic
        fun nanoseconds(time: Long): TimeDuration {
            val make = Duration.ofNanos(time)
            return TimeDuration(make, less(make, TimeDurationUnit.NANOSECONDS))
        }

        @JvmStatic
        fun milliseconds(time: Long): TimeDuration {
            val make = Duration.ofMillis(time)
            return TimeDuration(make, less(make, TimeDurationUnit.MILLISECONDS))
        }

        @JvmStatic
        fun parseCharSequence(text: CharSequence): TimeDuration {
            val list = text.toLowerTrim().split(look)
            val size = list.size
            if ((size == 0).or(size.rem(2) == 1)) {
                throw MercenaryFatalExceptiion("invalid size $size")
            }
            val data = TimeData()
            for (i in 0 until size step 2) {
                make(list[i], list[i + 1], data)
            }
            return TimeDuration(data.time, data.unit)
        }

        private fun less(time: Duration, unit: TimeDurationUnit): TimeDurationUnit {
            if (isEmpty(time)) {
                return unit
            }
            val days = time.toDays()
            return when {
                days > 0 -> when {
                    days >= DAYS_PER_YEAR -> TimeDurationUnit.YEARS
                    days >= DAYS_PER_WEEK -> TimeDurationUnit.WEEKS
                    else -> TimeDurationUnit.DAYS
                }
                time.toHours() > 0 -> TimeDurationUnit.HOURS
                time.toMinutes() > 0 -> TimeDurationUnit.MINUTES
                time.seconds > 0 -> TimeDurationUnit.SECONDS
                time.toMillis() > 0 -> TimeDurationUnit.MILLISECONDS
                else -> TimeDurationUnit.NANOSECONDS
            }
        }

        private fun make(buff: String, unit: String, data: TimeData) {
            val time = data.time
            val plus = buff.toLongOrNull() ?: throw MercenaryFatalExceptiion("invalid time $buff")
            when (unit) {
                "day", "days" -> data.make(plus, time::plusDays, TimeDurationUnit.DAYS)
                "hour", "hours" -> data.make(plus, time::plusHours, TimeDurationUnit.HOURS)
                "minute", "minutes" -> data.make(plus, time::plusMinutes, TimeDurationUnit.MINUTES)
                "second", "seconds" -> data.make(plus, time::plusSeconds, TimeDurationUnit.SECONDS)
                "year", "years" -> data.make(plus * DAYS_PER_YEAR, time::plusDays, TimeDurationUnit.YEARS)
                "week", "weeks" -> data.make(plus * DAYS_PER_WEEK, time::plusDays, TimeDurationUnit.WEEKS)
                "nanosecond", "nanoseconds" -> data.make(plus, time::plusNanos, TimeDurationUnit.NANOSECONDS)
                "millisecond", "milliseconds" -> data.make(plus, time::plusMillis, TimeDurationUnit.MILLISECONDS)
                else -> throw MercenaryFatalExceptiion("invalid unit $unit")
            }
        }

        private class TimeData(var time: Duration = Duration.ZERO, var unit: TimeDurationUnit = TimeDurationUnit.NANOSECONDS) {
            inline fun make(plus: Long, block: (Long) -> Duration, less: TimeDurationUnit) {
                if (less < unit) {
                    unit = less
                }
                time = block.invoke(plus)
            }
        }
    }
}