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
import java.text.SimpleDateFormat
import java.time.*
import java.time.format.*
import java.util.*

object TimeAndDate {

    private val formatter: DateTimeFormatter by lazy {
        DateTimeFormatterBuilder().appendPattern(TIME_DEFAULT_DATE_FORMAT).toFormatter()
    }

    @JvmStatic
    @CreatorsDsl
    @JvmOverloads
    fun dateTimeOf(zone: ZoneId = getDefaultTimeZoneId()): LocalDateTime = LocalDateTime.now(zone)

    @JvmStatic
    @CreatorsDsl
    fun nanos(): Long = System.nanoTime()

    @JvmStatic
    @CreatorsDsl
    fun nanosOf(): () -> Long = TimeAndDate::nanos

    @JvmStatic
    @CreatorsDsl
    fun mills(): Long = System.currentTimeMillis()

    @JvmStatic
    @CreatorsDsl
    fun millsOf(): () -> Long = TimeAndDate::mills

    @JvmStatic
    @CreatorsDsl
    @JvmOverloads
    fun getTimeStamp(nano: Boolean = false): Long = if (nano) nanos() else mills()

    @JvmStatic
    @CreatorsDsl
    fun getDefaultTimeZoneId(): ZoneId = ZoneId.of(TIME_DEFAULT_ZONE_STRING)

    @JvmStatic
    @CreatorsDsl
    fun getDefaultTimeZone(): TimeZone = TimeZone.getTimeZone(TIME_DEFAULT_ZONE_STRING)

    @JvmStatic
    @CreatorsDsl
    @JvmOverloads
    fun setDefaultTimeZone(zone: TimeZone = getDefaultTimeZone()) {
        TimeZone.setDefault(zone)
    }

    @JvmStatic
    @CreatorsDsl
    fun getDefaultDateFormat(): SimpleDateFormat = SimpleDateFormat(TIME_DEFAULT_DATE_FORMAT)

    @JvmStatic
    @CreatorsDsl
    fun getDefaultDateFormat(zone: TimeZone): SimpleDateFormat = getDefaultDateFormat().also { it.timeZone = zone }

    @JvmStatic
    @CreatorsDsl
    @JvmOverloads
    fun getDefaultDateFormatAndTimeZone(zone: TimeZone = getDefaultTimeZone()): SimpleDateFormat = getDefaultDateFormat(zone)

    @JvmStatic
    @CreatorsDsl
    @JvmOverloads
    fun convertTo(date: Date, zone: ZoneId = getDefaultTimeZoneId()): LocalDateTime {
        return LocalDateTime.ofInstant(date.toInstant(), zone)
    }

    @JvmStatic
    @CreatorsDsl
    @JvmOverloads
    fun convertTo(date: LocalDateTime, zone: ZoneId = getDefaultTimeZoneId()): Date {
        return Date.from(date.atZone(zone).toInstant())
    }

    @JvmStatic
    @CreatorsDsl
    @JvmOverloads
    fun formatDate(date: Date, safe: Boolean = true): String {
        return if (safe) getThreadLocalDefaultDateFormat().toValue().format(date) else getDefaultDateFormatAndTimeZone().format(date)
    }

    @JvmStatic
    @CreatorsDsl
    @JvmOverloads
    fun formatDate(date: LocalDateTime, zone: ZoneId = getDefaultTimeZoneId()): String {
        return formatter.withZone(zone).format(date)
    }

    @JvmStatic
    @CreatorsDsl
    @JvmOverloads
    fun parseDate(text: CharSequence, safe: Boolean = true): Date {
        return if (safe) getThreadLocalDefaultDateFormat().toValue().parse(text.toString()) else getDefaultDateFormatAndTimeZone().parse(text.toString())
    }

    @JvmStatic
    @CreatorsDsl
    @JvmOverloads
    fun parseDate(text: CharSequence, zone: ZoneId = getDefaultTimeZoneId()): LocalDateTime {
        return LocalDateTime.parse(text, formatter.withZone(zone))
    }

    @JvmStatic
    @CreatorsDsl
    @JvmOverloads
    fun getThreadLocalDefaultDateFormat(zone: TimeZone = getDefaultTimeZone()): ThreadLocal<SimpleDateFormat> = ThreadLocal.withInitial { getDefaultDateFormat(zone) }

    @JvmStatic
    @CreatorsDsl
    @JvmOverloads
    fun toDecimalPlaces(data: Double, tail: String = EMPTY_STRING, places: Int = Numeric.DEFAULT_PRECISION_SCALE): String = data.toDecimalPlacesString(places) + tail

    @JvmStatic
    @CreatorsDsl
    @JvmOverloads
    fun toElapsedString(data: Long, head: String = "elapsed "): String = head + if (data < 1000000L) "$data nanoseconds" else if (data < 1000000000L) toDecimalPlaces(1.0E-6 * data, " milliseconds") else toDecimalPlaces(1.0E-9 * data, " seconds")
}