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

    const val DEFAULT_PRECISION_SCALE = 3

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
    fun addExact(x: Int, y: Int): Int = NumericMath.addExact(x, y)

    @JvmStatic
    @CreatorsDsl
    fun subExact(x: Int, y: Int): Int = NumericMath.subtractExact(x, y)

    @JvmStatic
    @CreatorsDsl
    fun mulExact(x: Int, y: Int): Int = NumericMath.multiplyExact(x, y)

    @JvmStatic
    @CreatorsDsl
    fun addExact(x: Long, y: Long): Long = NumericMath.addExact(x, y)

    @JvmStatic
    @CreatorsDsl
    fun subExact(x: Long, y: Long): Long = NumericMath.subtractExact(x, y)

    @JvmStatic
    @CreatorsDsl
    fun mulExact(x: Long, y: Long): Long = NumericMath.multiplyExact(x, y)

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
    fun powerOf(value: Double, mult: Int): Double {
        val data = value.toFinite()
        if (data == -1.0) {
            return if (mult.isEven()) 1.0 else -1.0
        }
        return when (mult) {
            0 -> 1.0
            1 -> data
            2 -> data * data
            else -> data.pow(mult.toDouble())
        }
    }

    @JvmStatic
    @CreatorsDsl
    fun powerOf(value: Double, mult: Long): Double {
        val data = value.toFinite()
        if (data == -1.0) {
            return if (mult.isEven()) 1.0 else -1.0
        }
        return when (mult) {
            0L -> 1.0
            1L -> data
            2L -> data * data
            else -> data.pow(mult.toDouble())
        }
    }

    @JvmStatic
    @CreatorsDsl
    fun powerOf(value: Double, mult: Double): Double {
        val data = value.toFinite()
        if (data == -1.0) {
            return if (mult.isEven()) 1.0 else -1.0
        }
        return when (mult.toFinite()) {
            0.0 -> 1.0
            1.0 -> data
            2.0 -> data * data
            else -> data.pow(mult)
        }
    }

    @JvmStatic
    @CreatorsDsl
    fun rootOf(value: Double, root: Int): Double {
        val data = value.toFinite()
        return when (root) {
            0 -> 1.0
            1 -> data
            2 -> sqrtOf(data)
            else -> powerOf(data, -root)
        }
    }

    @JvmStatic
    @CreatorsDsl
    fun rootOf(value: Double, root: Long): Double {
        val data = value.toFinite()
        return when (root) {
            0L -> 1.0
            1L -> data
            2L -> sqrtOf(data)
            else -> powerOf(data, -root)
        }
    }

    @JvmStatic
    @CreatorsDsl
    fun rootOf(value: Double, root: Double): Double {
        val data = value.toFinite()
        return when (root.toFinite()) {
            0.0 -> 1.0
            1.0 -> data
            2.0 -> sqrtOf(data)
            else -> powerOf(data, -root)
        }
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
            if (value.isInfinite()) value else Double.NaN
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

    private object TailRecursiveFunctions {

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