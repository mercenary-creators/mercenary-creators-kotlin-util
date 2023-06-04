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

abstract class AbstractTimeWindowMovingAverage @JvmOverloads @FrameworkDsl constructor(window: Long, unit: SystemTimeUnit = SYSTEM_TIME_UNIT_MILLISECONDS, moment: Factory<Long> = TimeAndDate.millsOf()) : TimeWindowMovingAverage {

    @Volatile
    @FrameworkDsl
    private var ticker = 0L

    @Volatile
    @FrameworkDsl
    private var moving = 0.0

    @FrameworkDsl
    private val factory = moment

    @FrameworkDsl
    private val window = unit().convert(window.maxOf(1L), unit).realOf()

    @FrameworkDsl
    internal fun unit(): SystemTimeUnit {
        return getWaitTimeUnit()
    }

    @FrameworkDsl
    @IgnoreForSerialize
    override fun getAverage() = moving

    @FrameworkDsl
    @IgnoreForSerialize
    override fun getMomentInTime() = factory.create()

    @FrameworkDsl
    @Synchronized
    override fun addAverage(delta: Double): Double {
        getMomentInTime().also { clock ->
            if (ticker < 1) moving = delta
            else expOf(-1.0 * ((clock - ticker).realOf() / window)).also { timed ->
                moving = ((1.0 - timed) * delta) + (timed * moving)
            }
            ticker = clock
        }
        return getAverage()
    }

    @FrameworkDsl
    override fun toString(): String = toElapsedString()

    @FrameworkDsl
    @IgnoreForSerialize
    override fun getWindowHandle(): TimeWindowHandle = DefaultTimeWindowHandle(this)

    @IgnoreForSerialize
    protected open inner class DefaultTimeWindowHandle @FrameworkDsl constructor(self: TimeWindowMovingAverage, time: Long = self.getMomentInTime()) : TimeWindowHandle {

        @FrameworkDsl
        private val that = self

        @FrameworkDsl
        private val kick = time.copyOf()

        @FrameworkDsl
        private val open = getAtomicTrue()

        @FrameworkDsl
        @IgnoreForSerialize
        override fun getTimeWindowMovingAverage(): TimeWindowMovingAverage {
            return that
        }

        @FrameworkDsl
        override fun toString(): String {
            return toElapsedString()
        }

        @FrameworkDsl
        override fun close() {
            if (open.isTrueToFalse()) {
                getTimeWindowMovingAverage().withIn {
                    (getMomentInTime() - kick).realOf().also { diff ->
                        (addAverage(diff) - kick).longOf().absOf().also { delta ->
                            if (delta.isMoreThan(0L)) {
                                TimeAndDate.sleepFor(delta, getWaitTimeUnit())
                            }
                        }
                    }
                }
            }
        }
    }
}