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
import co.mercenary.creators.kotlin.util.type.Validated

class BoundingBox private constructor(private val list: DoubleArray) : MathField<BoundingBox>, Validated {

    constructor() : this(make())

    constructor(args: BoundingBox) : this(make(args))

    constructor(vararg args: Cartesian) : this(make(*args))

    constructor(minx: Number, miny: Number, maxx: Number, maxy: Number) : this(make(minx, miny, maxx, maxy))

    private val minx: Double
        get() = list[MIN_X]

    private val miny: Double
        get() = list[MIN_Y]

    private val maxx: Double
        get() = list[MAX_X]

    private val maxy: Double
        get() = list[MAX_Y]

    private val wide: Double
        get() = (maxx - minx).abs()

    private val high: Double
        get() = (maxy - miny).abs()

    override fun clone() = copyOf()

    override fun copyOf() = BoundingBox(list.copyOf())

    override fun toString() = toMapNames().toString()

    override fun toMapNames() = mapOf("x" to minx.rounded(6), "y" to miny.rounded(6), "wide" to wide.rounded(6), "high" to high.rounded(6))

    override fun hashCode() = minx.hashOf(miny, wide, high)

    override fun isValid() = when {
        maxx <= minx || maxx == MIN_D || minx == MAX_D -> false
        maxy <= miny || maxy == MIN_D || miny == MAX_D -> false
        else -> true
    }

    override fun equals(other: Any?) = when (other) {
        is BoundingBox -> this === other || minx == other.minx && miny == other.miny && wide == other.wide && high == other.high
        else -> false
    }

    operator fun plus(args: Cartesian): BoundingBox {
        list.addx(args.x).addy(args.y)
        return this
    }

    operator fun plus(args: BoundingBox): BoundingBox {
        list.addx(args.minx).addy(args.miny).addx(args.maxx).addy(args.maxy)
        return this
    }

    operator fun contains(args: Cartesian): Boolean {
        return args.x in minx..maxx && args.y in miny..maxy
    }

    operator fun contains(args: BoundingBox): Boolean {
        return (minx <= args.minx) && (maxx >= args.maxx) && (miny <= args.miny) && (maxy >= args.maxy)
    }

    fun intersects(args: BoundingBox): Boolean = when {
        maxx < args.minx || minx > args.maxx || maxy < args.miny || miny > args.maxy -> false
        else -> true
    }

    @JvmOverloads
    fun offset(dx: Number, dy: Number = dx): BoundingBox {
        list[MIN_X] += dx.toDouble()
        list[MAX_X] += dx.toDouble()
        list[MIN_Y] += dy.toDouble()
        list[MAX_Y] += dy.toDouble()
        return this
    }

    private companion object {

        private const val MIN_X = 0

        private const val MIN_Y = 1

        private const val MAX_X = 2

        private const val MAX_Y = 3

        private val MAX_D = Double.MAX_VALUE

        private val MIN_D = Double.MAX_VALUE.unaryMinus()

        private fun DoubleArray.addx(x: Double): DoubleArray {
            if (x < this[MIN_X]) {
                this[MIN_X] = x
            }
            if (x > this[MAX_X]) {
                this[MAX_X] = x
            }
            return this
        }

        private fun DoubleArray.addy(y: Double): DoubleArray {
            if (y < this[MIN_Y]) {
                this[MIN_Y] = y
            }
            if (y > this[MAX_Y]) {
                this[MAX_Y] = y
            }
            return this
        }

        @JvmStatic
        private fun make(): DoubleArray = toDoubleArrayOf(MAX_D, MAX_D, MIN_D, MIN_D)

        @JvmStatic
        private fun make(args: BoundingBox): DoubleArray = make()
            .addx(args.minx)
            .addy(args.miny)
            .addx(args.maxx)
            .addy(args.maxy)

        @JvmStatic
        private fun make(vararg args: Cartesian): DoubleArray = if (args.isEmpty()) make()
        else make().let { list ->
            args.forEach {
                list.addx(it.x).addy(it.y)
            }
            list
        }

        @JvmStatic
        private fun make(minx: Number, miny: Number, maxx: Number, maxy: Number): DoubleArray = make()
            .addx(minx.toDouble())
            .addy(miny.toDouble())
            .addx(maxx.toDouble())
            .addy(maxy.toDouble())
    }
}