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
class SecureString @CreatorsDsl constructor(data: CharArray) : SecureChars, StandardInterfaces<SecureString> {

    @CreatorsDsl
    constructor() : this(EMPTY_CHAR_ARRAY)

    @CreatorsDsl
    constructor(data: Array<Char>) : this(data.toCharArray())

    @CreatorsDsl
    constructor(data: SecureString) : this(recode(data.buff, data.swap))

    @CreatorsDsl
    constructor(data: CharSequence) : this(data.toString().toCharArray())

    @CreatorsDsl
    constructor(data: Iterable<Char>) : this(data.toList().toCharArray())

    @CreatorsDsl
    constructor(data: Sequence<Char>) : this(data.toList().toCharArray())

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
    override fun toCharArray(flip: Boolean): CharArray {
        return if (flip.isTrue()) recode(buff, swap) else buff.toCharArray()
    }

    @CreatorsDsl
    @IgnoreForSerialize
    override fun toMapNames() = dictOf("size" to size, "type" to nameOf())

    @CreatorsDsl
    override fun clone() = copyOf()

    @CreatorsDsl
    override fun copyOf() = SecureString(this)

    @CreatorsDsl
    override fun toString() = nameOf()

    @CreatorsDsl
    override fun hashCode() = idenOf()

    @CreatorsDsl
    override fun equals(other: Any?) = when (other) {
        is SecureString -> this === other
        else -> false
    }

    companion object {

        @JvmStatic
        @CreatorsDsl
        private fun swapof(data: CharArray): IntArray {
            return if (data.size < 2) EMPTY_INTS_ARRAY else Randoms.shuffled(data.indices).toIntArray()
        }

        @JvmStatic
        @CreatorsDsl
        private fun recode(data: CharArray, swap: IntArray, init: Boolean = false): CharArray {
            return when (val size = data.size) {
                0 -> EMPTY_CHAR_ARRAY
                1 -> data.toCharArray()
                else -> when (init.isTrue()) {
                    true -> CharArray(size) { iter -> data[swap[iter]] }
                    else -> data.toCharArray().also { self ->
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