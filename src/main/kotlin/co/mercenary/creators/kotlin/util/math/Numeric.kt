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

package co.mercenary.creators.kotlin.util.math

import co.mercenary.creators.kotlin.util.*
import org.apache.commons.math3.primes.Primes
import org.apache.commons.math3.stat.StatUtils
import java.math.RoundingMode
import kotlin.math.*

object Numeric {

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
    fun closeEnough(value: Double, other: Double, precision: Double = DEFAULT_PRECISION_DELTA): Boolean {
        val delta = if (precision.isFinite()) precision.abs() else DEFAULT_PRECISION_DELTA
        return if (value.toBits() != other.toBits()) (abs(value - other) <= delta) else true
    }

    @JvmStatic
    @JvmOverloads
    fun closeEnough(v1: Double, v2: Double, o1: Double, o2: Double, precision: Double = DEFAULT_PRECISION_DELTA): Boolean {
        val delta = if (precision.isFinite()) precision.abs() else DEFAULT_PRECISION_DELTA
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
    fun closeEnough(value: DoubleArray, other: DoubleArray, precision: Double = DEFAULT_PRECISION_DELTA): Boolean {
        if (value.size != other.size) {
            return false
        }
        val delta = if (precision.isFinite()) precision.abs() else DEFAULT_PRECISION_DELTA
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
    fun closeEnough(value: Array<Double>, other: Array<Double>, precision: Double = DEFAULT_PRECISION_DELTA): Boolean {
        if (value.size != other.size) {
            return false
        }
        val delta = if (precision.isFinite()) precision.abs() else DEFAULT_PRECISION_DELTA
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
    fun closeEnough(value: Array<DoubleArray>, other: Array<DoubleArray>, precision: Double = DEFAULT_PRECISION_DELTA): Boolean {
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
    fun closeEnough(value: Array<Array<Double>>, other: Array<Array<Double>>, precision: Double = DEFAULT_PRECISION_DELTA): Boolean {
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
    fun isPrimeValue(value: Int): Boolean = Primes.isPrime(value)

    @JvmStatic
    fun toPrimeAfter(value: Int): Int = Primes.nextPrime(value.coerceAtLeast(1))

    @JvmStatic
    fun toPrimeRoots(value: Int): List<Int> = if (value < 2) emptyList() else Primes.primeFactors(value)

    @JvmStatic
    fun toDoubleArray(list: IntArray): DoubleArray = DoubleArray(list.size) { i -> list[i].toDouble() }

    @JvmStatic
    fun toDoubleArray(list: ByteArray): DoubleArray = DoubleArray(list.size) { i -> list[i].toDouble() }

    @JvmStatic
    fun toDoubleArray(list: CharArray): DoubleArray = DoubleArray(list.size) { i -> list[i].toDouble() }

    @JvmStatic
    fun toDoubleArray(list: LongArray): DoubleArray = DoubleArray(list.size) { i -> list[i].toDouble() }

    @JvmStatic
    fun toDoubleArray(list: ShortArray): DoubleArray = DoubleArray(list.size) { i -> list[i].toDouble() }

    @JvmStatic
    fun toDoubleArray(list: FloatArray): DoubleArray = DoubleArray(list.size) { i -> list[i].toDouble() }

    @JvmStatic
    fun toDoubleArray(vararg list: Number): DoubleArray = DoubleArray(list.size) { i -> list[i].toDouble() }

    @JvmStatic
    fun toDoubleArray(list: Array<Int>): DoubleArray = DoubleArray(list.size) { i -> list[i].toDouble() }

    @JvmStatic
    fun toDoubleArray(list: Array<Byte>): DoubleArray = DoubleArray(list.size) { i -> list[i].toDouble() }

    @JvmStatic
    fun toDoubleArray(list: Array<Char>): DoubleArray = DoubleArray(list.size) { i -> list[i].toDouble() }

    @JvmStatic
    fun toDoubleArray(list: Array<Long>): DoubleArray = DoubleArray(list.size) { i -> list[i].toDouble() }

    @JvmStatic
    fun toDoubleArray(list: Array<Short>): DoubleArray = DoubleArray(list.size) { i -> list[i].toDouble() }

    @JvmStatic
    fun toDoubleArray(list: Array<Float>): DoubleArray = DoubleArray(list.size) { i -> list[i].toDouble() }

    @JvmStatic
    fun toDoubleArray(list: Array<Double>): DoubleArray = DoubleArray(list.size) { i -> list[i] }

    @JvmStatic
    fun <N : Number> toDoubleArray(list: Iterable<N>): DoubleArray = list.toList().let { DoubleArray(it.size) { i -> it[i].toDouble() } }

    @JvmStatic
    fun <N : Number> toDoubleArray(list: Sequence<N>): DoubleArray = list.toList().let { DoubleArray(it.size) { i -> it[i].toDouble() } }

    @JvmStatic
    fun mean(list: DoubleArray): Double = StatUtils.mean(list)

    @JvmStatic
    fun mean(list: IntArray): Double = mean(toDoubleArray(list))

    @JvmStatic
    fun mean(list: ByteArray): Double = mean(toDoubleArray(list))

    @JvmStatic
    fun mean(list: CharArray): Double = mean(toDoubleArray(list))

    @JvmStatic
    fun mean(list: LongArray): Double = mean(toDoubleArray(list))

    @JvmStatic
    fun mean(list: ShortArray): Double = mean(toDoubleArray(list))

    @JvmStatic
    fun mean(list: FloatArray): Double = mean(toDoubleArray(list))

    @JvmStatic
    fun mean(vararg list: Number): Double = mean(toDoubleArray(*list))

    @JvmStatic
    fun mean(list: Array<Int>): Double = mean(toDoubleArray(list))

    @JvmStatic
    fun mean(list: Array<Byte>): Double = mean(toDoubleArray(list))

    @JvmStatic
    fun mean(list: Array<Char>): Double = mean(toDoubleArray(list))

    @JvmStatic
    fun mean(list: Array<Long>): Double = mean(toDoubleArray(list))

    @JvmStatic
    fun mean(list: Array<Short>): Double = mean(toDoubleArray(list))

    @JvmStatic
    fun mean(list: Array<Float>): Double = mean(toDoubleArray(list))

    @JvmStatic
    fun mean(list: Array<Double>): Double = mean(toDoubleArray(list))

    @JvmStatic
    fun <N : Number> mean(list: Iterable<N>): Double = mean(toDoubleArray(list))

    @JvmStatic
    fun <N : Number> mean(list: Sequence<N>): Double = mean(toDoubleArray(list))

    @JvmStatic
    fun median(list: DoubleArray): Double = percentile(50.0, list)

    @JvmStatic
    fun median(list: IntArray): Double = median(toDoubleArray(list))

    @JvmStatic
    fun median(list: ByteArray): Double = median(toDoubleArray(list))

    @JvmStatic
    fun median(list: CharArray): Double = median(toDoubleArray(list))

    @JvmStatic
    fun median(list: LongArray): Double = median(toDoubleArray(list))

    @JvmStatic
    fun median(list: ShortArray): Double = median(toDoubleArray(list))

    @JvmStatic
    fun median(list: FloatArray): Double = median(toDoubleArray(list))

    @JvmStatic
    fun median(vararg list: Number): Double = median(toDoubleArray(*list))

    @JvmStatic
    fun median(list: Array<Int>): Double = median(toDoubleArray(list))

    @JvmStatic
    fun median(list: Array<Byte>): Double = median(toDoubleArray(list))

    @JvmStatic
    fun median(list: Array<Char>): Double = median(toDoubleArray(list))

    @JvmStatic
    fun median(list: Array<Long>): Double = median(toDoubleArray(list))

    @JvmStatic
    fun median(list: Array<Short>): Double = median(toDoubleArray(list))

    @JvmStatic
    fun median(list: Array<Float>): Double = median(toDoubleArray(list))

    @JvmStatic
    fun median(list: Array<Double>): Double = median(toDoubleArray(list))

    @JvmStatic
    fun <N : Number> median(list: Iterable<N>): Double = median(toDoubleArray(list))

    @JvmStatic
    fun <N : Number> median(list: Sequence<N>): Double = median(toDoubleArray(list))

    @JvmStatic
    fun variance(list: DoubleArray): Double = StatUtils.variance(list)

    @JvmStatic
    fun variance(list: IntArray): Double = variance(toDoubleArray(list))

    @JvmStatic
    fun variance(list: ByteArray): Double = variance(toDoubleArray(list))

    @JvmStatic
    fun variance(list: CharArray): Double = variance(toDoubleArray(list))

    @JvmStatic
    fun variance(list: LongArray): Double = variance(toDoubleArray(list))

    @JvmStatic
    fun variance(list: ShortArray): Double = variance(toDoubleArray(list))

    @JvmStatic
    fun variance(list: FloatArray): Double = variance(toDoubleArray(list))

    @JvmStatic
    fun variance(vararg list: Number): Double = variance(toDoubleArray(*list))

    @JvmStatic
    fun variance(list: Array<Int>): Double = variance(toDoubleArray(list))

    @JvmStatic
    fun variance(list: Array<Byte>): Double = variance(toDoubleArray(list))

    @JvmStatic
    fun variance(list: Array<Char>): Double = variance(toDoubleArray(list))

    @JvmStatic
    fun variance(list: Array<Long>): Double = variance(toDoubleArray(list))

    @JvmStatic
    fun variance(list: Array<Short>): Double = variance(toDoubleArray(list))

    @JvmStatic
    fun variance(list: Array<Float>): Double = variance(toDoubleArray(list))

    @JvmStatic
    fun variance(list: Array<Double>): Double = variance(toDoubleArray(list))

    @JvmStatic
    fun <N : Number> variance(list: Iterable<N>): Double = variance(toDoubleArray(list))

    @JvmStatic
    fun <N : Number> variance(list: Sequence<N>): Double = variance(toDoubleArray(list))

    @JvmStatic
    fun percentile(percentile: Double, list: DoubleArray): Double = StatUtils.percentile(list, percentile)

    @JvmStatic
    fun percentile(percentile: Double, list: IntArray): Double = percentile(percentile, toDoubleArray(list))

    @JvmStatic
    fun percentile(percentile: Double, list: ByteArray): Double = percentile(percentile, toDoubleArray(list))

    @JvmStatic
    fun percentile(percentile: Double, list: CharArray): Double = percentile(percentile, toDoubleArray(list))

    @JvmStatic
    fun percentile(percentile: Double, list: LongArray): Double = percentile(percentile, toDoubleArray(list))

    @JvmStatic
    fun percentile(percentile: Double, list: ShortArray): Double = percentile(percentile, toDoubleArray(list))

    @JvmStatic
    fun percentile(percentile: Double, list: FloatArray): Double = percentile(percentile, toDoubleArray(list))

    @JvmStatic
    fun percentile(percentile: Double, vararg list: Number): Double = percentile(percentile, toDoubleArray(*list))

    @JvmStatic
    fun percentile(percentile: Double, list: Array<Int>): Double = percentile(percentile, toDoubleArray(list))

    @JvmStatic
    fun percentile(percentile: Double, list: Array<Byte>): Double = percentile(percentile, toDoubleArray(list))

    @JvmStatic
    fun percentile(percentile: Double, list: Array<Char>): Double = percentile(percentile, toDoubleArray(list))

    @JvmStatic
    fun percentile(percentile: Double, list: Array<Long>): Double = percentile(percentile, toDoubleArray(list))

    @JvmStatic
    fun percentile(percentile: Double, list: Array<Short>): Double = percentile(percentile, toDoubleArray(list))

    @JvmStatic
    fun percentile(percentile: Double, list: Array<Float>): Double = percentile(percentile, toDoubleArray(list))

    @JvmStatic
    fun percentile(percentile: Double, list: Array<Double>): Double = percentile(percentile, toDoubleArray(list))

    @JvmStatic
    fun <N : Number> percentile(percentile: Double, list: Iterable<N>): Double = percentile(percentile, toDoubleArray(list))

    @JvmStatic
    fun <N : Number> percentile(percentile: Double, list: Sequence<N>): Double = percentile(percentile, toDoubleArray(list))

    @JvmStatic
    fun powNegative1(value: Int): Double = powNegative1(value.toLong())

    @JvmStatic
    fun powNegative1(value: Long): Double = if (value % 2 == 0L) 1.0 else -1.0

    @JvmStatic
    fun powNegative1(value: Int, other: Int): Double = powNegative1(value.toLong() + other)

    @JvmStatic
    fun gcd(value: Int, other: Int): Int = if (other == 0) abs(value) else TailRecursiveFunctions.gcd(value, other)

    @JvmStatic
    fun gcd(value: Long, other: Long): Long = if (other == 0L) abs(value) else TailRecursiveFunctions.gcd(value, other)

    @JvmStatic
    fun gcd(args: IntArray): Int = TailRecursiveFunctions.gcd(args)

    @JvmStatic
    fun gcd(args: Iterable<Int>): Int = TailRecursiveFunctions.gcd(args)

    @JvmStatic
    fun gcd(args: Sequence<Int>): Int = TailRecursiveFunctions.gcd(args)

    @JvmStatic
    fun gcd(args: LongArray): Long = TailRecursiveFunctions.gcd(args)

    @JvmStatic
    fun gcd(args: Iterable<Long>): Long = TailRecursiveFunctions.gcd(args)

    @JvmStatic
    fun gcd(args: Sequence<Long>): Long = TailRecursiveFunctions.gcd(args)

    @JvmStatic
    fun lcm(value: Int, other: Int): Int = TailRecursiveFunctions.lcm(value, other)

    @JvmStatic
    fun lcm(value: Long, other: Long): Long = TailRecursiveFunctions.lcm(value, other)

    @JvmStatic
    fun lcm(args: IntArray): Int = TailRecursiveFunctions.lcm(args)

    @JvmStatic
    fun lcm(args: LongArray): Long = TailRecursiveFunctions.lcm(args)

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
            val round = value.toBigDecimal().setScale(abs(scale), RoundingMode.HALF_UP).toDouble()
            if (round == 0.0) round * 0.0 else round
        }
        catch (cause: Throwable) {
            Throwables.thrown(cause)
            if (value.isInfinite()) value else Double.NaN
        }
    }

    @JvmStatic
    @JvmOverloads
    fun rounded(args: DoubleArray, scale: Int = 3): DoubleArray {
        return DoubleArray(args.size) {
            rounded(args[it], scale)
        }
    }

    @JvmStatic
    @JvmOverloads
    fun rounded(args: Array<Double>, scale: Int = 3): Array<Double> {
        return Array(args.size) {
            rounded(args[it], scale)
        }
    }

    @JvmStatic
    @JvmOverloads
    fun rounded(args: Array<DoubleArray>, scale: Int = 3): Array<DoubleArray> {
        return Array(args.size) {
            rounded(args[it], scale)
        }
    }

    @JvmStatic
    @JvmOverloads
    fun rounded(args: Array<Array<Double>>, scale: Int = 3): Array<Array<Double>> {
        return Array(args.size) {
            rounded(args[it], scale)
        }
    }

    @JvmStatic
    @JvmOverloads
    fun rounded(args: Sequence<Double>, scale: Int = 3): Sequence<Double> {
        return args.map {
            rounded(it, scale)
        }
    }

    @JvmStatic
    @JvmOverloads
    fun rounded(args: Iterable<Double>, scale: Int = 3): Iterable<Double> {
        return args.map {
            rounded(it, scale)
        }
    }

    private object TailRecursiveFunctions {

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
                0 -> throw MercenaryFatalExceptiion(MATH_INVALID_SIZE_ERROR)
                1 -> abs(args[0])
                2 -> gcd(args[0], args[1])
                else -> args.reduce { x, y ->
                    gcd(x, y)
                }
            }
        }

        fun gcd(args: Iterable<Int>): Int {
            val list = args.toList()
            return when (list.size) {
                0 -> throw MercenaryFatalExceptiion(MATH_INVALID_SIZE_ERROR)
                1 -> abs(list[0])
                2 -> gcd(list[0], list[1])
                else -> list.reduce { x, y ->
                    gcd(x, y)
                }
            }
        }

        fun gcd(args: Sequence<Int>): Int {
            val list = args.toList()
            return when (list.size) {
                0 -> throw MercenaryFatalExceptiion(MATH_INVALID_SIZE_ERROR)
                1 -> abs(list[0])
                2 -> gcd(list[0], list[1])
                else -> list.reduce { x, y ->
                    gcd(x, y)
                }
            }
        }

        fun gcd(args: LongArray): Long {
            return when (args.size) {
                0 -> throw MercenaryFatalExceptiion(MATH_INVALID_SIZE_ERROR)
                1 -> abs(args[0])
                2 -> gcd(args[0], args[1])
                else -> args.reduce { x, y ->
                    gcd(x, y)
                }
            }
        }

        fun gcd(args: Iterable<Long>): Long {
            val list = args.toList()
            return when (list.size) {
                0 -> throw MercenaryFatalExceptiion(MATH_INVALID_SIZE_ERROR)
                1 -> abs(list[0])
                2 -> gcd(list[0], list[1])
                else -> list.reduce { x, y ->
                    gcd(x, y)
                }
            }
        }

        fun gcd(args: Sequence<Long>): Long {
            val list = args.toList()
            return when (list.size) {
                0 -> throw MercenaryFatalExceptiion(MATH_INVALID_SIZE_ERROR)
                1 -> abs(list[0])
                2 -> gcd(list[0], list[1])
                else -> list.reduce { x, y ->
                    gcd(x, y)
                }
            }
        }

        fun lcm(value: Int, other: Int): Int {
            return when (val gcd = gcd(value, other)) {
                0 -> throw MercenaryFatalExceptiion(MATH_ZERO_DIVISOR_ERROR)
                else -> (value * other) / gcd
            }
        }

        fun lcm(value: Long, other: Long): Long {
            return when (val gcd = gcd(value, other)) {
                0L -> throw MercenaryFatalExceptiion(MATH_ZERO_DIVISOR_ERROR)
                else -> (value * other) / gcd
            }
        }

        fun lcm(args: IntArray): Int {
            return when (args.size) {
                0 -> throw MercenaryFatalExceptiion(MATH_INVALID_SIZE_ERROR)
                1 -> abs(args[0])
                2 -> lcm(args[0], args[1])
                else -> args.reduce { x, y ->
                    when (val gcd = gcd(x, y)) {
                        0 -> throw MercenaryFatalExceptiion(MATH_ZERO_DIVISOR_ERROR)
                        else -> x * (y / gcd)
                    }
                }
            }
        }

        fun lcm(args: LongArray): Long {
            return when (args.size) {
                0 -> throw MercenaryFatalExceptiion(MATH_INVALID_SIZE_ERROR)
                1 -> abs(args[0])
                2 -> lcm(args[0], args[1])
                else -> args.reduce { x, y ->
                    when (val gcd = gcd(x, y)) {
                        0L -> throw MercenaryFatalExceptiion(MATH_ZERO_DIVISOR_ERROR)
                        else -> x * (y / gcd)
                    }
                }
            }
        }
    }
}