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

package co.mercenary.creators.kotlin.util.security

import co.mercenary.creators.kotlin.util.*
import java.security.SecureRandom
import java.util.*
import kotlin.random.*

object Randoms {

    private fun SecureRandom.seedTo(seed: Long): SecureRandom {
        if (seed != 0L) {
            setSeed(seed)
        }
        return this
    }

    private val VALUES: CharArray by lazy {
        getCharArrayValuesInternal()
    }

    private val RANDOM: SecureRandom by lazy {
        SecureRandom()
    }

    private val KOTLIN: kotlin.random.Random by lazy {
        RANDOM.asKotlinRandom()
    }

    private val random: SecureRandom
        get() = RANDOM

    private val kotlin: kotlin.random.Random
        get() = KOTLIN

    @JvmStatic
    fun uuid() = UUID.randomUUID().toString()

    @JvmStatic
    fun randomOf() = SecureRandom()

    @JvmStatic
    fun randomOf(seed: Long) = randomOf().seedTo(seed)

    @JvmStatic
    fun randomOf(seed: ByteArray) = SecureRandom(seed)

    @JvmStatic
    fun getInteger() = random.nextInt()

    @JvmStatic
    fun getInteger(bound: Int) = random.nextInt(bound)

    @JvmStatic
    fun getDouble() = random.nextDouble()

    @JvmStatic
    fun getBoolean() = random.nextBoolean()

    @JvmStatic
    fun getLongValue() = random.nextLong()

    @JvmStatic
    fun getByteArray(sized: Int) = getByteArray(ByteArray(sized))

    @JvmStatic
    fun getByteArray(bytes: ByteArray) = getByteArray(random, bytes)

    @JvmStatic
    fun getByteArray(random: SecureRandom, sized: Int) = getByteArray(random, ByteArray(sized))

    @JvmStatic
    fun getByteArray(random: SecureRandom, bytes: ByteArray) = bytes.also { random.nextBytes(it) }

    @JvmStatic
    fun getInteger(range: IntRange) = kotlin.nextInt(range)

    @JvmStatic
    fun getInteger(lower: Int, upper: Int) = kotlin.nextInt(lower, upper)

    @JvmStatic
    fun getLongValue(bound: Long) = kotlin.nextLong(bound)

    @JvmStatic
    fun getLongValue(range: LongRange) = kotlin.nextLong(range)

    @JvmStatic
    fun getLongValue(lower: Int, upper: Int) = kotlin.nextLong(lower.toLong(), upper.toLong())

    @JvmStatic
    fun getLongValue(lower: Long, upper: Long) = kotlin.nextLong(lower, upper)

    @JvmStatic
    fun getDouble(bound: Double) = kotlin.nextDouble(bound)

    @JvmStatic
    fun getDouble(lower: Double, upper: Double) = kotlin.nextDouble(lower, upper)

    @JvmStatic
    fun getLongSequence(sized: Int): Sequence<Long> {
        return getLongSequence(sized.toLong())
    }

    @JvmStatic
    fun getLongSequence(sized: Long): Sequence<Long> {
        return if (sized < 1L) MercenarySequence() else MercenarySequence(random.longs(sized).iterator())
    }

    @JvmStatic
    fun getLongSequence(sized: Int, lower: Long, upper: Long): Sequence<Long> {
        return getLongSequence(sized.toLong(), lower, upper)
    }

    @JvmStatic
    fun getLongSequence(sized: Long, lower: Long, upper: Long): Sequence<Long> {
        return if (sized < 1L) MercenarySequence() else MercenarySequence(random.longs(sized, lower, upper).iterator())
    }

    @JvmStatic
    fun getIntegerSequence(sized: Int): Sequence<Int> {
        return getIntegerSequence(sized.toLong())
    }

    @JvmStatic
    fun getIntegerSequence(sized: Long): Sequence<Int> {
        return if (sized < 1L) MercenarySequence() else MercenarySequence(random.ints(sized).iterator())
    }

    @JvmStatic
    fun getIntegerSequence(sized: Int, lower: Int, upper: Int): Sequence<Int> {
        return getIntegerSequence(sized.toLong(), lower, upper)
    }

    @JvmStatic
    fun getIntegerSequence(sized: Long, lower: Int, upper: Int): Sequence<Int> {
        return if (sized < 1L) MercenarySequence() else MercenarySequence(random.ints(sized, lower, upper).iterator())
    }

    @JvmStatic
    fun getDoubleSequence(sized: Int): Sequence<Double> {
        return getDoubleSequence(sized.toLong())
    }

    @JvmStatic
    fun getDoubleSequence(sized: Long): Sequence<Double> {
        return if (sized < 1L) MercenarySequence() else MercenarySequence(random.doubles(sized).iterator())
    }

    @JvmStatic
    fun getDoubleSequence(sized: Int, lower: Double, upper: Double): Sequence<Double> {
        return getDoubleSequence(sized.toLong(), lower, upper)
    }

    @JvmStatic
    fun getDoubleSequence(sized: Long, lower: Double, upper: Double): Sequence<Double> {
        return if (sized < 1L) MercenarySequence() else MercenarySequence(random.doubles(sized, lower, upper).iterator())
    }

    @JvmStatic
    fun getBits(count: Int) = kotlin.nextBits(count)

    @JvmStatic
    fun getString(sized: Int): String = getString(sized, VALUES)

    @JvmStatic
    fun getString(sized: Int, chars: CharArray): String = getCharSequenceOf(sized, chars).toString()

    @JvmStatic
    fun getString(sized: Int, chars: CharSequence): String = getCharSequenceOf(sized, chars).toString()

    @JvmStatic
    fun getCharSequenceOf(sized: Int): CharSequence = getCharSequenceOf(sized, VALUES)

    @JvmStatic
    fun getCharSequenceOf(sized: Int, chars: CharSequence): CharSequence = getCharSequenceOf(sized, chars.toString().toCharArray())

    @JvmStatic
    fun getCharSequenceOf(sized: Int, chars: CharArray): CharSequence {
        if (sized < 1) {
            return EMPTY_STRING
        }
        if (chars.isEmpty()) {
            throw throw MercenaryFatalExceptiion(MATH_INVALID_SIZE_ERROR)
        }
        val rand = random
        return buildString(sized) {
            repeat(sized) {
                append(chars[rand.nextInt(chars.size)])
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
            ('0'..'9').forEach {
                append(it)
            }
            ('A'..'Z').forEach {
                append(it)
            }
            ('0'..'9').forEach {
                append(it)
            }
        }.toCharArray()
    }
}