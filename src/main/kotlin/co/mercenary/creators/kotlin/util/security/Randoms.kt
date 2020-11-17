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

    @CreatorsDsl
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

    private val STRONG: SecureRandom by lazy {
        SecureRandom.getInstanceStrong()
    }

    private val KOTLIN: kotlin.random.Random by lazy {
        RANDOM.asKotlinRandom()
    }

    private val random: SecureRandom
        get() = RANDOM

    private val kotlin: kotlin.random.Random
        get() = KOTLIN

    @JvmStatic
    @CreatorsDsl
    @IgnoreForSerialize
    fun getAlgorithms(): Algorithm = Algorithm.forName("SecureRandom")

    @JvmStatic
    @CreatorsDsl
    fun uuid() = UUID.randomUUID().toString()

    @JvmStatic
    @CreatorsDsl
    fun randomOf() = SecureRandom()

    @JvmStatic
    @CreatorsDsl
    fun strongOf() = STRONG

    @JvmStatic
    @CreatorsDsl
    fun randomOf(seed: Long) = randomOf().seedTo(seed)

    @JvmStatic
    @CreatorsDsl
    fun randomOf(seed: ByteArray) = SecureRandom(seed)

    @JvmStatic
    @CreatorsDsl
    @IgnoreForSerialize
    fun getInteger() = random.nextInt()

    @JvmStatic
    @CreatorsDsl
    fun getInteger(bound: Int) = random.nextInt(bound)

    @JvmStatic
    @CreatorsDsl
    @IgnoreForSerialize
    fun getDouble() = random.nextDouble()

    @JvmStatic
    @CreatorsDsl
    @IgnoreForSerialize
    fun getGaussian() = random.nextGaussian()

    @JvmStatic
    @CreatorsDsl
    @IgnoreForSerialize
    fun getBoolean() = random.nextBoolean()

    @JvmStatic
    @CreatorsDsl
    @IgnoreForSerialize
    fun getLongValue() = random.nextLong()

    @JvmStatic
    @CreatorsDsl
    fun getByteArray(sized: Int) = getByteArray(ByteArray(sized))

    @JvmStatic
    @CreatorsDsl
    fun getByteArray(bytes: ByteArray) = getByteArray(random, bytes)

    @JvmStatic
    @CreatorsDsl
    fun getByteArray(random: SecureRandom, sized: Int) = getByteArray(random, ByteArray(sized))

    @JvmStatic
    @CreatorsDsl
    fun getByteArray(random: SecureRandom, bytes: ByteArray) = bytes.also { random.nextBytes(it) }

    @JvmStatic
    @CreatorsDsl
    fun getInteger(range: IntRange) = kotlin.nextInt(range)

    @JvmStatic
    @CreatorsDsl
    fun getInteger(lower: Int, upper: Int) = kotlin.nextInt(lower, upper)

    @JvmStatic
    @CreatorsDsl
    fun getLongValue(bound: Long) = kotlin.nextLong(bound)

    @JvmStatic
    @CreatorsDsl
    fun getLongValue(range: LongRange) = kotlin.nextLong(range)

    @JvmStatic
    @CreatorsDsl
    fun getLongValue(lower: Int, upper: Int) = kotlin.nextLong(lower.toLong(), upper.toLong())

    @JvmStatic
    @CreatorsDsl
    fun getLongValue(lower: Long, upper: Long) = kotlin.nextLong(lower, upper)

    @JvmStatic
    @CreatorsDsl
    fun getDouble(bound: Double) = kotlin.nextDouble(bound)

    @JvmStatic
    @CreatorsDsl
    fun getDouble(lower: Double, upper: Double) = kotlin.nextDouble(lower, upper)

    @JvmStatic
    @CreatorsDsl
    fun getLongSequence(sized: Int): Sequence<Long> {
        return getLongSequence(sized.toLong())
    }

    @JvmStatic
    @CreatorsDsl
    fun getLongSequence(sized: Long): Sequence<Long> {
        return if (sized < 1L) MercenarySequence() else random.longs(sized).toSequence()
    }

    @JvmStatic
    @CreatorsDsl
    fun getLongSequence(sized: Int, lower: Long, upper: Long): Sequence<Long> {
        return getLongSequence(sized.toLong(), lower, upper)
    }

    @JvmStatic
    @CreatorsDsl
    fun getLongSequence(sized: Long, lower: Long, upper: Long): Sequence<Long> {
        return if (sized < 1L) MercenarySequence() else random.longs(sized, lower, upper).toSequence()
    }

    @JvmStatic
    @CreatorsDsl
    fun getIntegerSequence(sized: Int): Sequence<Int> {
        return getIntegerSequence(sized.toLong())
    }

    @JvmStatic
    @CreatorsDsl
    fun getIntegerSequence(sized: Long): Sequence<Int> {
        return if (sized < 1L) MercenarySequence() else random.ints(sized).toSequence()
    }

    @JvmStatic
    @CreatorsDsl
    fun getIntegerSequence(sized: Int, lower: Int, upper: Int): Sequence<Int> {
        return getIntegerSequence(sized.toLong(), lower, upper)
    }

    @JvmStatic
    @CreatorsDsl
    fun getIntegerSequence(sized: Long, lower: Int, upper: Int): Sequence<Int> {
        return if (sized < 1L) MercenarySequence() else random.ints(sized, lower, upper).toSequence()
    }

    @JvmStatic
    @CreatorsDsl
    fun getDoubleSequence(sized: Int): Sequence<Double> {
        return getDoubleSequence(sized.toLong())
    }

    @JvmStatic
    @CreatorsDsl
    fun getDoubleSequence(sized: Long): Sequence<Double> {
        return if (sized < 1L) MercenarySequence() else random.doubles(sized).toSequence()
    }

    @JvmStatic
    @CreatorsDsl
    fun getDoubleSequence(sized: Int, lower: Double, upper: Double): Sequence<Double> {
        return getDoubleSequence(sized.toLong(), lower, upper)
    }

    @JvmStatic
    @CreatorsDsl
    fun getDoubleSequence(sized: Long, lower: Double, upper: Double): Sequence<Double> {
        return if (sized < 1L) MercenarySequence() else random.doubles(sized, lower, upper).toSequence()
    }

    @JvmStatic
    @CreatorsDsl
    fun getIntegerArray(sized: Int): IntArray {
        return if (sized < 1) EMPTY_INTS_ARRAY else IntArray(sized) { getInteger(sized) }
    }

    @JvmStatic
    @CreatorsDsl
    fun getIntegerArray(sized: Int, range: IntRange): IntArray {
        return if (sized < 1) EMPTY_INTS_ARRAY else IntArray(sized) { getInteger(range) }
    }

    @JvmStatic
    @CreatorsDsl
    fun getIntegerArray(sized: Int, lower: Int, upper: Int): IntArray {
        return if (sized < 1) EMPTY_INTS_ARRAY else IntArray(sized) { getInteger(lower, upper) }
    }

    @JvmStatic
    @CreatorsDsl
    fun <T> shuffled(list: Iterable<T>): List<T> {
        return list.shuffled(random)
    }

    @JvmStatic
    @CreatorsDsl
    fun getBits(count: Int) = kotlin.nextBits(count)

    @JvmStatic
    @CreatorsDsl
    fun getString(sized: Int): String = getString(sized, VALUES)

    @JvmStatic
    @CreatorsDsl
    fun getString(sized: Int, chars: CharArray): String = getCharSequenceOf(sized, chars).toString()

    @JvmStatic
    @CreatorsDsl
    fun getString(sized: Int, chars: CharSequence): String = getCharSequenceOf(sized, chars).toString()

    @JvmStatic
    @CreatorsDsl
    fun getCharSequenceOf(sized: Int): CharSequence = getCharSequenceOf(sized, VALUES)

    @JvmStatic
    @CreatorsDsl
    fun getCharSequenceOf(sized: Int, chars: CharSequence): CharSequence = getCharSequenceOf(sized, chars.getCharArray())

    @JvmStatic
    @CreatorsDsl
    fun getCharSequenceOf(sized: Int, chars: CharArray): CharSequence {
        if (sized < 1) {
            return EMPTY_STRING
        }
        if (chars.isEmpty()) {
            throw throw MercenaryFatalExceptiion(MATH_INVALID_SIZE_ERROR)
        }
        return stringOf(sized) {
            sized.forEach {
                add(chars[getInteger(chars.size)])
            }
        }
    }

    @JvmStatic
    @CreatorsDsl
    @IgnoreForSerialize
    fun getCharArrayValues(): CharArray = VALUES.copyOf()

    @JvmStatic
    @CreatorsDsl
    @IgnoreForSerialize
    internal fun getCharArrayValuesInternal(): CharArray {
        return stringOf(72) {
            add('a'..'z').add('0'..'9').add('A'..'Z').add('0'..'9')
        }.getCharArray()
    }
}