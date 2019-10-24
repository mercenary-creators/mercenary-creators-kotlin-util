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
import co.mercenary.creators.kotlin.util.Throwables
import co.mercenary.creators.kotlin.util.type.*
import java.time.Duration
import kotlin.math.abs

class TimeDuration @JvmOverloads constructor(val duration: Duration, private val unit: TimeDurationUnit, private val high: TimeDurationUnit = unit) : Comparable<TimeDuration>, Copyable<TimeDuration>, Validated {

    operator fun plus(other: TimeDuration): TimeDuration = TimeDuration(copyOf(duration).plus(other.duration), high(high(unit, high), other.high))

    operator fun minus(other: TimeDuration): TimeDuration = TimeDuration(copyOf(duration).minus(other.duration), high(high(unit, high), other.high))

    operator fun div(other: Int): TimeDuration = div(other.toLong())

    operator fun div(other: Long): TimeDuration = TimeDuration(copyOf(duration).dividedBy(other), unit, high)

    operator fun times(other: Int): TimeDuration = times(other.toLong())

    operator fun times(other: Long): TimeDuration = TimeDuration(copyOf(duration).multipliedBy(other), unit, high)

    operator fun unaryPlus(): TimeDuration = TimeDuration(copyOf(duration).abs(), unit, high)

    operator fun unaryMinus(): TimeDuration = TimeDuration(copyOf(duration).negated(), unit, high)

    override operator fun compareTo(other: TimeDuration): Int {
        return duration.compareTo(other.duration)
    }

    override fun copyOf(): TimeDuration {
        return TimeDuration(copyOf(duration), unit, high)
    }

    override fun isValid(): Boolean {
        return isEmpty(duration).not()
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
        return text(duration, unit, false).trim()
    }

    fun parts(): String {
        return toTrimOrElse(text(duration, high, true)) {
            text(unit)
        }
    }

    fun sleepFor() {
        if (isEmpty(duration)) {
            return
        }
        try {
            Thread.sleep(duration.toMillis(), duration.nano.rem(1000000))
        }
        catch (cause: Throwable) {
            Throwables.thrown(cause)
        }
    }

    companion object {

        private const val DAYS_PER_WEEK = 7L

        private const val DAYS_PER_YEAR = 365L

        private val next = mutableMapOf<Int, TimeDurationUnit>()

        init {
            TimeDurationUnit.values().forEach {
                next[it.ordinal] = it
            }
        }

        private fun copyOf(time: Duration): Duration = Duration.ofSeconds(time.seconds, time.nano.toLong())

        private fun isEmpty(time: Duration): Boolean {
            return (time.seconds == 0L).and(time.nano == 0).or(time.isNegative)
        }

        private fun next(unit: TimeDurationUnit): TimeDurationUnit? {
            return next[unit.ordinal.plus(1)]
        }

        private fun high(unit: TimeDurationUnit, high: TimeDurationUnit): TimeDurationUnit {
            return if (high < unit) high else unit
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
            var copy = copyOf(time)
            val save = when (unit) {
                TimeDurationUnit.DAYS -> copy.toDays().also { copy = copy.minusDays(it) }
                TimeDurationUnit.HOURS -> copy.toHours().also { copy = copy.minusHours(it) }
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
                TimeDurationUnit.MINUTES -> copy.toMinutes().also { copy = copy.minusMinutes(it) }
                TimeDurationUnit.SECONDS -> copy.seconds.also { copy = copy.minusSeconds(it) }
                TimeDurationUnit.NANOSECONDS -> copy.nano.toLong().also { copy = copy.minusNanos(it) }
                TimeDurationUnit.MILLISECONDS -> copy.toMillis().also { copy = copy.minusMillis(it) }
            }
            if (part) {
                return when (val look = next(unit)) {
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
        fun weeks(time: Int) = weeks(time.toLong())

        @JvmStatic
        fun years(time: Int) = years(time.toLong())

        @JvmStatic
        fun days(time: Int) = days(time.toLong())

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
        fun weeks(time: Long) = TimeDuration(Duration.ofDays(time * DAYS_PER_WEEK), TimeDurationUnit.WEEKS)

        @JvmStatic
        fun years(time: Long) = TimeDuration(Duration.ofDays(time * DAYS_PER_YEAR), TimeDurationUnit.YEARS)

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
    }
}