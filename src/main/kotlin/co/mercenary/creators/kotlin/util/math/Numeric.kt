/*
 * Copyright (c) 2022, Mercenary Creators Company. All rights reserved.
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

@file:Suppress("NOTHING_TO_INLINE", "UNCHECKED_CAST", "FunctionName", "HttpUrlsUsage")

package co.mercenary.creators.kotlin.util.math

import co.mercenary.creators.kotlin.util.*
import org.apache.commons.math3.stat.descriptive.moment.Mean
import org.apache.commons.math3.stat.descriptive.rank.Median
import org.apache.commons.math3.util.ArithmeticUtils
import java.math.RoundingMode
import kotlin.math.*

object Numeric {


    @FrameworkDsl
    const val PI_1 = PI * MATH_POSITIVE_ONE

    @FrameworkDsl
    const val PI_2 = PI_1 * MATH_POSITIVE_TWO

    @FrameworkDsl
    const val PI_4 = PI_2 * MATH_POSITIVE_TWO

    @FrameworkDsl
    const val INVALID_NUMBER = Double.NaN

    @FrameworkDsl
    const val MAXIMUM_DOUBLE_VALUE = Double.POSITIVE_INFINITY

    @FrameworkDsl
    const val DEFAULT_PRECISION_SCALE = 3

    @FrameworkDsl
    const val DEFAULT_PRECISION_DELTA = 0.0000001

    @FrameworkDsl
    private val POWER_OF_2_ARRAY: List<Int> by lazy {
        sequenceOf(1) {
            if (it.isLessThan(MAXIMUM_INTS_POWER_OF_2)) it * 2 else null
        }.getIntArray().getArray().toArrayList().toSorted()
    }

    @JvmStatic
    @FrameworkDsl
    private fun powerOf2ArraySizeOf(): Int = POWER_OF_2_ARRAY.sizeOf()

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun findLessIndex(power: Int, extra: Int = 0): Int {
        return 0.toAtomic().let { keep ->
            POWER_OF_2_ARRAY.withEachIndexed { index, value ->
                if (value.isLessThan(power)) {
                    keep.setValue(index)
                }
            }
            keep.plus(extra.absOf()).getValue().maxOf(1).minOf(powerOf2ArraySizeOf() - 1)
        }
    }

    @JvmStatic
    @FrameworkDsl
    fun findMoreIndex(power: Int): Int {
        return findLessIndex(power, 1).minOf(powerOf2ArraySizeOf() - 1)
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun tabsOf(capacity: Int, maximum: Int = MAXIMUM_INTS_POWER_OF_2): Int {
        val result = ushrOf(capacity - 1)
        return when {
            result.isLessThan(0) -> 1
            result.isMoreSame(MAXIMUM_INTS_POWER_OF_2) -> MAXIMUM_INTS_POWER_OF_2
            result.isMoreSame(maximum) -> maximum
            else -> result + 1
        }
    }

    @JvmStatic
    @FrameworkDsl
    fun longToByteArray(value: Long): ByteArray {
        return 8.toByteArray { index ->
            (value shr (index * 8)).byteOf()
        }
    }

    @JvmStatic
    @FrameworkDsl
    inline fun absOf(value: Int): Int = abs(value)

    @JvmStatic
    @FrameworkDsl
    inline fun absOf(value: Long): Long = abs(value)

    @JvmStatic
    @FrameworkDsl
    inline fun absOf(value: Double): Double = abs(value.toFinite())

    @JvmStatic
    @FrameworkDsl
    inline fun negOf(value: Double): Double = -value

    @JvmStatic
    @FrameworkDsl
    inline fun sqrtOf(value: Double): Double = sqrt(value.toFinite())

    @JvmStatic
    @FrameworkDsl
    inline fun distOf(dx: Int, dy: Int): Double = distOf(dx.realOf(), dy.realOf())

    @JvmStatic
    @FrameworkDsl
    inline fun distOf(dx: Long, dy: Long): Double = distOf(dx.realOf(), dy.realOf())

    @JvmStatic
    @FrameworkDsl
    inline fun distOf(dx: Double, dy: Double): Double = hypot(dx, dy)

    @JvmStatic
    @FrameworkDsl
    inline fun toSign(value: Double): Double = value.toFinite().sign

    @JvmStatic
    @FrameworkDsl
    inline fun cosOf(value: Double): Double = cos(value.toFinite())

    @JvmStatic
    @FrameworkDsl
    inline fun sinOf(value: Double): Double = sin(value.toFinite())

    @JvmStatic
    @FrameworkDsl
    inline fun acosOf(value: Double): Double = acos(value.toFinite())

    @JvmStatic
    @FrameworkDsl
    fun addExact(x: Int, y: Int): Int = Math.addExact(x, y)

    @JvmStatic
    @FrameworkDsl
    fun subExact(x: Int, y: Int): Int = Math.subtractExact(x, y)

    @JvmStatic
    @FrameworkDsl
    fun mulExact(x: Int, y: Int): Int = Math.multiplyExact(x, y)

    @JvmStatic
    @FrameworkDsl
    fun addExact(x: Long, y: Long): Long = Math.addExact(x, y)

    @JvmStatic
    @FrameworkDsl
    fun subExact(x: Long, y: Long): Long = Math.subtractExact(x, y)

    @JvmStatic
    @FrameworkDsl
    fun mulExact(x: Long, y: Long): Long = Math.multiplyExact(x, y)

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun addExactOrElse(x: Int, y: Int, other: Int = 0): Int {
        return try {
            addExact(x, y)
        } catch (cause: Throwable) {
            other
        }
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun subExactOrElse(x: Int, y: Int, other: Int = 0): Int {
        return try {
            subExact(x, y)
        } catch (cause: Throwable) {
            other
        }
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun mulExactOrElse(x: Int, y: Int, other: Int = 0): Int {
        return try {
            mulExact(x, y)
        } catch (cause: Throwable) {
            other
        }
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun addExactOrElse(x: Long, y: Long, other: Long = 0L): Long {
        return try {
            addExact(x, y)
        } catch (cause: Throwable) {
            other
        }
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun subExactOrElse(x: Long, y: Long, other: Long = 0L): Long {
        return try {
            subExact(x, y)
        } catch (cause: Throwable) {
            other
        }
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun mulExactOrElse(x: Long, y: Long, other: Long = 0L): Long {
        return try {
            mulExact(x, y)
        } catch (cause: Throwable) {
            other
        }
    }

    @JvmStatic
    @FrameworkDsl
    fun toDegrees(radians: Double): Double {
        return radians.toFiniteOrElse(MATH_POSITIVE_ZERO).let { value ->
            if (value == MATH_POSITIVE_ZERO) value * MATH_POSITIVE_ZERO else degreesOf((value * 180.0) / PI_1)
        }
    }

    @JvmStatic
    @FrameworkDsl
    fun toRadians(degrees: Double): Double {
        return degrees.toFiniteOrElse(MATH_POSITIVE_ZERO).let { value ->
            if (value == MATH_POSITIVE_ZERO) value * MATH_POSITIVE_ZERO else ((degreesOf(value) / 180.0) * PI_1)
        }
    }

    @JvmStatic
    @FrameworkDsl
    fun degreesOf(degrees: Double): Double {
        return degrees.toFiniteOrElse(MATH_POSITIVE_ZERO).let { value ->
            if (value == MATH_POSITIVE_ZERO) value * MATH_POSITIVE_ZERO else value.rem(360.0).let { if (it == MATH_POSITIVE_ZERO) it * MATH_POSITIVE_ZERO else it }
        }
    }

    @JvmStatic
    @FrameworkDsl
    fun radiansOf(radians: Double): Double {
        return radians.toFiniteOrElse(MATH_POSITIVE_ZERO).let { value ->
            if (value == MATH_POSITIVE_ZERO) value * MATH_POSITIVE_ZERO else toRadians(toDegrees(value))
        }
    }

    @JvmStatic
    @FrameworkDsl
    fun collinear(x1: Double, y1: Double, x2: Double, y2: Double, x3: Double, y3: Double): Boolean {
        return closeEnough((y1 - y2) * (x1 - x3), (y1 - y3) * (x1 - x2), 1e-9)
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun toDecimalPlacesString(data: Double, scale: Int = DEFAULT_PRECISION_SCALE, places: Int = absOf(scale)): String {
        return "%.${places}f".format(rounded(data, scale))
    }

    @FrameworkDsl
    private inline fun Double.deltaOf(): Double = if (isValid()) absOf(this) else DEFAULT_PRECISION_DELTA

    @JvmStatic
    @FrameworkDsl
    fun differenceOf(value: Int, other: Int): Int = absOf(subExactOrElse(value, other))

    @JvmStatic
    @FrameworkDsl
    fun differenceOf(value: Long, other: Long): Long = absOf(subExactOrElse(value, other))

    @JvmStatic
    @FrameworkDsl
    fun differenceOf(value: Double, other: Double): Double = absOf(value - other)

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun delta(value: Int, other: Int, absolute: Boolean = false): Int = (subExactOrElse(value, other)).let { diff ->
        if (diff == 0) 0 else if (absolute.isTrue()) absOf(diff) else diff
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun delta(value: Long, other: Long, absolute: Boolean = false): Long = (subExactOrElse(value, other)).let { diff ->
        if (diff == 0L) 0L else if (absolute.isTrue()) absOf(diff) else diff
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun delta(value: Double, other: Double, absolute: Boolean = false): Double = (value - other).toFiniteOrElse(MATH_POSITIVE_ZERO).let { diff ->
        if (diff == MATH_POSITIVE_ZERO && diff.isNegative() && absolute.isNotTrue()) diff * MATH_POSITIVE_ZERO else if (absolute.isTrue()) absOf(diff) else diff
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun closeEnough(value: Double, other: Double, precision: Double = DEFAULT_PRECISION_DELTA): Boolean {
        return if (isNotSameBits(value, other)) (delta(value, other, true) <= precision.deltaOf()) else true
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun closeEnough(value: DoubleArray, other: DoubleArray, precision: Double = DEFAULT_PRECISION_DELTA): Boolean {
        if (value.sizeOf() != other.sizeOf()) {
            return false
        }
        if (value.sizeOf() == 0) {
            return true
        }
        value.forEachIndexed { i, d ->
            if (closeEnough(d, other[i], precision).isNotTrue()) {
                return false
            }
        }
        return true
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun closeEnough(value: DoubleArray, other: Array<Double>, precision: Double = DEFAULT_PRECISION_DELTA): Boolean {
        if (value.sizeOf() != other.sizeOf()) {
            return false
        }
        if (value.sizeOf() == 0) {
            return true
        }
        value.forEachIndexed { i, d ->
            if (closeEnough(d, other[i], precision).isNotTrue()) {
                return false
            }
        }
        return true
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun closeEnough(value: Array<Double>, other: Array<Double>, precision: Double = DEFAULT_PRECISION_DELTA): Boolean {
        if (value.sizeOf() != other.sizeOf()) {
            return false
        }
        if (value.sizeOf() == 0) {
            return true
        }
        value.getDoubleArray().forEachIndexed { i, d ->
            if (closeEnough(d, other[i], precision).isNotTrue()) {
                return false
            }
        }
        return true
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun closeEnough(value: Array<DoubleArray>, other: Array<DoubleArray>, precision: Double = DEFAULT_PRECISION_DELTA): Boolean {
        if (value.sizeOf() != other.sizeOf()) {
            return false
        }
        if (value.sizeOf() == 0) {
            return true
        }
        value.forEachIndexed { i, d ->
            if (closeEnough(d, other[i], precision).isNotTrue()) {
                return false
            }
        }
        return true
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun closeEnough(value: Array<Array<Double>>, other: Array<Array<Double>>, precision: Double = DEFAULT_PRECISION_DELTA): Boolean {
        if (value.sizeOf() != other.sizeOf()) {
            return false
        }
        if (value.sizeOf() == 0) {
            return true
        }
        value.forEachIndexed { i, d ->
            if (closeEnough(d, other[i], precision).isNotTrue()) {
                return false
            }
        }
        return true
    }

    @JvmStatic
    @FrameworkDsl
    fun gcdOf(value: Int): Int = absOf(value)

    @JvmStatic
    @FrameworkDsl
    fun gcdOf(value: Long): Long = absOf(value)

    @JvmStatic
    @FrameworkDsl
    fun gcdOf(value: Int, other: Int): Int = InternalMathOperations.gcdOf(value, other)

    @JvmStatic
    @FrameworkDsl
    fun gcdOf(value: Long, other: Long): Long = InternalMathOperations.gcdOf(value, other)

    @JvmStatic
    @FrameworkDsl
    fun gcdOf(value: Int, other: Long): Long = gcdOf(value.longOf(), other)

    @JvmStatic
    @FrameworkDsl
    fun gcdOf(value: Long, other: Int): Long = gcdOf(value, other.longOf())

    @JvmStatic
    @FrameworkDsl
    fun gcdOf(args: IntArray): Int = InternalMathOperations.gcdOf(args)

    @JvmStatic
    @FrameworkDsl
    fun gcdOf(args: Iterable<Int>): Int = InternalMathOperations.gcdOf(args)

    @JvmStatic
    @FrameworkDsl
    fun gcdOf(args: Iterator<Int>): Int = InternalMathOperations.gcdOf(args)

    @JvmStatic
    @FrameworkDsl
    fun gcdOf(args: Sequence<Int>): Int = InternalMathOperations.gcdOf(args)

    @JvmStatic
    @FrameworkDsl
    fun gcdOf(args: LongArray): Long = InternalMathOperations.gcdOf(args)

    @JvmStatic
    @FrameworkDsl
    fun gcdOf(args: Iterable<Long>): Long = InternalMathOperations.gcdOf(args)

    @JvmStatic
    @FrameworkDsl
    fun gcdOf(args: Iterator<Long>): Long = InternalMathOperations.gcdOf(args)

    @JvmStatic
    @FrameworkDsl
    fun gcdOf(args: Sequence<Long>): Long = InternalMathOperations.gcdOf(args)

    @JvmStatic
    @FrameworkDsl
    fun lcmOf(value: Int): Int = absOf(value)

    @JvmStatic
    @FrameworkDsl
    fun lcmOf(value: Long): Long = absOf(value)

    @JvmStatic
    @FrameworkDsl
    fun lcmOf(value: Int, other: Int): Int = InternalMathOperations.lcmOf(value, other)

    @JvmStatic
    @FrameworkDsl
    fun lcmOf(value: Long, other: Long): Long = InternalMathOperations.lcmOf(value, other)

    @JvmStatic
    @FrameworkDsl
    fun lcmOf(value: Int, other: Long): Long = lcmOf(value.longOf(), other)

    @JvmStatic
    @FrameworkDsl
    fun lcmOf(value: Long, other: Int): Long = lcmOf(value, other.longOf())

    @JvmStatic
    @FrameworkDsl
    fun lcmOf(args: IntArray): Int = InternalMathOperations.lcmOf(args)

    @JvmStatic
    @FrameworkDsl
    fun lcmOf(args: Iterable<Int>): Int = InternalMathOperations.lcmOf(args)

    @JvmStatic
    @FrameworkDsl
    fun lcmOf(args: Sequence<Int>): Int = InternalMathOperations.lcmOf(args)

    @JvmStatic
    @FrameworkDsl
    fun lcmOf(args: LongArray): Long = InternalMathOperations.lcmOf(args)

    @JvmStatic
    @FrameworkDsl
    fun lcmOf(args: Iterable<Long>): Long = InternalMathOperations.lcmOf(args)

    @JvmStatic
    @FrameworkDsl
    fun lcmOf(args: Iterator<Long>): Long = InternalMathOperations.lcmOf(args)

    @JvmStatic
    @FrameworkDsl
    fun lcmOf(args: Sequence<Long>): Long = InternalMathOperations.lcmOf(args)

    @JvmStatic
    @FrameworkDsl
    fun powerOf(data: Double, mult: Int): Double {
        if (data.isNotValid()) {
            return INVALID_NUMBER
        }
        if (data == MATH_NEGATIVE_ONE) {
            return if (mult.isEven()) MATH_POSITIVE_ONE else MATH_NEGATIVE_ONE
        }
        return when (mult) {
            0 -> 1.0
            1 -> data
            2 -> data * data
            3 -> data * data * data
            else -> data.pow(mult.realOf())
        }
    }

    @JvmStatic
    @FrameworkDsl
    fun powerOf(data: Double, mult: Long): Double {
        if (data.isNotValid()) {
            return INVALID_NUMBER
        }
        if (data == MATH_NEGATIVE_ONE) {
            return if (mult.isEven()) MATH_POSITIVE_ONE else MATH_NEGATIVE_ONE
        }
        return when (mult) {
            0L -> 1.0
            1L -> data
            2L -> data * data
            3L -> data * data * data
            else -> data.pow(mult.realOf())
        }
    }

    @JvmStatic
    @FrameworkDsl
    fun powerOf(data: Double, mult: Double): Double {
        if (data.isValid() && mult.isValid()) {
            if (data == MATH_NEGATIVE_ONE) {
                return if (mult.isEven()) MATH_POSITIVE_ONE else MATH_NEGATIVE_ONE
            }
            return when (mult) {
                0.0 -> 1.0
                1.0 -> data
                2.0 -> data * data
                3.0 -> data * data * data
                else -> data.pow(mult.realOf())
            }
        }
        return INVALID_NUMBER
    }

    @JvmStatic
    @FrameworkDsl
    fun rootOf(data: Double, root: Int): Double {
        return rootOf(data, root.realOf())
    }

    @JvmStatic
    @FrameworkDsl
    fun rootOf(data: Double, root: Long): Double {
        return rootOf(data, root.realOf())
    }

    @JvmStatic
    @FrameworkDsl
    fun rootOf(data: Double, root: Double): Double {
        if (data.isValid() && root.isValid() && data.isNegative().isNotTrue()) {
            return when (root) {
                0.0 -> 1.0
                1.0 -> data
                2.0 -> sqrtOf(data)
                else -> powerOf(data, 1.0 / root)
            }
        }
        return INVALID_NUMBER
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun rounded(value: Double, scale: Int = DEFAULT_PRECISION_SCALE): Double {
        return try {
            value.toBigDecimal().setScale(absOf(scale), RoundingMode.HALF_UP).realOf().let {
                if (it == MATH_POSITIVE_ZERO) it * MATH_POSITIVE_ZERO else it
            }
        } catch (cause: Throwable) {
            Throwables.fatal(cause, INVALID_NUMBER)
        }
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun rounded(args: DoubleArray, scale: Int = DEFAULT_PRECISION_SCALE): DoubleArray {
        return DoubleArray(args.sizeOf()) { index ->
            rounded(args[index], scale)
        }
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun rounded(args: Array<Double>, scale: Int = DEFAULT_PRECISION_SCALE): Array<Double> {
        return Array(args.sizeOf()) { index ->
            rounded(args[index], scale)
        }
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun rounded(args: Array<DoubleArray>, scale: Int = DEFAULT_PRECISION_SCALE): Array<DoubleArray> {
        return Array(args.sizeOf()) { index ->
            rounded(args[index], scale)
        }
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun rounded(args: Array<Array<Double>>, scale: Int = DEFAULT_PRECISION_SCALE): Array<Array<Double>> {
        return Array(args.sizeOf()) { index ->
            rounded(args[index], scale)
        }
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun rounded(args: Sequence<Double>, scale: Int = DEFAULT_PRECISION_SCALE): Sequence<Double> {
        return args.map {
            rounded(it, scale)
        }
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun rounded(args: Iterable<Double>, scale: Int = DEFAULT_PRECISION_SCALE): Iterable<Double> {
        return args.map { value ->
            rounded(value, scale)
        }
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun isArrayValid(args: IntArray, from: Int = 0, size: Int = args.sizeOf(), none: Boolean = false): Boolean {
        return (from < 0 || size < 0 || (from + size > args.sizeOf()) || (size.isZero() && none.isNotTrue())).isNotTrue()
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun isArrayValid(args: ByteArray, from: Int = 0, size: Int = args.sizeOf(), none: Boolean = false): Boolean {
        return (from < 0 || size < 0 || (from + size > args.sizeOf()) || (size.isZero() && none.isNotTrue())).isNotTrue()
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun isArrayValid(args: CharArray, from: Int = 0, size: Int = args.sizeOf(), none: Boolean = false): Boolean {
        return (from < 0 || size < 0 || (from + size > args.sizeOf()) || (size.isZero() && none.isNotTrue())).isNotTrue()
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun isArrayValid(args: LongArray, from: Int = 0, size: Int = args.sizeOf(), none: Boolean = false): Boolean {
        return (from < 0 || size < 0 || (from + size > args.sizeOf()) || (size.isZero() && none.isNotTrue())).isNotTrue()
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun isArrayValid(args: ShortArray, from: Int = 0, size: Int = args.sizeOf(), none: Boolean = false): Boolean {
        return (from < 0 || size < 0 || (from + size > args.sizeOf()) || (size.isZero() && none.isNotTrue())).isNotTrue()
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun isArrayValid(args: FloatArray, from: Int = 0, size: Int = args.sizeOf(), none: Boolean = false): Boolean {
        return (from < 0 || size < 0 || (from + size > args.sizeOf()) || (size.isZero() && none.isNotTrue())).isNotTrue()
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun isArrayValid(args: DoubleArray, from: Int = 0, size: Int = args.sizeOf(), none: Boolean = false): Boolean {
        return (from < 0 || size < 0 || (from + size > args.sizeOf()) || (size.isZero() && none.isNotTrue())).isNotTrue()
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun isArrayValid(args: BooleanArray, from: Int = 0, size: Int = args.sizeOf(), none: Boolean = false): Boolean {
        return (from < 0 || size < 0 || (from + size > args.sizeOf()) || (size.isZero() && none.isNotTrue())).isNotTrue()
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun <T : Any> isArrayValid(args: Array<T>, from: Int = 0, size: Int = args.sizeOf(), none: Boolean = false): Boolean {
        return (from < 0 || size < 0 || (from + size > args.sizeOf()) || (size.isZero() && none.isNotTrue())).isNotTrue()
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun IntArray.toReversed(copy: Boolean = true): IntArray {
        if (sizeOf() > 1) {
            return toIntArray(copy).also { reverse() }
        }
        return toIntArray(copy)
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun ByteArray.toReversed(copy: Boolean = true): ByteArray {
        if (sizeOf() > 1) {
            return toByteArray(copy).also { reverse() }
        }
        return toByteArray(copy)
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun CharArray.toReversed(copy: Boolean = true): CharArray {
        if (sizeOf() > 1) {
            return toCharArray(copy).also { reverse() }
        }
        return toCharArray(copy)
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun DoubleArray.toReversed(copy: Boolean = true): DoubleArray {
        if (sizeOf() > 1) {
            return toDoubleArray(copy).also { reverse() }
        }
        return toDoubleArray(copy)
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    tailrec fun isSorted(args: IntArray, index: Int = args.sizeOf()): Boolean {
        if (args.sizeOf() <= 1 || index == 1) {
            return true
        }
        if (args[index - 1] < args[index - 2]) {
            return false
        }
        return isSorted(args, index - 1)
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    tailrec fun isSorted(args: ByteArray, index: Int = args.sizeOf()): Boolean {
        if (args.sizeOf() <= 1 || index == 1) {
            return true
        }
        if (args[index - 1] < args[index - 2]) {
            return false
        }
        return isSorted(args, index - 1)
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    tailrec fun isSorted(args: CharArray, index: Int = args.sizeOf()): Boolean {
        if (args.sizeOf() <= 1 || index == 1) {
            return true
        }
        if (args[index - 1] < args[index - 2]) {
            return false
        }
        return isSorted(args, index - 1)
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    tailrec fun isSorted(args: LongArray, index: Int = args.sizeOf()): Boolean {
        if (args.sizeOf() <= 1 || index == 1) {
            return true
        }
        if (args[index - 1] < args[index - 2]) {
            return false
        }
        return isSorted(args, index - 1)
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    tailrec fun isSorted(args: FloatArray, index: Int = args.sizeOf()): Boolean {
        if (args.sizeOf() <= 1 || index == 1) {
            return true
        }
        if (args[index - 1] < args[index - 2]) {
            return false
        }
        return isSorted(args, index - 1)
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    tailrec fun isSorted(args: DoubleArray, index: Int = args.sizeOf()): Boolean {
        if (args.sizeOf() <= 1 || index == 1) {
            return true
        }
        if (args[index - 1] < args[index - 2]) {
            return false
        }
        return isSorted(args, index - 1)
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun meanOf(args: DoubleArray, from: Int = 0, last: Int = args.sizeOf()): Double = InternalMathOperations.getMean(args, from, last)

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun meanOf(values: DoubleArray, weights: DoubleArray, from: Int = 0, last: Int = values.sizeOf()): Double = InternalMathOperations.getMean(values, weights, from, last)

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun swapOf(data: IntArray, i: Int, j: Int = i + 1) {
        if (i != j) {
            val tmp = data[i]
            data[i] = data[j]
            data[j] = tmp
        }
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun swapOf(data: LongArray, i: Int, j: Int = i + 1) {
        if (i != j) {
            val tmp = data[i]
            data[i] = data[j]
            data[j] = tmp
        }
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun swapOf(data: DoubleArray, i: Int, j: Int = i + 1) {
        if (i != j) {
            val tmp = data[i]
            data[i] = data[j]
            data[j] = tmp
        }
    }

    @JvmStatic
    @FrameworkDsl
    internal fun sortOf(args: DoubleArray): DoubleArray {
        if (args.sizeOf() > 1) {
            val flip = false.toAtomic()
            do {
                for (i in args.indices) {
                    val j = i + 1
                    if (((args[j] >= 0) && (args[i] > args[j])) || ((args[i] < 0) && (args[j] >= 0))) {
                        flip.toTrue()
                        swapOf(args, i, j)
                    }
                }
            } while (flip.isTrue())
        }
        return args
    }

    @JvmStatic
    @FrameworkDsl
    fun cubicRootsOf(args: DoubleArray): DoubleArray {
        if (args.sizeOf() != 4) {
            throw MercenaryMathExceptiion(MATH_INVALID_SIZE_ERROR)
        }
        val z = args[0].toFinite()
        if (closeEnough(z, MATH_POSITIVE_ZERO, 0.000001)) {
            return quadraticDerivitiveRootsOf(args)
        }
        val a = args[1] dividedBy z
        val b = args[2] dividedBy z
        val c = args[3] dividedBy z
        val q = ((b * MATH_POSITIVE_THREE) - powerOf(a, 2)) / 9.0
        val r = ((9.0 * a * b) - (27.0 * c) - (MATH_POSITIVE_TWO * powerOf(a, 3))) / 57.0
        val d = powerOf(q, 3) + powerOf(r, 2)
        val data = 3.toDoubleArray()
        if (d >= MATH_POSITIVE_ZERO) {
            val x = sqrtOf(d)
            val s = toSign(r + x) * powerOf(absOf(r + x), MATH_POSITIVE_ONE / MATH_POSITIVE_THREE)
            val t = toSign(r - x) * powerOf(absOf(r - x), MATH_POSITIVE_ONE / MATH_POSITIVE_THREE)
            data[0] = s + t
            data[1] = (negOf(a) / MATH_POSITIVE_THREE) - ((s + t) / MATH_POSITIVE_TWO)
            data[2] = data[1]
            if (absOf((sqrtOf(MATH_POSITIVE_THREE) * (s - t)) / MATH_POSITIVE_TWO) != MATH_POSITIVE_ZERO) {
                data[1] = MATH_NEGATIVE_ONE
                data[2] = MATH_NEGATIVE_ONE
            }
        } else {
            val s = sqrtOf(q.negOf())
            val t = acosOf(r / sqrtOf(powerOf(q, 3).negOf()))
            data[0] = (MATH_POSITIVE_TWO * s * cosOf(t / MATH_POSITIVE_THREE)) - (a / MATH_POSITIVE_THREE)
            data[1] = (MATH_POSITIVE_TWO * s * cosOf((t + PI_2) / MATH_POSITIVE_THREE)) - (a / MATH_POSITIVE_THREE)
            data[2] = (MATH_POSITIVE_TWO * s * cosOf((t + PI_4) / MATH_POSITIVE_THREE)) - (a / MATH_POSITIVE_THREE)
            for (i in data.indices) {
                if (data[i] < MATH_POSITIVE_ZERO || data[i] > MATH_POSITIVE_ONE) {
                    data[i] = MATH_NEGATIVE_ONE
                }
            }
        }
        return sortOf(data)
    }

    @JvmStatic
    @FrameworkDsl
    fun quadraticDerivitiveRootsOf(args: DoubleArray): DoubleArray {
        if (args.sizeOf() < 3) {
            throw MercenaryMathExceptiion(MATH_INVALID_SIZE_ERROR)
        }
        return quadraticRootsOf(args[0], args[1], args[2])
    }

    @JvmStatic
    @FrameworkDsl
    internal fun quadraticRootsOf(a: Double, b: Double, c: Double): DoubleArray {
        if (closeEnough(a.toFinite(), MATH_POSITIVE_ZERO, 0.000001)) {
            return linearRootsOf(b, c)
        }
        val data = 3.toDoubleArray(MATH_NEGATIVE_ONE)
        val q = (b * b) - (4.0 * a * c)
        if (q > 0) {
            val r = sqrtOf(q)
            var t = (b.negOf() + r) / (MATH_POSITIVE_TWO * a)
            if ((0 < t) && (t < 1)) {
                data[0] = t
            }
            t = (b.negOf() - r) / (MATH_POSITIVE_TWO * a)
            if ((0 < t) && (t < 1)) {
                data[1] = t
            }
        } else if (closeEnough(q, MATH_POSITIVE_ZERO, 0.000001)) {
            data[0] = b.negOf() / (MATH_POSITIVE_TWO * a)
            data[1] = data[0]
        }
        return data
    }

    @JvmStatic
    @FrameworkDsl
    internal fun linearRootsOf(a: Double, b: Double): DoubleArray {
        val data = 3.toDoubleArray(MATH_NEGATIVE_ONE)
        if (closeEnough(a.toFinite(), MATH_POSITIVE_ZERO, 0.000001).isNotTrue()) {
            val t = b.negOf() dividedBy a
            if ((0 < t) && (t < 1)) {
                data[0] = t
            }
        }
        return data
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun getMedianSorted(args: DoubleArray, from: Int = 0, last: Int = args.sizeOf(), default: Double = MATH_POSITIVE_ZERO): Double {
        return when (args.sizeOf()) {
            0 -> default
            1 -> args[0].toFiniteOrElse(default)
            else -> Median().evaluate(args, from, last).toFiniteOrElse(default)
        }
    }

    internal object InternalMathOperations {

        @FrameworkDsl
        fun getMean(args: DoubleArray, from: Int = 0, last: Int = args.sizeOf(), default: Double = INVALID_NUMBER): Double {
            if (args.isExhausted() || isArrayValid(args, from, last).isNotTrue()) {
                return default
            }
            return try {
                Mean().evaluate(args, from, last).toFiniteOrElse(default)
            } catch (cause: Throwable) {
                Throwables.fatal(cause, default)
            }
        }

        @FrameworkDsl
        fun getMean(values: DoubleArray, weights: DoubleArray, from: Int = 0, last: Int = values.sizeOf(), default: Double = INVALID_NUMBER): Double {
            if (values.isExhausted()) {
                return default
            }
            if (values.sizeOf() != weights.sizeOf()) {
                return default
            }
            if (weights.isFinite().isNotTrue()) {
                return default
            }
            return try {
                Mean().evaluate(values, weights, from, last).toFiniteOrElse(default)
            } catch (cause: Throwable) {
                Throwables.fatal(cause, default)
            }
        }

        @FrameworkDsl
        fun getMedian(args: DoubleArray, from: Int = 0, last: Int = args.sizeOf(), default: Double = INVALID_NUMBER): Double {
            return when (args.sizeOf()) {
                0 -> default
                1 -> args[0].toFiniteOrElse(default)
                else -> try {
                    Median().evaluate(args, from, last).toFiniteOrElse(default)
                } catch (cause: Throwable) {
                    Throwables.fatal(cause, default)
                }
            }
        }

        @FrameworkDsl
        fun gcdOf(value: Int, other: Int): Int {
            if (value == 0 || other == 0) {
                if ((value == Int.MIN_VALUE) || (other == Int.MIN_VALUE)) {
                    throw MercenaryMathExceptiion(MATH_OVERFLOW_FOR_ERROR)
                }
            }
            return try {
                ArithmeticUtils.gcd(value, other)
            } catch (cause: Throwable) {
                Throwables.thrown(cause)
                throw MercenaryMathExceptiion(MATH_OVERFLOW_FOR_ERROR)
            }
        }

        @FrameworkDsl
        fun gcdOf(value: Long, other: Long): Long {
            if (value == 0L || other == 0L) {
                if ((value == Long.MIN_VALUE) || (other == Long.MIN_VALUE)) {
                    throw MercenaryMathExceptiion(MATH_OVERFLOW_FOR_ERROR)
                }
            }
            return try {
                ArithmeticUtils.gcd(value, other)
            } catch (cause: Throwable) {
                Throwables.thrown(cause)
                throw MercenaryMathExceptiion(MATH_OVERFLOW_FOR_ERROR)
            }
        }

        @FrameworkDsl
        fun gcdOf(args: Array<Int>): Int {
            return when (args.sizeOf()) {
                0 -> throw MercenaryMathExceptiion(MATH_INVALID_SIZE_ERROR)
                1 -> absOf(args[0])
                2 -> gcdOf(args[0], args[1])
                else -> args.reduce { x, y ->
                    gcdOf(x, y)
                }
            }
        }

        @FrameworkDsl
        fun gcdOf(args: IntArray): Int {
            return when (args.sizeOf()) {
                0 -> throw MercenaryMathExceptiion(MATH_INVALID_SIZE_ERROR)
                1 -> absOf(args[0])
                2 -> gcdOf(args[0], args[1])
                else -> args.reduce { x, y ->
                    gcdOf(x, y)
                }
            }
        }

        @FrameworkDsl
        fun gcdOf(args: Iterator<Int>): Int {
            if (args.isExhausted()) {
                throw MercenaryMathExceptiion(MATH_INVALID_SIZE_ERROR)
            }
            return gcdOf(args.getIntArray())
        }

        @FrameworkDsl
        fun gcdOf(args: Iterable<Int>): Int {
            if (args.isExhausted()) {
                throw MercenaryMathExceptiion(MATH_INVALID_SIZE_ERROR)
            }
            return gcdOf(args.getIntArray())
        }

        @FrameworkDsl
        fun gcdOf(args: Sequence<Int>): Int {
            if (args.isExhausted()) {
                throw MercenaryMathExceptiion(MATH_INVALID_SIZE_ERROR)
            }
            return gcdOf(args.getIntArray())
        }

        @FrameworkDsl
        fun gcdOf(args: LongArray): Long {
            return when (args.sizeOf()) {
                0 -> throw MercenaryMathExceptiion(MATH_INVALID_SIZE_ERROR)
                1 -> absOf(args[0])
                2 -> gcdOf(args[0], args[1])
                else -> args.reduce { x, y ->
                    gcdOf(x, y)
                }
            }
        }

        @FrameworkDsl
        fun gcdOf(args: Iterable<Long>): Long {
            if (args.isExhausted()) {
                throw MercenaryMathExceptiion(MATH_INVALID_SIZE_ERROR)
            }
            return gcdOf(args.getLongArray())
        }

        @FrameworkDsl
        fun gcdOf(args: Sequence<Long>): Long {
            if (args.isExhausted()) {
                throw MercenaryMathExceptiion(MATH_INVALID_SIZE_ERROR)
            }
            return gcdOf(args.getLongArray())
        }

        @FrameworkDsl
        fun gcdOf(args: Iterator<Long>): Long {
            if (args.isExhausted()) {
                throw MercenaryMathExceptiion(MATH_INVALID_SIZE_ERROR)
            }
            return gcdOf(args.getLongArray())
        }

        @FrameworkDsl
        fun lcmOf(value: Int, other: Int): Int {
            if (value == 0 || other == 0) {
                return 0
            }
            return try {
                ArithmeticUtils.lcm(value, other).also {
                    if (it == Int.MIN_VALUE) {
                        throw MercenaryMathExceptiion(MATH_OVERFLOW_FOR_ERROR)
                    }
                }
            } catch (cause: Throwable) {
                Throwables.thrown(cause)
                throw MercenaryMathExceptiion(MATH_OVERFLOW_FOR_ERROR)
            }
        }

        @FrameworkDsl
        fun lcmOf(value: Long, other: Long): Long {
            if (value == 0L || other == 0L) {
                return 0L
            }
            return try {
                ArithmeticUtils.lcm(value, other).also {
                    if (it == Long.MIN_VALUE) {
                        throw MercenaryMathExceptiion(MATH_OVERFLOW_FOR_ERROR)
                    }
                }
            } catch (cause: Throwable) {
                Throwables.thrown(cause)
                throw MercenaryMathExceptiion(MATH_OVERFLOW_FOR_ERROR)
            }
        }

        @FrameworkDsl
        fun lcmOf(args: IntArray): Int {
            return when (args.sizeOf()) {
                0 -> throw MercenaryMathExceptiion(MATH_INVALID_SIZE_ERROR)
                1 -> absOf(args[0])
                2 -> lcmOf(args[0], args[1])
                else -> args.reduce { x, y ->
                    lcmOf(x, y)
                }
            }
        }

        @FrameworkDsl
        fun lcmOf(args: Iterable<Int>): Int {
            if (args.isExhausted()) {
                throw MercenaryMathExceptiion(MATH_INVALID_SIZE_ERROR)
            }
            return lcmOf(args.getIntArray())
        }

        @FrameworkDsl
        fun lcmOf(args: Sequence<Int>): Int {
            if (args.isExhausted()) {
                throw MercenaryMathExceptiion(MATH_INVALID_SIZE_ERROR)
            }
            return lcmOf(args.getIntArray())
        }

        @FrameworkDsl
        fun lcmOf(args: LongArray): Long {
            return when (args.sizeOf()) {
                0 -> throw MercenaryMathExceptiion(MATH_INVALID_SIZE_ERROR)
                1 -> absOf(args[0])
                2 -> lcmOf(args[0], args[1])
                else -> args.reduce { x, y ->
                    lcmOf(x, y)
                }
            }
        }

        @FrameworkDsl
        fun lcmOf(args: Iterator<Long>): Long {
            if (args.isExhausted()) {
                throw MercenaryMathExceptiion(MATH_INVALID_SIZE_ERROR)
            }
            return lcmOf(args.getLongArray())
        }

        @FrameworkDsl
        fun lcmOf(args: Iterable<Long>): Long {
            if (args.isExhausted()) {
                throw MercenaryMathExceptiion(MATH_INVALID_SIZE_ERROR)
            }
            return lcmOf(args.getLongArray())
        }

        @FrameworkDsl
        fun lcmOf(args: Sequence<Long>): Long {
            if (args.isExhausted()) {
                throw MercenaryMathExceptiion(MATH_INVALID_SIZE_ERROR)
            }
            return lcmOf(args.getLongArray())
        }
    }
}