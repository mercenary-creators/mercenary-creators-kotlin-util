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

interface Coordinates {
    fun toComplex(): Complex
    fun toPoint2D(): Point2D
    fun toPolar2D(): Polar2D
    fun toPolarCoordinates(value: Point2D): PolarCoordinates = Numeric.toPolarCoordinates(value)
    fun toPolarCoordinates(value: Polar2D): PolarCoordinates = Numeric.toPolarCoordinates(value)
    fun toPolarCoordinates(value: Complex): PolarCoordinates = Numeric.toPolarCoordinates(value)
    fun toPolarCoordinates(): PolarCoordinates = Numeric.toPolarCoordinates(this)
    fun toPolarCoordinates(x: Double, y: Double): PolarCoordinates = Numeric.toPolarCoordinates(x, y)
    fun toCartesianCoordinates(value: Point2D): CartesianCoordinates = Numeric.toCartesianCoordinates(value)
    fun toCartesianCoordinates(value: Polar2D): CartesianCoordinates = Numeric.toCartesianCoordinates(value)
    fun toCartesianCoordinates(value: Complex): CartesianCoordinates = Numeric.toCartesianCoordinates(value)
    fun toCartesianCoordinates(): CartesianCoordinates = Numeric.toCartesianCoordinates(this)
    fun toCartesianCoordinates(r: Double, t: Double): CartesianCoordinates = Numeric.toCartesianCoordinates(r, t)
}