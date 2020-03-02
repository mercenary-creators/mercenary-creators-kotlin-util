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

class Degrees @JvmOverloads constructor(value: Double = 0.0) : RealNumber<Degrees>, Comparable<Degrees>, Angle {

    constructor(value: Int) : this(value.toDouble())

    constructor(value: Long) : this(value.toDouble())

    constructor(value: Angle) : this(value.toDegrees())

    private val degrees = Numeric.degreesOf(value)

    override fun toDegrees() = degrees

    override fun toRadians() = Numeric.toRadians(degrees)

    override fun clone() = copyOf()

    override fun copyOf() = Degrees(degrees)

    override fun toMapNames() = mapOf("degrees" to degrees)

    override fun hashCode() = degrees.hashCode()

    override fun toString() = toMapNames().toString()

    override fun equals(other: Any?) = when (other) {
        is Degrees -> this === other || degrees.toBits() == other.degrees.toBits()
        else -> false
    }

    override operator fun compareTo(other: Degrees) = degrees.compareTo(other.degrees)

    override operator fun div(value: Double) = when (value) {
        0.0 -> throw MercenaryFatalExceptiion(MATH_ZERO_DIVISOR_ERROR)
        else -> Degrees(degrees / value)
    }

    override operator fun div(value: Degrees) = div(value.degrees)

    override operator fun plus(value: Double) = Degrees(degrees + value)

    override operator fun plus(value: Degrees) = plus(value.degrees)

    override operator fun minus(value: Double) = Degrees(degrees - value)

    override operator fun minus(value: Degrees) = minus(value.degrees)

    override operator fun times(value: Double) = Degrees(degrees * value)

    override operator fun times(value: Degrees) = times(value.degrees)

    override operator fun unaryPlus() = Degrees(degrees.abs())

    override operator fun unaryMinus() = Degrees(degrees.unaryMinus())
}