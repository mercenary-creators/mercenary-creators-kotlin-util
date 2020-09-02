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
    @CreatorsDsl
    @JvmOverloads
    fun getGeneratedSalt(loop: Int = THE_LOOPS): CharSequence {
        val hash = Digests.sha512().proxyOf()
        val salt = Randoms.getByteArray(SALT_SIZE)
        val reps = loop.boxIn(MIN_LOOPS, MAX_LOOPS)
        for (i in 0 until reps) {
            hash.digest(salt)
        }
        return Encoders.hex().encode(salt)
    }

    @JvmStatic
    @CreatorsDsl
    @JvmOverloads
    fun getGeneratedPass(part: Int = THE_PARTS): CharSequence {
        val loop = part.boxIn(MIN_PARTS, MAX_PARTS)
        val buff = StringBuilder((loop * PART_SIZE) + loop + PART_SIZE)
        for (i in 0 until loop) {
            buff.add(Randoms.getString(PART_SIZE)).append(SEPARATOR)
        }
        return buff.append(CheckSums.crc32().encoder(buff.toString())).toString()
    }

    @JvmStatic
    @CreatorsDsl
    @JvmOverloads
    fun isValid(pass: CharSequence, part: Int = THE_PARTS): Boolean {
        val loop = part.boxIn(MIN_PARTS, MAX_PARTS)
        val last = pass.lastIndexOf(SEPARATOR)
        if (last != (((loop * PART_SIZE) + loop) - 1)) {
            return false
        }
        val list = pass.split(SEPARATOR)
        if (list.size != (loop + 1)) {
            return false
        }
        for (i in 0 until loop) {
            if (list[i].length != PART_SIZE) {
                return false
            }
        }
        return pass.endsWith(CheckSums.crc32().encoder(pass.substring(0, last + 1)))
    }
}