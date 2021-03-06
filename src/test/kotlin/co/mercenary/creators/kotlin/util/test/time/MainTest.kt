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

class MainTest : KotlinTest() {

    @Test
    fun test() {
        val time = 6.days + 3.hours + 1.minute + 4.weeks
        val same = 1.minute + 3.hours + 6.days + (1.week * 4)
        info { time }
        info { same }
        info { time == same }
        info { time.duration() }
        time shouldBe same
        warn { dash() }
        val plus = 1000.milliseconds + 100.nanoseconds
        info { plus }
        info { plus.unit().toLowerCase() }
        warn { dash() }
        val half = 1.year / 2
        info { half }
        info { half.unit().toLowerCase() }
        warn { dash() }
        val oops = 0.days
        info { oops }
        warn { dash() }
        val date = 366.days
        info { date }
        info { date.unit().toLowerCase() }
        warn { dash() }
        val data = 100.nanoseconds - 99.nanoseconds
        info { data }
        data shouldBe 1.nanosecond
        data shouldNotBe 99.nanoseconds
        warn { dash() }
        val test = 1.year + 3.weeks + 4.days + 5.hours + 6.minutes + 7.seconds + 8.milliseconds + 1.nanosecond
        info { test }
        warn { dash() }
        val buff = test.toString()
        warn { dash() }
        measured(10) {
            if (it.isNegative()) {
                warn { it }
            }
            for (i in 1..100000) {
                TimeDuration.parseCharSequence(buff)
            }
        }
        warn { dash() }
        measured(10) {
            if (it.isNegative()) {
                warn { it }
            }
            for (i in 1..100000) {
                TimeDuration.parse(buff)
            }
        }
        warn { dash() }
    }
}