/*
 * Copyright (c) 2019, Mercenary Creators Company. All rights reserved.
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

@file:kotlin.jvm.JvmName("MainKt")
@file:kotlin.jvm.JvmMultifileClass

package co.mercenary.creators.kotlin.util

import co.mercenary.creators.kotlin.util.math.Numeric
import kotlin.math.*

typealias Numeric = Numeric

fun powNegative1(value: Int): Double = Numeric.powNegative1(value)

fun powNegative1(value: Long): Double = Numeric.powNegative1(value)

fun powNegative1(value: Int, other: Int): Double = Numeric.powNegative1(value, other)

@JvmOverloads
fun pow2Round(value: Int, down: Boolean = true): Int = Numeric.pow2Round(value, down)

@JvmOverloads
fun pow2Round(value: Long, down: Boolean = true): Long = Numeric.pow2Round(value, down)

fun gcd(vararg args: Int): Int = Numeric.gcd(args)

fun gcd(vararg args: Long): Long = Numeric.gcd(args)

fun gcd(value: Int): Int = abs(value)

fun gcd(value: Long): Long = abs(value)

fun gcd(value: Int, other: Int): Int = Numeric.gcd(value, other)

fun gcd(value: Long, other: Long): Long = Numeric.gcd(value, other)

fun gcd(value: Int, other: Long): Long = Numeric.gcd(value.toLong(), other)

fun gcd(value: Long, other: Int): Long = Numeric.gcd(value, other.toLong())

fun lcm(vararg args: Int): Int = Numeric.lcm(args)

fun lcm(vararg args: Long): Long = Numeric.lcm(args)

fun lcm(value: Int): Int = abs(value)

fun lcm(value: Long): Long = abs(value)

fun lcm(value: Int, other: Int): Int = Numeric.lcm(value, other)

fun lcm(value: Long, other: Long): Long = Numeric.lcm(value, other)

fun lcm(value: Int, other: Long): Long = Numeric.lcm(value.toLong(), other)

fun lcm(value: Long, other: Int): Long = Numeric.lcm(value, other.toLong())

@JvmOverloads
fun Double.toDecimalPlacesString(scale: Int = 3, places: Int = abs(scale)): String {
    return Numeric.toDecimalPlacesString(this, scale, places)
}

@JvmOverloads
fun Double.closeEnough(value: Double, precision: Double = Numeric.DEFAULT_PRECISION): Boolean {
    return Numeric.closeEnough(this, value, precision)
}

@JvmOverloads
fun DoubleArray.closeEnough(value: DoubleArray, precision: Double = Numeric.DEFAULT_PRECISION): Boolean {
    return Numeric.closeEnough(this, value, precision)
}

@JvmOverloads
fun Array<Double>.closeEnough(value: Array<Double>, precision: Double = Numeric.DEFAULT_PRECISION): Boolean {
    return Numeric.closeEnough(this, value, precision)
}

@JvmOverloads
fun Array<DoubleArray>.closeEnough(value: Array<DoubleArray>, precision: Double = Numeric.DEFAULT_PRECISION): Boolean {
    return Numeric.closeEnough(this, value, precision)
}

@JvmOverloads
fun Array<Array<Double>>.closeEnough(value: Array<Array<Double>>, precision: Double = Numeric.DEFAULT_PRECISION): Boolean {
    return Numeric.closeEnough(this, value, precision)
}

@JvmOverloads
fun closeEnough(v1: Double, v2: Double, o1: Double, o2: Double, precision: Double = Numeric.DEFAULT_PRECISION): Boolean {
    return Numeric.closeEnough(v1, v2, o1, o2, precision)
}

@JvmOverloads
fun Double.root(root: Int = 2): Double = Numeric.root(this, root)

@JvmOverloads
fun Double.rounded(scale: Int = 2): Double = Numeric.rounded(this, scale)

fun toDoubleArrayOf(vararg args: Double): DoubleArray = doubleArrayOf(*args)

fun toDoubleArrayOf(vararg args: Number): DoubleArray = DoubleArray(args.size) { i -> args[i].toDouble() }

fun toArrayOfDoubleArray(cols: Int, args: DoubleArray): Array<DoubleArray> {
    return if (cols < 1) throw MercenaryFatalExceptiion(Numeric.INVALID_SIZE) else toArrayOfDoubleArray(args.size / cols, cols, args)
}

fun toArrayOfDoubleArray(rows: Int, cols: Int, args: DoubleArray): Array<DoubleArray> {
    return if (args.size != (rows * cols)) throw MercenaryFatalExceptiion(Numeric.INVALID_SIZE)
    else Array(rows) { r ->
        (r * cols).let { args.copyOfRange(it, it + cols) }
    }
}

fun squared(value: Double): Double = (value * value)

fun distance(dx: Double, dy: Double): Double = sqrt((dx * dx) + (dy * dy))
