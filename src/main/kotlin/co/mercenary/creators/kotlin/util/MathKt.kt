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

@file:kotlin.jvm.JvmName("MathKt")

package co.mercenary.creators.kotlin.util

import java.util.concurrent.atomic.AtomicInteger
import kotlin.math.*

typealias Numeric = co.mercenary.creators.kotlin.util.math.Numeric

const val MATH_INVALID_SIZE_ERROR = "invalid size"

const val MATH_ZERO_DIVISOR_ERROR = "can't divide by zero"

fun powNegative1(value: Int): Double = Numeric.powNegative1(value)

fun powNegative1(value: Long): Double = Numeric.powNegative1(value)

fun powNegative1(value: Int, other: Int): Double = Numeric.powNegative1(value, other)

@JvmOverloads
fun pow2Round(value: Int, down: Boolean = true): Int = Numeric.pow2Round(value, down)

@JvmOverloads
fun pow2Round(value: Long, down: Boolean = true): Long = Numeric.pow2Round(value, down)

fun gcdOf(vararg args: Int): Int = Numeric.gcdOf(*args)

fun gcdOf(vararg args: Long): Long = Numeric.gcdOf(*args)

fun gcdOf(value: Int): Int = Numeric.gcdOf(value)

fun gcdOf(value: Long): Long = Numeric.gcdOf(value)

fun gcdOf(value: Int, other: Int): Int = Numeric.gcdOf(value, other)

fun gcdOf(value: Long, other: Long): Long = Numeric.gcdOf(value, other)

fun gcdOf(value: Int, other: Long): Long = Numeric.gcdOf(value, other)

fun gcdOf(value: Long, other: Int): Long = Numeric.gcdOf(value, other)

fun lcmOf(vararg args: Int): Int = Numeric.lcmOf(*args)

fun lcmOf(vararg args: Long): Long = Numeric.lcmOf(*args)

fun lcmOf(value: Int): Int = Numeric.lcmOf(value)

fun lcmOf(value: Long): Long = Numeric.lcmOf(value)

fun lcmOf(value: Int, other: Int): Int = Numeric.lcmOf(value, other)

fun lcmOf(value: Long, other: Long): Long = Numeric.lcmOf(value, other)

fun lcmOf(value: Int, other: Long): Long = Numeric.lcmOf(value, other)

fun lcmOf(value: Long, other: Int): Long = Numeric.lcmOf(value, other)

fun Int.abs(): Int {
    return abs(this)
}

fun Int.neg(): Int {
    return unaryMinus()
}

@AssumptionDsl
fun Int.isNegative(): Boolean {
    return this < 0
}

infix fun Int.minOf(other: Int): Int {
    return min(this, other)
}

infix fun Int.maxOf(other: Int): Int {
    return max(this, other)
}

fun Long.abs(): Long {
    return abs(this)
}

fun Long.neg(): Long {
    return unaryMinus()
}

@AssumptionDsl
fun Long.isNegative(): Boolean {
    return this < 0
}

infix fun Long.minOf(other: Int): Long {
    return min(this, other.toLong())
}

infix fun Long.maxOf(other: Int): Long {
    return max(this, other.toLong())
}

infix fun Long.minOf(other: Long): Long {
    return min(this, other)
}

infix fun Long.maxOf(other: Long): Long {
    return max(this, other)
}

fun Double.abs(): Double {
    return abs(this)
}

fun Double.neg(): Double {
    return unaryMinus()
}

fun Double.sinOf(): Double {
    return Numeric.sinOf(this)
}

fun Double.cosOf(): Double {
    return Numeric.cosOf(this)
}

infix fun Double.minOf(other: Double): Double {
    return min(this, other)
}

infix fun Double.maxOf(other: Double): Double {
    return max(this, other)
}

fun Double.floor(): Double {
    return floor(this)
}

@AssumptionDsl
fun Double.isValid(): Boolean {
    return isFinite()
}

@AssumptionDsl
fun Double.isNegative(): Boolean {
    return sign(this) < 0
}

@JvmOverloads
fun Double.toDecimalPlacesString(scale: Int = 3, places: Int = scale.abs()): String {
    return Numeric.toDecimalPlacesString(this, scale, places)
}

@JvmOverloads
@AssumptionDsl
fun Double.closeEnough(value: Double, precision: Double = Numeric.DEFAULT_PRECISION_DELTA): Boolean {
    return Numeric.closeEnough(this, value, precision)
}

@JvmOverloads
@AssumptionDsl
fun DoubleArray.closeEnough(value: DoubleArray, precision: Double = Numeric.DEFAULT_PRECISION_DELTA): Boolean {
    return Numeric.closeEnough(this, value, precision)
}

@JvmOverloads
@AssumptionDsl
fun Array<Double>.closeEnough(value: Array<Double>, precision: Double = Numeric.DEFAULT_PRECISION_DELTA): Boolean {
    return Numeric.closeEnough(this, value, precision)
}

@JvmOverloads
@AssumptionDsl
fun Array<DoubleArray>.closeEnough(value: Array<DoubleArray>, precision: Double = Numeric.DEFAULT_PRECISION_DELTA): Boolean {
    return Numeric.closeEnough(this, value, precision)
}

@JvmOverloads
@AssumptionDsl
fun Array<Array<Double>>.closeEnough(value: Array<Array<Double>>, precision: Double = Numeric.DEFAULT_PRECISION_DELTA): Boolean {
    return Numeric.closeEnough(this, value, precision)
}

@JvmOverloads
fun Double.rounded(scale: Int = 3): Double {
    return Numeric.rounded(this, scale)
}

@JvmOverloads
fun DoubleArray.rounded(scale: Int = 3): DoubleArray {
    return Numeric.rounded(this, scale)
}

@JvmOverloads
fun Array<Double>.rounded(scale: Int = 3): Array<Double> {
    return Numeric.rounded(this, scale)
}

@JvmOverloads
fun Array<DoubleArray>.rounded(scale: Int = 3): Array<DoubleArray> {
    return Numeric.rounded(this, scale)
}

@JvmOverloads
fun Array<Array<Double>>.rounded(scale: Int = 3): Array<Array<Double>> {
    return Numeric.rounded(this, scale)
}

@JvmOverloads
fun Iterable<Double>.rounded(scale: Int = 3): Iterable<Double> {
    return Numeric.rounded(this, scale)
}

@JvmOverloads
fun Sequence<Double>.rounded(scale: Int = 3): Sequence<Double> {
    return Numeric.rounded(this, scale)
}

@JvmOverloads
fun Double.root(root: Int = 2): Double = Numeric.root(this, root)

@AssumptionDsl
fun Double.toFinite(): Double = if (isValid()) this else throw MercenaryFatalExceptiion("invalid value $this")

@AssumptionDsl
fun Double.toFiniteOrElse(value: Double): Double = if (isValid()) this else value

@AssumptionDsl
fun Double.toFiniteOrElse(block: () -> Double): Double = if (isValid()) this else block()

fun toDoubleArrayOf(vararg args: Double): DoubleArray = doubleArrayOf(*args)

fun toDoubleArrayOf(vararg args: Number): DoubleArray = DoubleArray(args.size) { i -> args[i].toDouble() }

fun toArrayOfDoubleArray(cols: Int, args: DoubleArray): Array<DoubleArray> {
    return if (cols < 1) throw MercenaryFatalExceptiion(MATH_INVALID_SIZE_ERROR) else toArrayOfDoubleArray(args.size / cols, cols, args)
}

fun toArrayOfDoubleArray(rows: Int, cols: Int, args: DoubleArray): Array<DoubleArray> {
    return if (args.size != (rows * cols)) throw MercenaryFatalExceptiion(MATH_INVALID_SIZE_ERROR)
    else Array(rows) { r ->
        (r * cols).let { args.copyOfRange(it, it + cols) }
    }
}

inline infix fun Int.forEach(action: (Int) -> Unit) {
    repeat(this, action)
}

@AssumptionDsl
fun Int.isPrimeValue(): Boolean = Numeric.isPrimeValue(this)

@AssumptionDsl
fun AtomicInteger.isPrimeValue(): Boolean = Numeric.isPrimeValue(this)

@JvmOverloads
fun Int.toHexString(pads: Int = 0): String = toString(16).padStart(pads.abs().maxOf(0), '0')

@JvmOverloads
fun Byte.toHexString(pads: Int = 0): String = toString(16).padStart(pads.abs().maxOf(0), '0')

@JvmOverloads
fun Long.toHexString(pads: Int = 0): String = toString(16).padStart(pads.abs().maxOf(0), '0')

@JvmOverloads
fun Short.toHexString(pads: Int = 0): String = toString(16).padStart(pads.abs().maxOf(0), '0')

@JvmOverloads
fun Char.toHexString(pads: Int = 0): String = toInt().toString(16).padStart(pads.abs().maxOf(0), '0')

@JvmOverloads
fun Int.toBinaryString(pads: Int = Int.SIZE_BITS): String = toString(2).padStart(pads.abs().maxOf(0), '0')

@JvmOverloads
fun Byte.toBinaryString(pads: Int = Byte.SIZE_BITS): String = toString(2).padStart(pads.abs().maxOf(0), '0')

@JvmOverloads
fun Long.toBinaryString(pads: Int = Long.SIZE_BITS): String = toString(2).padStart(pads.abs().maxOf(0), '0')

@JvmOverloads
fun Short.toBinaryString(pads: Int = Short.SIZE_BITS): String = toString(2).padStart(pads.abs().maxOf(0), '0')

@JvmOverloads
fun Char.toBinaryString(pads: Int = Byte.SIZE_BITS * 2): String = toInt().toString(2).padStart(pads.abs().maxOf(0), '0')
