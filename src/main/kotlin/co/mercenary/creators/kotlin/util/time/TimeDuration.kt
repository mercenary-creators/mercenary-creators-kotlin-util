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

    constructor(time: CharSequence) : this(parseCharSequence(time))

    constructor(time: TimeDuration) : this(time.duration(), time.unit())

    fun unit() = unit

    fun duration() = copyOf(time)

    operator fun plus(other: TimeDuration): TimeDuration = TimeDuration(duration().plus(other.time), unit())

    operator fun minus(other: TimeDuration): TimeDuration = TimeDuration(duration().minus(other.time), unit())

    operator fun div(other: Int): TimeDuration = div(other.toLong())

    operator fun div(other: Long): TimeDuration = TimeDuration(duration().dividedBy(other), unit())

    operator fun times(other: Int): TimeDuration = times(other.toLong())

    operator fun times(other: Long): TimeDuration = TimeDuration(duration().multipliedBy(other), unit())

    override operator fun compareTo(other: TimeDuration): Int {
        return time.compareTo(other.time)
    }

    override fun clone(): Any {
        return copyOf()
    }

    override fun copyOf(): TimeDuration {
        return TimeDuration(this)
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
        return toTrimOrElse(text(duration(), TimeDurationUnit.YEARS, true)) {
            text(unit())
        }
    }

    companion object {

        const val DAYS_PER_WEEK = 7L

        const val HOURS_PER_DAY = 24L

        const val DAYS_PER_YEAR = 365L

        const val MINUTES_PER_HOUR = 60L

        const val SECONDS_PER_MINUTE = 60L

        const val MILLISECONDS_PER_SECOND = 1000L

        private val look = Regex("\\h+")

        private val maps = mutableMapOf<Int, TimeDurationUnit>()

        init {
            TimeDurationUnit.values().forEach {
                maps[it.ordinal] = it
            }
        }

        private fun copyOf(time: Duration): Duration = Duration.ofSeconds(time.seconds, time.nano.toLong())

        private fun isEmpty(time: Duration): Boolean {
            return time.isZero.or(time.isNegative)
        }

        private fun text(unit: TimeDurationUnit, time: Long = 0): String {
            return buildString(32) {
                append(time).append(SPACE_STRING).append(name(unit.name.toLowerCase(), time))
            }
        }

        private fun name(name: String, time: Long): String {
            return if (abs(time) != 1L) name else name.removeSuffix("s")
        }

        private fun text(time: Duration, unit: TimeDurationUnit, part: Boolean): String {
            if (isEmpty(time)) {
                return if (part) EMPTY_STRING else text(unit)
            }
            var copy = time
            val save = when (unit) {
                TimeDurationUnit.DAYS -> copy.toDays().also {
                    if (part.and(it > 0)) {
                        copy = copy.minusDays(it)
                    }
                }
                TimeDurationUnit.HOURS -> copy.toHours().also {
                    if (part.and(it > 0)) {
                        copy = copy.minusHours(it)
                    }
                }
                TimeDurationUnit.WEEKS -> copy.toDays().div(DAYS_PER_WEEK).also {
                    if (part.and(it > 0)) {
                        copy = copy.minusDays(it.times(DAYS_PER_WEEK))
                    }
                }
                TimeDurationUnit.YEARS -> copy.toDays().div(DAYS_PER_YEAR).also {
                    if (part.and(it > 0)) {
                        copy = copy.minusDays(it.times(DAYS_PER_YEAR))
                    }
                }
                TimeDurationUnit.MINUTES -> copy.toMinutes().also {
                    if (part.and(it > 0)) {
                        copy = copy.minusMinutes(it)
                    }
                }
                TimeDurationUnit.SECONDS -> copy.seconds.also {
                    if (part.and(it > 0)) {
                        copy = copy.minusSeconds(it)
                    }
                }
                TimeDurationUnit.NANOSECONDS -> copy.nano.toLong().also {
                    if (part.and(it > 0)) {
                        copy = copy.minusNanos(it)
                    }
                }
                TimeDurationUnit.MILLISECONDS -> copy.toMillis().also {
                    if (part.and(it > 0)) {
                        copy = copy.minusMillis(it)
                    }
                }
            }
            if (part) {
                return when (val look = maps[unit.ordinal.plus(1)]) {
                    null -> when ((unit == TimeDurationUnit.NANOSECONDS).and(save > 0)) {
                        true -> text(unit, save)
                        else -> EMPTY_STRING
                    }
                    else -> when (save < 1) {
                        true -> text(copy, look, part)
                        else -> text(unit, save).plus(SPACE_STRING).plus(text(copy, look, part))
                    }
                }
            }
            return text(unit, save)
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
        fun years(time: Long) = TimeDuration(Duration.ofDays(time * DAYS_PER_YEAR), TimeDurationUnit.YEARS)

        @JvmStatic
        fun weeks(time: Long) = TimeDuration(Duration.ofDays(time * DAYS_PER_WEEK), TimeDurationUnit.WEEKS)

        @JvmStatic
        fun days(time: Long) = TimeDuration(Duration.ofDays(time), TimeDurationUnit.DAYS)

        @JvmStatic
        fun hours(time: Long) = TimeDuration(Duration.ofHours(time), TimeDurationUnit.HOURS)

        @JvmStatic
        fun minutes(time: Long) = TimeDuration(Duration.ofMinutes(time), TimeDurationUnit.MINUTES)

        @JvmStatic
        fun seconds(time: Long) = TimeDuration(Duration.ofSeconds(time), TimeDurationUnit.SECONDS)

        @JvmStatic
        fun nanoseconds(time: Long) = TimeDuration(Duration.ofNanos(time), TimeDurationUnit.NANOSECONDS)

        @JvmStatic
        fun milliseconds(time: Long) = TimeDuration(Duration.ofMillis(time), TimeDurationUnit.MILLISECONDS)

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
            less(data)
            return TimeDuration(data.time, data.unit)
        }

        private fun less(data: TimeData) {
            val time = data.time
            if (isEmpty(time)) {
                return
            }
            val days = time.toDays()
            when {
                days > 0 -> when {
                    days >= DAYS_PER_YEAR -> data.unit = TimeDurationUnit.YEARS
                    days >= DAYS_PER_WEEK -> data.unit = TimeDurationUnit.WEEKS
                    else -> data.unit = TimeDurationUnit.DAYS
                }
                time.toHours() > 0 -> data.unit = TimeDurationUnit.HOURS
                time.toMinutes() > 0 -> data.unit = TimeDurationUnit.MINUTES
                time.seconds > 0 -> data.unit = TimeDurationUnit.SECONDS
                time.toMillis() > 0 -> data.unit = TimeDurationUnit.MILLISECONDS
                time.nano > 0 -> data.unit = TimeDurationUnit.NANOSECONDS
            }
        }

        private fun make(buff: String, unit: String, data: TimeData) {
            val time = data.time
            val plus = buff.toLongOrNull() ?: throw MercenaryFatalExceptiion("invalid time $buff")
            when (unit) {
                "day", "days" -> data(plus, time::plusDays, TimeDurationUnit.DAYS)
                "year", "years" -> data(plus.times(DAYS_PER_YEAR), time::plusDays, TimeDurationUnit.YEARS)
                "week", "weeks" -> data(plus.times(DAYS_PER_WEEK), time::plusDays, TimeDurationUnit.WEEKS)
                "hour", "hours" -> data(plus, time::plusHours, TimeDurationUnit.HOURS)
                "minute", "minutes" -> data(plus, time::plusMinutes, TimeDurationUnit.MINUTES)
                "second", "seconds" -> data(plus, time::plusSeconds, TimeDurationUnit.SECONDS)
                "nanosecond", "nanoseconds" -> data(plus, time::plusNanos, TimeDurationUnit.NANOSECONDS)
                "millisecond", "milliseconds" -> data(plus, time::plusMillis, TimeDurationUnit.MILLISECONDS)
                else -> throw MercenaryFatalExceptiion("invalid unit $unit")
            }
        }

        private class TimeData(var time: Duration = Duration.ZERO, var unit: TimeDurationUnit = TimeDurationUnit.NANOSECONDS) {
            operator fun invoke(plus: Long, make: (Long) -> Duration, less: TimeDurationUnit) {
                time = make(plus)
                if (less < unit) {
                    unit = less
                }
            }
        }
    }
}