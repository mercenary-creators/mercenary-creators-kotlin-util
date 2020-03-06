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

package co.mercenary.creators.kotlin.util.test.main

import co.mercenary.creators.kotlin.util.*
import co.mercenary.creators.kotlin.util.math.*
import org.junit.jupiter.api.Test

class LogsTest : KotlinTest() {
    @Test
    fun test() {
        info { 16 }
        info { 1L }
        info { 1..3 }
        info { MainData() }
        info { dateOf() }
        info { dateTimeOf() }
        info { listOf<Any>() }
        info { listOf(1) }
        info { listOf(1, 2, 3) }
        info { sequenceOf(4, 5, 6) }
        info { toDoubleArrayOf(4, 5, 6) }
        info { TimeAndDate.nanosOf() }
        info { mapOf("name" to CREATORS_AUTHOR_INFO, "time" to 56.5.years, "date" to dateOf(), "horz" to "Maël Hörz\n", "size" to 1, "code" to String(CharArray(16) { it.toChar() })) }
        info { false }
        info { true.toAtomic() }
        info { 16.toAtomic() }
        info { 1L.toAtomic() }
        info { Complex(0.0, 1.0) }
        info { Polar2D(50.0, 45.0) }
        info { Numeric.toPrimeRoots(553) }
        warn { dash() }
        for (i in 1..99 step 3) {
            info { mapOf("data" to i, "test" to Numeric.isPrimeValue(i), "next" to Numeric.toPrimeAfter(i)) }
        }
        warn { dash() }
        info { Degrees(0.0) }
        info { Degrees(-0.0) }
        info { Degrees(360) }
        info { Degrees(367) }
        info { Degrees(-360) }
        info { Degrees(-367) }
        warn { dash() }
        info { Degrees(+367).asRadians() }
        info { Degrees(-367).asRadians() }
        warn { dash() }
        info { Degrees(367).asRadians().unaryPlus() }
        info { Degrees(367).asRadians().unaryMinus() }
        warn { dash() }
        info { Degrees(-367).asRadians().unaryPlus() }
        info { Degrees(-367).asRadians().unaryMinus() }
        warn { dash() }
        val bbox = BoundingBox(0, 0, 180, 180)
        info { bbox }
        val zero = Point2D(90, 90)
        info { zero }
        info { zero in bbox }
        info { bbox in bbox }
        info { zero.rotate(Degrees(180)) * 4 }
        info { zero.hashCode() }
        info { zero.hashOf() }
        info { zero.hashOfSystem() }
        info { null.hashOfSystem() }
    }
}