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

import kotlin.math.*

@Suppress("NOTHING_TO_INLINE")
object Passwords {

    const val PART_SIZE = 8

    const val MIN_PARTS = 3

    const val MAX_PARTS = 31

    const val THE_PARTS = 15

    const val SEPARATOR = '-'

    const val SALT_SIZE = 64

    const val MIN_LOOPS = 16 * 1024

    const val MAX_LOOPS = 96 * 1024

    const val THE_LOOPS = 64 * 1024

    @JvmStatic
    @JvmOverloads
    fun salt(loop: Int = THE_LOOPS): CharSequence {
        val hash = Digests.proxy(Digests.sha512())
        val data = Randoms.getByteArray(SALT_SIZE)
        repeat(getLoopOf(loop)) {
            hash(data)
        }
        return Encoders.hex().encode(data)
    }

    @JvmStatic
    @JvmOverloads
    fun pass(part: Int = THE_PARTS): CharSequence {
        val loop = getPartOf(part)
        val buff = StringBuilder((loop * PART_SIZE) + loop + PART_SIZE)
        repeat(loop) {
            buff.append(Randoms.getString(PART_SIZE)).append(SEPARATOR)
        }
        return buff.append(CheckSums.crc32().encoder(buff.toString())).toString()
    }

    @JvmStatic
    @JvmOverloads
    fun good(pass: CharSequence, test: Boolean = true, part: Int = THE_PARTS): Boolean {
        val loop = getPartOf(part)
        val last = pass.lastIndexOf(SEPARATOR)
        if (last != (((loop * PART_SIZE) + loop) - 1)) {
            return false
        }
        if (test && oops(pass.split(SEPARATOR), loop)) {
            return false
        }
        return pass.endsWith(CheckSums.crc32().encoder(pass.substring(0, last + 1)))
    }

    @JvmStatic
    private fun oops(list: List<String>, loop: Int): Boolean {
        if (list.size != (loop + 1)) {
            return true
        }
        for (i in 0 until loop) {
            val buff = list[i]
            if (buff.length != PART_SIZE) {
                return true
            }
        }
        return false
    }

    private inline fun getPartOf(part: Int) = min(max(part, MIN_PARTS), MAX_PARTS)

    private inline fun getLoopOf(loop: Int) = min(max(loop, MIN_LOOPS), MAX_LOOPS)
}