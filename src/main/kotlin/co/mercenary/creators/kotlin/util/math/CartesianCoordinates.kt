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

data class CartesianCoordinates(override val x: Double, override val y: Double) : MathField<CartesianCoordinates>, Coordinates, Cartesian {

    override fun clone() = copyOf()

    override fun copyOf() = CartesianCoordinates(x, y)

    override fun toString() = toMapNames().toString()

    override fun toMapNames() = mapOf("x" to x, "y" to y)

    override fun toPoint2D() = Point2D(x, y)

    override fun toComplex() = Complex(x, y)

    override fun toPolar2D() = toPolarCoordinates().toPolar2D()
}