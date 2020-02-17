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

package co.mercenary.creators.kotlin.util.logging

import co.mercenary.creators.kotlin.util.time.TimeAndDate
import co.mercenary.creators.kotlin.util.toList
import reactor.core.publisher.Flux
import java.time.LocalDateTime
import java.util.*
import java.util.concurrent.atomic.AtomicBoolean

class DefaultLoggingStringFormatter : LoggingStringFormatter(Int.MIN_VALUE) {

    private val safe = { data: Any? -> LoggingFactory.toSafeString { data } }

    override fun toSafeString(data: Any): String {
        return when (data) {
            is Flux<*> -> data.toList().joinToString(prefix = "[", postfix = "]", transform = safe)
            is DoubleArray -> data.joinToString(prefix = "[", postfix = "]", transform = safe)
            is Iterable<*> -> data.joinToString(prefix = "[", postfix = "]", transform = safe)
            is Sequence<*> -> data.joinToString(prefix = "[", postfix = "]", transform = safe)
            is Date -> TimeAndDate.formatDate(data)
            is LocalDateTime -> TimeAndDate.formatDate(data)
            is Function0<*> -> LoggingFactory.toSafeString(data)
            else -> data.toString()
        }
    }

    override fun isValidClass(data: Any): Boolean {
        return when (data) {
            is String -> true
            is Number -> true
            is Boolean -> true
            is Iterable<*> -> true
            is Sequence<*> -> true
            is DoubleArray -> true
            is Date -> true
            is LocalDateTime -> true
            is AtomicBoolean -> true
            is Function0<*> -> true
            is Flux<*> -> true
            else -> false
        }
    }
}