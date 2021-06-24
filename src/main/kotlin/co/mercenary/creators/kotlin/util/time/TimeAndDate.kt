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

package co.mercenary.creators.kotlin.util.time

import co.mercenary.creators.kotlin.util.*
import java.text.SimpleDateFormat
import java.time.*
import java.time.format.*
import java.util.*

@IgnoreForSerialize
object TimeAndDate : HasMapNames {

    @FrameworkDsl
    private val formatter: DateTimeFormatter by lazy {
        DateTimeFormatterBuilder().appendPattern(TIME_DEFAULT_DATE_FORMAT).toFormatter()
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun dateTimeOf(zone: ZoneId = getDefaultTimeZoneId()): LocalDateTime = LocalDateTime.now(zone)

    @JvmStatic
    @FrameworkDsl
    fun nanos(): Long = System.nanoTime()

    @JvmStatic
    @FrameworkDsl
    fun nanosOf(): () -> Long = TimeAndDate::nanos

    @JvmStatic
    @FrameworkDsl
    fun mills(): Long = System.currentTimeMillis()

    @JvmStatic
    @FrameworkDsl
    fun millsOf(): () -> Long = TimeAndDate::mills

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    @IgnoreForSerialize
    fun getTimeStamp(nano: Boolean = false): Long = if (nano) nanos() else mills()

    @JvmStatic
    @FrameworkDsl
    @IgnoreForSerialize
    fun getDefaultTimeZoneString(): String {
        return TIME_DEFAULT_ZONE_STRING
    }

    @JvmStatic
    @FrameworkDsl
    @IgnoreForSerialize
    fun getDefaultDateFormatString(): String {
        return TIME_DEFAULT_DATE_FORMAT
    }

    @JvmStatic
    @FrameworkDsl
    @IgnoreForSerialize
    fun getDefaultTimeZoneId(): ZoneId = ZoneId.of(getDefaultTimeZoneString())

    @JvmStatic
    @FrameworkDsl
    @IgnoreForSerialize
    fun getDefaultTimeZone(): TimeZone = TimeZone.getTimeZone(getDefaultTimeZoneString())

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    @IgnoreForSerialize
    fun setDefaultTimeZone(zone: TimeZone = getDefaultTimeZone()) {
        TimeZone.setDefault(zone)
    }

    @JvmStatic
    @FrameworkDsl
    @IgnoreForSerialize
    fun getDefaultDateFormat(): SimpleDateFormat = SimpleDateFormat(getDefaultDateFormatString())

    @JvmStatic
    @FrameworkDsl
    fun getDefaultDateFormat(zone: TimeZone): SimpleDateFormat = getDefaultDateFormat().also { it.timeZone = zone }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    @IgnoreForSerialize
    fun getDefaultDateFormatAndTimeZone(zone: TimeZone = getDefaultTimeZone()): SimpleDateFormat = getDefaultDateFormat(zone)

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun convertTo(date: Date, zone: ZoneId = getDefaultTimeZoneId()): LocalDateTime {
        return LocalDateTime.ofInstant(date.toInstant(), zone)
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun convertTo(date: LocalDateTime, zone: ZoneId = getDefaultTimeZoneId()): Date {
        return Date.from(date.atZone(zone).toInstant())
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun formatDate(date: Date, safe: Boolean = true): String {
        return if (safe) getThreadLocalDefaultDateFormat().toValue().format(date) else getDefaultDateFormatAndTimeZone().format(date)
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun formatDate(date: LocalDateTime, zone: ZoneId = getDefaultTimeZoneId()): String {
        return formatter.withZone(zone).format(date)
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun parseDate(text: CharSequence, safe: Boolean = true): Date {
        return if (safe) getThreadLocalDefaultDateFormat().toValue().parse(text.copyOf()) else getDefaultDateFormatAndTimeZone().parse(text.copyOf())
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun parseDate(text: CharSequence, zone: ZoneId = getDefaultTimeZoneId()): LocalDateTime {
        return LocalDateTime.parse(text, formatter.withZone(zone))
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    @IgnoreForSerialize
    fun getThreadLocalDefaultDateFormat(zone: TimeZone = getDefaultTimeZone()): ThreadLocal<SimpleDateFormat> = ThreadLocal.withInitial { getDefaultDateFormat(zone) }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun toDecimalPlaces(data: Double, tail: String = EMPTY_STRING, places: Int = Numeric.DEFAULT_PRECISION_SCALE): String = data.toDecimalPlacesString(places) + tail

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun toElapsedString(data: Long, head: String = "elapsed "): String = head + if (data < 1000000L) "$data nanoseconds" else if (data < 1000000000L) toDecimalPlaces(1.0E-6 * data, " milliseconds") else toDecimalPlaces(1.0E-9 * data, " seconds")

    @FrameworkDsl
    override fun toString() = toMapNames().toSafeString()

    @FrameworkDsl
    override fun toMapNames() = dictOf("type" to nameOf())
}