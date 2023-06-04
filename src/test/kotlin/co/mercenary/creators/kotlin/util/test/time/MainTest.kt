/*
 * Copyright (c) 2023, Mercenary Creators Company. All rights reserved.
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
        val time = 6.Days + 3.Hours + 1.Minute + 4.Weeks
        val same = 1.Minute + 3.Hours + 6.Days + (1.Week * 4)
        info { time }
        info { same }
        info { time == same }
        info { time.duration() }
        time shouldBe same
        warn { dash() }
        val plus = 1000.Milliseconds + 100.Nanoseconds
        info { plus }
        info { plus.unit().toLowerCase() }
        warn { dash() }
        val half = 1.Year / 2
        info { half }
        info { half.unit().toLowerCase() }
        warn { dash() }
        val oops = 0.Days
        info { oops }
        warn { dash() }
        val date = 366.Days
        info { date }
        info { date.unit().toLowerCase() }
        warn { dash() }
        val data = 100.Nanoseconds - 99.Nanoseconds
        info { data }
        data shouldBe 1.Nanosecond
        data shouldNotBe 99.Nanoseconds
        warn { dash() }
        val test = 1.Year + 3.Weeks + 4.Days + 5.Hours + 6.Minutes + 7.Seconds + 8.Milliseconds + 1.Nanosecond
        info { test }
        warn { dash() }
        val buff = test.toString()
        warn { dash() }
        measured(10) {
            if (it.isNegative()) {
                warn { it }
            }
            for (i in 1..100000) {
                CreatorsTimeDuration.parseCharSequence(buff)
            }
        }
        warn { dash() }
        measured(10) {
            if (it.isNegative()) {
                warn { it }
            }
            for (i in 1..100000) {
                CreatorsTimeDuration.parse(buff)
            }
        }
        warn { dash() }
    }
}