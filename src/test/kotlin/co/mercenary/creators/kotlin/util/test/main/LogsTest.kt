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
import co.mercenary.creators.kotlin.util.time.TimeAndDate
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
        info { listOf(1, 2, 3) }
        info { sequenceOf(4, 5, 6) }
        info { toDoubleArrayOf(4, 5, 6) }
        info { TimeAndDate::nanos }
        info { mapOf("name" to CREATORS_AUTHOR_INFO, "time" to 56.years) }
        info { false }
        info { false.toAtomic() }
        info { 16.toAtomic() }
        info { 1L.toAtomic() }
        info { toDoubleArrayOf(4, 5, 6).toFlux() }
    }
}