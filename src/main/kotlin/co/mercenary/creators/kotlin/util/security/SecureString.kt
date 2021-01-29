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
class SecureString @FrameworkDsl constructor(data: CharArray) : SecureChars<SecureString> {

    @FrameworkDsl
    constructor() : this(EMPTY_CHAR_ARRAY)

    @FrameworkDsl
    constructor(data: CharSequence) : this(data.toCharArray(false))

    @FrameworkDsl
    constructor(data: SecureString) : this(recode(data.buff, data.swap))

    @FrameworkDsl
    constructor(data: Iterable<Char>) : this(data.toList().toCharArray())

    @FrameworkDsl
    private val swap = swapof(data)

    @FrameworkDsl
    private val buff = recode(data, swap, true)

    @FrameworkDsl
    override fun sizeOf(): Int {
        return buff.sizeOf()
    }

    @FrameworkDsl
    override fun toCharArray(flip: Boolean): CharArray {
        return if (flip.isTrue()) recode(buff, swap) else buff.toCharArray()
    }

    @FrameworkDsl
    override fun toMapNames() = dictOf("size" to sizeOf(), "type" to nameOf())

    @FrameworkDsl
    override fun clone() = copyOf()

    @FrameworkDsl
    override fun copyOf() = SecureString(this)

    @FrameworkDsl
    override fun toString() = nameOf()

    @FrameworkDsl
    override fun hashCode() = idenOf()

    @FrameworkDsl
    override fun equals(other: Any?) = when (other) {
        is SecureString -> this === other
        else -> false
    }

    companion object {

        @JvmStatic
        @FrameworkDsl
        private fun swapof(data: CharArray): IntArray {
            return if (data.sizeOf() < 2) EMPTY_INTS_ARRAY else Randoms.shuffled(data.indices).toIntArray()
        }

        @JvmStatic
        @FrameworkDsl
        private fun recode(data: CharArray, swap: IntArray, init: Boolean = false, size: Int = data.sizeOf()): CharArray {
            return when (size) {
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