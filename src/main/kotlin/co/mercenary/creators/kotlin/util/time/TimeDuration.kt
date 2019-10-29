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
import kotlin.math.abs

class TimeDuration(private val duration: Duration, private val unit: TimeDurationUnit) : Comparable<TimeDuration>, Cloneable {

    constructor(time: String) : this(parseCharSequence(time))

    constructor(time: TimeDuration) : this(copyOf(time.duration), time.unit)

    fun toDuration() = copyOf(duration)

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

    fun copyOf(): TimeDuration {
        return TimeDuration(copyOf(duration), unit)
    }

    override fun clone(): Any {
        return TimeDuration(copyOf(duration), unit)
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
        return text(copyOf(duration), unit, false).trim()
    }

    fun toFormattedString(): String {
        return toTrimOrElse(text(copyOf(duration), TimeDurationUnit.YEARS, true)) {
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

        const val DAYS_PER_WEEK = 7L

        const val DAYS_PER_YEAR = 365L

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

        private fun next(unit: TimeDurationUnit): TimeDurationUnit? {
            return maps[unit.ordinal.plus(1)]
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
        fun parseCharSequence(text: String): TimeDuration {
            val list = text.toLowerTrim().split(Regex("\\h+"))
            val size = list.size
            if ((size == 0).or(size.rem(2) == 1)) {
                throw MercenaryFatalExceptiion("invalid size $size")
            }
            var copy = make(list[0], list[1])
            for (i in 2 until size step 2) {
                copy += make(list[i], list[i + 1])
            }
            return copy
        }

        private fun make(buff: String, unit: String): TimeDuration {
            val time = buff.toLongOrNull() ?: throw MercenaryFatalExceptiion("invalid time $buff")
            return when (unit) {
                "day", "days" -> days(time)
                "year", "years" -> years(time)
                "week", "weeks" -> weeks(time)
                "hour", "hours" -> hours(time)
                "minute", "minutes" -> minutes(time)
                "second", "seconds" -> seconds(time)
                "nanosecond", "nanoseconds" -> nanoseconds(time)
                "millisecond", "milliseconds" -> milliseconds(time)
                else -> throw MercenaryFatalExceptiion("invalid unit $unit")
            }
        }
    }
}