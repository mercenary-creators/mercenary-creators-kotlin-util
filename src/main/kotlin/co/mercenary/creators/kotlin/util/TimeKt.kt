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

@file:kotlin.jvm.JvmName("MainKt")
@file:kotlin.jvm.JvmMultifileClass

package co.mercenary.creators.kotlin.util

import java.util.*
import kotlin.math.max
import co.mercenary.creators.kotlin.util.time.NanoTicker

const val DEFAULT_ZONE_STRING = "UTC"

const val DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss,SSS z"

typealias TimeUnit = java.util.concurrent.TimeUnit

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

fun Date?.copyOf(): Date = when (this) {
    null -> Date()
    else -> Date(time)
}

fun Date.toLong(): Long = this.time

fun Long.toDate(): Date = Date(max(this, 0))

fun <T> timed(after: (String) -> Unit, block: () -> T): T = NanoTicker().let { block().also { after(it(false)) } }

@JvmOverloads
fun getTimeStamp(nano: Boolean = false): Long = if (nano) System.nanoTime() else System.currentTimeMillis()

@JvmOverloads
fun toDecimalPlaces3(data: Double, tail: String = EMPTY_STRING): String = "(%.3f)%s".format(data, tail)

fun toElapsedString(data: Long): String = if (data < 1000000L) "($data) nanoseconds" else if (data < 1000000000L) toDecimalPlaces3(1.0E-6 * data, " milliseconds") else toDecimalPlaces3(1.0E-9 * data, " seconds")