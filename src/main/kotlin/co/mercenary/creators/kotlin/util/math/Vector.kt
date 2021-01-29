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

class Vector @FrameworkDsl internal constructor(base: DoubleArray, copy: Boolean = true, private val head: Int = ZERO, private val tail: Int = base.sizeOf(), private val subs: Boolean) : IVector<Vector> {

    @FrameworkDsl
    constructor(size: Int) : this(size.toDoubleArray(), false)

    @FrameworkDsl
    constructor(base: Vector) : this(base.data, true, base.headOf(), base.tailOf())

    @FrameworkDsl
    constructor(size: Int, init: VectorInitializer) : this(init.initialize(size), false)

    @JvmOverloads
    @FrameworkDsl
    constructor(base: DoubleArray, copy: Boolean = true, head: Int = ZERO, tail: Int = base.sizeOf()) : this(base, copy, head, tail, false)

    @FrameworkDsl
    internal val data = base.toDoubleArray(copy)

    init {
        if (headOf().isNegative() || headOf().isLessThan(tailOf())) {
            throw MercenaryMathExceptiion("Vector.headOf(${headOf()})")
        }
        if (tailOf().isNegative() || tailOf().isMoreThan(data.sizeOf())) {
            throw MercenaryMathExceptiion("Vector.tailOf(${tailOf()})")
        }
    }

    @FrameworkDsl
    override fun headOf(): Int = head.copyOf()

    @FrameworkDsl
    override fun tailOf(): Int = tail.copyOf()

    @FrameworkDsl
    @IgnoreForSerialize
    override fun isSlice(): Boolean = subs.isTrue()

    @FrameworkDsl
    internal fun offsetOf(index: Int): Int {
        return (index + headOf()).boxIn(0, data.sizeOf())
    }

    @FrameworkDsl
    override operator fun get(index: Int): Double {
        return data[offsetOf(index)]
    }

    @FrameworkDsl
    override operator fun set(index: Int, value: Double) {
        data[offsetOf(index)] = value
    }

    @FrameworkDsl
    override fun clone() = copyOf()

    @FrameworkDsl
    override fun copyOf() = Vector(this)

    @FrameworkDsl
    override fun isEmpty(): Boolean {
        return data.isExhausted() || sizeOf().isExhausted()
    }

    @FrameworkDsl
    override fun average(default: Double): Double {
        if (isNotSlice()) {
            return data.average(default)
        }
        return default
    }

    @FrameworkDsl
    override fun average(index: Int, place: Int, default: Double): Double {
        if (isNotSlice()) {
            return data.average(index, place, default)
        }
        TODO("Not yet implemented")
    }

    @FrameworkDsl
    override fun sort(index: Int, place: Int, descending: Boolean): Vector {
        if (data.sizeOf() > 1) {
            if (isNotSlice()) {
                data.sort(index, place)
                if (descending.isTrue()) {
                    data.reverse(index, place)
                }
            }
        }
        return this
    }

    @FrameworkDsl
    override fun reverse(): Vector {
        if (data.sizeOf() > 1) {
            if (isNotSlice()) {
                data.reverse()
            }
        }
        return this
    }

    @FrameworkDsl
    override fun reverse(index: Int, place: Int): Vector {
        if (data.sizeOf() > 1) {
            if (isNotSlice()) {
                if (data.sizeOf().isNotValidRange(index, place)) {
                    throw MercenaryMathExceptiion("reverse().isNotValidRange()")
                }
                data.reverse(index, place)
            } else {
                data.reverse(index, place)
            }
        }
        return this
    }

    @FrameworkDsl
    override fun slice(index: Int, place: Int): Vector {
        return Vector(data, false, index, place, true)
    }

    @FrameworkDsl
    override fun reduce(reducer: (Double, Double) -> Double): Double {
        if (data.isExhausted()) {
            throw MercenaryMathExceptiion("reduce().isExhausted()")
        }
        if (isNotSlice()) {
            return data.reduce(reducer)
        }
        var keep = get(0)
        for (look in 1..lastIndex) {
            keep = reducer(keep, get(look))
        }
        return keep
    }

    @FrameworkDsl
    override fun reduce(index: Int, place: Int, reducer: (Double, Double) -> Double): Double {
        if (data.isExhausted()) {
            throw MercenaryMathExceptiion("reduce().isExhausted()")
        }
        if (data.sizeOf().isNotValidRange(index, place)) {
            throw MercenaryMathExceptiion("reduce().isNotValidRange()")
        }
        return 0.0
    }

    @FrameworkDsl
    override fun hashCode() = hashAs(this, data)

    @FrameworkDsl
    override fun equals(other: Any?) = when (other) {
        is Vector -> this === other || sameAs(this, other)
        else -> false
    }

    companion object {

        @FrameworkDsl
        private const val ZERO = 0

        @JvmStatic
        @FrameworkDsl
        internal fun sameAs(self: Vector, base: Vector): Boolean {
            if (self.headOf() != base.headOf()) {
                return false
            }
            if (self.tailOf() != base.tailOf()) {
                return false
            }
            if (self.sizeOf() != base.sizeOf()) {
                return false
            }
            if (self.isEmpty() != base.isEmpty()) {
                return false
            }
            if (self.isSlice() != base.isSlice()) {
                return false
            }
            if (self.isEmpty()) {
                return false
            }
            for (index in self.indices) {
                if (self[index] != base[index]) {
                    return false
                }
            }
            return true
        }

        @JvmStatic
        @FrameworkDsl
        internal fun hashAs(self: Vector, base: DoubleArray): Int {
            if (self.isEmpty() || base.isExhausted()) {
                return HASH_BASE_VALUE
            }
            if (self.isNotSlice()) {
                base.hashOf()
            }
            var hash = HASH_BASE_VALUE
            for (index in self.indices) {
                hash = hash * HASH_NEXT_VALUE + self[index].hashOf()
            }
            return hash
        }
    }
}