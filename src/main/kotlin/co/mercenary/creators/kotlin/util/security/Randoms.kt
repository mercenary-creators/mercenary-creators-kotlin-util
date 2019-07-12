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

import java.security.SecureRandom

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
    fun getByteArray(random: SecureRandom, bytes: ByteArray) = random.nextBytes(bytes).let { bytes }

    @JvmStatic
    fun getLongRange(range: IntRange) = getLongRange(range.first, range.last)

    @JvmStatic
    fun getLongRange(range: LongRange) = getLongRange(range.first, range.last)

    @JvmStatic
    fun getLongRange(lower: Int, upper: Int) = getLongRange(lower.toLong(), upper.toLong())

    @JvmStatic
    fun getLongRange(lower: Long, upper: Long) = (getDouble() * ((upper + 1) - lower)).toLong() + lower

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
            throw IllegalArgumentException("Illegal sized size $sized")
        }
        if (sized < 1) {
            return ""
        }
        if (chars.isEmpty()) {
            throw IllegalArgumentException("Illegal chars size 0")
        }
        return StringBuilder(sized).also { maker ->
            repeat(sized) {
                maker.append(chars[getInteger(chars.size)])
            }
        }
    }

    @JvmStatic
    fun getCharArrayValues(): CharArray = VALUES.copyOf()

    internal fun getCharArrayValuesInternal(): CharArray {
        val buff = StringBuilder(72)
        for (c in 'a'..'z') {
            buff.append(c)
        }
        for (c in '0'..'9') {
            buff.append(c)
        }
        for (c in 'A'..'Z') {
            buff.append(c)
        }
        for (c in '0'..'9') {
            buff.append(c)
        }
        return buff.toString().toCharArray()
    }
}