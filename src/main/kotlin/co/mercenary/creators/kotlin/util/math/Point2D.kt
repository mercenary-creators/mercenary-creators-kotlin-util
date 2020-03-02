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
import kotlin.math.*

data class Point2D @JvmOverloads constructor(override val x: Double = 0.0, override val y: Double = 0.0) : MathField<Point2D>, Cartesian {

    constructor(value: Point2D) : this(value.x, value.y)

    constructor(value: Polar2D) : this(value.toCartesianCoordinates())

    constructor(x: Int, y: Int) : this(x.toDouble(), y.toDouble())

    constructor(value: Coordinates) : this(value.toPoint2D())

    constructor(x: Long, y: Long) : this(x.toDouble(), y.toDouble())

    operator fun unaryPlus(): Point2D {
        return Point2D(x.abs(), y.abs())
    }

    operator fun unaryMinus(): Point2D {
        return Point2D(-x, -y)
    }

    operator fun plus(value: Point2D): Point2D {
        return Point2D(x + value.x, y + value.y)
    }

    operator fun minus(value: Point2D): Point2D {
        return Point2D(x - value.x, y - value.y)
    }

    operator fun div(value: Int): Point2D = div(value.toDouble())

    operator fun div(value: Long): Point2D = div(value.toDouble())

    operator fun div(value: Double): Point2D {
        return when (value) {
            0.0 -> throw MercenaryFatalExceptiion(MATH_ZERO_DIVISOR_ERROR)
            else -> times(1.0 / value)
        }
    }

    operator fun times(value: Int): Point2D = Point2D(x * value, y * value)

    operator fun times(value: Long): Point2D = Point2D(x * value, y * value)

    operator fun times(value: Double): Point2D = Point2D(x * value, y * value)

    override fun clone() = copyOf()

    override fun copyOf() = Point2D(x, y)

    override fun toString() = toMapNames().toString()

    override fun toMapNames() = mapOf("x" to x.rounded(6), "y" to y.rounded(6))

    fun toPolarCoordinates() = Numeric.toPolarCoordinates(this)

    fun toCartesianCoordinates() = Numeric.toCartesianCoordinates(this)

    fun unit(): Point2D {
        return when (val dist = length()) {
            0.0 -> throw MercenaryFatalExceptiion(MATH_ZERO_DIVISOR_ERROR)
            else -> div(dist)
        }
    }

    fun theta(): Double {
        return if (x == 0.0 && y == 0.0) 0.0
        else atan2(y, x).let {
            if (it >= 0.0) it else (it + PI * 2.0)
        }
    }

    infix fun theta(value: Point2D): Double {
        return if (x == value.x && y == value.y) 0.0
        else (atan2(value.y, value.x) - atan2(y, x)).let {
            if (it >= 0.0) it else (it + PI * 2.0)
        }
    }

    infix fun rotate(angle: Angle): Point2D {
        return rotate(angle.toRadians())
    }

    infix fun rotate(radians: Double): Point2D {
        val a = Numeric.radiansOf(radians.toFinite())
        if (a == 0.0) {
            return copyOf()
        }
        val s = Numeric.sinOf(a)
        val c = Numeric.cosOf(a)
        return Point2D(c * x - s * y, s * x - c * y)
    }

    fun normalize(length: Double): Point2D {
        if (x == 0.0 || y == 0.0 || length.isFinite().not() || length == 0.0) {
            return copyOf()
        }
        return atan2(y, x).let {
            Point2D(Numeric.cosOf(it) * length, Numeric.sinOf(it) * length)
        }
    }

    fun length(): Double = distance(this)

    fun perpendicular(): Point2D = Point2D(-y, x)

    infix fun dot(value: Point2D): Double = (x * value.x) + (y * value.y)

    infix fun det(value: Point2D): Double = (x * value.y) - (y * value.x)

    infix fun scalar(value: Point2D): Double = (x * value.y) - (y * value.x)

    infix fun distance(value: Point2D): Double = Numeric.distance(x - value.x, y - value.y)

    fun collinear(value: Point2D, other: Point2D): Boolean = Numeric.collinear(this, value, other)
}