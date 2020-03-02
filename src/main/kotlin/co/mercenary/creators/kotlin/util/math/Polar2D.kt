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

data class Polar2D @JvmOverloads constructor(override val radius: Double = 0.0, override val theta: Double = 0.0) : MathField<Polar2D>, Polar {

    constructor(polar: Polar2D) : this(polar.radius, polar.theta)

    init {
        if (radius.isNegative()) {
            throw MercenaryFatalExceptiion("invalid radius $radius")
        }
    }

    fun toPolarCoordinates() = Numeric.toPolarCoordinates(this)

    fun toCartesianCoordinates() = Numeric.toCartesianCoordinates(this)

    override fun clone() = copyOf()

    override fun copyOf() = Polar2D(radius, theta)

    override fun toMapNames() = mapOf("radius" to radius, "theta" to theta)

    override fun toString() = toMapNames().toString()

    operator fun unaryPlus(): Polar2D {
        return Polar2D(radius.abs(), theta.abs())
    }

    operator fun unaryMinus(): Polar2D {
        return Polar2D(-radius, -theta)
    }
}