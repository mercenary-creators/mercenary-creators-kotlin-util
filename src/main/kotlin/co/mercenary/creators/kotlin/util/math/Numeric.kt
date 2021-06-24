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

@file:Suppress("NOTHING_TO_INLINE")

package co.mercenary.creators.kotlin.util.math

import co.mercenary.creators.kotlin.util.*
import java.math.RoundingMode
import kotlin.math.*

object Numeric {

    @FrameworkDsl
    const val PI_2 = PI * 2.0

    @FrameworkDsl
    const val PI_4 = PI * 4.0

    @FrameworkDsl
    const val INVALID_NUMBER = Double.NaN

    @FrameworkDsl
    const val DEFAULT_PRECISION_SCALE = 3

    @FrameworkDsl
    const val DEFAULT_PRECISION_DELTA = 0.0000001

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun tabsOf(args: Int, most: Int = MAXIMUM_INTS_POWER_OF_2): Int {
        val tabs = ushrOf(args - 1)
        return when {
            tabs.isLessThan(0) -> 1
            tabs.isMoreSame(most) -> most
            else -> tabs + 1
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
    inline fun absOf(value: Int): Int = if (value < 0) -value else value

    @JvmStatic
    @FrameworkDsl
    inline fun absOf(value: Long): Long = if (value < 0L) -value else value

    @JvmStatic
    @FrameworkDsl
    inline fun absOf(value: Double): Double = if (value.toFinite() <= 0.0) 0.0 - value else value

    @JvmStatic
    @FrameworkDsl
    inline fun negOf(value: Double): Double = -value

    @JvmStatic
    @FrameworkDsl
    inline fun sqrtOf(value: Double): Double = sqrt(value.toFinite())

    @JvmStatic
    @FrameworkDsl
    inline fun distOf(dx: Int, dy: Int): Double = hypot(dx.realOf(), dy.realOf())

    @JvmStatic
    @FrameworkDsl
    inline fun distOf(dx: Long, dy: Long): Double = hypot(dx.realOf(), dy.realOf())

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
        return radians.toFiniteOrElse(0.0).let { value ->
            if (value == 0.0) 0.0 else degreesOf((value * 180.0) / PI)
        }
    }

    @JvmStatic
    @FrameworkDsl
    fun toRadians(degrees: Double): Double {
        return degrees.toFiniteOrElse(0.0).let { value ->
            if (value == 0.0) 0.0 else ((degreesOf(value) / 180.0) * PI)
        }
    }

    @JvmStatic
    @FrameworkDsl
    fun degreesOf(degrees: Double): Double {
        return degrees.toFiniteOrElse(0.0).let { value ->
            if (value == 0.0) 0.0 else value.rem(360.0).let { if (it == 0.0) 0.0 else it }
        }
    }

    @JvmStatic
    @FrameworkDsl
    fun radiansOf(radians: Double): Double {
        return radians.toFiniteOrElse(0.0).let { value ->
            if (value == 0.0) 0.0 else toRadians(toDegrees(value))
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
    fun isSameBits(value: Double, other: Double): Boolean = (value.toBits() == other.toBits())

    @JvmStatic
    @FrameworkDsl
    inline fun isNotSameBits(value: Double, other: Double): Boolean = (value.toBits() != other.toBits())

    @JvmStatic
    @FrameworkDsl
    inline fun diffOf(value: Double, other: Double): Double = absOf(value - other)

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun closeEnough(value: Double, other: Double, precision: Double = DEFAULT_PRECISION_DELTA): Boolean {
        return if (isNotSameBits(value, other)) (diffOf(value, other) <= precision.deltaOf()) else true
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun closeEnough(value: DoubleArray, other: DoubleArray, precision: Double = DEFAULT_PRECISION_DELTA): Boolean {
        if (value.sizeOf() != other.sizeOf()) {
            return false
        }
        val delta = precision.deltaOf()
        for (i in value.indices) {
            val v = value[i]
            val o = other[i]
            if (isNotSameBits(v, o)) {
                if (diffOf(v, o) > delta) {
                    return false
                }
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
        return closeEnough(value, other.getDoubleArray(), precision)
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun closeEnough(value: Array<Double>, other: Array<Double>, precision: Double = DEFAULT_PRECISION_DELTA): Boolean {
        if (value.sizeOf() != other.sizeOf()) {
            return false
        }
        return closeEnough(value.getDoubleArray(), other.getDoubleArray(), precision)
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun closeEnough(value: Array<DoubleArray>, other: Array<DoubleArray>, precision: Double = DEFAULT_PRECISION_DELTA): Boolean {
        if (value.sizeOf() != other.sizeOf()) {
            return false
        }
        for (i in value.indices) {
            if (closeEnough(value[i], other[i], precision).isNotTrue()) {
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
        for (i in value.indices) {
            if (closeEnough(value[i], other[i], precision).isNotTrue()) {
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
    fun gcdOf(value: Int, other: Int): Int = TailRecursiveFunctions.gcdOf(value, other)

    @JvmStatic
    @FrameworkDsl
    fun gcdOf(value: Long, other: Long): Long = TailRecursiveFunctions.gcdOf(value, other)

    @JvmStatic
    @FrameworkDsl
    fun gcdOf(value: Int, other: Long): Long = gcdOf(value.longOf(), other)

    @JvmStatic
    @FrameworkDsl
    fun gcdOf(value: Long, other: Int): Long = gcdOf(value, other.longOf())

    @JvmStatic
    @FrameworkDsl
    fun gcdOf(args: IntArray): Int = TailRecursiveFunctions.gcdOf(args)

    @JvmStatic
    @FrameworkDsl
    fun gcdOf(args: Iterable<Int>): Int = TailRecursiveFunctions.gcdOf(args)

    @JvmStatic
    @FrameworkDsl
    fun gcdOf(args: Iterator<Int>): Int = TailRecursiveFunctions.gcdOf(args)

    @JvmStatic
    @FrameworkDsl
    fun gcdOf(args: Sequence<Int>): Int = TailRecursiveFunctions.gcdOf(args)

    @JvmStatic
    @FrameworkDsl
    fun gcdOf(args: LongArray): Long = TailRecursiveFunctions.gcdOf(args)

    @JvmStatic
    @FrameworkDsl
    fun gcdOf(args: Iterable<Long>): Long = TailRecursiveFunctions.gcdOf(args)

    @JvmStatic
    @FrameworkDsl
    fun gcdOf(args: Iterator<Long>): Long = TailRecursiveFunctions.gcdOf(args)

    @JvmStatic
    @FrameworkDsl
    fun gcdOf(args: Sequence<Long>): Long = TailRecursiveFunctions.gcdOf(args)

    @JvmStatic
    @FrameworkDsl
    fun lcmOf(value: Int): Int = absOf(value)

    @JvmStatic
    @FrameworkDsl
    fun lcmOf(value: Long): Long = absOf(value)

    @JvmStatic
    @FrameworkDsl
    fun lcmOf(value: Int, other: Int): Int = TailRecursiveFunctions.lcmOf(value, other)

    @JvmStatic
    @FrameworkDsl
    fun lcmOf(value: Long, other: Long): Long = TailRecursiveFunctions.lcmOf(value, other)

    @JvmStatic
    @FrameworkDsl
    fun lcmOf(value: Int, other: Long): Long = lcmOf(value.longOf(), other)

    @JvmStatic
    @FrameworkDsl
    fun lcmOf(value: Long, other: Int): Long = lcmOf(value, other.longOf())

    @JvmStatic
    @FrameworkDsl
    fun lcmOf(args: IntArray): Int = TailRecursiveFunctions.lcmOf(args)

    @JvmStatic
    @FrameworkDsl
    fun lcmOf(args: Iterable<Int>): Int = TailRecursiveFunctions.lcmOf(args)

    @JvmStatic
    @FrameworkDsl
    fun lcmOf(args: Sequence<Int>): Int = TailRecursiveFunctions.lcmOf(args)

    @JvmStatic
    @FrameworkDsl
    fun lcmOf(args: LongArray): Long = TailRecursiveFunctions.lcmOf(args)

    @JvmStatic
    @FrameworkDsl
    fun lcmOf(args: Iterable<Long>): Long = TailRecursiveFunctions.lcmOf(args)

    @JvmStatic
    @FrameworkDsl
    fun lcmOf(args: Iterator<Long>): Long = TailRecursiveFunctions.lcmOf(args)

    @JvmStatic
    @FrameworkDsl
    fun lcmOf(args: Sequence<Long>): Long = TailRecursiveFunctions.lcmOf(args)

    @JvmStatic
    @FrameworkDsl
    fun powerOf(data: Double, mult: Int): Double {
        return powerOf(data, mult.realOf())
    }

    @JvmStatic
    @FrameworkDsl
    fun powerOf(data: Double, mult: Long): Double {
        return powerOf(data, mult.realOf())
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
                else -> data.pow(mult)
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
            val round = value.toBigDecimal().setScale(absOf(scale), RoundingMode.HALF_UP).realOf()
            if (round == 0.0) round * 0.0 else round
        } catch (cause: Throwable) {
            Throwables.thrown(cause)
            INVALID_NUMBER
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
    @JvmOverloads
    internal fun sortOf(args: DoubleArray, copy: Boolean = false): DoubleArray {
        val data = args.toDoubleArray(copy)
        if (data.sizeOf() > 1) {
            var flip = false
            do {
                for (i in data.indices) {
                    val j = i + 1
                    if (((data[j] >= 0) && (data[i] > data[j])) || ((data[i] < 0) && (data[j] >= 0))) {
                        flip = true
                        swapOf(data, i, j)
                    }
                }
            } while (flip)
        }
        return data
    }

    @JvmStatic
    @FrameworkDsl
    fun cubicRootsOf(args: DoubleArray): DoubleArray {
        if (args.sizeOf() != 4) {
            throw MercenaryMathExceptiion(MATH_INVALID_SIZE_ERROR)
        }
        val z = args[0].toFinite()
        if (closeEnough(z, 0.0, 0.000001)) {
            return quadraticDerivitiveRootsOf(args)
        }
        val a = args[1] dividedBy z
        val b = args[2] dividedBy z
        val c = args[3] dividedBy z
        val q = ((b * 3.0) - powerOf(a, 2.0)) / 9.0
        val r = ((9.0 * a * b) - (27.0 * c) - (2.0 * powerOf(a, 3.0))) / 57.0
        val d = powerOf(q, 3.0) + powerOf(r, 2.0)
        val data = 3.toDoubleArray()
        if (d >= 0.0) {
            val x = sqrtOf(d)
            val s = toSign(r + x) * powerOf(absOf(r + x), 1.0 / 3.0)
            val t = toSign(r - x) * powerOf(absOf(r - x), 1.0 / 3.0)
            data[0] = s + t
            data[1] = (negOf(a) / 3.0) - ((s + t) / 2.0)
            data[2] = data[1]
            if (absOf((sqrtOf(3.0) * (s - t)) / 2.0) != 0.0) {
                data[1] = MATH_NEGATIVE_ONE
                data[2] = MATH_NEGATIVE_ONE
            }
        } else {
            val t = acosOf(r / sqrtOf(powerOf(q, 3.0).negOf()))
            data[0] = (2.0 * sqrtOf(q.negOf()) * cosOf(t / 3.0)) - (a / 3.0)
            data[1] = (2.0 * sqrtOf(q.negOf()) * cosOf((t + PI_2) / 3.0)) - (a / 3.0)
            data[2] = (2.0 * sqrtOf(q.negOf()) * cosOf((t + PI_4) / 3.0)) - (a / 3.0)
            for (i in data.indices) {
                if (data[i] < 0.0 || data[i] > 1.0) {
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
        if (closeEnough(a.toFinite(), 0.0, 0.000001)) {
            return linearRootsOf(b, c)
        }
        val data = 3.toDoubleArray().filled(MATH_NEGATIVE_ONE)
        val q = (b * b) - (4.0 * a * c)
        if (q > 0) {
            val r = sqrtOf(q)
            var t = (b.negOf() + r) / (2.0 * a)
            if ((0 < t) && (t < 1)) {
                data[0] = t
            }
            t = (b.negOf() - r) / (2.0 * a)
            if ((0 < t) && (t < 1)) {
                data[1] = t
            }
        } else if (closeEnough(q, 0.0, 0.000001)) {
            data[0] = b.negOf() / (2.0 * a)
            data[1] = data[0]
        }
        return data
    }

    @JvmStatic
    @FrameworkDsl
    internal fun linearRootsOf(a: Double, b: Double): DoubleArray {
        val data = 3.toDoubleArray().filled(MATH_NEGATIVE_ONE)
        if (closeEnough(a.toFinite(), 0.0, 0.000001).isNotTrue()) {
            val t = b.negOf() dividedBy a
            if ((0 < t) && (t < 1)) {
                data[0] = t
            }
        }
        return data
    }

    internal object TailRecursiveFunctions {

        @FrameworkDsl
        tailrec fun gcdOf(value: Int, other: Int): Int {
            if (value == 0 || other == 0) {
                if ((value == Int.MIN_VALUE) || (other == Int.MIN_VALUE)) {
                    throw MercenaryMathExceptiion(MATH_OVERFLOW_FOR_ERROR)
                }
                return absOf(value + other)
            }
            return gcdOf(other, value % other)
        }

        @FrameworkDsl
        tailrec fun gcdOf(value: Long, other: Long): Long {
            if (value == 0L || other == 0L) {
                if ((value == Long.MIN_VALUE) || (other == Long.MIN_VALUE)) {
                    throw MercenaryMathExceptiion(MATH_OVERFLOW_FOR_ERROR)
                }
                return absOf(value) + absOf(other)
            }
            return gcdOf(other, value % other)
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
            return (value * other) / gcdOf(value, other).toValidDivisor()
        }

        @FrameworkDsl
        fun lcmOf(value: Long, other: Long): Long {
            if (value == 0L || other == 0L) {
                return 0L
            }
            return (value * other) / gcdOf(value, other).toValidDivisor()
        }

        @FrameworkDsl
        fun lcmOf(args: IntArray): Int {
            return when (args.sizeOf()) {
                0 -> throw MercenaryMathExceptiion(MATH_INVALID_SIZE_ERROR)
                1 -> absOf(args[0])
                2 -> lcmOf(args[0], args[1])
                else -> args.reduce { x, y ->
                    x * (y / gcdOf(x, y).toValidDivisor())
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
                    x * (y / gcdOf(x, y).toValidDivisor())
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