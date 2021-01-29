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

package co.mercenary.creators.kotlin.util.time

import co.mercenary.creators.kotlin.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.*

abstract class AbstractTimeWindowMovingAverage @JvmOverloads @CreatorsDsl constructor(window: Long, private val wait: TimeUnit = TimeUnit.MILLISECONDS, unit: TimeUnit = TimeUnit.MILLISECONDS, private val moment: () -> Long = TimeAndDate::mills) : TimeWindowMovingAverage {

    @Volatile
    private var ticker = 0L

    @Volatile
    private var moving = 0.0

    private val window = wait.convert(window.maxOf(1L), unit).toDouble()

    @CreatorsDsl
    @IgnoreForSerialize
    override fun getAverage() = moving

    @CreatorsDsl
    @IgnoreForSerialize
    override fun getWaitTimeUnit() = wait

    @CreatorsDsl
    @IgnoreForSerialize
    override fun getMomentInTime() = moment.invoke()

    @CreatorsDsl
    @Synchronized
    override fun addAverage(delta: Double): Double {
        getMomentInTime().also { clock ->
            if (ticker < 1) moving = delta
            else exp(-1.0 * ((clock - ticker).toDouble() / window)).also { timed ->
                moving = ((1.0 - timed) * delta) + (timed * moving)
            }
            ticker = clock
        }
        return getAverage()
    }

    @CreatorsDsl
    override fun toString(): String = TimeAndDate.toDecimalPlaces(getAverage(), " milliseconds")

    @CreatorsDsl
    @IgnoreForSerialize
    override fun getWindowHandle(): TimeWindowHandle = DefaultTimeWindowHandle(this, getMomentInTime())

    @IgnoreForSerialize
    protected open inner class DefaultTimeWindowHandle @CreatorsDsl constructor(private val self: TimeWindowMovingAverage, private val time: Long) : TimeWindowHandle {

        private val open = true.toAtomic()

        override fun close() {
            if (open.isTrueToFalse()) {
                self.getMomentInTime().minus(time).toDouble().also { diff ->
                    self.addAverage(diff).minus(diff).toLong().also {
                        if (it > 0) {
                            try {
                                self.getWaitTimeUnit().sleep(it)
                            }
                            catch (cause: Throwable) {
                                Throwables.thrown(cause)
                            }
                        }
                    }
                }
            }
        }
    }
}