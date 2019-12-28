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

package co.mercenary.creators.kotlin.util.security

import co.mercenary.creators.kotlin.util.*
import java.security.SecureRandom
import kotlin.random.*

object Randoms {

    private val VALUES: CharArray by lazy {
        getCharArrayValuesInternal()
    }

    private val RANDOM: SecureRandom by lazy {
        toRandom()
    }

    @JvmStatic
    fun getRandom() = RANDOM

    @JvmStatic
    fun toRandom() = SecureRandom()

    @JvmStatic
    fun getInteger() = getRandom().nextInt()

    @JvmStatic
    fun getInteger(bound: Int) = getRandom().nextInt(bound)

    @JvmStatic
    fun getDouble() = getRandom().nextDouble()

    @JvmStatic
    fun getBoolean() = getRandom().nextBoolean()

    @JvmStatic
    fun getLongValue() = getRandom().nextLong()

    @JvmStatic
    fun getGaussian() = getRandom().nextGaussian()

    @JvmStatic
    fun getByteArray(sized: Int) = getByteArray(ByteArray(sized))

    @JvmStatic
    fun getByteArray(bytes: ByteArray) = getByteArray(getRandom(), bytes)

    @JvmStatic
    fun getByteArray(random: SecureRandom, sized: Int) = getByteArray(random, ByteArray(sized))

    @JvmStatic
    fun getByteArray(random: SecureRandom, bytes: ByteArray) = bytes.also { random.nextBytes(it) }

    @JvmStatic
    fun getInteger(range: IntRange) = getRandom().asKotlinRandom().nextInt(range)

    @JvmStatic
    fun getInteger(lower: Int, upper: Int) = getRandom().asKotlinRandom().nextInt(lower, upper)

    @JvmStatic
    fun getLongValue(bound: Long) = getRandom().asKotlinRandom().nextLong(bound)

    @JvmStatic
    fun getLongValue(range: LongRange) = getRandom().asKotlinRandom().nextLong(range)

    @JvmStatic
    fun getLongValue(lower: Int, upper: Int) = getRandom().asKotlinRandom().nextLong(lower.toLong(), upper.toLong())

    @JvmStatic
    fun getLongValue(lower: Long, upper: Long) = getRandom().asKotlinRandom().nextLong(lower, upper)

    @JvmStatic
    fun getDouble(bound: Double) = getRandom().asKotlinRandom().nextDouble(bound)

    @JvmStatic
    fun getDouble(lower: Double, upper: Double) = getRandom().asKotlinRandom().nextDouble(lower, upper)

    @JvmStatic
    fun getLongSequence(sized: Int): Sequence<Long> {
        return getLongSequence(sized.toLong())
    }

    @JvmStatic
    @JvmOverloads
    fun getLongSequence(sized: Long = Long.MAX_VALUE): Sequence<Long> {
        return MercenarySequence(getRandom().longs(sized).iterator())
    }

    @JvmStatic
    fun getLongSequence(lower: Long, upper: Long): Sequence<Long> {
        return getLongSequence(Long.MAX_VALUE, lower, upper)
    }

    @JvmStatic
    fun getLongSequence(sized: Int, lower: Long, upper: Long): Sequence<Long> {
        return getLongSequence(sized.toLong(), lower, upper)
    }

    @JvmStatic
    fun getLongSequence(sized: Long, lower: Long, upper: Long): Sequence<Long> {
        return MercenarySequence(getRandom().longs(sized, lower, upper).iterator())
    }

    @JvmStatic
    fun getIntegerSequence(sized: Int): Sequence<Int> {
        return getIntegerSequence(sized.toLong())
    }

    @JvmStatic
    @JvmOverloads
    fun getIntegerSequence(sized: Long = Long.MAX_VALUE): Sequence<Int> {
        return MercenarySequence(getRandom().ints(sized).iterator())
    }

    @JvmStatic
    fun getIntegerSequence(lower: Int, upper: Int): Sequence<Int> {
        return getIntegerSequence(Long.MAX_VALUE, lower, upper)
    }

    @JvmStatic
    fun getIntegerSequence(sized: Int, lower: Int, upper: Int): Sequence<Int> {
        return getIntegerSequence(sized.toLong(), lower, upper)
    }

    @JvmStatic
    fun getIntegerSequence(sized: Long, lower: Int, upper: Int): Sequence<Int> {
        return MercenarySequence(getRandom().ints(sized, lower, upper).iterator())
    }

    @JvmStatic
    fun getDoubleSequence(sized: Int): Sequence<Double> {
        return getDoubleSequence(sized.toLong())
    }

    @JvmStatic
    @JvmOverloads
    fun getDoubleSequence(sized: Long = Long.MAX_VALUE): Sequence<Double> {
        return MercenarySequence(getRandom().doubles(sized).iterator())
    }

    @JvmStatic
    fun getDoubleSequence(lower: Double, upper: Double): Sequence<Double> {
        return getDoubleSequence(Long.MAX_VALUE, lower, upper)
    }

    @JvmStatic
    fun getDoubleSequence(sized: Int, lower: Double, upper: Double): Sequence<Double> {
        return getDoubleSequence(sized.toLong(), lower, upper)
    }

    @JvmStatic
    fun getDoubleSequence(sized: Long, lower: Double, upper: Double): Sequence<Double> {
        return MercenarySequence(getRandom().doubles(sized, lower, upper).iterator())
    }

    @JvmStatic
    fun getBits(count: Int) = getRandom().asKotlinRandom().nextBits(count)

    @JvmStatic
    fun getString(sized: Int): String = getString(sized, VALUES)

    @JvmStatic
    fun getString(sized: Int, chars: CharArray): String = getCharSequence(sized, chars).toString()

    @JvmStatic
    fun getString(sized: Int, chars: CharSequence): String = getCharSequence(sized, chars).toString()

    @JvmStatic
    fun getCharSequence(sized: Int): CharSequence = getCharSequence(sized, VALUES)

    @JvmStatic
    fun getCharSequence(sized: Int, chars: CharSequence): CharSequence = getCharSequence(sized, chars.toString().toCharArray())

    @JvmStatic
    fun getCharSequence(sized: Int, chars: CharArray): CharSequence {
        if (sized < 0) {
            throw MercenaryExceptiion("Illegal sized size $sized")
        }
        if (sized < 1) {
            return EMPTY_STRING
        }
        if (chars.isEmpty()) {
            throw MercenaryExceptiion("Illegal chars size 0")
        }
        return StringBuilder(sized).apply {
            repeat(sized) {
                append(chars[getInteger(chars.size)])
            }
        }
    }

    @JvmStatic
    fun getCharArrayValues(): CharArray = VALUES.copyOf()

    private fun getCharArrayValuesInternal(): CharArray {
        return buildString(72) {
            ('a'..'z').forEach {
                append(it)
            }
            for (c in '0'..'9') {
                append(c)
            }
            for (c in 'A'..'Z') {
                append(c)
            }
            for (c in '0'..'9') {
                append(c)
            }
        }.toCharArray()
    }
}