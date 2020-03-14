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
import org.junit.jupiter.api.Test

class MainTest : KotlinTest() {
    @Test
    fun test() {
        val size = 0.toAtomic()
        info { dash() }
        assumeEach {
            assumeThat {
                info { size }
                info { dash() }
                size.increment()
                5 shouldBe 5
            }
            assumeThat {
                info { size }
                info { dash() }
                size.increment()
                6 shouldNotBe 8
            }
            assumeThat {
                info { size }
                info { dash() }
                size shouldBe 2
                size.increment()
            }
            assumeThat {
                info { size }
                info { dash() }
                size shouldNotBe 2
                info { here() }
                oops()
            }
        }
        info { dash() }
        info { here() }
        oops()
    }

    fun oops() {
        info { dash() }
        info { here() }
    }
}