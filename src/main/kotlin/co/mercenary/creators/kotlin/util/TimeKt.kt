/*
 * Copyright (c) 2023, Mercenary Creators Company. All rights reserved.
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

@file:JvmName("TimeKt")
@file:Suppress("NOTHING_TO_INLINE", "UNCHECKED_CAST", "FunctionName", "HttpUrlsUsage")

package co.mercenary.creators.kotlin.util

import java.time.*
import java.util.*
import kotlin.system.*
import kotlin.time.*

typealias SystemTimeUnit = java.util.concurrent.TimeUnit

@FrameworkDsl
val SYSTEM_TIME_UNIT_NANOSECONDS = SystemTimeUnit.NANOSECONDS

@FrameworkDsl
val SYSTEM_TIME_UNIT_MILLISECONDS = SystemTimeUnit.MILLISECONDS

@FrameworkDsl
const val TIME_DEFAULT_ZONE_STRING = "UTC"

@FrameworkDsl
const val TIME_DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss,SSS z"

typealias TimeAndDate = co.mercenary.creators.kotlin.util.time.TimeAndDate

typealias CreatorsTimeDuration = co.mercenary.creators.kotlin.util.time.TimeDuration

typealias CreatorsTimeDurationUnit = co.mercenary.creators.kotlin.util.time.TimeDurationUnit

typealias KotlinTimeDuration = kotlin.time.Duration

typealias KotlinTimeDurationUnit = DurationUnit

@FrameworkDsl
inline fun KotlinTimeDuration.toCreatorsTimeDuration(): CreatorsTimeDuration = CreatorsTimeDuration.from(toJavaDuration())

@FrameworkDsl
inline fun CreatorsTimeDuration.toKotlinTimeDuration(): KotlinTimeDuration = duration().toKotlinDuration()

@FrameworkDsl
val Int.Day: CreatorsTimeDuration
    get() = CreatorsTimeDuration.days(this)

@FrameworkDsl
val Int.Hour: CreatorsTimeDuration
    get() = CreatorsTimeDuration.hours(this)

@FrameworkDsl
val Int.Week: CreatorsTimeDuration
    get() = CreatorsTimeDuration.weeks(this)

@FrameworkDsl
val Int.Year: CreatorsTimeDuration
    get() = CreatorsTimeDuration.years(this)

@FrameworkDsl
val Int.Minute: CreatorsTimeDuration
    get() = CreatorsTimeDuration.minutes(this)

@FrameworkDsl
val Int.Second: CreatorsTimeDuration
    get() = CreatorsTimeDuration.seconds(this)

@FrameworkDsl
val Int.Millisecond: CreatorsTimeDuration
    get() = CreatorsTimeDuration.milliseconds(this)

@FrameworkDsl
val Int.Nanosecond: CreatorsTimeDuration
    get() = CreatorsTimeDuration.nanoseconds(this)

@FrameworkDsl
val Int.Days: CreatorsTimeDuration
    get() = CreatorsTimeDuration.days(this)

@FrameworkDsl
val Int.Hours: CreatorsTimeDuration
    get() = CreatorsTimeDuration.hours(this)

@FrameworkDsl
val Int.Weeks: CreatorsTimeDuration
    get() = CreatorsTimeDuration.weeks(this)

@FrameworkDsl
val Int.Years: CreatorsTimeDuration
    get() = CreatorsTimeDuration.years(this)

@FrameworkDsl
val Int.Minutes: CreatorsTimeDuration
    get() = CreatorsTimeDuration.minutes(this)

@FrameworkDsl
val Int.Seconds: CreatorsTimeDuration
    get() = CreatorsTimeDuration.seconds(this)

@FrameworkDsl
val Int.Milliseconds: CreatorsTimeDuration
    get() = CreatorsTimeDuration.milliseconds(this)

@FrameworkDsl
val Int.Nanoseconds: CreatorsTimeDuration
    get() = CreatorsTimeDuration.nanoseconds(this)

@FrameworkDsl
val Long.Day: CreatorsTimeDuration
    get() = CreatorsTimeDuration.days(this)

@FrameworkDsl
val Long.Hour: CreatorsTimeDuration
    get() = CreatorsTimeDuration.hours(this)

@FrameworkDsl
val Long.Week: CreatorsTimeDuration
    get() = CreatorsTimeDuration.weeks(this)

@FrameworkDsl
val Long.Year: CreatorsTimeDuration
    get() = CreatorsTimeDuration.years(this)

@FrameworkDsl
val Long.Minute: CreatorsTimeDuration
    get() = CreatorsTimeDuration.minutes(this)

@FrameworkDsl
val Long.Second: CreatorsTimeDuration
    get() = CreatorsTimeDuration.seconds(this)

@FrameworkDsl
val Long.Millisecond: CreatorsTimeDuration
    get() = CreatorsTimeDuration.milliseconds(this)

@FrameworkDsl
val Long.Nanosecond: CreatorsTimeDuration
    get() = CreatorsTimeDuration.nanoseconds(this)

@FrameworkDsl
val Long.Days: CreatorsTimeDuration
    get() = CreatorsTimeDuration.days(this)

@FrameworkDsl
val Long.Hours: CreatorsTimeDuration
    get() = CreatorsTimeDuration.hours(this)

@FrameworkDsl
val Long.Weeks: CreatorsTimeDuration
    get() = CreatorsTimeDuration.weeks(this)

@FrameworkDsl
val Long.Years: CreatorsTimeDuration
    get() = CreatorsTimeDuration.years(this)

@FrameworkDsl
val Long.Minutes: CreatorsTimeDuration
    get() = CreatorsTimeDuration.minutes(this)

@FrameworkDsl
val Long.Seconds: CreatorsTimeDuration
    get() = CreatorsTimeDuration.seconds(this)

@FrameworkDsl
val Long.Milliseconds: CreatorsTimeDuration
    get() = CreatorsTimeDuration.milliseconds(this)

@FrameworkDsl
val Long.Nanoseconds: CreatorsTimeDuration
    get() = CreatorsTimeDuration.nanoseconds(this)

@FrameworkDsl
val Short.Day: CreatorsTimeDuration
    get() = CreatorsTimeDuration.days(this)

@FrameworkDsl
val Short.Hour: CreatorsTimeDuration
    get() = CreatorsTimeDuration.hours(this)

@FrameworkDsl
val Short.Week: CreatorsTimeDuration
    get() = CreatorsTimeDuration.weeks(this)

@FrameworkDsl
val Short.Year: CreatorsTimeDuration
    get() = CreatorsTimeDuration.years(this)

@FrameworkDsl
val Short.Minute: CreatorsTimeDuration
    get() = CreatorsTimeDuration.minutes(this)

@FrameworkDsl
val Short.Second: CreatorsTimeDuration
    get() = CreatorsTimeDuration.seconds(this)

@FrameworkDsl
val Short.Millisecond: CreatorsTimeDuration
    get() = CreatorsTimeDuration.milliseconds(this)

@FrameworkDsl
val Short.Nanosecond: CreatorsTimeDuration
    get() = CreatorsTimeDuration.nanoseconds(this)

@FrameworkDsl
val Short.Days: CreatorsTimeDuration
    get() = CreatorsTimeDuration.days(this)

@FrameworkDsl
val Short.Hours: CreatorsTimeDuration
    get() = CreatorsTimeDuration.hours(this)

@FrameworkDsl
val Short.Weeks: CreatorsTimeDuration
    get() = CreatorsTimeDuration.weeks(this)

@FrameworkDsl
val Short.Years: CreatorsTimeDuration
    get() = CreatorsTimeDuration.years(this)

@FrameworkDsl
val Short.Minutes: CreatorsTimeDuration
    get() = CreatorsTimeDuration.minutes(this)

@FrameworkDsl
val Short.Seconds: CreatorsTimeDuration
    get() = CreatorsTimeDuration.seconds(this)

@FrameworkDsl
val Short.Milliseconds: CreatorsTimeDuration
    get() = CreatorsTimeDuration.milliseconds(this)

@FrameworkDsl
val Short.Nanoseconds: CreatorsTimeDuration
    get() = CreatorsTimeDuration.nanoseconds(this)

@FrameworkDsl
val Float.Days: CreatorsTimeDuration
    get() = CreatorsTimeDuration.days(this)

@FrameworkDsl
val Float.Hours: CreatorsTimeDuration
    get() = CreatorsTimeDuration.hours(this)

@FrameworkDsl
val Float.Weeks: CreatorsTimeDuration
    get() = CreatorsTimeDuration.weeks(this)

@FrameworkDsl
val Float.Years: CreatorsTimeDuration
    get() = CreatorsTimeDuration.years(this)

@FrameworkDsl
val Float.Minutes: CreatorsTimeDuration
    get() = CreatorsTimeDuration.minutes(this)

@FrameworkDsl
val Float.Seconds: CreatorsTimeDuration
    get() = CreatorsTimeDuration.seconds(this)

@FrameworkDsl
val Float.Milliseconds: CreatorsTimeDuration
    get() = CreatorsTimeDuration.milliseconds(this)

@FrameworkDsl
val Float.Nanoseconds: CreatorsTimeDuration
    get() = CreatorsTimeDuration.nanoseconds(this)

@FrameworkDsl
val Double.Days: CreatorsTimeDuration
    get() = CreatorsTimeDuration.days(this)

@FrameworkDsl
val Double.Hours: CreatorsTimeDuration
    get() = CreatorsTimeDuration.hours(this)

@FrameworkDsl
val Double.Weeks: CreatorsTimeDuration
    get() = CreatorsTimeDuration.weeks(this)

@FrameworkDsl
val Double.Years: CreatorsTimeDuration
    get() = CreatorsTimeDuration.years(this)

@FrameworkDsl
val Double.Minutes: CreatorsTimeDuration
    get() = CreatorsTimeDuration.minutes(this)

@FrameworkDsl
val Double.Seconds: CreatorsTimeDuration
    get() = CreatorsTimeDuration.seconds(this)

@FrameworkDsl
val Double.Milliseconds: CreatorsTimeDuration
    get() = CreatorsTimeDuration.milliseconds(this)

@FrameworkDsl
val Double.Nanoseconds: CreatorsTimeDuration
    get() = CreatorsTimeDuration.nanoseconds(this)

@FrameworkDsl
operator fun Int.times(value: CreatorsTimeDuration): CreatorsTimeDuration = value.times(this)

@FrameworkDsl
operator fun Long.times(value: CreatorsTimeDuration): CreatorsTimeDuration = value.times(this)

@FrameworkDsl
operator fun Short.times(value: CreatorsTimeDuration): CreatorsTimeDuration = value.times(this)

@FrameworkDsl
operator fun Float.times(value: CreatorsTimeDuration): CreatorsTimeDuration = value.times(toFiniteOrElse(1.0))

@FrameworkDsl
operator fun Double.times(value: CreatorsTimeDuration): CreatorsTimeDuration = value.times(toFiniteOrElse(1.0))

@FrameworkDsl
inline fun CreatorsTimeDuration.getNanoSeconds(): Long = duration().toNanos()

@FrameworkDsl
inline fun CreatorsTimeDuration.getMilliSeconds(): Long = duration().toMillis()

@FrameworkDsl
fun Date?.copyOf(): Date = when (this) {
    null -> Date()
    else -> Date(toLong())
}

@FrameworkDsl
inline fun Date.toLong(): Long = time.maxOf(0L)

@FrameworkDsl
inline fun Long.toDate(): Date = Date(maxOf(0L))

@FrameworkDsl
inline fun dateOf(): Date = Date()

@FrameworkDsl
operator fun Date.plus(value: CreatorsTimeDuration): Date = Date(toLong() + value.getMilliSeconds())

@FrameworkDsl
operator fun Date.minus(value: CreatorsTimeDuration): Date = Date(toLong() - value.getMilliSeconds())

@FrameworkDsl
operator fun LocalDateTime.plus(value: CreatorsTimeDuration): LocalDateTime = plus(value.duration())

@FrameworkDsl
operator fun LocalDateTime.minus(value: CreatorsTimeDuration): LocalDateTime = minus(value.duration())

@FrameworkDsl
fun dateTimeOf(zone: ZoneId = TimeAndDate.getDefaultTimeZoneId()): LocalDateTime = TimeAndDate.dateTimeOf(zone)

@FrameworkDsl
fun getTimeStamp(nano: Boolean = false): Long = TimeAndDate.getTimeStamp(nano)

@FrameworkDsl
fun Date.convertTo(zone: ZoneId = TimeAndDate.getDefaultTimeZoneId()): LocalDateTime = TimeAndDate.convertTo(this, zone)

@FrameworkDsl
fun LocalDateTime.convertTo(zone: ZoneId = TimeAndDate.getDefaultTimeZoneId()): Date = TimeAndDate.convertTo(this, zone)

@FrameworkDsl
fun LocalDateTime.convertTo(zone: TimeZone): Date = TimeAndDate.convertTo(this, zone.toZoneId())

@FrameworkDsl
fun Date.formatDate(safe: Boolean = true): String = TimeAndDate.formatDate(this, safe)

@FrameworkDsl
fun LocalDateTime.formatDate(zone: ZoneId = TimeAndDate.getDefaultTimeZoneId()): String = TimeAndDate.formatDate(this, zone)

@FrameworkDsl
fun CharSequence.parseDate(): Date = TimeAndDate.parseDate(this, true)

@FrameworkDsl
fun String.parseDate(zone: ZoneId = TimeAndDate.getDefaultTimeZoneId()): LocalDateTime = TimeAndDate.parseDate(this, zone)

@FrameworkDsl
fun <T> timed(after: (String) -> Unit, block: () -> T): T = co.mercenary.creators.kotlin.util.time.NanoTicker().let { block().also { after(it(false)) } }

@FrameworkDsl
inline fun elapsed(nano: Boolean = true, block: () -> Unit): Long {
    return if (nano) measureNanoTime(block) else measureTimeMillis(block)
}

@FrameworkDsl
inline fun co.mercenary.creators.kotlin.util.time.AverageWindow.toElapsedString(): String = TimeAndDate.toDecimalPlaces(getAverage(), " milliseconds")

@FrameworkDsl
inline fun co.mercenary.creators.kotlin.util.time.TimeWindowHandle.toElapsedString(): String = getTimeWindowMovingAverage().toElapsedString().copyOf()
