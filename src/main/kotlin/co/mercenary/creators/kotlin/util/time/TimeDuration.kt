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
import co.mercenary.creators.kotlin.util.type.*
import java.math.BigDecimal
import java.time.Duration

class TimeDuration private constructor(private val time: Duration, private val unit: TimeDurationUnit) : Comparable<TimeDuration>, Copyable<TimeDuration>, Cloneable {

    constructor(text: String) : this(parse(text))

    constructor(self: TimeDuration) : this(self.time, self.unit)

    fun unit() = unit

    fun duration() = time.copyOf()

    operator fun plus(other: TimeDuration): TimeDuration {
        return time.plus(other.time).let {
            TimeDuration(it, less(it, unit))
        }
    }

    operator fun minus(other: TimeDuration): TimeDuration {
        return time.minus(other.time).let {
            TimeDuration(it, less(it, unit))
        }
    }

    operator fun div(other: Int): TimeDuration = div(other.toDouble())

    operator fun div(other: Long): TimeDuration = div(other.toDouble())

    operator fun div(other: Short): TimeDuration = div(other.toDouble())

    operator fun div(other: Float): TimeDuration = div(other.toDouble())

    operator fun div(other: Double): TimeDuration {
        if (other.isFinite()) {
            return when (other) {
                1.0 -> TimeDuration(time, unit)
                0.0 -> throw MercenaryFatalExceptiion(MATH_ZERO_DIVISOR_ERROR)
                else -> time.toBigDecimal().div(other.toBigDecimal()).toDuration().let {
                    TimeDuration(it, less(it, unit))
                }
            }
        }
        throw MercenaryFatalExceptiion("invalid value $other")
    }

    operator fun times(other: Int): TimeDuration = times(other.toDouble())

    operator fun times(other: Long): TimeDuration = times(other.toDouble())

    operator fun times(other: Short): TimeDuration = times(other.toDouble())

    operator fun times(other: Float): TimeDuration = times(other.toDouble())

    operator fun times(other: Double): TimeDuration {
        if (other.isFinite()) {
            return when (other) {
                1.0 -> TimeDuration(time, unit)
                0.0 -> TimeDuration(ZERO, unit)
                else -> time.toBigDecimal().times(other.toBigDecimal()).toDuration().let {
                    TimeDuration(it, less(it, unit))
                }
            }
        }
        throw MercenaryFatalExceptiion("invalid value $other")
    }

    operator fun unaryPlus() = TimeDuration(time.abs(), unit)

    operator fun unaryMinus() = TimeDuration(time.negated(), unit)

    override operator fun compareTo(other: TimeDuration) = time.compareTo(other.time)

    override fun clone() = copyOf()

    override fun copyOf() = TimeDuration(time.copyOf(), unit)

    override fun equals(other: Any?) = when (other) {
        is TimeDuration -> this === other || time == other.time
        else -> false
    }

    override fun hashCode() = time.hashCode()

    override fun toString() = toTrimOrElse(text(time, unit)) { text(unit) }

    companion object {

        private const val NANOS_SHIFTED = 9

        private const val DAYS_PER_WEEK = 7L

        private const val DAYS_PER_YEAR = 365L

        private const val SECONDS_PER_TICK = 60L

        private const val MILLS_PER_SECOND = 1000L

        private const val NANOS_PER_SECOND = 1000000000L

        private const val SECONDS_PER_HOUR = SECONDS_PER_TICK * 60L

        private const val SECONDS_PER_DAYS = SECONDS_PER_HOUR * 24L

        private const val SECONDS_PER_WEEK = SECONDS_PER_DAYS * DAYS_PER_WEEK

        private const val SECONDS_PER_YEAR = SECONDS_PER_DAYS * DAYS_PER_YEAR

        private val NANOS_PER_SECOND_VALUE = NANOS_PER_SECOND.toBigInteger()

        private val ZERO = Duration.ZERO

        private val GLOB = Regex("\\h+")

        private val HASH = AtomicHashMap<String, TimeDuration>()

        private fun Duration.copyOf(): Duration = Duration.ofSeconds(seconds, nano.toLong())

        private fun Duration.toBigDecimal(): BigDecimal = seconds.toBigDecimal().plus(BigDecimal.valueOf(nano.toLong(), NANOS_SHIFTED))

        private fun BigDecimal.toDuration(): Duration = movePointRight(NANOS_SHIFTED).toBigIntegerExact().let { nano ->
            nano.divideAndRemainder(NANOS_PER_SECOND_VALUE).let {
                if (it[0].bitLength() > 63) throw MercenaryFatalExceptiion("invalid capacity $nano") else Duration.ofSeconds(it[0].toLong(), it[1].toLong())
            }
        }

        private fun isEmpty(time: Duration): Boolean {
            return time.isZero.or(time.isNegative)
        }

        private fun text(unit: TimeDurationUnit, time: Long = 0L): String {
            return "$time ${unit.toLowerCase(time.abs() != 1L)}"
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
                null -> when (save > 0) {
                    true -> text(unit, save)
                    else -> EMPTY_STRING
                }
                else -> when (save < 1) {
                    true -> text(copy, next)
                    else -> "${text(unit, save)} ${text(copy, next)}"
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
        fun days(time: Short) = days(time.toLong())

        @JvmStatic
        fun weeks(time: Short) = weeks(time.toLong())

        @JvmStatic
        fun years(time: Short) = years(time.toLong())

        @JvmStatic
        fun hours(time: Short) = hours(time.toLong())

        @JvmStatic
        fun minutes(time: Short) = minutes(time.toLong())

        @JvmStatic
        fun seconds(time: Short) = seconds(time.toLong())

        @JvmStatic
        fun nanoseconds(time: Short) = nanoseconds(time.toLong())

        @JvmStatic
        fun milliseconds(time: Short) = milliseconds(time.toLong())

        @JvmStatic
        fun days(time: Float) = days(time.toDouble())

        @JvmStatic
        fun weeks(time: Float) = weeks(time.toDouble())

        @JvmStatic
        fun years(time: Float) = years(time.toDouble())

        @JvmStatic
        fun hours(time: Float) = hours(time.toDouble())

        @JvmStatic
        fun minutes(time: Float) = minutes(time.toDouble())

        @JvmStatic
        fun seconds(time: Float) = seconds(time.toDouble())

        @JvmStatic
        fun nanoseconds(time: Float) = nanoseconds(time.toDouble())

        @JvmStatic
        fun milliseconds(time: Float) = milliseconds(time.toDouble())

        @JvmStatic
        fun years(time: Long): TimeDuration {
            return Duration.ofDays(time * DAYS_PER_YEAR).let {
                TimeDuration(it, less(it, TimeDurationUnit.YEARS))
            }
        }

        @JvmStatic
        fun weeks(time: Long): TimeDuration {
            return Duration.ofDays(time * DAYS_PER_WEEK).let {
                TimeDuration(it, less(it, TimeDurationUnit.WEEKS))
            }
        }

        @JvmStatic
        fun days(time: Long): TimeDuration {
            return Duration.ofDays(time).let {
                TimeDuration(it, less(it, TimeDurationUnit.DAYS))
            }
        }

        @JvmStatic
        fun hours(time: Long): TimeDuration {
            return Duration.ofHours(time).let {
                TimeDuration(it, less(it, TimeDurationUnit.HOURS))
            }
        }

        @JvmStatic
        fun minutes(time: Long): TimeDuration {
            return Duration.ofMinutes(time).let {
                TimeDuration(it, less(it, TimeDurationUnit.MINUTES))
            }
        }

        @JvmStatic
        fun seconds(time: Long): TimeDuration {
            return Duration.ofSeconds(time).let {
                TimeDuration(it, less(it, TimeDurationUnit.SECONDS))
            }
        }

        @JvmStatic
        fun nanoseconds(time: Long): TimeDuration {
            return Duration.ofNanos(time).let {
                TimeDuration(it, less(it, TimeDurationUnit.NANOSECONDS))
            }
        }

        @JvmStatic
        fun milliseconds(time: Long): TimeDuration {
            return Duration.ofMillis(time).let {
                TimeDuration(it, less(it, TimeDurationUnit.MILLISECONDS))
            }
        }

        @JvmStatic
        fun years(time: Double): TimeDuration {
            return secondsOf(time * SECONDS_PER_YEAR, TimeDurationUnit.YEARS)
        }

        @JvmStatic
        fun weeks(time: Double): TimeDuration {
            return secondsOf(time * SECONDS_PER_WEEK, TimeDurationUnit.WEEKS)
        }

        @JvmStatic
        fun days(time: Double): TimeDuration {
            return secondsOf(time * SECONDS_PER_DAYS, TimeDurationUnit.DAYS)
        }

        @JvmStatic
        fun hours(time: Double): TimeDuration {
            return secondsOf(time * SECONDS_PER_HOUR, TimeDurationUnit.HOURS)
        }

        @JvmStatic
        fun minutes(time: Double): TimeDuration {
            return secondsOf(time * SECONDS_PER_TICK, TimeDurationUnit.MINUTES)
        }

        @JvmStatic
        fun seconds(time: Double): TimeDuration {
            return secondsOf(time, TimeDurationUnit.SECONDS)
        }

        @JvmStatic
        fun milliseconds(time: Double): TimeDuration {
            return secondsOf(time / MILLS_PER_SECOND, TimeDurationUnit.MILLISECONDS)
        }

        @JvmStatic
        fun nanoseconds(time: Double): TimeDuration {
            return secondsOf(time / NANOS_PER_SECOND, TimeDurationUnit.NANOSECONDS)
        }

        @JvmStatic
        fun parse(text: String): TimeDuration {
            return text.toLowerTrim().let { look ->
                HASH.computeIfAbsent(look) {
                    parseCharSequence(it)
                }
            }
        }

        @JvmStatic
        fun parseCharSequence(text: CharSequence): TimeDuration {
            val list = text.toLowerTrim().split(GLOB)
            val size = list.size
            if ((size == 0).or(size.rem(2) == 1)) {
                throw MercenaryFatalExceptiion("invalid size $size")
            }
            val data = TimeData()
            for (i in 0 until size step 2) {
                make(list[i], list[i + 1], data)
            }
            return data.build()
        }

        private fun secondsOf(time: Double, unit: TimeDurationUnit): TimeDuration {
            val full = if (time.isFinite()) time.abs() else throw MercenaryFatalExceptiion("invalid time $time")
            val part = full.floor()
            if (part >= Long.MAX_VALUE) {
                throw MercenaryFatalExceptiion("invalid part $part")
            }
            val frac = (full - part) * NANOS_PER_SECOND
            if (frac >= Long.MAX_VALUE) {
                throw MercenaryFatalExceptiion("invalid frac $frac")
            }
            return Duration.ofSeconds(part.toLong(), frac.toLong()).let { if (time.isNegative()) it.negated() else it }.let { TimeDuration(it, less(it, unit)) }
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

        private class TimeData(var time: Duration = ZERO, var unit: TimeDurationUnit = TimeDurationUnit.NANOSECONDS) : Builder<TimeDuration> {

            fun make(plus: Long, block: (Long) -> Duration, less: TimeDurationUnit) {
                if (less < unit) {
                    unit = less
                }
                time = block.invoke(plus)
            }

            override fun build(): TimeDuration {
                return TimeDuration(time, unit)
            }
        }
    }
}