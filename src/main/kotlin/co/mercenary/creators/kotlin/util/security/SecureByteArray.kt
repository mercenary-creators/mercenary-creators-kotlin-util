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

@IgnoreForSerialize
class SecureByteArray @FrameworkDsl constructor(data: ByteArray) : SecureBytes<SecureByteArray> {

    @FrameworkDsl
    constructor() : this(EMPTY_BYTE_ARRAY)

    @FrameworkDsl
    constructor(data: CharSequence) : this(data.getContentData())

    @FrameworkDsl
    constructor(data: SecureByteArray) : this(recode(data.buff, data.swap))

    @FrameworkDsl
    private val swap = swapof(data)

    @FrameworkDsl
    private val buff = recode(data, swap, true)

    @FrameworkDsl
    override fun sizeOf(): Int {
        return buff.sizeOf()
    }

    @FrameworkDsl
    override fun toByteArray(flip: Boolean): ByteArray {
        return if (flip.isTrue()) recode(buff, swap) else buff.toByteArray()
    }

    @FrameworkDsl
    override fun toMapNames() = dictOfType<SecureByteArray>("size" to sizeOf())

    @FrameworkDsl
    override fun clone() = copyOf()

    @FrameworkDsl
    override fun copyOf() = SecureByteArray(this)

    @FrameworkDsl
    override fun toString() = dictOfType<SecureByteArray>().toSafeString()

    @FrameworkDsl
    override fun hashCode() = idenOf()

    @FrameworkDsl
    override fun equals(other: Any?) = when (other) {
        is SecureByteArray -> this === other
        else -> false
    }

    companion object {

        @JvmStatic
        @FrameworkDsl
        fun random(size: Int): SecureByteArray {
            return if (size < 1) SecureByteArray() else SecureByteArray(Randoms.getByteArray(size))
        }

        @JvmStatic
        @FrameworkDsl
        private fun swapof(data: ByteArray): IntArray {
            return if (data.sizeOf() < 2) EMPTY_INTS_ARRAY else Randoms.shuffled(data.indices)
        }

        @JvmStatic
        @FrameworkDsl
        private fun recode(data: ByteArray, swap: IntArray, init: Boolean = false, size: Int = data.sizeOf()): ByteArray {
            return when (size) {
                0 -> EMPTY_BYTE_ARRAY
                1 -> data.toByteArray()
                else -> when (init.isTrue()) {
                    true -> size.toByteArray() { iter -> data[swap[iter]] }
                    else -> data.toByteArray().also { self ->
                        size.forEach { iter ->
                            swap[iter].also { flip ->
                                if (iter != flip) {
                                    self[flip] = data[iter]
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}