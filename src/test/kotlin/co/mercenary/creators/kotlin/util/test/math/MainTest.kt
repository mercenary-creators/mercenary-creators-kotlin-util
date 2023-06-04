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
import kotlin.math.pow

class MainTest : KotlinTest() {

    @Test
    fun test() {
        val data = -0.0000101
        info { data.rounded(3) }
        info { data.rounded(7) }
        info { data.toDecimalPlacesString(3) }
        info { data.toDecimalPlacesString(7) }
        warn { (-1.0).pow(-5) }
        warn { (-1.0).pow(-4) }
        info { gcdOf(8, 12) }
        info { lcmOf(8, 12) }
        info { gcdOf(8, -6, 12) }
        info { lcmOf(8, 12, 10) }
        info { lcmOf(0, 8) }
        warn { -1 % 2 }
        warn { (-1).isEven() }
        warn { (-2).isEven() }
        warn { (-1.2).isEven() }
        warn { (-2.2).isEven() }
        error { (120).toNextPowerOf2() }
        dashes()
    }
}