/*
 * Copyright (c) 2022, Mercenary Creators Company. All rights reserved.
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
        info { 0.5.Years }
        info { 1.0.Years }
        info { 1.5.Years }
        warn { dash() }
        info { 0.5.Weeks }
        info { 1.0.Weeks }
        info { 1.5.Weeks }
        warn { dash() }
        info { 0.5.Days }
        info { 1.0.Days }
        info { 1.5.Days }
        warn { dash() }
        info { 0.5.Hours }
        info { 1.0.Hours }
        info { 1.5.Hours }
        warn { dash() }
        info { 0.5.Minutes }
        info { 1.0.Minutes }
        info { 1.5.Minutes }
        warn { dash() }
        info { 0.5.Seconds }
        info { 1.0.Seconds }
        info { 1.5.Seconds }
        warn { dash() }
        info { 1.Millisecond / 2 }
        warn { dash() }
        info { 0.5.Milliseconds }
        info { 1.0.Milliseconds }
        info { 1.5.Milliseconds }
        warn { dash() }
        info { 0.5.Nanoseconds }
        info { 1.0.Nanoseconds }
        info { 1.5.Nanoseconds }
        warn { dash() }
        info { 1.Year * 0.5 }
        info { 1.Year * 1.0 }
        info { 1.Year * 1.5 }
        warn { dash() }
        info { 1.Year / 0.5 }
        info { 1.Year / 1.0 }
        info { 1.Year / 1.5 }
        info { 1.Year / 2.0 }
        warn { dash() }
        val oops = 1.Year * IS_NOT_FOUND
        info { oops }
        info { +oops }
        info { -oops }
        info { oops * 2.0 / IS_NOT_FOUND.toDouble() }
        warn { dash() }
    }
}