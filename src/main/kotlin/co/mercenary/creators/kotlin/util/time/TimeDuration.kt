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
import java.math.BigDecimal
import java.time.Duration

class TimeDuration @CreatorsDsl private constructor(private val time: Duration, private val unit: TimeDurationUnit) : Comparable<TimeDuration>, Copyable<TimeDuration>, Cloneable {

    @CreatorsDsl
    constructor(text: String) : this(parse(text))

    @CreatorsDsl
    constructor(self: TimeDuration) : this(self.time, self.unit)

    @CreatorsDsl
    fun unit() = unit

    @CreatorsDsl
    fun duration() = time.copyOf()

    @CreatorsDsl
    operator fun plus(other: TimeDuration): TimeDuration {
        return time.plus(other.time).build(unit)
    }

    @CreatorsDsl
    operator fun minus(other: TimeDuration): TimeDuration {
        return time.minus(other.time).build(unit)
    }

    @CreatorsDsl
    operator fun div(other: Int): TimeDuration = div(other.toDouble())

    @CreatorsDsl
    operator fun div(other: Long): TimeDuration = div(other.toDouble())

    @CreatorsDsl
    operator fun div(other: Short): TimeDuration = div(other.toDouble())

    @CreatorsDsl
    operator fun div(other: Float): TimeDuration = div(other.toDouble())

    @CreatorsDsl
    operator fun div(other: Double): TimeDuration {
        return when (other.toValidDivisor()) {
            1.0 -> TimeDuration(time, unit)
            else -> time.toBigDecimal().div(other).toDuration().build(unit)
        }
    }

    @CreatorsDsl
    operator fun times(other: Int): TimeDuration = times(other.toDouble())

    @CreatorsDsl
    operator fun times(other: Long): TimeDuration = times(other.toDouble())

    @CreatorsDsl
    operator fun times(other: Short): TimeDuration = times(other.toDouble())

    @CreatorsDsl
    operator fun times(other: Float): TimeDuration = times(other.toDouble())

    @CreatorsDsl
    operator fun times(other: Double): TimeDuration {
        return when (other.toFinite()) {
            1.0 -> TimeDuration(time, unit)
            0.0 -> TimeDuration(ZERO, unit)
            else -> time.toBigDecimal().times(other).toDuration().build(unit)
        }
    }

    @CreatorsDsl
    operator fun unaryPlus() = TimeDuration(time.abs(), unit)

    @CreatorsDsl
    operator fun unaryMinus() = TimeDuration(time.negated(), unit)

    @CreatorsDsl
    override operator fun compareTo(other: TimeDuration) = time.compareTo(other.time)

    @CreatorsDsl
    override fun clone() = copyOf()

    @CreatorsDsl
    override fun copyOf() = TimeDuration(time.copyOf(), unit)

    @CreatorsDsl
    override fun equals(other: Any?) = when (other) {
        is TimeDuration -> this === other || time == other.time
        else -> false
    }

    @CreatorsDsl
    override fun hashCode() = time.hashCode()

    @CreatorsDsl
    override fun toString() = text(time, unit).toTrimOr { text(unit) }

    companion object {

        @CreatorsDsl
        private const val NANOS_SHIFTED = 9

        @CreatorsDsl
        private const val DAYS_PER_WEEK = 7L

        @CreatorsDsl
        private const val DAYS_PER_YEAR = 365L

        @CreatorsDsl
        private const val SECONDS_PER_TICK = 60L

        @CreatorsDsl
        private const val MILLS_PER_SECOND = 1000L

        @CreatorsDsl
        private const val NANOS_PER_SECOND = 1000000000L

        @CreatorsDsl
        private const val SECONDS_PER_HOUR = SECONDS_PER_TICK * 60L

        @CreatorsDsl
        private const val SECONDS_PER_DAYS = SECONDS_PER_HOUR * 24L

        @CreatorsDsl
        private const val SECONDS_PER_WEEK = SECONDS_PER_DAYS * DAYS_PER_WEEK

        @CreatorsDsl
        private const val SECONDS_PER_YEAR = SECONDS_PER_DAYS * DAYS_PER_YEAR

        @CreatorsDsl
        private val NANOS_PER_SECOND_VALUE = NANOS_PER_SECOND.toBigInteger()

        @CreatorsDsl
        private val ZERO = Duration.ZERO

        @CreatorsDsl
        private val GLOB = Regex("\\h+")

        @CreatorsDsl
        private val HASH = atomicMapOf<String, TimeDuration>()
        @CreatorsDsl
        private val Duration.nanos: Long
            get() = nano.toLong()

        @CreatorsDsl
        private fun Duration.copyOf(): Duration = Duration.ofSeconds(seconds, nanos)

        @CreatorsDsl
        private fun Duration.build(unit: TimeDurationUnit): TimeDuration = TimeDuration(this, less(this, unit))

        @CreatorsDsl
        private fun Duration.toBigDecimal(): BigDecimal = seconds.toBigDecimal().plus(BigDecimal.valueOf(nanos, NANOS_SHIFTED))

        @CreatorsDsl
        private fun BigDecimal.toDuration(): Duration = movePointRight(NANOS_SHIFTED).toBigIntegerExact().let { nano ->
            nano.divideAndRemainder(NANOS_PER_SECOND_VALUE).let {
                if (it[0].bitLength() > 63) throw MercenaryFatalExceptiion("invalid capacity $nano") else Duration.ofSeconds(it[0].toLong(), it[1].toInt().toLong())
            }
        }

        @JvmStatic
        @CreatorsDsl
        private fun isEmpty(time: Duration): Boolean {
            return time.isZero.or(time.isNegative)
        }

        @JvmStatic
        @CreatorsDsl
        private fun text(unit: TimeDurationUnit, time: Long = 0L): String {
            return "$time ${unit.toLowerCase(time.absOf() != 1L)}"
        }

        @JvmStatic
        @CreatorsDsl
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
                TimeDurationUnit.NANOSECONDS -> copy.nanos.also {
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
        @CreatorsDsl
        fun days(time: Int) = days(time.toLong())

        @JvmStatic
        @CreatorsDsl
        fun weeks(time: Int) = weeks(time.toLong())

        @JvmStatic
        @CreatorsDsl
        fun years(time: Int) = years(time.toLong())

        @JvmStatic
        @CreatorsDsl
        fun hours(time: Int) = hours(time.toLong())

        @JvmStatic
        @CreatorsDsl
        fun minutes(time: Int) = minutes(time.toLong())

        @JvmStatic
        @CreatorsDsl
        fun seconds(time: Int) = seconds(time.toLong())

        @JvmStatic
        @CreatorsDsl
        fun nanoseconds(time: Int) = nanoseconds(time.toLong())

        @JvmStatic
        @CreatorsDsl
        fun milliseconds(time: Int) = milliseconds(time.toLong())

        @JvmStatic
        @CreatorsDsl
        fun days(time: Short) = days(time.toLong())

        @JvmStatic
        @CreatorsDsl
        fun weeks(time: Short) = weeks(time.toLong())

        @JvmStatic
        @CreatorsDsl
        fun years(time: Short) = years(time.toLong())

        @JvmStatic
        @CreatorsDsl
        fun hours(time: Short) = hours(time.toLong())

        @JvmStatic
        @CreatorsDsl
        fun minutes(time: Short) = minutes(time.toLong())

        @JvmStatic
        @CreatorsDsl
        fun seconds(time: Short) = seconds(time.toLong())

        @JvmStatic
        @CreatorsDsl
        fun nanoseconds(time: Short) = nanoseconds(time.toLong())

        @JvmStatic
        @CreatorsDsl
        fun milliseconds(time: Short) = milliseconds(time.toLong())

        @JvmStatic
        @CreatorsDsl
        fun days(time: Float) = days(time.toDouble())

        @JvmStatic
        @CreatorsDsl
        fun weeks(time: Float) = weeks(time.toDouble())

        @JvmStatic
        @CreatorsDsl
        fun years(time: Float) = years(time.toDouble())

        @JvmStatic
        @CreatorsDsl
        fun hours(time: Float) = hours(time.toDouble())

        @JvmStatic
        @CreatorsDsl
        fun minutes(time: Float) = minutes(time.toDouble())

        @JvmStatic
        @CreatorsDsl
        fun seconds(time: Float) = seconds(time.toDouble())

        @JvmStatic
        @CreatorsDsl
        fun nanoseconds(time: Float) = nanoseconds(time.toDouble())

        @JvmStatic
        @CreatorsDsl
        fun milliseconds(time: Float) = milliseconds(time.toDouble())

        @JvmStatic
        @CreatorsDsl
        fun years(time: Long): TimeDuration {
            return Duration.ofDays(time * DAYS_PER_YEAR).build(TimeDurationUnit.YEARS)
        }

        @JvmStatic
        @CreatorsDsl
        fun weeks(time: Long): TimeDuration {
            return Duration.ofDays(time * DAYS_PER_WEEK).build(TimeDurationUnit.WEEKS)
        }

        @JvmStatic
        @CreatorsDsl
        fun days(time: Long): TimeDuration {
            return Duration.ofDays(time).build(TimeDurationUnit.DAYS)
        }

        @JvmStatic
        @CreatorsDsl
        fun hours(time: Long): TimeDuration {
            return Duration.ofHours(time).build(TimeDurationUnit.HOURS)
        }

        @JvmStatic
        @CreatorsDsl
        fun minutes(time: Long): TimeDuration {
            return Duration.ofMinutes(time).build(TimeDurationUnit.MINUTES)
        }

        @JvmStatic
        @CreatorsDsl
        fun seconds(time: Long): TimeDuration {
            return Duration.ofSeconds(time).build(TimeDurationUnit.SECONDS)
        }

        @JvmStatic
        @CreatorsDsl
        fun nanoseconds(time: Long): TimeDuration {
            return Duration.ofNanos(time).build(TimeDurationUnit.NANOSECONDS)
        }

        @JvmStatic
        @CreatorsDsl
        fun milliseconds(time: Long): TimeDuration {
            return Duration.ofMillis(time).build(TimeDurationUnit.MILLISECONDS)
        }

        @JvmStatic
        @CreatorsDsl
        fun years(time: Double): TimeDuration {
            return secondsOf(time * SECONDS_PER_YEAR, TimeDurationUnit.YEARS)
        }

        @JvmStatic
        @CreatorsDsl
        fun weeks(time: Double): TimeDuration {
            return secondsOf(time * SECONDS_PER_WEEK, TimeDurationUnit.WEEKS)
        }

        @JvmStatic
        @CreatorsDsl
        fun days(time: Double): TimeDuration {
            return secondsOf(time * SECONDS_PER_DAYS, TimeDurationUnit.DAYS)
        }

        @JvmStatic
        @CreatorsDsl
        fun hours(time: Double): TimeDuration {
            return secondsOf(time * SECONDS_PER_HOUR, TimeDurationUnit.HOURS)
        }

        @JvmStatic
        @CreatorsDsl
        fun minutes(time: Double): TimeDuration {
            return secondsOf(time * SECONDS_PER_TICK, TimeDurationUnit.MINUTES)
        }

        @JvmStatic
        @CreatorsDsl
        fun seconds(time: Double): TimeDuration {
            return secondsOf(time, TimeDurationUnit.SECONDS)
        }

        @JvmStatic
        @CreatorsDsl
        fun milliseconds(time: Double): TimeDuration {
            return secondsOf(time / MILLS_PER_SECOND, TimeDurationUnit.MILLISECONDS)
        }

        @JvmStatic
        @CreatorsDsl
        fun nanoseconds(time: Double): TimeDuration {
            return secondsOf(time / NANOS_PER_SECOND, TimeDurationUnit.NANOSECONDS)
        }

        @JvmStatic
        @CreatorsDsl
        fun parse(text: String): TimeDuration {
            return HASH.computeIfAbsent(text.toLowerTrimEnglish()) { buff ->
                parseCharSequence(buff)
            }
        }

        @JvmStatic
        @CreatorsDsl
        fun parseCharSequence(text: CharSequence): TimeDuration {
            val list = text.toLowerTrimEnglish().split(GLOB)
            val size = list.size
            if (size == 0 || size % 2 == 1) {
                throw MercenaryFatalExceptiion("invalid size $size")
            }
            val data = TimeData()
            for (i in 0 until size step 2) {
                make(list[i], list[i + 1], data)
            }
            return data.build()
        }

        @JvmStatic
        @CreatorsDsl
        private fun secondsOf(time: Double, unit: TimeDurationUnit): TimeDuration {
            val full = if (time.isValid()) time.absOf() else throw MercenaryFatalExceptiion("invalid time $time")
            val part = full.floorOf()
            if (part >= Long.MAX_VALUE) {
                throw MercenaryFatalExceptiion("invalid part $part")
            }
            val frac = (full - part) * NANOS_PER_SECOND
            if (frac >= Long.MAX_VALUE) {
                throw MercenaryFatalExceptiion("invalid frac $frac")
            }
            return Duration.ofSeconds(part.toLong(), frac.toLong()).let { if (time.isNegative()) it.negated() else it }.let { TimeDuration(it, less(it, unit)) }
        }

        @JvmStatic
        @CreatorsDsl
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

        @JvmStatic
        @CreatorsDsl
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

        private class TimeData @CreatorsDsl constructor(var time: Duration = ZERO, var unit: TimeDurationUnit = TimeDurationUnit.NANOSECONDS) : Builder<TimeDuration> {

            @CreatorsDsl
            fun make(plus: Long, block: (Long) -> Duration, less: TimeDurationUnit) {
                if (less < unit) {
                    unit = less
                }
                time = block.invoke(plus)
            }

            @CreatorsDsl
            override fun build(): TimeDuration {
                return TimeDuration(time, unit)
            }
        }
    }
}