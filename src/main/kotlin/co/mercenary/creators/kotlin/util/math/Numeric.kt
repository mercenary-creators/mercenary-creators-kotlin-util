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

@file:Suppress("NOTHING_TO_INLINE")

package co.mercenary.creators.kotlin.util.math

import co.mercenary.creators.kotlin.util.*
import java.math.RoundingMode
import kotlin.math.*

object Numeric {

    @CreatorsDsl
    private const val PI_2 = PI * 2.0

    @CreatorsDsl
    private const val PI_4 = PI * 4.0

    @CreatorsDsl
    const val INVALID_NUMBER = Double.NaN

    @CreatorsDsl
    const val DEFAULT_PRECISION_SCALE = 3

    @CreatorsDsl
    const val DEFAULT_PRECISION_DELTA = 0.0000001

    @JvmStatic
    @CreatorsDsl
    inline fun absOf(value: Int): Int = if (value < 0) -value else value

    @JvmStatic
    @CreatorsDsl
    inline fun absOf(value: Long): Long = if (value < 0L) -value else value

    @JvmStatic
    @CreatorsDsl
    inline fun absOf(value: Double): Double = if (value.toFinite() <= 0.0) 0.0 - value else value

    @JvmStatic
    @CreatorsDsl
    inline fun sqrtOf(value: Double): Double = sqrt(value.toFinite())

    @JvmStatic
    @CreatorsDsl
    inline fun toSign(value: Double): Double = value.toFinite().sign

    @JvmStatic
    @CreatorsDsl
    inline fun cosOf(value: Double): Double = cos(value.toFinite())

    @JvmStatic
    @CreatorsDsl
    inline fun sinOf(value: Double): Double = sin(value.toFinite())

    @JvmStatic
    @CreatorsDsl
    inline fun acosOf(value: Double): Double = acos(value.toFinite())

    @JvmStatic
    @CreatorsDsl
    fun addExact(x: Int, y: Int): Int = Math.addExact(x, y)

    @JvmStatic
    @CreatorsDsl
    fun subExact(x: Int, y: Int): Int = Math.subtractExact(x, y)

    @JvmStatic
    @CreatorsDsl
    fun mulExact(x: Int, y: Int): Int = Math.multiplyExact(x, y)

    @JvmStatic
    @CreatorsDsl
    fun addExact(x: Long, y: Long): Long = Math.addExact(x, y)

    @JvmStatic
    @CreatorsDsl
    fun subExact(x: Long, y: Long): Long = Math.subtractExact(x, y)

    @JvmStatic
    @CreatorsDsl
    fun mulExact(x: Long, y: Long): Long = Math.multiplyExact(x, y)

    @JvmStatic
    @CreatorsDsl
    @JvmOverloads
    fun addExactOrElse(x: Int, y: Int, other: Int = 0): Int {
        return try {
            addExact(x, y)
        }
        catch (cause: Throwable) {
            other
        }
    }

    @JvmStatic
    @CreatorsDsl
    @JvmOverloads
    fun subExactOrElse(x: Int, y: Int, other: Int = 0): Int {
        return try {
            subExact(x, y)
        }
        catch (cause: Throwable) {
            other
        }
    }

    @JvmStatic
    @CreatorsDsl
    @JvmOverloads
    fun mulExactOrElse(x: Int, y: Int, other: Int = 0): Int {
        return try {
            mulExact(x, y)
        }
        catch (cause: Throwable) {
            other
        }
    }

    @JvmStatic
    @CreatorsDsl
    @JvmOverloads
    fun addExactOrElse(x: Long, y: Long, other: Long = 0L): Long {
        return try {
            addExact(x, y)
        }
        catch (cause: Throwable) {
            other
        }
    }

    @JvmStatic
    @CreatorsDsl
    @JvmOverloads
    fun subExactOrElse(x: Long, y: Long, other: Long = 0L): Long {
        return try {
            subExact(x, y)
        }
        catch (cause: Throwable) {
            other
        }
    }

    @JvmStatic
    @CreatorsDsl
    @JvmOverloads
    fun mulExactOrElse(x: Long, y: Long, other: Long = 0L): Long {
        return try {
            mulExact(x, y)
        }
        catch (cause: Throwable) {
            other
        }
    }

    @JvmStatic
    @CreatorsDsl
    fun toDegrees(radians: Double): Double {
        return radians.toFiniteOrElse(0.0).let { value ->
            if (value == 0.0) 0.0 else degreesOf((value * 180.0) / PI)
        }
    }

    @JvmStatic
    @CreatorsDsl
    fun toRadians(degrees: Double): Double {
        return degrees.toFiniteOrElse(0.0).let { value ->
            if (value == 0.0) 0.0 else ((degreesOf(value) / 180.0) * PI)
        }
    }

    @JvmStatic
    @CreatorsDsl
    fun degreesOf(degrees: Double): Double {
        return degrees.toFiniteOrElse(0.0).let { value ->
            if (value == 0.0) 0.0 else value.rem(360.0).let { if (it == 0.0) 0.0 else it }
        }
    }

    @JvmStatic
    @CreatorsDsl
    fun radiansOf(radians: Double): Double {
        return radians.toFiniteOrElse(0.0).let { value ->
            if (value == 0.0) 0.0 else toRadians(toDegrees(value))
        }
    }

    @JvmStatic
    @CreatorsDsl
    fun collinear(x1: Double, y1: Double, x2: Double, y2: Double, x3: Double, y3: Double): Boolean {
        return closeEnough((y1 - y2) * (x1 - x3), (y1 - y3) * (x1 - x2), 1e-9)
    }

    @JvmStatic
    @CreatorsDsl
    @JvmOverloads
    fun toDecimalPlacesString(data: Double, scale: Int = DEFAULT_PRECISION_SCALE, places: Int = absOf(scale)): String {
        return "%.${places}f".format(rounded(data, scale))
    }

    @CreatorsDsl
    private inline fun Double.deltaOf(): Double = if (isValid()) absOf(this) else DEFAULT_PRECISION_DELTA

    @JvmStatic
    @CreatorsDsl
    @JvmOverloads
    fun closeEnough(value: Double, other: Double, precision: Double = DEFAULT_PRECISION_DELTA): Boolean {
        return if (value.toBits() != other.toBits()) (absOf(value - other) <= precision.deltaOf()) else true
    }

    @JvmStatic
    @CreatorsDsl
    @JvmOverloads
    fun closeEnough(value: DoubleArray, other: DoubleArray, precision: Double = DEFAULT_PRECISION_DELTA): Boolean {
        if (value.size != other.size) {
            return false
        }
        val delta = precision.deltaOf()
        for (i in value.indices) {
            val v = value[i]
            val o = other[i]
            if (v.toBits() != o.toBits()) {
                if (absOf(v - o) > delta) {
                    return false
                }
            }
        }
        return true
    }

    @JvmStatic
    @CreatorsDsl
    @JvmOverloads
    fun closeEnough(value: DoubleArray, other: Array<Double>, precision: Double = DEFAULT_PRECISION_DELTA): Boolean {
        if (value.size != other.size) {
            return false
        }
        val delta = precision.deltaOf()
        for (i in value.indices) {
            val v = value[i]
            val o = other[i]
            if (v.toBits() != o.toBits()) {
                if (absOf(v - o) > delta) {
                    return false
                }
            }
        }
        return true
    }

    @JvmStatic
    @CreatorsDsl
    @JvmOverloads
    fun closeEnough(value: Array<Double>, other: Array<Double>, precision: Double = DEFAULT_PRECISION_DELTA): Boolean {
        if (value.size != other.size) {
            return false
        }
        val delta = precision.deltaOf()
        for (i in value.indices) {
            val v = value[i]
            val o = other[i]
            if (v.toBits() != o.toBits()) {
                if (absOf(v - o) > delta) {
                    return false
                }
            }
        }
        return true
    }

    @JvmStatic
    @CreatorsDsl
    @JvmOverloads
    fun closeEnough(value: Array<DoubleArray>, other: Array<DoubleArray>, precision: Double = DEFAULT_PRECISION_DELTA): Boolean {
        if (value.size != other.size) {
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
    @CreatorsDsl
    @JvmOverloads
    fun closeEnough(value: Array<Array<Double>>, other: Array<Array<Double>>, precision: Double = DEFAULT_PRECISION_DELTA): Boolean {
        if (value.size != other.size) {
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
    @CreatorsDsl
    fun toDoubleArray(list: IntArray): DoubleArray = DoubleArray(list.size) { i -> list[i].toDouble() }

    @JvmStatic
    @CreatorsDsl
    fun toDoubleArray(list: LongArray): DoubleArray = DoubleArray(list.size) { i -> list[i].toDouble() }

    @JvmStatic
    @CreatorsDsl
    fun toDoubleArray(list: Array<Int>): DoubleArray = DoubleArray(list.size) { i -> list[i].toDouble() }

    @JvmStatic
    @CreatorsDsl
    fun toDoubleArray(list: Array<Long>): DoubleArray = DoubleArray(list.size) { i -> list[i].toDouble() }

    @JvmStatic
    @CreatorsDsl
    fun toDoubleArray(list: DoubleArray): DoubleArray = DoubleArray(list.size) { i -> list[i].toFinite() }

    @JvmStatic
    @CreatorsDsl
    fun toDoubleArray(list: Array<Double>): DoubleArray = DoubleArray(list.size) { i -> list[i].toFinite() }

    @JvmStatic
    @CreatorsDsl
    fun <N : Number> toDoubleArray(args: Iterable<N>): DoubleArray = args.toList().let { list -> DoubleArray(list.size) { i -> list[i].toDouble().toFinite() } }

    @JvmStatic
    @CreatorsDsl
    fun <N : Number> toDoubleArray(args: Sequence<N>): DoubleArray = args.toList().let { list -> DoubleArray(list.size) { i -> list[i].toDouble().toFinite() } }

    @JvmStatic
    @CreatorsDsl
    fun gcdOf(value: Int): Int = absOf(value)

    @JvmStatic
    @CreatorsDsl
    fun gcdOf(value: Long): Long = absOf(value)

    @JvmStatic
    @CreatorsDsl
    fun gcdOf(value: Int, other: Int): Int = TailRecursiveFunctions.gcdOf(value, other)

    @JvmStatic
    @CreatorsDsl
    fun gcdOf(value: Long, other: Long): Long = TailRecursiveFunctions.gcdOf(value, other)

    @JvmStatic
    @CreatorsDsl
    fun gcdOf(value: Int, other: Long): Long = gcdOf(value.toLong(), other)

    @JvmStatic
    @CreatorsDsl
    fun gcdOf(value: Long, other: Int): Long = gcdOf(value, other.toLong())

    @JvmStatic
    @CreatorsDsl
    fun gcdOf(vararg args: Int): Int = TailRecursiveFunctions.gcdOf(args)

    @JvmStatic
    @CreatorsDsl
    fun gcdOf(args: Iterable<Int>): Int = TailRecursiveFunctions.gcdOf(args)

    @JvmStatic
    @CreatorsDsl
    fun gcdOf(args: Sequence<Int>): Int = TailRecursiveFunctions.gcdOf(args)

    @JvmStatic
    @CreatorsDsl
    fun gcdOf(vararg args: Long): Long = TailRecursiveFunctions.gcdOf(args)

    @JvmStatic
    @CreatorsDsl
    fun gcdOf(args: Iterable<Long>): Long = TailRecursiveFunctions.gcdOf(args)

    @JvmStatic
    @CreatorsDsl
    fun gcdOf(args: Sequence<Long>): Long = TailRecursiveFunctions.gcdOf(args)

    @JvmStatic
    @CreatorsDsl
    fun lcmOf(value: Int): Int = absOf(value)

    @JvmStatic
    @CreatorsDsl
    fun lcmOf(value: Long): Long = absOf(value)

    @JvmStatic
    @CreatorsDsl
    fun lcmOf(value: Int, other: Int): Int = TailRecursiveFunctions.lcmOf(value, other)

    @JvmStatic
    @CreatorsDsl
    fun lcmOf(value: Long, other: Long): Long = TailRecursiveFunctions.lcmOf(value, other)

    @JvmStatic
    @CreatorsDsl
    fun lcmOf(value: Int, other: Long): Long = lcmOf(value.toLong(), other)

    @JvmStatic
    @CreatorsDsl
    fun lcmOf(value: Long, other: Int): Long = lcmOf(value, other.toLong())

    @JvmStatic
    @CreatorsDsl
    fun lcmOf(vararg args: Int): Int = TailRecursiveFunctions.lcmOf(args)

    @JvmStatic
    @CreatorsDsl
    fun lcmOf(args: Iterable<Int>): Int = TailRecursiveFunctions.lcmOf(args)

    @JvmStatic
    @CreatorsDsl
    fun lcmOf(args: Sequence<Int>): Int = TailRecursiveFunctions.lcmOf(args)

    @JvmStatic
    @CreatorsDsl
    fun lcmOf(vararg args: Long): Long = TailRecursiveFunctions.lcmOf(args)

    @JvmStatic
    @CreatorsDsl
    fun lcmOf(args: Iterable<Long>): Long = TailRecursiveFunctions.lcmOf(args)

    @JvmStatic
    @CreatorsDsl
    fun lcmOf(args: Sequence<Long>): Long = TailRecursiveFunctions.lcmOf(args)

    @JvmStatic
    @CreatorsDsl
    fun powerOf(data: Double, mult: Int): Double {
        return powerOf(data, mult.toDouble())
    }

    @JvmStatic
    @CreatorsDsl
    fun powerOf(data: Double, mult: Long): Double {
        return powerOf(data, mult.toDouble())
    }

    @JvmStatic
    @CreatorsDsl
    fun powerOf(data: Double, mult: Double): Double {
        if (data.isValid() && mult.isValid()) {
            if (data == NEGATIVE_ONE) {
                return if (mult.isEven()) POSITIVE_ONE else NEGATIVE_ONE
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
    @CreatorsDsl
    fun rootOf(data: Double, root: Int): Double {
        return rootOf(data, root.toDouble())
    }

    @JvmStatic
    @CreatorsDsl
    fun rootOf(data: Double, root: Long): Double {
        return rootOf(data, root.toDouble())
    }

    @JvmStatic
    @CreatorsDsl
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
    @CreatorsDsl
    @JvmOverloads
    fun rounded(value: Double, scale: Int = DEFAULT_PRECISION_SCALE): Double {
        return try {
            val round = value.toBigDecimal().setScale(absOf(scale), RoundingMode.HALF_UP).toDouble()
            if (round == 0.0) round * 0.0 else round
        }
        catch (cause: Throwable) {
            Throwables.thrown(cause)
            INVALID_NUMBER
        }
    }

    @JvmStatic
    @CreatorsDsl
    @JvmOverloads
    fun rounded(args: DoubleArray, scale: Int = DEFAULT_PRECISION_SCALE): DoubleArray {
        return DoubleArray(args.size) { index ->
            rounded(args[index], scale)
        }
    }

    @JvmStatic
    @CreatorsDsl
    @JvmOverloads
    fun rounded(args: Array<Double>, scale: Int = DEFAULT_PRECISION_SCALE): Array<Double> {
        return Array(args.size) { index ->
            rounded(args[index], scale)
        }
    }

    @JvmStatic
    @CreatorsDsl
    @JvmOverloads
    fun rounded(args: Array<DoubleArray>, scale: Int = DEFAULT_PRECISION_SCALE): Array<DoubleArray> {
        return Array(args.size) { index ->
            rounded(args[index], scale)
        }
    }

    @JvmStatic
    @CreatorsDsl
    @JvmOverloads
    fun rounded(args: Array<Array<Double>>, scale: Int = DEFAULT_PRECISION_SCALE): Array<Array<Double>> {
        return Array(args.size) { index ->
            rounded(args[index], scale)
        }
    }

    @JvmStatic
    @CreatorsDsl
    @JvmOverloads
    fun rounded(args: Sequence<Double>, scale: Int = DEFAULT_PRECISION_SCALE): Sequence<Double> {
        return args.map {
            rounded(it, scale)
        }
    }

    @JvmStatic
    @CreatorsDsl
    @JvmOverloads
    fun rounded(args: Iterable<Double>, scale: Int = DEFAULT_PRECISION_SCALE): Iterable<Double> {
        return args.map { value ->
            rounded(value, scale)
        }
    }

    @JvmStatic
    @CreatorsDsl
    @JvmOverloads
    internal fun swapOf(data: IntArray, i: Int, j: Int = i + 1) {
        if (i != j) {
            val tmp = data[i]
            data[i] = data[j]
            data[j] = tmp
        }
    }

    @JvmStatic
    @CreatorsDsl
    @JvmOverloads
    internal fun swapOf(data: LongArray, i: Int, j: Int = i + 1) {
        if (i != j) {
            val tmp = data[i]
            data[i] = data[j]
            data[j] = tmp
        }
    }

    @JvmStatic
    @CreatorsDsl
    @JvmOverloads
    internal fun swapOf(data: DoubleArray, i: Int, j: Int = i + 1) {
        if (i != j) {
            val tmp = data[i]
            data[i] = data[j]
            data[j] = tmp
        }
    }

    @JvmStatic
    @CreatorsDsl
    @JvmOverloads
    internal fun sortOf(args: DoubleArray, copy: Boolean = false): DoubleArray {
        val data = if (copy) args.copyOf() else args
        if (data.size > 1) {
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
    @CreatorsDsl
    fun cubicRootsOf(args: DoubleArray): DoubleArray {
        if (args.size != 4) {
            throw MercenaryFatalExceptiion(MATH_INVALID_SIZE_ERROR)
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
        val data = Vector(3)
        if (d >= 0.0) {
            val x = sqrtOf(d)
            val s = toSign(r + x) * powerOf(absOf(r + x), 1.0 / 3.0)
            val t = toSign(r - x) * powerOf(absOf(r - x), 1.0 / 3.0)
            data[0] = s + t
            data[1] = (a.neg() / 3.0) - ((s + t) / 2.0)
            data[2] = data[1]
            if (absOf((sqrtOf(3.0) * (s - t)) / 2.0) != 0.0) {
                data[1] = NEGATIVE_ONE
                data[2] = NEGATIVE_ONE
            }
        }
        else {
            val t = acosOf(r / sqrtOf(powerOf(q, 3.0).neg()))
            data[0] = (2.0 * sqrtOf(q.neg()) * cosOf(t / 3.0)) - (a / 3.0)
            data[1] = (2.0 * sqrtOf(q.neg()) * cosOf((t + PI_2) / 3.0)) - (a / 3.0)
            data[2] = (2.0 * sqrtOf(q.neg()) * cosOf((t + PI_4) / 3.0)) - (a / 3.0)
            for (i in data.indices) {
                if (data[i] < 0.0 || data[i] > 1.0) {
                    data[i] = NEGATIVE_ONE
                }
            }
        }
        return sortOf(data)
    }

    @JvmStatic
    @CreatorsDsl
    fun quadraticDerivitiveRootsOf(args: DoubleArray): DoubleArray {
        if (args.size < 3) {
            throw MercenaryFatalExceptiion(MATH_INVALID_SIZE_ERROR)
        }
        val (a, b, c) = args
        return quadraticRootsOf(a, b, c)
    }

    @JvmStatic
    @CreatorsDsl
    internal fun quadraticRootsOf(a: Double, b: Double, c: Double): DoubleArray {
        if (closeEnough(a.toFinite(), 0.0, 0.000001)) {
            return linearRootsOf(b, c)
        }
        val data = Vector(3, NEGATIVE_ONE)
        val q = (b * b) - (4.0 * a * c)
        if (q > 0) {
            val r = sqrtOf(q)
            var t = (b.neg() + r) / (2.0 * a)
            if ((0 < t) && (t < 1)) {
                data[0] = t
            }
            t = (b.neg() - r) / (2.0 * a)
            if ((0 < t) && (t < 1)) {
                data[1] = t
            }
        }
        else if (closeEnough(q, 0.0, 0.000001)) {
            data[0] = b.neg() / (2.0 * a)
            data[1] = data[0]
        }
        return data
    }

    @JvmStatic
    @CreatorsDsl
    internal fun linearRootsOf(a: Double, b: Double): DoubleArray {
        val data = Vector(3, NEGATIVE_ONE)
        if (closeEnough(a.toFinite(), 0.0, 0.000001).isNotTrue()) {
            val t = b.neg() dividedBy a
            if ((0 < t) && (t < 1)) {
                data[0] = t
            }
        }
        return data
    }

    internal object TailRecursiveFunctions {

        @CreatorsDsl
        tailrec fun gcdOf(value: Int, other: Int): Int {
            if (value == 0 || other == 0) {
                if ((value == Int.MIN_VALUE) || (other == Int.MIN_VALUE)) {
                    throw MercenaryFatalExceptiion(MATH_OVERFLOW_FOR_ERROR)
                }
                return absOf(value + other)
            }
            return gcdOf(other, value % other)
        }

        @CreatorsDsl
        tailrec fun gcdOf(value: Long, other: Long): Long {
            if (value == 0L || other == 0L) {
                if ((value == Long.MIN_VALUE) || (other == Long.MIN_VALUE)) {
                    throw MercenaryFatalExceptiion(MATH_OVERFLOW_FOR_ERROR)
                }
                return absOf(value) + absOf(other)
            }
            return gcdOf(other, value % other)
        }

        @CreatorsDsl
        fun gcdOf(args: IntArray): Int {
            return when (args.size) {
                0 -> throw MercenaryFatalExceptiion(MATH_INVALID_SIZE_ERROR)
                1 -> absOf(args[0])
                2 -> gcdOf(args[0], args[1])
                else -> args.reduce { x, y ->
                    gcdOf(x, y)
                }
            }
        }

        @CreatorsDsl
        fun gcdOf(args: Iterable<Int>): Int {
            val list = args.toList()
            return when (list.size) {
                0 -> throw MercenaryFatalExceptiion(MATH_INVALID_SIZE_ERROR)
                1 -> absOf(list[0])
                2 -> gcdOf(list[0], list[1])
                else -> list.reduce { x, y ->
                    gcdOf(x, y)
                }
            }
        }

        @CreatorsDsl
        fun gcdOf(args: Sequence<Int>): Int {
            val list = args.toList()
            return when (list.size) {
                0 -> throw MercenaryFatalExceptiion(MATH_INVALID_SIZE_ERROR)
                1 -> absOf(list[0])
                2 -> gcdOf(list[0], list[1])
                else -> list.reduce { x, y ->
                    gcdOf(x, y)
                }
            }
        }

        @CreatorsDsl
        fun gcdOf(args: LongArray): Long {
            return when (args.size) {
                0 -> throw MercenaryFatalExceptiion(MATH_INVALID_SIZE_ERROR)
                1 -> absOf(args[0])
                2 -> gcdOf(args[0], args[1])
                else -> args.reduce { x, y ->
                    gcdOf(x, y)
                }
            }
        }

        @CreatorsDsl
        fun gcdOf(args: Iterable<Long>): Long {
            val list = args.toList()
            return when (list.size) {
                0 -> throw MercenaryFatalExceptiion(MATH_INVALID_SIZE_ERROR)
                1 -> absOf(list[0])
                2 -> gcdOf(list[0], list[1])
                else -> list.reduce { x, y ->
                    gcdOf(x, y)
                }
            }
        }

        @CreatorsDsl
        fun gcdOf(args: Sequence<Long>): Long {
            val list = args.toList()
            return when (list.size) {
                0 -> throw MercenaryFatalExceptiion(MATH_INVALID_SIZE_ERROR)
                1 -> absOf(list[0])
                2 -> gcdOf(list[0], list[1])
                else -> list.reduce { x, y ->
                    gcdOf(x, y)
                }
            }
        }

        @CreatorsDsl
        fun lcmOf(value: Int, other: Int): Int {
            if (value == 0 || other == 0) {
                return 0
            }
            return when (val gcd = gcdOf(value, other)) {
                0 -> throw MercenaryFatalExceptiion(MATH_ZERO_DIVISOR_ERROR)
                else -> (value * other) / gcd
            }
        }

        @CreatorsDsl
        fun lcmOf(value: Long, other: Long): Long {
            if (value == 0L || other == 0L) {
                return 0L
            }
            return when (val gcd = gcdOf(value, other)) {
                0L -> throw MercenaryFatalExceptiion(MATH_ZERO_DIVISOR_ERROR)
                else -> (value * other) / gcd
            }
        }

        @CreatorsDsl
        fun lcmOf(args: IntArray): Int {
            return when (args.size) {
                0 -> throw MercenaryFatalExceptiion(MATH_INVALID_SIZE_ERROR)
                1 -> absOf(args[0])
                2 -> lcmOf(args[0], args[1])
                else -> args.reduce { x, y ->
                    when (val gcd = gcdOf(x, y)) {
                        0 -> throw MercenaryFatalExceptiion(MATH_ZERO_DIVISOR_ERROR)
                        else -> x * (y / gcd)
                    }
                }
            }
        }

        @CreatorsDsl
        fun lcmOf(args: Iterable<Int>): Int {
            val list = args.toList()
            return when (list.size) {
                0 -> throw MercenaryFatalExceptiion(MATH_INVALID_SIZE_ERROR)
                1 -> absOf(list[0])
                2 -> lcmOf(list[0], list[1])
                else -> list.reduce { x, y ->
                    when (val gcd = gcdOf(x, y)) {
                        0 -> throw MercenaryFatalExceptiion(MATH_ZERO_DIVISOR_ERROR)
                        else -> x * (y / gcd)
                    }
                }
            }
        }

        @CreatorsDsl
        fun lcmOf(args: Sequence<Int>): Int {
            val list = args.toList()
            return when (list.size) {
                0 -> throw MercenaryFatalExceptiion(MATH_INVALID_SIZE_ERROR)
                1 -> absOf(list[0])
                2 -> lcmOf(list[0], list[1])
                else -> list.reduce { x, y ->
                    when (val gcd = gcdOf(x, y)) {
                        0 -> throw MercenaryFatalExceptiion(MATH_ZERO_DIVISOR_ERROR)
                        else -> x * (y / gcd)
                    }
                }
            }
        }

        @CreatorsDsl
        fun lcmOf(args: LongArray): Long {
            return when (args.size) {
                0 -> throw MercenaryFatalExceptiion(MATH_INVALID_SIZE_ERROR)
                1 -> absOf(args[0])
                2 -> lcmOf(args[0], args[1])
                else -> args.reduce { x, y ->
                    when (val gcd = gcdOf(x, y)) {
                        0L -> throw MercenaryFatalExceptiion(MATH_ZERO_DIVISOR_ERROR)
                        else -> x * (y / gcd)
                    }
                }
            }
        }

        @CreatorsDsl
        fun lcmOf(args: Iterable<Long>): Long {
            val list = args.toList()
            return when (list.size) {
                0 -> throw MercenaryFatalExceptiion(MATH_INVALID_SIZE_ERROR)
                1 -> absOf(list[0])
                2 -> lcmOf(list[0], list[1])
                else -> list.reduce { x, y ->
                    when (val gcd = gcdOf(x, y)) {
                        0L -> throw MercenaryFatalExceptiion(MATH_ZERO_DIVISOR_ERROR)
                        else -> x * (y / gcd)
                    }
                }
            }
        }

        @CreatorsDsl
        fun lcmOf(args: Sequence<Long>): Long {
            val list = args.toList()
            return when (list.size) {
                0 -> throw MercenaryFatalExceptiion(MATH_INVALID_SIZE_ERROR)
                1 -> absOf(list[0])
                2 -> lcmOf(list[0], list[1])
                else -> list.reduce { x, y ->
                    when (val gcd = gcdOf(x, y)) {
                        0L -> throw MercenaryFatalExceptiion(MATH_ZERO_DIVISOR_ERROR)
                        else -> x * (y / gcd)
                    }
                }
            }
        }
    }
}