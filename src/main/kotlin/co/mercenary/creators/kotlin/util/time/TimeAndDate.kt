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
import java.util.*

object TimeAndDate {

    @JvmStatic
    fun getDefaultTimeZone(): TimeZone = TimeZone.getTimeZone(DEFAULT_ZONE_STRING)

    @JvmStatic
    fun setDefaultTimeZone(zone: TimeZone = getDefaultTimeZone()) {
        TimeZone.setDefault(zone)
    }

    @JvmStatic
    fun getDefaultDateFormat(): SimpleDateFormat = SimpleDateFormat(DEFAULT_DATE_FORMAT)

    @JvmStatic
    fun getDefaultDateFormat(zone: TimeZone): SimpleDateFormat = getDefaultDateFormat().also { it.timeZone = zone }

    @JvmStatic
    fun getThreadLocalDefaultDateFormat(zone: TimeZone = getDefaultTimeZone()): ThreadLocal<SimpleDateFormat> = ThreadLocal.withInitial { getDefaultDateFormat(zone) }
}