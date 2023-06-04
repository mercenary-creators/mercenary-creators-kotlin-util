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

package co.mercenary.creators.kotlin.util.test.math

import co.mercenary.creators.kotlin.util.*
import org.junit.jupiter.api.Test
import kotlin.math.*

class MathTest : KotlinTest() {
    @Test
    fun test() {
        val data = 2.5.negOf()
        val negs = 1.0.negOf()
        info { data }
        warn { dash() }
        info { data.truncated() }
        warn { dash() }
        info { data.floorOf() }
        warn { dash() }
        info { data - data.floorOf() }
        warn { dash() }
        info { data }
        warn { dash() }
        info { data.rem(1.0) }
        warn { dash() }
        info { data - data.rem(1.0) }
        warn { dash() }
        info { sign(data) }
        info { data.isNegative() }
        info { sign(-data) }
        info { data.negOf().isNegative() }
        warn { dash() }
        info { 2.0.pow(2) }
        info { 2.0.pow(-1) }
        warn { dash() }
        info { 2.0 power 2 }
        info { 2.0 power -1 }
        warn { dash() }
        info { negs power 2 }
        info { negs power 3 }
        info { negs.pow(2) }
        info { negs.pow(3) }
        warn { dash() }
        info { 2.0 power 0 }
        info { 2.0 power 1 }
        info { 2.0 power 2 }
        info { 2.0 power 3 }
        warn { dash() }
        info { 9.0 rootOf 2 }
        info { 8.0 rootOf 3 }
        info { 8.0 rootOf 3.0.negOf() }
        warn { dash() }
        var save = 1
        measured(10) {
            for (i in 1..10000000) {
                save = abs(IS_NOT_FOUND)
            }
        }
        warn { dash() }
        measured(10) {
            for (i in 1..10000000) {
                save = abs(1)
            }
        }
        warn { dash() }
        measured(10) {
            for (i in 1..10000000) {
                save = IS_NOT_FOUND.absOf()
            }
        }
        warn { dash() }
        measured(10) {
            for (i in 1..10000000) {
                save = 1.absOf()
            }
        }
        warn { dash() }
        save shouldBe 1
    }
}