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
import java.text.SimpleDateFormat
import java.time.*
import java.time.format.*
import java.util.*

object TimeAndDate {

    private val formatter: DateTimeFormatter by lazy {
        DateTimeFormatterBuilder().appendPattern(TIME_DEFAULT_DATE_FORMAT).toFormatter()
    }

    @JvmStatic
    @JvmOverloads
    fun dateTimeOf(zone: ZoneId = getDefaultTimeZoneId()): LocalDateTime = LocalDateTime.now(zone)

    @JvmStatic
    fun nanos(): Long = System.nanoTime()

    @JvmStatic
    fun nanosOf(): () -> Long = System::nanoTime

    @JvmStatic
    fun mills(): Long = System.currentTimeMillis()

    @JvmStatic
    fun millsOf(): () -> Long = System::currentTimeMillis

    @JvmStatic
    @JvmOverloads
    fun getTimeStamp(nano: Boolean = false): Long = if (nano) nanos() else mills()

    @JvmStatic
    fun getDefaultTimeZoneId(): ZoneId = ZoneId.of(TIME_DEFAULT_ZONE_STRING)

    @JvmStatic
    fun getDefaultTimeZone(): TimeZone = TimeZone.getTimeZone(TIME_DEFAULT_ZONE_STRING)

    @JvmStatic
    @JvmOverloads
    fun setDefaultTimeZone(zone: TimeZone = getDefaultTimeZone()) {
        TimeZone.setDefault(zone)
    }

    @JvmStatic
    fun getDefaultDateFormat(): SimpleDateFormat = SimpleDateFormat(TIME_DEFAULT_DATE_FORMAT)

    @JvmStatic
    fun getDefaultDateFormat(zone: TimeZone): SimpleDateFormat = getDefaultDateFormat().also { it.timeZone = zone }

    @JvmStatic
    @JvmOverloads
    fun getDefaultDateFormatAndTimeZone(zone: TimeZone = getDefaultTimeZone()): SimpleDateFormat = getDefaultDateFormat(zone)

    @JvmStatic
    @JvmOverloads
    fun formatDate(date: Date, safe: Boolean = true): String {
        return if (safe) getThreadLocalDefaultDateFormat().get().format(date) else getDefaultDateFormatAndTimeZone().format(date)
    }

    @JvmStatic
    @JvmOverloads
    fun formatDate(date: LocalDateTime, zone: ZoneId = getDefaultTimeZoneId()): String {
        return formatter.withZone(zone).format(date)
    }

    @JvmStatic
    @JvmOverloads
    fun parseDate(text: CharSequence, safe: Boolean = true): Date {
        return if (safe) getThreadLocalDefaultDateFormat().get().parse(text.toString()) else getDefaultDateFormatAndTimeZone().parse(text.toString())
    }

    @JvmStatic
    @JvmOverloads
    fun parseDate(text: CharSequence, zone: ZoneId = getDefaultTimeZoneId()): LocalDateTime {
        return LocalDateTime.parse(text, formatter.withZone(zone))
    }

    @JvmStatic
    @JvmOverloads
    fun getThreadLocalDefaultDateFormat(zone: TimeZone = getDefaultTimeZone()): ThreadLocal<SimpleDateFormat> = ThreadLocal.withInitial { getDefaultDateFormat(zone) }

    @JvmStatic
    @JvmOverloads
    fun toDecimalPlaces(data: Double, tail: String = EMPTY_STRING, places: Int = 3): String = data.toDecimalPlacesString(places) + tail

    @JvmStatic
    @JvmOverloads
    fun toElapsedString(data: Long, head: String = "elapsed "): String = head + if (data < 1000000L) "$data nanoseconds" else if (data < 1000000000L) toDecimalPlaces(1.0E-6 * data, " milliseconds") else toDecimalPlaces(1.0E-9 * data, " seconds")
}