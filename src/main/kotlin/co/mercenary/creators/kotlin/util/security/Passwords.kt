/*
 * Copyright (c) 2021, Mercenary Creators Company. All rights reserved.
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

@FrameworkDsl
@IgnoreForSerialize
object Passwords {

    @FrameworkDsl
    const val PART_SIZE = 8

    @FrameworkDsl
    const val MIN_PARTS = 3

    @FrameworkDsl
    const val MAX_PARTS = 31

    @FrameworkDsl
    const val THE_PARTS = 15

    @FrameworkDsl
    const val SEPARATOR = '-'

    @FrameworkDsl
    const val SALT_SIZE = 64

    @FrameworkDsl
    const val MIN_LOOPS = 16 * 1024

    @FrameworkDsl
    const val MAX_LOOPS = 96 * 1024

    @FrameworkDsl
    const val THE_LOOPS = 64 * 1024

    @JvmStatic
    @FrameworkDsl
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
    @FrameworkDsl
    @JvmOverloads
    fun getGeneratedPass(part: Int = THE_PARTS): CharSequence {
        val loop = part.boxIn(MIN_PARTS, MAX_PARTS)
        val buff = StringBuilder((loop * PART_SIZE) + loop + PART_SIZE)
        for (i in 0 until loop) {
            buff.add(Randoms.getString(PART_SIZE)).add(SEPARATOR)
        }
        return buff.add(CheckSums.crc32().encoder(buff.copyOf())).copyOf()
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun isValid(pass: CharSequence, part: Int = THE_PARTS): Boolean {
        val loop = part.boxIn(MIN_PARTS, MAX_PARTS)
        val last = pass.lastIndexOf(SEPARATOR)
        if (last != (((loop * PART_SIZE) + loop) - 1)) {
            return false
        }
        val list = pass.split(SEPARATOR)
        if (list.sizeOf() != (loop + 1)) {
            return false
        }
        for (i in 0 until loop) {
            if (list[i].sizeOf() != PART_SIZE) {
                return false
            }
        }
        return pass.endsWith(CheckSums.crc32().encoder(pass.substring(0, last + 1)))
    }
}