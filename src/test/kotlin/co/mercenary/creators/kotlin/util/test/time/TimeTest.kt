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

package co.mercenary.creators.kotlin.util.test.time

import co.mercenary.creators.kotlin.util.*
import org.junit.jupiter.api.Test

class TimeTest : KotlinTest() {
    @Test
    fun test() {
        info { 0.5.years }
        info { 1.0.years }
        info { 1.5.years }
        warn { dash() }
        info { 0.5.weeks }
        info { 1.0.weeks }
        info { 1.5.weeks }
        warn { dash() }
        info { 0.5.days }
        info { 1.0.days }
        info { 1.5.days }
        warn { dash() }
        info { 0.5.hours }
        info { 1.0.hours }
        info { 1.5.hours }
        warn { dash() }
        info { 0.5.minutes }
        info { 1.0.minutes }
        info { 1.5.minutes }
        warn { dash() }
        info { 0.5.seconds }
        info { 1.0.seconds }
        info { 1.5.seconds }
        warn { dash() }
        info { 1.millisecond / 2 }
        warn { dash() }
        info { 0.5.milliseconds }
        info { 1.0.milliseconds }
        info { 1.5.milliseconds }
        warn { dash() }
        info { 0.5.nanoseconds }
        info { 1.0.nanoseconds }
        info { 1.5.nanoseconds }
        warn { dash() }
        info { 1.year * 0.5 }
        info { 1.year * 1.0 }
        info { 1.year * 1.5 }
        warn { dash() }
        info { 1.year / 0.5 }
        info { 1.year / 1.0 }
        info { 1.year / 1.5 }
        info { 1.year / 2.0 }
        warn { dash() }
        val oops = 1.year * IS_NOT_FOUND.toDouble()
        info { oops }
        info { +oops }
        info { -oops }
        info { oops * 2.0 / IS_NOT_FOUND.toDouble() }
        warn { dash() }
    }
}