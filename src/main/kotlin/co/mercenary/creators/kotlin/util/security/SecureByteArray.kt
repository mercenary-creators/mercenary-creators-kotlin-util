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

@IgnoreForSerialize
class SecureByteArray @CreatorsDsl constructor(data: ByteArray) : SecureBytes, StandardInterfaces<SecureByteArray> {

    @CreatorsDsl
    constructor() : this(EMPTY_BYTE_ARRAY)

    @CreatorsDsl
    constructor(data: Array<Byte>) : this(data.toByteArray())

    @CreatorsDsl
    constructor(data: CharSequence) : this(data.toString().toByteArray())

    @CreatorsDsl
    constructor(data: Iterable<Byte>) : this(data.toList().toByteArray())

    @CreatorsDsl
    constructor(data: Sequence<Byte>) : this(data.toList().toByteArray())

    @CreatorsDsl
    constructor(data: SecureByteArray) : this(recode(data.buff, data.swap))

    private val swap = swapof(data)

    private val buff = recode(data, swap, true)

    @CreatorsDsl
    override val size: Int
        @IgnoreForSerialize
        get() = buff.size

    @CreatorsDsl
    @IgnoreForSerialize
    override fun isEmpty() = buff.isEmpty().isTrue()

    @CreatorsDsl
    @IgnoreForSerialize
    override fun isNotEmpty() = isEmpty().isNotTrue()

    @CreatorsDsl
    @IgnoreForSerialize
    override fun toByteArray(flip: Boolean): ByteArray {
        return if (flip.isTrue()) recode(buff, swap) else buff.toByteArray()
    }

    @CreatorsDsl
    @IgnoreForSerialize
    override fun toMapNames() = dictOf("size" to buff.size, "type" to nameOf())

    @CreatorsDsl
    override fun clone() = copyOf()

    @CreatorsDsl
    override fun copyOf() = SecureByteArray(this)

    @CreatorsDsl
    override fun toString() = nameOf()

    @CreatorsDsl
    override fun hashCode() = idenOf()

    @CreatorsDsl
    override fun equals(other: Any?) = when (other) {
        is SecureByteArray -> this === other
        else -> false
    }

    companion object {

        @CreatorsDsl
        @IgnoreForSerialize
        private val EMPTY_SWAP_ARRAY = IntArray(0)

        @CreatorsDsl
        @IgnoreForSerialize
        private val EMPTY_BYTE_ARRAY = ByteArray(0)

        @JvmStatic
        @CreatorsDsl
        fun random(size: Int): SecureByteArray {
            return if (size < 1) SecureByteArray() else SecureByteArray(Randoms.getByteArray(size))
        }

        @JvmStatic
        @CreatorsDsl
        private fun swapof(data: ByteArray): IntArray {
            return if (data.size < 2) EMPTY_SWAP_ARRAY else Randoms.shuffled(data.indices).toIntArray()
        }

        @JvmStatic
        @CreatorsDsl
        private fun recode(data: ByteArray, swap: IntArray, init: Boolean = false): ByteArray {
            return when (val size = data.size) {
                0 -> EMPTY_BYTE_ARRAY
                1 -> ByteArray(1) { data[0] }
                else -> when (init.isTrue()) {
                    true -> ByteArray(size) { iter -> data[swap[iter]] }
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