/*
 * Copyright (c) 2021, Mercenary Creators Company. All rights reserved.
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

package co.mercenary.creators.kotlin.util.time

import co.mercenary.creators.kotlin.util.*

@IgnoreForSerialize
abstract class AbstractTicker @FrameworkDsl constructor(private val tick: Factory<Long>) : Ticker {

    @FrameworkDsl
    private var time = tick.create()

    @FrameworkDsl
    @Synchronized
    override fun reset() {
        time = tick.create()
    }

    @FrameworkDsl
    override fun since(): Long = tick.create() - time

    @FrameworkDsl
    override fun toElapsedString(): String = TimeAndDate.toElapsedString(since())

    @FrameworkDsl
    @JvmOverloads
    operator fun invoke(reset: Boolean = false): String = toString().also { if (reset.isTrue()) reset() }
}