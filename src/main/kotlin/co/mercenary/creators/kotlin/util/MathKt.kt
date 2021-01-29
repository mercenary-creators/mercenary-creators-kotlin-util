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

import java.math.BigDecimal
import java.util.concurrent.atomic.*
import kotlin.math.*

typealias Numeric = co.mercenary.creators.kotlin.util.math.Numeric

typealias MercenaryMathExceptiion = MercenaryFatalExceptiion

@FrameworkDsl
const val POSITIVE_ONE = 1.0

@FrameworkDsl
const val NEGATIVE_ONE = -1.0

@FrameworkDsl
const val MATH_INVALID_SIZE_ERROR = "invalid size"

@FrameworkDsl
const val MATH_ZERO_DIVISOR_ERROR = "can't divide by zero"

@FrameworkDsl
const val MATH_OVERFLOW_FOR_ERROR = "overflow for operation"

@FrameworkDsl
fun gcdOf(vararg args: Int): Int = Numeric.gcdOf(*args)

@FrameworkDsl
fun gcdOf(vararg args: Long): Long = Numeric.gcdOf(*args)

@FrameworkDsl
fun gcdOf(value: Int): Int = Numeric.gcdOf(value)

@FrameworkDsl
fun gcdOf(value: Long): Long = Numeric.gcdOf(value)

@FrameworkDsl
fun gcdOf(value: Int, other: Int): Int = Numeric.gcdOf(value, other)

@FrameworkDsl
fun gcdOf(value: Long, other: Long): Long = Numeric.gcdOf(value, other)

@FrameworkDsl
fun gcdOf(value: Int, other: Long): Long = Numeric.gcdOf(value, other)

@FrameworkDsl
fun gcdOf(value: Long, other: Int): Long = Numeric.gcdOf(value, other)

@FrameworkDsl
fun lcmOf(vararg args: Int): Int = Numeric.lcmOf(*args)

@FrameworkDsl
fun lcmOf(vararg args: Long): Long = Numeric.lcmOf(*args)

@FrameworkDsl
fun lcmOf(value: Int): Int = Numeric.lcmOf(value)

@FrameworkDsl
fun lcmOf(value: Long): Long = Numeric.lcmOf(value)

@FrameworkDsl
fun lcmOf(value: Int, other: Int): Int = Numeric.lcmOf(value, other)

@FrameworkDsl
fun lcmOf(value: Long, other: Long): Long = Numeric.lcmOf(value, other)

@FrameworkDsl
fun lcmOf(value: Int, other: Long): Long = Numeric.lcmOf(value, other)

@FrameworkDsl
fun lcmOf(value: Long, other: Int): Long = Numeric.lcmOf(value, other)

@FrameworkDsl
inline fun Int.absOf(): Int {
    return Numeric.absOf(this)
}

@FrameworkDsl
inline fun Int.negOf(): Int {
    return if (this == 0) this else -this
}

@FrameworkDsl
inline fun Int.copyOf(): Int = this

@FrameworkDsl
inline fun Int.intsOf(): Int = this

@FrameworkDsl
inline fun Long.intsOf(): Int = toInt()

@FrameworkDsl
inline fun Byte.intsOf(): Int = toInt()

@FrameworkDsl
inline fun Char.intsOf(): Int = toInt()

@FrameworkDsl
inline fun Short.intsOf(): Int = toInt()

@FrameworkDsl
inline fun AtomicInteger.intsOf(): Int = getValue()

@FrameworkDsl
inline fun AtomicLong.intsOf(): Int = getValue().intsOf()

@FrameworkDsl
inline fun Long.copyOf(): Long = this

@FrameworkDsl
inline fun Int.longOf(): Long = toLong()

@FrameworkDsl
inline fun Long.longOf(): Long = this

@FrameworkDsl
inline fun Byte.longOf(): Long = toLong()

@FrameworkDsl
inline fun Char.longOf(): Long = toLong()

@FrameworkDsl
inline fun Short.longOf(): Long = toLong()

@FrameworkDsl
inline fun AtomicLong.longOf(): Long = getValue()

@FrameworkDsl
inline fun AtomicInteger.longOf(): Long = getValue().longOf()

@FrameworkDsl
inline operator fun Long.plus(other: Char): Long = this + other.intsOf()

@FrameworkDsl
inline fun Int.isNegative(): Boolean {
    return this < 0
}

@FrameworkDsl
inline fun Int.isLessThan(other: Int): Boolean {
    return this < other
}

@FrameworkDsl
inline fun Int.isMoreThan(other: Int): Boolean {
    return this > other
}

@FrameworkDsl
inline fun Int.isEven(): Boolean {
    return absOf() % 2 == 0
}

@FrameworkDsl
inline fun Int.isNotEven(): Boolean {
    return absOf() % 2 == 1
}

@FrameworkDsl
inline infix fun Int.minOf(other: Int): Int {
    return if (this < other) this else other
}

@FrameworkDsl
inline infix fun Int.maxOf(other: Int): Int {
    return if (this < other) other else this
}

@FrameworkDsl
inline fun Int.boxIn(min: Int, max: Int): Int {
    return coerceIn(min, max)
}

@FrameworkDsl
inline fun Int.boxIn(range: ClosedRange<Int>): Int {
    return coerceIn(range)
}

@FrameworkDsl
inline fun Long.absOf(): Long {
    return Numeric.absOf(this)
}

@FrameworkDsl
inline fun Long.negOf(): Long {
    return if (this == 0L) this else -this
}

@FrameworkDsl
inline fun Long.isNegative(): Boolean {
    return this < 0
}

@FrameworkDsl
inline fun Long.isEven(): Boolean {
    return absOf() % 2 == 0L
}

@FrameworkDsl
inline fun Long.isNotEven(): Boolean {
    return absOf() % 2 == 1L
}

@FrameworkDsl
inline infix fun Long.minOf(other: Int): Long {
    return minOf(other.longOf())
}

@FrameworkDsl
inline infix fun Long.maxOf(other: Int): Long {
    return maxOf(other.longOf())
}

@FrameworkDsl
inline infix fun Long.minOf(other: Long): Long {
    return if (this < other) this else other
}

@FrameworkDsl
inline infix fun Long.maxOf(other: Long): Long {
    return if (this < other) other else this
}

@FrameworkDsl
inline fun Long.boxIn(min: Int, max: Int): Long {
    return boxIn(min.longOf(), max.longOf())
}

@FrameworkDsl
inline fun Long.boxIn(min: Long, max: Long): Long {
    return coerceIn(min, max)
}

@FrameworkDsl
inline fun Long.boxIn(range: ClosedRange<Long>): Long {
    return coerceIn(range)
}

@FrameworkDsl
inline fun Double.absOf(): Double {
    return Numeric.absOf(this)
}

@FrameworkDsl
inline fun Double.negOf(): Double {
    return -this
}

@FrameworkDsl
inline infix fun Double.minOf(other: Double): Double {
    return toFinite().coerceAtMost(other.toFinite())
}

@FrameworkDsl
inline infix fun Double.maxOf(other: Double): Double {
    return toFinite().coerceAtLeast(other.toFinite())
}

@FrameworkDsl
inline fun Double.boxIn(min: Double, max: Double): Double {
    return toFinite().coerceIn(min.toFinite(), max.toFinite())
}

@FrameworkDsl
inline fun Double.boxIn(range: ClosedFloatingPointRange<Double>): Double {
    return toFinite().coerceIn(range)
}

@FrameworkDsl
inline fun Double.ceilOf(): Double {
    return ceil(toFinite())
}

@FrameworkDsl
inline fun Double.floorOf(): Double {
    return floor(toFinite())
}

@FrameworkDsl
inline fun Double.truncated(): Double {
    return if (isValid()) if (isPositive()) floorOf() else ceilOf() else 0.0
}

@FrameworkDsl
inline fun Float.isValid(): Boolean = isFinite()

@FrameworkDsl
inline fun Float.isNotValid(): Boolean = isNaN() || isInfinite()

@FrameworkDsl
inline fun Double.isValid(): Boolean = isFinite()

@FrameworkDsl
inline fun Double.isNotValid(): Boolean = isNaN() || isInfinite()

@FrameworkDsl
inline fun Double.isNegative(): Boolean {
    return sign < 0
}

@FrameworkDsl
inline fun Double.isPositive(): Boolean {
    return this > 0
}

@FrameworkDsl
inline fun Double.isEven(): Boolean {
    return truncated().absOf() % 2 == 0.0
}

@FrameworkDsl
inline fun Double.isNotEven(): Boolean {
    return truncated().absOf() % 2 == 1.0
}

@FrameworkDsl
inline fun Int.isValidDivisor(): Boolean {
    return (this != 0)
}

@FrameworkDsl
inline fun Long.isValidDivisor(): Boolean {
    return (this != 0L)
}

@FrameworkDsl
inline fun Double.isValidDivisor(): Boolean {
    return (toFinite().absOf() != 0.0)
}

@FrameworkDsl
inline fun Int.toValidDivisor(): Int {
    return if (isValidDivisor()) this else throw MercenaryFatalExceptiion(MATH_ZERO_DIVISOR_ERROR)
}

@FrameworkDsl
inline fun Long.toValidDivisor(): Long {
    return if (isValidDivisor()) this else throw MercenaryFatalExceptiion(MATH_ZERO_DIVISOR_ERROR)
}

@FrameworkDsl
inline fun Double.toValidDivisor(): Double {
    return if (isValidDivisor()) this else throw MercenaryFatalExceptiion(MATH_ZERO_DIVISOR_ERROR)
}

@FrameworkDsl
fun Double.toDecimalPlacesString(scale: Int = Numeric.DEFAULT_PRECISION_SCALE, places: Int = scale.absOf()): String {
    return Numeric.toDecimalPlacesString(this, scale, places)
}

@FrameworkDsl
fun Double.closeEnough(value: Double, precision: Double = Numeric.DEFAULT_PRECISION_DELTA): Boolean {
    return Numeric.closeEnough(this, value, precision)
}

@FrameworkDsl
fun DoubleArray.closeEnough(value: DoubleArray, precision: Double = Numeric.DEFAULT_PRECISION_DELTA): Boolean {
    return Numeric.closeEnough(this, value, precision)
}

@FrameworkDsl
fun DoubleArray.closeEnough(value: Array<Double>, precision: Double = Numeric.DEFAULT_PRECISION_DELTA): Boolean {
    return Numeric.closeEnough(this, value, precision)
}

@FrameworkDsl
fun Array<Double>.closeEnough(value: DoubleArray, precision: Double = Numeric.DEFAULT_PRECISION_DELTA): Boolean {
    return Numeric.closeEnough(value, this, precision)
}

@FrameworkDsl
fun Array<Double>.closeEnough(value: Array<Double>, precision: Double = Numeric.DEFAULT_PRECISION_DELTA): Boolean {
    return Numeric.closeEnough(this, value, precision)
}

@FrameworkDsl
fun Array<DoubleArray>.closeEnough(value: Array<DoubleArray>, precision: Double = Numeric.DEFAULT_PRECISION_DELTA): Boolean {
    return Numeric.closeEnough(this, value, precision)
}

@FrameworkDsl
fun Array<Array<Double>>.closeEnough(value: Array<Array<Double>>, precision: Double = Numeric.DEFAULT_PRECISION_DELTA): Boolean {
    return Numeric.closeEnough(this, value, precision)
}

@FrameworkDsl
fun Double.rounded(scale: Int = Numeric.DEFAULT_PRECISION_SCALE): Double {
    return Numeric.rounded(this, scale)
}

@FrameworkDsl
fun DoubleArray.rounded(scale: Int = Numeric.DEFAULT_PRECISION_SCALE): DoubleArray {
    return Numeric.rounded(this, scale)
}

@FrameworkDsl
fun Array<Double>.rounded(scale: Int = Numeric.DEFAULT_PRECISION_SCALE): Array<Double> {
    return Numeric.rounded(this, scale)
}

@FrameworkDsl
fun Array<DoubleArray>.rounded(scale: Int = Numeric.DEFAULT_PRECISION_SCALE): Array<DoubleArray> {
    return Numeric.rounded(this, scale)
}

@FrameworkDsl
fun Array<Array<Double>>.rounded(scale: Int = Numeric.DEFAULT_PRECISION_SCALE): Array<Array<Double>> {
    return Numeric.rounded(this, scale)
}

@FrameworkDsl
fun Iterable<Double>.rounded(scale: Int = Numeric.DEFAULT_PRECISION_SCALE): Iterable<Double> {
    return Numeric.rounded(this, scale)
}

@FrameworkDsl
fun Sequence<Double>.rounded(scale: Int = Numeric.DEFAULT_PRECISION_SCALE): Sequence<Double> {
    return Numeric.rounded(this, scale)
}

@FrameworkDsl
infix fun Double.rootOf(root: Int): Double {
    return Numeric.rootOf(this, root)
}

@FrameworkDsl
infix fun Double.rootOf(root: Long): Double {
    return Numeric.rootOf(this, root)
}

@FrameworkDsl
infix fun Double.rootOf(root: Double): Double {
    return Numeric.rootOf(this, root)
}

@FrameworkDsl
infix fun Int.power(x: Int): Double = toDouble().power(x)

@FrameworkDsl
infix fun Int.power(x: Long): Double = toDouble().power(x)

@FrameworkDsl
infix fun Int.power(x: Double): Double = toDouble().power(x)

@FrameworkDsl
infix fun Long.power(x: Int): Double = toDouble().power(x)

@FrameworkDsl
infix fun Long.power(x: Long): Double = toDouble().power(x)

@FrameworkDsl
infix fun Long.power(x: Double): Double = toDouble().power(x)

@FrameworkDsl
infix fun Double.power(x: Int): Double = Numeric.powerOf(this, x)

@FrameworkDsl
infix fun Double.power(x: Long): Double = Numeric.powerOf(this, x)

@FrameworkDsl
infix fun Double.power(x: Double): Double = Numeric.powerOf(this, x)

@FrameworkDsl
inline infix fun Double.dividedBy(x: Int): Double = toFinite() / x.toValidDivisor()

@FrameworkDsl
inline infix fun Double.dividedBy(x: Long): Double = toFinite() / x.toValidDivisor()

@FrameworkDsl
inline infix fun Double.dividedBy(x: Double): Double = toFinite() / x.toValidDivisor()

@FrameworkDsl
inline fun Double.toFinite(): Double = if (isValid()) this else throw MercenaryFatalExceptiion("invalid value $this")

@FrameworkDsl
inline fun Double.toFiniteOrElse(value: Double = 0.0): Double = if (isValid()) this else value

@FrameworkDsl
inline fun Double.toFiniteOrElse(block: Factory<Double>): Double = if (isValid()) this else block()

@FrameworkDsl
inline fun DoubleArray.average(default: Double): Double {
    return when (sizeOf()) {
        0 -> default
        1 -> this[0].toFiniteOrElse(default)
        else -> average().toFiniteOrElse(default)
    }
}

@FrameworkDsl
inline fun DoubleArray.average(index: Int, place: Int, default: Double): Double {
    return when (sizeOf() == 0 || sizeOf().isNotValidRange(index, place)) {
        true -> default
        else -> copyOfRange(index, place).average(default)
    }
}

@FrameworkDsl
inline fun DoubleArray.reduce(index: Int, place: Int, reducer: (Double, Double) -> Double): Double {
    if (isExhausted()) {
        throw MercenaryMathExceptiion("reduce().isExhausted()")
    }
    if (sizeOf().isNotValidRange(index, place)) {
        throw MercenaryMathExceptiion("reduce().isNotValidRange()")
    }
    return copyOfRange(index, place).reduce(reducer)
}

@FrameworkDsl
inline fun Int.isValidRange(from: Int, last: Int): Boolean {
    return when {
        from.isLessThan(0) -> false
        from.isMoreThan(last) -> false
        else -> last.isMoreThan(copyOf())
    }
}

@FrameworkDsl
inline fun Int.isNotValidRange(from: Int, last: Int): Boolean {
    return when {
        from.isLessThan(0) -> true
        from.isMoreThan(last) -> true
        else -> last.isMoreThan(copyOf()).isNotTrue()
    }
}

@FrameworkDsl
fun Byte.toMaskedInt(): Int = longOf().toMaskedInt()

@FrameworkDsl
fun Char.toMaskedInt(): Int = longOf().toMaskedInt()

@FrameworkDsl
fun Short.toMaskedInt(): Int = longOf().toMaskedInt()

@FrameworkDsl
fun Long.toMaskedInt(): Int = (this and 0xffffffffL).intsOf()

@FrameworkDsl
fun toDoubleArrayOf(vararg args: Double): DoubleArray = doubleArrayOf(*args)

@FrameworkDsl
fun toDoubleArrayOf(vararg args: Number): DoubleArray = DoubleArray(args.size) { i -> args[i].toDouble() }

@FrameworkDsl
inline fun vectorOf(size: Int = 0): DoubleArray = DoubleArray(size)

@FrameworkDsl
inline fun vectorOf(size: Int, value: Double): DoubleArray = DoubleArray(size) { value }

@FrameworkDsl
inline fun vectorOf(size: Int, block: (Int) -> Double): DoubleArray = DoubleArray(size) { i -> block(i) }

@FrameworkDsl
fun toArrayOfDoubleArray(cols: Int, args: DoubleArray): Array<DoubleArray> {
    return if (cols < 1) throw MercenaryFatalExceptiion(MATH_INVALID_SIZE_ERROR) else toArrayOfDoubleArray(args.sizeOf() / cols, cols, args)
}

@FrameworkDsl
fun toArrayOfDoubleArray(rows: Int, cols: Int, args: DoubleArray): Array<DoubleArray> {
    return if (args.sizeOf() != (rows * cols)) throw MercenaryFatalExceptiion(MATH_INVALID_SIZE_ERROR)
    else Array(rows) { r ->
        (r * cols).let { args.copyOfRange(it, it + cols) }
    }
}

@FrameworkDsl
inline infix fun Int.forEach(action: (Int) -> Unit) {
    for (index in 0 until this) {
        action(index)
    }
}

@FrameworkDsl
inline operator fun BigDecimal.div(value: Double): BigDecimal = div(value.toBigDecimal())

@FrameworkDsl
inline operator fun BigDecimal.rem(value: Double): BigDecimal = rem(value.toBigDecimal())

@FrameworkDsl
inline operator fun BigDecimal.plus(value: Double): BigDecimal = plus(value.toBigDecimal())

@FrameworkDsl
inline operator fun BigDecimal.minus(value: Double): BigDecimal = minus(value.toBigDecimal())

@FrameworkDsl
inline operator fun BigDecimal.times(value: Double): BigDecimal = times(value.toBigDecimal())

@FrameworkDsl
fun Int.toHexString(pads: Int = 0): String = toString(16).padStart(pads.maxOf(0), '0')

@FrameworkDsl
fun Byte.toHexString(pads: Int = 0): String = toString(16).padStart(pads.maxOf(0), '0')

@FrameworkDsl
fun Long.toHexString(pads: Int = 0): String = toString(16).padStart(pads.maxOf(0), '0')

@FrameworkDsl
fun Short.toHexString(pads: Int = 0): String = toString(16).padStart(pads.maxOf(0), '0')

@FrameworkDsl
fun Char.toHexString(pads: Int = 0): String = intsOf().toString(16).padStart(pads.maxOf(0), '0')

@FrameworkDsl
fun Float.toHexString(pads: Int = 0): String = toRawBits().toString(16).padStart(pads.maxOf(0), '0')

@FrameworkDsl
fun Double.toHexString(pads: Int = 0): String = toRawBits().toString(16).padStart(pads.maxOf(0), '0')

@FrameworkDsl
fun Int.toDecString(pads: Int = 0): String = toString(10).padStart(pads.maxOf(0), '0')

@FrameworkDsl
fun Byte.toDecString(pads: Int = 0): String = toString(10).padStart(pads.maxOf(0), '0')

@FrameworkDsl
fun Long.toDecString(pads: Int = 0): String = toString(10).padStart(pads.maxOf(0), '0')

@FrameworkDsl
fun Short.toDecString(pads: Int = 0): String = toString(10).padStart(pads.maxOf(0), '0')

@FrameworkDsl
fun Char.toDecString(pads: Int = 0): String = intsOf().toString(10).padStart(pads.maxOf(0), '0')

@FrameworkDsl
fun Int.toBinaryString(pads: Int = Int.SIZE_BITS): String = toString(2).padStart(pads.maxOf(0), '0')

@FrameworkDsl
fun Byte.toBinaryString(pads: Int = Byte.SIZE_BITS): String = toString(2).padStart(pads.maxOf(0), '0')

@FrameworkDsl
fun Long.toBinaryString(pads: Int = Long.SIZE_BITS): String = toString(2).padStart(pads.maxOf(0), '0')

@FrameworkDsl
fun Short.toBinaryString(pads: Int = Short.SIZE_BITS): String = toString(2).padStart(pads.maxOf(0), '0')

@FrameworkDsl
fun Char.toBinaryString(pads: Int = Char.SIZE_BITS): String = intsOf().toString(2).padStart(pads.maxOf(0), '0')

@FrameworkDsl
fun Float.toBinaryString(pads: Int = Float.SIZE_BITS): String = toRawBits().toString(2).padStart(pads.maxOf(0), '0')

@FrameworkDsl
fun Double.toBinaryString(pads: Int = Double.SIZE_BITS): String = toRawBits().toString(2).padStart(pads.maxOf(0), '0')

@FrameworkDsl
inline fun Long.isEmpty(): Boolean = absOf() == 0L

@FrameworkDsl
inline fun Long.isNotEmpty(): Boolean = absOf() != 0L

@FrameworkDsl
inline fun CharSequence.toLongOrElse(other: Long = 0L) = toTrimOr("0").toLongOrNull(10).otherwise(other)

@FrameworkDsl
inline fun Int.toDoubleArray(): DoubleArray = DoubleArray(maxOf(0))

@FrameworkDsl
inline fun Int.toDoubleArray(convert: Convert<Int, Double>): DoubleArray = DoubleArray(maxOf(0)) { index ->
    convert.convert(index)
}

@FrameworkDsl
inline fun DoubleArray.toDoubleArray(copy: Boolean = true): DoubleArray = if (copy) copyOf() else this
