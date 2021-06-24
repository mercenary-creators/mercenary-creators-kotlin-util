/*
 * Copyright (c) 2021, Mercenary Creators Company. All rights reserved.
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

@file:kotlin.jvm.JvmName("TimeKt")
@file:Suppress("NOTHING_TO_INLINE", "UNCHECKED_CAST")

package co.mercenary.creators.kotlin.util

import co.mercenary.creators.kotlin.util.time.NanoTicker
import java.time.*
import java.util.*

@FrameworkDsl
const val TIME_DEFAULT_ZONE_STRING = "UTC"

@FrameworkDsl
const val TIME_DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss,SSS z"

typealias TimeAndDate = co.mercenary.creators.kotlin.util.time.TimeAndDate

typealias TimeDuration = co.mercenary.creators.kotlin.util.time.TimeDuration

typealias TimeDurationUnit = co.mercenary.creators.kotlin.util.time.TimeDurationUnit

@FrameworkDsl
val Int.day: TimeDuration
    get() = TimeDuration.days(this)

@FrameworkDsl
val Int.hour: TimeDuration
    get() = TimeDuration.hours(this)

@FrameworkDsl
val Int.week: TimeDuration
    get() = TimeDuration.weeks(this)

@FrameworkDsl
val Int.year: TimeDuration
    get() = TimeDuration.years(this)

@FrameworkDsl
val Int.minute: TimeDuration
    get() = TimeDuration.minutes(this)

@FrameworkDsl
val Int.second: TimeDuration
    get() = TimeDuration.seconds(this)

@FrameworkDsl
val Int.millisecond: TimeDuration
    get() = TimeDuration.milliseconds(this)

@FrameworkDsl
val Int.nanosecond: TimeDuration
    get() = TimeDuration.nanoseconds(this)

@FrameworkDsl
val Int.days: TimeDuration
    get() = TimeDuration.days(this)

@FrameworkDsl
val Int.hours: TimeDuration
    get() = TimeDuration.hours(this)

@FrameworkDsl
val Int.weeks: TimeDuration
    get() = TimeDuration.weeks(this)

@FrameworkDsl
val Int.years: TimeDuration
    get() = TimeDuration.years(this)

@FrameworkDsl
val Int.minutes: TimeDuration
    get() = TimeDuration.minutes(this)

@FrameworkDsl
val Int.seconds: TimeDuration
    get() = TimeDuration.seconds(this)

@FrameworkDsl
val Int.milliseconds: TimeDuration
    get() = TimeDuration.milliseconds(this)

@FrameworkDsl
val Int.nanoseconds: TimeDuration
    get() = TimeDuration.nanoseconds(this)

@FrameworkDsl
val Long.day: TimeDuration
    get() = TimeDuration.days(this)

@FrameworkDsl
val Long.hour: TimeDuration
    get() = TimeDuration.hours(this)

@FrameworkDsl
val Long.week: TimeDuration
    get() = TimeDuration.weeks(this)

@FrameworkDsl
val Long.year: TimeDuration
    get() = TimeDuration.years(this)

@FrameworkDsl
val Long.minute: TimeDuration
    get() = TimeDuration.minutes(this)

@FrameworkDsl
val Long.second: TimeDuration
    get() = TimeDuration.seconds(this)

@FrameworkDsl
val Long.millisecond: TimeDuration
    get() = TimeDuration.milliseconds(this)

@FrameworkDsl
val Long.nanosecond: TimeDuration
    get() = TimeDuration.nanoseconds(this)

@FrameworkDsl
val Long.days: TimeDuration
    get() = TimeDuration.days(this)

@FrameworkDsl
val Long.hours: TimeDuration
    get() = TimeDuration.hours(this)

@FrameworkDsl
val Long.weeks: TimeDuration
    get() = TimeDuration.weeks(this)

@FrameworkDsl
val Long.years: TimeDuration
    get() = TimeDuration.years(this)

@FrameworkDsl
val Long.minutes: TimeDuration
    get() = TimeDuration.minutes(this)

@FrameworkDsl
val Long.seconds: TimeDuration
    get() = TimeDuration.seconds(this)

@FrameworkDsl
val Long.milliseconds: TimeDuration
    get() = TimeDuration.milliseconds(this)

@FrameworkDsl
val Long.nanoseconds: TimeDuration
    get() = TimeDuration.nanoseconds(this)

@FrameworkDsl
val Short.day: TimeDuration
    get() = TimeDuration.days(this)

@FrameworkDsl
val Short.hour: TimeDuration
    get() = TimeDuration.hours(this)

@FrameworkDsl
val Short.week: TimeDuration
    get() = TimeDuration.weeks(this)

@FrameworkDsl
val Short.year: TimeDuration
    get() = TimeDuration.years(this)

@FrameworkDsl
val Short.minute: TimeDuration
    get() = TimeDuration.minutes(this)

@FrameworkDsl
val Short.second: TimeDuration
    get() = TimeDuration.seconds(this)

@FrameworkDsl
val Short.millisecond: TimeDuration
    get() = TimeDuration.milliseconds(this)

@FrameworkDsl
val Short.nanosecond: TimeDuration
    get() = TimeDuration.nanoseconds(this)

@FrameworkDsl
val Short.days: TimeDuration
    get() = TimeDuration.days(this)

@FrameworkDsl
val Short.hours: TimeDuration
    get() = TimeDuration.hours(this)

@FrameworkDsl
val Short.weeks: TimeDuration
    get() = TimeDuration.weeks(this)

@FrameworkDsl
val Short.years: TimeDuration
    get() = TimeDuration.years(this)

@FrameworkDsl
val Short.minutes: TimeDuration
    get() = TimeDuration.minutes(this)

@FrameworkDsl
val Short.seconds: TimeDuration
    get() = TimeDuration.seconds(this)

@FrameworkDsl
val Short.milliseconds: TimeDuration
    get() = TimeDuration.milliseconds(this)

@FrameworkDsl
val Short.nanoseconds: TimeDuration
    get() = TimeDuration.nanoseconds(this)

@FrameworkDsl
val Float.days: TimeDuration
    get() = TimeDuration.days(this)

@FrameworkDsl
val Float.hours: TimeDuration
    get() = TimeDuration.hours(this)

@FrameworkDsl
val Float.weeks: TimeDuration
    get() = TimeDuration.weeks(this)

@FrameworkDsl
val Float.years: TimeDuration
    get() = TimeDuration.years(this)

@FrameworkDsl
val Float.minutes: TimeDuration
    get() = TimeDuration.minutes(this)

@FrameworkDsl
val Float.seconds: TimeDuration
    get() = TimeDuration.seconds(this)

@FrameworkDsl
val Float.milliseconds: TimeDuration
    get() = TimeDuration.milliseconds(this)

@FrameworkDsl
val Float.nanoseconds: TimeDuration
    get() = TimeDuration.nanoseconds(this)

@FrameworkDsl
val Double.days: TimeDuration
    get() = TimeDuration.days(this)

@FrameworkDsl
val Double.hours: TimeDuration
    get() = TimeDuration.hours(this)

@FrameworkDsl
val Double.weeks: TimeDuration
    get() = TimeDuration.weeks(this)

@FrameworkDsl
val Double.years: TimeDuration
    get() = TimeDuration.years(this)

@FrameworkDsl
val Double.minutes: TimeDuration
    get() = TimeDuration.minutes(this)

@FrameworkDsl
val Double.seconds: TimeDuration
    get() = TimeDuration.seconds(this)

@FrameworkDsl
val Double.milliseconds: TimeDuration
    get() = TimeDuration.milliseconds(this)

@FrameworkDsl
val Double.nanoseconds: TimeDuration
    get() = TimeDuration.nanoseconds(this)

@FrameworkDsl
operator fun Int.times(value: TimeDuration): TimeDuration = value.times(this)

@FrameworkDsl
operator fun Long.times(value: TimeDuration): TimeDuration = value.times(this)

@FrameworkDsl
operator fun Short.times(value: TimeDuration): TimeDuration = value.times(this)

@FrameworkDsl
operator fun Float.times(value: TimeDuration): TimeDuration = value.times(toFiniteOrElse(1.0))

@FrameworkDsl
operator fun Double.times(value: TimeDuration): TimeDuration = value.times(toFiniteOrElse(1.0))

@FrameworkDsl
inline fun TimeDuration.getNanoSeconds(): Long = duration().toNanos()

@FrameworkDsl
inline fun TimeDuration.getMilliSeconds(): Long = duration().toMillis()

@FrameworkDsl
fun Date?.copyOf(): Date = when (this) {
    null -> Date()
    else -> Date(toLong())
}

@FrameworkDsl
inline fun Date.toLong(): Long = time

@FrameworkDsl
inline fun Long.toDate(): Date = Date(this)

@FrameworkDsl
inline fun dateOf(): Date = Date()

@FrameworkDsl
operator fun Date.plus(value: TimeDuration): Date = Date(toLong() + value.getMilliSeconds())

@FrameworkDsl
operator fun Date.minus(value: TimeDuration): Date = Date(toLong() - value.getMilliSeconds())

@FrameworkDsl
operator fun LocalDateTime.plus(value: TimeDuration): LocalDateTime = plus(value.duration())

@FrameworkDsl
operator fun LocalDateTime.minus(value: TimeDuration): LocalDateTime = minus(value.duration())

@FrameworkDsl
fun dateTimeOf(zone: ZoneId = TimeAndDate.getDefaultTimeZoneId()): LocalDateTime = TimeAndDate.dateTimeOf(zone)

@FrameworkDsl
fun getTimeStamp(nano: Boolean = false): Long = TimeAndDate.getTimeStamp(nano)

@FrameworkDsl
fun Date.convertTo(zone: ZoneId = TimeAndDate.getDefaultTimeZoneId()): LocalDateTime = TimeAndDate.convertTo(this, zone)

@FrameworkDsl
fun LocalDateTime.convertTo(zone: ZoneId = TimeAndDate.getDefaultTimeZoneId()): Date = TimeAndDate.convertTo(this, zone)

@FrameworkDsl
fun Date.formatDate(safe: Boolean = true): String = TimeAndDate.formatDate(this, safe)

@FrameworkDsl
fun LocalDateTime.formatDate(zone: ZoneId = TimeAndDate.getDefaultTimeZoneId()): String = TimeAndDate.formatDate(this, zone)

@FrameworkDsl
fun CharSequence.parseDate(): Date = TimeAndDate.parseDate(this, true)

@FrameworkDsl
fun String.parseDate(zone: ZoneId = TimeAndDate.getDefaultTimeZoneId()): LocalDateTime = TimeAndDate.parseDate(this, zone)

@FrameworkDsl
fun <T> timed(after: (String) -> Unit, block: () -> T): T = NanoTicker().let { block().also { after(it(false)) } }

@FrameworkDsl
fun elapsed(nano: Boolean = true, block: () -> Unit): Long {
    val time = getTimeStamp(nano)
    block()
    return getTimeStamp(nano) - time
}
