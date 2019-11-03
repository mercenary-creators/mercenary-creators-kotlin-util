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

package co.mercenary.creators.kotlin.util.math

import co.mercenary.creators.kotlin.util.*
import java.math.*
import kotlin.math.*

object Numeric {

    const val DEFAULT_PRECISION = 0.0000001

    const val INVALID_SIZE = "invalid size"

    const val ZERO_DIVISOR = "can't divide by zero"

    private val TAIL_RECURSIVE_FUNCTIONS: TailRecursiveFunctions by lazy {
        TailRecursiveFunctions()
    }

    @JvmStatic
    @JvmOverloads
    fun toDecimalPlacesString(data: Double, scale: Int = 3, places: Int = abs(scale)): String {
        return "%.${places}f".format(rounded(data, scale))
    }

    @JvmStatic
    @JvmOverloads
    fun pow2Round(value: Int, down: Boolean = true): Int {
        return when (value < 2) {
            true -> 1
            else -> Integer.highestOneBit(value - 1).also {
                return when (down) {
                    true -> it
                    else -> it * 2
                }
            }
        }
    }

    @JvmStatic
    @JvmOverloads
    fun pow2Round(value: Long, down: Boolean = true): Long {
        return when (value < 2) {
            true -> 1
            else -> java.lang.Long.highestOneBit(value - 1).also {
                return when (down) {
                    true -> it
                    else -> it * 2
                }
            }
        }
    }

    @JvmStatic
    @JvmOverloads
    fun closeEnough(value: Double, other: Double, precision: Double = DEFAULT_PRECISION): Boolean {
        val delta = if (precision.isNaN().or(precision.isInfinite())) DEFAULT_PRECISION else abs(precision)
        return if (value.toBits() != other.toBits()) (abs(value - other) <= delta) else true
    }

    @JvmStatic
    @JvmOverloads
    fun closeEnough(v1: Double, v2: Double, o1: Double, o2: Double, precision: Double = DEFAULT_PRECISION): Boolean {
        val delta = if (precision.isNaN().or(precision.isInfinite())) DEFAULT_PRECISION else abs(precision)
        if (v1.toBits() != v2.toBits()) {
            if (abs(v1 - v2) > delta) {
                return false
            }
        }
        if (o1.toBits() != o2.toBits()) {
            if (abs(o1 - o2) > delta) {
                return false
            }
        }
        return true
    }

    @JvmStatic
    @JvmOverloads
    fun closeEnough(value: DoubleArray, other: DoubleArray, precision: Double = DEFAULT_PRECISION): Boolean {
        if (value.size != other.size) {
            return false
        }
        val delta = if (precision.isNaN().or(precision.isInfinite())) DEFAULT_PRECISION else abs(precision)
        for (i in value.indices) {
            val v = value[i]
            val o = other[i]
            if (v.toBits() != o.toBits()) {
                if (abs(v - o) > delta) {
                    return false
                }
            }
        }
        return true
    }

    @JvmStatic
    @JvmOverloads
    fun closeEnough(value: Array<Double>, other: Array<Double>, precision: Double = DEFAULT_PRECISION): Boolean {
        if (value.size != other.size) {
            return false
        }
        val delta = if (precision.isNaN().or(precision.isInfinite())) DEFAULT_PRECISION else abs(precision)
        for (i in value.indices) {
            val v = value[i]
            val o = other[i]
            if (v.toBits() != o.toBits()) {
                if (abs(v - o) > delta) {
                    return false
                }
            }
        }
        return true
    }

    @JvmStatic
    @JvmOverloads
    fun closeEnough(value: Array<DoubleArray>, other: Array<DoubleArray>, precision: Double = DEFAULT_PRECISION): Boolean {
        if (value.size != other.size) {
            return false
        }
        for (i in value.indices) {
            if (closeEnough(value[i], other[i], precision).not()) {
                return false
            }
        }
        return true
    }

    @JvmStatic
    @JvmOverloads
    fun closeEnough(value: Array<Array<Double>>, other: Array<Array<Double>>, precision: Double = DEFAULT_PRECISION): Boolean {
        if (value.size != other.size) {
            return false
        }
        for (i in value.indices) {
            if (closeEnough(value[i], other[i], precision).not()) {
                return false
            }
        }
        return true
    }

    @JvmStatic
    fun powNegative1(value: Int): Double = powNegative1(value.toLong())

    @JvmStatic
    fun powNegative1(value: Long): Double = if (value % 2 == 0L) 1.0 else -1.0

    @JvmStatic
    fun powNegative1(value: Int, other: Int): Double = powNegative1(value.toLong() + other)

    @JvmStatic
    fun gcd(value: Int, other: Int): Int = if (other == 0) abs(value) else TAIL_RECURSIVE_FUNCTIONS.gcd(value, other)

    @JvmStatic
    fun gcd(value: Long, other: Long): Long = if (other == 0L) abs(value) else TAIL_RECURSIVE_FUNCTIONS.gcd(value, other)

    @JvmStatic
    fun gcd(args: IntArray): Int = TAIL_RECURSIVE_FUNCTIONS.gcd(args)

    @JvmStatic
    fun gcd(args: LongArray): Long = TAIL_RECURSIVE_FUNCTIONS.gcd(args)

    @JvmStatic
    fun lcm(value: Int, other: Int): Int = TAIL_RECURSIVE_FUNCTIONS.lcm(value, other)

    @JvmStatic
    fun lcm(value: Long, other: Long): Long = TAIL_RECURSIVE_FUNCTIONS.lcm(value, other)

    @JvmStatic
    fun lcm(args: IntArray): Int = TAIL_RECURSIVE_FUNCTIONS.lcm(args)

    @JvmStatic
    fun lcm(args: LongArray): Long = TAIL_RECURSIVE_FUNCTIONS.lcm(args)

    @JvmStatic
    @JvmOverloads
    fun root(value: Double, root: Int = 2): Double {
        return when (root) {
            0 -> 1.0
            1 -> value
            2 -> sqrt(value)
            else -> value.pow(-root.toDouble())
        }
    }

    @JvmStatic
    @JvmOverloads
    fun rounded(value: Double, scale: Int = 3): Double {
        return try {
            val round = BigDecimal(value.toString()).setScale(abs(scale), RoundingMode.HALF_UP).toDouble()
            if (round == 0.0) round * 0.0 else round
        }
        catch (cause: Throwable) {
            Throwables.thrown(cause)
            if (value.isInfinite()) value else Double.NaN
        }
    }

    @JvmStatic
    fun divide(value: Double, other: Int): Double = divide(value, other.toDouble())

    @JvmStatic
    fun divide(value: Double, other: Long): Double = divide(value, other.toDouble())

    @JvmStatic
    fun divide(value: Double, other: Double): Double = if (other == 0.0) throw MercenaryFatalExceptiion(ZERO_DIVISOR) else value / other

    @JvmStatic
    fun hashCode(value: DoubleArray): Int {
        return value.contentHashCode()
    }

    @JvmStatic
    fun hashCode(value: Array<Double>): Int {
        return value.toDoubleArray().contentHashCode()
    }

    @JvmStatic
    fun hashCode(value: Array<DoubleArray>): Int {
        return value.contentDeepHashCode()
    }

    @JvmStatic
    fun hashCode(value: Array<Array<Double>>): Int {
        return value.contentDeepHashCode()
    }

    private class TailRecursiveFunctions {

        tailrec fun gcd(value: Int, other: Int): Int {
            if (other == 0) {
                return abs(value)
            }
            return gcd(other, value % other)
        }

        tailrec fun gcd(value: Long, other: Long): Long {
            if (other == 0L) {
                return abs(value)
            }
            return gcd(other, value % other)
        }

        fun gcd(args: IntArray): Int {
            return when (args.size) {
                0 -> throw MercenaryFatalExceptiion(INVALID_SIZE)
                1 -> abs(args[0])
                2 -> gcd(args[0], args[1])
                else -> args.reduce { x, y ->
                    gcd(x, y)
                }
            }
        }

        fun gcd(args: LongArray): Long {
            return when (args.size) {
                0 -> throw MercenaryFatalExceptiion(INVALID_SIZE)
                1 -> abs(args[0])
                2 -> gcd(args[0], args[1])
                else -> args.reduce { x, y ->
                    gcd(x, y)
                }
            }
        }

        fun lcm(value: Int, other: Int): Int {
            return when (val gcd = gcd(value, other)) {
                0 -> throw MercenaryFatalExceptiion(ZERO_DIVISOR)
                else -> (value * other) / gcd
            }
        }

        fun lcm(value: Long, other: Long): Long {
            return when (val gcd = gcd(value, other)) {
                0L -> throw MercenaryFatalExceptiion(ZERO_DIVISOR)
                else -> (value * other) / gcd
            }
        }

        fun lcm(args: IntArray): Int {
            return when (args.size) {
                0 -> throw MercenaryFatalExceptiion(INVALID_SIZE)
                1 -> abs(args[0])
                2 -> lcm(args[0], args[1])
                else -> args.reduce { x, y ->
                    when (val gcd = gcd(x, y)) {
                        0 -> throw MercenaryFatalExceptiion(ZERO_DIVISOR)
                        else -> x * (y / gcd)
                    }
                }
            }
        }

        fun lcm(args: LongArray): Long {
            return when (args.size) {
                0 -> throw MercenaryFatalExceptiion(INVALID_SIZE)
                1 -> abs(args[0])
                2 -> lcm(args[0], args[1])
                else -> args.reduce { x, y ->
                    when (val gcd = gcd(x, y)) {
                        0L -> throw MercenaryFatalExceptiion(ZERO_DIVISOR)
                        else -> x * (y / gcd)
                    }
                }
            }
        }
    }
}