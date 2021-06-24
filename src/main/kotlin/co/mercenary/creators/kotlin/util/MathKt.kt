/*
 * Copyright (c) 2021, Mercenary Creators Company. All rights reserved.
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

import java.math.*
import java.util.concurrent.atomic.*
import kotlin.math.*

typealias Numeric = co.mercenary.creators.kotlin.util.math.Numeric

typealias MercenaryMathExceptiion = MercenaryFatalExceptiion

@FrameworkDsl
const val MATH_POSITIVE_ONE = 1.0

@FrameworkDsl
const val MATH_POSITIVE_TWO = 2.0

@FrameworkDsl
const val MATH_NEGATIVE_ONE = -1.0

@FrameworkDsl
const val MATH_NEGATIVE_TWO = -2.0

@FrameworkDsl
const val MATH_INVALID_SIZE_ERROR = "invalid size"

@FrameworkDsl
const val MATH_ZERO_DIVISOR_ERROR = "can't divide by zero"

@FrameworkDsl
const val MATH_OVERFLOW_FOR_ERROR = "overflow for operation"

@FrameworkDsl
fun gcdOf(value: Int, vararg args: Int): Int = Numeric.gcdOf(intArrayOf(value, *args))

@FrameworkDsl
fun gcdOf(args: Sequence<Int>): Int = Numeric.gcdOf(args)

@FrameworkDsl
fun gcdOf(args: Iterable<Int>): Int = Numeric.gcdOf(args)

@FrameworkDsl
fun gcdOf(args: Iterator<Int>): Int = Numeric.gcdOf(args)

@FrameworkDsl
fun gcdOf(value: Long, vararg args: Long): Long = Numeric.gcdOf(longArrayOf(value, *args))

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
fun gcdOf(args: Sequence<Long>): Long = Numeric.gcdOf(args)

@FrameworkDsl
fun gcdOf(args: Iterable<Long>): Long = Numeric.gcdOf(args)

@FrameworkDsl
fun gcdOf(args: Iterator<Long>): Long = Numeric.gcdOf(args)

@FrameworkDsl
fun lcmOf(args: Sequence<Long>): Long = Numeric.lcmOf(args)

@FrameworkDsl
fun lcmOf(args: Iterable<Long>): Long = Numeric.lcmOf(args)

@FrameworkDsl
fun lcmOf(args: Iterator<Long>): Long = Numeric.lcmOf(args)

@FrameworkDsl
fun lcmOf(value: Int, vararg args: Int): Int = Numeric.lcmOf(intArrayOf(value, *args))

@FrameworkDsl
fun lcmOf(value: Long, vararg args: Long): Long = Numeric.lcmOf(longArrayOf(value, *args))

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
fun byteOf(high: Int, lows: Int): Byte {
    if (high.isNegative() || lows.isNegative()) {
        fail("byteOf($high, $lows) bad arguments")
    }
    return (high * 16 + lows).byteOf()
}

@FrameworkDsl
fun Array<Int>.getIntArray(): IntArray {
    if (isExhausted()) {
        return EMPTY_INTS_ARRAY
    }
    return toIntArray()
}

@FrameworkDsl
fun Iterator<Int>.getIntArray(): IntArray {
    if (isExhausted()) {
        return EMPTY_INTS_ARRAY
    }
    return toCollection().toIntArray()
}

@FrameworkDsl
fun Iterable<Int>.getIntArray(): IntArray {
    if (isExhausted()) {
        return EMPTY_INTS_ARRAY
    }
    return toCollection().toIntArray()
}

@FrameworkDsl
fun Sequence<Int>.getIntArray(): IntArray {
    if (isExhausted()) {
        return EMPTY_INTS_ARRAY
    }
    return toCollection().toIntArray()
}

@FrameworkDsl
fun Array<Char>.getCharArray(): CharArray {
    if (isExhausted()) {
        return EMPTY_CHAR_ARRAY
    }
    return toCharArray()
}

@FrameworkDsl
fun Iterator<Char>.getCharArray(): CharArray {
    if (isExhausted()) {
        return EMPTY_CHAR_ARRAY
    }
    return toCollection().toCharArray()
}

@FrameworkDsl
fun Iterable<Char>.getCharArray(): CharArray {
    if (isExhausted()) {
        return EMPTY_CHAR_ARRAY
    }
    return toCollection().toCharArray()
}

@FrameworkDsl
fun Sequence<Char>.getCharArray(): CharArray {
    if (isExhausted()) {
        return EMPTY_CHAR_ARRAY
    }
    return toCollection().toCharArray()
}

@FrameworkDsl
fun Array<Long>.getLongArray(): LongArray {
    if (isExhausted()) {
        return EMPTY_LONG_ARRAY
    }
    return toLongArray()
}

@FrameworkDsl
fun Iterator<Long>.getLongArray(): LongArray {
    if (isExhausted()) {
        return EMPTY_LONG_ARRAY
    }
    return toCollection().toLongArray()
}

@FrameworkDsl
fun Iterable<Long>.getLongArray(): LongArray {
    if (isExhausted()) {
        return EMPTY_LONG_ARRAY
    }
    return toCollection().toLongArray()
}

@FrameworkDsl
fun Sequence<Long>.getLongArray(): LongArray {
    if (isExhausted()) {
        return EMPTY_LONG_ARRAY
    }
    return toCollection().toLongArray()
}

@FrameworkDsl
fun Array<Double>.getDoubleArray(): DoubleArray {
    if (isExhausted()) {
        return EMPTY_REAL_ARRAY
    }
    return toDoubleArray()
}

@FrameworkDsl
fun Iterator<Double>.getDoubleArray(): DoubleArray {
    if (isExhausted()) {
        return EMPTY_REAL_ARRAY
    }
    return toCollection().toDoubleArray()
}

@FrameworkDsl
fun Iterable<Double>.getDoubleArray(): DoubleArray {
    if (isExhausted()) {
        return EMPTY_REAL_ARRAY
    }
    return toCollection().toDoubleArray()
}

@FrameworkDsl
fun Sequence<Double>.getDoubleArray(): DoubleArray {
    if (isExhausted()) {
        return EMPTY_REAL_ARRAY
    }
    return toCollection().toDoubleArray()
}

@FrameworkDsl
inline fun Int.isPowerOf2(): Boolean {
    if (isLessThan(1) || isMoreThan(MAXIMUM_INTS_POWER_OF_2)) {
        return false
    }
    return ((this and this.negOf()) == this)
}

@FrameworkDsl
inline fun Long.isPowerOf2(): Boolean {
    if (isLessThan(1L) || isMoreThan(MAXIMUM_LONG_POWER_OF_2)) {
        return false
    }
    return ((this and this.negOf()) == this)
}

@FrameworkDsl
inline fun Int.closerOf(less: Int, more: Int): Int {
    if (less > more) {
        throw MercenaryMathExceptiion(MATH_INVALID_SIZE_ERROR)
    }
    if (isEither(less, more)) {
        return this
    }
    return if (distTo(less) < distTo(more)) less else more
}

@FrameworkDsl
inline fun ushrOf(args: Int): Int {
    return args.ushrOr(1).ushrOr(2).ushrOr(4).ushrOr(8).ushrOr(16)
}

@FrameworkDsl
inline fun ushrOf(args: Long): Long {
    return args.ushrOr(1).ushrOr(2).ushrOr(4).ushrOr(8).ushrOr(16).ushrOr(32)
}

@FrameworkDsl
inline infix fun Int.ushrOr(bits: Int): Int {
    return this or (this ushr bits)
}

@FrameworkDsl
inline infix fun Long.ushrOr(bits: Int): Long {
    return this or (this ushr bits)
}

@FrameworkDsl
inline infix fun Int.calcTo(calc: Convert<Int, Int>): Int {
    return calc.convert(this)
}

@FrameworkDsl
inline infix fun Int.compareOf(other: Int): Int {
    return this.absOf().compareTo(other.absOf())
}

@FrameworkDsl
inline infix fun Int.diffOf(other: Int): Int {
    return (other - this).absOf()
}

@FrameworkDsl
inline infix fun Int.distTo(other: Int): Double {
    return (other - this).let { Numeric.distOf(it, it) }
}

@FrameworkDsl
inline infix fun Long.distTo(other: Long): Double {
    return (other - this).let { Numeric.distOf(it, it) }
}

@FrameworkDsl
inline infix fun Double.distTo(other: Double): Double {
    return (other - this).let { Numeric.distOf(it, it) }
}

@FrameworkDsl
inline fun Int.absOf(): Int {
    return Numeric.absOf(this)
}

@FrameworkDsl
inline fun Int.negOf(): Int {
    return if (this == 0) this else -this
}

@FrameworkDsl
inline fun Int.byteOf(): Byte = toByte()

@FrameworkDsl
inline fun Byte.byteOf(): Byte = this

@FrameworkDsl
inline fun Byte.copyOf(): Byte = this

@FrameworkDsl
inline fun Char.byteOf(): Byte = toCode().byteOf()

@FrameworkDsl
inline fun Long.byteOf(): Byte = toByte()

@FrameworkDsl
inline fun Short.byteOf(): Byte = toByte()

@FrameworkDsl
inline fun Int.copyOf(): Int = this

@FrameworkDsl
inline fun Int.intsOf(): Int = this

@FrameworkDsl
inline fun Long.intsOf(): Int = toInt()

@FrameworkDsl
inline fun Byte.intsOf(): Int = toInt()

@FrameworkDsl
inline fun Char.intsOf(): Int = toCode()

@FrameworkDsl
inline fun Short.intsOf(): Int = toInt()

@FrameworkDsl
inline fun Float.intsOf(default: Int = 0): Int = toFiniteOrElse(default.realOf()).toInt()

@FrameworkDsl
inline fun Double.intsOf(default: Int = 0): Int = toFiniteOrElse(default.realOf()).toInt()

@FrameworkDsl
inline fun AtomicInteger.intsOf(): Int = getValue()

@FrameworkDsl
inline fun AtomicLong.intsOf(): Int = getValue().intsOf()

@FrameworkDsl
inline fun Char.copyOf(): Char = this

@FrameworkDsl
inline fun Long.copyOf(): Long = this

@FrameworkDsl
inline fun Int.longOf(): Long = toLong()

@FrameworkDsl
inline fun Long.longOf(): Long = this

@FrameworkDsl
inline fun Byte.longOf(): Long = toLong()

@FrameworkDsl
inline fun Char.longOf(): Long = toCode().longOf()

@FrameworkDsl
inline fun Short.longOf(): Long = toLong()

@FrameworkDsl
inline fun Int.realOf(): Double = toDouble()

@FrameworkDsl
inline fun Long.realOf(): Double = toDouble()

@FrameworkDsl
inline fun Byte.realOf(): Double = toDouble()

@FrameworkDsl
inline fun Char.realOf(): Double = toCode().realOf()

@FrameworkDsl
inline fun Short.realOf(): Double = toDouble()

@FrameworkDsl
inline fun Float.realOf(): Double = toDouble()

@FrameworkDsl
inline fun Double.realOf(): Double = this

@FrameworkDsl
inline fun Double.copyOf(): Double = this

@FrameworkDsl
inline fun BigDecimal.realOf(): Double = toDouble()

@FrameworkDsl
inline fun BigInteger.realOf(): Double = toDouble()

@FrameworkDsl
inline fun AtomicLong.realOf(): Double = toDouble()

@FrameworkDsl
inline fun AtomicInteger.realOf(): Double = toDouble()

@FrameworkDsl
inline fun Float.longOf(default: Long = 0L): Long = toFiniteOrElse(default.realOf()).toLong()

@FrameworkDsl
inline fun Double.longOf(default: Long = 0L): Long = toFiniteOrElse(default.realOf()).toLong()

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
inline fun Int.isEither(value: Int, other: Int): Boolean {
    return (this == value) || (this == other)
}

@FrameworkDsl
inline fun Int.isBetween(less: Int, more: Int): Boolean {
    return if (less > more) false else ((this >= less) && (this <= more))
}

@FrameworkDsl
inline infix fun Int.isLessThan(other: Int): Boolean {
    return this < other
}

@FrameworkDsl
inline infix fun Int.isLessSame(other: Int): Boolean {
    return this <= other
}

@FrameworkDsl
inline infix fun Int.isMoreThan(other: Int): Boolean {
    return this > other
}

@FrameworkDsl
inline infix fun Int.isMoreSame(other: Int): Boolean {
    return this >= other
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
inline fun Long.isEither(value: Long, other: Long): Boolean {
    return (this == value) || (this == other)
}

@FrameworkDsl
inline fun Long.isBetween(less: Long, more: Long): Boolean {
    return if (less > more) false else ((this >= less) && (this <= more))
}

@FrameworkDsl
inline infix fun Long.isLessThan(other: Long): Boolean {
    return this < other
}

@FrameworkDsl
inline infix fun Long.isLessSame(other: Long): Boolean {
    return this <= other
}

@FrameworkDsl
inline infix fun Long.isMoreThan(other: Long): Boolean {
    return this > other
}

@FrameworkDsl
inline infix fun Long.isMoreSame(other: Long): Boolean {
    return this >= other
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
    return if (isValid()) if (isMoreThan(0.0)) floorOf() else ceilOf() else 0.0
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
    return isNegative().isNotTrue()
}

@FrameworkDsl
inline fun Double.isEither(value: Double, other: Double): Boolean {
    return (this == value) || (this == other)
}

@FrameworkDsl
inline fun Double.isBetween(less: Double, more: Double): Boolean {
    return if (less > more) false else ((this >= less) && (this <= more))
}

@FrameworkDsl
inline infix fun Double.isLessThan(other: Double): Boolean {
    return this < other
}

@FrameworkDsl
inline infix fun Double.isLessSame(other: Double): Boolean {
    return this <= other
}

@FrameworkDsl
inline infix fun Double.isMoreThan(other: Double): Boolean {
    return this > other
}

@FrameworkDsl
inline infix fun Double.isMoreSame(other: Double): Boolean {
    return this >= other
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
    return if (isValidDivisor()) this else throw MercenaryMathExceptiion(MATH_ZERO_DIVISOR_ERROR)
}

@FrameworkDsl
inline fun Long.toValidDivisor(): Long {
    return if (isValidDivisor()) this else throw MercenaryMathExceptiion(MATH_ZERO_DIVISOR_ERROR)
}

@FrameworkDsl
inline fun Double.toValidDivisor(): Double {
    return if (isValidDivisor()) this else throw MercenaryMathExceptiion(MATH_ZERO_DIVISOR_ERROR)
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
infix fun Int.power(x: Int): Double = realOf().power(x)

@FrameworkDsl
infix fun Int.power(x: Long): Double = realOf().power(x)

@FrameworkDsl
infix fun Int.power(x: Double): Double = realOf().power(x)

@FrameworkDsl
infix fun Long.power(x: Int): Double = realOf().power(x)

@FrameworkDsl
infix fun Long.power(x: Long): Double = realOf().power(x)

@FrameworkDsl
infix fun Long.power(x: Double): Double = realOf().power(x)

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
inline fun Double.toFiniteOrElse(block: Factory<Double>): Double = if (isValid()) this else block.create()

@FrameworkDsl
inline fun Float.toFiniteOrElse(value: Float = 0.0f): Float = if (isValid()) this else value

@FrameworkDsl
inline fun Float.toFiniteOrElse(value: Double = 0.0): Float = if (isValid()) this else toFiniteOrElse(value.toFloat())

@FrameworkDsl
inline fun Float.toFiniteOrElse(block: Factory<Float>): Float = if (isValid()) this else block.create()

@FrameworkDsl
fun DoubleArray.sorted(descending: Boolean = false): DoubleArray {
    if (sizeOf() > 1) {
        sort()
        if (descending.isTrue()) {
            reverse()
        }
    }
    return this
}

@FrameworkDsl
fun DoubleArray.sliced(index: Int, place: Int): DoubleArray {
    if (sizeOf().isNotValidRange(index, place)) {
        throw MercenaryMathExceptiion("sliced().isNotValidRange()")
    }
    return copyOfRange(index, place)
}

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
        else -> sliced(index, place).average(default)
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
    return sliced(index, place).reduce(reducer)
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
fun toDoubleArrayOf(vararg args: Double): DoubleArray = if (args.isExhausted()) EMPTY_REAL_ARRAY else DoubleArray(args.sizeOf()) { i -> args[i] }

@FrameworkDsl
fun toDoubleArrayOf(vararg args: Number): DoubleArray = if (args.isExhausted()) EMPTY_REAL_ARRAY else DoubleArray(args.sizeOf()) { i -> args[i].toDouble() }

@FrameworkDsl
fun toArrayOfDoubleArray(cols: Int, args: DoubleArray): Array<DoubleArray> {
    return if (cols < 1) throw MercenaryMathExceptiion(MATH_INVALID_SIZE_ERROR) else toArrayOfDoubleArray(args.sizeOf() / cols, cols, args)
}

@FrameworkDsl
fun toArrayOfDoubleArray(rows: Int, cols: Int, args: DoubleArray): Array<DoubleArray> {
    return if (args.sizeOf() != (rows * cols)) throw MercenaryMathExceptiion(MATH_INVALID_SIZE_ERROR)
    else Array(rows) { r ->
        (r * cols).let { args.sliced(it, it + cols) }
    }
}

@FrameworkDsl
inline infix fun Int.forEach(action: (Int) -> Unit) {
    for (index in 0 until this) {
        action(index)
    }
}

@FrameworkDsl
inline infix fun Int.forEachOther(action: (Int, Int) -> Unit) {
    for (index in 0 until this step 2) {
        action(index, index + 1)
    }
}

@FrameworkDsl
inline fun Int.forEachStep(jump: Int, action: (Int) -> Unit) {
    for (index in 0 until this step jump.absOf()) {
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
fun Char.toHexString(pads: Int = 0): String = toCode().toShort().toString(16).padStart(pads.maxOf(0), '0')

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
fun Char.toDecString(pads: Int = 0): String = toCode().toShort().toString(10).padStart(pads.maxOf(0), '0')

@FrameworkDsl
fun Int.toBinaryString(pads: Int = Int.SIZE_BITS): String = toString(2).padStart(pads.maxOf(0), '0')

@FrameworkDsl
fun Byte.toBinaryString(pads: Int = Byte.SIZE_BITS): String = toString(2).padStart(pads.maxOf(0), '0')

@FrameworkDsl
fun Long.toBinaryString(pads: Int = Long.SIZE_BITS): String = toString(2).padStart(pads.maxOf(0), '0')

@FrameworkDsl
fun Short.toBinaryString(pads: Int = Short.SIZE_BITS): String = toString(2).padStart(pads.maxOf(0), '0')

@FrameworkDsl
fun Char.toBinaryString(pads: Int = Char.SIZE_BITS): String = toCode().toShort().toString(2).padStart(pads.maxOf(0), '0')

@FrameworkDsl
fun Float.toBinaryString(pads: Int = Float.SIZE_BITS): String = toRawBits().toString(2).padStart(pads.maxOf(0), '0')

@FrameworkDsl
fun Double.toBinaryString(pads: Int = Double.SIZE_BITS): String = toRawBits().toString(2).padStart(pads.maxOf(0), '0')

@FrameworkDsl
fun AtomicLong.toBinaryString(pads: Int = Long.SIZE_BITS): String = getValue().toString(2).padStart(pads.maxOf(0), '0')

@FrameworkDsl
fun AtomicInteger.toBinaryString(pads: Int = Int.SIZE_BITS): String = getValue().toString(2).padStart(pads.maxOf(0), '0')

@FrameworkDsl
fun CharSequence.toBinaryStringList(pads: Int = Char.SIZE_BITS): List<String> {
    return map { c -> c.toBinaryString(pads) }
}

@FrameworkDsl
fun CharSequence.toBinaryString(pads: Int = Char.SIZE_BITS): String {
    return toBinaryStringList(pads).let { l ->
        stringOf {
            l.forEach { b ->
                add(b)
            }
        }
    }
}

@FrameworkDsl
inline fun Int.isZero(): Boolean = absOf() == 0

@FrameworkDsl
inline fun Long.isZero(): Boolean = absOf() == 0L

@FrameworkDsl
inline fun Long.isEmpty(): Boolean = absOf() == 0L

@FrameworkDsl
inline fun Long.isNotEmpty(): Boolean = absOf() != 0L

@FrameworkDsl
inline fun CharSequence.toLongOrElse(other: Long = 0L) = toTrimOr("0").toLongOrNull().otherwise(other)

@FrameworkDsl
inline fun CharSequence.toIntOrElse(other: Int = 0) = toTrimOr("0").toIntOrNull().otherwise(other)

@FrameworkDsl
inline fun Int.toDoubleArray(): DoubleArray = if (isLessSame(0)) EMPTY_REAL_ARRAY else DoubleArray(this)

@FrameworkDsl
inline fun Int.toDoubleArray(convert: Convert<Int, Double>): DoubleArray = DoubleArray(maxOf(0)) { index ->
    convert.convert(index)
}

@FrameworkDsl
fun DoubleArray.filled(value: Int): DoubleArray {
    return filled(value.realOf())
}

@FrameworkDsl
fun DoubleArray.filled(value: Long): DoubleArray {
    return filled(value.realOf())
}

@FrameworkDsl
fun DoubleArray.filled(value: Double): DoubleArray {
    if (isNotExhausted()) {
        when (sizeOf()) {
            1 -> this[0] = value
            else -> indices.forEach { this[it] = value }
        }
    }
    return this
}

@FrameworkDsl
inline fun DoubleArray.toDoubleArray(copy: Boolean = true): DoubleArray = if (copy.isTrue() && sizeOf() > 0) copyOf() else this
