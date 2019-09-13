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
    fun check(precision: Double): Double = if (precision.isNaN().or(precision.isInfinite())) DEFAULT_PRECISION else precision

    @JvmStatic
    fun delta(value: Double, other: Double, precision: Double = DEFAULT_PRECISION): Boolean {
        return if (value.toBits() != other.toBits()) (abs(value - other) <= abs(precision)) else true
    }

    @JvmStatic
    fun rounded(value: Double, scale: Int = 2): Double {
        try {
            val round = BigDecimal(value.toString()).setScale(scale, RoundingMode.HALF_UP).toDouble()
            return if (round == 0.0) round * 0.0 else round
        }
        catch (cause: Throwable) {
            Throwables.assert(cause)
        }
        return if (value.isInfinite()) value else Double.NaN
    }
}