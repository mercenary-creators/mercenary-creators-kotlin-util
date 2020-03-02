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

data class PolarCoordinates(override val radius: Double, override val theta: Double) : MathField<PolarCoordinates>, Coordinates, Polar {

    init {
        if (radius.isNegative()) {
            throw MercenaryFatalExceptiion("invalid radius $radius")
        }
    }

    override fun clone() = copyOf()

    override fun copyOf() = PolarCoordinates(radius, theta)

    override fun toString() = toMapNames().toString()

    override fun toMapNames() = mapOf("radius" to radius, "theta" to theta)

    override fun toPolar2D() = Polar2D(radius, theta)

    override fun toComplex() = toCartesianCoordinates().toComplex()

    override fun toPoint2D() = toCartesianCoordinates().toPoint2D()
}