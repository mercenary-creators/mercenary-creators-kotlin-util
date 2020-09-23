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

package co.mercenary.creators.kotlin.util.test.math

import co.mercenary.creators.kotlin.util.*
import org.junit.jupiter.api.Test

class RootTest : KotlinTest() {
    @Test
    fun test() {
        warn { dash() }
        info { 9.0 rootOf 2 }
        info { 8.0 rootOf 3 }
        info { 8.0 rootOf 3.0.neg() }
        warn { dash() }
        info { 9.0.neg() rootOf 2 }
        info { 8.0.neg() rootOf 3 }
        info { 8.0.neg() rootOf 3.0.neg() }
        warn { dash() }
        info { Double.NaN.toBigDecimal() }
        warn { dash() }
    }
}