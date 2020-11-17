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

@file:kotlin.jvm.JvmName("TimeKt")
@file:Suppress("NOTHING_TO_INLINE", "UNCHECKED_CAST")

package co.mercenary.creators.kotlin.util

import co.mercenary.creators.kotlin.util.time.NanoTicker
import java.time.*
import java.util.*

@CreatorsDsl
const val TIME_DEFAULT_ZONE_STRING = "UTC"

@CreatorsDsl
const val TIME_DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss,SSS z"

typealias TimeAndDate = co.mercenary.creators.kotlin.util.time.TimeAndDate

typealias TimeDuration = co.mercenary.creators.kotlin.util.time.TimeDuration

typealias TimeDurationUnit = co.mercenary.creators.kotlin.util.time.TimeDurationUnit

@CreatorsDsl
val Int.day: TimeDuration
    get() = TimeDuration.days(this)

@CreatorsDsl
val Int.hour: TimeDuration
    get() = TimeDuration.hours(this)

@CreatorsDsl
val Int.week: TimeDuration
    get() = TimeDuration.weeks(this)

@CreatorsDsl
val Int.year: TimeDuration
    get() = TimeDuration.years(this)

@CreatorsDsl
val Int.minute: TimeDuration
    get() = TimeDuration.minutes(this)

@CreatorsDsl
val Int.second: TimeDuration
    get() = TimeDuration.seconds(this)

@CreatorsDsl
val Int.millisecond: TimeDuration
    get() = TimeDuration.milliseconds(this)

@CreatorsDsl
val Int.nanosecond: TimeDuration
    get() = TimeDuration.nanoseconds(this)

@CreatorsDsl
val Int.days: TimeDuration
    get() = TimeDuration.days(this)

@CreatorsDsl
val Int.hours: TimeDuration
    get() = TimeDuration.hours(this)

@CreatorsDsl
val Int.weeks: TimeDuration
    get() = TimeDuration.weeks(this)

@CreatorsDsl
val Int.years: TimeDuration
    get() = TimeDuration.years(this)

@CreatorsDsl
val Int.minutes: TimeDuration
    get() = TimeDuration.minutes(this)

@CreatorsDsl
val Int.seconds: TimeDuration
    get() = TimeDuration.seconds(this)

@CreatorsDsl
val Int.milliseconds: TimeDuration
    get() = TimeDuration.milliseconds(this)

@CreatorsDsl
val Int.nanoseconds: TimeDuration
    get() = TimeDuration.nanoseconds(this)

@CreatorsDsl
val Long.day: TimeDuration
    get() = TimeDuration.days(this)

@CreatorsDsl
val Long.hour: TimeDuration
    get() = TimeDuration.hours(this)

@CreatorsDsl
val Long.week: TimeDuration
    get() = TimeDuration.weeks(this)

@CreatorsDsl
val Long.year: TimeDuration
    get() = TimeDuration.years(this)

@CreatorsDsl
val Long.minute: TimeDuration
    get() = TimeDuration.minutes(this)

@CreatorsDsl
val Long.second: TimeDuration
    get() = TimeDuration.seconds(this)

@CreatorsDsl
val Long.millisecond: TimeDuration
    get() = TimeDuration.milliseconds(this)

@CreatorsDsl
val Long.nanosecond: TimeDuration
    get() = TimeDuration.nanoseconds(this)

@CreatorsDsl
val Long.days: TimeDuration
    get() = TimeDuration.days(this)

@CreatorsDsl
val Long.hours: TimeDuration
    get() = TimeDuration.hours(this)

@CreatorsDsl
val Long.weeks: TimeDuration
    get() = TimeDuration.weeks(this)

@CreatorsDsl
val Long.years: TimeDuration
    get() = TimeDuration.years(this)

@CreatorsDsl
val Long.minutes: TimeDuration
    get() = TimeDuration.minutes(this)

@CreatorsDsl
val Long.seconds: TimeDuration
    get() = TimeDuration.seconds(this)

@CreatorsDsl
val Long.milliseconds: TimeDuration
    get() = TimeDuration.milliseconds(this)

@CreatorsDsl
val Long.nanoseconds: TimeDuration
    get() = TimeDuration.nanoseconds(this)

@CreatorsDsl
val Short.day: TimeDuration
    get() = TimeDuration.days(this)

@CreatorsDsl
val Short.hour: TimeDuration
    get() = TimeDuration.hours(this)

@CreatorsDsl
val Short.week: TimeDuration
    get() = TimeDuration.weeks(this)

@CreatorsDsl
val Short.year: TimeDuration
    get() = TimeDuration.years(this)

@CreatorsDsl
val Short.minute: TimeDuration
    get() = TimeDuration.minutes(this)

@CreatorsDsl
val Short.second: TimeDuration
    get() = TimeDuration.seconds(this)

@CreatorsDsl
val Short.millisecond: TimeDuration
    get() = TimeDuration.milliseconds(this)

@CreatorsDsl
val Short.nanosecond: TimeDuration
    get() = TimeDuration.nanoseconds(this)

@CreatorsDsl
val Short.days: TimeDuration
    get() = TimeDuration.days(this)

@CreatorsDsl
val Short.hours: TimeDuration
    get() = TimeDuration.hours(this)

@CreatorsDsl
val Short.weeks: TimeDuration
    get() = TimeDuration.weeks(this)

@CreatorsDsl
val Short.years: TimeDuration
    get() = TimeDuration.years(this)

@CreatorsDsl
val Short.minutes: TimeDuration
    get() = TimeDuration.minutes(this)

@CreatorsDsl
val Short.seconds: TimeDuration
    get() = TimeDuration.seconds(this)

@CreatorsDsl
val Short.milliseconds: TimeDuration
    get() = TimeDuration.milliseconds(this)

@CreatorsDsl
val Short.nanoseconds: TimeDuration
    get() = TimeDuration.nanoseconds(this)

@CreatorsDsl
val Float.days: TimeDuration
    get() = TimeDuration.days(this)

@CreatorsDsl
val Float.hours: TimeDuration
    get() = TimeDuration.hours(this)

@CreatorsDsl
val Float.weeks: TimeDuration
    get() = TimeDuration.weeks(this)

@CreatorsDsl
val Float.years: TimeDuration
    get() = TimeDuration.years(this)

@CreatorsDsl
val Float.minutes: TimeDuration
    get() = TimeDuration.minutes(this)

@CreatorsDsl
val Float.seconds: TimeDuration
    get() = TimeDuration.seconds(this)

@CreatorsDsl
val Float.milliseconds: TimeDuration
    get() = TimeDuration.milliseconds(this)

@CreatorsDsl
val Float.nanoseconds: TimeDuration
    get() = TimeDuration.nanoseconds(this)

@CreatorsDsl
val Double.days: TimeDuration
    get() = TimeDuration.days(this)

@CreatorsDsl
val Double.hours: TimeDuration
    get() = TimeDuration.hours(this)

@CreatorsDsl
val Double.weeks: TimeDuration
    get() = TimeDuration.weeks(this)

@CreatorsDsl
val Double.years: TimeDuration
    get() = TimeDuration.years(this)

@CreatorsDsl
val Double.minutes: TimeDuration
    get() = TimeDuration.minutes(this)

@CreatorsDsl
val Double.seconds: TimeDuration
    get() = TimeDuration.seconds(this)

@CreatorsDsl
val Double.milliseconds: TimeDuration
    get() = TimeDuration.milliseconds(this)

@CreatorsDsl
val Double.nanoseconds: TimeDuration
    get() = TimeDuration.nanoseconds(this)

@CreatorsDsl
inline fun TimeDuration.getNanoSeconds(): Long = duration().toNanos()

@CreatorsDsl
inline fun TimeDuration.getMilliSeconds(): Long = duration().toMillis()

@CreatorsDsl
fun Date?.copyOf(): Date = when (this) {
    null -> Date()
    else -> Date(toLong())
}

@CreatorsDsl
fun Date.toLong(): Long = time

@CreatorsDsl
fun Long.toDate(): Date = Date(this)

@CreatorsDsl
fun dateOf(): Date = Date()

@CreatorsDsl
operator fun Date.plus(value: TimeDuration): Date = Date(toLong() + value.getMilliSeconds())

@CreatorsDsl
operator fun Date.minus(value: TimeDuration): Date = Date(toLong() - value.getMilliSeconds())

@CreatorsDsl
operator fun LocalDateTime.plus(value: TimeDuration): LocalDateTime = plus(value.duration())

@CreatorsDsl
operator fun LocalDateTime.minus(value: TimeDuration): LocalDateTime = minus(value.duration())

@CreatorsDsl
fun dateTimeOf(zone: ZoneId = TimeAndDate.getDefaultTimeZoneId()): LocalDateTime = TimeAndDate.dateTimeOf(zone)

@CreatorsDsl
fun getTimeStamp(nano: Boolean = false): Long = TimeAndDate.getTimeStamp(nano)

@CreatorsDsl
fun Date.convertTo(zone: ZoneId = TimeAndDate.getDefaultTimeZoneId()): LocalDateTime = TimeAndDate.convertTo(this, zone)

@CreatorsDsl
fun LocalDateTime.convertTo(zone: ZoneId = TimeAndDate.getDefaultTimeZoneId()): Date = TimeAndDate.convertTo(this, zone)

@CreatorsDsl
fun Date.formatDate(safe: Boolean = true): String = TimeAndDate.formatDate(this, safe)

@CreatorsDsl
fun LocalDateTime.formatDate(zone: ZoneId = TimeAndDate.getDefaultTimeZoneId()): String = TimeAndDate.formatDate(this, zone)

@CreatorsDsl
fun CharSequence.parseDate(): Date = TimeAndDate.parseDate(this, true)

@CreatorsDsl
fun String.parseDate(zone: ZoneId = TimeAndDate.getDefaultTimeZoneId()): LocalDateTime = TimeAndDate.parseDate(this, zone)

@CreatorsDsl
fun <T> timed(after: (String) -> Unit, block: () -> T): T = NanoTicker().let { block().also { after(it(false)) } }
