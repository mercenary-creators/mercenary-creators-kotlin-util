/*
 * Copyright (c) 2022, Mercenary Creators Company. All rights reserved.
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

package co.mercenary.creators.kotlin.util.system

import co.mercenary.creators.kotlin.util.*

class MemoryUsageData @FrameworkDsl constructor(private val type: MemoryType, private val used: Long, private val initial: Long, private val maximum: Long, private val committed: Long) : StandardInterfaces<MemoryUsageData> {

    @FrameworkDsl
    constructor(data: MemoryUsageData) : this(data.getType(), data.getUsed(), data.getInitial(), data.getMaximum(), data.getComitted())

    @FrameworkDsl
    fun getType() = type

    @FrameworkDsl
    fun getUsed() = used

    @FrameworkDsl
    fun getInitial() = initial

    @FrameworkDsl
    fun getMaximum() = maximum

    @FrameworkDsl
    fun getComitted() = committed

    @FrameworkDsl
    override fun clone() = copyOf()

    @FrameworkDsl
    override fun copyOf() = MemoryUsageData(this)

    @FrameworkDsl
    override fun hashCode() = idenOf()

    @FrameworkDsl
    override fun toString() = toMapNames().toSafeString()

    @FrameworkDsl
    override fun equals(other: Any?) = when (other) {
        is MemoryUsageData -> this === other || this isEverySame other
        else -> false
    }

    @FrameworkDsl
    private fun Long.formatToSizes(): String = getType().invoke(this)

    @FrameworkDsl
    override fun toMapNames() = dictOf("type" to getType(), "used" to getUsed().formatToSizes(), "initial" to getInitial().formatToSizes(), "maximum" to getMaximum().formatToSizes(), "committed" to getComitted().formatToSizes())

    private companion object {

        @FrameworkDsl
        infix fun MemoryUsageData.isEverySame(other: MemoryUsageData): Boolean {
            if (getType() != other.getType()) {
                return false
            }
            if (getUsed() != other.getUsed()) {
                return false
            }
            if (getInitial() != other.getInitial()) {
                return false
            }
            if (getMaximum() != other.getMaximum()) {
                return false
            }
            if (getComitted() != other.getComitted()) {
                return false
            }
            return true
        }
    }
}