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

package co.mercenary.creators.kotlin.util.time

import co.mercenary.creators.kotlin.util.*

@IgnoreForSerialize
abstract class AbstractTicker @FrameworkDsl constructor(tick: Factory<Long>) : Ticker {

    @FrameworkDsl
    private val make = tick

    @FrameworkDsl
    private val time = make.create().toAtomic()

    @FrameworkDsl
    override fun reset() {
        time.putValue(make.create())
    }

    @FrameworkDsl
    override fun since(): Long = make.create() - time.getValue()

    @FrameworkDsl
    override fun toString(): String = toElapsedString()

    @FrameworkDsl
    override fun toElapsedString(): String = TimeAndDate.toElapsedString(since())

    @FrameworkDsl
    @JvmOverloads
    operator fun invoke(reset: Boolean = false): String = toString().also { if (reset.isTrue()) reset() }

    @FrameworkDsl
    override fun hashCode() = idenOf()

    @FrameworkDsl
    override fun equals(other: Any?) = when (other) {
        is AbstractTicker -> this === other
        else -> false
    }
}