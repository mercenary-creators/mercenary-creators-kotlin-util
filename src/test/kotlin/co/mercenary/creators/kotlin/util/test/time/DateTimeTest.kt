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

class DateTimeTest : KotlinTest() {
    @Test
    fun test() {
        val date = dateTimeOf()
        info { date.formatDate() }
        val plus = date + 1.Year
        info { plus.formatDate() }
        val less = date - 1.Year
        info { less.formatDate() }
        warn { dash() }
        val olds = date - 100.Years
        info { olds.formatDate() }
        info { date.convertTo() - 100.Years }
        warn { dash() }
        val half = date + 0.5.Years
        info { half.formatDate() }
        val down = date - 0.5.Years
        info { down.formatDate() }
    }
}