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

@file:Suppress("NOTHING_TO_INLINE", "UNCHECKED_CAST")

package co.mercenary.creators.kotlin.util.time

import co.mercenary.creators.kotlin.util.*
import java.math.BigDecimal
import java.time.Duration

class TimeDuration @FrameworkDsl private constructor(private val time: Duration, private val unit: TimeDurationUnit) : Comparable<TimeDuration>, Copyable<TimeDuration>, Cloneable, Container {

    @FrameworkDsl
    constructor(text: String) : this(parse(text))

    @FrameworkDsl
    constructor(self: TimeDuration) : this(self.time(), self.unit())

    @FrameworkDsl
    private val buff by lazy {
        text(time(), unit()).toTrimOr { text(unit()) }
    }

    @FrameworkDsl
    internal inline fun data() = buff

    @FrameworkDsl
    internal inline fun time() = time

    @FrameworkDsl
    fun unit() = unit

    @FrameworkDsl
    fun duration() = time().copyOf()

    @FrameworkDsl
    operator fun plus(other: TimeDuration): TimeDuration {
        return time().plus(other.time()).build(unit())
    }

    @FrameworkDsl
    operator fun minus(other: TimeDuration): TimeDuration {
        return time().minus(other.time()).build(unit())
    }

    @FrameworkDsl
    operator fun div(other: Int): TimeDuration = div(other.realOf())

    @FrameworkDsl
    operator fun div(other: Long): TimeDuration = div(other.realOf())

    @FrameworkDsl
    operator fun div(other: Short): TimeDuration = div(other.realOf())

    @FrameworkDsl
    operator fun div(other: Float): TimeDuration = div(other.realOf())

    @FrameworkDsl
    operator fun div(other: Double): TimeDuration {
        return when (other.toValidDivisor()) {
            1.0 -> TimeDuration(time(), unit())
            else -> time().toBigDecimal().div(other).toDuration().build(unit())
        }
    }

    @FrameworkDsl
    operator fun times(other: Int): TimeDuration = times(other.realOf())

    @FrameworkDsl
    operator fun times(other: Long): TimeDuration = times(other.realOf())

    @FrameworkDsl
    operator fun times(other: Short): TimeDuration = times(other.realOf())

    @FrameworkDsl
    operator fun times(other: Float): TimeDuration = times(other.realOf())

    @FrameworkDsl
    operator fun times(other: Double): TimeDuration {
        return when (other.toFinite()) {
            1.0 -> TimeDuration(time(), unit())
            0.0 -> TimeDuration(zero(), unit())
            else -> time().toBigDecimal().times(other).toDuration().build(unit())
        }
    }

    @FrameworkDsl
    operator fun unaryPlus() = TimeDuration(time().abs(), unit())

    @FrameworkDsl
    operator fun unaryMinus() = TimeDuration(time().negated(), unit())

    @FrameworkDsl
    override operator fun compareTo(other: TimeDuration) = time().compareTo(other.time())

    @FrameworkDsl
    override fun clone() = copyOf()

    @FrameworkDsl
    override fun copyOf() = TimeDuration(this)

    @FrameworkDsl
    @IgnoreForSerialize
    override fun isEmpty() = time().isEmpty()

    @FrameworkDsl
    override fun equals(other: Any?) = when (other) {
        is TimeDuration -> this === other || time() == other.time()
        else -> false
    }

    @FrameworkDsl
    override fun hashCode() = time().hashOf()

    @FrameworkDsl
    override fun toString() = data().copyOf()

    companion object {

        @FrameworkDsl
        private const val NANOS_SHIFTED = 9

        @FrameworkDsl
        private const val DAYS_PER_WEEK = 7L

        @FrameworkDsl
        private const val DAYS_PER_YEAR = 365L

        @FrameworkDsl
        private const val SECONDS_PER_TICK = 60L

        @FrameworkDsl
        private const val MILLS_PER_SECOND = 1000L

        @FrameworkDsl
        private const val NANOS_PER_SECOND = 1000000000L

        @FrameworkDsl
        private const val SECONDS_PER_HOUR = 3600L

        @FrameworkDsl
        private const val SECONDS_PER_DAYS = 86400L

        @FrameworkDsl
        private const val SECONDS_PER_YEAR = 31556952L

        @FrameworkDsl
        private const val SECONDS_PER_WEEK = SECONDS_PER_DAYS * DAYS_PER_WEEK

        @FrameworkDsl
        private val NANOS_PER_SECOND_VALUE = NANOS_PER_SECOND.toBigInteger()

        @FrameworkDsl
        private val GLOB = Regex("\\h+")

        @FrameworkDsl
        private val HASH = atomicMapOf<String, TimeDuration>()

        @FrameworkDsl
        private val Duration.nanos: Long
            get() = nano.longOf()

        @FrameworkDsl
        private fun zero() = Duration.ZERO

        @FrameworkDsl
        private fun cashe(text: String, block: (String) -> TimeDuration) = HASH.computeIfAbsent(text.toLowerTrimEnglish(), block)

        @FrameworkDsl
        private fun Duration.copyOf(): Duration = Duration.ofSeconds(seconds, nanos)

        @FrameworkDsl
        private fun Duration.build(unit: TimeDurationUnit): TimeDuration = TimeDuration(this, less(this, unit))

        @FrameworkDsl
        private fun Duration.toBigDecimal(): BigDecimal = seconds.toBigDecimal().plus(BigDecimal.valueOf(nanos, NANOS_SHIFTED))

        @FrameworkDsl
        private fun BigDecimal.toDuration(): Duration = movePointRight(NANOS_SHIFTED).toBigIntegerExact().let { nano ->
            nano.divideAndRemainder(NANOS_PER_SECOND_VALUE).let {
                if (it[0].bitLength() > 63) throw MercenaryFatalExceptiion("invalid capacity $nano") else Duration.ofSeconds(it[0].toLong(), it[1].toInt().toLong())
            }
        }

        @JvmStatic
        @FrameworkDsl
        private fun isZero(time: Duration): Boolean {
            return time.isZero
        }

        @JvmStatic
        @FrameworkDsl
        private fun isNegative(time: Duration): Boolean {
            return time.isNegative
        }

        @JvmStatic
        @FrameworkDsl
        fun isEmpty(time: Duration): Boolean {
            return time.isZero || time.isNegative
        }

        @JvmStatic
        @FrameworkDsl
        private fun text(unit: TimeDurationUnit, time: Long = 0L): String {
            return "$time ${unit.toLowerCase(time.absOf() != 1L)}"
        }

        @JvmStatic
        @FrameworkDsl
        private fun text(time: Duration, unit: TimeDurationUnit): String {
            if (time.isEmpty()) {
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
        @FrameworkDsl
        @JvmOverloads
        fun from(time: Duration, unit: TimeDurationUnit = TimeDurationUnit.YEARS) = time.build(unit)

        @JvmStatic
        @FrameworkDsl
        fun days(time: Int) = days(time.longOf())

        @JvmStatic
        @FrameworkDsl
        fun weeks(time: Int) = weeks(time.longOf())

        @JvmStatic
        @FrameworkDsl
        fun years(time: Int) = years(time.longOf())

        @JvmStatic
        @FrameworkDsl
        fun hours(time: Int) = hours(time.longOf())

        @JvmStatic
        @FrameworkDsl
        fun minutes(time: Int) = minutes(time.longOf())

        @JvmStatic
        @FrameworkDsl
        fun seconds(time: Int) = seconds(time.longOf())

        @JvmStatic
        @FrameworkDsl
        fun nanoseconds(time: Int) = nanoseconds(time.longOf())

        @JvmStatic
        @FrameworkDsl
        fun milliseconds(time: Int) = milliseconds(time.longOf())

        @JvmStatic
        @FrameworkDsl
        fun days(time: Short) = days(time.longOf())

        @JvmStatic
        @FrameworkDsl
        fun weeks(time: Short) = weeks(time.longOf())

        @JvmStatic
        @FrameworkDsl
        fun years(time: Short) = years(time.longOf())

        @JvmStatic
        @FrameworkDsl
        fun hours(time: Short) = hours(time.longOf())

        @JvmStatic
        @FrameworkDsl
        fun minutes(time: Short) = minutes(time.longOf())

        @JvmStatic
        @FrameworkDsl
        fun seconds(time: Short) = seconds(time.longOf())

        @JvmStatic
        @FrameworkDsl
        fun nanoseconds(time: Short) = nanoseconds(time.longOf())

        @JvmStatic
        @FrameworkDsl
        fun milliseconds(time: Short) = milliseconds(time.longOf())

        @JvmStatic
        @FrameworkDsl
        fun days(time: Float) = days(time.realOf())

        @JvmStatic
        @FrameworkDsl
        fun weeks(time: Float) = weeks(time.realOf())

        @JvmStatic
        @FrameworkDsl
        fun years(time: Float) = years(time.realOf())

        @JvmStatic
        @FrameworkDsl
        fun hours(time: Float) = hours(time.realOf())

        @JvmStatic
        @FrameworkDsl
        fun minutes(time: Float) = minutes(time.realOf())

        @JvmStatic
        @FrameworkDsl
        fun seconds(time: Float) = seconds(time.realOf())

        @JvmStatic
        @FrameworkDsl
        fun nanoseconds(time: Float) = nanoseconds(time.realOf())

        @JvmStatic
        @FrameworkDsl
        fun milliseconds(time: Float) = milliseconds(time.realOf())

        @JvmStatic
        @FrameworkDsl
        fun years(time: Long): TimeDuration {
            return make(time, TimeDurationUnit.YEARS)
        }

        @JvmStatic
        @FrameworkDsl
        fun weeks(time: Long): TimeDuration {
            return make(time, TimeDurationUnit.WEEKS)
        }

        @JvmStatic
        @FrameworkDsl
        fun days(time: Long): TimeDuration {
            return Duration.ofDays(time).build(TimeDurationUnit.DAYS)
        }

        @JvmStatic
        @FrameworkDsl
        fun hours(time: Long): TimeDuration {
            return Duration.ofHours(time).build(TimeDurationUnit.HOURS)
        }

        @JvmStatic
        @FrameworkDsl
        fun minutes(time: Long): TimeDuration {
            return Duration.ofMinutes(time).build(TimeDurationUnit.MINUTES)
        }

        @JvmStatic
        @FrameworkDsl
        fun seconds(time: Long): TimeDuration {
            return Duration.ofSeconds(time).build(TimeDurationUnit.SECONDS)
        }

        @JvmStatic
        @FrameworkDsl
        fun nanoseconds(time: Long): TimeDuration {
            return Duration.ofNanos(time).build(TimeDurationUnit.NANOSECONDS)
        }

        @JvmStatic
        @FrameworkDsl
        fun milliseconds(time: Long): TimeDuration {
            return Duration.ofMillis(time).build(TimeDurationUnit.MILLISECONDS)
        }

        @JvmStatic
        @FrameworkDsl
        fun years(time: Double): TimeDuration {
            return secondsOf(time * SECONDS_PER_YEAR, TimeDurationUnit.YEARS)
        }

        @JvmStatic
        @FrameworkDsl
        fun weeks(time: Double): TimeDuration {
            return secondsOf(time * SECONDS_PER_WEEK, TimeDurationUnit.WEEKS)
        }

        @JvmStatic
        @FrameworkDsl
        fun days(time: Double): TimeDuration {
            return secondsOf(time * SECONDS_PER_DAYS, TimeDurationUnit.DAYS)
        }

        @JvmStatic
        @FrameworkDsl
        fun hours(time: Double): TimeDuration {
            return secondsOf(time * SECONDS_PER_HOUR, TimeDurationUnit.HOURS)
        }

        @JvmStatic
        @FrameworkDsl
        fun minutes(time: Double): TimeDuration {
            return secondsOf(time * SECONDS_PER_TICK, TimeDurationUnit.MINUTES)
        }

        @JvmStatic
        @FrameworkDsl
        fun seconds(time: Double): TimeDuration {
            return secondsOf(time, TimeDurationUnit.SECONDS)
        }

        @JvmStatic
        @FrameworkDsl
        fun milliseconds(time: Double): TimeDuration {
            return secondsOf(time / MILLS_PER_SECOND, TimeDurationUnit.MILLISECONDS)
        }

        @JvmStatic
        @FrameworkDsl
        fun nanoseconds(time: Double): TimeDuration {
            return secondsOf(time / NANOS_PER_SECOND, TimeDurationUnit.NANOSECONDS)
        }

        @JvmStatic
        @FrameworkDsl
        fun parse(text: String): TimeDuration {
            return cashe(text) {
                parseCharSequence(it)
            }
        }

        @JvmStatic
        @FrameworkDsl
        fun parseCharSequence(text: CharSequence): TimeDuration {
            val list = text.toLowerTrimEnglish().split(GLOB)
            val size = list.sizeOf()
            if (size.isZero() || size.isNotEven()) {
                throw MercenaryFatalExceptiion("invalid size $size")
            }
            return TimeData().create(list)
        }

        @JvmStatic
        @FrameworkDsl
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
            return Duration.ofSeconds(part.longOf(), frac.longOf()).let { if (time.isNegative()) it.negated() else it }.let { TimeDuration(it, less(it, unit)) }
        }

        @JvmStatic
        @FrameworkDsl
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
        @FrameworkDsl
        private fun make(time: Long, unit: TimeDurationUnit): TimeDuration {
            return Duration.of(time, unit.toSystemTimeUnit()).build(unit)
        }

        @JvmStatic
        @FrameworkDsl
        private fun make(buff: String, unit: String, data: TimeData) {
            val time = data.time()
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

        internal class TimeData @FrameworkDsl constructor() : Builder<TimeDuration> {

            @FrameworkDsl
            private var time = zero()

            @FrameworkDsl
            private var unit = TimeDurationUnit.NANOSECONDS

            @FrameworkDsl
            inline fun time(): Duration {
                return time
            }

            @FrameworkDsl
            private fun time(args: Duration) {
                time = args
            }

            @FrameworkDsl
            inline fun unit() = unit

            @FrameworkDsl
            private fun unit(args: TimeDurationUnit) {
                unit = args
            }

            @FrameworkDsl
            fun make(plus: Long, block: (Long) -> Duration, less: TimeDurationUnit) {
                if (less < unit()) {
                    unit(less)
                }
                time(block(plus))
            }

            @FrameworkDsl
            fun create(args: List<String>): TimeDuration {
                args.forEachOther { a, b ->
                    make(a, b, this)
                }
                return build()
            }

            @FrameworkDsl
            override fun build(): TimeDuration {
                return TimeDuration(time(), unit())
            }
        }
    }
}