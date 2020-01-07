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

@file:kotlin.jvm.JvmName("MainKt")
@file:kotlin.jvm.JvmMultifileClass

package co.mercenary.creators.kotlin.util

import co.mercenary.creators.kotlin.util.time.NanoTicker
import java.time.*
import java.util.*

const val TIME_DEFAULT_ZONE_STRING = "UTC"

const val TIME_DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss,SSS z"

typealias TimeAndDate = co.mercenary.creators.kotlin.util.time.TimeAndDate

typealias TimeDuration = co.mercenary.creators.kotlin.util.time.TimeDuration

typealias TimeDurationUnit = co.mercenary.creators.kotlin.util.time.TimeDurationUnit

val Int.day: TimeDuration
    get() = TimeDuration.days(this)

val Int.hour: TimeDuration
    get() = TimeDuration.hours(this)

val Int.week: TimeDuration
    get() = TimeDuration.weeks(this)

val Int.year: TimeDuration
    get() = TimeDuration.years(this)

val Int.minute: TimeDuration
    get() = TimeDuration.minutes(this)

val Int.second: TimeDuration
    get() = TimeDuration.seconds(this)

val Int.millisecond: TimeDuration
    get() = TimeDuration.milliseconds(this)

val Int.nanosecond: TimeDuration
    get() = TimeDuration.nanoseconds(this)

val Int.days: TimeDuration
    get() = TimeDuration.days(this)

val Int.hours: TimeDuration
    get() = TimeDuration.hours(this)

val Int.weeks: TimeDuration
    get() = TimeDuration.weeks(this)

val Int.years: TimeDuration
    get() = TimeDuration.years(this)

val Int.minutes: TimeDuration
    get() = TimeDuration.minutes(this)

val Int.seconds: TimeDuration
    get() = TimeDuration.seconds(this)

val Int.milliseconds: TimeDuration
    get() = TimeDuration.milliseconds(this)

val Int.nanoseconds: TimeDuration
    get() = TimeDuration.nanoseconds(this)

val Long.day: TimeDuration
    get() = TimeDuration.days(this)

val Long.hour: TimeDuration
    get() = TimeDuration.hours(this)

val Long.week: TimeDuration
    get() = TimeDuration.weeks(this)

val Long.year: TimeDuration
    get() = TimeDuration.years(this)

val Long.minute: TimeDuration
    get() = TimeDuration.minutes(this)

val Long.second: TimeDuration
    get() = TimeDuration.seconds(this)

val Long.millisecond: TimeDuration
    get() = TimeDuration.milliseconds(this)

val Long.nanosecond: TimeDuration
    get() = TimeDuration.nanoseconds(this)

val Long.days: TimeDuration
    get() = TimeDuration.days(this)

val Long.hours: TimeDuration
    get() = TimeDuration.hours(this)

val Long.weeks: TimeDuration
    get() = TimeDuration.weeks(this)

val Long.years: TimeDuration
    get() = TimeDuration.years(this)

val Long.minutes: TimeDuration
    get() = TimeDuration.minutes(this)

val Long.seconds: TimeDuration
    get() = TimeDuration.seconds(this)

val Long.milliseconds: TimeDuration
    get() = TimeDuration.milliseconds(this)

val Long.nanoseconds: TimeDuration
    get() = TimeDuration.nanoseconds(this)

val Float.days: TimeDuration
    get() = TimeDuration.days(this)

val Float.hours: TimeDuration
    get() = TimeDuration.hours(this)

val Float.weeks: TimeDuration
    get() = TimeDuration.weeks(this)

val Float.years: TimeDuration
    get() = TimeDuration.years(this)

val Float.minutes: TimeDuration
    get() = TimeDuration.minutes(this)

val Float.seconds: TimeDuration
    get() = TimeDuration.seconds(this)

val Float.milliseconds: TimeDuration
    get() = TimeDuration.milliseconds(this)

val Float.nanoseconds: TimeDuration
    get() = TimeDuration.nanoseconds(this)

val Double.days: TimeDuration
    get() = TimeDuration.days(this)

val Double.hours: TimeDuration
    get() = TimeDuration.hours(this)

val Double.weeks: TimeDuration
    get() = TimeDuration.weeks(this)

val Double.years: TimeDuration
    get() = TimeDuration.years(this)

val Double.minutes: TimeDuration
    get() = TimeDuration.minutes(this)

val Double.seconds: TimeDuration
    get() = TimeDuration.seconds(this)

val Double.milliseconds: TimeDuration
    get() = TimeDuration.milliseconds(this)

val Double.nanoseconds: TimeDuration
    get() = TimeDuration.nanoseconds(this)

fun Date?.copyOf(): Date = when (this) {
    null -> Date()
    else -> Date(toLong())
}

fun Date.toLong(): Long = time

fun Long.toDate(): Date = Date(this)

fun dateOf(): Date = Date()

operator fun Date.plus(value: TimeDuration): Date = Date(toLong() + value.duration.toMillis())

operator fun Date.minus(value: TimeDuration): Date = Date(toLong() - value.duration.toMillis())

operator fun LocalDateTime.plus(value: TimeDuration): LocalDateTime = plus(value.duration)

operator fun LocalDateTime.minus(value: TimeDuration): LocalDateTime = minus(value.duration)

@JvmOverloads
fun dateTimeOf(zone: ZoneId = TimeAndDate.getDefaultTimeZoneId()): LocalDateTime = TimeAndDate.dateTimeOf(zone)

@JvmOverloads
fun getTimeStamp(nano: Boolean = false): Long = TimeAndDate.getTimeStamp(nano)

@JvmOverloads
fun Date.formatDate(safe: Boolean = true): String = TimeAndDate.formatDate(this, safe)

@JvmOverloads
fun LocalDateTime.formatDate(zone: ZoneId = TimeAndDate.getDefaultTimeZoneId()): String = TimeAndDate.formatDate(this, zone)

@JvmOverloads
fun CharSequence.parseDate(safe: Boolean = true): Date = TimeAndDate.parseDate(this, safe)

@JvmOverloads
fun CharSequence.parseDateTime(zone: ZoneId = TimeAndDate.getDefaultTimeZoneId()): LocalDateTime = TimeAndDate.parseDate(this, zone)

fun <T> timed(after: (String) -> Unit, block: () -> T): T = NanoTicker().let { block().also { after(it(false)) } }
