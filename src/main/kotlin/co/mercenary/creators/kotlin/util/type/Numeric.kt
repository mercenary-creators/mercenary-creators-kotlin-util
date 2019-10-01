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

package co.mercenary.creators.kotlin.util.type

import java.math.*
import kotlin.math.abs

object Numeric {

    const val DEFAULT_PRECISION = 0.0000001

    @JvmStatic
    @JvmOverloads
    fun toDecimalPlaces(data: Double, scale: Int = 2, places: Int = abs(scale)): String {
        return "%.${places}f".format(rounded(data, scale))
    }

    @JvmStatic
    @JvmOverloads
    fun closeEnough(value: Double, other: Double, precision: Double = DEFAULT_PRECISION): Boolean {
        val delta = if (precision.isNaN().or(precision.isInfinite())) DEFAULT_PRECISION else abs(precision)
        return if (value.toBits() != other.toBits()) (abs(value - other) <= delta) else true
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
        return closeEnough(value.toDoubleArray(), other.toDoubleArray(), precision)
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
    @JvmOverloads
    fun rounded(value: Double, scale: Int = 2): Double {
        try {
            val round = BigDecimal(value.toString()).setScale(abs(scale), RoundingMode.HALF_UP).toDouble()
            return if (round == 0.0) round * 0.0 else round
        }
        catch (cause: Throwable) {
            Throwables.thrown(cause)
        }
        return if (value.isInfinite()) value else Double.NaN
    }

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
}