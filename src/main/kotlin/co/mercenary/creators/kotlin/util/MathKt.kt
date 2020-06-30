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
@file:Suppress("NOTHING_TO_INLINE")

package co.mercenary.creators.kotlin.util

import kotlin.math.*

typealias NumericMath = Math

typealias Numeric = co.mercenary.creators.kotlin.util.math.Numeric

const val MATH_INVALID_SIZE_ERROR = "invalid size"

const val MATH_ZERO_DIVISOR_ERROR = "can't divide by zero"

const val MATH_OVERFLOW_FOR_ERROR = "overflow for operation"

const val DEFAULT_PRECISION_SCALE = Numeric.DEFAULT_PRECISION_SCALE

const val DEFAULT_PRECISION_DELTA = Numeric.DEFAULT_PRECISION_DELTA

@CreatorsDsl
fun gcdOf(vararg args: Int): Int = Numeric.gcdOf(*args)

@CreatorsDsl
fun gcdOf(vararg args: Long): Long = Numeric.gcdOf(*args)

@CreatorsDsl
fun gcdOf(value: Int): Int = Numeric.gcdOf(value)

@CreatorsDsl
fun gcdOf(value: Long): Long = Numeric.gcdOf(value)

@CreatorsDsl
fun gcdOf(value: Int, other: Int): Int = Numeric.gcdOf(value, other)

@CreatorsDsl
fun gcdOf(value: Long, other: Long): Long = Numeric.gcdOf(value, other)

@CreatorsDsl
fun gcdOf(value: Int, other: Long): Long = Numeric.gcdOf(value, other)

@CreatorsDsl
fun gcdOf(value: Long, other: Int): Long = Numeric.gcdOf(value, other)

@CreatorsDsl
fun lcmOf(vararg args: Int): Int = Numeric.lcmOf(*args)

@CreatorsDsl
fun lcmOf(vararg args: Long): Long = Numeric.lcmOf(*args)

@CreatorsDsl
fun lcmOf(value: Int): Int = Numeric.lcmOf(value)

@CreatorsDsl
fun lcmOf(value: Long): Long = Numeric.lcmOf(value)

@CreatorsDsl
fun lcmOf(value: Int, other: Int): Int = Numeric.lcmOf(value, other)

@CreatorsDsl
fun lcmOf(value: Long, other: Long): Long = Numeric.lcmOf(value, other)

@CreatorsDsl
fun lcmOf(value: Int, other: Long): Long = Numeric.lcmOf(value, other)

@CreatorsDsl
fun lcmOf(value: Long, other: Int): Long = Numeric.lcmOf(value, other)

@CreatorsDsl
inline fun Int.abs(): Int {
    return Numeric.absOf(this)
}

@CreatorsDsl
inline fun Int.neg(): Int {
    return -this
}

@CreatorsDsl
inline fun Int.isNegative(): Boolean {
    return this < 0
}

@CreatorsDsl
inline fun Int.isEven(): Boolean {
    return abs() % 2 == 0
}

@CreatorsDsl
inline fun Int.isNotEven(): Boolean {
    return abs() % 2 == 1
}

@CreatorsDsl
inline infix fun Int.minOf(other: Int): Int {
    return if (this < other) this else other
}

@CreatorsDsl
inline infix fun Int.maxOf(other: Int): Int {
    return if (this < other) other else this
}

@CreatorsDsl
inline fun Int.boxIn(min: Int, max: Int): Int {
    return coerceIn(min, max)
}

@CreatorsDsl
inline fun Int.boxIn(range: ClosedRange<Int>): Int {
    return coerceIn(range)
}

@CreatorsDsl
inline fun Long.abs(): Long {
    return Numeric.absOf(this)
}

@CreatorsDsl
inline fun Long.neg(): Long {
    return -this
}

@CreatorsDsl
inline fun Long.isNegative(): Boolean {
    return this < 0
}

@CreatorsDsl
inline fun Long.isEven(): Boolean {
    return abs() % 2 == 0L
}

@CreatorsDsl
inline fun Long.isNotEven(): Boolean {
    return abs() % 2 == 1L
}

@CreatorsDsl
inline infix fun Long.minOf(other: Int): Long {
    return minOf(other.toLong())
}

@CreatorsDsl
inline infix fun Long.maxOf(other: Int): Long {
    return maxOf(other.toLong())
}

@CreatorsDsl
inline infix fun Long.minOf(other: Long): Long {
    return if (this < other) this else other
}

@CreatorsDsl
inline infix fun Long.maxOf(other: Long): Long {
    return if (this < other) other else this
}

@CreatorsDsl
inline fun Long.boxIn(min: Int, max: Int): Long {
    return boxIn(min.toLong(), max.toLong())
}

@CreatorsDsl
inline fun Long.boxIn(min: Long, max: Long): Long {
    return coerceIn(min, max)
}

@CreatorsDsl
inline fun Long.boxIn(range: ClosedRange<Long>): Long {
    return coerceIn(range)
}

@CreatorsDsl
inline fun Double.abs(): Double {
    return Numeric.absOf(this)
}

@CreatorsDsl
inline fun Double.neg(): Double {
    return -this
}

@CreatorsDsl
inline infix fun Double.minOf(other: Double): Double {
    return min(toFinite(), other.toFinite())
}

@CreatorsDsl
inline infix fun Double.maxOf(other: Double): Double {
    return max(toFinite(), other.toFinite())
}

@CreatorsDsl
inline fun Double.boxIn(min: Double, max: Double): Double {
    return toFinite().coerceIn(min.toFinite(), max.toFinite())
}

@CreatorsDsl
inline fun Double.boxIn(range: ClosedFloatingPointRange<Double>): Double {
    return toFinite().coerceIn(range)
}

@CreatorsDsl
inline fun Double.ceil(): Double {
    return ceil(this)
}

@CreatorsDsl
inline fun Double.floor(): Double {
    return floor(this)
}

@CreatorsDsl
inline fun Double.truncated(): Double {
    return if (isValid()) if (isNegative()) ceil() else floor() else 0.0
}

@CreatorsDsl
inline fun Float.isValid(): Boolean {
    return isFinite()
}

@CreatorsDsl
inline fun Double.isValid(): Boolean {
    return isFinite()
}

@CreatorsDsl
inline fun Double.isNegative(): Boolean {
    return sign < 0
}

@CreatorsDsl
inline fun Double.isEven(): Boolean {
    return truncated().abs() % 2 == 0.0
}

@CreatorsDsl
inline fun Double.isNotEven(): Boolean {
    return truncated().abs() % 2 == 1.0
}

@CreatorsDsl
inline fun Int.isValidDivisor(): Boolean {
    return (this != 0)
}

@CreatorsDsl
inline fun Long.isValidDivisor(): Boolean {
    return (this != 0L)
}

@CreatorsDsl
inline fun Double.isValidDivisor(): Boolean {
    return (toFinite().abs() != 0.0)
}

@CreatorsDsl
inline fun Int.toValidDivisor(): Int {
    return if (isValidDivisor()) this else throw MercenaryFatalExceptiion(MATH_ZERO_DIVISOR_ERROR)
}

@CreatorsDsl
inline fun Long.toValidDivisor(): Long {
    return if (isValidDivisor()) this else throw MercenaryFatalExceptiion(MATH_ZERO_DIVISOR_ERROR)
}

@CreatorsDsl
inline fun Double.toValidDivisor(): Double {
    return if (isValidDivisor()) this else throw MercenaryFatalExceptiion(MATH_ZERO_DIVISOR_ERROR)
}

@CreatorsDsl
fun Double.toDecimalPlacesString(scale: Int = Numeric.DEFAULT_PRECISION_SCALE, places: Int = scale.abs()): String {
    return Numeric.toDecimalPlacesString(this, scale, places)
}

@CreatorsDsl
fun Double.closeEnough(value: Double, precision: Double = Numeric.DEFAULT_PRECISION_DELTA): Boolean {
    return Numeric.closeEnough(this, value, precision)
}

@CreatorsDsl
fun DoubleArray.closeEnough(value: DoubleArray, precision: Double = Numeric.DEFAULT_PRECISION_DELTA): Boolean {
    return Numeric.closeEnough(this, value, precision)
}

@CreatorsDsl
fun DoubleArray.closeEnough(value: Array<Double>, precision: Double = Numeric.DEFAULT_PRECISION_DELTA): Boolean {
    return Numeric.closeEnough(this, value, precision)
}

@CreatorsDsl
fun Array<Double>.closeEnough(value: DoubleArray, precision: Double = Numeric.DEFAULT_PRECISION_DELTA): Boolean {
    return Numeric.closeEnough(value, this, precision)
}

@CreatorsDsl
fun Array<Double>.closeEnough(value: Array<Double>, precision: Double = Numeric.DEFAULT_PRECISION_DELTA): Boolean {
    return Numeric.closeEnough(this, value, precision)
}

@CreatorsDsl
fun Array<DoubleArray>.closeEnough(value: Array<DoubleArray>, precision: Double = Numeric.DEFAULT_PRECISION_DELTA): Boolean {
    return Numeric.closeEnough(this, value, precision)
}

@CreatorsDsl
fun Array<Array<Double>>.closeEnough(value: Array<Array<Double>>, precision: Double = Numeric.DEFAULT_PRECISION_DELTA): Boolean {
    return Numeric.closeEnough(this, value, precision)
}

@CreatorsDsl
fun Double.rounded(scale: Int = Numeric.DEFAULT_PRECISION_SCALE): Double {
    return Numeric.rounded(this, scale)
}

@CreatorsDsl
fun DoubleArray.rounded(scale: Int = Numeric.DEFAULT_PRECISION_SCALE): DoubleArray {
    return Numeric.rounded(this, scale)
}

@CreatorsDsl
fun Array<Double>.rounded(scale: Int = Numeric.DEFAULT_PRECISION_SCALE): Array<Double> {
    return Numeric.rounded(this, scale)
}

@CreatorsDsl
fun Array<DoubleArray>.rounded(scale: Int = Numeric.DEFAULT_PRECISION_SCALE): Array<DoubleArray> {
    return Numeric.rounded(this, scale)
}

@CreatorsDsl
fun Array<Array<Double>>.rounded(scale: Int = Numeric.DEFAULT_PRECISION_SCALE): Array<Array<Double>> {
    return Numeric.rounded(this, scale)
}

@CreatorsDsl
fun Iterable<Double>.rounded(scale: Int = Numeric.DEFAULT_PRECISION_SCALE): Iterable<Double> {
    return Numeric.rounded(this, scale)
}

@CreatorsDsl
fun Sequence<Double>.rounded(scale: Int = Numeric.DEFAULT_PRECISION_SCALE): Sequence<Double> {
    return Numeric.rounded(this, scale)
}

@CreatorsDsl
infix fun Double.rootOf(root: Int): Double {
    return Numeric.rootOf(this, root)
}

@CreatorsDsl
infix fun Double.rootOf(root: Long): Double {
    return Numeric.rootOf(this, root)
}

@CreatorsDsl
infix fun Double.rootOf(root: Double): Double {
    return Numeric.rootOf(this, root)
}

@CreatorsDsl
infix fun Int.power(x: Int): Double = toDouble().power(x)

@CreatorsDsl
infix fun Int.power(x: Long): Double = toDouble().power(x)

@CreatorsDsl
infix fun Int.power(x: Double): Double = toDouble().power(x)

@CreatorsDsl
infix fun Long.power(x: Int): Double = toDouble().power(x)

@CreatorsDsl
infix fun Long.power(x: Long): Double = toDouble().power(x)

@CreatorsDsl
infix fun Long.power(x: Double): Double = toDouble().power(x)

@CreatorsDsl
infix fun Double.power(x: Int): Double = Numeric.powerOf(this, x)

@CreatorsDsl
infix fun Double.power(x: Long): Double = Numeric.powerOf(this, x)

@CreatorsDsl
infix fun Double.power(x: Double): Double = Numeric.powerOf(this, x)

@CreatorsDsl
inline fun Double.toFinite(): Double = if (isValid()) this else throw MercenaryFatalExceptiion("invalid value $this")

@CreatorsDsl
inline fun Double.toFiniteOrElse(value: Double): Double = if (isValid()) this else value

@CreatorsDsl
inline fun Double.toFiniteOrElse(block: () -> Double): Double = if (isValid()) this else block.invoke()

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

@CreatorsDsl
inline infix fun Int.forEach(action: (Int) -> Unit) {
    for (index in 0 until this) {
        action(index)
    }
}

@CreatorsDsl
fun Int.toHexString(pads: Int = 0): String = toString(16).padStart(pads.maxOf(0), '0')

@CreatorsDsl
fun Byte.toHexString(pads: Int = 0): String = toString(16).padStart(pads.maxOf(0), '0')

@CreatorsDsl
fun Long.toHexString(pads: Int = 0): String = toString(16).padStart(pads.maxOf(0), '0')

@CreatorsDsl
fun Short.toHexString(pads: Int = 0): String = toString(16).padStart(pads.maxOf(0), '0')

@CreatorsDsl
fun Char.toHexString(pads: Int = 0): String = toInt().toString(16).padStart(pads.maxOf(0), '0')

@CreatorsDsl
fun Int.toBinaryString(pads: Int = Int.SIZE_BITS): String = toString(2).padStart(pads.maxOf(0), '0')

@CreatorsDsl
fun Byte.toBinaryString(pads: Int = Byte.SIZE_BITS): String = toString(2).padStart(pads.maxOf(0), '0')

@CreatorsDsl
fun Long.toBinaryString(pads: Int = Long.SIZE_BITS): String = toString(2).padStart(pads.maxOf(0), '0')

@CreatorsDsl
fun Short.toBinaryString(pads: Int = Short.SIZE_BITS): String = toString(2).padStart(pads.maxOf(0), '0')

@CreatorsDsl
fun Char.toBinaryString(pads: Int = Byte.SIZE_BITS * 2): String = toInt().toString(2).padStart(pads.maxOf(0), '0')
