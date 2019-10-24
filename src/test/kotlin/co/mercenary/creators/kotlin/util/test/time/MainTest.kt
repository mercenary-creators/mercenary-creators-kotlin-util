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

package co.mercenary.creators.kotlin.util.test.time

import co.mercenary.creators.kotlin.util.*
import org.junit.jupiter.api.Test

class MainTest : KotlinTest() {
    @Test
    fun test() {
        val time = 6.days + 3.hours + 1.minutes + 4.weeks
        val same = 1.minutes + 3.hours + 6.days + 4.weeks
        info { time.parts() }
        info { time == same }
        val half = 1000.milliseconds + 100.nanoseconds
        info { half }
        info { half.parts() }
        sleepFor(half)
        val oops = IS_NOT_FOUND.days
        info { oops }
        info { oops.parts() }
        info { oops.isValid() }
        val data = 100.nanoseconds - 99.nanoseconds
        info { data }
        info { data.parts() }
    }
}