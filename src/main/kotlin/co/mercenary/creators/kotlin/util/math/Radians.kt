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

package co.mercenary.creators.kotlin.util.math

import co.mercenary.creators.kotlin.util.*

class Radians @JvmOverloads constructor(value: Double = 0.0) : RealNumber<Radians>, Comparable<Radians>, Angle {

    constructor(value: Int) : this(value.toDouble())

    constructor(value: Long) : this(value.toDouble())

    constructor(value: Angle) : this(value.toRadians())

    private val radians = Numeric.radiansOf(value.toFinite())

    override fun toRadians() = radians

    override fun toDegrees() = Numeric.toDegrees(radians)

    override fun clone() = copyOf()

    override fun copyOf() = Radians(radians)

    override fun toMapNames() = mapOf("radians" to radians)

    override fun hashCode() = radians.hashCode()

    override fun toString() = toMapNames().toString()

    override fun equals(other: Any?) = when (other) {
        is Radians -> this === other || radians.toBits() == other.radians.toBits()
        else -> false
    }

    override operator fun compareTo(other: Radians) = radians.compareTo(other.radians)

    override operator fun div(value: Double) = when (value) {
        0.0 -> throw MercenaryFatalExceptiion(MATH_ZERO_DIVISOR_ERROR)
        else -> Radians(radians / value)
    }

    override operator fun div(value: Radians) = div(value.radians)

    override operator fun plus(value: Double) = Radians(radians + value)

    override operator fun plus(value: Radians) = plus(value.radians)

    override operator fun minus(value: Double) = Radians(radians - value)

    override operator fun minus(value: Radians) = minus(value.radians)

    override operator fun times(value: Double) = Radians(radians * value)

    override operator fun times(value: Radians) = times(value.radians)

    override operator fun unaryPlus() = Radians(radians.abs())

    override operator fun unaryMinus() = Radians(radians.unaryMinus())
}