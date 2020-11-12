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
        onExitOfProcess { getStandardError().echo(uuid()).newline() }
        error { 5.typeOf() }
        error { 5 isType 6 }
        error { 5 isType 6L }
        dashes()
        info { Common.load().toStringDictionary() }
        dashes()
        info { Common.getSystemProperties().toStringDictionary() }
        dashes()
        val maps = timed { Manager.toMapNames() }
        warn { maps }
        warn { loggerOf().name }
        warn { loggerOf().getLevel() }
        loggerOf().withLevel(LoggingLevel.DEBUG) {
            warn { loggerOf().getLevel() }
            dashes()
            debug { 6.toBinaryString() }
            dashes()
        }
        warn { loggerOf().getLevel() }
        dashes()
        debug { 6.toBinaryString() }
        LoggingFactory.withLevel(LoggingLevel.TRACE) {
            trace { loggerOf().getLevel() }
        }
        dashes()
        error { LoggingFactory }
        val size = 0.toAtomic()
        dashes()
        assumeEach {
            assumeThat {
                info { size }
                dashes()
                size.increment()
                5 shouldBe 5
            }
            assumeThat {
                info { size }
                dashes()
                size.increment()
                6 shouldNotBe 8
            }
            assumeThat {
                info { size }
                dashes()
                size shouldBe 2
                size.increment()
            }
            assumeThat {
                info { size }
                dashes()
                size shouldNotBe 2
                info { here() }
                oops()
            }
        }
        warn { dash() }
        info { here() }
        oops()
    }

    fun oops() {
        dashes()
        warn { here() }
    }
}