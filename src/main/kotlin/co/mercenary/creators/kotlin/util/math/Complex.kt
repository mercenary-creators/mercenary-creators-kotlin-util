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
import org.apache.commons.math3.complex.Complex as Proxy

class Complex internal constructor(private val proxy: Proxy) : RealNumber<Complex> {

    constructor(value: Complex) : this(Proxy(value.real, value.imaginary))

    constructor(value: Polar2D) : this(value.toCartesianCoordinates())

    constructor(value: Coordinates) : this(value.toComplex())

    @JvmOverloads
    constructor(real: Double, imaginary: Double = 0.0) : this(Proxy(real, imaginary))

    val real: Double
        get() = proxy.real

    val imaginary: Double
        get() = proxy.imaginary

    fun toPolarCoordinates() = Numeric.toPolarCoordinates(this)

    fun toCartesianCoordinates() = Numeric.toCartesianCoordinates(this)

    override operator fun div(value: Complex): Complex {
        return proxy.divide(value.proxy).toComplex()
    }

    override operator fun div(value: Double): Complex {
        if (value.isFinite()) {
            return when (value) {
                1.0 -> copyOf()
                0.0 -> throw MercenaryFatalExceptiion(MATH_ZERO_DIVISOR_ERROR)
                else -> proxy.divide(value).toComplex()
            }
        }
        throw MercenaryFatalExceptiion("invalid value $value")
    }

    override operator fun plus(value: Complex): Complex {
        return proxy.add(value.proxy).toComplex()
    }

    override operator fun plus(value: Double): Complex {
        if (value.isFinite()) {
            return proxy.add(value).toComplex()
        }
        throw MercenaryFatalExceptiion("invalid value $value")
    }

    override operator fun minus(value: Complex): Complex {
        return proxy.subtract(value.proxy).toComplex()
    }

    override operator fun minus(value: Double): Complex {
        if (value.isFinite()) {
            return proxy.subtract(value).toComplex()
        }
        throw MercenaryFatalExceptiion("invalid value $value")
    }

    override operator fun times(value: Complex): Complex {
        return proxy.multiply(value.proxy).toComplex()
    }

    override operator fun times(value: Double): Complex {
        if (value.isFinite()) {
            return when (value) {
                1.0 -> copyOf()
                0.0 -> Complex(0.0, 0.0)
                else -> proxy.multiply(value).toComplex()
            }
        }
        throw MercenaryFatalExceptiion("invalid value $value")
    }

    override operator fun unaryPlus(): Complex {
        return Complex(real.abs(), imaginary.abs())
    }

    override operator fun unaryMinus(): Complex {
        return proxy.negate().toComplex()
    }

    override fun clone() = copyOf()

    override fun copyOf() = Complex(real, imaginary)

    override fun toString() = toMapNames().toString()

    override fun hashCode() = proxy.hashCode()

    override fun toMapNames() = mapOf("real" to real, "imaginary" to imaginary)

    override fun equals(other: Any?) = when (other) {
        is Complex -> this === other || proxy == other.proxy
        else -> false
    }

    companion object {

        val ZERO = Complex(0.0, 0.0)

        val UNIT = Complex(1.0, 0.0)

        private fun Proxy.toComplex() = Complex(this)
    }
}