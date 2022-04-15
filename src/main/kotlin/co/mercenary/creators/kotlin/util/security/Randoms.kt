/*
 * Copyright (c) 2022, Mercenary Creators Company. All rights reserved.
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

@file:Suppress("NOTHING_TO_INLINE", "UNCHECKED_CAST", "FunctionName", "HttpUrlsUsage")

package co.mercenary.creators.kotlin.util.security

import co.mercenary.creators.kotlin.util.*
import java.security.SecureRandom
import java.util.*
import kotlin.random.*

@IgnoreForSerialize
object Randoms {

    @FrameworkDsl
    private const val SEED_SIZE_MINIMUM = 1 shl 10

    @FrameworkDsl
    private const val SEED_SIZE_MAXIMUM = 1 shl 16

    @FrameworkDsl
    private fun SecureRandom.getSeedByteArray(size: Int): ByteArray {
        return generateSeed(size.maxOf(0))
    }

    @FrameworkDsl
    private fun SecureRandom.seedToSize(size: Int): SecureRandom {
        return seedToData(getSeedByteArray(size.boxIn(SEED_SIZE_MINIMUM, SEED_SIZE_MAXIMUM)))
    }

    @FrameworkDsl
    private fun SecureRandom.seedToLong(seed: Long): SecureRandom {
        if (seed.isNotExhausted()) {
            setSeed(seed)
        }
        return this
    }

    @FrameworkDsl
    private fun SecureRandom.seedToData(seed: ByteArray): SecureRandom {
        if (seed.isNotExhausted()) {
            setSeed(seed.toByteArray(true))
        }
        return this
    }

    @FrameworkDsl
    private fun SecureRandom.nextByteArray(bytes: ByteArray, copy: Boolean = false): ByteArray {
        return bytes.toByteArray(copy.isTrue()).also { nextBytes(it) }
    }

    @FrameworkDsl
    private val VALUES: CharArray by lazy {
        stringOf(72) {
            add('a'..'z').add('0'..'9').add('A'..'Z').add('0'..'9')
        }.getCharArray(false)
    }

    @FrameworkDsl
    private val RANDOM: SecureRandom by lazy {
        SecureRandom()
    }

    @FrameworkDsl
    private inline fun hexvals() = VALUES.toCharArray(false)

    @FrameworkDsl
    private inline fun secure() = RANDOM

    @FrameworkDsl
    private val STRONG: SecureRandom by lazy {
        getStrongInstance()
    }

    @FrameworkDsl
    private inline fun strong() = STRONG

    @FrameworkDsl
    private val KOTLIN: kotlin.random.Random by lazy {
        secure().asKotlinRandom()
    }

    @FrameworkDsl
    private inline fun kotlin() = KOTLIN

    @JvmStatic
    @FrameworkDsl
    @IgnoreForSerialize
    fun getAlgorithms(): Algorithm = Algorithm.forName("SecureRandom")

    @JvmStatic
    @FrameworkDsl
    fun uuid() = UUID.randomUUID().toString()

    @JvmStatic
    @FrameworkDsl
    fun randomOf() = SecureRandom()

    @JvmStatic
    @FrameworkDsl
    fun strongOf() = STRONG

    @JvmStatic
    @FrameworkDsl
    fun randomOf(seed: Long) = randomOf().seedToLong(seed)

    @JvmStatic
    @FrameworkDsl
    @IgnoreForSerialize
    fun getInteger() = secure().nextInt()

    @JvmStatic
    @FrameworkDsl
    fun getInteger(bound: Int) = secure().nextInt(bound)

    @JvmStatic
    @FrameworkDsl
    @IgnoreForSerialize
    fun getDouble() = secure().nextDouble()

    @JvmStatic
    @FrameworkDsl
    @IgnoreForSerialize
    fun getBoolean() = secure().nextBoolean()

    @JvmStatic
    @FrameworkDsl
    @IgnoreForSerialize
    fun getLongValue() = secure().nextLong()

    @JvmStatic
    @FrameworkDsl
    fun getByteArray(sized: Int) = getByteArray(secure(), sized.maxOf(0))

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun getByteArray(bytes: ByteArray, copy: Boolean = false) = kotlin().nextBytes(bytes).toByteArray(copy)

    @JvmStatic
    @FrameworkDsl
    fun getByteArray(random: SecureRandom, sized: Int) = getByteArray(random, sized.toByteArray(), false)

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun getByteArray(random: SecureRandom, bytes: ByteArray, copy: Boolean = false) = random.nextByteArray(bytes, copy)

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun getByteArray(factory: Factory<SecureRandom>, bytes: ByteArray, copy: Boolean = false) = factory.create().nextByteArray(bytes, copy)

    @JvmStatic
    @FrameworkDsl
    fun getInteger(range: IntRange) = kotlin().nextInt(range)

    @JvmStatic
    @FrameworkDsl
    fun getInteger(lower: Int, upper: Int) = kotlin().nextInt(lower, upper)

    @JvmStatic
    @FrameworkDsl
    fun getLongValue(bound: Int) = getLongValue(bound.longOf())

    @JvmStatic
    @FrameworkDsl
    fun getLongValue(bound: Long) = kotlin().nextLong(bound)

    @JvmStatic
    @FrameworkDsl
    fun getLongValue(range: IntRange) = getLongValue(range.longOf())

    @JvmStatic
    @FrameworkDsl
    fun getLongValue(range: LongRange) = kotlin().nextLong(range)

    @JvmStatic
    @FrameworkDsl
    fun getLongValue(lower: Int, upper: Int) = getLongValue(lower.longOf(), upper.longOf())

    @JvmStatic
    @FrameworkDsl
    fun getLongValue(lower: Long, upper: Long) = kotlin().nextLong(lower, upper)

    @JvmStatic
    @FrameworkDsl
    fun getDouble(bound: Double) = kotlin().nextDouble(bound)

    @JvmStatic
    @FrameworkDsl
    fun getDouble(lower: Double, upper: Double) = kotlin().nextDouble(lower, upper)

    @JvmStatic
    @FrameworkDsl
    @IgnoreForSerialize
    fun getFloat() = kotlin().nextFloat()

    @JvmStatic
    @FrameworkDsl
    fun getLongSequence(sized: Int): Sequence<Long> {
        return getLongSequence(sized.longOf())
    }

    @JvmStatic
    @FrameworkDsl
    fun getLongSequence(sized: Long): Sequence<Long> {
        return if (sized < 1L) MercenarySequence() else secure().longs(sized).toSequence()
    }

    @JvmStatic
    @FrameworkDsl
    fun getLongSequence(sized: Int, lower: Long, upper: Long): Sequence<Long> {
        return getLongSequence(sized.longOf(), lower, upper)
    }

    @JvmStatic
    @FrameworkDsl
    fun getLongSequence(sized: Long, lower: Long, upper: Long): Sequence<Long> {
        return if (sized < 1L) MercenarySequence() else secure().longs(sized, lower, upper).toSequence()
    }

    @JvmStatic
    @FrameworkDsl
    fun getIntegerSequence(sized: Int): Sequence<Int> {
        return getIntegerSequence(sized.longOf())
    }

    @JvmStatic
    @FrameworkDsl
    fun getIntegerSequence(sized: Long): Sequence<Int> {
        return if (sized < 1L) MercenarySequence() else secure().ints(sized).toSequence()
    }

    @JvmStatic
    @FrameworkDsl
    fun getIntegerSequence(sized: Int, lower: Int, upper: Int): Sequence<Int> {
        return getIntegerSequence(sized.longOf(), lower, upper)
    }

    @JvmStatic
    @FrameworkDsl
    fun getIntegerSequence(sized: Long, lower: Int, upper: Int): Sequence<Int> {
        return if (sized < 1L) MercenarySequence() else secure().ints(sized, lower, upper).toSequence()
    }

    @JvmStatic
    @FrameworkDsl
    fun getDoubleSequence(sized: Int): Sequence<Double> {
        return getDoubleSequence(sized.longOf())
    }

    @JvmStatic
    @FrameworkDsl
    fun getDoubleSequence(sized: Long): Sequence<Double> {
        return if (sized < 1L) MercenarySequence() else secure().doubles(sized).toSequence()
    }

    @JvmStatic
    @FrameworkDsl
    fun getDoubleSequence(sized: Int, lower: Double, upper: Double): Sequence<Double> {
        return getDoubleSequence(sized.longOf(), lower, upper)
    }

    @JvmStatic
    @FrameworkDsl
    fun getDoubleSequence(sized: Long, lower: Double, upper: Double): Sequence<Double> {
        return if (sized < 1L) MercenarySequence() else secure().doubles(sized, lower, upper).toSequence()
    }

    @JvmStatic
    @FrameworkDsl
    fun getIntegerArray(sized: Int): IntArray {
        return if (sized < 1) EMPTY_INTS_ARRAY else sized.toIntArray { getInteger(sized) }
    }

    @JvmStatic
    @FrameworkDsl
    fun getIntegerArray(sized: Int, range: IntRange): IntArray {
        return if (sized < 1) EMPTY_INTS_ARRAY else sized.toIntArray { getInteger(range) }
    }

    @JvmStatic
    @FrameworkDsl
    fun getIntegerArray(sized: Int, lower: Int, upper: Int): IntArray {
        return if (sized < 1) EMPTY_INTS_ARRAY else sized.toIntArray { getInteger(lower, upper) }
    }

    @JvmStatic
    @FrameworkDsl
    fun getLongArray(sized: Int): LongArray {
        return if (sized < 1) EMPTY_LONG_ARRAY else sized.toLongArray()
    }

    @JvmStatic
    @FrameworkDsl
    fun getLongArray(sized: Int, range: IntRange): LongArray {
        return if (sized < 1) EMPTY_LONG_ARRAY else sized.toLongArray { getLongValue(range) }
    }

    @JvmStatic
    @FrameworkDsl
    fun getLongArray(sized: Int, lower: Int, upper: Int): LongArray {
        return if (sized < 1) EMPTY_LONG_ARRAY else sized.toLongArray { getLongValue(lower, upper) }
    }

    @JvmStatic
    @FrameworkDsl
    fun getLongArray(sized: Int, range: LongRange): LongArray {
        return if (sized < 1) EMPTY_LONG_ARRAY else sized.toLongArray { getLongValue(range) }
    }

    @JvmStatic
    @FrameworkDsl
    fun getLongArray(sized: Int, lower: Long, upper: Long): LongArray {
        return if (sized < 1) EMPTY_LONG_ARRAY else sized.toLongArray { getLongValue(lower, upper) }
    }

    @JvmStatic
    @FrameworkDsl
    fun <T> shuffled(list: Iterable<T>): List<T> {
        if (list is Collection) {
            if (list.sizeOf().isLessThan(2)) {
                return toListOf(list)
            }
        }
        return list.shuffled(kotlin())
    }

    @JvmStatic
    @FrameworkDsl
    fun shuffled(list: CharSequence): CharSequence {
        if (list.sizeOf().isLessThan(2)) {
            return list.copyOf()
        }
        return shuffled(list.getCharArray()).getContentText()
    }

    @JvmStatic
    @FrameworkDsl
    fun <T> shuffled(list: Sequence<T>): Sequence<T> {
        return list.shuffled(kotlin())
    }

    @JvmStatic
    @FrameworkDsl
    fun shuffled(list: IntRange): IntArray {
        return if (list.isExhausted()) EMPTY_INTS_ARRAY else list.getIntArray().let { ints ->
            if (ints.sizeOf() < 2) ints else ints.also { it.shuffle(kotlin()) }
        }
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun <T> shuffled(list: Array<T>, copy: Boolean = false): Array<T> {
        if (list.sizeOf().isLessThan(2)) {
            return list.toArray(copy)
        }
        return list.toArray(copy).also { it.shuffle(kotlin()) }
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun shuffled(list: IntArray, copy: Boolean = false): IntArray {
        if (list.sizeOf().isLessThan(2)) {
            return list.toIntArray(copy)
        }
        return list.toIntArray(copy).also { it.shuffle(kotlin()) }
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun shuffled(list: ByteArray, copy: Boolean = false): ByteArray {
        if (list.sizeOf().isLessThan(2)) {
            return list.toByteArray(copy)
        }
        return list.toByteArray(copy).also { it.shuffle(kotlin()) }
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun shuffled(list: CharArray, copy: Boolean = false): CharArray {
        if (list.sizeOf().isLessThan(2)) {
            return list.toCharArray(copy)
        }
        return list.toCharArray(copy).also { it.shuffle(kotlin()) }
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun shuffled(list: LongArray, copy: Boolean = false): LongArray {
        if (list.sizeOf().isLessThan(2)) {
            return list.toLongArray(copy)
        }
        return list.toLongArray(copy).also { it.shuffle(kotlin()) }
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun shuffled(list: ShortArray, copy: Boolean = false): ShortArray {
        if (list.sizeOf().isLessThan(2)) {
            return list.toShortArray(copy)
        }
        return list.toShortArray(copy).also { it.shuffle(kotlin()) }
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun shuffled(list: FloatArray, copy: Boolean = false): FloatArray {
        if (list.sizeOf().isLessThan(2)) {
            return list.toFloatArray(copy)
        }
        return list.toFloatArray(copy).also { it.shuffle(kotlin()) }
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun shuffled(list: DoubleArray, copy: Boolean = false): DoubleArray {
        if (list.sizeOf().isLessThan(2)) {
            return list.toDoubleArray(copy)
        }
        return list.toDoubleArray(copy).also { it.shuffle(kotlin()) }
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun shuffled(list: BooleanArray, copy: Boolean = false): BooleanArray {
        if (list.sizeOf().isLessThan(2)) {
            return list.toBooleanArray(copy)
        }
        return list.toBooleanArray(copy).also { it.shuffle(kotlin()) }
    }

    @JvmStatic
    @FrameworkDsl
    fun getBits(count: Int) = kotlin().nextBits(count)

    @JvmStatic
    @FrameworkDsl
    fun getString(sized: Int): String = getString(sized, hexvals())

    @JvmStatic
    @FrameworkDsl
    fun getString(sized: Int, chars: CharArray): String = getCharSequenceOf(sized, chars).copyOf()

    @JvmStatic
    @FrameworkDsl
    fun getString(sized: Int, chars: CharSequence): String = getCharSequenceOf(sized, chars).copyOf()

    @JvmStatic
    @FrameworkDsl
    fun getCharSequenceOf(sized: Int): CharSequence = getCharSequenceOf(sized, VALUES)

    @JvmStatic
    @FrameworkDsl
    fun getCharSequenceOf(sized: Int, chars: CharSequence): CharSequence = getCharSequenceOf(sized, chars.getCharArray())

    @JvmStatic
    @FrameworkDsl
    fun getCharSequenceOf(sized: Int, chars: CharArray): CharSequence {
        if (sized < 1) {
            return EMPTY_STRING
        }
        if (chars.isExhausted()) {
            throw throw MercenaryFatalExceptiion(MATH_INVALID_SIZE_ERROR)
        }
        return stringOf(sized) {
            sized.forEach {
                add(chars[getInteger(chars.sizeOf())])
            }
        }
    }

    @JvmStatic
    @FrameworkDsl
    @IgnoreForSerialize
    fun getCharArrayValues(): CharArray = VALUES.toCharArray(true)

    @JvmStatic
    @FrameworkDsl
    @IgnoreForSerialize
    fun getStrongInstance(): SecureRandom {
        return try {
            SecureRandom.getInstanceStrong()
        } catch (cause: Throwable) {
            randomOf().seedToSize(SEED_SIZE_MAXIMUM)
        }
    }

    @JvmStatic
    @FrameworkDsl
    @IgnoreForSerialize
    fun getStrongInstanceFactory(): Factory<SecureRandom> = Randoms::getStrongInstance
}